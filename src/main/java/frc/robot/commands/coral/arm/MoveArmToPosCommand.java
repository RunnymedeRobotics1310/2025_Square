package frc.robot.commands.coral.arm;

import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.coral.CoralSubsystem;

public class MoveArmToPosCommand extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;

    private final double         angle;

    public MoveArmToPosCommand(double angle, CoralSubsystem coralSubsystem) {

        this.coralSubsystem = coralSubsystem;
        this.angle          = angle;

        addRequirements(coralSubsystem);
    }


    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {

        // FIXME: Move the arm.
        // Put the arm positioning code in the coral subsystem
        // position = coralSubsystem.setArmPosition()
        // This code will try to drive the coral arm to the requested position
        // and will return the current position.

    }

    @Override
    public boolean isFinished() {

        // FIXME: Temporarily end after 1 second
        if (hasElapsed(1)) {
            return true;
        }

        // Check if the arm is at the requested position.
        return false;

    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }


}
