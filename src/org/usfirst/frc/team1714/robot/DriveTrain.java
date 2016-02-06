package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class DriveTrain{
    public CANTalon tRightFront, tRightRear, tLeftFront, tLeftRear;
    private BuiltInAccelerometer acc;
    private AnalogGyro gyro;
    private DoubleSolenoid solenoid;
    public double angle;	//gyroscope
    public double X, Y, Z;	//accelerometer
    public double rightSpeed, leftSpeed;
    private boolean shiftingGearHigh = true, shiftingGearLow = false, resettingGyro = false;
    
    // THESE ARE PLACEHOLDER VALUES!!! CHANGE THEM!!!
    final private int tRightFrontPin = 0;
    final private int tRightRearPin = 0;
    final private int tLeftFrontPin = 0;
    final private int tLeftRearPin = 0;
    final private int gyroPin = 0;
    final private int solenoidPin1 = 0;
    final private int solenoidPin2 = 0;
    final private int pcmID = 0;
    // END OF PLACEHOLDER VALUES!!!
    
    DriveTrain(){
    	tRightFront = new CANTalon(tRightFrontPin);
    	tRightRear = new CANTalon(tRightRearPin);
    	tLeftFront = new CANTalon(tLeftFrontPin);
    	tLeftRear = new CANTalon(tLeftRearPin);
    	gyro = new AnalogGyro(gyroPin);
    	gyro.setSensitivity(0/**volts/Degree/Second**/);
    	acc = new BuiltInAccelerometer();
    	solenoid = new DoubleSolenoid(pcmID, solenoidPin1, solenoidPin2);
    	solenoid.set(DoubleSolenoid.Value.kForward);
    	
    }

    public void update(){
    	angle = gyro.getAngle();
    	X = acc.getX();
    	Y = acc.getY();
    	Z = acc.getZ();
    	
    	if (resettingGyro) {
    		resetGyro();
    	}
    	
    	if (shiftingGearHigh) {
    		shiftGearHigh();
    	}
    	
    	if (shiftingGearLow) {
    		shiftGearLow();
    	}
    }
    
    /**transmission**/
    
    private void shiftGearHigh() {
    	solenoid.set(DoubleSolenoid.Value.kForward);
    	shiftingGearHigh = false;
    }
    
    private void shiftGearLow() {
		solenoid.set(DoubleSolenoid.Value.kReverse);
		shiftingGearLow = false;
    }

    public void setShiftGearHigh() {
    	shiftingGearHigh = true;
    }
    
    public void setShiftGearLow() {
    	shiftingGearLow = true;
    }
    
    /**gyroscope**/
    public void setResetGyro() {
    	resettingGyro = true;
    }
    
    private void resetGyro() {
    	gyro.reset();
		resettingGyro = false;
    }
    
    /**accelerometer**/
    
    
}