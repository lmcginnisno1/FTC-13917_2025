package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.GlobalVariables;
import org.firstinspires.ftc.teamcode.RobotContainer;
import org.firstinspires.ftc.teamcode.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.ftclib.command.InstantCommand;
import org.firstinspires.ftc.teamcode.ftclib.command.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.ftclib.command.WaitCommand;

public class CMD_HandleReadyToDeploy extends CommandBase {
    RobotContainer m_robot;
    public CMD_HandleReadyToDeploy(RobotContainer p_robot){
        m_robot = p_robot;
    }

    @Override
    public void initialize(){
        if(m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.STOW)
            || m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.TRANSITION_TO_STOW)
            || m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.TRANSITIONING_TO_HOME)
            || m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.READY_TO_DEPLOY)
            || m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.TRANSITIONING_TO_DEPLOY)
        ){}else{return;}

        if(m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.STOW)){
            m_robot.schedule(new SequentialCommandGroup(
                    new ParallelCommandGroup(
                            new CMD_ReadyToDeploy(m_robot.m_claw, m_robot.m_arm, m_robot.GlobalVariables)
                            ,new InstantCommand(()-> m_robot.GlobalVariables.setRobotState(GlobalVariables.RobotState.TRANSITIONING_TO_DEPLOY))
                    ),new InstantCommand(()-> m_robot.GlobalVariables.setRobotState(GlobalVariables.RobotState.READY_TO_DEPLOY))
            ));
        }

        if(m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.READY_TO_DEPLOY)){
            m_robot.schedule(new SequentialCommandGroup(
                    new ParallelCommandGroup(
                        new CMD_Deploy(m_robot.m_arm, m_robot.GlobalVariables)
                        ,new InstantCommand(()-> m_robot.GlobalVariables.setRobotState(GlobalVariables.RobotState.TRANSITIONING_TO_DEPLOY))
                        ,new SequentialCommandGroup(
                            new WaitCommand(250)
                            ,new InstantCommand(()-> m_robot.m_claw.pivotSetDeploy())
                        )
                    )
                    ,new InstantCommand(()-> m_robot.GlobalVariables.setRobotState(GlobalVariables.RobotState.TRANSITIONING_TO_HOME))
            ));
        }

        if(m_robot.GlobalVariables.isRobotState(GlobalVariables.RobotState.TRANSITIONING_TO_HOME)){
            m_robot.schedule(new SequentialCommandGroup(
                    new InstantCommand(()-> m_robot.m_claw.pincherOpen())
                    ,new WaitCommand(1000)
                    ,new InstantCommand(()-> m_robot.m_claw.pincherClosed())
                    ,new InstantCommand(()-> m_robot.m_claw.setPivotHome())
                    ,new CMD_StowArm(m_robot.m_arm)
                    ,new InstantCommand(()-> m_robot.GlobalVariables.setRobotState(GlobalVariables.RobotState.HOME))
            ));
        }
    }

    @Override
    public boolean isFinished(){
        return Math.abs(m_robot.m_arm.getCurrentPosition()
                - m_robot.m_arm.getTargetPosition()) <= Constants.armConstants.tolerance;
    }
}
