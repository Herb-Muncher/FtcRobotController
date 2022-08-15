// version 2 10-20-2019 9:42pm
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name="Stones", group="Linear Opmode")
//@Disabled
public class Tele7673Skystone extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    DcMotor leftDrive = null;
    DcMotor rightDrive = null;
    DcMotor leftDrive2 = null;
    DcMotor rightDrive2 = null;
    DcMotor arm = null;
    Servo grabber = null;
    Servo leftServo = null;
    Servo rightServo = null;


    @Override
    public void runOpMode() {

        double leftPower;
        double rightPower;
        double leftPower2;
        double rightPower2;
        double armPower;

        double rotateSpeed = 0.8;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. The strings (words inside "    ")
        // are used here as parameters
        // to 'get' must correspond to the names assigned when you configure
        // the program on the FTC Robot Controller  phone).


        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        leftDrive2 = hardwareMap.get(DcMotor.class, "left_drive_2");
        rightDrive2 = hardwareMap.get(DcMotor.class,"right_drive_2");

        arm = hardwareMap.get(DcMotor.class, "arm");
        grabber = hardwareMap.servo.get("grabber");
        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.REVERSE);  // see if this fixs steering
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        leftDrive2.setDirection(DcMotor.Direction.REVERSE) ;
        rightDrive2.setDirection(DcMotor.Direction.FORWARD) ;
        //setDirection for arm motor below May not need the line below
        arm.setDirection(DcMotor.Direction.FORWARD);



        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry

            // below code sets power for buttons A and Y
            if (gamepad2.a)
                arm.setPower(-0.6);
            else if (gamepad2.y)
                arm.setPower(0.6);
            else
                arm.setPower(0.0);


            //  Tank Mode
            leftPower = gamepad1.left_stick_y;
            rightPower = gamepad1.right_stick_y;
            leftPower2 = gamepad1.right_stick_y;
            rightPower2 = gamepad1.left_stick_y;


            // Send calculated power to wheels
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);
            leftDrive2.setPower(leftPower2);
            rightDrive2.setPower(rightPower2);


            //armDrive.setPower(armPower) is not needed
            //arm power is set above at a and y


            // Move the arm servo when a or y pressed
            if (gamepad2.b)  //b is down and back Is in good position
            {
                 grabber.setPosition(-0.4);  //was - 0.8 wanted it a bit farther back

            }
            if (gamepad2.x)   //x is up
            {
                grabber.setPosition(0.8); //was .2 not far enough back - changed to .8

            }

            if (gamepad2.dpad_down)
            {
                leftServo.setPosition(0.3);
                rightServo.setPosition(0.3);
            }

             if (gamepad2.dpad_up)
            {
                leftServo.setPosition(-0.5);
                rightServo.setPosition(-0.5);

            }



            if (gamepad1.dpad_down)
            {
                leftDrive2.setPower(-rotateSpeed);
                rightDrive2.setPower(rotateSpeed);
                leftDrive.setPower(-rotateSpeed);
                rightDrive.setPower(rotateSpeed);
            }
            else if (gamepad1.dpad_up)
            {
                leftDrive2.setPower(rotateSpeed);
                rightDrive2.setPower(-rotateSpeed);
                leftDrive.setPower(rotateSpeed);
                rightDrive.setPower(-rotateSpeed);
            }

            // Show the elapsed game time and wheel power.
            //Line below shows on Driver Station phone how long robot has been running
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            // Line below shows on the DS phone -  power for left and right motors
            //add   armPower after rightPower on the line below
            telemetry.addData("Motors", "left (%.2f), right (%.2f) left2", leftPower, rightPower);

            telemetry.update();
        }
    }
}










