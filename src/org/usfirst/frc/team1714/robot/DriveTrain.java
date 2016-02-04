package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class DriveTrain{
    public CANTalon tRightFront, tRightBack, tLeftFront, tLeftBack;
    private BuiltInAccelerometer acc;
    private AnalogGyro gyro;
    private DoubleSolenoid solenoid;
    public double angle;	//gyroscope
    public double X, Y, Z;	//accelerometer
    public double rightSpeed, leftSpeed;
    private boolean shiftingGearHigh = true, shiftingGearLow = false, resettingGyro = false;
    
    
    DriveTrain(){
    	tRightFront = new CANTalon(0);
    	tRightBack = new CANTalon(1);
    	tLeftFront = new CANTalon(2);
    	tLeftBack = new CANTalon(3);
    	gyro = new AnalogGyro(0);
    	gyro.setSensitivity(0/**volts/Degree/Second**/);
    	acc = new BuiltInAccelerometer();
    	solenoid.set(DoubleSolenoid.Value.kOff);
    	solenoid = new DoubleSolenoid(0,1);
    	
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