package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class RollerClaw {
	private Talon rollerMotor, armMotor; 
	private DigitalInput backSafetyLS, frontSafetyLS, ballDetectLS;
	private AnalogPotentiometer rollerPot;
	private boolean rollerArmUp, rollerArmDown, rollerBarIn, rollerBarOut;
	private boolean adjustRollerUp, adjustRollerPos, adjustRollerDown;
	private final double armSpeed=0,rollerSpeed=0,rollerAdjustment=20;
	private final double rollerPotMax=0, rollerPotMin=0,potBuffer=10;//max and min of potentiometer reading and buffer
	private final double rollerPotPos1=0, rollerPotPos2=0;//potentiometer reading for a arm positions
	private double targetPos;
	public enum directions{
		UP,POSITION,DOWN
	}
	
	
	
	RollerClaw(){
		rollerMotor = new Talon(1);
		armMotor = new Talon(2);
		backSafetyLS = new DigitalInput(1);
		frontSafetyLS = new DigitalInput(2);
		ballDetectLS = new DigitalInput(3);
		rollerPot = new AnalogPotentiometer(1,100);
	}
	
	private void tiltRollerArmUp(){//called to perform the action of tilting roller arm up
		if(!rollerArmFullUp()){
			armMotor.set(armSpeed);
		}
		else{
			rollerArmUp=false;
			armMotor.set(0);
		}//when it reach the limit it stop
	}
	
	private void tiltRollerArmDown(){//called to perform the action of tilting roller arm down
		if(!rollerArmFullDown()){
			armMotor.set(-armSpeed);
		}
		else{
			armMotor.set(0);
			rollerArmDown=false;
		}//when it reach the limit it stop
	}
	
	private void rollBallIn(){//called to perform the action of rolling the ball in
		if(!ballAcquired()){
			rollerMotor.set(rollerSpeed);
		}
		else{
			rollerMotor.set(0);
			rollerBarIn=false;
		}//it stop when the ball reach its safe holding position
	}
	
	private void rollBallOut(){//called to perform the action of rolling the ball out
		rollerMotor.set(-rollerSpeed);	
	}//this action will not automatically stop till you set inward or stop the roller bar
	
	public boolean rollerArmFullUp(){
		return !backSafetyLS.get();
	}//limit switch to prevent roller arm from tilting backward too much
	
	public boolean rollerArmFullDown(){
		return !frontSafetyLS.get();
	}//limit switch to prevent roller arm from tilting forward too much
	
	public boolean ballAcquired(){
		return !ballDetectLS.get();
	}//limit switch to detect if the ball is hold safely
	
	public void Update(){
		if (rollerArmUp){
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
		/*This method is called every time autonomousPeriodic and teleopPeriodic is called
		 * to check the current status of roller claw
		 * and send command for actions
		 */
	}
	
	public void setRollerArmUp(){
		rollerArmUp=true;
		rollerArmDown=false;
	}//called to set the roller arm tilt up till it reach the limit
	
	public void setRollerArmDown(){
		rollerArmDown=true;
		rollerArmUp=false;
	}//called to set the roller arm tilt down till it reach the limit
	
	public void setRollerArmStop(){
		rollerArmUp=false;
		rollerArmDown=false;
	}//called to stop the roller arm tilting
	
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
		rollerBarIn=true;
		rollerBarOut=false;
	}//called to set the roller bar to roll in the ball
	
	public void setRollerBarOut(){
		rollerBarOut=true;
		rollerBarIn=false;
	}//called to set the roller bar to roll out the ball
	
	public void setRollerBarStop(){
		rollerBarIn=false;
		rollerBarOut=false;
	}//called to stop the roller bar rolling
	
}
