package org.firstinspires.ftc.teamcode;

//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class AutonomousCode extends MoveForward {
    @Override
    public DcMotor getRightLower() {
        return rightLowerMotor;
    }
    public DcMotor getLeftLower() {
        return leftLowerMotor;
    }
    public DcMotor getLeftUpper() {
        return leftUpperMotor;
    }
    public DcMotor getRightUpper() {
        return rightUpperMotor;
    }
    public DcMotor getArm(){
        return arm;
    }
    public Servo getWobbleGrabber(){
        return wobbleGrabber;
    }

    double armPower = 0.0;

    double power;

    String zone = "";
    //AutoOne move = new AutoOne();

}
