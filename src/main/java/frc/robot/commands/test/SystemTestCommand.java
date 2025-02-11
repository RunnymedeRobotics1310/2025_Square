package frc.robot.commands.test;

import static frc.robot.commands.test.SystemTestCommand.Motor.NONE;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OperatorInput;
import frc.robot.commands.GameController;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.coral.CoralSubsystem;

public class SystemTestCommand extends LoggingCommand {

    public enum Motor {
        NONE,
        CORAL_ELEVATOR,
        CORAL_ARM,
        CORAL_INTAKE
    }

    private final OperatorInput  oi;
    private final GameController controller;
    private final CoralSubsystem coralSubsystem;

    private Motor                selectedMotor   = NONE;
    private double               motorSpeed;

    private boolean              testModeEnabled = false;

    public SystemTestCommand(OperatorInput oi, CoralSubsystem coralSubsystem) {

        this.oi             = oi;
        this.controller     = oi.getDriverController();

        this.coralSubsystem = coralSubsystem;

        addRequirements(coralSubsystem);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        /*
         * The SystemTestCommand is not interruptible, and prevents all other commands that try to
         * interrupt it. Only the cancel button will end the SystemTestCommand.
         */
        return InterruptionBehavior.kCancelIncoming;
    }

    @Override
    public void initialize() {

        logCommandStart();

        stopAllMotors();

        // Clear the bumper buttonPressed buffers
        controller.getRightBumperButtonPressed();
        controller.getLeftBumperButtonPressed();

        // Indicate test mode is started
        testModeEnabled = true;
    }


    @Override
    public void execute() {

        selectMotor();

        readMotorSpeed();

        applyMotorSpeed();

        updateDashboard();
    }


    /**
     * Use the bumpers to select the next / previous motor in the motor ring.
     * <p>
     * Switching motors will cause all motors to stop
     */
    private void selectMotor() {

        // Right and left bumpers control motor selection
        boolean rightBumper = controller.getRightBumperButtonPressed();
        boolean leftBumper  = controller.getLeftBumperButtonPressed();

        if (rightBumper || leftBumper) {

            int nextMotorIndex = selectedMotor.ordinal();

            if (rightBumper) {

                // Select the next motor in the ring
                nextMotorIndex = (nextMotorIndex + 1) % Motor.values().length;
            }
            else {

                // Select the previous motor in the ring
                nextMotorIndex--;
                if (nextMotorIndex < 0) {
                    nextMotorIndex = Motor.values().length - 1;
                }
            }

            stopAllMotors();

            selectedMotor = Motor.values()[nextMotorIndex];

        }
    }


    /**
     * The SystemTestCommand can use either the POV or the triggers to control
     * the motor speed. If the triggers are used, the POV is cleared.
     * <p>
     * Once the motor is selected, use the POV up and down to
     * adjust the motor speed.
     * <p>
     * The speed is adjusted 50 times / second as the user holds the
     * POV control. Allow 5 seconds to ramp the speed from 0 to full value.
     * <p>
     * increment = 1.0 (full) / 50 adjustments/sec / 5 sec = .004 adjustment size / loop.
     */
    private void readMotorSpeed() {

        int    pov          = controller.getPOV();
        double leftTrigger  = controller.getLeftTriggerAxis();
        double rightTrigger = controller.getRightTriggerAxis();


        if (controller.getXButton()) {
            // If the X button is pressed, reset the motor speed to zero
            motorSpeed = 0;
        }
        else {

            // No triggers are pressed, use the POV to control the motor speed
            if (pov == 0) {

                motorSpeed += 0.004;

                if (motorSpeed > 1.0) {
                    motorSpeed = 1.0;
                }
            }

            if (pov == 180) {

                motorSpeed -= 0.004;

                if (motorSpeed < -1.0) {
                    motorSpeed = -1.0;
                }
            }
        }

    }

    /**
     * Apply the selected motor speed to the selected motor
     */
    private void applyMotorSpeed() {
        switch (selectedMotor) {

        case NONE:
            break;

        case CORAL_ELEVATOR:
            coralSubsystem.setElevatorSpeed(motorSpeed);
            break;

        case CORAL_ARM:
            coralSubsystem.setArmSpeed(motorSpeed);
            break;

        case CORAL_INTAKE:
            coralSubsystem.setIntakeSpeed(motorSpeed);
            break;
        }
    }

    @Override
    public boolean isFinished() {

        // Wait 1/2 second before finishing.
        // This allows the user to start this command using the start and back
        // button combination without cancelling on the start button as
        // the user releases the buttons
        if (!hasElapsed(0.5d)) {
            return false;
        }

        // Cancel on the regular cancel button after the first 0.5 seconds
        if (oi.isCancel()) {
            setFinishReason("Cancelled by driver controller");
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {

        stopAllMotors();
        selectedMotor   = Motor.NONE;
        testModeEnabled = false;

        updateDashboard();
        logCommandEnd(interrupted);
    }

    private void stopAllMotors() {
        motorSpeed = 0;
        coralSubsystem.stop();
    }

    private void updateDashboard() {

        SmartDashboard.putBoolean("Test Mode", testModeEnabled);
        SmartDashboard.putString("Test Motor Selected", selectedMotor.toString());
        SmartDashboard.putBoolean("Test Coral Elevator", selectedMotor == Motor.CORAL_ELEVATOR);
        SmartDashboard.putBoolean("Test Coral Arm", selectedMotor == Motor.CORAL_ARM);
        SmartDashboard.putBoolean("Test Coral Intake", selectedMotor == Motor.CORAL_INTAKE);
        SmartDashboard.putNumber("Test Speed", motorSpeed);
    }

}
