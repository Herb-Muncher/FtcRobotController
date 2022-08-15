package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "RedDuckPark", group = "FreightFrenzy")

public class RedDuckPark extends LinearOpMode {
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
    private DcMotor arm;

    private double motorPower = 0.0; // determines power of motors

    public void runOpMode(){

        initialize();

        waitForStart();
        while(opModeIsActive()){
            /*telemetry.addData("IMU Z:", imu.getAngularOrientation().firstAngle);
            telemetry.addData("","");
            telemetry.addData("","");
            telemetry.addData("Sensor West", sensorW.getDistance(DistanceUnit.MM));
            telemetry.addLine("");
            telemetry.addData("Claw 2",claw2.getPortNumber());
            telemetry.addData("Claw 1",claw1.getPortNumber());
            telemetry.update();*/

            point.moveStraight(100.0, "forward", 0.3);
            double startPos = sensorS.getDistance(DistanceUnit.MM);
            //point.rotateSeconds(750,"left",0.5);
            //point.rotate(-87, "s","S");
            //point.moveSideways(670.0, "left", 0.4);

            while(sensorW.getDistance(DistanceUnit.MM) > 250){
                motorPower = 0.5;
                setSidewaysMotorPower(-motorPower);
            }
            setMotorPower(0.0);

            //double endPos = sensorS.getDistance(DistanceUnit.MM);
           /* if(endPos > startPos){
                point.moveSideways(endPos-startPos, "backward", 0.4);
            }*/
            //point.rotate(1.0, "s","S");

            sleep(1200);

            spin.setPower(0.3);
            sleep(700);
            spin.setPower(0.0);
            sleep(300);
            spin.setPower(0.3);
            sleep(1600);
            spin.setPower(0.3);
            sleep(1000);
            spin.setPower(0.0);

            //point.rotateSeconds(50, "left", 0.5);
            //point.rotate(90, "s","S");
            point.moveStraight(400.0, "forward", 0.5);
            point.moveSideways(125.0,"left",0.5);

            stop();
        }

    }
    public void setMotorPower(double power){
        leftLow.setPower(power);
        leftUp.setPower(power);
        rightLow.setPower(power);
        rightUp.setPower(power);
    }

    public void setSidewaysMotorPower(double power){
        leftLow.setPower(power);
        leftUp.setPower(-power);
        rightLow.setPower(-power);
        rightUp.setPower(power);
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

        arm = hardwareMap.get(DcMotor.class, "arm");
        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        point = new Point(sensorN, sensorW, sensorE, sensorS, imu, leftLow, leftUp, rightLow, rightUp);
    }
}
