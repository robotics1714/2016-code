package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.Solenoid;

public class DriveTrain {
    public CANTalon tRightFront,tRightBack,tLeftFront,tLeftBack;
    private BuiltInAccelerometer acc;
    private GyroBase gyro;
    private Solenoid solenoid;
    public double angle;	//Gyro
    public double X,Y,Z;	//acc
    public double rightSpeed,leftSpeed;
    private boolean gearSwitch=true;
    
    
    DriveTrain() {
    	tRightFront = new CANTalon(0);
    	tRightBack = new CANTalon(1);
    	tLeftFront = new CANTalon(2);
    	tLeftBack = new CANTalon(3);
    	gyro.reset();
    	solenoid = new Solenoid(0);
    	
    }
    
    public void update(){
    	angle = gyro.getAngle();
    	X = acc.getX();
    	Y = acc.getY();
    	Z = acc.getZ();
    	shift();
    }
    
    public void setShift(){
    	gearSwitch=!gearSwitch;
    }
    
    private void shift(){
    	solenoid.set(gearSwitch);
    }
}