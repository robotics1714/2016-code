package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriverStation {
	Joystick leftStick, rightStick, xboxStick;
	private DriveTrain train;
	private LinearLift lift;
	private RollerClaw claw;
	private Control control;
	private RobotDrive drive;
	
	int button;//placeholder
	
	boolean scaleMode=false,liftOverride=false;
	
	DriverStation(DriveTrain driveTrain, RollerClaw rollerClaw, LinearLift linearLift, Control control){
		train = driveTrain;
		claw = rollerClaw;
		lift = linearLift;
		leftStick = new Joystick(1);
		rightStick = new Joystick(0);
		xboxStick= new Joystick(2);
		drive = new RobotDrive(driveTrain.tLeftFront, driveTrain.tLeftRear ,driveTrain.tRightFront, driveTrain.tRightRear);	
	}
	
	public void update(){
		drive.tankDrive(leftStick, rightStick, true);
		if(scaleMode==true){
			drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
			drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
			drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
			drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		}
		if(leftStick.getRawButton(button)){//enter scale mode, reverse the motor control
			scaleMode=!scaleMode;
		}
			
		if(leftStick.getRawButton(6)) {//shift the transmission to low gear
			train.setShiftGearLow();
		}
		else if(leftStick.getRawButton(7)) {//shift the transmmission to high gear
			train.setShiftGearHigh();
		}
		
		if(xboxStick.getPOV()==0){//roller roll inward the robot //D-pad up
			claw.setRollerBarIn();
		}
		if(xboxStick.getPOV()==180){//roller roll outward the robot //D-pad down
			claw.setRollerBarOut();
		}
		if(xboxStick.getPOV()==270){//
			
		}
		if(xboxStick.getPOV()==180){//stop roller bar rolling  //D-pad right
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
		
		if(xboxStick.getRawButton(7)){//auto scaling //back button
		
		}
		if(xboxStick.getRawButton(10)){//manual override auto scaling  //press right joystick
			//control.whatever();
			liftOverride=true;
		}
		
		if(liftOverride){
			if(xboxStick.getPOV()==0){//liftExtend  //D-pad up
				lift.setExtendLift();
			}
			else if(xboxStick.getPOV()==180){//liftRetrack  //D-pad down
				lift.setRetractLift();
			}
			else if(xboxStick.getPOV()==270){//liftStop  //D-pad left
				lift.setLiftStop();
			}
			if(xboxStick.getPOV()==180){//liftUp  //D-pad right
				lift.setTiltLiftUp();
			}
		}
		
		if(xboxStick.getRawButton(6)){//slightly tilt the roller arm up //right bumper
			claw.setRollerArmAdjustUp();
		}
		else if(xboxStick.getRawButton(5)){//slightly tilt the roller arm down //left bumper
			claw.setRollerArmAdjustDown();
		}

		
		if(leftStick.getRawButton(8)) {
			train.setCompressorOff();
		}
		else if(leftStick.getRawButton(9)) {
			train.setCompressorOn();
		}
	}
	
}
