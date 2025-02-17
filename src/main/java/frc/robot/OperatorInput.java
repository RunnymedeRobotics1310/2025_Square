package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.AutoConstants.AutoPattern;
import frc.robot.Constants.DriveConstants.DriveMode;
import frc.robot.Constants.OperatorInputConstants;
import frc.robot.commands.CancelCommand;
import frc.robot.commands.GameController;
import frc.robot.commands.coral.arm.MoveArmToAngleCommand;
import frc.robot.commands.coral.elevator.MoveToHeightCommand;
import frc.robot.commands.coral.intake.IntakeCoralCommand;
import frc.robot.commands.coral.intake.PlantCoralCommand;
import frc.robot.commands.test.SystemTestCommand;
import frc.robot.subsystems.CoralSubsystem;

/**
 * The DriverController exposes all driver functions
 * <p>
 * Extend SubsystemBase in order to have a built in periodic call to support SmartDashboard updates
 */
public class OperatorInput extends SubsystemBase {

    private final GameController driverController;
    // private final GameController operatorController;

    // Auto Setup Choosers
    SendableChooser<AutoPattern> autoPatternChooser = new SendableChooser<>();
    SendableChooser<Integer>     waitTimeChooser    = new SendableChooser<>();
    SendableChooser<DriveMode>   driveModeChooser   = new SendableChooser<>();

    /**
     * Construct an OperatorInput class that is fed by a DriverController and optionally an
     * OperatorController.
     */
    public OperatorInput() {

        driverController = new GameController(OperatorInputConstants.DRIVER_CONTROLLER_PORT,
            OperatorInputConstants.CONTROLLER_DEADBAND);

        // Initialize the dashboard selectors
        autoPatternChooser.setDefaultOption("Do Nothing", AutoPattern.DO_NOTHING);
        SmartDashboard.putData("Auto Pattern", autoPatternChooser);
        autoPatternChooser.addOption("Drive Forward", AutoPattern.DRIVE_FORWARD);
        autoPatternChooser.addOption("Box", AutoPattern.BOX);

        waitTimeChooser.setDefaultOption("No wait", 0);
        SmartDashboard.putData("Auto Wait Time", waitTimeChooser);
        waitTimeChooser.addOption("1 second", 1);
        waitTimeChooser.addOption("3 seconds", 3);
        waitTimeChooser.addOption("5 seconds", 5);

        driveModeChooser.setDefaultOption("Arcade", DriveMode.ARCADE);
        SmartDashboard.putData("Drive Mode", driveModeChooser);
        driveModeChooser.addOption("Tank", DriveMode.TANK);
        driveModeChooser.addOption("Single Stick (L)", DriveMode.SINGLE_STICK_LEFT);
        driveModeChooser.addOption("Single Stick (R)", DriveMode.SINGLE_STICK_RIGHT);
    }

    /**
     * Configure the button bindings for all operator commands
     * <p>
     * NOTE: This routine requires all subsystems to be passed in
     * <p>
     * NOTE: This routine must only be called once from the RobotContainer
     *
     * @param driveSubsystem
     */
    public void configureButtonBindings(CoralSubsystem coralSubsystem) {

        // System Test Command
        new Trigger(() -> driverController.getStartButton() && driverController.getBackButton())
            .onTrue(new SystemTestCommand(this, coralSubsystem));

        // Cancel Command - cancels all running commands on all subsystems
        new Trigger(() -> isCancel())
            .onTrue(new CancelCommand(this, coralSubsystem));

        // Gyro and Encoder Reset
        new Trigger(() -> driverController.getBackButton())
            .onTrue(new InstantCommand(() -> {
                // FIXME: reset encoders and gyro
            }));

        // FIXME: Replace these button bindings the ones provided in the controller map

        /*
         * Elevator Buttons
         */
        // Configure the DPAD to set elevator height
        new Trigger(() -> driverController.getPOV() == 0)
            .onTrue(new MoveToHeightCommand(coralSubsystem, Constants.CoralConstants.ElevatorHeight.LEVEL_1));

        new Trigger(() -> driverController.getPOV() == 90)
            .onTrue(new MoveToHeightCommand(coralSubsystem, Constants.CoralConstants.ElevatorHeight.LEVEL_2));

        new Trigger(() -> driverController.getPOV() == 180)
            .onTrue(new MoveToHeightCommand(coralSubsystem, Constants.CoralConstants.ElevatorHeight.LEVEL_3));

        new Trigger(() -> driverController.getPOV() == 270)
            .onTrue(new MoveToHeightCommand(coralSubsystem, Constants.CoralConstants.ElevatorHeight.LEVEL_4));

        new Trigger(() -> driverController.getXButton())
            .onTrue(new SequentialCommandGroup(
                new MoveArmToAngleCommand(Constants.CoralConstants.ARM_POSITION_COMPACT, coralSubsystem),
                new MoveToHeightCommand(coralSubsystem, Constants.CoralConstants.ElevatorHeight.LEVEL_0)));


        /*
         * Arm Buttons
         */
        // Configure the controller buttons X (resting, above^),
        // Y (delivery), A (intake) for arm position


        // triggers/buttons
        new Trigger(() -> driverController.getYButton())
            .onTrue(new MoveArmToAngleCommand(Constants.CoralConstants.ARM_POSITION_LEVEL_2, coralSubsystem));

        new Trigger(() -> driverController.getAButton())
            .onTrue(new MoveArmToAngleCommand(Constants.CoralConstants.ARM_POSITION_INTAKE, coralSubsystem));

        /*
         * Coral Intake Buttons
         */

        // Intake Coral
        new Trigger(() -> driverController.getLeftTriggerAxis() > 0.5)
            .onTrue(new IntakeCoralCommand(coralSubsystem));

        // Plant Coral
        new Trigger(() -> driverController.getRightTriggerAxis() > 0.5)
            .onTrue(new PlantCoralCommand(coralSubsystem));
    }

    /*
     * Auto Pattern Selectors
     */
    public AutoPattern getAutoPattern() {
        return autoPatternChooser.getSelected();
    }

    public Integer getAutoDelay() {
        return waitTimeChooser.getSelected();
    }

    /*
     * Cancel Command support
     * Do not end the command while the button is pressed
     */
    public boolean isCancel() {
        return driverController.getStartButton();
    }

    /*
     * The following routines are used by the default commands for each subsystem
     *
     * They allow the default commands to get user input to manually move the
     * robot elements.
     */
    /*
     * Default Coral Command
     */
    public double getElevatorInput() {
        return driverController.getRightY();
    }

    public double getArmStick() {
        return driverController.getLeftY();
    }

    public boolean getEjectButton() {
        return driverController.getRightBumperButton();
    }

    public boolean getInjectButton() {
        return driverController.getLeftBumperButton();
    }

    /*
     * Support for haptic feedback to the driver
     */
    public void startVibrate() {
        driverController.setRumble(GenericHID.RumbleType.kBothRumble, 1);
    }

    public void stopVibrate() {
        driverController.setRumble(GenericHID.RumbleType.kBothRumble, 0);
    }


    @Override
    public void periodic() {
        SmartDashboard.putString("Driver Controller", driverController.toString());
    }

    public GameController getDriverController() {
        return driverController;
    }
}
