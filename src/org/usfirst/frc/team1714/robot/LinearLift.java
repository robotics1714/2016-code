package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

// TODO: replace booleans with enum state system

public class LinearLift {
	private Talon tiltMotor;
	private Talon winchMotor;
	private DigitalInput tiltLS;
	private DigitalInput winchLSMax;
	private DigitalInput winchLSMin;
	private AnalogPotentiometer tiltPot;
	private AnalogPotentiometer winchPot;
	
	// THESE ARE PLACEHOLDERS!!! CHANGE THEM!!!
	
	final private double tiltSpeed = 0;
	final private double tiltPotMax = 0;
	final private double tiltPotMin = 0;
	final private double winchSpeed = 0;
	final private double winchPotMax = 0;
	final private double winchPotMin = 0;
	final private int tiltPotPin = 0;
	final private int tiltMotorPin = 0;
	final private int tiltLSPin = 0;
	final private int winchPotPin = 0;
	final private int winchMotorPin = 0;
	final private int winchLSMaxPin = 0;
	final private int winchLSMinPin = 0;
	
	// END OF PLACEHOLDERS!!!
	
	private boolean tiltingLiftUp = false;
	private boolean tiltingLiftDown = false;
	private boolean extendingLift = false;
	private boolean retractingLift = false;
	
	LinearLift() {
		tiltMotor = new Talon(tiltMotorPin);
		tiltLS = new DigitalInput(tiltLSPin);
		tiltPot = new AnalogPotentiometer(tiltPotPin);
		winchMotor = new Talon(winchMotorPin);
		winchLSMax = new DigitalInput(winchLSMaxPin);
		winchLSMin = new DigitalInput(winchLSMinPin);
		winchPot = new AnalogPotentiometer(winchPotPin);
	}
	
	void setTiltLiftUp() {
		tiltingLiftDown = false;
		tiltingLiftUp = true;
	}

	void setTiltLiftDown() {
		tiltingLiftUp = false;
		tiltingLiftDown = true;
	}

	void setTiltLiftStop() {
		tiltingLiftUp = false;
		tiltingLiftDown = false;
		tiltMotor.set(0.0);
	}
	
	void setExtendLift() {
		retractingLift = false;
		extendingLift = true;
	}
	
	void setRetractLift() {
		extendingLift = false;
		retractingLift = true;
	}
	
	void setLiftStop(){
		extendingLift = false;
		retractingLift = true;
		winchMotor.set (0.0);
	}
	
	private void tiltLiftUp() {
		if (tiltLS.get() && tiltPot.get() < tiltPotMax) {
			tiltMotor.set(tiltSpeed);
		} 
		else {
			tiltMotor.set(0.0);
			tiltingLiftUp = false;
		}
	}

	private void tiltLiftDown() {
		if (tiltPot.get() > tiltPotMin) {
			tiltMotor.set(-tiltSpeed);
		} 
		else {
			tiltMotor.set(0.0);
			tiltingLiftDown = false;
		}
	}
	
	private void extendLift() {
		if (winchLSMax.get() && winchPot.get() < winchPotMax) {
			winchMotor.set(winchSpeed);
		}
		else {
			winchMotor.set(0.0);
			extendingLift = false;
		}
	}
	
	private void retractLift() {
		if (winchLSMin.get() && winchPot.get() > winchPotMin) {
			winchMotor.set(-winchSpeed);
		}
		else {
			winchMotor.set(0.0);
			retractingLift = false;
		}
	}
	
	void update() {
		if (tiltingLiftUp) {
			tiltLiftUp();
		}
		
		if (tiltingLiftDown) {
			tiltLiftDown();
		}
		
		if (extendingLift) {
			extendLift();
		}
		
		if (retractingLift) {
			retractLift();
		}
	}
	
}
