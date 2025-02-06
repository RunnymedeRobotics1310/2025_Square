package frc.robot.commands.coral;

import frc.robot.Constants.CoralConstants;
import frc.robot.Constants.CoralConstants.ElevatorHeight;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;


public class MoveToHeightCommand extends LoggingCommand {

    // FIXME: use consistent naming.
    private final ElevatorHeight targetLevel;
    private final CoralSubsystem coralSubsystem;

    public MoveToHeightCommand(CoralSubsystem coralSubsystem, ElevatorHeight ScoringPosition) {

        // FIXME: do not make up new names !
        // FIXME: variables never start with a capital letter ScoringPosition must be
        // scoringPosition (or better, elevatorHeight since that is what it contains)
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

        // FIXME: Use the routine that you wrote to get the elevator height
        // and compare with the target elevator level (enum)
        // For enums, you can use the compareTo() method to
        // compare the ordinal position (order) of enums.

        // if (coralSubsystem.getElevatorState().compareTo(targetLevel) < 0) {
        // go up
        // }
        // or even better...
        // if (coralSubsystem.getElevatorHeight().compareTo(targetElevatorHeight) < 0) {...

        // All compareTo() methods in java compare the first value to the
        // second value and return an integer number value less than zero if the first
        // element is smaller, 0 if they are equal, and a number greater
        // than zero if the first value is larger.

        double targetHeight = 50;

        if (coralSubsystem.getElevatorEncoder() > targetHeight) {
            coralSubsystem.setElevatorSpeed(-CoralConstants.ELEVATOR_SPEED);
        }
        else {
            coralSubsystem.setElevatorSpeed(CoralConstants.ELEVATOR_SPEED);
        }

        // TODO: for discussion with Q. He said that he wanted the elevator movement logic
        // in the subsystem. If that is the case, then we need one method here.
        // coralSubsystem.moveElevatorToHeight(ElevatorHeight) which returns a boolean
        // indicating whether the elevator is at the height requested.
        // Let's talk with Q about this, but in the mean time, finish the other FIXMEs
    }


    @Override
    public boolean isFinished() {

        // FIXME: Since you wrote the code to return the elevator height, why not use it?
        // if (coralSubsystem.getElevatorState() == targetLevel) {
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
