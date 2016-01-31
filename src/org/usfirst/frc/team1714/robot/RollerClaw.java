package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class RollerClaw {
	private Talon rollerMotor, armMotor; 
	private DigitalInput backSafetyLS, frontSafetyLS, ballDetectLS;
	private AnalogPotentiometer rollerPot;
	private boolean rollerArmUp, rollerArmDown, rollerBarIn, rollerBarOut;
	private final double armSpeed=0,rollerSpeed=0;
	private final double rollerPotMax=0, rollerPotMin=0;//max and min of potentiometer reading
	private final double rollerPotPos1=0, rollerPotPos2=0;//range of potentiometer reading for a position
	
	
	
	RollerClaw(){
		rollerMotor = new Talon(1);
		armMotor = new Talon(2);
		backSafetyLS = new DigitalInput(1);
		frontSafetyLS = new DigitalInput(2);
		ballDetectLS = new DigitalInput(3);
		rollerPot = new AnalogPotentiometer(1);
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
	
	public void setRollerArmPos(){
		
	}//called to set the roller arm to a designated position
	
	public void setRollerArmUpward(){
		
	}//called to tilt the roller arm up for a certain and designated distance or time
	
	public void setRollerArmDownward(){
		
	}//called to tilt the roller arm down for a certain and designated distance or time
	
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
