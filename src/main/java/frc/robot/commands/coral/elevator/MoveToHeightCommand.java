package frc.robot.commands.coral.elevator;

import frc.robot.Constants.CoralConstants.ElevatorHeight;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;

// FIXME: can we rename this class to include the word 'elevator' somewhere?
public class MoveToHeightCommand extends LoggingCommand {

    private final ElevatorHeight elevatorHeight;
    private final CoralSubsystem coralSubsystem;

    public MoveToHeightCommand(CoralSubsystem coralSubsystem, ElevatorHeight elevatorHeight) {

        this.elevatorHeight = elevatorHeight;
        this.coralSubsystem = coralSubsystem;

        addRequirements(coralSubsystem);
    }

    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {
        coralSubsystem.setElevatorHeight(elevatorHeight);
    }

    @Override
    public boolean isFinished() {
        return coralSubsystem.getElevatorHeight() == elevatorHeight;
    }

    @Override
    public void end(boolean interrupted) {
        coralSubsystem.setElevatorSpeed(0);
        logCommandEnd(interrupted);
    }
}
