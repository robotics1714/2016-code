package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Encoder;

public class LinearLift {
	private Servo tiltServo;
	private Talon winchMotor1, winchMotor2;
	// public DigitalInput tiltLS;
	// public DigitalInput winchLSMax;
	private Encoder winchEnc;
	
	/* Autolift system - really bad, don't use
	private double currentTime;
	private double lastTime;
	private boolean startedAutoScale = false;
	*/
	
// THESE ARE PLACEHOLDERS!!! CHANGE THEM!!!
	final private double winchSpeed = 0.1;
	final private double winchEncMax = 100000;
	final private double winchEncMin = -100000;
	final private int tiltServoPin = 4;
	// final private int tiltLSPin = 3;
	final private int winchEncPin1 = 6;
	final private int winchEncPin2 = 7;
	final private int winchMotor1Pin = 6;
	final private int winchMotor2Pin = 7;
	final private double tiltServoPos = 0;
// END OF PLACEHOLDER VALUES!!!
	
	private boolean tiltingLiftUp = false;
	private boolean liftTilted = false;
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
		winchEnc.reset();
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
		winchMotor1.set (0.0);
		currentState = LiftState.stopped;
	}
	
	/*
	void setAutoScale() {
		autoScaling = true;
	}
	*/
	
	private void tiltLiftUp() {
			tiltServo.set(tiltServoPos);
			tiltServo.set(0.0);
			tiltingLiftUp = false;
			liftTilted = true;
	}
	
	private void extendLift() {
		if (/*winchLSMax.get() && */winchEnc.get() < winchEncMax) {
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
		if (winchEnc.get() > winchEncMin) {
			winchMotor1.set(-winchSpeed);
			winchMotor2.set(-winchSpeed);
		}
		else {
			winchMotor1.set(0.0);
			winchMotor2.set(0.0);
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
		
		//if (autoScaling) {
			//autoScale();
		//}
	}
	
}
