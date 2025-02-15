package frc.robot.commands.coral;

import frc.robot.Constants;
import frc.robot.Constants.CoralConstants;
import frc.robot.OperatorInput;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;

public class DefaultCoralCommand extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;
    private final OperatorInput  operatorInput;

    public DefaultCoralCommand(CoralSubsystem coralSubsystem, OperatorInput operatorInput) {
        this.coralSubsystem = coralSubsystem;
        this.operatorInput  = operatorInput;

        addRequirements(coralSubsystem);
    }


    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {


        double  elevatorInput = operatorInput.getElevatorInput();
        boolean ejectButton   = operatorInput.getEjectButton();
        boolean injectButton  = operatorInput.getInjectButton();

        // Elevator commands

        // invery Y joystick to ensure +1 is up
        coralSubsystem.setElevatorSpeed(-elevatorInput * Constants.CoralConstants.ELEVATOR_OPERATOR_SCALE_FACTOR);

        double armStick = operatorInput.getArmStick();
        if (Math.abs(armStick) > 0) {
            coralSubsystem.setArmSpeed(armStick * Constants.CoralConstants.ARM_TUNE_RATE);
        }
        else {
            coralSubsystem.setArmSpeed(0);
        }

        // Intake commands

        if (ejectButton) {
            coralSubsystem.setIntakeSpeed(CoralConstants.CORAL_INTAKE_SPEED);
        }
        else if (injectButton) {

            coralSubsystem.setIntakeSpeed(-CoralConstants.CORAL_INTAKE_SPEED);
        }
        // FIXME: This should stop if no buttons are pressed.

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        coralSubsystem.stop();
    }

}