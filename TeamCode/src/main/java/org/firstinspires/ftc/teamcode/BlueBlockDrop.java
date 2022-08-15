package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "BlueBlockDrop", group = "FreightFrenzy")

public class BlueBlockDrop extends LinearOpMode {
    private BNO055IMU.Parameters params = new BNO055IMU.Parameters();

    private Point point = new Point();
    private BlueDelivery delivery = new BlueDelivery();
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

    private WebcamName webcam;

    //tensor flow declarations

    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };

    private static final String VUFORIA_KEY = "AXxpalT/////AAABmdnVUkg/RE90vdspn0evQAROwOn0BieX3843TGsRcRUpKMh7xbF+agvkEspF/5teGXW1RFWyKvPYBTTbvU8KyqGVpe/KCiI+RUltWoMFpokHOq3zQND+QKd2E2mm+UH8lT2Bgn+nZ74YLLTLJQIG74+Jw25Pv1wUiuXnhg+67LGWpX/NnIwh1PrNLuGqWTDDl0t/oxVWxLxZ0S9KN4c0t3gkiy2mzA1G77bpUhqfPksWopwoeI2N7FOVOM5RHW38Lpy4tiBEbIQooM3yoAKDYlrUW3ZFMuUt17I8b+9YO8j4B/OlEMbDbYNfvd+XDqviWUGDDrHT/52T0lfJ7Bv71d2BqeTGN7+gmg/KGmHRtWRR";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    public void runOpMode(){

        initialize();
        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(1.0, 16.0/9.0);
        }

        waitForStart();
        while(opModeIsActive()){

            double position = 0.0;

            double b = Math.random()*3;
            telemetry.addData("Double ", b);
            int a = (int)b + 1;

            telemetry.addData("Level", a);
            telemetry.update();

            /*if(a == 3){
                delivery.upperDrop();
            }
            else if(a == 2) {
                delivery.middleDrop();
            }
            else{
                delivery.lowerDrop();
            }*/
            //delivery.upperDrop();

            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    boolean found = false;
                    ElapsedTime runTime = new ElapsedTime();
                    runTime.reset();
                    while(!found){
                        updatedRecognitions = tfod.getUpdatedRecognitions();
                        if(updatedRecognitions != null) {
                            telemetry.addData("# Object Detected", updatedRecognitions.size());
                            int j = 0;
                            for(Recognition recognition : updatedRecognitions){
                                telemetry.addData("Object Detected", recognition.getLabel());
                                telemetry.addData("Object Location", recognition.getLeft());
                                telemetry.addData("Object Confidence", recognition.getConfidence());
                                j++;
                                if(recognition.getLabel() == "Duck"){
                                    found = true;
                                    position = recognition.getLeft();

                                }
                                else{

                                }
                            }
                        }
                        if(runTime.seconds() > 5){
                            delivery.lowerDrop();
                            stop();
                        }
                        telemetry.addData("Position", position);
                        telemetry.addLine("");
                        telemetry.addData("Runtime", runTime.seconds());
                        telemetry.update();
                    }
                    if(position > 500){
                        delivery.lowerDrop();
                        telemetry.addData("position", position);
                        telemetry.addData("delivery", "upperDrop");

                    }
                    else if(position < 200){
                        delivery.upperDrop();
                        telemetry.addData("position", position);
                        telemetry.addData("delivery", "lowerDrop");
                    }
                    else{
                        delivery.middleDrop();
                        telemetry.addData("position", position);
                        telemetry.addData("delivery", "middleDrop");
                    }
                    telemetry.update();

                    // step through the list of recognitions and display boundary info.
                   /* int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        i++;
                    }
                    telemetry.update();
                    sleep(5000);*/
                }
            }


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

        arm = hardwareMap.get(DcMotorEx.class, "arm");
        claw1 = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        actuate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        point = new Point(sensorN, sensorW, sensorE, sensorS, imu, leftLow, leftUp, rightLow, rightUp);
        delivery = new BlueDelivery(sensorN, sensorW, sensorE, sensorS, imu, leftLow, leftUp, rightLow, rightUp, arm, actuate, spin, claw1, claw2);
    }
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        webcam = hardwareMap.get(WebcamName.class, "Webcam 1");
        parameters.cameraName = webcam;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.3f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
}
