package frc.robot.subsystems;

import static frc.robot.Constants.CoralConstants.ElevatorHeight.LEVEL_1;
import static frc.robot.Constants.CoralConstants.ElevatorHeight.LEVEL_2;
import static frc.robot.Constants.CoralConstants.ElevatorHeight.LEVEL_3;
import static frc.robot.Constants.CoralConstants.ElevatorHeight.LEVEL_4;

import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CoralConstants;
import frc.robot.Constants.CoralConstants.ElevatorHeight;
import frc.robot.Robot;

public class CoralSubsystem extends SubsystemBase {

    private final LightsSubsystem lightsSubsystem;

//    // Coral Subsystem Motors
//    private final SparkFlex       elevatorMotor                       = new SparkFlex(CoralConstants.ELEVATOR_MOTOR_CAN_ID,
//        MotorType.kBrushed);
//    private final SparkMax        armMotor                            = new SparkMax(CoralConstants.ARM_MOTOR_CAN_ID,
//        MotorType.kBrushless);
//    private final SparkMax        intakeMotor                         = new SparkMax(CoralConstants.INTAKE_MOTOR_CAN_ID,
//        MotorType.kBrushless);

    private double elevatorSpeed = 0;
    private double armSpeed = 0;
    private double intakeSpeed = 0;

    // Elevator

//    private RelativeEncoder       elevatorEncoder                     = elevatorMotor.getEncoder();
//
//    private SparkLimitSwitch      elevatorLowerLimitSwitch            = elevatorMotor.getReverseLimitSwitch();
//    private SparkLimitSwitch      elevatorUpperLimitSwitch            = elevatorMotor.getForwardLimitSwitch();

    private double elevatorEncoderOffset = 0;

    // Arm

//    private SparkAbsoluteEncoder  armPositionEncoder                  = armMotor.getAbsoluteEncoder();

    private double armEncoderOffset = 0;

    // Intake

//  private RelativeEncoder       intakeEncoder                     = intakeMotor.getEncoder();
//
//    private SparkLimitSwitch      intakeCoralDetector                 = intakeMotor.getForwardLimitSwitch();


    // Simulation constants
    private boolean isSimulation = false;
    // Elevator full speed up: the elevator will raise 60 inches in 2 seconds with a loop time of
    // 20ms.
    private static final double ELEVATOR_MAX_UP_DISTANCE_PER_LOOP = 60 * .02 / 2;
    // Elevator full speed down: the elevator will lower in 1.5 seconds.
    private static final double ELEVATOR_MAX_DOWN_DISTANCE_PER_LOOP = 60 * .02 / 1.5;
    private double simulationElevatorHeight = 0;
    // Arm full speed: the arm will raise 180 degrees in two secondsconds.
    private static final double ARM_ANGLE_MAX_DEGREES_PER_LOOP = 180 * .02 / 2.0;
    private double simulationArmAngle = 0;
    // Intake detect time seconds.
    private static final double INTAKE_DETECTION_TIME_SECONDS = 3;
    private Timer simulationIntakeDetectTimer = new Timer();
    private boolean simulationIntakeDetector = false;
    private double simulationPreviousIntakeSpeed = 0;
    private int simulationIntakeEncoder = 0;


    public CoralSubsystem(LightsSubsystem lightsSubsystem) {

        this.lightsSubsystem = lightsSubsystem;

        /*
         * Elevator Motor Config
         */
        SparkFlexConfig flexConfig = new SparkFlexConfig();

        flexConfig.disableFollowerMode();
        flexConfig.idleMode(IdleMode.kBrake);
        flexConfig.inverted(CoralConstants.ELEVATOR_MOTOR_INVERTED);

        // Upper and Lower Limit switches
        flexConfig.limitSwitch.forwardLimitSwitchEnabled(false);
        flexConfig.limitSwitch.forwardLimitSwitchType(Type.kNormallyOpen);

        flexConfig.limitSwitch.reverseLimitSwitchEnabled(false);
        flexConfig.limitSwitch.reverseLimitSwitchType(Type.kNormallyOpen);

//        elevatorMotor.configure(flexConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
//
//        elevatorEncoder.setPosition(0);

        /*
         * Arm Motor Config
         */
        SparkMaxConfig sparkMaxConfig = new SparkMaxConfig();

        sparkMaxConfig.disableFollowerMode();
        sparkMaxConfig.idleMode(IdleMode.kBrake);
        sparkMaxConfig.inverted(CoralConstants.ARM_MOTOR_INVERTED);

        sparkMaxConfig.absoluteEncoder.inverted(CoralConstants.ARM_POSITION_ENCODER_INVERTED);

//        armMotor.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        /*
         * Intake Motor Config
         */
        sparkMaxConfig = new SparkMaxConfig();

        sparkMaxConfig.disableFollowerMode();
        sparkMaxConfig.idleMode(IdleMode.kBrake);
        sparkMaxConfig.inverted(CoralConstants.INTAKE_MOTOR_INVERTED);

        sparkMaxConfig.limitSwitch.forwardLimitSwitchEnabled(false);
        sparkMaxConfig.limitSwitch.forwardLimitSwitchType(Type.kNormallyOpen);

//        intakeMotor.configure(sparkMaxConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        /*
         * Simulation
         */
        if (Robot.isSimulation()) {
            isSimulation = true;
        }
    }

    /*
     * Elevator Routines
     */

    public void setElevatorSpeed(double speed) {

        this.elevatorSpeed = speed;

        checkSafety();

        // elevatorMotor.set(ControlMode.PercentOutput, elevatorSpeed);
    }

    public boolean setElevatorHeight(ElevatorHeight targetHeight) {

        if (isAtElevatorHeight(targetHeight)) {
            setElevatorSpeed(0);
            return true;
        }

        double error = targetHeight.encoderCount - getElevatorEncoder();
        setElevatorSpeed(error * CoralConstants.ELEVATOR_P * CoralConstants.ELEVATOR_MAX_SPEED);

        return false;
    }

    public boolean isAtElevatorHeight(ElevatorHeight height) {

        return (Math.abs(height.encoderCount - getElevatorEncoder()) <= CoralConstants.ELEVATOR_TOLERANCE);

    }

    public boolean isElevatorAtLowerLimit() {

        if (isSimulation) {
            if (simulationElevatorHeight <= 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;// elevatorLowerLimitSwitch.isPressed();
    }

    public boolean isElevatorAtUpperLimit() {

        if (isSimulation) {
            if (simulationElevatorHeight >= 60) {
                return true;
            } else {
                return false;
            }
        }
        return false; // elevatorUpperLimitSwitch.isPressed();
    }

    public double getElevatorEncoder() {

        if (isSimulation) {
            return simulationElevatorHeight + elevatorEncoderOffset;
        }
        return 0; // elevatorEncoder.getPosition() + elevatorEncoderOffset;
    }

    public void resetElevatorEncoder() {
        setElevatorEncoder(0);
    }

    public void setElevatorEncoder(double encoderValue) {

        elevatorEncoderOffset = 0;
        elevatorEncoderOffset = -getElevatorEncoder() + encoderValue;
    }

    /*
     * Arm Routines
     */
    public void setArmSpeed(double speed) {
        armSpeed = speed;

        checkSafety();
    }

    public boolean isArmAtLowerLimit() {

        return getArmPosition() <= CoralConstants.ARM_LOWER_LIMIT_POSITION;
    }

    public boolean isArmAtUpperLimit() {

        return getArmPosition() >= CoralConstants.ARM_UPPER_LIMIT_POSITION;
    }

    public double getArmPosition() {

        if (isSimulation) {
            return simulationArmAngle + armEncoderOffset;
        }

        return 0; // armPositionEncoder.getPosition() + armEncoderOffset;
    }

    public void resetArmEncoder() {
        setArmEncoderPostion(0);
    }

    public void setArmEncoderPostion(double encoderValue) {
        armEncoderOffset = 0;
        armEncoderOffset = -getArmPosition() + encoderValue;

    }

    public boolean moveArmToPosition(double targetPosition) {
        double currentPosition = getArmPosition();

        double error = targetPosition - currentPosition;
        double desiredArmSpeed = CoralConstants.ARM_FAST_SPEED;

        if (Math.abs(error) < CoralConstants.ARM_TOLERANCE) {
            armSpeed = 0;
            return true;
        }

        if (Math.abs(error) < CoralConstants.ARM_SLOW_ZONE_THRESHOLD) {
            desiredArmSpeed = CoralConstants.ARM_SLOW_SPEED;
        }

        if (error < 0) {
            desiredArmSpeed = -desiredArmSpeed;
        }

        setArmSpeed(desiredArmSpeed);
        return false;
    }

    /*
     * Intake Routines
     */
    public void setIntakeSpeed(double speed) {

        this.intakeSpeed = speed;

        checkSafety();
    }

    public boolean isCoralDetected() {

        if (isSimulation) {
            return simulationIntakeDetector;
        }
        return false; // intakeCoralDetector.isPressed();
    }

    public double getIntakeEncoder() {

        if (isSimulation) {
            return simulationIntakeEncoder;
        }

        return 0; // intakeEncoder.getPosition();
    }

    public void stop() {
        setElevatorSpeed(0);
        setArmSpeed(0);
        setIntakeSpeed(0);
    }


    /*
     * Periodic routines
     */
    @Override
    public void periodic() {

        // FIXME: replace the simulation when the robot is ready.
        simulate();

        checkSafety();

        // FIXME: Add a call to the lights subsystem to show the current speed
        lightsSubsystem.setElevatorHeight(getElevatorEncoder());
        lightsSubsystem.setArmPosition(getArmPosition());

        SmartDashboard.putNumber("Coral/Elevator Speed", elevatorSpeed);
        SmartDashboard.putNumber("Coral/Elevator Position", getElevatorEncoder());
        SmartDashboard.putBoolean("Coral/Elevator Upper Limit", isElevatorAtUpperLimit());
        SmartDashboard.putBoolean("Coral/Elevator Lower Limit", isElevatorAtLowerLimit());

        SmartDashboard.putNumber("Coral/Arm Speed", armSpeed);
        SmartDashboard.putNumber("Coral/Arm Position", getArmPosition());
        SmartDashboard.putBoolean("Coral/Arm Upper Limit", isArmAtUpperLimit());
        SmartDashboard.putBoolean("Coral/Arm Lower Limit", isArmAtLowerLimit());

        SmartDashboard.putNumber("Coral/Intake Speed", intakeSpeed);
        SmartDashboard.putBoolean("Coral/Coral Detected", isCoralDetected());
    }

    private void simulate() {

        // This loop will be called every 20 ms, 50 times per second

        // Move the elevator up or down depending on the direction of the motor speed
        // The elevator will fall faster than it will lift.
        if (elevatorSpeed > 0) {
            simulationElevatorHeight += ELEVATOR_MAX_UP_DISTANCE_PER_LOOP * elevatorSpeed;
        }
        if (elevatorSpeed < 0) {
            simulationElevatorHeight += ELEVATOR_MAX_DOWN_DISTANCE_PER_LOOP * elevatorSpeed;
        }

        simulationArmAngle += ARM_ANGLE_MAX_DEGREES_PER_LOOP * armSpeed;

        simulationIntakeEncoder += intakeSpeed;

        // Intake detection, change states if the timer is running for 3 seconds
        if (intakeSpeed != 0 && simulationPreviousIntakeSpeed == 0) {
            simulationIntakeDetectTimer.reset();
            simulationIntakeDetectTimer.start();
        }
        simulationPreviousIntakeSpeed = intakeSpeed;

        if (intakeSpeed != 0) {
            if (simulationIntakeDetectTimer.hasElapsed(INTAKE_DETECTION_TIME_SECONDS)) {
                simulationIntakeDetector = !simulationIntakeDetector;
                simulationIntakeDetectTimer.reset();
                simulationIntakeDetectTimer.stop();
            }
        } else {
            simulationIntakeDetectTimer.reset();
            simulationIntakeDetectTimer.stop();
        }
    }

    private void checkSafety() {

        if (isElevatorAtLowerLimit()) {

            if (elevatorSpeed < 0) {
                elevatorSpeed = 0;
                // Directly set the motor speed, do not call the setter method (recursive loop)
                // elevatorMotor.set(ControlMode.PercentOutput, 0);
                resetElevatorEncoder();
            }
        }

        if (isElevatorAtUpperLimit()) {

            if (elevatorSpeed > 0) {
                elevatorSpeed = 0;
                // Directly set the motor speed, do not call the setter method (recursive loop)
                // elevatorMotor.set(ControlMode.PercentOutput, 0);
            }
        }

        /*
         * Arm Safety
         */
        if (isArmAtLowerLimit()) {

            if (armSpeed < 0) {
                armSpeed = 0;
            }
        }

        if (isArmAtUpperLimit()) {

            if (armSpeed > 0) {
                armSpeed = 0;
            }
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName()).append(" : ")
                .append("Elevator: speed ").append(elevatorSpeed)
                .append(" height ").append(getElevatorEncoder()).append("in")
                .append(",  Arm: speed ").append(armSpeed)
                .append(" angle ").append(getArmPosition()).append(" deg")
                .append(",  Intake: speed ").append(intakeSpeed)
                .append(" coral detect: ").append(isCoralDetected());

        return sb.toString();
    }
}
