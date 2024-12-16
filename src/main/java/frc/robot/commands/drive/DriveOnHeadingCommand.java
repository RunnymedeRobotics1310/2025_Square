package frc.robot.commands.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;

public class DriveOnHeadingCommand extends LoggingCommand {

    private final DriveSubsystem driveSubsystem;
    private long                 startTimeMillis;
    private long                 durationMillis;
    private double               speed;


    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DriveOnHeadingCommand(Rotation2d desiredHeading, double speed, long durationMillis, DriveSubsystem driveSubsystem) {

        this.speed          = speed;
        this.driveSubsystem = driveSubsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        log("boe jiden said this was the initialize");
        logCommandStart();
        startTimeMillis = System.currentTimeMillis();
    }

    // Called every time the scheduler runs while the command is scheduled.


    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // The default drive command never ends, but can be interrupted by other commands.

        return System.currentTimeMillis() - startTimeMillis > durationMillis;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        log("hi, this is the end");
        logCommandEnd(interrupted);
        driveSubsystem.setMotorSpeeds(0, 0);
    }

    @Override
    public void execute() {
        log("hello, this is the execute");
        driveSubsystem.setMotorSpeeds(speed, speed);
    }



}