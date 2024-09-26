package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.*;

import org.firstinspires.ftc.teamcode.ftclib.command.ConditionalCommand;
import org.firstinspires.ftc.teamcode.ftclib.command.InstantCommand;
import org.firstinspires.ftc.teamcode.ftclib.command.ParallelCommandGroup;
import org.firstinspires.ftc.teamcode.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.ftclib.command.button.GamepadButton;
import org.firstinspires.ftc.teamcode.ftclib.first.math.trajectory.TrapezoidProfile;
import org.firstinspires.ftc.teamcode.ftclib.gamepad.GamepadEx;
import org.firstinspires.ftc.teamcode.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.ftclib.command.Command;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Teleop Red", group ="Teleop Red")
public class Teleop_Robot_Centric_Red extends LinearOpMode {

     public RobotContainer m_robot;
     private GamepadEx m_driverOp;
     private GamepadEx m_toolOp;
     private boolean m_setFieldCentric = false;

     private static ElapsedTime m_runTime = new ElapsedTime();
     private ElapsedTime m_releaseTimeout = new ElapsedTime();

     public void initialize() {
          telemetry.clearAll();
          telemetry.addData("init complete", true);

          m_runTime.reset();
     }

     @Override
     public void runOpMode() throws InterruptedException {
          initializeSubsystems();
          // waitForStart();
          while (!opModeIsActive() && !isStopRequested()) {
               telemetry.update();
          }

          m_runTime.reset();
          while (!isStopRequested() && opModeIsActive()) {
               m_robot.run(); // run the scheduler

               m_robot.drivetrain.update();
               Pose2d poseEstimate = m_robot.drivetrain.getPoseEstimate();
               telemetry.addData("ODM:","x[%3.2f] y[%3.2f] heading(%3.2f)", poseEstimate.getX(), poseEstimate.getY(), Math.toDegrees(poseEstimate.getHeading()));
               telemetry.addData("RobotState", m_robot.GlobalVariables.getRobotState().name());
               telemetry.update();
          }

          //
          endOfOpMode();
          m_robot.reset();
     }

     public void endOfOpMode() {

     }

     public void initializeSubsystems() {
          m_robot = new RobotContainer(this);
          m_driverOp = new GamepadEx(gamepad1);
          m_toolOp = new GamepadEx(gamepad2);

          setSide();

          //drivetrain initialization
          m_robot.drivetrain.setPoseEstimate(new Pose2d(0, 0, Math.toDegrees(0)));
          m_robot.drivetrain.setFieldCentric(false);
          m_robot.drivetrain.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
          m_robot.drivetrain.setDefaultCommand(new RR_MecanumDriveDefault(m_robot.drivetrain, m_driverOp,0.0,0.01));
          //button bindings and global variables initialization
          configureButtonBindings();
     }

     public void configureButtonBindings() {
          AddButtonCommand(m_driverOp, GamepadKeys.Button.LEFT_BUMPER, new CMD_HandleReadyToIntake(m_robot));
          AddButtonCommand(m_driverOp, GamepadKeys.Button.RIGHT_BUMPER, new CMD_HandleReadyToDeploy(m_robot));
          AddButtonCommand(m_driverOp, GamepadKeys.Button.A,
                new InstantCommand(()-> m_robot.m_arm.armSetTargetPosition(GlobalVariables.armPositions.INTAKE_SUBMERSIBLE)));
          AddButtonCommand(m_driverOp, GamepadKeys.Button.B, new ConditionalCommand(
                  new InstantCommand(()-> m_robot.m_claw.pincherOpen())
                  ,new InstantCommand(()-> m_robot.m_claw.pincherClosed())
                  ,()-> !m_robot.m_claw.getPincherOpen()
          ));
          AddButtonCommand(m_driverOp, GamepadKeys.Button.X, new ConditionalCommand(
                  new InstantCommand(()-> m_robot.m_claw.setPivotSubmersible())
                  ,new InstantCommand(()-> m_robot.m_claw.setPivotHome())
                  ,()-> m_robot.m_claw.getPivotHome()
          ));
          AddButtonCommand(m_driverOp, GamepadKeys.Button.Y,
                  new InstantCommand(()-> m_robot.m_claw.setPivotReadyToIntakeWall()));
     }

     public void setSide() {
          m_robot.setRedSide();
     }

     public void AddButtonCommand(GamepadEx gamepad, GamepadKeys.Button button, Command command) {
          (new GamepadButton(gamepad, button)).whenPressed(command);
     }

     public void AddButtonCommandNoInt(GamepadEx gamepad, GamepadKeys.Button button, Command command) {
          (new GamepadButton(gamepad, button)).whenPressed(command, false);
     }
}