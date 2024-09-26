package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.SUB_Arm;

public class CMD_ArmSetTargetAngle extends CommandBase {
    SUB_Arm m_arm;
    double m_targetAngle;
    public CMD_ArmSetTargetAngle(SUB_Arm p_arm, double p_targetAngle){
        m_arm = p_arm;
        m_targetAngle = p_targetAngle;
    }

    @Override
    public void initialize(){
        m_arm.setTargetAngle(m_targetAngle);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
