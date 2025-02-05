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
    private boolean              driveStart;
    private double               driveTime;


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
        durationTime = 30000;
        driveStart   = false;
        driveTime    = 99999999;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        angleDifferance = ((desiredHeading.getDegrees() - (driveSubsystem.getHeading().getDegrees() % 360)) % 360);

        log("angle differance" + angleDifferance);
        log("speed value" + angleDifferance / 180);

        if (!driveStart) {
            speed = Math.max((angleDifferance / 180) * .3, 0.15);
            if (Math.abs(angleDifferance) < 15) {
                speed = 0.15;
            }

            if (angleDifferance > 180) {
                driveSubsystem.setMotorSpeeds(speed, -speed);
            }
            else if (angleDifferance <= 180) {
                driveSubsystem.setMotorSpeeds(-speed, speed);
            }
            else if (angleDifferance < 0.1) {
                driveTime  = System.currentTimeMillis();
                driveStart = true;
            }
        }
        else {
            speed = 0.5;
            driveSubsystem.setMotorSpeeds(speed, speed);
        }

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // The default drive command never ends, but can be interrupted by other commands.
        return System.currentTimeMillis() - startTime > durationTime || System.currentTimeMillis() - driveTime > 3000;


    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
        driveSubsystem.setMotorSpeeds(0, 0);
    }

}