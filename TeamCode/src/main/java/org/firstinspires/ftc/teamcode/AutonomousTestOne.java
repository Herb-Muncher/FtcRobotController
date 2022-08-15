package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "AutonomousTestOne", group = "FreightFrenzy")

public class AutonomousTestOne extends LinearOpMode {
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
    private Servo arm;

    private double motorPower = 0.0; // determines power of motors

    public void runOpMode(){

        initialize();

        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("IMU Z:", imu.getAngularOrientation().firstAngle);
            telemetry.addData("","");
            telemetry.addData("","");
            telemetry.addData("Sensor West", sensorW.getDistance(DistanceUnit.MM));
            telemetry.addLine("");
            telemetry.addData("Claw 2",claw2.getPortNumber());
            telemetry.addData("Claw 1",claw1.getPortNumber());
            telemetry.update();

            /*arm.setPosition(0.5);
            claw1.setPosition(0.5);
            claw2.setPosition(0.5);*/


            //point.rotate(90.0, "hi");
            //point.moveSideways(500.0);
            //point.rotate(-90.0, "hi");

            point.moveSideways(500.0, "right", 1.0);
            //point.moveStraight(500.0,"forward");
            //point.rotateSeconds(1000,"right");
            //point.rotate(90, "hi");
            stop();
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

        arm = hardwareMap.get(Servo.class, "arm");
        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");

        point = new Point(sensorN, sensorW, sensorE, sensorS, imu, leftLow, leftUp, rightLow, rightUp);
    }
}
