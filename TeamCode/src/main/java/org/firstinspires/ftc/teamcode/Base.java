package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;


public abstract class Base extends LinearOpMode{

    protected DcMotor leftLowerMotor;
    protected DcMotor rightLowerMotor;
    protected DcMotor leftUpperMotor;
    protected DcMotor rightUpperMotor;
    protected DcMotor arm;

    protected Servo wobbleGrabber;
    protected Servo lift;
    protected CRServo spin;

    public abstract DcMotor getLeftLower();
    public abstract DcMotor getLeftUpper();
    public abstract DcMotor getRightLower();
    public abstract DcMotor getRightUpper();

    public abstract DcMotor getArm();
    public abstract Servo getWobbleGrabber();
}
