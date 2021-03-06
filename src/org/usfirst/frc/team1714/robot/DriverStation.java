package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;


public class DriverStation {
	Joystick leftStick, rightStick, xboxStick;
	private DriveTrain train;
	private LinearLift lift;
	private RollerClaw claw;
	private RobotDrive drive;
	
	int button;//placeholder
	
	boolean scaleMode=false,liftOverride=false;
	
	DriverStation(DriveTrain driveTrain, RollerClaw rollerClaw, LinearLift linearLift){
		
		train = driveTrain;
		claw = rollerClaw;
		lift = linearLift;
		leftStick = new Joystick(1);
		rightStick = new Joystick(0);
		xboxStick= new Joystick(2);
		drive = new RobotDrive(driveTrain.tLeftFront, driveTrain.tLeftRear ,driveTrain.tRightFront, driveTrain.tRightRear);	
	}
	
	public void update(){
		drive.tankDrive(leftStick, rightStick, false);
		if(scaleMode==true){
			drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
			drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
			drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
			drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		}
		/*
		if(leftStick.getRawButton(button)){//enter scale mode, reverse the motor control
			scaleMode=!scaleMode;
		}
		*/
		
		if(rightStick.getRawButton(2)) {//shift the transmission to low gear
			train.setShiftGearLow();
		}
		else if(rightStick.getRawButton(3)) {//shift the transmmission to high gear
			train.setShiftGearHigh();
		}
		
		
		if(xboxStick.getPOV()==180){//roller roll inward the robot //D-pad down
			claw.setRollerBarIn();
		}
		if(xboxStick.getPOV()==0){//roller roll outward the robot //D-pad up
			claw.setRollerBarOut();
		}
		if(xboxStick.getPOV()==270 || xboxStick.getPOV()==90){ //stop roller claw with d-pad left or right
			claw.setRollerBarStop();
		}

		if(xboxStick.getRawButton(4)){//roller arm up //button Y
			claw.setRollerArmUp();
		}
		else if(xboxStick.getRawButton(1)){//roller arm down  //button A
			claw.setRollerArmDown();
		}
		else if(xboxStick.getRawButton(2)){//roller arm stop tilting  //button B
			claw.setRollerArmStop();
		}
		else if(xboxStick.getRawButton(3)){//move roller arm to a position //button X
			claw.setRollerArmPos();
		}
		
		if(xboxStick.getRawButton(6)){//liftExtend  //right bumper
			lift.setExtendLift();
		}
		else if(xboxStick.getRawButton(5)){//liftRetract  //left bumper
			lift.setRetractLift();
		}
		else if(xboxStick.getRawButton(8)){//liftStop  //start button
			lift.setLiftStop();
		}
		if(xboxStick.getRawButton(7)){//liftUp  //back button
			if(claw.rollerPot.get() > claw.rollerPotPos1) {
				lift.setTiltLiftUp();
			}
			else {
				claw.setRollerArmDown();
			}
		}
		if(xboxStick.getRawButton(9)) {
			lift.resetLift();
		}
		if(xboxStick.getRawButton(10)) {
			claw.setArmPowerLift();
		}
		
		/*
		if(xboxStick.getRawButton(6)){//slightly tilt the roller arm up //right bumper
			claw.setRollerArmAdjustUp();
		}
		else if(xboxStick.getRawButton(5)){//slightly tilt the roller arm down //left bumper
			claw.setRollerArmAdjustDown();
		}
		*/
		
		if(rightStick.getRawButton(8)) {
			train.setCompressorOff();
		}
		else if(rightStick.getRawButton(9)) {
			train.setCompressorOn();
		}
	}
	
}
