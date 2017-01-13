package org.usfirst.frc.team1251.robot;

import edu.wpi.first.wpilibj.*;

/**
 * Created by Eric on 10/2/2016.
 */
public class Robot extends IterativeRobot {


    public static final int LEFT_UP = 0;
    public static final int LEFT_BACK = 1;
    public static final int RIGHT_UP = 2;
    public static final int RIGHT_BACK = 3;

    public static final int XBOX_PORT = 0;

    public static final int XBOX_LEFT_JOYSTICK = 0;
    public static final int XBOX_RIGHT_JOYSTICK = 1;

    public static final int CLAW_BACK_MAX_PORT = 1;

    private Victor leftUpMotor;
    private Victor leftBackMotor;
    private Victor rightUpMotor;
    private Victor rightBackMotor;

    private Joystick driveController;

    public RobotDrive driveTrain;

    private DigitalInput clawBackLimit;
    @Override
    public void robotInit(){
        leftUpMotor = new Victor(LEFT_UP);
        leftBackMotor = new Victor(LEFT_BACK);
        rightUpMotor = new Victor(RIGHT_UP);
        rightBackMotor = new Victor(RIGHT_BACK);

        driveTrain = new RobotDrive(leftUpMotor, leftBackMotor, rightUpMotor, rightBackMotor);

        driveController = new Joystick(XBOX_PORT);

        clawBackLimit = new DigitalInput(CLAW_BACK_MAX_PORT);
}

    @Override
    public void autonomousInit(){

    }

    @Override
    public void autonomousPeriodic(){

    }

    @Override
    public void teleopInit(){

    }

    @Override
    public void teleopPeriodic(){
        driveTrain.tankDrive(driveController.getRawAxis(XBOX_LEFT_JOYSTICK), driveController.getRawAxis(XBOX_RIGHT_JOYSTICK));
        // if limit switch is pressed
        if (!clawBackLimit.get()){

        }
    }
}
