package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CoralSubsystem extends SubsystemBase {

    private final LightsSubsystem lightsSubsystem;

    // Coral Subsystem Motors
//    private final TalonSRX        elevatorMotor             = new TalonSRX(CoralConstants.ELEVATOR_MOTOR_CAN_ID);
//    private final TalonSRX        armMotor                            = new TalonSRX(CoralConstants.ARM_MOTOR_CAN_ID);
//    private final TalonSRX        intakeMotor               = new TalonSRX(CoralConstants.INTAKE_MOTOR_CAN_ID);

    private double              elevatorSpeed                       = 0;
    private double              armSpeed                            = 0;
    private double              intakeSpeed                         = 0;

    private double              elevatorEncoderOffset               = 0;
    private double              elevatorEncoder                     = 0;

    private double              armEncoderOffset                    = 0;
    private double              armEncoder                          = 0;

    // Simulation constants
    // Full speed up: the elevator will raise 60 inches in 2 seconds with a loop time of 20ms.
    private static final double ELEVATOR_MAX_UP_DISTANCE_PER_LOOP   = 60 * .02 / 2;
    // Full speed down: the elevator will lower in 1.5 seconds.
    private static final double ELEVATOR_MAX_DOWN_DISTANCE_PER_LOOP = 60 * .02 / 1.5;

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

    public boolean isElevatorAtLowerLimit() {

        // This method should check a sensor.
        if (getElevatorEncoder() <= 0) {
            return true;
        }

        return false;
    }

    public boolean isElevatorAtUpperLimit() {

        // This method should check a sensor.
        if (getElevatorEncoder() >= 60) {
            return true;
        }

        return false;
    }

    public double getElevatorEncoder() {
        // This method should read an encoder
        return elevatorEncoder + elevatorEncoderOffset;
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
        if (getArmEncoder() <= Constants.CoralConstants.armPositionLowerLimit) {
            return true;
        }
        return false;
    }

    public boolean isArmAtUpperLimit() {
        if (getArmEncoder() >= Constants.CoralConstants.armPositionUpperLimit) {
            return true;
        }
        return false;
    }

    public double getArmEncoder() {
        return armEncoder + armEncoderOffset;
    }


    public void resetArmEncoder() {
        setArmEncoderOffset(0);
    }

    public void setArmEncoderOffset(double encoderValue) {
        armEncoderOffset = 0;
        armEncoderOffset = -getArmEncoder() + encoderValue;

    }
    /*
     * Intake Routines
     * FIXME: Make intake routines similar to the elevator routines
     * NOTE: the intake will not have encoders or limit switches.
     */



    /*
     * Periodic routines
     */
    @Override
    public void periodic() {

        // FIXME: replace the simulation when the robot is ready.
        simulate();

        checkSafety();

        // FIXME: Add a call to the lights subsystem to show the current speed or height
//        lightsSubsystem.setArmMotorSpeeds(armSpeed);
//        lightsSubsystem.setArmPosition(armEncoder);
        // FIXME: do not pass the arm encoder offset, pass getArmEncoder();
        lightsSubsystem.setArmMotorSpeedsAndPosition(armSpeed, armEncoderOffset);

        SmartDashboard.putNumber("Coral Elevator Motor", elevatorSpeed);
        SmartDashboard.putNumber("Coral Intake Motor", intakeSpeed);

        // FIXME: what else should we put on the SmartDashboard
        SmartDashboard.putNumber("Coral Arm Motor", armSpeed);
        SmartDashboard.putNumber("Coral Elevator Position", getElevatorEncoder());

    }

    private void simulate() {

        // This loop will be called every 20 ms, 50 times per second
        // FIXME: why are we overriding the arm speed here to 1?
        armSpeed    = 1;
        // FIXME: should be armEncoder += armSpeed * MAX_ARM_DISTANCE_PER_LOOP;
        armEncoder += 0;

        // Move the elevator up or down depending on the direction of the motor speed
        // The elevator will fall faster than it will lift.
        if (elevatorSpeed > 0) {
            elevatorEncoder += ELEVATOR_MAX_UP_DISTANCE_PER_LOOP * elevatorSpeed;
        }
        if (elevatorSpeed < 0) {
            elevatorEncoder += ELEVATOR_MAX_DOWN_DISTANCE_PER_LOOP * elevatorSpeed;
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

        // FIXME: by convention up is positive and down is negative.
        if (isArmAtUpperLimit()) {
            // Do not know which way the motor will actually move, might need to be >
            // FIXME: FALSE: we know because we will configure the motor controller properly, up
            // will be positive.

            // FIXME: is this if statement correct? At the top, we want to be able
            // to drive down, but not up.
            if (armSpeed < 0) {
                armSpeed = 0;
            }
        }

        // FIXME: is this correct?
        if (isArmAtLowerLimit()) {
            // Do not know which way the motor will actually move, might need to be <
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
            .append(" height ").append(getElevatorEncoder()).append("in");

        return sb.toString();
    }
}
