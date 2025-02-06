package frc.robot.commands.coral;

import frc.robot.Constants.CoralConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;

/**
 * Pushes coral out of the arm, then stops the wheels when the coral is gone.
 */
public class PlantCoralCommand extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;

    // FIXME: this will not work because the variable is set when the class is
    // constructed, not when it executes.
    // In order to use this concept, you would need to set the minimumEndTime in
    // the initialize method.
    // The logging command has an hasElapsed(seconds) method which should be used
    // instead of this idea.
    public double                minimumEndTime = System.currentTimeMillis() + 100;

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

        // FIXME: Perhaps we should be waiting some amount of time after the coral is no longer
        // detected,
        // but that would be different. This code will wait at least the minimum wait time, and then
        // until the coral is not detected. Ideally we would set the minimumEndTime _after_ the
        // coral
        // is no longer detected.

        // FIXME: This logic, which may or may not be what we want, can be replaced with
        // !coralDetected && hasElapsed(minimumTimeSec)
        if (!coralSubsystem.isCoralDetected() && System.currentTimeMillis() > minimumEndTime) {
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        coralSubsystem.setIntakeSpeed(0);
        logCommandEnd(interrupted);
    }
}
