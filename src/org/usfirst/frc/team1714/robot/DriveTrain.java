package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class DriveTrain {
    RobotDrive myRobot;
    CANTalon tRightFront,tRightBack,tLeftFront,tLeftBack;
    BuiltInAccelerometer accelerometer;
    GyroBase gyro;
    Compressor compressor;
    Solenoid solenoid;
    double angle;	//Gyro
    double X,Y,Z;	//accelerometer
    boolean compressorstatus = compressor.getPressureSwitchValue(), compressorenable = compressor.enabled();	//compressor
    float compressorcurrent = compressor.getCompressorCurrent();	//compressor
    double rightspeed,leftspeed;
    boolean transmissionswtich=true;
    
    
    DriveTrain() {
    	tRightFront = new CANTalon(0);
    	tRightBack = new CANTalon(1);
    	tLeftFront = new CANTalon(2);
    	tLeftBack = new CANTalon(3);
    	
    	gyro.reset();
    	angle = gyro.getAngle();
    	
    	compressor.setClosedLoopControl(false);
    	compressor = new Compressor(0);
    	solenoid = new Solenoid(0);
    	
    	accelerometer.initTable(null);
    	X = accelerometer.getX();
    	Y = accelerometer.getY();
    	Z = accelerometer.getZ();
    	
    }
    
    public void update(){
    	if('press transmissionswtich buttom'){
    }
    
    public void Teleoperation(){	
    	tRightFront.set(1*rightspeed);
    	tRightBack.set(1*rightspeed);
    	tLeftFront.set(1*leftspeed);
    	tLeftBack.set(1*leftspeed);
    }
    
    private boolean transmissionswtich(){
    	return !transmissionswtich;
    }
    private void TransmissionSwtich(){
    	solenoid.set(transmissionswtich);
    }
    
    private void compressor(){
    	compressor.setClosedLoopControl(false);
    }
}