package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class GlobalVariables {
     public static Pose2d m_autonomousEndPose = new Pose2d();
     public enum armPositions {
          HOME
          ,INTAKE_GROUND
          ,INTAKE_SUBMERSIBLE
          ,READY_TO_INTAKE_WALL
          ,INTAKE_WALL
          ,STOW
          ,LOW_SAMPLE
          ,LOW_SPECIMEN
          ,HIGH_SPECIMEN
          ,HIGH_SPECIMEN_READY_TO_DEPLOY
     }

     public enum RobotState {
          HOME
          ,TRANSITIONING_TO_INTAKE
          ,READY_TO_INTAKE
          ,INTAKE
          ,TRANSITION_TO_STOW
          ,STOW
          ,TRANSITIONING_TO_DEPLOY
          ,READY_TO_DEPLOY
          ,DEPLOY
          ,TRANSITIONING_TO_HOME
          ,READY_TO_clIMB
          ,CLIMB
     }

     RobotState m_robotState = RobotState.HOME;

     public RobotState getRobotState(){
          return m_robotState;
     }

     public void setRobotState(RobotState p_robotState){
          m_robotState = p_robotState;
     }

     public boolean isRobotState(RobotState p_robotState){
          return m_robotState == p_robotState;
     }

     public enum IntakeState{
          WALL
          ,SUBMERSIBLE
     }

     IntakeState m_intakeState = IntakeState.WALL;

     public IntakeState getIntakeState(){
          return m_intakeState;
     }

     public void setIntakeState(IntakeState p_IntakeState){
          m_intakeState = p_IntakeState;
     }

     public boolean isIntakeState(IntakeState p_IntakeState){
          return m_intakeState == p_IntakeState;
     }

     public enum DeployState{
          HIGH_SPECIMEN
          ,LOW_SPECIMEN
          ,LOW_BASKET
     }

     DeployState m_deployState = DeployState.HIGH_SPECIMEN;

     public DeployState getDeployState(){
          return m_deployState;
     }

     public void setDeployState(DeployState p_deployState){
          m_deployState = p_deployState;
     }

     public boolean isDeployState(DeployState p_deployState){
          return m_deployState == p_deployState;
     }

}
