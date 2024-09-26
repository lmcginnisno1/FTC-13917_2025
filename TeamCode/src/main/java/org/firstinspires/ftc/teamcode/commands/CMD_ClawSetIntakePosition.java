package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.GlobalVariables;
import org.firstinspires.ftc.teamcode.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.SUB_Claw;

public class CMD_ClawSetIntakePosition extends CommandBase {
    SUB_Claw m_claw;
    GlobalVariables m_variables;
    public CMD_ClawSetIntakePosition(SUB_Claw p_claw, GlobalVariables p_variables){
        m_claw = p_claw;
        m_variables = p_variables;
    }

    @Override
    public void initialize(){
        if(m_variables.isIntakeState(GlobalVariables.IntakeState.SUBMERSIBLE)){
            m_claw.setPivotSubmersible();
            m_claw.pincherOpen();
        }else if(m_variables.isIntakeState(GlobalVariables.IntakeState.WALL)){
            m_claw.pincherOpen();
            m_claw.setPivotReadyToIntakeWall();
        }else{
            return;
        }
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
