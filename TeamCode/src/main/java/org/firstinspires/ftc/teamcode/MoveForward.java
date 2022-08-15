package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Gyroscope;

public abstract class MoveForward extends Base {
    public void setMotorPower(DcMotor leftLower, DcMotor leftUpper, DcMotor rightLower, DcMotor rightUpper, double power){
        //useful for moving forward or setting powers to zero
        double p = power;
        leftLower.setPower(p);
        leftUpper.setPower(p);
        rightLower.setPower(p);
        rightUpper.setPower(p);
    }
    double motorPower;
    public void moveForward(DcMotor leftLower, DcMotor leftUpper, DcMotor rightLower, DcMotor rightUpper, int miliseconds, double power, int multiplier){
        motorPower = power*multiplier; // Multiplies the power by a multiplier, allowing for simple variable changes within this scope.
        leftLower.setPower(motorPower);
        leftUpper.setPower(motorPower);
        rightUpper.setPower(motorPower);
        rightLower.setPower(motorPower); // Four block of code sets the power of each motor to the motorPower variable, passed as a parameter.
        sleep(miliseconds); // Moves a specified amount of time
        motorPower = 0.0; // changes motorPower to zero.
        leftLower.setPower(motorPower); // changes the power of the motors again to zero, stops the motors.
        leftUpper.setPower(motorPower);
        rightUpper.setPower(motorPower);
        rightLower.setPower(motorPower);
    }

    public void spin(DcMotor leftLower, DcMotor leftUpper, DcMotor rightLower, DcMotor rightUpper, int miliseconds, double power, int multiplier, boolean right){
        double mPower;
        motorPower = power*multiplier;
        if(right){
            leftLower.setPower(motorPower);
            leftUpper.setPower(motorPower);
            rightLower.setPower(-motorPower);
            rightUpper.setPower(-motorPower);
        }
        else{
            leftLower.setPower(-motorPower);
            leftUpper.setPower(-motorPower);
            rightLower.setPower(motorPower);
            rightUpper.setPower(motorPower);
        }
        sleep(miliseconds);
        motorPower = 0.0;
        leftLower.setPower(motorPower);
        leftUpper.setPower(motorPower);
        rightLower.setPower(motorPower);
        rightUpper.setPower(motorPower);
    }

    public void spin(DcMotor leftLower, DcMotor leftUpper, DcMotor rightLower, DcMotor rightUpper,
                     double power, int multiplier, boolean right){
        // TeleOp? - find solution to the time issue
        double mPower;
        motorPower = power*multiplier;
        if(right){
            leftLower.setPower(motorPower);
            leftUpper.setPower(motorPower);
            rightLower.setPower(-motorPower);
            rightUpper.setPower(-motorPower);
        }
        else{
            leftLower.setPower(-motorPower);
            leftUpper.setPower(-motorPower);
            rightLower.setPower(motorPower);
            rightUpper.setPower(motorPower);
        }
        motorPower = 0.0;
        leftLower.setPower(motorPower);
        leftUpper.setPower(motorPower);
        rightLower.setPower(motorPower);
        rightUpper.setPower(motorPower);
    }


    public void strafe(DcMotor leftLower, DcMotor leftUpper, DcMotor rightLower, DcMotor rightUpper, int miliseconds, double power, int multiplier, boolean right) {
        motorPower = power * multiplier;
        if (right) { // strafe right
            leftLower.setPower(-motorPower);
            rightUpper.setPower(motorPower);

            leftUpper.setPower(motorPower);
            rightLower.setPower(-motorPower);
        } else { // strafe left
            power *= -1.0;
            leftLower.setPower(-motorPower);
            rightUpper.setPower(motorPower);

            leftUpper.setPower(motorPower);
            rightLower.setPower(-motorPower);
        }
        sleep(miliseconds);
        motorPower = 0.0;
        leftLower.setPower(motorPower);
        leftUpper.setPower(motorPower);
        rightLower.setPower(motorPower);
        rightUpper.setPower(motorPower);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        leftLowerMotor = hardwareMap.dcMotor.get("leftLowerMotor");
        leftUpperMotor = hardwareMap.dcMotor.get("leftUpperMotor");
        rightUpperMotor = hardwareMap.dcMotor.get("rightUpperMotor");
        rightLowerMotor = hardwareMap.dcMotor.get("rightLowerMotor");
        arm = hardwareMap.dcMotor.get("wobbleArm");
        wobbleGrabber = hardwareMap.servo.get("wobbleGrabber");
        leftUpperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftLowerMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightUpperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLowerMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftLowerMotor.setDirection(DcMotor.Direction.REVERSE);
        leftUpperMotor.setDirection(DcMotor.Direction.REVERSE);
    }
}
