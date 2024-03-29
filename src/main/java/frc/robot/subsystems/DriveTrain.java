/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import frc.robot.RobotMap;
import frc.robot.commands.DriveWithGamepadController;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Victor;

/**
 * The subsystem that moves the robot.
 */
public class DriveTrain extends Subsystem
{
	SpeedController leftMotor;
	SpeedController rightMotor;
	
	DifferentialDrive drive;
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	
	public DriveTrain()
	{

		leftMotor = new Victor(RobotMap.leftDriveMotorPwmChannel);
		rightMotor = new Victor(RobotMap.rightDriveMotorPwmChannel);
		
		// Determine if any motors need to be set inverted:
		rightMotor.setInverted(true);
		leftMotor.setInverted(true);
		
		drive = new DifferentialDrive(leftMotor, rightMotor);
	}

	public void stop()
	{
		drive.stopMotor();
	}
	
	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		setDefaultCommand(new DriveWithGamepadController());
	}
	
	/**
	 * Arcade style drive using input from a single joystick.
	 * @param stick
	 */
	public void driveArcade(Joystick stick)
	{
		// drive.arcadeDrive(stick);
	}
	
	/**
	 * Tank style drive using input from two joysticks, left and right.
	 * @param leftStick
	 * @param rightStick
	 */
	public void driveTank(Joystick leftStick, Joystick rightStick)
	{
		drive.tankDrive(leftStick.getY(), rightStick.getY());
	}
	
	public void driveTank(XboxController gameController)
	{
		drive.tankDrive(gameController.getY(Hand.kLeft), gameController.getY(Hand.kRight));
	}
	
	/**
	 * Team Caution style drive using input from two joysticks, left and right.
	 * @param leftStick
	 * @param rightStick
	 */
	public void driveCaution(Joystick leftStick, Joystick rightStick)
	{
		setLeftRightMotorOutputs(leftStick.getY() - rightStick.getX(), leftStick.getY() + rightStick.getX());
	}
	
	public void driveCaution(XboxController gameController)
	{
		double forewardComponent = gameController.getY(Hand.kLeft);

		double turnComponent = gameController.getX(Hand.kRight);

		setLeftRightMotorOutputs(forewardComponent - turnComponent, forewardComponent + turnComponent);
	}
	
	public void setLeftRightMotorOutputs(double left, double right)
	{

		
		drive.tankDrive(left, right, true);
	}
	
	/**
	 * Drives with a specified speed and curve.
	 * @param speed Between -1.0 and 1.0: >0 is forward while <0 is reverse.
	 * @param curve Between -1.0 and 1.0: >0 is turn right while <0 is turn left.
	 */
	public void drive(double speed, double curve)
	{
		drive.curvatureDrive(speed, curve, false);
	}
	
}
