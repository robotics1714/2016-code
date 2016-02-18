package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;

public class DriveTrain{
    public CANTalon tRightFront, tRightRear, tLeftFront, tLeftRear;
    private BuiltInAccelerometer acc;
    private AnalogGyro gyro;
    private DoubleSolenoid solenoid;
    public double angle;	//gyroscope
    public double X, Y, Z;	//accelerometer
    public double rightSpeed, leftSpeed;
    private boolean shiftingGearHigh = true, shiftingGearLow = false, resettingGyro = false, turningCompressorOn = false, turningCompressorOff = true;
    private Compressor comp;
    
    // THESE ARE PLACEHOLDER VALUES!!! CHANGE THEM!!!
    final private int tRightFrontPin = 4;
    final private int tRightRearPin = 5;
    final private int tLeftFrontPin = 2;
    final private int tLeftRearPin = 3;
    final private int gyroPin = 0;
    final private int solenoidPin1 = 0;
    final private int solenoidPin2 = 1;
    final private int pcmID = 1;
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
    	comp = new Compressor(pcmID);
    	comp.setClosedLoopControl(true);
    }

    public void update(){
		SmartDashboard.putBoolean("Gear High?", (solenoid.get() == DoubleSolenoid.Value.kForward));
    	
    	angle = gyro.getAngle();
    	X = acc.getX();
    	Y = acc.getY();
    	Z = acc.getZ();
    	
    	if (resettingGyro) {
    		resetGyro();
    	}
    	
    	if (shiftingGearLow) {
    		shiftGearLow();
    	}
    	else if (shiftingGearHigh) {
    		shiftGearHigh();
    	}
    	
    	if(turningCompressorOff) {
    		turnCompressorOff();
    	}
    	else if(turningCompressorOn) {
    		turnCompressorOn();
    	}
    }
    
    void setLeftSide(double speed) {
    	tLeftFront.set(speed);
    	tLeftRear.set(speed);
    }
    
    void setRightSide(double speed) {
    	tRightFront.set(speed);
    	tRightRear.set(speed);
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
    
    public void setCompressorOn() {
    	turningCompressorOff = false;
    	turningCompressorOn = true;
    }
    
    public void setCompressorOff() {
    	turningCompressorOff = true;
    	turningCompressorOn = false;
    }
    
    private void turnCompressorOn() {
    	comp.start();
    	turningCompressorOn = false;
    }
    
    private void turnCompressorOff() {
    	comp.stop();
    	turningCompressorOff = false;
    }
    /**accelerometer**/
    
    
}