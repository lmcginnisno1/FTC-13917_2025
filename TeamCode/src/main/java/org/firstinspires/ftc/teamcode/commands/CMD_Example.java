package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.SUB_Example;

public class CMD_Example extends CommandBase {
    //create variables to store subsystem and variables, without this nothing can be used after constructor
    SUB_Example m_exampleSubsystem;
    double m_exampleServoPos;
    boolean m_isFinished;
    public CMD_Example(SUB_Example p_exampleSubsystem, double p_exampleServoPos){//import subsystems and variables
        //^^^constructor^^^ everything in these brackets only runs ONCE PER OPMODE
        //save variables and subsystems so they can be used later in command
        m_exampleSubsystem = p_exampleSubsystem;
        m_exampleServoPos = p_exampleServoPos;
    }

    @Override
    public void initialize(){
    //runs once each time the command is called
        m_isFinished = false;
        m_exampleSubsystem.exampleMotorOn();
        m_exampleSubsystem.exampleServoSetPosition(m_exampleServoPos);
    }

    @Override
    public void execute() {
    //runs indefinitely until command is ended,
    // either from an interruption or end condition is met
        m_isFinished = true;
    }

    @Override
    public void end(boolean interrupted){
        //runs once when command is ended, either from interruption or end condition is met
        if(interrupted){
            //runs only if the command was interrupted, not when command end condition is met
        }
    }

    @Override
    public boolean isFinished(){
        //return true if just setting motor power or servo position, otherwise return m_isFinished
        //must have some condition where m_isFinished becomes true
        return m_isFinished;
    }
}
