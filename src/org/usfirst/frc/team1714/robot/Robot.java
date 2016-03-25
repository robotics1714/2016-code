
package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {
    String defenseSelected;
    String endSelected;
    boolean ballSelected;
    // int positionSelected;
    SendableChooser defenseChooser;
    SendableChooser endChooser;
    SendableChooser ballChooser;
    SendableChooser positionChooser;
    
    DriveTrain train;
    RollerClaw claw;
    LinearLift lift;
	DriverStation station;
	// THESE ARE PLACHOLDERS, HANGE THEM !!!
	final double defLowbarTime = 7;
	final double defLowbarSpeed = 0.35;
	final double defRoughTime = 3.5;
	final double defRoughSpeed = 1.0;
	final double defMoatTime = 3.5;
	final double defMoatSpeed = 1.0;
	final double defRockTime = 3.5;
	final double defRockSpeed = 1.0;
	final double defRampartsTime = 3.5;
	final double defRampartsReachTime = 1.5;
	final double defRampartsSpeed = 1.0;
	final double def2Time = 4;
	final double def2Speed = 0.2;
	// THE TIMES ARE THE AMOUNT OF TIME AFTER STARTING, ---NOT--- AFTER THE LAST STAGE!! A STAGE SHOULD NEVER BE LESS THAN THE PREVIOUS STAGE!
	final double defGoal1Speed = 0.35;
	final double defGoal1Time = 7.75;
	final double defGoal2Speed = 0.35;
	final double defGoal2Time = 8.5;
	final double defGoal3Speed = 0.55;
	final double defGoal3Time = 14;
	
	/* 
	final double pos1Time = 7;
	final double pos2Time = 0;
	final double pos3Time = 0;
	final double pos4Time = 0;
	final double pos5Time = 0;
	*/
	
	//auto timing vars
	 boolean defRan = false;
	 boolean defFin = false;
	 boolean endRan = false;
	 boolean endFin = false;
	 boolean ballRan = false;
	 // boolean ballFin = false;
	 boolean ballPosessed = false;
	 boolean gearLow = false;
	 
	 double defStartTime = 0;
	 double endStartTime = 0;
	 double ballStartTime = 0;
	 double currentTime = 0;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        defenseChooser = new SendableChooser();
        defenseChooser.addObject("Lowbar", "defLowbar");
        defenseChooser.addObject("Lowbar + Lowgoal", "defGoal");
        defenseChooser.addObject("Rough Terrain", "defRough");
        defenseChooser.addObject("Moat", "defMoat");
        defenseChooser.addObject("Ramparts", "defRamparts");
        defenseChooser.addDefault("Rock Wall", "defRock");
        defenseChooser.addObject("Reach (any defense)", "def2");
        SmartDashboard.putData("Defenses:", defenseChooser);
        endChooser = new SendableChooser();
        // endChooser.addObject("Drive near the lowgoal", "endLG");
        // endChooser.addObject("Drive to the neutral zone", "endNZ");
        endChooser.addObject("Do nothing (choose this if reaching)", "end2");
        // endChooser.addObject("Score (DON'T USE THIS)", "endScore");
        // SmartDashboard.putData("End:", endChooser);
        ballChooser = new SendableChooser();
        ballChooser.addDefault("Keep Ball (IGNORED IF LG)", true);
        ballChooser.addObject("Drop Ball (IGNORED IF LG)", false);
        SmartDashboard.putData("Keep Ball:", ballChooser);
        // positionChooser = new SendableChooser();
        // positionChooser.addObject("Position 5 (Lowbar)", 5);
        // positionChooser.addObject("Position 4", 4);
        // positionChooser.addObject("Position 3", 3);
        // positionChooser.addObject("Position 2", 2);
        // positionChooser.addObject("Position 1", 1);
        // SmartDashboard.putData("Position:", positionChooser);
        
        train = new DriveTrain();
        claw = new RollerClaw();
        lift = new LinearLift();
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    
    public void autonomousInit() {
    	ballSelected = (boolean) ballChooser.getSelected();
    	defenseSelected = (String) defenseChooser.getSelected();
    	// endSelected = (String) endChooser.getSelected();
    	endSelected = "end2";
    	// positionSelected = (int) positionChooser.getSelected();
		System.out.println("Auto selected: " + ballSelected + defenseSelected + endSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	currentTime = Timer.getFPGATimestamp();
    	SmartDashboard.putNumber("Roller Pot", claw.rollerPot.get());
    	if(!gearLow) {
        	train.shiftGearLow();
        	gearLow = true;
    	}
    	
    	if(ballSelected && defenseSelected != "defGoal") {
			// ballPosessed = claw.ballDetectLS.get();
			ballPosessed = claw.laser.get();
    		claw.rollBallIn();
    		// ballFin = true;
    	}
    	else if(defenseSelected != "defGoal") {
    		claw.rollBallOut();
    		// ballFin = true;
    	}
    	else if((currentTime - defStartTime) < defGoal3Time) {
    		claw.rollBallIn();
    	}
    	
    	if(defenseSelected != "defLowbar" && defenseSelected != "defGoal") {
    		claw.tiltRollerArmUp();
    	}
    	
    	if(!defFin) {
    		if(!ballPosessed){
    			claw.setRollerBarIn();
    		}

	    	switch(defenseSelected) {
	    	case "defRough":
	    		currentTime = Timer.getFPGATimestamp();
	    		// if we haven't started timing, start timing
	    		if(!defRan) {
	    			defStartTime = Timer.getFPGATimestamp();
	    			defRan = true;
	    		}
	    		// if we've reached the time we set, we're done moving, so stop the motors.
	    		if((currentTime - defStartTime) > defRoughTime) {
	    			train.setLeftSide(0.0);
	    			train.setRightSide(0.0);
	    			defFin = true;
	    		}
	    		// if we haven't reached the time we set, keep moving!
	    		else {
	    			train.setRightSide(defRoughSpeed);
	    			train.setLeftSide(defRoughSpeed);
	    		}
	            break;
	    	case "defMoat":
	    		currentTime = Timer.getFPGATimestamp();
	    		// if we haven't started timing, start timing
	    		if(!defRan) {
	    			defStartTime = Timer.getFPGATimestamp();
	    			defRan = true;
	    		}
	    		// if we've reached the time we set, we're done moving, so stop the motors.
	    		if((currentTime - defStartTime) > defMoatTime) {
	    			train.setLeftSide(0.0);
	    			train.setRightSide(0.0);
	    			defFin = true;
	    		}
	    		// if we haven't reached the time we set, keep moving!
	    		else {
	    			train.setRightSide(defMoatSpeed);
	    			train.setLeftSide(defMoatSpeed);
	    		}
	    		break;
	    	case "defRamparts":
	    		currentTime = Timer.getFPGATimestamp();
	    		// if we haven't started timing, start timing
	    		if(!defRan) {
	    			defStartTime = Timer.getFPGATimestamp();
	    			defRan = true;
	    		}
	    		// if we've reached the time we set, we're done moving, so stop the motors.
	    		if((currentTime - defStartTime) > defRampartsTime) {
	    			train.setLeftSide(0.0);
	    			train.setRightSide(0.0);
	    			defFin = true;
	    		}
	    		// if we haven't reached the time we set, keep moving!
	    		else {
	    			train.setRightSide(defRampartsSpeed);
	    			train.setLeftSide(defRampartsSpeed);
	    		}
	    		break;
	    	case "defRock":
	    	default:
	    		currentTime = Timer.getFPGATimestamp();
	    		// if we haven't started timing, start timing
	    		if(!defRan) {
	    			defStartTime = Timer.getFPGATimestamp();
	    			defRan = true;
	    		}
	    		// if we've reached the time we set, we're done moving, so stop the motors.
	    		if((currentTime - defStartTime) > defRockTime) {
	    			train.setLeftSide(0.0);
	    			train.setRightSide(0.0);
	    			defFin = true;
	    		}
	    		// if we haven't reached the time we set, keep moving!
	    		else {
	    			train.setRightSide(defRockSpeed);
	    			train.setLeftSide(defRockSpeed);
	    		}
	    		break;
	    	case "defLowbar":
	    		currentTime = Timer.getFPGATimestamp();
	    		// if we haven't started timing, start timing
	    		if(!defRan) {
	    			defStartTime = Timer.getFPGATimestamp();
	    			defRan = true;
	    		}
	    		
	    		if(claw.rollerPot.get() < (claw.rollerPotPos1-claw.potBuffer)){
					claw.adjustRollerArmDown();
					System.out.println("adjusting arm down");
				}
				else if(claw.rollerPot.get() > (claw.rollerPotPos1+claw.potBuffer)){
					claw.adjustRollerArmUp();
					System.out.println("adjusting arm up");
				}
				else if(claw.rollerPot.get() > claw.rollerPotPos1-claw.potBuffer && claw.rollerPot.get() < claw.rollerPotPos1+claw.potBuffer){
					claw.holdRollerArm();
					System.out.println("holding arm");
				}
	    		
	    		// if we've reached the time we set, we're done moving, so stop the motors.
	    		if((currentTime - defStartTime) > defLowbarTime) {
	    			train.setLeftSide(0.0);
	    			train.setRightSide(0.0);
	    			defFin = true;
	    		}
	    		// if we haven't reached the time we set, keep moving!
	    		else {
	    			train.setRightSide(defLowbarSpeed);
	    			train.setLeftSide(defLowbarSpeed);
	    		}
	    		break;
	    	case "defGoal":
	    		currentTime = Timer.getFPGATimestamp();
	    		// if we haven't started timing, start timing
	    		if(!defRan) {
	    			defStartTime = Timer.getFPGATimestamp();
	    			defRan = true;
	    		}
	    		
	    		if(claw.rollerPot.get() < (claw.rollerPotPos1-claw.potBuffer)){
					claw.adjustRollerArmDown();
					System.out.println("adjusting arm down");
				}
				else if(claw.rollerPot.get() > (claw.rollerPotPos1+claw.potBuffer)){
					claw.adjustRollerArmUp();
					System.out.println("adjusting arm up");
				}
				else if(claw.rollerPot.get() > claw.rollerPotPos1-claw.potBuffer && claw.rollerPot.get() < claw.rollerPotPos1+claw.potBuffer){
					claw.holdRollerArm();
					System.out.println("holding arm");
				}
	    		
	    		// STAGE 4: STOP AND SHOOT THE BALL
	    		if((currentTime - defStartTime) > defGoal3Time) {
	    			train.setLeftSide(0.0);
	    			train.setRightSide(0.0);
	    			defFin = true;
	    			// SCORE THE BALL HERE
	    			claw.rollBallOut();
	    		}
	    		// STAGE 3: DRIVE TOWARDS THE LOW GOAL
	    		else if((currentTime - defStartTime) > defGoal2Time) {
	    			train.setRightSide(defGoal3Speed);
	    			train.setLeftSide(defGoal3Speed);
	    		}
	    		// STAGE 2: TURN TO THE RIGHT SLIGHTLY
	    		else if((currentTime - defStartTime) > defGoal1Time) {
	    			train.setLeftSide(defGoal2Speed);
	    			train.setRightSide(-defGoal2Speed);
	    		}
	    		// STAGE 1: DRIVE THROUGH THE LOWBAR
	    		else {
	    			train.setRightSide(defGoal1Speed);  
	    			train.setLeftSide(defGoal1Speed);
	    		}
	    		break;
	    	case "def2":
	    		currentTime = Timer.getFPGATimestamp();
	    		// if we haven't started timing, start timing
	    		if(!defRan) {
	    			defStartTime = Timer.getFPGATimestamp();
	    			defRan = true;
	    		}
	    		// if we've reached the time we set, we're done moving, so stop the motors.
	    		if((currentTime - defStartTime) > def2Time) {
	    			train.setLeftSide(0.0);
	    			train.setRightSide(0.0);
	    			defFin = true;
	    		}
	    		// if we haven't reached the time we set, keep moving!
	    		else {
	    			train.setRightSide(def2Speed);
	    			train.setLeftSide(def2Speed);
	    		}
	    		break;
	    	}
    	}
    	
    	if(defFin && !endFin) {
	    	switch(endSelected) {
	    	/*
			case "endLG":
				switch(positionSelected) {
				case 1:
	    			currentTime = Timer.getFPGATimestamp();
		    		// if we haven't started timing, start timing
		    		if(!endRan) {
		    			endStartTime = Timer.getFPGATimestamp();
		    			endRan = true;
		    		}
		    		// if we've reached the time we set, we're done moving, so stop the motors.
		    		if((currentTime - endStartTime) > pos1Time) {
		    			train.setLeftSide(0.0);
		    			train.setRightSide(0.0);
		    			endFin = true;
		    		}
		    		// if we haven't reached the time we set, keep moving (towards the lowgoal)!
		    		else {
		    			train.setRightSide(lgSpeed);
		    			train.setLeftSide(lgSpeed);
		    		}
					break;
				case 2:
	    			currentTime = Timer.getFPGATimestamp();
		    		// if we haven't started timing, start timing
		    		if(!endRan) {
		    			endStartTime = Timer.getFPGATimestamp();
		    			endRan = true;
		    		}
		    		// if we've reached the time we set, we're done moving, so stop the motors.
		    		if((currentTime - endStartTime) > pos2Time) {
		    			train.setLeftSide(0.0);
		    			train.setRightSide(0.0);
		    			endFin = true;
		    		}
		    		// if we haven't reached the time we set, keep moving (towards the lowgoal)!
		    		else {
		    			train.setRightSide(lgSpeed);
		    			train.setLeftSide(lgSpeed);
		    		}
					break;
				case 3:
	    			currentTime = Timer.getFPGATimestamp();
		    		// if we haven't started timing, start timing
		    		if(!endRan) {
		    			endStartTime = Timer.getFPGATimestamp();
		    			endRan = true;
		    		}
		    		// if we've reached the time we set, we're done moving, so stop the motors.
		    		if((currentTime - endStartTime) > pos3Time) {
		    			train.setLeftSide(0.0);
		    			train.setRightSide(0.0);
		    			endFin = true;
		    		}
		    		// if we haven't reached the time we set, keep moving (towards the lowgoal)!
		    		else {
		    			train.setRightSide(lgSpeed);
		    			train.setLeftSide(lgSpeed);
		    		}
					break;
				case 4:
	    			currentTime = Timer.getFPGATimestamp();
		    		// if we haven't started timing, start timing
		    		if(!endRan) {
		    			endStartTime = Timer.getFPGATimestamp();
		    			endRan = true;
		    		}
		    		// if we've reached the time we set, we're done moving, so stop the motors.
		    		if((currentTime - endStartTime) > pos4Time) {
		    			train.setLeftSide(0.0);
		    			train.setRightSide(0.0);
		    			endFin = true;
		    		}
		    		// if we haven't reached the time we set, keep moving (towards the lowgoal)!
		    		else {
		    			train.setRightSide(lgSpeed);
		    			train.setLeftSide(lgSpeed);
		    		}
					break;
				case 5:
				default:
	    			currentTime = Timer.getFPGATimestamp();
		    		// if we haven't started timing, start timing
		    		if(!endRan) {
		    			endStartTime = Timer.getFPGATimestamp();
		    			endRan = true;
		    		}
		    		// if we've reached the time we set, we're done moving, so stop the motors.
		    		if((currentTime - endStartTime) > pos5Time) {
		    			train.setLeftSide(0.0);
		    			train.setRightSide(0.0);
		    			endFin = true;
		    		}
		    		// if we haven't reached the time we set, keep moving (towards the lowgoal)!
		    		else {
		    			train.setRightSide(lgSpeed);
		    			train.setLeftSide(lgSpeed);
		    		}
					break;
				}
	    		break;
	    	case "endNZ":
	    		switch(defenseSelected) {
	    		case "defLowbar":
	    			currentTime = Timer.getFPGATimestamp();
		    		// if we haven't started timing, start timing
		    		if(!endRan) {
		    			endStartTime = Timer.getFPGATimestamp();
		    			endRan = true;
		    		}
		    		// if we've reached the time we set, we're done moving, so stop the motors.
		    		if((currentTime - endStartTime) > defLowbarTime) {
		    			train.setLeftSide(0.0);
		    			train.setRightSide(0.0);
		    			endFin = true;
		    		}
		    		// if we haven't reached the time we set, keep moving (backwards)!
		    		else {
		    			train.setRightSide(-defLowbarSpeed);
		    			train.setLeftSide(-defLowbarSpeed);
		    		}
	    			break;
	    		case "defRough":
	    			currentTime = Timer.getFPGATimestamp();
		    		// if we haven't started timing, start timing
		    		if(!endRan) {
		    			endStartTime = Timer.getFPGATimestamp();
		    			endRan = true;
		    		}
		    		// if we've reached the time we set, we're done moving, so stop the motors.
		    		if((currentTime - endStartTime) > defRoughTime) {
		    			train.setLeftSide(0.0);
		    			train.setRightSide(0.0);
		    			endFin = true;
		    		}
		    		// if we haven't reached the time we set, keep moving (backwards)!
		    		else {
		    			train.setRightSide(-defRoughSpeed);
		    			train.setLeftSide(-defRoughSpeed);
		    		}
	    			break;
	    		case "defRock":
	    			currentTime = Timer.getFPGATimestamp();
		    		// if we haven't started timing, start timing
		    		if(!endRan) {
		    			endStartTime = Timer.getFPGATimestamp();
		    			endRan = true;
		    		}
		    		// if we've reached the time we set, we're done moving, so stop the motors.
		    		if((currentTime - endStartTime) > defRockTime) {
		    			train.setLeftSide(0.0);
		    			train.setRightSide(0.0);
		    			endFin = true;
		    		}
		    		// if we haven't reached the time we set, keep moving (backwards)!
		    		else {
		    			train.setRightSide(-defRockSpeed);
		    			train.setLeftSide(-defRockSpeed);
		    		}
	    			break;
	    		case "defMoat":
	    			currentTime = Timer.getFPGATimestamp();
		    		// if we haven't started timing, start timing
		    		if(!endRan) {
		    			endStartTime = Timer.getFPGATimestamp();
		    			endRan = true;
		    		}
		    		// if we've reached the time we set, we're done moving, so stop the motors.
		    		if((currentTime - endStartTime) > defMoatTime) {
		    			train.setLeftSide(0.0);
		    			train.setRightSide(0.0);
		    			endFin = true;
		    		}
		    		// if we haven't reached the time we set, keep moving (backwards)!
		    		else {
		    			train.setRightSide(-defMoatSpeed);
		    			train.setLeftSide(-defMoatSpeed);
		    		}
	    			break;
	    		case "defRamparts":
	    			currentTime = Timer.getFPGATimestamp();
		    		// if we haven't started timing, start timing
		    		if(!endRan) {
		    			endStartTime = Timer.getFPGATimestamp();
		    			endRan = true;
		    		}
		    		// if we've reached the time we set, we're done moving, so stop the motors.
		    		if((currentTime - endStartTime) > defRampartsTime) {
		    			train.setLeftSide(0.0);
		    			train.setRightSide(0.0);
		    			endFin = true;
		    		}
		    		// if we haven't reached the time we set, keep moving (backwards)!
		    		else {
		    			train.setRightSide(-defRampartsSpeed);
		    			train.setLeftSide(-defRampartsSpeed);
		    		}
		    		break;
	    		}
	    		*/
    		case "end2":
    		default:
    			break;
    		/*
	    	case "endScore":
	    		//INSERT CODE TO SCORE
	    		endFin = true;
	    		break;
	    	*/
	    	}
    	}
	}
    
    public void teleopInit(){
    	station = new DriverStation(train, claw, lift);
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        train.update();
        claw.update();
        lift.update();
        station.update();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
