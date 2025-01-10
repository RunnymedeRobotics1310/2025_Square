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
        startTimeMillis = System.currentTimeMillis();
    }

    // Called every time the scheduler runs while the command is scheduled.


    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // The default drive command never ends, but can be interrupted by other commands.
        durationMillis = 10000;
        return System.currentTimeMillis() - startTimeMillis > durationMillis;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        logCommandEnd(interrupted);
        driveSubsystem.setMotorSpeeds(0, 0);
    }

    @Override
    public void execute() {



        // error = (desiredHeading.getDegrees() - driveSubsystem.getHeading().getDegrees()) % 360;

        // turnSpeed = 0.75 * Math.abs(error) / 180;
        // // turnSpeed = Math.max(turnSpeed, 0.1);

        // log("desired" + desiredHeading.getDegrees());
        // log("heading" + driveSubsystem.getHeading().getDegrees() % 360);

        // if ((desiredHeading.getDegrees() - driveSubsystem.getHeading().getDegrees() % 360) < 180)
        // {
        // driveSubsystem.setMotorSpeeds(-turnSpeed, turnSpeed);
        // }
        // else {
        // driveSubsystem.setMotorSpeeds(turnSpeed, -turnSpeed);
        // }

        // log("error = " + error);
        // log("speed = " + turnSpeed);


        error = desiredHeading.minus(driveSubsystem.getHeading()).getDegrees();

        // Ensure the turn speed is proportional to the error, capped between 0.1 and 0.75
        double turnSpeed = 0.75 * Math.abs(error) / 180;
        turnSpeed = Math.max(turnSpeed, 0.15);

        // Log the desired and current heading
        log("desired: " + desiredHeading.getDegrees());
        log("heading: " + driveSubsystem.getHeading().getDegrees());

        // If the error is positive (meaning we need to turn left), set motor speeds accordingly
        if (Math.abs(error) > 180) {
            // Turn in one direction
            driveSubsystem.setMotorSpeeds(-turnSpeed, turnSpeed); // Left motor negative, Right
            // motor positive
        }
        else {
            // Turn in the opposite direction
            driveSubsystem.setMotorSpeeds(turnSpeed, -turnSpeed); // Left motor positive, Right
            // motor negative
        }

    }



}