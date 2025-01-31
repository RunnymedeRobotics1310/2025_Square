package frc.robot.commands.coral;

import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;


public class ScoreL4Command extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;
    private final double         l4Height          = 20;
    private final double         elevatorTolerance = 1;


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
        coralSubsystem.setElevatorSpeed(0.5);

        if (coralSubsystem.getElevatorEncoder() <= l4Height + elevatorTolerance &&
            coralSubsystem.getElevatorEncoder() >= l4Height - elevatorTolerance) {
            coralSubsystem.setElevatorSpeed(0);
        }
    }

    @Override
    public boolean isFinished() {
        log("AT L4");
        return false;

    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }
}
