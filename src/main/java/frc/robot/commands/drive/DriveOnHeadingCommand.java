package frc.robot.commands.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;

public class DriveOnHeadingCommand extends LoggingCommand {

    private final DriveSubsystem driveSubsystem;
    private long                 startTime;
    private long                 durationTime;
    private double               speed;
    private double               angleDifferance;
    private Rotation2d           desiredHeading;

    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DriveOnHeadingCommand(Rotation2d desiredHeading, double speed, long durationMillis, DriveSubsystem driveSubsystem) {

        this.driveSubsystem  = driveSubsystem;
        this.angleDifferance = angleDifferance;
        this.desiredHeading  = desiredHeading;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        logCommandStart();
        startTime    = System.currentTimeMillis();
        durationTime = 10000;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        angleDifferance = ((desiredHeading.getDegrees() - driveSubsystem.getHeading().getDegrees()) % 360);

        log("angle differance" + angleDifferance);


        if (angleDifferance >= 0) {
            driveSubsystem.setMotorSpeeds(angleDifferance / 720, -angleDifferance / 720);
        }
        else if (angleDifferance < 0) {
            driveSubsystem.setMotorSpeeds(-angleDifferance / 720, angleDifferance / 720);
        }

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // The default drive command never ends, but can be interrupted by other commands.
        return System.currentTimeMillis() - startTime > durationTime;

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
        driveSubsystem.setMotorSpeeds(0, 0);
    }

}