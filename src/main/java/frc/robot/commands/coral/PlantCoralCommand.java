package frc.robot.commands.coral;

import frc.robot.Constants.CoralConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;

/**
 * This command is used to safely stop the robot in its current position, and to cancel any running
 * commands
 */
public class PlantCoralCommand extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;

    /**
     * Cancel the commands running on all subsystems.
     *
     * All subsystems must be passed to this command, and each subsystem should have a stop command
     * that safely stops the robot from moving.
     */
    public PlantCoralCommand(CoralSubsystem coralSubsystem) {
        this.coralSubsystem = coralSubsystem;

        addRequirements(coralSubsystem);
    }

    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {
        coralSubsystem.setIntakeSpeed(CoralConstants.CORAL_OUTAKE_SPEED);
    }

    @Override
    public boolean isFinished() {

        // FIXME: maybe stop .1 seconds after the coral is no longer
        // detected to give it time to clear the wheels.
        if (!coralSubsystem.isCoralDetected()) {
            // FIXME: move to the end routine
            // wheels should stop when the command ends
            // stop the motors when coral is gone
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
