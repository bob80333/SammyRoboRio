package org.usfirst.frc.team1251.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

/**
 * Created by Eric on 10/2/2016.
 *
 * Edit by Jared on 1/14/2017.
 *
 * Version: 1.0
 */
public class Robot extends IterativeRobot {

    //Define PWM ports
    private static final int PWM_PORT_0 = 0;
    private static final int PWM_PORT_1 = 1;
    private static final int PWM_PORT_2 = 2;
    private static final int PWM_PORT_3 = 3;
    private static final int PWM_PORT_4 = 4;
    private static final int PWM_PORT_5 = 5;

    //Define PCM ports
    private static final int PCM_PORT_0 = 0;
    private static final int PCM_PORT_1 = 1;
    private static final int PCM_PORT_2 = 2;
    private static final int PCM_PORT_3 = 3;
    private static final int PCM_PORT_4 = 4;
    private static final int PCM_PORT_5 = 5;

    //Define joystick port
    private static final int LOGITECH_PORT = 0;

    //Define joystick inputs
    private static final int LOGITECH_LEFT_JOYSTICK = 0;
    private static final int LOGITECH_RIGHT_JOYSTICK = 1;
    private static final int LOGITECH_A_BUTTON = 2;
    private static final int LOGITECH_B_BUTTON = 3;
    private static final int LOGITECH_Y_BUTTON = 4;
    private static final int LOGITECH_LEFT_TRIGGER = 7;
    private static final int LOGITECH_RIGHT_TRIGGER = 8;
    private static final int LOGITECH_START_BUTTON = 10;

    //Define sensor inputs
    private static final int CLAW_LIMIT_SWITCH = 0;
    private static final int SHOOTER_LIMIT_SWITCH = 1;
    private static final int ENCODER_SOURCE_A = 2;
    private static final int ENCODER_SOURCE_B = 3;

    //Static counter values
    private static final int CLAW_CALIBRATION_DISTANCE = 60;

    //Defaults to false
    //Non-static counter values
    private boolean isCalibrated;
    private boolean clawLimit;

    //Define speed controllers
    private Talon leftTank1;
    private Talon leftTank2;
    private Talon rightTank1;
    private Talon rightTank2;
    private Talon collector;
    private Talon clawPivot;

    //Define solenoids
    private DoubleSolenoid wings;
    private DoubleSolenoid claw;
    private DoubleSolenoid gearShift;

    //Define joystick
    private Joystick driveController;

    //Define drivetrain
    private RobotDrive driveTrain;

    //Define sensors
    private DigitalInput clawBackLimit;
    private DigitalInput shooterLimit;
    private Encoder clawPosition;

    @Override
    public void robotInit(){
        //Declare speed controllers
        leftTank1 = new Talon(PWM_PORT_0);
        leftTank2 = new Talon(PWM_PORT_1);
        rightTank1 = new Talon(PWM_PORT_2);
        rightTank2 = new Talon(PWM_PORT_3);
        collector = new Talon(PWM_PORT_4);
        clawPivot = new Talon(PWM_PORT_5);

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
        shooterLimit = new DigitalInput(SHOOTER_LIMIT_SWITCH);
        clawPosition = new Encoder(ENCODER_SOURCE_A, ENCODER_SOURCE_B);
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
        if (isCalibrated) {

            /*  -Drivetrain-
                    >Left stick: Moves left side of drivetrain forward and back
                    >Right stick: Moves right side of drivetrain forward and back
             */
            driveTrain.tankDrive(driveController.getRawAxis(LOGITECH_LEFT_JOYSTICK), driveController.getRawAxis(LOGITECH_RIGHT_JOYSTICK));


            /*  -Collector-
                    >A button: Move claw down and intake
                    >B button: Outtake
             */
            if (driveController.getRawButton(LOGITECH_A_BUTTON)) {
                //fill later
            } else if (driveController.getRawButton(LOGITECH_B_BUTTON)) {
                collector.set(-1.0);
            } else {
                collector.set(0);
            }


            /*  -Wings-
                    >Y button(hold): Opens wings and claw
                    >Y button(release): Closes wings and claw
             */
            if (driveController.getRawButton(LOGITECH_Y_BUTTON)) {
                wings.set(DoubleSolenoid.Value.kForward);
                claw.set(DoubleSolenoid.Value.kReverse);
            } else {
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


            //re-calibrate robot (only for testing)
            if (driveController.getRawButton(LOGITECH_START_BUTTON)) {
                isCalibrated = false;
            }

        }
        else {
            //Move claw back until sensor is pressed
            if (!clawLimit) {
                if (clawBackLimit.get()) {
                    clawPivot.set(0);
                    clawPosition.reset();
                    clawLimit = true;
                }
                else {
                    clawPivot.set(-0.3);
                }
            }
            else {
                //move claw a set distance and zero the encoder
                if(clawPosition.get() <= CLAW_CALIBRATION_DISTANCE) {
                    clawPivot.set(0.7);
                }
                else {
                    clawPivot.set(0);
                    clawPosition.reset();
                    isCalibrated = true;
                }
            }

        }
    }
}
