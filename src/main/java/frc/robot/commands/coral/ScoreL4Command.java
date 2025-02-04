package frc.robot.commands.coral;

import frc.robot.Constants.CoralConstants;
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

        // sets the elevator speed to the elevator speed constant
        coralSubsystem.setElevatorSpeed(CoralConstants.ELEVATOR_SPEED);

        // checks to see if the elevator is at the postion of lvl 4
        if (Math
            .abs(coralSubsystem.getElevatorEncoder() - CoralConstants.LEVEL_FOUR_HEIGHT) <= CoralConstants.ELEVATOR_TOLERANCE) {
            coralSubsystem.setElevatorSpeed(0);
            // finished
        }
    }


    @Override
    public boolean isFinished() {
        if (Math
            .abs(coralSubsystem.getElevatorEncoder() - CoralConstants.LEVEL_FOUR_HEIGHT) <= CoralConstants.ELEVATOR_TOLERANCE) {
            // logs that it's at lvl 4 if the position is at lvl 4
            log("AT L4");
            return true;
        }

        else
            ;
        return false;

    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }
}
