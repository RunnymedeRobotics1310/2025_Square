package frc.robot.commands.coral;

import frc.robot.Constants;
import frc.robot.OperatorInput;
import frc.robot.commands.LoggingCommand;
import frc.robot.subsystems.CoralSubsystem;

public class DefaultCoralCommand extends LoggingCommand {

    private final CoralSubsystem coralSubsystem;
    private final OperatorInput  operatorInput;

    public DefaultCoralCommand(CoralSubsystem coralSubsystem, OperatorInput operatorInput) {
        this.coralSubsystem = coralSubsystem;
        this.operatorInput  = operatorInput;
    }


    @Override
    public void initialize() {
        logCommandStart();
    }

    @Override
    public void execute() {

        // FIXME: Don't make up new names
        // is it elevatorStick or elevatorInput - what is the difference?

        double elevatorStick = operatorInput.getElevatorInput();

        // FIXME: by convention all inputs should be positive? why negate the stick?
        // Does this need a comment?
        coralSubsystem.setElevatorSpeed(-elevatorStick * Constants.CoralConstants.ELEVATOR_OPERATOR_SCALE_FACTOR);

        double armStick = operatorInput.getArmStick();
        if (Math.abs(armStick) > 0) {
            coralSubsystem.setArmSpeed(armStick * Constants.CoralConstants.ARM_TUNE_RATE);
        }
        else {
            coralSubsystem.setArmSpeed(0);
        }

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