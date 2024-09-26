package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.GlobalVariables;
import org.firstinspires.ftc.teamcode.ftclib.command.InstantCommand;
import org.firstinspires.ftc.teamcode.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.SUB_Arm;
import org.firstinspires.ftc.teamcode.subsystems.SUB_Claw;

public class CMD_ReadyToIntake extends SequentialCommandGroup { ;
    public CMD_ReadyToIntake(SUB_Arm p_arm, SUB_Claw p_claw, GlobalVariables p_variables){
        addRequirements(p_arm, p_claw);
        addCommands(
                new InstantCommand(()-> p_claw.pincherOpen())
                ,new CMD_ClawSetIntakePosition(p_claw, p_variables)
                ,new CMD_ArmSetIntakePosition(p_arm, p_variables)
        );
    }
}
