package frc.robot.commands.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;

public class DriveOnHeadingCommand extends LoggingCommand {

    private final DriveSubsystem driveSubsystem;

    private long                 startTimeMillis;
    private long                 durationMillis;
    private double               speed;
    private Rotation2d           desiredHeading;


    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DriveOnHeadingCommand(Rotation2d desiredHeading, double speed, long durationMillis, DriveSubsystem driveSubsystem) {

        this.driveSubsystem = driveSubsystem;
        this.speed          = speed;
        this.durationMillis = durationMillis;
        this.desiredHeading = desiredHeading;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }


    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        log("3,2,1 start");
        logCommandStart();
        startTimeMillis = System.currentTimeMillis();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // log("execution");

        if ((desiredHeading.getDegrees() - driveSubsystem.getHeading().getDegrees()) % 360 > 0) {
            driveSubsystem.setMotorSpeeds(1, -1);
            System.out.println(driveSubsystem.getHeading().getDegrees());
        }
        else /*
              * ((desiredHeading.getDegrees() - driveSubsystem.getHeading().getDegrees()) % 360 < 0)
              */ {
            driveSubsystem.setMotorSpeeds(-1, 1);
            System.out.println(driveSubsystem.getHeading().getDegrees());
        }


    }


    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (System.currentTimeMillis() - startTimeMillis > durationMillis) {
            log("Hyhhhhhhhh");
            return true;
        }
        return false;
        // The default drive command never ends, but can be interrupted by other commands.
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        log("what's up this is the end");
        logCommandEnd(interrupted);
        driveSubsystem.setMotorSpeeds(0, 0);
    }


}

