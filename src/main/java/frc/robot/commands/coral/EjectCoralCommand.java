package frc.robot.commands.coral;

import frc.robot.Constants.CoralConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;

/**
 * This command is used to safely stop the robot in its current position, and to cancel any running
 * commands
 */
public class EjectCoralCommand extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;

    /**
     * FIXME: Correct the javadoc
     * Cancel the commands running on all subsystems.
     *
     * All subsystems must be passed to this command, and each subsystem should have a stop command
     * that safely stops the robot from moving.
     */
    public EjectCoralCommand(CoralSubsystem coralSubsystem) {
        this.coralSubsystem = coralSubsystem;

        addRequirements(coralSubsystem);
    }

    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {
        // Sets the coral intake motor to NEGATIVE intake speed
        coralSubsystem.setIntakeSpeed(-CoralConstants.CORAL_INTAKE_SPEED);
    }

    @Override
    public boolean isFinished() {

        // FIXME: how does this command end?

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }
}
