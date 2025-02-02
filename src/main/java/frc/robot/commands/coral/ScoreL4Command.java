package frc.robot.commands.coral;

import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;


public class ScoreL4Command extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;
    // FIXME: move the height to the Constants class and make constants for all of the heights.
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

        // FIXME: This will cause the elevator to always go up a little bit even when
        // at the target because we are setting the elevator speed twice (once here (always),
        // and once below (when we are at the target). The motor will appear to stutter.
        // Instead, calculate the motor speed that you want (up, down, or zero), and then
        // set it once at the end of the execute loop.
        coralSubsystem.setElevatorSpeed(0.5);

        // TODO: While this is correct, normally a tolerance would be checked by
        // if (Math.abs(actual - target) <= tolerance) {
        if (coralSubsystem.getElevatorEncoder() <= l4Height + elevatorTolerance &&
            coralSubsystem.getElevatorEncoder() >= l4Height - elevatorTolerance) {
            coralSubsystem.setElevatorSpeed(0);
        }
    }

    @Override
    public boolean isFinished() {

        // FIXME:
        // set the end reason and the end routine will log it for you
        // setFinishReason("the reason text");
        // Also, only set the reason once we know the command is finished.
        log("AT L4");

        // FIXME: When should this command end?
        return false;

    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }
}
