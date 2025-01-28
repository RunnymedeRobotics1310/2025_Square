package frc.robot.commands.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.DriveSubsystem;

public class DriveOnHeadingCommand extends LoggingCommand {

    private final DriveSubsystem driveSubsystem;

    private long                 startTimeMillis;
    private long                 durationMillis;
    private double               maxSpeed;
    private double               speed;
    private Rotation2d           desiredHeading;

    private double               angleDistance;
    private double               effectiveSpeed;
    private long                 driveDurationMillis;
    private boolean              startDrive;


    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DriveOnHeadingCommand(Rotation2d desiredHeading, double maxSpeed, long durationMillis, DriveSubsystem driveSubsystem) {

        this.driveSubsystem = driveSubsystem;
        this.maxSpeed       = speed;
        this.durationMillis = durationMillis;
        this.desiredHeading = desiredHeading;
        this.startDrive     = startDrive;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }


    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        log("Start!");
        logCommandStart();
        startTimeMillis = System.currentTimeMillis();
        durationMillis  = 4000;
        startDrive      = false;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        if (!startDrive) {
            angleDistance = (desiredHeading.getDegrees() - driveSubsystem.getHeading().getDegrees())
                % 360;
            maxSpeed      = 1.5;

            if (angleDistance > 180) {
                angleDistance -= 360;
            }

            else if (angleDistance < -180) {
                angleDistance += 360;

            }

            effectiveSpeed = maxSpeed * (((Math.abs(angleDistance)) / 180));

            if (Math.abs(angleDistance) < 20) {
                effectiveSpeed *= 0.1;

            }

            if (Math.abs(angleDistance) < 4) {
                startDrive = true;
            }
            if (angleDistance > 0) {
                driveSubsystem.setMotorSpeeds(-effectiveSpeed, effectiveSpeed);
            }

            else {
                driveSubsystem.setMotorSpeeds(effectiveSpeed, -effectiveSpeed);
            }
        }

        if (startDrive == true) {
            effectiveSpeed = 0.2;

            if (System.currentTimeMillis() - startTimeMillis >= durationMillis / 2) {
                driveSubsystem.setMotorSpeeds(effectiveSpeed, effectiveSpeed);
            }
        }



        log("START DRIVE START DRIVE START DRIVE " + startDrive);
        log("EFFCTIVE SPEED: " + effectiveSpeed);


    }



    // *
    // driveSubsystem.setMotorSpeeds(-1, -1);
    // Syst



    @Override
    public boolean isFinished() {

        if (System.currentTimeMillis() - startTimeMillis > durationMillis) {
            log("isFinished called");
            return true;
        }
        return false;
        // The default drive command never ends, but can be interrupted by other commands.
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        log("end");
        logCommandEnd(interrupted);
        driveSubsystem.setMotorSpeeds(0, 0);
    }


}

