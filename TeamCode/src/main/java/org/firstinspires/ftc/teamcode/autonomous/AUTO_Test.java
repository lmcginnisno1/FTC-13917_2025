package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot_Auto;
import org.firstinspires.ftc.teamcode.commands.RR_TrajectoryFollowerCommand;
import org.firstinspires.ftc.teamcode.ftclib.command.Command;
import org.firstinspires.ftc.teamcode.ftclib.command.InstantCommand;
import org.firstinspires.ftc.teamcode.ftclib.command.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.ftclib.command.SequentialCommandGroup;

@Autonomous(name = "Auto Test", group = "Auto Test")
public class AUTO_Test extends Robot_Auto {
     int m_Analysis;

     @Override
     public void prebuildTasks() {
          //run these tasks now
          setStartingPose(new Pose2d(0, 0, Math.toRadians(0)));
     }

     @Override
     public SequentialCommandGroup buildTasks(int p_Analysis) {
          SequentialCommandGroup completeTasks = new SequentialCommandGroup();
          completeTasks.addCommands(
             testRoutine()
          );
          m_robot.schedule(completeTasks);
          return completeTasks;
     }

     SequentialCommandGroup testRoutine() {
          SequentialCommandGroup cmds = new SequentialCommandGroup();
          Trajectory moveTo = m_robot.drivetrain.trajectoryBuilder(getStartingPose(), false)
                  .lineToSplineHeading(new Pose2d(24,24, Math.toRadians(90)))
                  .build();

          Trajectory splineTo = m_robot.drivetrain.trajectoryBuilder(moveTo.end(), false)
                  .splineTo(new Vector2d(48, 48), Math.toRadians(90))
                  .build();

          cmds.addCommands(
               new RR_TrajectoryFollowerCommand(m_robot.drivetrain, moveTo)
               ,new RR_TrajectoryFollowerCommand(m_robot.drivetrain, splineTo)
          );
          return cmds;
     }
}
