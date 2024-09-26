package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.GlobalVariables;
import org.firstinspires.ftc.teamcode.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.SUB_Arm;
import org.firstinspires.ftc.teamcode.subsystems.SUB_Claw;

public class CMD_ReadyToDeploy extends CommandBase {
    SUB_Claw m_claw;
    SUB_Arm m_arm;
    GlobalVariables m_variables;
    public CMD_ReadyToDeploy(SUB_Claw p_claw, SUB_Arm p_arm, GlobalVariables p_variables){
        m_claw = p_claw;
        m_arm = p_arm;
        m_variables = p_variables;
    }

    @Override
    public void initialize(){
        if(m_variables.isDeployState(GlobalVariables.DeployState.HIGH_SPECIMEN)){
            m_claw.pivotSetReadyToDeploy();
            m_arm.armSetTargetPosition(GlobalVariables.armPositions.HIGH_SPECIMEN_READY_TO_DEPLOY);
        }
    }

    @Override
    public boolean isFinished(){
        return Math.abs(m_arm.getCurrentPosition() - m_arm.getTargetPosition()) < Constants.armConstants.tolerance;
    }
}
