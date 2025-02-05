package frc.robot.commands.coral;

import frc.robot.Constants.CoralConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;

/**
 * This command is used to safely stop the robot in its current position, and to cancel any running
 * commands
 */
public class IntakeCoralCommand extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;

    /**
     * Cancel the commands running on all subsystems.
     *
     * All subsystems must be passed to this command, and each subsystem should have a stop command
     * that safely stops the robot from moving.
     */
    public IntakeCoralCommand(CoralSubsystem coralSubsystem) {
        this.coralSubsystem = coralSubsystem;

        addRequirements(coralSubsystem);
    }

    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {
        coralSubsystem.setIntakeSpeed(CoralConstants.CORAL_INTAKE_SPEED);
    }

    @Override
    public boolean isFinished() {

        if (coralSubsystem.isCoralDetected()) {
            // stop the motors when coral is detected
            // FIXME: need to test whether this is true
            // we may need to wait a few MS for the coral to be in the middle.
            // FIXME: move the motor stop to the end routine.
            coralSubsystem.setIntakeSpeed(0);
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }
}
