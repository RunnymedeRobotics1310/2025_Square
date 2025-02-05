package frc.robot.commands.coral;

import frc.robot.Constants.CoralConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;

/**
 * Pulls in coral until it is fully inside the arm, then stops the wheels.
 */
public class IntakeCoralCommand extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;

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
