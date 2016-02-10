
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
    final String auto1Lowbar = "auto1Lowbar";
    final String auto1Rough = "auto1Rough";
    String autoSelected;
    SendableChooser chooser;
    
    DriveTrain train;
    RollerClaw claw;
    LinearLift lift;
    Control control;
	DriverStation station;
	
	final double auto1LowbarTime = 10;
	final double auto1LowbarSpeed = 0.5;
	double currentTime = 0;
	double lastTime = 0;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Auto 1: Lowbar", auto1Lowbar);
        chooser.addObject("Auto 1: Rough Terrain", auto1Rough);
        SmartDashboard.putData("Auto choices", chooser);
        
        train = new DriveTrain();
        claw = new RollerClaw();
        lift = new LinearLift();
        control = new Control(train, claw, lift);
        station= new DriverStation(train, claw, lift, control);
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
    	autoSelected = (String) chooser.getSelected();
		System.out.println("Auto selected: " + autoSelected);
		lastTime = Timer.getFPGATimestamp();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case auto1Rough:
        //Put custom auto code here   
            break;
    	case auto1Lowbar:
    	default:
    		currentTime = Timer.getFPGATimestamp();
    		if((currentTime - lastTime) > auto1LowbarTime) {
    			train.setLeftSide(0.0);
    			train.setRightSide(0.0);
    			System.out.println("stopping \n");
    		}
    		else {
    			train.setLeftSide(auto1LowbarSpeed);
    			train.setRightSide(auto1LowbarSpeed);
    		}
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        train.update();
        claw.update();
        lift.update();
        control.update();
        station.update();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
