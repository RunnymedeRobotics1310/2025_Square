package frc.robot.commands.coral.arm;

import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;

public class MoveArmToPosCommand extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;

    private final double         position;

    private boolean atPosition = false;

    public MoveArmToPosCommand(double position, CoralSubsystem coralSubsystem) {

        this.coralSubsystem = coralSubsystem;
        this.position          = position;

        addRequirements(coralSubsystem);
    }


    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {


        atPosition = coralSubsystem.moveArmToPosition(position);


    }

    @Override
    public boolean isFinished() {

        if (hasElapsed(1)) {
            return true;
        }

        // Check if the arm is at the requested position.
        if (atPosition) {
            return true;
        }
        return false;

    }

    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
    }


}
