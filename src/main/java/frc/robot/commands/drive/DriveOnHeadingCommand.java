package frc.robot.commands.drive;

import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;

public class DriveOnHeadingCommand extends LoggingCommand {

    private double               desiredHeading;
    private int                  time;
    private final DriveSubsystem driveSubsystem;
    private double               leftSpeed;
    private double               rightSpeed;
    private double               turn;
    private double               robotHeading;

    public DriveOnHeadingCommand(int speed, double heading, int time, DriveSubsystem driveSubsystem) {

        this.desiredHeading = heading;
        this.time           = time;
        this.driveSubsystem = driveSubsystem;
        this.leftSpeed      = speed;
        this.rightSpeed     = speed;

        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {
        robotHeading = driveSubsystem.


            driveSubsystem.setMotorSpeeds(leftSpeed, rightSpeed);

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {

    }

}
