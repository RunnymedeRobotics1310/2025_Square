package frc.robot.commands.coral;

import frc.robot.Constants;
import frc.robot.Constants.CoralConstants.CoralPose;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;

public class MoveToCoralPoseCommand extends LoggingCommand {

    private final CoralSubsystem                     coralSubsystem;
    private final Constants.CoralConstants.CoralPose pose;

    public MoveToCoralPoseCommand(CoralSubsystem coralSubsystem, CoralPose pose) {
        this.coralSubsystem = coralSubsystem;
        this.pose           = pose;
        addRequirements(coralSubsystem);
    }

    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {
        coralSubsystem.setElevatorHeight(pose.elevatorHeight);
        coralSubsystem.setArmEncoderPostion(pose.armAngle);
    }

    @Override
    public boolean isFinished() {

        return coralSubsystem.isAtElevatorHeight(pose.elevatorHeight)
            && (Math.abs(coralSubsystem.getArmAngle() - pose.armAngle) <= Constants.CoralConstants.ARM_ANGLE_TOLERANCE);

    }


}
