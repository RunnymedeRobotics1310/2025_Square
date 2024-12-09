package frc.robot.commands.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;

public class DriveOnHeading extends LoggingCommand {

    private final DriveSubsystem driveSubsystem;

    private long                 startTimeMillis;
    private long                 durationMillis;
    private double               speed;


    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DriveOnHeading(Rotation2d desiredHeading, double speed, long durationMillis, DriveSubsystem driveSubsystem) {

        this.driveSubsystem = driveSubsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }



    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        logCommandStart();
        startTimeMillis = System.currentTimeMillis();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        driveSubsystem.setMotorSpeeds(speed, speed);


    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - startTimeMillis > durationMillis;
        // The default drive command never ends, but can be interrupted by other commands.
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.setMotorSpeeds(0, 0);
    }


}

