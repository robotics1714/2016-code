package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class LinearLift {
	private Talon tiltMotor;
	private Talon winchMotor;
	private DigitalInput tiltSafetyLS;
	private DigitalInput liftSafetyLSMax;
	private DigitalInput liftSafetyLSMin;
	private AnalogPotentiometer tiltPot;
	private AnalogPotentiometer winchPot;
	final private double tiltSpeed = 0;
	final private double tiltPotMax = 0;
	final private double tiltPotMin = 0;
	final private double winchSpeed = 0;
	final private double winchPotMax = 0;
	final private double winchPotMin = 0;
	private boolean tiltingLiftUp = false;
	private boolean tiltingLiftDown = false;
	private boolean extendingLift = false;
	private boolean retractingLift = false;

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
		if (tiltSafetyLS.get() && tiltPot.get() < tiltPotMax) {
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
		if (liftSafetyLSMax.get() && winchPot.get() < winchPotMax) {
			winchMotor.set(winchSpeed);
		}
		else {
			winchMotor.set(0.0);
			extendingLift = false;
		}
	}
	
	private void retractLift() {
		if (liftSafetyLSMin.get() && winchPot.get() > winchPotMin) {
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
