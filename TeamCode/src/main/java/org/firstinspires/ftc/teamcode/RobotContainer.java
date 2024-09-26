package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.ftclib.command.Command;
import org.firstinspires.ftc.teamcode.ftclib.command.Robot;
import org.firstinspires.ftc.teamcode.subsystems.*;

public class RobotContainer {
     public boolean m_red = true;
     public Robot m_robot = new Robot();
     public MecanumDriveSubsystem drivetrain;
     public GlobalVariables GlobalVariables;
     public SUB_Odometry m_odometry;
     public SUB_Arm m_arm;
     public SUB_Claw m_claw;

     public RobotContainer(OpMode p_opMode) {
          m_odometry = new SUB_Odometry(p_opMode);
          SampleMecanumDrive drivebase = new SampleMecanumDrive(p_opMode.hardwareMap);
          drivetrain = new MecanumDriveSubsystem(drivebase, true);
          GlobalVariables = new GlobalVariables();
          m_arm = new SUB_Arm(p_opMode);
          m_claw = new SUB_Claw(p_opMode);
     };

     public void run() {
          m_robot.run();
     }

     public void reset() {
          m_robot.reset();
     }

     public void schedule(Command... commands) {
          m_robot.schedule(commands);
     }

     public void setRedSide() {
          m_red = true;
     }

     public void setBlueSide() {
          m_red = false;
     }
}

