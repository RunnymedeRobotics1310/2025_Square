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
    private double               error;
    private double               turnSpeed;
    private long                 driveingStartTime;
    private boolean              isTurningComplete;


    /**
     * Creates a new ExampleCommand.
     *
     * @param driveSubsystem The subsystem used by this command.
     */
    public DriveOnHeadingCommand(Rotation2d desiredHeading, double speed, long durationMillis, DriveSubsystem driveSubsystem) {

        this.durationMillis = durationMillis;
        this.speed          = speed;
        this.driveSubsystem = driveSubsystem;
        this.desiredHeading = desiredHeading;


        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        logCommandStart();
        startTimeMillis   = System.currentTimeMillis();
        isTurningComplete = false;
    }

    // Called every time the scheduler runs while the command is scheduled.


    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // The default drive command never ends, but can be interrupted by other commands.
        durationMillis = 10000;
        if (System.currentTimeMillis() - driveingStartTime > 2000 && isTurningComplete) {
            return true;
        }
        if (System.currentTimeMillis() - startTimeMillis > durationMillis) {
            return true;
        }
        else {
            return false;
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
        driveSubsystem.setMotorSpeeds(0, 0);
    }

    @Override
    public void execute() {

        error = desiredHeading.minus(driveSubsystem.getHeading()).getDegrees();

        // Normalize the error to the range of -180 to 180
        if (error > 180) {
            error -= 360;
        }
        else if (error < -180) {
            error += 360;
        }

        // Ensure the turn speed is proportional to the error
        turnSpeed = 0.9 * Math.abs(error) / 180;
        turnSpeed = Math.max(turnSpeed, 0.1);

        // Log the desired, current heading, and error
        log("desired: " + desiredHeading.getDegrees());
        log("heading: " + driveSubsystem.getHeading().getDegrees());
        log("Error: " + error);

        // If the robot is at the position, move on heading, if not rotate to heading
        if (turnSpeed > 0.1) {
            if (error > 1 || error < -1) {
                // If the error is positive (meaning we need to turn left), set motor speeds
                // accordingly
                if (error > 0) {
                    // Turn counter-clockwise (left motor negative, right motor positive)
                    driveSubsystem.setMotorSpeeds(-turnSpeed, turnSpeed);
                }
                else {
                    // Turn clockwise (left motor positive, right motor negative)
                    driveSubsystem.setMotorSpeeds(turnSpeed, -turnSpeed);
                }
            }
        }
        else {
            if (error < 1 || error > -1) {
                // If turn is complete, record drive start time
                if (!isTurningComplete) {
                    isTurningComplete = true;
                    driveingStartTime = System.currentTimeMillis();
                }
                // If needed adjust heading left
                if (error > 5) {
                    driveSubsystem.setMotorSpeeds(speed - turnSpeed * 0.5, speed);
                }
                else {
                    // If needed adjust heading right
                    if (error < 0) {
                        driveSubsystem.setMotorSpeeds(speed, speed - turnSpeed * 0.5);
                    }
                    // If going on heading, go at speed
                    else {
                        driveSubsystem.setMotorSpeeds(speed, speed);
                    }
                }
            }
        }

    }



}