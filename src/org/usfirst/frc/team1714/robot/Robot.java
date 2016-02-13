
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
    int delaySelected;
    int positionSelected;
    SendableChooser defenseChooser;
    SendableChooser endChooser;
    SendableChooser delayChooser;
    SendableChooser positionChooser;
    
    DriveTrain train;
    RollerClaw claw;
    LinearLift lift;
	DriverStation station;
	
	// auto timing constants
	final double defLowbarTime = 10;
	final double defLowbarSpeed = 0.5;
	final double defRoughTime = 5;
	final double defRoughSpeed = 0.25;
	final double defMoatTime = 0;
	final double defMoatSpeed = 0;
	final double defRockTime = 0;
	final double defRockSpeed = 0;
	final double defRampartsTime = 0;
	final double defRampartsSpeed = 0;
	final double lgSpeed = 1;
	final double pos1Time = 7;
	final double pos2Time = 0;
	final double pos3Time = 0;
	final double pos4Time = 0;
	final double pos5Time = 0;
	
	//auto timing vars
	 boolean defRan = false;
	 boolean defFin = false;
	 boolean endRan = false;
	 boolean endFin = false;
	 boolean delayRan = false;
	 boolean delayFin = false;
	 
	 double defStartTime = 0;
	 double endStartTime = 0;
	 double delayStartTime = 0;
	 double currentTime = 0;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        defenseChooser = new SendableChooser();
        defenseChooser.addDefault("Lowbar", "defLowbar");
        defenseChooser.addObject("Rough Terrain", "defRough");
        defenseChooser.addObject("Moat", "defMoat");
        defenseChooser.addObject("Ramparts", "defRamparts");
        defenseChooser.addObject("Rock Wall", "defRock");
        SmartDashboard.putData("Defenses:", defenseChooser);
        endChooser = new SendableChooser();
        endChooser.addObject("Near Lowgoal", "endLG");
        endChooser.addObject("In Neutral Zone", "endNZ");
        endChooser.addObject("Score (EXPERIMENTAL)", "endScore");
        SmartDashboard.putData("End:", endChooser);
        delayChooser = new SendableChooser();
        delayChooser.addObject("0 seconds", 0);
        delayChooser.addObject("5 seconds", 5);
        SmartDashboard.putData("Delay:", delayChooser);
        positionChooser = new SendableChooser();
        positionChooser.addObject("Position 5 (Lowbar)", 5);
        positionChooser.addObject("Position 4", 4);
        positionChooser.addObject("Position 3", 3);
        positionChooser.addObject("Position 2", 2);
        positionChooser.addObject("Position 1", 1);
        SmartDashboard.putData("Position:", positionChooser);
        
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
    	delaySelected = (int) delayChooser.getSelected();
    	defenseSelected = (String) defenseChooser.getSelected();
    	endSelected = (String) endChooser.getSelected();
    	positionSelected = (int) positionChooser.getSelected();
		System.out.println("Auto selected: " + delaySelected + defenseSelected + endSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	// if we're not done delaying, ...
    	if(!delayFin) {
	    	switch(delaySelected) {
	    	case 0:
	    	default:
	    		currentTime = Timer.getFPGATimestamp();
	    		// if we haven't started timing, start timing
	    		if(!delayRan) {
	    			delayStartTime = Timer.getFPGATimestamp();
	    			delayRan = true;
	    		}
	    		// if we've reached the time we set, we're done delaying
	    		if((currentTime - delayStartTime) > 0) {
	    			delayFin = true;
	    		}
	    		break;
	    	case 5:
	    		currentTime = Timer.getFPGATimestamp();
	    		// if we haven't started timing, start timing
	    		if(!delayRan) {
	    			delayStartTime = Timer.getFPGATimestamp();
	    			delayRan = true;
	    		}
	    		// if we've reached the time we set, we're done delaying
	    		if((currentTime - delayStartTime) > 5) {
	    			delayFin = true;
	    		}
	    		break;
	    	}
    	}
    	
    	if(delayFin && !defFin) {
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
	    	default:
	    		currentTime = Timer.getFPGATimestamp();
	    		// if we haven't started timing, start timing
	    		if(!defRan) {
	    			defStartTime = Timer.getFPGATimestamp();
	    			defRan = true;
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
    	
	    	}
    	}
    	
    	if(delayFin && defFin && !endFin) {
	    	switch(endSelected) {
			case "endLG":
			default:
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
	    		default:
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
	    		break;
	    	case "endScore":
	    		//INSERT CODE TO SCORE
	    		endFin = true;
	    		break;
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
