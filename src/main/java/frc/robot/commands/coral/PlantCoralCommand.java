package frc.robot.commands.coral;

import frc.robot.Constants.CoralConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;

/**
 * Pushes coral out of the arm, then stops the wheels when the coral is gone.
 */
public class PlantCoralCommand extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;

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
