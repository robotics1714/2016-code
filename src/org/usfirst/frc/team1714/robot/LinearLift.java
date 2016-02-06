package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Servo;

public class LinearLift {
	private Servo tiltServo;
	private Talon winchMotor;
	public DigitalInput tiltLS;
	public DigitalInput winchLSMax;
	private DigitalInput winchLSMin;
	private AnalogPotentiometer winchPot;
	
	private double currentTime;
	private double lastTime;
	private boolean startedAutoScale = false;
	
// THESE ARE PLACEHOLDERS!!! CHANGE THEM!!!
	final private double winchSpeed = 0;
	final private double winchPotMax = 0;
	final private double winchPotMin = 0;
	final private int tiltServoPin = 0;
	final private int tiltLSPin = 0;
	final private int winchPotPin = 0;
	final private int winchMotorPin = 0;
	final private int winchLSMaxPin = 0;
	final private int winchLSMinPin = 0;
	final private double tiltServoPos = 0;
// END OF PLACEHOLDER VALUES!!!
	
	private boolean tiltingLiftUp = false;
	private boolean autoScaling = false;
	
	private enum LiftState {
		extending, retracting, stopped
	}
	
	private LiftState currentState = LiftState.stopped;
	
	LinearLift() {
		tiltServo = new Servo(tiltServoPin);
		tiltLS = new DigitalInput(tiltLSPin);
		winchMotor = new Talon(winchMotorPin);
		winchLSMax = new DigitalInput(winchLSMaxPin);
		winchLSMin = new DigitalInput(winchLSMinPin);
		winchPot = new AnalogPotentiometer(winchPotPin);
	}
	
	void setTiltLiftUp() {
		tiltingLiftUp = true;
	}
	
	void setExtendLift() {
		currentState = LiftState.extending;
	}
	
	void setRetractLift() {
		currentState = LiftState.retracting;
	}
	
	void setLiftStop() {
		winchMotor.set (0.0);
		currentState = LiftState.stopped;
	}
	
	void setAutoScale() {
		autoScaling = true;
	}
	
	private void tiltLiftUp() {
		if (tiltLS.get()) {
			tiltServo.set(tiltServoPos);
		} 
		else {
			tiltServo.set(0.0);
			tiltingLiftUp = false;
		}
	}
	
	private void extendLift() {
		if (winchLSMax.get() && winchPot.get() < winchPotMax) {
			winchMotor.set(winchSpeed);
		}
		else {
			winchMotor.set(0.0);
			currentState = LiftState.stopped;
		}
	}
	
	private void retractLift() {
		if (winchLSMin.get() && winchPot.get() > winchPotMin) {
			winchMotor.set(-winchSpeed);
		}
		else {
			winchMotor.set(0.0);
			currentState = LiftState.stopped;
		}
	}
	
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
		currentTime=Timer.getFPGATimestamp();
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
