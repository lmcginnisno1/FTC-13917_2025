package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.Constants.armConstants;
import org.firstinspires.ftc.teamcode.GlobalVariables;
import org.firstinspires.ftc.teamcode.RobotContainer;
import org.firstinspires.ftc.teamcode.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.ftclib.command.InstantCommand;
import org.firstinspires.ftc.teamcode.ftclib.command.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.ftclib.command.WaitCommand;

public class CMD_HandleReadyToIntake extends CommandBase {
    RobotContainer m_robot;
    public CMD_HandleReadyToIntake(RobotContainer p_robot){
        m_robot = p_robot;
    }

    @Override
    public void initialize(){
        if(
                m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.HOME)
                        || m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.TRANSITIONING_TO_HOME)
                        || m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.READY_TO_INTAKE)
                        || m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.TRANSITIONING_TO_INTAKE)
                        || m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.INTAKE)
                        || m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.STOW)
                        || m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.TRANSITION_TO_STOW)
            //allowable states continue
        ){}else{
            return;
            //not allowed state, end command
        }

        if(m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.HOME) ||
                m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.STOW)){
            m_robot.schedule(new SequentialCommandGroup(
                    new ParallelCommandGroup(
                            new CMD_ReadyToIntake(m_robot.m_arm, m_robot.m_claw, m_robot.GlobalVariables)
                            ,new InstantCommand(()-> m_robot.GlobalVariables.setRobotState(GlobalVariables.RobotState.TRANSITIONING_TO_INTAKE))
                    ), new InstantCommand(()-> m_robot.GlobalVariables.setRobotState(GlobalVariables.RobotState.READY_TO_INTAKE))
            ));
        }

        if(m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.READY_TO_INTAKE)){
            m_robot.schedule(new SequentialCommandGroup(
                new InstantCommand(()-> m_robot.m_claw.pincherClosed())
                ,new WaitCommand(250)
                ,new InstantCommand(()-> m_robot.m_claw.setPivotHome())
                ,new InstantCommand(()-> m_robot.m_arm.armSetTargetPosition(GlobalVariables.armPositions.INTAKE_WALL))
                ,new InstantCommand(()-> m_robot.GlobalVariables.setRobotState(GlobalVariables.RobotState.TRANSITIONING_TO_INTAKE))
                ,new InstantCommand(()-> m_robot.GlobalVariables.setRobotState(GlobalVariables.RobotState.INTAKE))
            ));
        }

        if(m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.INTAKE)){
            m_robot.schedule(new SequentialCommandGroup(
                    new ParallelCommandGroup(
                        new CMD_StowArm(m_robot.m_arm)
                        ,new InstantCommand(()-> m_robot.GlobalVariables.setRobotState(GlobalVariables.RobotState.TRANSITION_TO_STOW))
                    ),new InstantCommand(()-> m_robot.GlobalVariables.setRobotState(GlobalVariables.RobotState.STOW))
            ));
        }

    }

    @Override
    public boolean isFinished(){
        return Math.abs(m_robot.m_arm.getCurrentPosition()
                - m_robot.m_arm.getTargetPosition()) <= armConstants.tolerance;
    }
}
