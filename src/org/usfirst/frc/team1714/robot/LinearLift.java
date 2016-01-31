package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class LinearLift
{
	private Talon tiltMotor;
	private Talon winchMotor;
	private DigitalInput tiltSafetyLS;
	private DigitalInput liftSafetyLS1;
	private DigitalInput liftSafetyLS2;
	private AnalogPotentiometer tiltPot;
	private AnalogPotentiometer winchPot;
	final private double tiltSpeed = 0;
	final private double winchSpeed = 0;
	final private double winchPotMax = 0;
	final private double winchPotMin = 0;
	private boolean tiltingLiftUp;
	private boolean tiltingLiftDown;
	
	void setTiltLiftUp()
	{
		if(!tiltingLiftDown)
		{
			tiltingLiftUp = true;
		}
	}
	
	void setTiltLiftDown()
	{
		if(!tiltingLiftUp)
		{
			tiltingLiftDown = true;
		}
	}
	
	private void tiltLiftUp()
	{
		if(tiltSafetyLS.get() || winchPot.get() < winchPotMax )
		{
			winchMotor.set(winchSpeed);
		}
		else
		{
			winchMotor.set(0.0);
			tiltingLiftUp = false;
		}
	}
	
	private void tiltLiftDown()
	{
		if(winchPot.get() > winchPotMin );
		{
			
		}
	}
	
	void update()
	{
		if(tiltingLiftUp)
		{
			tiltLiftUp();
		}
		if(tiltingLiftDown)
		{
			tiltLiftDown();
		}
	}
}
