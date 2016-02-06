package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class RollerClaw {
	private Talon rollerMotor, armMotor; 
	private DigitalInput rearLS, frontLS, ballDetectLS;
	private AnalogPotentiometer rollerPot;
	private double 
		armSpeed=0, 
		rollerSpeed=0, 
		rollerAdjustment=20,
		potBuffer=5,//max and min of potentiometer reading and buffer
		rollerPotPos1 = 0,//potentiometer reading for a arm positions
		targetPos;
	
	// THESE ARE PLACEHOLDER VALUES!!! CHANGE THEM!!!
	private final int 
		rollerMotorPin = 0,
		armMotorPin = 0,
		rearLSPin = 0,
		frontLSPin = 0,
		ballDetectLSPin = 0,
		rollerPotPin = 0;
	// END OF PLACEHOLDER VALUES!!!
	
	public enum armDirection{
		UP,POSITION,DOWN,ADJUSTUP,ADJUSTDOWN,STOP
	}
	
	public enum rollDirection{
		IN, OUT, STOP
	}
	
	private armDirection ArmDirection;
	private rollDirection RollDirection;
	
	RollerClaw(){
		rollerMotor = new Talon(rollerMotorPin);
		armMotor = new Talon(armMotorPin);
		rearLS = new DigitalInput(rearLSPin);
		frontLS = new DigitalInput(frontLSPin);
		ballDetectLS = new DigitalInput(ballDetectLSPin);
		rollerPot = new AnalogPotentiometer(rollerPotPin, 100);
	}
	
	private void tiltRollerArmUp(){//called to perform the action of tilting roller arm up
		if(!rollerArmFullUp()){
			armMotor.set(armSpeed);
		}
		else{
			ArmDirection=armDirection.STOP;
			armMotor.set(0);
		}//when it reach the limit it stop
	}
	
	private void tiltRollerArmDown(){//called to perform the action of tilting roller arm down
		if(!rollerArmFullDown()){
			armMotor.set(-armSpeed);
		}
		else{
			armMotor.set(0);
			ArmDirection=armDirection.STOP;
		}//when it reach the limit it stop
	}
	
	private void tiltRollerArmStop(){
		armMotor.set(0);
	}//force stop the moving of the roller arm
	
	private void rollBallIn(){//called to perform the action of rolling the ball in
		if(!ballAcquired()){
			rollerMotor.set(rollerSpeed);
		}
		else{
			rollerMotor.set(0);
			RollDirection=rollDirection.STOP;
		}//it stop when the ball reach its safe holding position
	}
	
	private void rollBallOut(){//called to perform the action of rolling the ball out
		rollerMotor.set(-rollerSpeed);	
	}//this action will not automatically stop till you set inward or stop the roller bar
	
	private void rollBallStop(){
		rollerMotor.set(0);
	}
	
	public boolean rollerArmFullUp(){
		return !rearLS.get();
	}//limit switch to prevent roller arm from tilting backward too much
	
	public boolean rollerArmFullDown(){
		return !frontLS.get();
	}//limit switch to prevent roller arm from tilting forward too much
	
	public boolean ballAcquired(){
		return !ballDetectLS.get();
	}//limit switch to detect if the ball is hold safely
	
	/*public void adjustRollerArm(armDirection ArmDirection){
	    switch(ArmDirection){
	        case UP:
	            targetPos=rollerPot.get() + rollerAdjustment;
	            adjustRollerUp=true;
	            adjustRollerPos=false;
	            adjustRollerDown=false;
	            
	            break;
	        
	        case POSITION:
	            targetPos=rollerPotPos1;
	            adjustRollerPos=true;
	            adjustRollerUp=false;
	            adjustRollerDown=false;
	            
	            break;
	        
	        case DOWN:
	            targetPos=rollerPot.get() - rollerAdjustment;
	            adjustRollerDown=true;
	            adjustRollerUp=false;
	            adjustRollerPos=false;
	            
	            break;
	        
	        default:
	            adjustRollerDown=false;
	            adjustRollerUp=false;
	            adjustRollerPos=false;
	            
	            break;
	    }
	}*/

	
	public void update(){
		switch(ArmDirection){
			case UP:
			tiltRollerArmUp();	
			break;
		
			case DOWN:
			tiltRollerArmDown();
			break;
			
			case STOP:
			tiltRollerArmStop();
			break;
		
			case ADJUSTUP:
			if(rollerPot.get() < targetPos-potBuffer){
				tiltRollerArmUp();
			}
			break;
			
			case ADJUSTDOWN:
			if(rollerPot.get() > targetPos+potBuffer){
				tiltRollerArmDown();
			}
			break;
			
			case POSITION:
			if(rollerPot.get() > targetPos+potBuffer){
				tiltRollerArmDown();
			}
			else if(rollerPot.get() < targetPos-potBuffer){
				tiltRollerArmUp();
			}
			else if(rollerPot.get() < targetPos+potBuffer && rollerPot.get() > targetPos-potBuffer){
				tiltRollerArmStop();
			}
			break;
		}
		switch(RollDirection){
			case IN:
			rollBallIn();
			break;
			
			case OUT:
			rollBallOut();
			break;
			
			case STOP:
			rollBallStop();
			break;
		}
	}
	//This method is called every time autonomousPeriodic and teleopPeriodic is called
	//to check the current status of roller claw and send command for actions
		/*if (rollerArmUp){
			tiltRollerArmUp();
		}
		else if(rollerArmDown){
			tiltRollerArmDown();
		}
		
		if(rollerBarIn){
			rollBallIn();
		}
		else if(rollerBarOut){
			rollBallOut();
		}
		
		if(adjustRollerUp){
	        if(rollerPot.get() < targetPos-potBuffer){
				setRollerArmUp();
			}
	    }
		else if(adjustRollerDown){
			if(rollerPot.get() > targetPos+potBuffer){
				setRollerArmDown();
			}
		}
	    else if(adjustRollerPos){
	        if(rollerPot.get() > targetPos+potBuffer){
				setRollerArmDown();
			}
			else if(rollerPot.get() < targetPos-potBuffer){
				setRollerArmUp();
			}
			else if(rollerPot.get() < targetPos+potBuffer && rollerPot.get() > targetPos-potBuffer){
				setRollerArmStop();
			}
	    }
		*/
	
	
	public void setRollerArmUp(){
		ArmDirection=armDirection.UP;
	}//called to set the roller arm tilt up till it reach the limit
	
	public void setRollerArmDown(){
		ArmDirection=armDirection.DOWN;
	}//called to set the roller arm tilt down till it reach the limit
	
	public void setRollerArmStop(){
		ArmDirection=armDirection.STOP;
	}//called to stop the roller arm tilting
	
	public void setRollerArmAdjustUp(){
		
		targetPos=rollerPot.get()+rollerAdjustment;
		ArmDirection=armDirection.ADJUSTUP;
	}
	
	public void setRollerArmAdjustDown(){
		targetPos=rollerPot.get()-rollerAdjustment;
		ArmDirection=armDirection.ADJUSTDOWN;
	}
	
	public void setRollerArmPos(){
		targetPos=rollerPotPos1;
		ArmDirection=armDirection.POSITION;
	}
	/*public void setRollerArmPos(int i){
		if(i ==1){
			targetPos=rollerPotPos1;
			if(rollerPot.get() > targetPos+potBuffer){
				setRollerArmDown();
			}
			else if(rollerPot.get() < targetPos-potBuffer){
				setRollerArmUp();
			}
			else if(rollerPot.get() < targetPos+potBuffer && rollerPot.get() > targetPos-potBuffer){
				setRollerArmStop();
			}
		}
		else if(i==2){
			targetPos=rollerPotPos2;
			if(rollerPot.get() > targetPos+potBuffer){
				setRollerArmDown();
			}
			else if(rollerPot.get() < targetPos-potBuffer){
				setRollerArmUp();
			}
			else if(rollerPot.get() < targetPos+potBuffer && rollerPot.get() > targetPos-potBuffer){
				setRollerArmStop();
			}
		}
	}//called to set the roller arm to multiple designated positions
	*/
	
	/*public void setAdjustArmUp(){
		targetPos=rollerPot.get()+rollerAdjustment;
		if(rollerPot.get() < targetPos){
			setRollerArmUp();
		}
	}//called to tilt the roller arm up for a certain and designated distance or time
	
	public void setAdjustArmDown(){
		targetPos=rollerPot.get()-rollerAdjustment;
		if(rollerPot.get() > targetPos){
			setRollerArmDown();
		}
		
	}//called to tilt the roller arm down for a certain and designated distance or time
	*/
	public void setRollerBarIn(){
		RollDirection=rollDirection.IN;
	}//called to set the roller bar to roll in the ball
	
	public void setRollerBarOut(){
		RollDirection=rollDirection.OUT;
	}//called to set the roller bar to roll out the ball
	
	public void setRollerBarStop(){
		RollDirection=rollDirection.STOP;
	}//called to stop the roller bar rolling
	
}
