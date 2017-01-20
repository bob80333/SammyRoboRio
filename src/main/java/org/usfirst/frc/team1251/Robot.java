package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.*;


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
    private static final int LOGITECH_X_BUTTON = 1;
    private static final int LOGITECH_A_BUTTON = 2;
    private static final int LOGITECH_B_BUTTON = 3;
    private static final int LOGITECH_Y_BUTTON = 4;
    private static final int LOGITECH_LEFT_BUMPER = 5;
    private static final int LOGITECH_RIGHT_BUMPER = 6;
    private static final int LOGITECH_LEFT_TRIGGER = 7;
    private static final int LOGITECH_RIGHT_TRIGGER = 8;

    //Define sensor inputs
    private static final int CLAW_LIMIT_SWITCH = 1;
    private static final int SHOOTER_LIMIT_SWITCH = 0;

    //Define variables
    private boolean shooterReady = false;
    private int pauseCounter = 30;

    //Define speed controllers
    private Talon collector;
    private Talon clawPivot;
    private Talon retractShooter;


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
    private DoubleSolenoid shooter;

    //Define joystick
    private Joystick driveController;
    private Joystick shootController;


    //Define joystick
    private Joystick driveController;


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

    private Encoder clawPosition;

    @Override
    public void robotInit() {
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
        wings = new DoubleSolenoid(PCM_PORT_0, PCM_PORT_1);
        claw = new DoubleSolenoid(PCM_PORT_2, PCM_PORT_3);
        gearShift = new DoubleSolenoid(PCM_PORT_4, PCM_PORT_5);

        //Declare joystick
        driveController = new Joystick(LOGITECH_PORT);


        //Declare sensors
        clawBackLimit = new DigitalInput(CLAW_LIMIT_SWITCH);
        shooterLimit = new DigitalInput(SHOOTER_LIMIT_SWITCH);

    }

    @Override
    public void autonomousInit(){

        clawPosition = new Encoder(ENCODER_SOURCE_A, ENCODER_SOURCE_B);
    }

    @Override
    public void autonomousInit(){
    }

    @Override

    public void autonomousPeriodic(){

    public void autonomousPeriodic() {


    }

    @Override

    public void teleopInit(){

    public void teleopInit() {


    }

    @Override

    public void teleopPeriodic(){
        /*  -Drivetrain-
                >Left stick: Moves left side of drivetrain forward and back
                >Right stick: Moves right side of drivetrain forward and back
         */
        driveTrain.tankDrive(-driveController.getRawAxis(LOGITECH_LEFT_STICK),
                -driveController.getRawAxis(LOGITECH_RIGHT_STICK));


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
            wings.set(DoubleSolenoid.Value.kForward);
        }
        else {
            claw.set(DoubleSolenoid.Value.kForward);
            wings.set(DoubleSolenoid.Value.kReverse);
        }


        /*	-Shooter-
         		>X button: shoots ball
         */
        if (shooterReady) {
            if (shootController.getRawButton(LOGITECH_X_BUTTON)) {
                shooter.set(DoubleSolenoid.Value.kForward);
            }
            if (!shooterLimit.get()){
                pauseCounter = 0;
                shooterReady = false;
            }
        }
        else {
            if (pauseCounter == 30) {
                shooter.set(DoubleSolenoid.Value.kReverse);
                if (!shooterLimit.get()) {
                    retractShooter.set(1.0);
                }
                else {
                    retractShooter.set(0);
                    shooterReady = true;
                }

            }
            else {
                pauseCounter++;
            }
        }

    public void teleopPeriodic() {
        if (isCalibrated) {

            /*  -Drivetrain-
                    >Left stick: Moves left side of drivetrain forward and back
                    >Right stick: Moves right side of drivetrain forward and back
             */
            driveTrain.tankDrive(getLeftJoystick(), getRightJoystick());


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

        } else {
            isCalibrated = calibrateClaw();
        }
    }

    private boolean calibrateClaw() {
        //Move claw back until sensor is pressed
        if (!clawLimit) {
            if (clawBackLimit.get()) {
                clawPivot.set(0);
                clawPosition.reset();
                clawLimit = true;
            } else {
                clawPivot.set(-0.3);
            }
        } else {
            //move claw a set distance and zero the encoder
            if (clawPosition.get() <= CLAW_CALIBRATION_DISTANCE) {
                clawPivot.set(0.7);
            } else {
                clawPivot.set(0);
                clawPosition.reset();
                return true;
            }
        }
        return false;
    }

    private double getLeftJoystick(){
        return driveController.getRawAxis(LOGITECH_LEFT_JOYSTICK);
    }

    private double getRightJoystick(){
        return driveController.getRawAxis(LOGITECH_RIGHT_JOYSTICK);

    }
}
