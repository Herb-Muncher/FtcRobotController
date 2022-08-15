package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "RedDelivery", group = "FreightFrenzy")

public class RedDelivery extends OpMode {
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
    private double CLAW_TWO_CLOSE = 0.3; //0.4
    private double CLAW_ONE_OPEN = 0.4; //0.3
    private double CLAW_TWO_OPEN = 0.4; //0.5

    public RedDelivery(){

    }

    public RedDelivery(DistanceSensor sN, DistanceSensor sW, DistanceSensor sE, DistanceSensor sS, BNO055IMU imuu, DcMotor lLow, DcMotor lUp, DcMotor rLow, DcMotor rUp, DcMotorEx arm, DcMotor actuate, DcMotor spin, Servo c1, Servo c2){
        sensorN = sN;
        sensorW = sW;
        sensorE = sE;
        sensorS = sS;

        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.loggingEnabled = false;

        imu = imuu;
        imu.initialize(params);

        leftUp = lUp;
        leftLow = lLow;
        rightUp = rUp;
        rightLow = rLow;
        this.actuate = actuate;
        this.spin = spin;

        this.arm = arm;
        claw1 = c1;
        claw2 = c2;

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setDirection(DcMotorSimple.Direction.FORWARD);
        actuate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        point = new Point(sensorN, sensorW, sensorE, sensorS, imu, leftLow, leftUp, rightLow, rightUp);
    }


    public void upperDrop(){
          /*telemetry.addData("IMU Z:", imu.getAngularOrientation().firstAngle);
            telemetry.addData("","");
            telemetry.addData("","");
            telemetry.addData("Sensor West", sensorW.getDistance(DistanceUnit.MM));
            telemetry.addLine("");
            telemetry.addData("Claw 2",claw2.getPortNumber());
            telemetry.addData("Claw 1",claw1.getPortNumber());
            telemetry.update();*/

        claw1.setPosition(CLAW_ONE_CLOSE);
        claw2.setPosition(CLAW_TWO_CLOSE);

        actuate.setPower(-1.0);
        sleep(600);
        actuate.setPower(0.0);

        point.moveStraight(200.0, "forward", 1.0);

        arm.setPower(1.0);
        arm.setTargetPosition(80);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setVelocity(500);

        sleep(1000);
        //point.moveStraight(250.0, "forward", 0.5);
        double curDist = sensorS.getDistance(DistanceUnit.MM);
        while(sensorS.getDistance(DistanceUnit.MM)-curDist < 250){
            motorPower = -0.5;
            leftLow.setPower(motorPower);
            leftUp.setPower(motorPower);
            rightLow.setPower(motorPower);
            rightUp.setPower(motorPower);
        }
        point.rotate(55, "s", "s", .4);
        sleep(1000);

        claw2.setPosition(1.0);
        sleep(400);
        claw1.setPosition(0.6);

        sleep(1000);

        point.moveStraight(100.0, "backward", 0.5);

        point.rotate(-10, "s", "s",.4);
        claw2.setPosition(CLAW_TWO_CLOSE);
        claw1.setPosition(CLAW_ONE_CLOSE);
        sleep(1000);

        arm.setPower(0.0);
            /*while(sensorS.getDistance(DistanceUnit.MM) > 100){
                point.moveStraight(50.0, "backward", 0.5);
                if(sensorS.getDistance(DistanceUnit.MM) < 100){
                    stop();
                }
            }*/
    }
    public void middleDrop(){
            /*telemetry.addData("IMU Z:", imu.getAngularOrientation().firstAngle);
            telemetry.addData("","");
            telemetry.addData("","");
            telemetry.addData("Sensor West", sensorW.getDistance(DistanceUnit.MM));
            telemetry.addLine("");
            telemetry.addData("Claw 2",claw2.getPortNumber());
            telemetry.addData("Claw 1",claw1.getPortNumber());
            telemetry.update();*/

        claw1.setPosition(CLAW_ONE_CLOSE);
        claw2.setPosition(CLAW_TWO_CLOSE);

        point.moveStraight(200.0, "forward", 1.0);

        arm.setPower(1.0);
        arm.setTargetPosition(53);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setVelocity(2000);

        sleep(1000);
        //point.moveStraight(250.0, "forward", 0.5);
        double curDist = sensorS.getDistance(DistanceUnit.MM);
        while(sensorS.getDistance(DistanceUnit.MM)-curDist < 250){
            motorPower = -0.5;
            leftLow.setPower(motorPower);
            leftUp.setPower(motorPower);
            rightLow.setPower(motorPower);
            rightUp.setPower(motorPower);
        }
        point.rotate(55, "s", "s", .4);
        sleep(1000);

        claw2.setPosition(1.0);
        sleep(400);
        claw1.setPosition(0.6);

        sleep(1000);

        //point.moveStraight(100.0, "backward", 0.5);

        /*motorPower = -1.0;
        leftLow.setPower(motorPower);
        leftUp.setPower(motorPower);
        rightUp.setPower(motorPower);
        rightLow.setPower(motorPower);
        sleep(400);

        motorPower = 0.0;
        leftLow.setPower(motorPower);
        leftUp.setPower(motorPower);
        rightUp.setPower(motorPower);
        rightLow.setPower(motorPower);*/


        point.rotate(-10, "s", "s", .4);
        claw2.setPosition(CLAW_TWO_CLOSE);
        claw1.setPosition(CLAW_ONE_CLOSE);
        sleep(1000);

        arm.setPower(0.0);
            /*while(sensorS.getDistance(DistanceUnit.MM) > 100){
                point.moveStraight(50.0, "backward", 0.5);
                if(sensorS.getDistance(DistanceUnit.MM) < 100){
                    stop();
                }
            }*/
    }
    public void lowerDrop(){
            /*telemetry.addData("IMU Z:", imu.getAngularOrientation().firstAngle);
            telemetry.addData("","");
            telemetry.addData("","");
            telemetry.addData("Sensor West", sensorW.getDistance(DistanceUnit.MM));
            telemetry.addLine("");
            telemetry.addData("Claw 2",claw2.getPortNumber());
            telemetry.addData("Claw 1",claw1.getPortNumber());
            telemetry.update();*/

        claw1.setPosition(CLAW_ONE_CLOSE);
        claw2.setPosition(CLAW_TWO_CLOSE);

        point.moveStraight(200.0, "forward", 1.0);

        arm.setPower(1.0);
        arm.setTargetPosition(30);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setVelocity(2000);

        sleep(1000);
        //point.moveStraight(250.0, "forward", 0.5);
        double curDist = sensorS.getDistance(DistanceUnit.MM);
        while(sensorS.getDistance(DistanceUnit.MM)-curDist < 250){
            motorPower = -0.5;
            leftLow.setPower(motorPower);
            leftUp.setPower(motorPower);
            rightLow.setPower(motorPower);
            rightUp.setPower(motorPower);
        }
        point.rotate(55, "s", "s", .4);
        sleep(1000);

        claw2.setPosition(1.0);
        sleep(400);
        claw1.setPosition(0.6);

        sleep(1000);

        //point.moveStraight(100.0, "backward", 0.5); // DANGEROUS

        point.rotate(95, "s", "s", .4);
        claw2.setPosition(CLAW_TWO_CLOSE);
        claw1.setPosition(CLAW_ONE_CLOSE);
        sleep(1000);

        arm.setPower(0.0);
            /*while(sensorS.getDistance(DistanceUnit.MM) > 100){
                point.moveStraight(50.0, "backward", 0.5);
                if(sensorS.getDistance(DistanceUnit.MM) < 100){
                    stop();
                }
            }*/
    }

    public void init(){

    }
    public void loop(){

    }
    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException var4) {
            Thread.currentThread().interrupt();
        }
    }
}
