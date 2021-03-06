package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.Solenoid;

public class LinearLift {
	private Servo tiltServo;
	private Talon winchMotor1, winchMotor2;
	private DoubleSolenoid lockSlnd;
	// public DigitalInput tiltLS;
	// public DigitalInput winchLSMax;
	private Encoder winchEnc;
	private edu.wpi.first.wpilibj.DriverStation dStation;
	
	/* Autolift system - really bad, don't use
	private double currentTime;
	private double lastTime;
	private boolean startedAutoScale = false;
	*/
	
// THESE ARE PLACEHOLDERS!!! CHANGE THEM!!!
	final private double winchSpeed = 0.7;
	final private double winchEncMax = 2939;
	final private double winchEncPreMax = 940;
	final private double winchEncMin = 50;
	final private int tiltServoPin = 4;
	// final private int tiltLSPin = 3;
	final private int winchEncPin1 = 6;
	final private int winchEncPin2 = 7;
	final private int lockSlndPin1 = 2;
	final private int lockSlndPin2 = 3;
	final private int winchMotor1Pin = 6;
	final private int winchMotor2Pin = 7;
	final private double tiltServoPos = 1;
	final private int lockSlndpcmID = 1;
// END OF PLACEHOLDER VALUES!!!
	
	private boolean tiltingLiftUp = false;
	private boolean liftTilted = false;
	private boolean liftMaxed = false;
	
	// private boolean autoScaling = false;
	
	private enum LiftState {
		extending, retracting, stopped
	}
	
	private LiftState currentState = LiftState.stopped;
	
	LinearLift() {
		tiltServo = new Servo(tiltServoPin);
		// tiltLS = new DigitalInput(tiltLSPin);
		winchMotor1 = new Talon(winchMotor1Pin);
		winchMotor2 = new Talon(winchMotor2Pin);
		winchEnc = new Encoder(winchEncPin1, winchEncPin2);
		lockSlnd = new DoubleSolenoid(lockSlndpcmID,lockSlndPin1,lockSlndPin2);
		lockSlnd.set(DoubleSolenoid.Value.kForward);
		SmartDashboard.putBoolean("Starting LL Lock", (lockSlnd.get() == DoubleSolenoid.Value.kForward));
		winchEnc.reset();
		tiltServo.set(0.2);
		dStation = edu.wpi.first.wpilibj.DriverStation.getInstance();
	}
	
	void setTiltLiftUp() {
		tiltingLiftUp = true;
	}
	
	void setExtendLift() {
		if(liftTilted) {
			currentState = LiftState.extending;
		}
	}
	
	void setRetractLift() {
		if(liftTilted) {
			currentState = LiftState.retracting;
		}
	}
	
	/* DEPRECATED
	void setResetLift() {
		// EVEN IF THE LIFT IS TILTED PHYSICALLY, YOU MUST "TILT" THE LIFT IN SOFTWARE BEFORE RESETTING!!!!!!!!!!
		if(liftTilted) {
			resetLift();
		}
	}
	*/
	
	void setLiftStop() {
		winchMotor1.set(0.0);
		winchMotor2.set(0.0);
		currentState = LiftState.stopped;
	}
	
	void lockLift() {
		lockSlnd.set(DoubleSolenoid.Value.kForward);
	}
	
	void unlockLift() {
		lockSlnd.set(DoubleSolenoid.Value.kReverse);
	}
	
	/*
	void setAutoScale() {
		autoScaling = true;
	}
	*/
	
	private void tiltLiftUp() {
			tiltServo.set(tiltServoPos);
			//tiltServo.set(0.0);
			tiltingLiftUp = false;
			liftTilted = true;
	}
	
	void resetLift() {
		tiltServo.set(0.2);
	}
	
	private void extendLift() {
		if (winchEnc.get() < winchEncMax && lockSlnd.get() == DoubleSolenoid.Value.kForward  /*&& dStation.getMatchTime() <= 20 */) {
			winchMotor1.set(winchSpeed);
			winchMotor2.set(winchSpeed);
		}
		else if (winchEnc.get() < winchEncPreMax && lockSlnd.get() == DoubleSolenoid.Value.kForward) {
			winchMotor1.set(winchSpeed);
			winchMotor2.set(winchSpeed);
		}
		else {
			winchMotor1.set(0.0);
			winchMotor2.set(0.0);
			currentState = LiftState.stopped;
		}
	}
	
	private void retractLift() {
		if (winchEnc.get() > winchEncMin && lockSlnd.get()==DoubleSolenoid.Value.kForward) {
			winchMotor1.set(-winchSpeed);
			winchMotor2.set(-winchSpeed);
		}
		else {
			winchMotor1.set(0.0);
			winchMotor2.set(0.0);
			if(liftMaxed) {
				lockSlnd.set(DoubleSolenoid.Value.kReverse);
				System.out.println("I tried to lock the lift");
			}
			// SmartDashboard.putBoolean("ending LL Lock", (lockSlnd.get() == DoubleSolenoid.Value.kForward));
			currentState = LiftState.stopped;
		}
	}

	/* DEPRECATED
	private void resetLift() {
		if (winchLSMin.get()) {
			winchMotor1.set(-winchSpeed);
			winchMotor2.set(-winchSpeed);
		}
		else {
			winchMotor1.set(0.0);
			winchMotor2.set(0.0);
			currentState = LiftState.stopped;
		}
	}
	*/
	
	/*public void autoScale() {
		
		if(!startedAutoScale) {
			lastTime=Timer.getFPGATimestamp();
			startedAutoScale = true;
		}
		if(tiltLS.get()) {
			setTiltLiftUp();
		}
		if(!tiltLS.get() && winchLSMax.get()) {
			setExtendLift();
		}
		if(!tiltLS.get() && !winchLSMax.get()) {
			currentTime = Timer.getFPGATimestamp();
			if((currentTime - lastTime) > 2) {
				setRetractLift();
				lastTime = currentTime;
				
			}
			
		}
	}*/
	//code for experiemental auto scale
	
	void update() {
		SmartDashboard.putNumber("Winch Enc", winchEnc.get());
		SmartDashboard.putString("Lift Status", currentState.toString());
		SmartDashboard.putNumber("Servo", tiltServo.get());
		SmartDashboard.putNumber("Remaining Match Time", dStation.getMatchTime());
		// SmartDashboard.putBoolean("Tilt LS", !tiltLS.get());
		// SmartDashboard.putBoolean("Winch Max LS", !winchLSMax.get());
		
		// currentTime=Timer.getFPGATimestamp();
		if (tiltingLiftUp) {
			tiltLiftUp();
		}
		
		if (currentState == LiftState.extending) {
			extendLift();
		}
		
		if (currentState == LiftState.retracting) {
			retractLift();
		}
		
		if(winchEnc.get() >= 2600) {
			liftMaxed = true;
		}
		//if (autoScaling) {
			//autoScale();
		//}
	}
	
}
