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
		if(rightStick.getRawButton(1) || leftStick.getRawButton(1)) {
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
			
			if(rightStick.getRawButton(7)) {
				train.setShiftGearLow();
			}
			else if(rightStick.getRawButton(6)) {
				train.setShiftGearHigh();
			}
			
			if(rightStick.getRawButton(5)) {
				lift.setLiftStop();
			}
			else if(rightStick.getRawButton(8)) {
				lift.setRetractLift();
			}
			else if(rightStick.getRawButton(9)) {
				lift.setExtendLift();
			}
			
			if(leftStick.getRawButton(8)) {
				lift.setTiltLiftUp();
			}
			
			if(rightStick.getRawButton(4)) {
				claw.setRollerBarStop();
			}
			else if(rightStick.getRawButton(10)) {
				claw.setRollerBarIn();
			}
			else if(rightStick.getRawButton(11)) {
				claw.setRollerBarOut();
			}
			
			if(leftStick.getRawButton(8) && leftStick.getRawButton(9)) {
				lift.setTiltLiftUp();
			}
			
			if(leftStick.getRawButton(5)) {
				claw.setRollerArmStop();
			}
			else if(leftStick.getRawButton(10)) {
				claw.setRollerArmDown();
			}
			else if(leftStick.getRawButton(11)) {
				claw.setRollerArmUp();
			}
		}
		train.setLeftSide(leftSpeed);
		train.setRightSide(rightSpeed);
	}
}
