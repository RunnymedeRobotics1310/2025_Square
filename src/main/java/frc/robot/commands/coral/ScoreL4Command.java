package frc.robot.commands.coral;

import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;


public class ScoreL4Command extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;

    public ScoreL4Command(CoralSubsystem coralSubsystem) {

        this.coralSubsystem = coralSubsystem;

        addRequirements(coralSubsystem);
    }

    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }
}
