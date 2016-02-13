
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
    String delaySelected;
    SendableChooser defenseChooser;
    SendableChooser endChooser;
    SendableChooser delayChooser;
    
    DriveTrain train;
    RollerClaw claw;
    LinearLift lift;
	DriverStation station;
	
	final double defLowbarTime = 10;
	final double defLowbarSpeed = 0.5;
	final double defRoughTime = 0;
	final double defRoughSpeed = 0;
	final double defMoatTime = 0;
	final double defMoatSpeed = 0;
	final double defRockTime = 0;
	final double defRockSpeed = 0;
	final double defRampartsTime = 0;
	final double defRampartsSpeed = 0;
	
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
        delayChooser.addObject("0 seconds", "delay0");
        delayChooser.addObject("5 seconds", "delay5");
        SmartDashboard.putData("Delay:", delayChooser);
        
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
    	delaySelected = (String) delayChooser.getSelected();
    	defenseSelected = (String) defenseChooser.getSelected();
    	endSelected = (String) endChooser.getSelected();
		System.out.println("Auto selected: " + delaySelected + defenseSelected + endSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	// if we're not done delaying, ...
    	if(!delayFin) {
	    	switch(delaySelected) {
	    	case "delay0":
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
	    	case "delay5":
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
    	
    	switch(endSelected) {
		case "endLG":
		default:
			// INSERT CODE TO GO TO LOW GOAL
    		break;
    	case "endNZ":
    		// INSERT CODE TO GO TO NEUTRAL ZONE
    		break;
    	case "endScore":
    		// INSERT CODE TO SCORE
    		break;
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
