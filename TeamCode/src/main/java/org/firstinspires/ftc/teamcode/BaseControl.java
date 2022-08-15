package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "BaseControl", group = "GameChangers")

public class BaseControl extends LinearOpMode {

    private DcMotor leftUpperMotor;
    private DcMotor leftLowerMotor;
    private DcMotor rightUpperMotor;
    private DcMotor rightLowerMotor;
    private DcMotor shooter;

    private DcMotor wheel;

    private DcMotor arm;
    private Servo armGrabber;
    private Servo lift;
    private CRServo spin;

    private Gyroscope imu;

    private Servo linear;

    private double motorPowerOne = 0.0;
    private double motorPowerTwo = 0.0;

    private double whilePowerOne = 0.0;
    private double whilePowerTwo = 0.0;
    private double armPower = 0.0;
    private double shooterPower = 0.0;

    private static final double MIN_LIFT = 0.25;
    private static final double MAX_LIFT = 0.5;

    public DcMotor getMotor() {
        return leftLowerMotor;
    }

    public DcMotor getMotorTwo() {
        return leftUpperMotor;
    }

    public DcMotor getMotorThree() {
        return rightLowerMotor;
    }

    /*public void moveForward(DcMotor leftLower, DcMotor leftUpper, DcMotor rightLower, DcMotor rightUpper, int miliseconds, double power, int multiplier){
        int ms = miliseconds; double p = power; int mp = multiplier;
        super.moveForward(leftLower, leftUpper, rightLower, rightUpper, ms, p, mp);
    }
    public void spin(DcMotor leftLower, DcMotor leftUpper, DcMotor rightLower, DcMotor rightUpper, int miliseconds, double power, int multiplier, boolean right){
        int ms = miliseconds; double p = power; int mp = multiplier; boolean r = right;
        super.spin(leftLower, leftUpper, rightLower, rightUpper, ms, p, mp, r);
    }
    public void strafe(DcMotor leftLower, DcMotor leftUpper, DcMotor rightLower, DcMotor rightUpper, int miliseconds, double power, int multiplier, boolean right) {
        int ms = miliseconds; double p = power; int mp = multiplier; boolean r = right;
        super.strafe(leftLower, leftUpper, rightLower, rightUpper, ms, p, mp, r);
    }
    public void spin(DcMotor leftLower, DcMotor leftUpper, DcMotor rightLower, DcMotor rightUpper, double power, int multiplier, boolean right){
        double p = power; int mp = multiplier; boolean r = right;
        super.spin(leftLower, leftUpper, rightLower, rightUpper, p, mp, r);
    }*/

    //BaseControl move = new BaseControl();

    @Override
    public void runOpMode() throws InterruptedException {

        leftLowerMotor = hardwareMap.dcMotor.get("leftLowerMotor");
        leftUpperMotor = hardwareMap.dcMotor.get("leftUpperMotor");
        rightUpperMotor = hardwareMap.dcMotor.get("rightUpperMotor");
        rightLowerMotor = hardwareMap.dcMotor.get("rightLowerMotor");
        arm = hardwareMap.dcMotor.get("wobbleArm");
        wheel = hardwareMap.dcMotor.get("wheel");
        shooter = hardwareMap.dcMotor.get("shooter");
        lift = hardwareMap.servo.get("lift");
        spin = hardwareMap.crservo.get("spin");
        //linear = hardwareMap.servo.get("adjust");
        //wheel = hardwareMap.dcMotor.get("wheel");



        //imu = hardwareMap.get(Gyroscope.class, "imu");

        armGrabber = hardwareMap.servo.get("wobbleGrabber");

        leftUpperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftLowerMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightUpperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLowerMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLowerMotor.setDirection(DcMotor.Direction.REVERSE);
        leftUpperMotor.setDirection(DcMotor.Direction.REVERSE);
        //rightUpperMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLowerMotor.setDirection(DcMotor.Direction.REVERSE);
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("Status: ", "Running");

            /*motorPowerOne = gamepad1.right_stick_y;
            motorPowerOne *= -0.75;
            leftLowerMotor.setPower(motorPowerOne);
            rightUpperMotor.setPower(motorPowerOne);

            motorPowerTwo = gamepad1.left_stick_y;
            motorPowerTwo *= -0.75;
            leftUpperMotor.setPower(motorPowerTwo);
            rightLowerMotor.setPower(motorPowerTwo);*/


            /*if(gamepad1.right_bumper){
                linear.setPosition(1.0);
            }
            else if(gamepad1.left_bumper){
                linear.setPosition(0.0);
            }*/


            if (gamepad2.right_bumper) {
                armPower = 0.6;
                arm.setPower(armPower);
            } else if (gamepad2.left_bumper) {
                armPower = -0.6;
                arm.setPower(armPower);
            } else {
                arm.setPower(0.0);
            }

            if(gamepad2.dpad_up){
                shooterPower = 1.0;
                shooter.setPower(shooterPower);
                /*sleep(1100);
                spin.setPower(1);
                sleep(1000);
                spin.setPower(0);
                sleep(300);*/
            }
            else{
                shooterPower = 0.0;
                shooter.setPower(shooterPower);
                spin.setPower(0);
            }

            if(gamepad2.dpad_right){
                lift.setPosition(MAX_LIFT);
                telemetry.addData("Gamepad UP: ", gamepad2.dpad_up);
                telemetry.update();
            }
            else if(gamepad2.dpad_left){
                lift.setPosition(MIN_LIFT);
                telemetry.addData("Gamepad DOWN: ", gamepad2.dpad_down);
                telemetry.update();
            }




            /* remove 1133
            armPower = gamepad1.right_trigger;
            arm.setPower(armPower);
            armPower = 0;
            arm.setPower(0.0);

            armPower = gamepad1.left_trigger;
            arm.setPower(-armPower);
            armPower = 0;
            arm.setPower(0.0);*/


            if (gamepad1.dpad_right) {
                // spin right
                whilePowerOne = 0.75;
                rightLowerMotor.setPower(-whilePowerOne);
                rightUpperMotor.setPower(-whilePowerOne);
                leftUpperMotor.setPower(whilePowerOne);
                leftLowerMotor.setPower(whilePowerOne);
                //move.spin(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, whilePowerOne, 1, true);
            } else if (gamepad1.dpad_left) {
                // spin left
                whilePowerTwo = 0.75;
                rightLowerMotor.setPower(whilePowerTwo);
                rightUpperMotor.setPower(whilePowerTwo);
                leftUpperMotor.setPower(-whilePowerTwo);
                leftLowerMotor.setPower(-whilePowerTwo);
                //move.spin(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, whilePowerOne, 1, false);
            } else {

                if (gamepad1.left_stick_y > 0.5 || gamepad1.left_stick_y < -0.5) {
                    motorPowerOne = gamepad1.left_stick_y;
                    motorPowerOne *= -0.75;
                    leftLowerMotor.setPower(motorPowerOne);
                    rightUpperMotor.setPower(motorPowerOne);

                    //motorPowerTwo = gamepad1.left_stick_y;
                    //motorPowerTwo *= -0.75;
                    leftUpperMotor.setPower(motorPowerOne);
                    rightLowerMotor.setPower(motorPowerOne);
                }
                else if (gamepad1.left_stick_x > 0.5) {
                    // strafe right?
                    telemetry.addData("gamepad1 left stick x: ", gamepad1.left_stick_x);
                    telemetry.update();
                    motorPowerOne = gamepad1.left_stick_x;
                    motorPowerOne *= 0.75;
                    leftLowerMotor.setPower(-motorPowerOne);
                    rightUpperMotor.setPower(motorPowerOne);

                    leftUpperMotor.setPower(motorPowerOne);
                    rightLowerMotor.setPower(-motorPowerOne);
                }
                else if (gamepad1.left_stick_x < -0.5) {
                    // strafe left
                    telemetry.addData("gamepad1 left stick x: ", gamepad1.left_stick_x);
                    telemetry.update();
                    motorPowerOne = gamepad1.left_stick_x;
                    motorPowerOne *= 0.75;
                    leftLowerMotor.setPower(-motorPowerOne);
                    rightUpperMotor.setPower(motorPowerOne);

                    leftUpperMotor.setPower(motorPowerOne);
                    rightLowerMotor.setPower(-motorPowerOne);
                }

                else{
                    double motorPowerZero = 0;
                    leftLowerMotor.setPower(motorPowerZero);
                    rightUpperMotor.setPower(motorPowerZero);
                    leftUpperMotor.setPower(motorPowerZero);
                    rightLowerMotor.setPower(motorPowerZero);
                }
            }

            if (gamepad2.a) {
                armGrabber.setPosition(1.0);
            } else if (gamepad2.b) {
                armGrabber.setPosition(0.0);
            } else;

            if(gamepad2.right_trigger > 0){
                spin.setPower(1);
                //sleep(1000);
                telemetry.addData("Power:", gamepad2.right_trigger);
                telemetry.update();
            }
            else if(gamepad2.right_trigger == 0){
                spin.setPower(0);
                telemetry.addData("Power:", gamepad2.right_trigger);
                telemetry.update();
            } // 2/27/2021 Match change - built servo for firing rings
            else;


            // wheel roller tingy

            if(gamepad2.right_stick_button){
                wheel.setPower(1.0);
            }
            else if(gamepad2.left_stick_button){
                wheel.setPower(0.0);
            }
        }
    }
}
