package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ftclib.command.InstantCommand;
import org.firstinspires.ftc.teamcode.ftclib.command.SequentialCommandGroup;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class Robot_Auto extends LinearOpMode {

     public RobotContainer m_robot;
     public int m_Analysis;

     private boolean m_redAlliance;

     private Pose2d m_startingPose = new Pose2d(0, 0, 0);
     SequentialCommandGroup tasks;

     private ElapsedTime m_runTime = new ElapsedTime();

     public void initialize() {
          telemetry.clearAll();
          telemetry.addData("init complete", "BaseRobot");
     }

     @Override
     public void runOpMode() throws InterruptedException {
          initializeSubsystems();

          prebuildTasks();
          // waitForStart();
          while (!opModeIsActive() && !isStopRequested()) {
               m_robot.run(); // run the scheduler
               telemetry.addData("Analysis: ", 1);
               telemetry.update();
          }

//          m_Analysis = m_robot.m_autonomousDetect.getSelected();
//          m_robot.m_backCamera.setProcessorDisabled(m_robot.m_autonomousDetect); // shutdown auto detect visual processor
//        m_Analysis = 1; // override for testing

          buildTasks(m_Analysis);

          m_runTime.reset();

          while (!isStopRequested() && opModeIsActive()) {
               m_robot.run(); // run the scheduler

               m_robot.drivetrain.update();
               Pose2d poseEstimate = m_robot.drivetrain.getPoseEstimate();
               telemetry.addData("ODM","x[%3.2f] y[%3.2f] heading(%3.2f)", poseEstimate.getX(), poseEstimate.getY(), Math.toDegrees(poseEstimate.getHeading()));
               telemetry.update();
          }

          GlobalVariables.m_autonomousEndPose = m_robot.drivetrain.getPoseEstimate();
          endOfOpMode();
          m_robot.reset();
     }

     public void endOfOpMode() {

     }

     public void initializeSubsystems() {
          m_robot = new RobotContainer(this);
     }

     public void setStartingPose(Pose2d p_pose) {
          m_startingPose = p_pose;
          m_robot.drivetrain.setPoseEstimate(m_startingPose);
     }

     public Pose2d getStartingPose() {
          return m_startingPose;
     }

     public abstract SequentialCommandGroup buildTasks(int m_Analysis);
     public abstract void prebuildTasks();

}