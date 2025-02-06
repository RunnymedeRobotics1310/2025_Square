package frc.robot.commands.coral;

import frc.robot.Constants.CoralConstants;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;


public class MoveToHeightCommand extends LoggingCommand {

    private final CoralConstants.ElevatorHeight targetLevel;
    private final CoralSubsystem                coralSubsystem;

    public MoveToHeightCommand(CoralSubsystem coralSubsystem, CoralConstants.ElevatorHeight ScoringPosition) {

        this.targetLevel    = ScoringPosition;
        this.coralSubsystem = coralSubsystem;



        addRequirements(coralSubsystem);
    }

    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {

        // FIXME: Set target height based on target level
        double targetHeight = 50;

        if (coralSubsystem.getElevatorEncoder() > targetHeight) {
            coralSubsystem.setElevatorSpeed(-CoralConstants.ELEVATOR_SPEED);
        }
        else {
            coralSubsystem.setElevatorSpeed(CoralConstants.ELEVATOR_SPEED);
        }
        // sets the elevator speed to the elevator speed constant

        // checks to see if the elevator is at the postion of lvl 4
    }


    @Override
    public boolean isFinished() {
        if (Math
            .abs(coralSubsystem.getElevatorEncoder() - CoralConstants.LEVEL_FOUR_HEIGHT) <= CoralConstants.ELEVATOR_TOLERANCE) {
            coralSubsystem.setElevatorSpeed(0);
            // logs that it's at lvl 4 if the position is at lvl 4
            setFinishReason("at l4");
            return true;
        }
        else if (Math
            .abs(coralSubsystem.getElevatorEncoder() - CoralConstants.LEVEL_THREE_HEIGHT) <= CoralConstants.ELEVATOR_TOLERANCE) {
            coralSubsystem.setElevatorSpeed(0);
            // logs that it's at lvl 3 if the position is at lvl 3
            setFinishReason("at l3");
            return true;
        }
        else if (Math
            .abs(coralSubsystem.getElevatorEncoder() - CoralConstants.LEVEL_TWO_HEIGHT) <= CoralConstants.ELEVATOR_TOLERANCE) {
            coralSubsystem.setElevatorSpeed(0);
            // logs that it's at lvl 2 if the position is at lvl 2
            setFinishReason("at l2");
            return true;
        }
        else if (Math
            .abs(coralSubsystem.getElevatorEncoder() - CoralConstants.LEVEL_ONE_HEIGHT) <= CoralConstants.ELEVATOR_TOLERANCE) {
            coralSubsystem.setElevatorSpeed(0);
            // logs that it's at lvl 1 if the position is at lvl 1
            setFinishReason("at l1");
            return true;
        }

        return false;

    }

    @Override
    public void end(boolean interrupted) {
        coralSubsystem.setElevatorSpeed(0);
        logCommandEnd(interrupted);
    }
}
