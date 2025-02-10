package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CoralConstants;
import frc.robot.Constants.CoralConstants.ElevatorHeight;

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
    private double armSpeed      = 0;
    private double intakeSpeed   = 0;

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

//    private SparkLimitSwitch      intakeCoralDetector                 = intakeMotor.getForwardLimitSwitch();


    // Simulation constants
    private boolean             isSimulation                        = false;
    // Elevator full speed up: the elevator will raise 60 inches in 2 seconds with a loop time of
    // 20ms.
    private static final double ELEVATOR_MAX_UP_DISTANCE_PER_LOOP   = 60 * .02 / 2;
    // Elevator full speed down: the elevator will lower in 1.5 seconds.
    private static final double ELEVATOR_MAX_DOWN_DISTANCE_PER_LOOP = 60 * .02 / 1.5;
    private double              simulationElevatorHeight            = 0;
    // Arm full speed: the arm will raise 180 degrees in two secondsconds.
    private static final double ARM_ANGLE_MAX_DEGREES_PER_LOOP      = 180 * .02 / 2.0;
    private double              simulationArmAngle                  = 0;
    // Intake detect time seconds.
    private static final double INTAKE_DETECTION_TIME_SECONDS       = 3;
    private Timer               simulationIntakeDetectTimer         = new Timer();
    private boolean             simulationIntakeDetector            = false;
    private double              simulationPreviousIntakeSpeed       = 0;


    public CoralSubsystem(LightsSubsystem lightsSubsystem) {

        this.lightsSubsystem = lightsSubsystem;
    }

    /*
     * Elevator Routines
     */

    public void setElevatorSpeed(double speed) {

        this.elevatorSpeed = speed;

        checkSafety();

        // elevatorMotor.set(ControlMode.PercentOutput, elevatorSpeed);
    }

    public boolean setElevatorHeight(ElevatorHeight height) {

        if (getElevatorHeight().compareTo(height) == 0) {
            setElevatorSpeed(0);
            return true;
        }

        if (getElevatorHeight().compareTo(height) < 0) {
            setElevatorSpeed(CoralConstants.ELEVATOR_SPEED);
        }
        else {
            setElevatorSpeed(-CoralConstants.ELEVATOR_SPEED);
        }
        return false;
    }

    public ElevatorHeight getElevatorHeight() {

        if (isElevatorAtLowerLimit()) {
            return ElevatorHeight.LEVEL_0;
        }
        else if (getElevatorEncoder() < CoralConstants.LEVEL_ONE_HEIGHT - CoralConstants.ELEVATOR_TOLERANCE) {
            return ElevatorHeight.LEVEL_0_5;
        }
        else if (getElevatorEncoder() < CoralConstants.LEVEL_ONE_HEIGHT + CoralConstants.ELEVATOR_TOLERANCE) {
            return ElevatorHeight.LEVEL_1;
        }
        else if (getElevatorEncoder() < CoralConstants.LEVEL_TWO_HEIGHT - CoralConstants.ELEVATOR_TOLERANCE) {
            return ElevatorHeight.LEVEL_1_5;
        }
        else if (getElevatorEncoder() < CoralConstants.LEVEL_TWO_HEIGHT + CoralConstants.ELEVATOR_TOLERANCE) {
            return ElevatorHeight.LEVEL_2;
        }
        else if (getElevatorEncoder() < CoralConstants.LEVEL_THREE_HEIGHT - CoralConstants.ELEVATOR_TOLERANCE) {
            return ElevatorHeight.LEVEL_2_5;
        }
        else if (getElevatorEncoder() < CoralConstants.LEVEL_THREE_HEIGHT + CoralConstants.ELEVATOR_TOLERANCE) {
            return ElevatorHeight.LEVEL_3;
        }
        else if (getElevatorEncoder() < CoralConstants.LEVEL_FOUR_HEIGHT - CoralConstants.ELEVATOR_TOLERANCE) {
            return ElevatorHeight.LEVEL_3_5;
        }
        else if (getElevatorEncoder() < CoralConstants.LEVEL_FOUR_HEIGHT + CoralConstants.ELEVATOR_TOLERANCE) {
            return ElevatorHeight.LEVEL_4;
        }
        else {
            return ElevatorHeight.LEVEL_4_5;
        }
    }

    public boolean isElevatorAtLowerLimit() {

        if (isSimulation) {
            if (simulationElevatorHeight <= 0) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;// elevatorLowerLimitSwitch.isPressed();
    }

    public boolean isElevatorAtUpperLimit() {

        if (isSimulation) {
            if (simulationElevatorHeight >= 60) {
                return true;
            }
            else {
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
        lightsSubsystem.setElevatorHeight(getElevatorHeight());
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
        }
        else {
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

        // FIXME: add an upper limit check
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
