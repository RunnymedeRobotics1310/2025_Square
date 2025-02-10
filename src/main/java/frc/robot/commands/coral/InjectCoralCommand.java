package frc.robot.commands.coral;

import frc.robot.Constants.CoralConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;

/**
 * This comand runs the coral Intake motors forwards while button is held.
 */
public class InjectCoralCommand extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;

    public InjectCoralCommand(CoralSubsystem coralSubsystem) {
        this.coralSubsystem = coralSubsystem;

        addRequirements(coralSubsystem);
    }

    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {
        // Sets the coral intake motor to intake speed
        coralSubsystem.setIntakeSpeed(CoralConstants.CORAL_INTAKE_SPEED);
    }

    @Override
    public boolean isFinished() {

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        // When inturrupted stop the motors.
        // The command is called on a whileTrue in OI, so the intake will stop when the button is
        // released
        coralSubsystem.setIntakeSpeed(0);
        logCommandEnd(interrupted);
    }
}
