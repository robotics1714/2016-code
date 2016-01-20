package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	Joystick stick;
	int autoLoopCounter;
	Relay relay;
	boolean trigger;
	boolean toggleState = false;
	boolean lastTrigger = false;
	boolean LimitSwitch;
	boolean LStoggleState = false;
	boolean lastLimitSwitch = false;
	AnalogPotentiometer potentiometer;
	DigitalInput limitSwitch;
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	myRobot = new RobotDrive(0,1);
    	stick = new Joystick(0);
    	relay = new Relay(0);
    	relay.set(Relay.Value.kForward);
    	potentiometer = new AnalogPotentiometer(1);
    	limitSwitch = new DigitalInput(0);
    	
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			myRobot.drive(-0.5, 0.0); 	// drive forwards half speed
			autoLoopCounter++;
			} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
		}
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        myRobot.arcadeDrive(stick);
        trigger = stick.getTrigger();
        if(trigger && toggleState == false && !lastTrigger)
        {
        	relay.set(Relay.Value.kForward);
        	toggleState = true;
        }
        else if(trigger && toggleState == true && !lastTrigger)
        {
        	relay.set(Relay.Value.kReverse);
        	toggleState = false;
        }
        lastTrigger = stick.getTrigger();
        
        
        LimitSwitch=limitSwitch.get();
        /*if(LimitSwitch && LStoggleState==false && !lastLimitSwitch){
        	relay.set(Relay.Value.kForward);
        	LStoggleState=true;
        }
        else if(LimitSwitch && LStoggleState==true && !lastLimitSwitch){
        	relay.set(Relay.Value.kReverse);
        	LStoggleState=false;
        }
        lastLimitSwitch=limitSwitch.get();
        */
        //the code above is to turn the light on or off when the limit switch is triggered
        
        if(LimitSwitch){
        	relay.set(Relay.Value.kForward);
        }
        else{
        	relay.set(Relay.Value.kReverse);
        }
        
        SmartDashboard.putNumber("Potentiometer reading",potentiometer.get());
        System.out.println(potentiometer.get());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
