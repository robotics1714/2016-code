package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class RollerClaw {
	private Talon rollerMotor, armMotor; 
	private DigitalInput backSafetyLS, frontSafetyLS, ballDetectLS;
	private AnalogPotentiometer rollerPot;
	private boolean rollerArmUp, rollerArmDown, rollerBarIn, rollerBarOut;
	private final double armSpeed=0,rollerSpeed=0, rollerPotMax=0, rollerPotMin=0;
	
	RollerClaw(){
		rollerMotor = new Talon(1);
		armMotor = new Talon(2);
		backSafetyLS = new DigitalInput(1);
		frontSafetyLS = new DigitalInput(2);
		ballDetectLS = new DigitalInput(3);
		rollerPot = new AnalogPotentiometer(1);
	}
	
	private void TiltRollerArmUp(){
		if(!RollerArmFullUp()){
			armMotor.set(armSpeed);
		}
		else{
			rollerArmUp=false;
			armMotor.set(0);
		}
	}
	
	private void TiltRollerArmDown(){
		if(!RollerArmFullDown()){
			armMotor.set(-armSpeed);
		}
		else{
			armMotor.set(0);
			rollerArmDown=false;
		}
	}
	
	private void RollBallIn(){
		if(!BallAcquired()){
			rollerMotor.set(rollerSpeed);
		}
		else{
			rollerMotor.set(0);
			rollerBarIn=false;
		}
	}
	
	private void RollBallOut(){
		rollerMotor.set(-rollerSpeed);
		
	}
	
	public boolean RollerArmFullUp(){
		return !backSafetyLS.get();
	}
	
	public boolean RollerArmFullDown(){
		return !frontSafetyLS.get();
	}
	
	public boolean BallAcquired(){
		return !ballDetectLS.get();
	}
	
	public void Update(){
		if (rollerArmUp){
			TiltRollerArmUp();
		}
		else if(rollerArmDown){
			TiltRollerArmDown();
		}
		
		if(rollerBarIn){
			RollBallIn();
		}
		else if(rollerBarOut){
			RollBallOut();
		}
	}
	
	public void SetRollerArmUp(){
		rollerArmUp=true;
		rollerArmDown=false;
		
	}
	
	public void SetRollerArmDown(){
		rollerArmDown=true;
		rollerArmUp=false;
	}
	
	public void SetRollerArmStop(){
		rollerArmUp=false;
		rollerArmDown=false;
	}
	
	public void SetRollerBarIn(){
		rollerBarIn=true;
		rollerBarOut=false;
	}
	
	public void SetRollerBarOut(){
		rollerBarOut=true;
		rollerBarIn=false;
	}
	
	public void SetRollerBarStop(){
		rollerBarIn=false;
		rollerBarOut=false;
	}
	
}
