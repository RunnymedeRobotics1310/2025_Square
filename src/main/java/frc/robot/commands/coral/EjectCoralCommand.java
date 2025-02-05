package frc.robot.commands.coral;

import frc.robot.Constants.CoralConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;

/**
 * This comand runs the coral Intake motors backwards while button is held.
 */
public class EjectCoralCommand extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;

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
