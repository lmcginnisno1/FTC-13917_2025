package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ftclib.command.SubsystemBase;

public class SUB_Example extends SubsystemBase {
    //variables, must be created here to use later
    DcMotorEx m_exampleMotor;
    Servo m_exampleServo;
    public SUB_Example(OpMode p_opMode){
    //set variables to use later
       m_exampleMotor = p_opMode.hardwareMap.get(DcMotorEx.class, "exampleMotor");
       m_exampleServo = p_opMode.hardwareMap.get(Servo.class, "exampleServo");
    }

    public void exampleMotorOn(){
        //set motor power
        m_exampleMotor.setPower(1);
    }

    public void exampleServoSetPosition(double p_ServoPos){//value to set servo position too
        //set servo position
        m_exampleServo.setPosition(p_ServoPos);
    }
}
