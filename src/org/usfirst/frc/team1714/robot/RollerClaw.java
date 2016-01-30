package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class RollerClaw {
	private Talon rollerMotor, armMotor; 
	private DigitalInput backSafetyLS, frontSafetyLS, ballDetectLS;
	private AnalogPotentiometer rollerPotentiometer;
	private boolean rollerArmUp, rollerArmDown, rollerBarIn, rollerBarOut;
	private double armSpeed,rollerSpeed;
	
	RollerClaw(){
		rollerMotor = new Talon(1);
		armMotor = new Talon(2);
		backSafetyLS = new DigitalInput(1);
		frontSafetyLS = new DigitalInput(2);
		ballDetectLS = new DigitalInput(3);
		rollerPotentiometer = new AnalogPotentiometer(1);
	}
	
	private void TiltRollerArmUp(){
		if(!RollerArmFullUp()){
			armMotor.set(armSpeed);
		}
		else{
			rollerArmUp=false;
		}
	}
	
	private void TiltRollerArmDown(){
		if(!RollerArmFullDown()){
			armMotor.set(-armSpeed);
		}
		else{
			rollerArmDown=false;
		}
	}
	
	private void RollBallIn(){
		if(!BallAcquired()){
			rollerMotor.set(rollerSpeed);
		}
		else{
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
		boolean x=!ballDetectLS.get();
		return x;
	}
	
	public void Update(){
		if (rollerArmUp){
			TiltRollerArmUp();
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
	
	public void SetRollerBarIn(){
		rollerBarIn=true;
		rollerBarOut=false;
	}
	
	public void SetRollerBarOut(){
		rollerBarOut=true;
		rollerBarIn=false;
	}
	
}
