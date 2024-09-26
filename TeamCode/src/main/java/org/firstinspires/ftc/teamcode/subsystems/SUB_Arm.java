package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.GlobalVariables.armPositions;
import org.firstinspires.ftc.teamcode.Constants.armConstants;
import org.firstinspires.ftc.teamcode.ftclib.command.TrapezoidProfileSubsystem;
import org.firstinspires.ftc.teamcode.ftclib.first.math.trajectory.TrapezoidProfile;

@Config
public class SUB_Arm extends TrapezoidProfileSubsystem {
    //variables, must be created here to use later
    DcMotorEx m_rightShoulderMotor;
    DcMotorEx m_leftShoulderMotor;
    OpMode m_opMode;
    TrapezoidProfile.Constraints m_constraints = new TrapezoidProfile.Constraints(
        armConstants.maxVelocityDegSec
        ,armConstants.maxAccelerationDegSecSec
    );

    TrapezoidProfile m_profile = new TrapezoidProfile(m_constraints);
    TrapezoidProfile.State m_state = new TrapezoidProfile.State(0, 0);
    TrapezoidProfile.State m_goal = new TrapezoidProfile.State(0, 0);
    public SUB_Arm (OpMode p_opMode){
        super (
            new TrapezoidProfile.Constraints(
                armConstants.maxVelocityDegSec
                ,armConstants.maxAccelerationDegSecSec
            ), 0
        );
        //set variables to use later
        m_opMode = p_opMode;
        m_rightShoulderMotor = p_opMode.hardwareMap.get(DcMotorEx.class, "rightShoulderMotor");
        m_rightShoulderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m_rightShoulderMotor.setVelocityPIDFCoefficients(10, 7.5, 0, 14);
        m_rightShoulderMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_rightShoulderMotor.setTargetPosition(m_rightShoulderMotor.getCurrentPosition());
        m_rightShoulderMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(10, 7.5, .5,0));
        m_rightShoulderMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION,
                new PIDFCoefficients(5, 0, 0, 0));
        m_rightShoulderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m_rightShoulderMotor.setPower(1);

        m_leftShoulderMotor = p_opMode.hardwareMap.get(DcMotorEx.class, "leftShoulderMotor");
        m_leftShoulderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m_leftShoulderMotor.setVelocityPIDFCoefficients(10, 7.5, 0, 14);
        m_leftShoulderMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m_leftShoulderMotor.setTargetPosition(m_leftShoulderMotor.getCurrentPosition());
        m_leftShoulderMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(10, 7.5, .5,0));
        m_leftShoulderMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION,
                new PIDFCoefficients(5, 0, 0, 0));
        m_leftShoulderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m_leftShoulderMotor.setPower(1);
        enable();
    }


    protected void useState(TrapezoidProfile.State setpoint) {
        int tickPosition = (int)(setpoint.position * armConstants.kTicksToDegrees);
        m_rightShoulderMotor.setTargetPosition(tickPosition);
        m_leftShoulderMotor.setTargetPosition(tickPosition);
    }

    public void armSetTargetPosition(armPositions p_armPositions){
        switch(p_armPositions){
            case HOME:
                setTargetAngle(armConstants.home);
                break;
            case STOW:
                setTargetAngle(armConstants.stow);
                break;
            case LOW_SAMPLE:
                setTargetAngle(armConstants.lowSample);
                break;
            case HIGH_SPECIMEN:
                setTargetAngle(armConstants.highSpecimen);
                break;
            case HIGH_SPECIMEN_READY_TO_DEPLOY:
                setTargetAngle(armConstants.highSpecimenReadyToDeploy);
                break;
            case LOW_SPECIMEN:
                setTargetAngle(armConstants.lowSpecimen);
                break;
            case INTAKE_WALL:
                setTargetAngle(armConstants.intakeWall);
                break;
            case READY_TO_INTAKE_WALL:
                setTargetAngle(armConstants.readyToIntakeWall);
                break;
            case INTAKE_SUBMERSIBLE:
                setTargetAngle(armConstants.intakeSubmersible);
        }
    }
    
    public double getCurrentPosition(){
        return m_rightShoulderMotor.getCurrentPosition() / armConstants.kTicksToDegrees;
    }

    public double getTargetPosition(){
        return m_goal.position;
    }

    public void setTargetAngle(double degree) {
        m_goal = new TrapezoidProfile.State(degree, 0);
    }


    @Override
    public void periodic(){
        m_opMode.telemetry.addData("Shoulder Target Pos", getTargetPosition());
        m_opMode.telemetry.addData("Shoulder Current Pos", getCurrentPosition());

        m_state = m_profile.calculate(0.02, m_goal, m_state);
        useState(m_state);
    }
}