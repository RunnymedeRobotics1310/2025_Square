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

        // Elevator Heights in encoder counts
        public enum ElevatorHeight {
            COMPACT(0),
            INTAKE(0),
            LEVEL_1(5),
            LEVEL_2(10),
            LEVEL_3(15),
            LEVEL_4(20),
            REMOVE_LOW_ALGAE(12),
            REMOVE_HIGH_ALGAE(17);

            public final double encoderCount;

            ElevatorHeight(double encoderCount) {
                this.encoderCount = encoderCount;
            }
        }

        // Arm Angles in degrees
        public enum ArmAngle {
            COMPACT(0),
            INTAKE(37),
            LEVEL_1(0),
            LEVEL_2(120),
            LEVEL_3(120),
            LEVEL_4(100),
            REMOVE_ALGAE(40);

            public final double angle;

            ArmAngle(double angle) {
                this.angle = angle;
            }
        }

        public enum CoralPose {

            COMPACT(ElevatorHeight.COMPACT, ArmAngle.COMPACT),
            INTAKE(ElevatorHeight.INTAKE, ArmAngle.INTAKE),
            SCORE_L1(ElevatorHeight.LEVEL_1, ArmAngle.LEVEL_1),
            SCORE_L2(ElevatorHeight.LEVEL_2, ArmAngle.LEVEL_2),
            SCORE_L3(ElevatorHeight.LEVEL_3, ArmAngle.LEVEL_3),
            SCORE_L4(ElevatorHeight.LEVEL_4, ArmAngle.LEVEL_4),
            REMOVE_LOW_ALGAE(ElevatorHeight.REMOVE_LOW_ALGAE, ArmAngle.REMOVE_ALGAE),
            REMOVE_HIGH_ALGAE(ElevatorHeight.REMOVE_HIGH_ALGAE, ArmAngle.REMOVE_ALGAE);

            public final ElevatorHeight elevatorHeight;
            public final ArmAngle       armAngle;

            CoralPose(ElevatorHeight elevatorHeight, ArmAngle armAngle) {
                this.elevatorHeight = elevatorHeight;
                this.armAngle       = armAngle;
            }
        }

        /*
         * Motor CAN IDs and inversions
         */
        public static final int     ELEVATOR_MOTOR_CAN_ID      = 40;
        public static final int     ARM_MOTOR_CAN_ID           = 41;
        public static final int     INTAKE_MOTOR_CAN_ID        = 42;

        public static final boolean ELEVATOR_MOTOR_INVERTED    = true;
        public static final boolean ARM_MOTOR_INVERTED         = false;
        public static final boolean INTAKE_MOTOR_INVERTED      = false;

        /*
         * Elevator Constants
         */
        public static final double  ELEVATOR_MAX_SPEED         = 0.7;
        public static final double  ELEVATOR_MAX_HEIGHT        = 180;

        public static final double  ELEVATOR_TOLERANCE         = 2.5;
        public static final double  ELEVATOR_P                 = 0.05;

        // Maximum manual tuning speed
        public static final double  ELEVATOR_TUNE_MAX_SPEED    = 0.2;

        // Safety constants near the limits
        public static final double  ELEVATOR_SLOW_ZONE_SPEED   = 0.05;
        public static final double  ELEVATOR_SLOW_ZONE         = 10;       // encoder counts

        /*
         * Arm Constants
         */
        public static final double  ARM_MAX_SPEED              = 0.6;

        public static final double  ARM_LOWER_LIMIT_POSITION   = 0;
        public static final double  ARM_UPPER_LIMIT_POSITION   = 126;

        public static final boolean ARM_ANGLE_ENCODER_INVERTED = false;
        // Set the encoder offset so that the encoder reads 0.1 rotations against the hard stop
        // This is so that the angle can go negative instead of back to 360 deg when slightly
        // less than zero. This constant was read off the REV Hardware Client Absolute Encoder page
        public static final double  ARM_ANGLE_ENCODER_OFFSET   = 0.0316152;

        // Maximum manual tuning speed
        public static final double  ARM_TUNE_MAX_SPEED         = 0.2;

        // Pseudo PID and safe zone constants
        public static final double  ARM_ANGLE_TOLERANCE        = 1.5;

        public static final double  ARM_FAST_SPEED             = 0.4;
        public static final double  ARM_SLOW_ZONE_SPEED        = 0.15;
        public static final double  ARM_SLOW_ZONE_ANGLE        = 10;

        /*
         * Intake Constants
         */
        public static final double  CORAL_INTAKE_SPEED         = 0.5;
        public static final double  CORAL_OUTAKE_SPEED         = 0.8;
        public static final double  PLANT_ROTATIONS            = 15;
        public static final double  INTAKE_ROTATIONS           = 8;
    }

    public static final class LightsConstants {

        public static final int LED_STRING_PWM_PORT = 9;
        public static final int LED_STRING_LENGTH   = 60;
    }


}
