package org.usfirst.frc.team1251.robot;

import edu.wpi.first.wpilibj.*;

/**
 * Created by Eric on 10/2/2016.
 * Edit by Jared on 1/13/2017.
 *
 * Version: 1.0
 */
public class Robot extends IterativeRobot {

    //Define PWM ports
    public static final int PWM_PORT_0 = 0;
    public static final int PWM_PORT_1 = 1;
    public static final int PWM_PORT_2 = 2;
    public static final int PWM_PORT_3 = 3;

    //Define PCM ports
    public static final int PCM_PORT_0 = 0;
    public static final int PCM_PORT_1 = 1;
    public static final int PCM_PORT_2 = 2;
    public static final int PCM_PORT_3 = 3;
    public static final int PCM_PORT_4 = 4;
    public static final int PCM_PORT_5 = 5;

    //Define joystick port
    public static final int LOGITECH_PORT = 0;

    //Define joystick inputs
    public static final int LOGITECH_LEFT_JOYSTICK = 0;
    public static final int LOGITECH_RIGHT_JOYSTICK = 1;
    public static final int LOGITECH_Y_BUTTON = 4;
    public static final int LOGITECH_LEFT_TRIGGER = 7;
    public static final int LOGITECH_RIGHT_TRIGGER = 8;

    //Define sensor inputs
    public static final int CLAW_LIMIT_SWITCH = 0;

    //Define speed controllers
    private Talon leftTank1;
    private Talon leftTank2;
    private Talon rightTank1;
    private Talon rightTank2;

    //Define solenoids
    private DoubleSolenoid wings;
    private DoubleSolenoid claw;
    private DoubleSolenoid gearShift;

    //Define joystick
    private Joystick driveController;

    //Define drivetrain
    public RobotDrive driveTrain;

    //Define sensors
    private DigitalInput clawBackLimit;

    @Override
    public void robotInit(){
        //Declare speed controllers
        leftTank1 = new Talon(PWM_PORT_0);
        leftTank2 = new Talon(PWM_PORT_1);
        rightTank1 = new Talon(PWM_PORT_2);
        rightTank2 = new Talon(PWM_PORT_3);

        //Declare drivetrain
        driveTrain = new RobotDrive(leftTank1, leftTank2, rightTank1, rightTank2);

        //Declare solenoids
        wings = new DoubleSolenoid(PCM_PORT_0,PCM_PORT_1);
        claw = new DoubleSolenoid(PCM_PORT_2, PCM_PORT_3);
        gearShift = new DoubleSolenoid(PCM_PORT_4, PCM_PORT_5);

        //Declare joystick
        driveController = new Joystick(LOGITECH_PORT);

        //Declare sensors
        clawBackLimit = new DigitalInput(CLAW_LIMIT_SWITCH);
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

        /*  -Drivetrain-
                >Left stick: Moves left side of drivetrain forward and back
                >Right stick: Moves right side of drivetrain forward and back
         */
        driveTrain.tankDrive(driveController.getRawAxis(LOGITECH_LEFT_JOYSTICK), driveController.getRawAxis(LOGITECH_RIGHT_JOYSTICK));


        /*  -Wings-
                >Y button(hold): Opens wings and claw
                >Y button(release): Closes wings and claw
         */
        if (driveController.getRawButton(LOGITECH_Y_BUTTON)) {
            wings.set(DoubleSolenoid.Value.kForward);
            claw.set(DoubleSolenoid.Value.kForward);
        }
        else {
            wings.set(DoubleSolenoid.Value.kReverse);
            claw.set(DoubleSolenoid.Value.kReverse);
        }


        /*  -gearShifter-
                >Left Trigger: Shifts drive gear to low
                >Right Trigger Shifts drive gear to high
         */
        if (driveController.getRawButton(LOGITECH_LEFT_TRIGGER)) {
            gearShift.set(DoubleSolenoid.Value.kReverse);
        }
        if (driveController.getRawButton(LOGITECH_RIGHT_TRIGGER)) {
            gearShift.set(DoubleSolenoid.Value.kForward);
        }

    }
}
