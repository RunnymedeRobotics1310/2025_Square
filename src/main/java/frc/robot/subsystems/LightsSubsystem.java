package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CoralConstants.ElevatorHeight;
import frc.robot.Constants.LightsConstants;
import frc.robot.Robot;

public class LightsSubsystem extends SubsystemBase {

    private final AddressableLED ledString = new AddressableLED(LightsConstants.LED_STRING_PWM_PORT);
    private final AddressableLEDBuffer ledBuffer = new AddressableLEDBuffer(LightsConstants.LED_STRING_LENGTH);

    private final AddressableLEDBufferView coralArmBuffer = new AddressableLEDBufferView(ledBuffer, 1, 10);
    private final AddressableLEDBufferView coralElevatorBuffer = new AddressableLEDBufferView(ledBuffer, 11, 20);
    private final AddressableLEDBufferView coralIntakeBuffer = new AddressableLEDBufferView(ledBuffer, 21, 23);

    // RSL Flash
    private static final Color RSL_COLOR = new Color(255, 20, 0);
    private static final AddressableLEDBuffer RSL_ON = new AddressableLEDBuffer(LightsConstants.LED_STRING_LENGTH);
    private static final AddressableLEDBuffer RSL_OFF = new AddressableLEDBuffer(LightsConstants.LED_STRING_LENGTH);
    private Timer simRslTimer = new Timer();
    private boolean simRslState = false;
    private int rslFlashCount = -1;
    private boolean previousRslState = false;

    public LightsSubsystem() {

        // Initialize the RSL flash buffers
        LEDPattern.solid(RSL_COLOR).applyTo(RSL_ON);
        LEDPattern.kOff.applyTo(RSL_OFF);

        // Start the LED string
        ledString.setLength(LightsConstants.LED_STRING_LENGTH);
        ledString.start();
    }

    public void setElevatorHeight(double elevatorHeight) {

        LEDPattern.kOff.applyTo(coralElevatorBuffer);

        // light percentage of lights based on encoders
        int lightCount = (int) (elevatorHeight / Constants.CoralConstants.ELEVATOR_MAX_HEIGHT * coralElevatorBuffer.getLength());

        // light at least one light
        if (lightCount == 0) {
            lightCount = 1;
        }

        for (int i = 0; i < lightCount; i++) {
            coralElevatorBuffer.setLED(i, Color.kAquamarine);
        }
    }

    public void setArmPosition(double armAngle) {

        LEDPattern.kOff.applyTo(coralArmBuffer);

        int lightCount = Math.min(
                (int) (armAngle / 135 * coralArmBuffer.getLength()),
                coralArmBuffer.getLength() - 1);

        // Light at least one light
        if (lightCount == 0) {
            lightCount = 1;
        }

        for (int i = 0; i < lightCount; i++) {
            coralArmBuffer.setLED(i, Color.kAliceBlue);
        }
    }

    public void setRSLFlashCount(int count) {
        rslFlashCount = count;
    }

    @Override
    public void periodic() {

        if (rslFlashCount > 0) {
            flashRSL();
        } else {

            // Update the LEDs on the corners to flash the RSL color
            if (getRSLState()) {
                ledBuffer.setLED(0, RSL_COLOR);
                ledBuffer.setLED(29, RSL_COLOR);
                ledBuffer.setLED(30, RSL_COLOR);
                ledBuffer.setLED(59, RSL_COLOR);
            } else {
                ledBuffer.setLED(0, Color.kBlack);
                ledBuffer.setLED(29, Color.kBlack);
                ledBuffer.setLED(30, Color.kBlack);
                ledBuffer.setLED(59, Color.kBlack);
            }

            // Apply the total view to the LEDs
            ledString.setData(ledBuffer);
        }
    }

    @Override
    public void simulationPeriodic() {

        // Set the simulated RSL state
        if (RobotState.isEnabled()) {
            simRslTimer.start();
            if (simRslTimer.hasElapsed(.5)) {
                simRslState = !simRslState; // toggle the state ever .5 sec
                simRslTimer.restart();
            }
        } else {
            // Solid on when not enabled
            simRslState = true;
        }
    }

    private boolean getRSLState() {

        // Simulate the rsl flash if in simulation
        if (Robot.isSimulation()) {
            return simRslState;
        }
        return RobotController.getRSLState();
    }

    /**
     * Flash all LEDs in the buffer in time with the RSL light for a set number of flashes
     */
    private void flashRSL() {

        boolean rslState = getRSLState();

        // when the RSL goes from on to off, decrement the flash count
        if (!rslState && previousRslState) {
            rslFlashCount--;
        }
        previousRslState = rslState;

        if (rslState) {
            ledString.setData(RSL_ON);
        } else {
            ledString.setData(RSL_OFF);
        }
    }
}
