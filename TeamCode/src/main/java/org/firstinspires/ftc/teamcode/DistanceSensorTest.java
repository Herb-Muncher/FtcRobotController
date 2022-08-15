package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "DistanceSensorTest", group = "GameChangers")
//@Disabled
public class DistanceSensorTest extends LinearOpMode{
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

    @Override
    public void runOpMode(){

        telemetry.addLine("I'm here before innitialize");
        telemetry.update();
        sleep(5000);

        initialize();

        waitForStart();
        while(opModeIsActive()){

            telemetry.addLine("Im here");
            telemetry.update();
            telemetry.addData("IMU Z:", imu.getAngularOrientation().firstAngle);
            telemetry.addData("","");

            telemetry.addData("sensorN",sensorN.getDeviceName() );
            telemetry.addData("range", String.format("%.01f mm", sensorN.getDistance(DistanceUnit.MM)));
            telemetry.addData("range", String.format("%.01f cm", sensorN.getDistance(DistanceUnit.CM)));
            telemetry.addData("range", String.format("%.01f m", sensorN.getDistance(DistanceUnit.METER)));
            telemetry.addData("range", String.format("%.01f in", sensorN.getDistance(DistanceUnit.INCH)));

            telemetry.addData("","");

            telemetry.addData("sensorW",sensorW.getDeviceName() );
            telemetry.addData("range", String.format("%.01f mm", sensorW.getDistance(DistanceUnit.MM)));
            telemetry.addData("range", String.format("%.01f cm", sensorW.getDistance(DistanceUnit.CM)));
            telemetry.addData("range", String.format("%.01f m", sensorW.getDistance(DistanceUnit.METER)));
            telemetry.addData("range", String.format("%.01f in", sensorW.getDistance(DistanceUnit.INCH)));

            telemetry.addData("","");

            telemetry.addData("sensorE",sensorE.getDeviceName() );
            telemetry.addData("range", String.format("%.01f mm", sensorE.getDistance(DistanceUnit.MM)));
            telemetry.addData("range", String.format("%.01f cm", sensorE.getDistance(DistanceUnit.CM)));
            telemetry.addData("range", String.format("%.01f m", sensorE.getDistance(DistanceUnit.METER)));
            telemetry.addData("range", String.format("%.01f in", sensorE.getDistance(DistanceUnit.INCH)));

            telemetry.addData("","");

            telemetry.addData("SensorS",sensorS.getDeviceName() );
            telemetry.addData("range", String.format("%.01f mm", sensorS.getDistance(DistanceUnit.MM)));
            telemetry.addData("range", String.format("%.01f cm", sensorS.getDistance(DistanceUnit.CM)));
            telemetry.addData("range", String.format("%.01f m", sensorS.getDistance(DistanceUnit.METER)));
            telemetry.addData("range", String.format("%.01f in", sensorS.getDistance(DistanceUnit.INCH)));

            telemetry.update();
        }
    }
    public void initialize(){
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

        point = new Point(sensorN, sensorW, sensorE, sensorS, imu, leftLow, leftUp, rightLow, rightUp);
    }
}