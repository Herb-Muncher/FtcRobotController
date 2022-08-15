package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

public class Point extends OpMode {

    private BNO055IMU.Parameters params = new BNO055IMU.Parameters();

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

    // default constructor
    public Point(){

    }
    public Point(DistanceSensor sN, DistanceSensor sW, DistanceSensor sE, DistanceSensor sS, BNO055IMU imuu, DcMotor lLow, DcMotor lUp, DcMotor rLow, DcMotor rUp){
        sensorN = sN;
        sensorW = sW;
        sensorE = sE;
        sensorS = sS;
        imu = imuu;

        leftUp = lUp;
        leftLow = lLow;
        rightLow = rLow;
        rightUp = rUp;

        rightLow.setDirection(DcMotorSimple.Direction.REVERSE);
        rightUp.setDirection(DcMotorSimple.Direction.REVERSE);
        leftUp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftLow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightUp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    //@Override
    public void init() {

    }

    @Override
    public void loop() {

    }
    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException var4) {
            Thread.currentThread().interrupt();
        }

    }

    public void rotateSeconds(long ms, String direction, double speed){
        direction = direction.toLowerCase();
        if(direction.equals("right")){
            leftLow.setPower(speed);
            leftUp.setPower(speed);
            rightUp.setPower(-speed);
            rightLow.setPower(-speed);
            sleep(ms);
            leftUp.setPower(0);
            leftLow.setPower(0);
            rightUp.setPower(0);
            rightLow.setPower(0);
        }
        if(direction.equals("left")){
            leftLow.setPower(-speed);
            leftUp.setPower(-speed);
            rightUp.setPower(speed);
            rightLow.setPower(speed);
            sleep(ms);
            leftUp.setPower(0);
            leftLow.setPower(0);
            rightUp.setPower(0);
            rightLow.setPower(0);
        }
        else{
        }
        leftLow.setPower(0);
        leftUp.setPower(0);
        rightLow.setPower(0);
        rightUp.setPower(0);
    }

    public void rotate(double degrees){
        double d = imu.getAngularOrientation().firstAngle + degrees;
        double upperRange = d+15;
        double lowerRange = d-15;
        double speed = 0.5;
        if(imu.getAngularOrientation().firstAngle <= lowerRange || imu.getAngularOrientation().firstAngle >= upperRange){
            if(imu.getAngularOrientation().firstAngle > upperRange){
                double imuZ = imu.getAngularOrientation().firstAngle;
                leftLow.setPower(speed);
                leftUp.setPower(speed);
                rightUp.setPower(-speed);
                rightLow.setPower(-speed);
                while(imuZ <= upperRange + 30 && imuZ >= upperRange){
                    leftLow.setPower(0.3);
                    leftUp.setPower(0.3);
                    rightUp.setPower(-0.3);
                    rightLow.setPower(-0.3);
                    imuZ = imu.getAngularOrientation().firstAngle;
                }
            }
            else if(imu.getAngularOrientation().firstAngle < lowerRange){
                double imuZ = imu.getAngularOrientation().firstAngle;
                leftLow.setPower(-speed);
                leftUp.setPower(-speed);
                rightUp.setPower(speed);
                rightLow.setPower(speed);
                while(imuZ >= lowerRange - 30 && imuZ <= lowerRange){
                    leftLow.setPower(-0.3);
                    leftUp.setPower(-0.3);
                    rightUp.setPower(0.3);
                    rightLow.setPower(0.3);
                    imuZ = imu.getAngularOrientation().firstAngle;
                }
            }
            else {
                leftUp.setPower(0);
                leftLow.setPower(0);
                rightLow.setPower(0);
                rightUp.setPower(0);
            }
        }
    }

    public void rotate(double degrees, String s, String t, double s1){
        double speed = s1;
        if(degrees <= 0 || degrees < imu.getAngularOrientation().firstAngle){
            while(degrees < imu.getAngularOrientation().firstAngle ){
                leftLow.setPower(speed);
                leftUp.setPower(speed);
                rightUp.setPower(-speed);
                rightLow.setPower(-speed);
            }
        } //degrees <= 0 || degrees < imu.getAngularOrientation().firstAngle
        else if(degrees >= 0){
            while(degrees > imu.getAngularOrientation().firstAngle){
                leftLow.setPower(-speed);
                leftUp.setPower(-speed);
                rightUp.setPower(speed);
                rightLow.setPower(speed);
            }
        }
        speed = 0.0;
        leftLow.setPower(speed);
        leftUp.setPower(speed);
        rightUp.setPower(-speed);
        rightLow.setPower(-speed);
    }

    public void rotate(double degrees,String s){
        double speed = 1.0;
        if(degrees >= 0) {
            degrees = imu.getAngularOrientation().firstAngle + degrees;
            degrees = degrees-1;
            double d = degrees - 30;
            if (imu.getAngularOrientation().firstAngle < d) {
                leftLow.setPower(-speed);
                leftUp.setPower(-speed);
                rightUp.setPower(speed);
                rightLow.setPower(speed);
            }
            else if(imu.getAngularOrientation().firstAngle > d && imu.getAngularOrientation().firstAngle < degrees){
                speed = 0.3;
                leftLow.setPower(-speed);
                leftUp.setPower(-speed);
                rightUp.setPower(speed);
                rightLow.setPower(speed);
            } else {
                leftUp.setPower(0);
                rightUp.setPower(0);
                leftLow.setPower(0);
                rightLow.setPower(0);
            }
        }
        else if(degrees < 0){
            degrees = imu.getAngularOrientation().firstAngle + degrees;
            degrees = degrees +1;
            double d = degrees + 30;
            if(imu.getAngularOrientation().firstAngle > d){
                leftLow.setPower(speed);
                leftUp.setPower(speed);
                rightUp.setPower(-speed);
                rightLow.setPower(-speed);
            }
            else if(imu.getAngularOrientation().firstAngle < d && imu.getAngularOrientation().firstAngle > degrees){
                speed = 0.3;
                leftLow.setPower(speed);
                leftUp.setPower(speed);
                rightUp.setPower(-speed);
                rightLow.setPower(-speed);
            }
            else{
                leftUp.setPower(0);
                rightUp.setPower(0);
                leftLow.setPower(0);
                rightLow.setPower(0);
            }
        }

            else{leftUp.setPower(0);
            rightUp.setPower(0);
            leftLow.setPower(0);
            rightLow.setPower(0);}
    }

    public void moveSideways(double targetDistanceMM, String direction, double speed){
        if(direction.equals("right")) {
            if(targetDistanceMM > sensorE.getDistance(DistanceUnit.MM)){
               // telemetry.addData("TargetDistance too large", targetDistanceMM-sensorE.getDistance(DistanceUnit.MM));
               // telemetry.update();
            }
            else {
                if(sensorE.getDistance(DistanceUnit.MM) < 1300) {
                    double distance = sensorE.getDistance(DistanceUnit.MM);
                    double curDistance = sensorE.getDistance(DistanceUnit.MM);
                    while ((distance - curDistance < targetDistanceMM) && curDistance < 1300) {
                        leftLow.setPower(speed);
                        leftUp.setPower(-speed);
                        rightLow.setPower(-speed);
                        rightUp.setPower(speed);
                        curDistance = sensorE.getDistance(DistanceUnit.MM);
                    }
                    speed = 0;
                    leftLow.setPower(speed);
                    leftUp.setPower(speed);
                    rightLow.setPower(speed);
                    rightUp.setPower(speed);
                }
                else if(sensorW.getDistance(DistanceUnit.MM) < 1300){
                    double distance = sensorW.getDistance(DistanceUnit.MM);
                    double curDistance = sensorW.getDistance(DistanceUnit.MM);
                    while((curDistance-distance < targetDistanceMM) && curDistance < 1300){
                        leftLow.setPower(speed);
                        leftUp.setPower(-speed);
                        rightLow.setPower(-speed);
                        rightUp.setPower(speed);
                        curDistance = sensorW.getDistance(DistanceUnit.MM);
                    }
                    speed = 0;
                    leftLow.setPower(speed);
                    leftUp.setPower(speed);
                    rightLow.setPower(speed);
                    rightUp.setPower(speed);
                }
                else{
                    speed = 0;
                    leftLow.setPower(speed);
                    leftUp.setPower(-speed);
                    rightLow.setPower(-speed);
                    rightUp.setPower(speed);
                }
            }
        }
        else{
            if(targetDistanceMM > sensorW.getDistance(DistanceUnit.MM)){
               // telemetry.addData("TargetDistance too large", targetDistanceMM-sensorW.getDistance(DistanceUnit.MM));
            }
            else {
                if(sensorW.getDistance(DistanceUnit.MM) < 1300) {
                    double distance = sensorW.getDistance(DistanceUnit.MM);
                    double curDistance = sensorW.getDistance(DistanceUnit.MM);
                    while ((distance - curDistance < targetDistanceMM) && curDistance < 1300) {
                        leftLow.setPower(-speed);
                        leftUp.setPower(speed);
                        rightLow.setPower(speed);
                        rightUp.setPower(-speed);
                        curDistance = sensorW.getDistance(DistanceUnit.MM);
                    }
                    speed = 0;
                    leftLow.setPower(speed);
                    leftUp.setPower(speed);
                    rightLow.setPower(speed);
                    rightUp.setPower(speed);
                }
                else if(sensorE.getDistance(DistanceUnit.MM) < 1300){
                    double distance = sensorE.getDistance(DistanceUnit.MM);
                    double curDistance = sensorE.getDistance(DistanceUnit.MM);
                    while ((curDistance - distance < targetDistanceMM) && curDistance < 1300) {
                        leftLow.setPower(-speed);
                        leftUp.setPower(speed);
                        rightLow.setPower(speed);
                        rightUp.setPower(-speed);
                        curDistance = sensorE.getDistance(DistanceUnit.MM);
                    }
                    speed = 0;
                    leftLow.setPower(speed);
                    leftUp.setPower(speed);
                    rightLow.setPower(speed);
                    rightUp.setPower(speed);
                }
                else{
                    speed = 0;
                    leftLow.setPower(-speed);
                    leftUp.setPower(speed);
                    rightLow.setPower(speed);
                    rightUp.setPower(-speed);
                }
            }
        }
        leftLow.setPower(0);
        leftUp.setPower(0);
        rightLow.setPower(0);
        rightUp.setPower(0);
    }

    public void moveStraight(Double targetDistanceMM, String direction, double speed){
        if(direction.toLowerCase().equals("forward")){
            if(targetDistanceMM > sensorN.getDistance(DistanceUnit.MM)){
                telemetry.addData("TargetDistance too large", targetDistanceMM-sensorN.getDistance(DistanceUnit.MM));
            }
            else {
                if (sensorN.getDistance(DistanceUnit.MM) < 1300) {
                    double distance = sensorN.getDistance(DistanceUnit.MM);
                    double curDistance = sensorN.getDistance(DistanceUnit.MM);
                    while ((distance - curDistance < targetDistanceMM) && curDistance < 1300) {
                        leftLow.setPower(-speed);
                        leftUp.setPower(-speed);
                        rightLow.setPower(-speed);
                        rightUp.setPower(-speed);
                        curDistance = sensorN.getDistance(DistanceUnit.MM);
                    }
                    speed = 0;
                    leftLow.setPower(speed);
                    leftUp.setPower(speed);
                    rightLow.setPower(speed);
                    rightUp.setPower(speed);
                } else if(sensorS.getDistance(DistanceUnit.MM) < 1300){
                    double distance = sensorS.getDistance(DistanceUnit.MM);
                    double curDistance = sensorS.getDistance(DistanceUnit.MM);
                    while ((curDistance - distance < targetDistanceMM) && curDistance < 1300) {
                        leftLow.setPower(-speed);
                        leftUp.setPower(-speed);
                        rightLow.setPower(-speed);
                        rightUp.setPower(-speed);
                        curDistance = sensorS.getDistance(DistanceUnit.MM);
                    }
                    speed = 0;
                    leftLow.setPower(speed);
                    leftUp.setPower(speed);
                    rightLow.setPower(speed);
                    rightUp.setPower(speed);
                }
                else{
                    speed = 0;
                    leftLow.setPower(-speed);
                    leftUp.setPower(-speed);
                    rightLow.setPower(-speed);
                    rightUp.setPower(-speed);
                }
            }
        }
        else{
            if(targetDistanceMM > sensorS.getDistance(DistanceUnit.MM)){
                telemetry.addData("TargetDistance too large", targetDistanceMM-sensorS.getDistance(DistanceUnit.MM));
                moveStraight(sensorS.getDistance(DistanceUnit.MM) - 20.0, direction, speed);
            }
            else {
                if (sensorS.getDistance(DistanceUnit.MM) < 1300) {
                    double distance = sensorS.getDistance(DistanceUnit.MM);
                    double curDistance = sensorS.getDistance(DistanceUnit.MM);
                    while ((distance - curDistance < targetDistanceMM) && curDistance < 1300) {
                        leftLow.setPower(speed);
                        leftUp.setPower(speed);
                        rightLow.setPower(speed);
                        rightUp.setPower(speed);
                        curDistance = sensorS.getDistance(DistanceUnit.MM);
                    }
                    speed = 0;
                    leftLow.setPower(speed);
                    leftUp.setPower(speed);
                    rightLow.setPower(speed);
                    rightUp.setPower(speed);
                }
                else if(sensorN.getDistance(DistanceUnit.MM) < 1300){
                    double distance = sensorN.getDistance(DistanceUnit.MM);
                    double curDistance = sensorN.getDistance(DistanceUnit.MM);
                    while ((curDistance - distance < targetDistanceMM) && curDistance < 1300) {
                        leftLow.setPower(speed);
                        leftUp.setPower(speed);
                        rightLow.setPower(speed);
                        rightUp.setPower(speed);
                        curDistance = sensorN.getDistance(DistanceUnit.MM);
                    }
                    speed = 0;
                    leftLow.setPower(speed);
                    leftUp.setPower(speed);
                    rightLow.setPower(speed);
                    rightUp.setPower(speed);
                }
                else{
                    speed = 0;
                    leftLow.setPower(speed);
                    leftUp.setPower(speed);
                    rightLow.setPower(speed);
                    rightUp.setPower(speed);
                }
            }
        }
        leftLow.setPower(0);
        leftUp.setPower(0);
        rightLow.setPower(0);
        rightUp.setPower(0);
    }

}
