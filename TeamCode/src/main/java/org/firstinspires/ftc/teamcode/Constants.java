package org.firstinspires.ftc.teamcode;

public class Constants {
    public final static class clawConstants{
        public final static double pincherServoOpen = 0.6;
        public final static double pincherServoClosed = 0.49;
        public final static double pivotServoSubmersible = 0.76;
        public final static double pivotServoReadyToDeploy = 0.4;//0.35
        public final static double pivotServoDeploy = 0.20;//
        public final static double pivotServoWall = 0.5;//0.5
        public final static double pivotServoHome = 0.9;
    }
    public final static class armConstants{
        public final static double home = 0;
        public final static double stow = 0;
        public final static double intakeSubmersible = 215;//100
        public final static double intakeWall = 40;//365, 47
        public final static double readyToIntakeWall = 15;//240, 31
        public final static double lowSample = 87.25;//675
        public final static double highSample = 116.25;//900
        public final static double highSpecimenReadyToDeploy = 100;//850, 110
        public final static double lowSpecimen = 32.3;//250
        public final static double highSpecimen = 77.5;//600
        public final static double tolerance = 1;

        public static final double maxVelocityDegSec = 1800;//90
        public static final double maxAccelerationDegSecSec = 1800;//15
        public static final double kPPR = 2786.2;// Calculated using a 99.5:1 ratio motor
        public static final double kTicksToDegrees = kPPR / 360;
    }
}
