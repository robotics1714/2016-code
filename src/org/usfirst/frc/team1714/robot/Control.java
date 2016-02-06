package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Control {
	DriveTrain train;
	RollerClaw claw;
	LinearLift lift;
	Joystick driveStickLeft;
	Joystick driveStickRight;
	Joystick manipController;
	RobotDrive tankDrive;
	
	// TODO: move joystick stuff to driver station!
	
	// THESE ARE PLACEHOLDER VALUES!!! CHANGE THEM!!!
	int driveStickLeftPin = 0;
	int driveStickRightPin = 1;
	int manipControllerPin = 2;
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
		
	}
}
