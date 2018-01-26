package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.*;
/**
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
    private static final int PWM_PORT_6 = 6;


    //Define PCM ports
    private static final int PCM_PORT_0 = 0;
    private static final int PCM_PORT_1 = 1;
    private static final int PCM_PORT_2 = 2;
    private static final int PCM_PORT_3 = 3;
    private static final int PCM_PORT_4 = 4;
    private static final int PCM_PORT_5 = 5;
    private static final int PCM_PORT_6 = 6;
    private static final int PCM_PORT_7 = 7;



    //Define joystick port
    private static final int LOGITECH_DRIVER_PORT = 0;
    private static final int LOGITECH_SHOOTER_PORT = 1;

    //Define joystick inputs
    private static final int LOGITECH_LEFT_STICK = 1;
    private static final int LOGITECH_RIGHT_STICK = 3;

    //Define variables
    private boolean shooterReady = false;
    private int pauseCounter = 30;

    //Define speed controllers
    private Talon retractShooter;


    //Define joystick port

    private static final int LOGITECH_X_BUTTON = 1;
    private static final int LOGITECH_A_BUTTON = 2;
    private static final int LOGITECH_B_BUTTON = 3;
    private static final int LOGITECH_Y_BUTTON = 4;
    private static final int LOGITECH_LEFT_BUMPER = 5;
    private static final int LOGITECH_RIGHT_BUMPER = 6;


    //Define sensor inputs
    private static final int CLAW_LIMIT_SWITCH = 1;
    private static final int SHOOTER_LIMIT_SWITCH = 0;



    //Define speed controllers
    private Talon collector;
    private Talon clawPivot;



    //Define solenoids
    private DoubleSolenoid wings;
    private DoubleSolenoid claw;
    private DoubleSolenoid gearShift;
    private DoubleSolenoid shooter;

    //Define joystick
    private Joystick driveController;
    private Joystick shootController;

    //Define drivetrain
    private RobotDrive driveTrain;

    //Define sensors
    private DigitalInput clawBackLimit;
    private DigitalInput shooterLimit;

    @Override
    public void robotInit(){
        //Declare speed controllers
        collector = new Talon(PWM_PORT_5);
        clawPivot = new Talon(PWM_PORT_4);
        retractShooter = new Talon(PWM_PORT_6);

        //Declare drivetrain
        driveTrain = new RobotDrive(PWM_PORT_0, PWM_PORT_1, PWM_PORT_2, PWM_PORT_3);

        //Declare solenoids
        wings = new DoubleSolenoid(PCM_PORT_4,PCM_PORT_5);
        claw = new DoubleSolenoid(PCM_PORT_2, PCM_PORT_3);
        gearShift = new DoubleSolenoid(PCM_PORT_6, PCM_PORT_7);
        shooter = new DoubleSolenoid(PCM_PORT_0, PCM_PORT_1);

        //Declare joystick
        driveController = new Joystick(LOGITECH_DRIVER_PORT);
        shootController = new Joystick(LOGITECH_SHOOTER_PORT);


        //Declare sensors
        clawBackLimit = new DigitalInput(CLAW_LIMIT_SWITCH);
        shooterLimit = new DigitalInput(SHOOTER_LIMIT_SWITCH);
    }

    @Override
    public void autonomousInit(){
    }

    @Override
    public void autonomousPeriodic() {

    }
    @Override


    public void teleopInit() {

    }
    @Override


    public void teleopPeriodic(){

        /*  -Drivetrain-
                >Left stick: Moves left side of drivetrain forward and back
                >Right stick: Moves right side of drivetrain forward and back
         */

            driveTrain.tankDrive(-driveController.getRawAxis(LOGITECH_LEFT_STICK),
                    driveController.getRawAxis(LOGITECH_RIGHT_STICK));

        /*  -gearShifter-
                >Left Bumper: Shifts drive gear to low
                >Right Bumper Shifts drive gear to high
         */
            if (driveController.getRawButton(LOGITECH_LEFT_BUMPER)) {
                gearShift.set(DoubleSolenoid.Value.kReverse);
            }
            if (driveController.getRawButton(LOGITECH_RIGHT_BUMPER)) {
                gearShift.set(DoubleSolenoid.Value.kForward);
            }


        /*  -Collector-
        		>A button: intake
        		>Y button: Outtake
         */
            if (shootController.getRawButton(LOGITECH_A_BUTTON)) {
                collector.set(-1.0);
            } else if (shootController.getRawButton(LOGITECH_Y_BUTTON)) {
                collector.set(1.0);
            } else {
                collector.set(0);
            }

        if (driveController.getRawButton(LOGITECH_LEFT_BUMPER)) {
            gearShift.set(DoubleSolenoid.Value.kReverse);
        }
        if (driveController.getRawButton(LOGITECH_RIGHT_BUMPER)) {
            gearShift.set(DoubleSolenoid.Value.kForward);
        }


        /*  -Collector-
        		>A button: intake
        		>Y button: Outtake
         */
        if (shootController.getRawButton(LOGITECH_A_BUTTON)) {
            collector.set(-1.0);
        }
        else if (shootController.getRawButton(LOGITECH_Y_BUTTON)) {
            collector.set(1.0);
        }
        else {
            collector.set(0);
        }


        /*	-clawPivot-
          		>Left stick: moves claw up and down (can't pass limit switch)
         */
            if (!clawBackLimit.get()) {
                clawPivot.set(-shootController.getRawAxis(LOGITECH_LEFT_STICK) * 0.3);
            } else {
                clawPivot.set(0.7);
            }

        /*	-clawPivot-
          		>Left stick: moves claw up and down (can't pass limit switch)
         */
        if (!clawBackLimit.get()) {
            clawPivot.set(-shootController.getRawAxis(LOGITECH_LEFT_STICK)*0.3);
        }
        else {
            clawPivot.set(0.7);
        }

        /*
            -Wings and Claw-
        		>Right bumper(hold): Opens wings and claw
        		>Right bumper(release): Closes wings and claw
        */
            if (shootController.getRawButton(LOGITECH_RIGHT_BUMPER)) {
                claw.set(DoubleSolenoid.Value.kReverse);
                wings.set(DoubleSolenoid.Value.kReverse);
            } else {
                claw.set(DoubleSolenoid.Value.kForward);
                wings.set(DoubleSolenoid.Value.kForward);
            }

        /*	-Shooter-
         		>X button: shoots ball
         */

            if (shooterReady) {
                if (shootController.getRawButton(LOGITECH_X_BUTTON)) {
                    shooter.set(DoubleSolenoid.Value.kForward);
                }
                if (!shooterLimit.get()) {
                    pauseCounter = 0;
                    shooterReady = false;
                }
            } else {
                if (pauseCounter == 30) {
                    shooter.set(DoubleSolenoid.Value.kReverse);
                    if (!shooterLimit.get()) {
                        retractShooter.set(1.0);
                    } else {
                        retractShooter.set(0);
                        shooterReady = true;
                    }

                } else {
                    pauseCounter++;
                }
            }


        }
}
