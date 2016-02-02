package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Servo;

public class LinearLift {
	//CIM TILT SYSTEM// private Talon tiltMotor;
	private Servo tiltServo;
	private Talon winchMotor;
	private DigitalInput tiltLS;
	private DigitalInput winchLSMax;
	private DigitalInput winchLSMin;
	//CIM TILT SYSTEM// private AnalogPotentiometer tiltPot;
	private AnalogPotentiometer winchPot;
	
// THESE ARE PLACEHOLDERS!!! CHANGE THEM!!!
	
	//CIM TILT SYSTEM// final private double tiltSpeed = 0;
	//CIM TILT SYSTEM// final private double tiltPotMax = 0;
	//CIM TILT SYSTEM// final private double tiltPotMin = 0;
	final private double winchSpeed = 0;
	final private double winchPotMax = 0;
	final private double winchPotMin = 0;
	//CIM TILT SYSTEM// final private int tiltPotPin = 0;
	final private int tiltServoPin = 0;
	final private int tiltLSPin = 0;
	final private int winchPotPin = 0;
	final private int winchMotorPin = 0;
	final private int winchLSMaxPin = 0;
	final private int winchLSMinPin = 0;
	final private double tiltServoPos = 0;
	
// END OF PLACEHOLDERS!!!
	
	private boolean tiltingLiftUp = false;
	//CIM TILT SYSTEM// private boolean tiltingLiftDown = false;
	private enum LiftState {
		extending, retracting, stopped
	}
	private LiftState currentState = LiftState.stopped;
	
	LinearLift() {
		//CIM TILT SYSTEM// tiltMotor = new Talon(tiltMotorPin);
		tiltServo = new Servo(tiltServoPin);
		tiltLS = new DigitalInput(tiltLSPin);
		//CIM TILT SYSTEM// tiltPot = new AnalogPotentiometer(tiltPotPin);
		winchMotor = new Talon(winchMotorPin);
		winchLSMax = new DigitalInput(winchLSMaxPin);
		winchLSMin = new DigitalInput(winchLSMinPin);
		winchPot = new AnalogPotentiometer(winchPotPin);
	}
	
	void setTiltLiftUp() {
		//CIM TILT SYSTEM// tiltingLiftDown = false;
		tiltingLiftUp = true;
	}

	/*CIM TILT SYSTEM//
	void setTiltLiftDown() {
		tiltingLiftUp = false;
		tiltingLiftDown = true;
	}
	
	void setTiltLiftStop() {
		tiltingLiftUp = false;
		tiltingLiftDown = false;
		tiltMotor.set(0.0);
	}
	//CIM TILT SYSTEM*/
	
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
	
	private void tiltLiftUp() {
		if (tiltLS.get() /*CIM TILT SYSTEM// && tiltPot.get() < tiltPotMax */) {
			//CIM TILT SYSTEM// tiltMotor.set(tiltSpeed);
			tiltServo.set(tiltServoPos);
		} 
		else {
			//CIM TILT SYSTEM// tiltMotor.set(0.0);
			tiltingLiftUp = false;
		}
	}

	/*CIM TILT SYSTEM//
	private void tiltLiftDown() {
		if (tiltPot.get() > tiltPotMin) {
			tiltMotor.set(-tiltSpeed);
		} 
		else {
			tiltMotor.set(0.0);
			tiltingLiftDown = false;
		}
	}
	//CIM TILT SYSTEM*/
	
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
	
	void update() {
		if (tiltingLiftUp) {
			tiltLiftUp();
		}
		
		/*CIM TILT SYSTEM//
		if (tiltingLiftDown) {
			tiltLiftDown();
		}
		//CIM TILT SYSTEM*/
		
		if (currentState == LiftState.extending) {
			extendLift();
		}
		
		if (currentState == LiftState.retracting) {
			retractLift();
		}
	}
	
}
