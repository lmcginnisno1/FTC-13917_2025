package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.Constants.clawConstants;

public class SUB_Claw extends SubsystemBase {
    Servo m_pincherServo;
    Servo m_pivotServo;
    boolean m_pincherIsOpen = false;
    boolean m_pivotIsHome = true;
    public SUB_Claw(OpMode p_opMode){
        m_pincherServo = p_opMode.hardwareMap.get(Servo.class, "pincherServo");
        m_pivotServo = p_opMode.hardwareMap.get(Servo.class, "pivotServo");
        m_pivotServo.setPosition(clawConstants.pivotServoHome);
        m_pincherServo.setPosition(clawConstants.pincherServoClosed);
    }

    public void pincherOpen(){
        m_pincherServo.setPosition(clawConstants.pincherServoOpen);
        m_pincherIsOpen = true;
    }

    public void pincherClosed(){
        m_pincherServo.setPosition(clawConstants.pincherServoClosed);
        m_pincherIsOpen = false;
    }

    public void pivotSetReadyToDeploy(){
        m_pivotIsHome = false;
        m_pivotServo.setPosition(clawConstants.pivotServoReadyToDeploy);
    }

    public void pivotSetDeploy(){
        m_pivotIsHome = false;
        m_pivotServo.setPosition(clawConstants.pivotServoDeploy);
    }

    public void setPivotSubmersible(){
        m_pivotIsHome = false;
        m_pivotServo.setPosition(clawConstants.pivotServoSubmersible);
    }

    public void setPivotHome(){
        m_pivotIsHome = true;
        m_pivotServo.setPosition(clawConstants.pivotServoHome);
    }

    public void setPivotReadyToIntakeWall(){
        m_pivotIsHome = false;
        m_pivotServo.setPosition(clawConstants.pivotServoWall);
    }

    public void setPivotWall(){
        m_pivotServo.setPosition(clawConstants.pivotServoWall);
    }

    public boolean getPincherOpen(){
        return m_pincherIsOpen;
    }

    public boolean getPivotHome(){
        return m_pivotIsHome;
    }
}
