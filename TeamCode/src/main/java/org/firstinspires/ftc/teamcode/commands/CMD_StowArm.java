package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.GlobalVariables;
import org.firstinspires.ftc.teamcode.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.SUB_Arm;
import org.firstinspires.ftc.teamcode.Constants.armConstants;

public class CMD_StowArm extends CommandBase {
    SUB_Arm m_arm;
    public CMD_StowArm(SUB_Arm p_arm){
        m_arm = p_arm;
    }

    @Override
    public void initialize(){
        m_arm.armSetTargetPosition(GlobalVariables.armPositions.STOW);
    }

    @Override
    public boolean isFinished(){
        return Math.abs(m_arm.getCurrentPosition() - m_arm.getTargetPosition()) < armConstants.tolerance;
    }
}
