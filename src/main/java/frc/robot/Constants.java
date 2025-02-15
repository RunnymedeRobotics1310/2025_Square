// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose.
 * <p>
 * All constants should be declared globally (i.e. public static). <br>
 * Do not put anything functional in this class.
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final double DEFAULT_COMMAND_TIMEOUT_SECONDS = 5;

    public static final class OperatorInputConstants {

        public static final int    DRIVER_CONTROLLER_PORT   = 0;
        public static final int    OPERATOR_CONTROLLER_PORT = 1;
        public static final double CONTROLLER_DEADBAND      = .2;
    }

    public static final class AutoConstants {

        public static enum AutoPattern {
            DO_NOTHING, DRIVE_FORWARD, BOX
        }
    }

    public static final class DriveConstants {

        public static enum DriveMode {
            TANK, ARCADE, SINGLE_STICK_LEFT, SINGLE_STICK_RIGHT;
        }

        // NOTE: Follower motors are at CAN_ID+1
        public static final int     LEFT_MOTOR_CAN_ID    = 10;
        public static final int     RIGHT_MOTOR_CAN_ID   = 20;

        public static final boolean LEFT_MOTOR_INVERTED  = false;
        public static final boolean RIGHT_MOTOR_INVERTED = true;

        public static final double  CM_PER_ENCODER_COUNT = 3.503;

        public static final boolean GYRO_INVERTED        = false;

        /**
         * Proportional gain for gyro pid tracking
         */
        public static final double  GYRO_PID_KP          = 0.01;

        public static final double  DRIVE_SCALING_BOOST  = 1;
        public static final double  DRIVE_SCALING_NORMAL = .6;
        public static final double  DRIVE_SCALING_SLOW   = .3;
    }

    public static final class CoralConstants {

        public enum ElevatorHeight {
            LEVEL_0(0),
            LEVEL_0_5(-1), // Transit phase, no encoder count
            LEVEL_1(5),
            LEVEL_1_5(-1), // Transit phase, no encoder count
            LEVEL_2(10),
            LEVEL_2_5(-1), // Transit phase, no encoder count
            LEVEL_3(15),
            LEVEL_3_5(-1), // Transit phase, no encoder count
            LEVEL_4(20),
            LEVEL_4_5(-1); // Transit phase, no encoder count

            public final double encoderCount;

            ElevatorHeight(double encoderCount) {
                this.encoderCount = encoderCount;
            }
        }

        public static final double  ELEVATOR_TOLERANCE            = 2.5;
        public static final double  ELEVATOR_P                    = 0.05;

        // placeholder value for speed of elevator
        public static final double  ELEVATOR_MAX_SPEED            = 0.5;
        public static final double  ELEVATOR_TUNE_MAX_SPEED       = 0.1;

        public static final int     ELEVATOR_MOTOR_CAN_ID         = 30;
        public static final int     ARM_MOTOR_CAN_ID              = 31;
        public static final int     INTAKE_MOTOR_CAN_ID           = 32;

        public static final boolean ELEVATOR_MOTOR_INVERTED       = false;
        public static final boolean ARM_MOTOR_INVERTED            = false;
        public static final boolean INTAKE_MOTOR_INVERTED         = false;

        public static final boolean ARM_POSITION_ENCODER_INVERTED = false;
        public static final double  ARM_LOWER_LIMIT_POSITION      = 0;
        public static final double  ARM_UPPER_LIMIT_POSITION      = 180;
        public static final double  ARM_TUNE_RATE                 = 0.2;

        public static final double  CORAL_INTAKE_SPEED            = 0.5;
        public static final double  CORAL_OUTAKE_SPEED            = 0.8;
        public static final int     PLANT_ROTATIONS               = 10;   // FIXME: How many
        // rotations

    }

    public static final class LightsConstants {

        public static final int LED_STRING_PWM_PORT = 9;
        public static final int LED_STRING_LENGTH   = 60;
    }


}
