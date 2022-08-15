package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ChainDrive", group = "FreightFrenzy") // sets name and group

public class ChainDrive extends LinearOpMode {

    private BNO055IMU.Parameters params = new BNO055IMU.Parameters();

    private Point point = new Point();
    private DistanceSensor sensorN;
    private DistanceSensor sensorW;
    private DistanceSensor sensorE;
    private DistanceSensor sensorS;

    private BNO055IMU imu;

    private DcMotor leftLow;
    private DcMotor leftUp;
    private DcMotor rightLow;
    private DcMotor rightUp;
    private DcMotor actuate;
    private DcMotor spin;

    private Servo claw1;
    private Servo claw2;
    private DcMotorEx arm;

    private double motorPower = 0.0; // determines power of motors
    private double CLAW_ONE_CLOSE = 0.5; //0.4
    private double CLAW_TWO_CLOSE = 0.2; //0.4
    private double CLAW_ONE_OPEN = 0.4; //0.3
    private double CLAW_TWO_OPEN = 0.4; //0.5

    private int position = 0;

    @Override
    public void runOpMode() throws InterruptedException{ // runOpMode
        initialize(); // method call to void method initialize()

        //arm.setDirection(DcMotorSimple.Direction.FORWARD);
        //arm.setPower(1.0);
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();
        while(opModeIsActive()){



           // arm.setTargetPosition(position);
           // arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //arm.setVelocity(3000);

            // code
            telemetry.addData("Status:", "Running");
            telemetry.addLine("DEBUG:");
            //telemetry.addData("Arm Position", arm.getCurrentPosition());

            leftUp.setPower(gamepad1.left_stick_y); // move forward, backward, sideways
            leftLow.setPower(gamepad1.left_stick_y);
            rightUp.setPower(gamepad1.left_stick_y);
            rightLow.setPower(gamepad1.left_stick_y);

            //leftLow.setPower(1.0);
            /*arm.setPosition(0.5);
            claw1.setPosition(0.6);
            claw2.setPosition(0.4);*/

            telemetry.addData("Claw1 Pos ", claw1.getPosition());
            telemetry.addData("Claw2 Pos ", claw2.getPosition());
            telemetry.update();
            //arm.setTargetPosition(0);

            if(gamepad1.right_stick_x > 0.3){
                leftUp.setPower(-gamepad1.right_stick_x);
                leftLow.setPower(gamepad1.right_stick_x);
                rightUp.setPower(gamepad1.right_stick_x);
                rightLow.setPower(-gamepad1.right_stick_x);
            }
            else if(gamepad1.right_stick_x < -0.3){
                leftUp.setPower(-gamepad1.right_stick_x);
                leftLow.setPower(gamepad1.right_stick_x);
                rightUp.setPower(gamepad1.right_stick_x);
                rightLow.setPower(-gamepad1.right_stick_x);
            }

            if(gamepad1.dpad_right){ // spins right?
                motorPower = 1;
                leftUp.setPower(motorPower);
                leftLow.setPower(motorPower);
                rightLow.setPower(-motorPower);
                rightUp.setPower(-motorPower);
            }
            else if(gamepad1.dpad_left){ // spins left?
                motorPower = 1;
                leftUp.setPower(-motorPower);
                leftLow.setPower(-motorPower);
                rightLow.setPower(motorPower);
                rightUp.setPower(motorPower);
            }


            if(gamepad2.right_bumper){
                actuate.setPower(1.0);
            }
            else if(gamepad2.left_bumper){
                actuate.setPower(-1.0);
            }
            else{
                actuate.setPower(0.0);
            }

            if(gamepad2.a){
                position = 80;
                arm.setPower(1.0);
            }
            else if(gamepad2.b){
                position = 0;
                arm.setPower(0.0);
            }
            else{
                arm.setPower(0.0);
            }

            if(gamepad2.x){
                claw1.setPosition(CLAW_ONE_CLOSE);
                claw2.setPosition(CLAW_TWO_CLOSE);
            }
            else if(gamepad2.y){
                claw1.setPosition(CLAW_ONE_OPEN);
                claw2.setPosition(CLAW_TWO_OPEN);
            }

            if(gamepad2.left_trigger > 0.2){
                claw1.setPosition(1.0);
            }
            else if(gamepad2.right_trigger > 0.2){
                claw2.setPosition(1.0);
            }

            if(gamepad2.dpad_up){
                spin.setPower(0.5);
            }
            else if(gamepad2.dpad_down){
                spin.setPower(-0.5);
            }
            else{
                spin.setPower(0.0);
            }
        }

    }
    public void initialize(){ // initializes all of the motors and sets the ZeroPowerBehavior to BRAKE
        sensorN = hardwareMap.get(DistanceSensor.class, "sensorN");
        sensorW = hardwareMap.get(DistanceSensor.class, "sensorW");
        sensorE = hardwareMap.get(DistanceSensor.class, "sensorE");
        sensorS = hardwareMap.get(DistanceSensor.class, "sensorS");

        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.loggingEnabled = false;

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(params);

        leftUp = hardwareMap.get(DcMotor.class, "lUp");
        leftLow = hardwareMap.get(DcMotor.class, "lLow");
        rightUp = hardwareMap.get(DcMotor.class, "rUp");
        rightLow = hardwareMap.get(DcMotor.class, "rLow");
        actuate = hardwareMap.get(DcMotor.class, "actuate");
        spin = hardwareMap.get(DcMotor.class, "spin");

        arm = hardwareMap.get(DcMotorEx.class, "arm");
        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        point = new Point(sensorN, sensorW, sensorE, sensorS, imu, leftLow, leftUp, rightLow, rightUp);
        //arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}

// test comment
// comment