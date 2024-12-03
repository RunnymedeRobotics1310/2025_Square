package frc.robot.commands.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;

public class DriveOnHeadingCommand extends LoggingCommand {

    private Rotation2d           desiredHeading;
    private long                 durationMs;
    private final DriveSubsystem driveSubsystem;
    private double               leftSpeed;
    private double               rightSpeed;
    private double               turn;
    private Rotation2d           robotHeading;
    private double               offset;
    private long                 startTimeMs;
    private double               P_VALUE = 0.005;

    public DriveOnHeadingCommand(Rotation2d heading, double speed, long durationMs, DriveSubsystem driveSubsystem) {

        this.desiredHeading = heading;
        this.durationMs     = durationMs;
        this.driveSubsystem = driveSubsystem;
        this.leftSpeed      = speed;
        this.rightSpeed     = speed;

        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        logCommandStart();
        startTimeMs = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        robotHeading = driveSubsystem.getHeading();

        offset       = (desiredHeading.getDegrees() - robotHeading.getDegrees()) % 360;



        driveSubsystem.setMotorSpeeds(leftSpeed + offset * P_VALUE, rightSpeed - offset * P_VALUE);

    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - startTimeMs >= durationMs;
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.stop();
    }

}
