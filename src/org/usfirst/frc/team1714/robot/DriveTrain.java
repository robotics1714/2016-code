package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class DriveTrain{
    public CANTalon tRightFront,tRightBack,tLeftFront,tLeftBack;
    private BuiltInAccelerometer acc;
    private AnalogGyro gyro;
    private DoubleSolenoid Solenoid;
    public double angle;	//gyroscope
    public double X,Y,Z;	//accelerometer
    public double rightSpeed,leftSpeed;
    private boolean gearSwitch=true,gyroreset=false;
    
    
    DriveTrain(){
    	tRightFront = new CANTalon(0);
    	tRightBack = new CANTalon(1);
    	tLeftFront = new CANTalon(2);
    	tLeftBack = new CANTalon(3);
    	gyro = new AnalogGyro(0);
    	gyro.setSensitivity(0/**volts/Degree/Second**/);
    	acc = new BuiltInAccelerometer();
    	Solenoid.set(DoubleSolenoid.Value.kOff);
    	Solenoid = new DoubleSolenoid(0,1);
    	
    }

    public void update(){
    	angle = gyro.getAngle();
    	X = acc.getX();
    	Y = acc.getY();
    	Z = acc.getZ();
    	shift();
    	Gyroreset();
    }
    
    /**transmission**/
    public void setShift(){
    	gearSwitch=!gearSwitch;
    }
    private void shift(){
    	if(gearSwitch=true){
    		Solenoid.set(DoubleSolenoid.Value.kForward);
    	}
    	else if(gearSwitch=false){
    		Solenoid.set(DoubleSolenoid.Value.kReverse);
    	}
    	else if(X==1/** discuss if need set solenoid off **/){
    		Solenoid.set(DoubleSolenoid.Value.kOff);
    	}
    }
    
    /**gyroscope**/
    public void resetGyro(){
    	gyroreset = true;
    }
    private void Gyroreset(){
    	if(gyroreset=true);
    	gyro.reset();
    	gyroreset=!gyroreset;
    }
    
    /**accelerometer**/
    
    
}