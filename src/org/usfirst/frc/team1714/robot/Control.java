package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Control {
	private DriveTrain train;
	private RollerClaw claw;
	private LinearLift lift;
	private Joystick driveStickLeft;
	private Joystick driveStickRight;
	private Joystick manipController;
	private RobotDrive tankDrive;
	
	// TODO: move joystick stuff to driver station!
	
	// THESE ARE PLACEHOLDER VALUES!!! CHANGE THEM!!!
	private final int driveStickLeftPin = 0;
	private final int driveStickRightPin = 1;
	private final int manipControllerPin = 2;
	// END OF PLACEHOLDER VALUES!!!

	Control(DriveTrain driveTrain, RollerClaw rollerClaw, LinearLift linearLift) {
		train = driveTrain;
		claw = rollerClaw;
		lift = linearLift;
		
		driveStickLeft = new Joystick(driveStickLeftPin);
		driveStickRight = new Joystick(driveStickRightPin);
		manipController = new Joystick(manipControllerPin);
		
		tankDrive = new RobotDrive(train.tLeftFront, train.tLeftRear, train.tRightFront, train.tRightRear);
	}
	
	void update() {
		tankDrive.tankDrive(driveStickLeft, driveStickRight);
		if(driveStickLeft.getRawButton(7)) {
			train.setShiftGearLow();
		}
		else if(driveStickLeft.getRawButton(6)) {
			train.setShiftGearHigh();
		}
		
		if(driveStickLeft.getRawButton(8)) {
			train.setCompressorOff();
		}
		else if(driveStickLeft.getRawButton(9)) {
			train.setCompressorOn();
		}
		
	}
}
