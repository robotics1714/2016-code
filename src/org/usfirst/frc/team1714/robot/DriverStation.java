package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Joystick;

public class DriverStation {
	Joystick leftStick, rightStick, xboxStick;
	private DriveTrain train;
	private LinearLift lift;
	private RollerClaw claw;
	private double rightSpeed = 0;
	private double leftSpeed = 0;
	private double speedIncrement = 0.1;
	// private RobotDrive drive;
	
	int button;//placeholder
	
	boolean scaleMode=false,liftOverride=false;
	
	DriverStation(DriveTrain driveTrain, RollerClaw rollerClaw, LinearLift linearLift){
		train = driveTrain;
		claw = rollerClaw;
		lift = linearLift;
		leftStick = new Joystick(1);
		rightStick = new Joystick(0);
		xboxStick= new Joystick(2);
		// drive = new RobotDrive(driveTrain.tLeftFront, driveTrain.tLeftRear ,driveTrain.tRightFront, driveTrain.tRightRear);	
	}
	
	public void update(){
		if(rightStick.getRawButton(0) || leftStick.getRawButton(0)) {
			leftSpeed = 0;
			rightSpeed = 0;
		}
		else {
			if(rightStick.getRawButton(2)) {
				rightSpeed -= speedIncrement;
			}
			else if(rightStick.getRawButton(3)) {
				rightSpeed += speedIncrement;
			}
			
			if(leftStick.getRawButton(2)) {
				leftSpeed -= speedIncrement;
			}
			else if(leftStick.getRawButton(3)) {
				leftSpeed += speedIncrement;
			}
		}
		train.setLeftSide(leftSpeed);
		train.setRightSide(rightSpeed);
	}
}
