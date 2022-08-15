package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Gyroscope;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;



@Autonomous(name = "AutoFive", group = "GameChangers")


public class AutoFive extends AutonomousCode {

    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";

    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private static final String VUFORIA_KEY = "AXxpalT/////AAABmdnVUkg/RE90vdspn0evQAROwOn0BieX3843TGsRcRUpKMh7xbF+agvkEspF/5teGXW1RFWyKvPYBTTbvU8KyqGVpe/KCiI+RUltWoMFpokHOq3zQND+QKd2E2mm+UH8lT2Bgn+nZ74YLLTLJQIG74+Jw25Pv1wUiuXnhg+67LGWpX/NnIwh1PrNLuGqWTDDl0t/oxVWxLxZ0S9KN4c0t3gkiy2mzA1G77bpUhqfPksWopwoeI2N7FOVOM5RHW38Lpy4tiBEbIQooM3yoAKDYlrUW3ZFMuUt17I8b+9YO8j4B/OlEMbDbYNfvd+XDqviWUGDDrHT/52T0lfJ7Bv71d2BqeTGN7+gmg/KGmHRtWRR";

    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    private boolean found;

    public void moveForward(DcMotor leftLower, DcMotor leftUpper, DcMotor rightLower, DcMotor rightUpper, int miliseconds, double power, int multiplier) {
        int ms = miliseconds;
        double p = power;
        int mp = multiplier;
        super.moveForward(leftLower, leftUpper, rightLower, rightUpper, ms, p, mp);
    }

    public void spin(DcMotor leftLower, DcMotor leftUpper, DcMotor rightLower, DcMotor rightUpper, int miliseconds, double power, int multiplier, boolean right) {
        int ms = miliseconds;
        double p = power;
        int mp = multiplier;
        boolean r = right;
        super.spin(leftLower, leftUpper, rightLower, rightUpper, ms, p, mp, r);
    }

    public void strafe(DcMotor leftLower, DcMotor leftUpper, DcMotor rightLower, DcMotor rightUpper, int miliseconds, double power, int multiplier, boolean right) {
        int ms = miliseconds;
        double p = power;
        int mp = multiplier;
        boolean r = right;
        super.strafe(leftLower, leftUpper, rightLower, rightUpper, ms, p, mp, r);
    }

    //protected Servo wobbleGrabber;


    @Override
    public void runOpMode() throws InterruptedException {

        initVuforia();
        initTfod();


        //telemetry.addData("Status", "First");
        //telemetry.update();
        lift = hardwareMap.servo.get("lift");
        spin = hardwareMap.crservo.get("spin");

        leftLowerMotor = hardwareMap.dcMotor.get("leftLowerMotor");
        leftUpperMotor = hardwareMap.dcMotor.get("leftUpperMotor");
        rightUpperMotor = hardwareMap.dcMotor.get("rightUpperMotor");
        rightLowerMotor = hardwareMap.dcMotor.get("rightLowerMotor");
        arm = hardwareMap.dcMotor.get("wobbleArm");
        //wheel = hardwareMap.dcMotor.get("wheel");
        telemetry.addData("Status", "Two");
        telemetry.update();

        //imu = hardwareMap.get(Gyroscope.class, "imu");

        wobbleGrabber = hardwareMap.servo.get("wobbleGrabber");

        leftUpperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftLowerMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightUpperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLowerMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        telemetry.addData("Status", "Three");
        telemetry.update();


        leftLowerMotor.setDirection(DcMotor.Direction.REVERSE);
        leftUpperMotor.setDirection(DcMotor.Direction.REVERSE);
        rightLowerMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 1.78 or 16/9).

            // Uncomment the following line if you want to adjust the magnification and/or the aspect ratio of the input images.
            //tfod.setZoom(2.5, 1.78);
        }


        waitForStart();


        telemetry.addData("Status", "Running");
        telemetry.update();

        power = 0.5;

        wobbleGrabber.setPosition(0.0);
        sleep(1500);
        /*wobbleGrabber.setPosition(0.0);
        sleep(1000);
        armPower = 0.75;
        arm.setPower(armPower);
        sleep(500);*/

        AutoThree move = new AutoThree();
        //sleep(1000);

        move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,25,power,-1,false);


        //move.strafe(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 300,power , 1, false);
        move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 200, power, -1);
        move.strafe(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 600, power, -1, false); // if multiplier is negative, right: true moves left
       /* rightLowerMotor.setPower(1.0);
        sleep(500);
        rightLowerMotor.setPower(0.0);*/
        //move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 1550, power, -1);
        move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 1600, power, -1);
        //move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,100,power,1,false);


        found = false;

        int x = 0;

        while(!found && x < 10) {
            if (opModeIsActive()) {

                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    sleep(500);

                    //while (x < 50) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        if (updatedRecognitions.size() == 0) {
                            // empty list.  no objects recognized.
                            telemetry.addData("TFOD", "No items detected.");
                            telemetry.addData("Target Zone", "A");
                            telemetry.addData("x", x);
                            //found = true;
                            telemetry.update();
                            sleep(100);
                            x++;
                            zone = "A";
                        } else {
                            // list is not empty.
                            // step through the list of recognitions and display boundary info.

                            int i = 0;
                            for (Recognition recognition : updatedRecognitions) {
                                telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                        recognition.getLeft(), recognition.getTop());
                                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                        recognition.getRight(), recognition.getBottom());

                                // check label to see which target zone to go after.
                                if (recognition.getLabel().equals("Single")) {
                                    telemetry.addData("Target Zone", "B");
                                    found = true;
                                    zone = "B";
                                } else if (recognition.getLabel().equals("Quad")) {
                                    telemetry.addData("Target Zone", "C");
                                    found = true;
                                    //x+=8;
                                    zone = "C";
                                } else {
                                    telemetry.addData("Target Zone", "UNKNOWN");
                                    found = true;
                                }
                                telemetry.update();
                                sleep(100);
                            }
                        }
                        /*telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                        }*/
                        telemetry.update();
                    }
                    x++;
                    telemetry.update();
                    //}
                }
            }

        }
        /*if(zone.equals("A")){
            //move.strafe(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 150, power, -1, false); // if multiplier is negative, right: true moves left
            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,100,power,-1,false);
        }*/
        move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,150,power,-1,false);

        sleep(500);
        //move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 200, power, -1);
        arm.setPower(-1.0);
        sleep(1450);
        arm.setPower(0.0);
        sleep(1500);

        //move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 200, power, -1);


        if(zone.equals("A")){
            //move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,50,power,-1,false);
            arm.setPower(1.0);
            sleep(290);
            arm.setPower(0.0);
            sleep(200);

            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 500, power, -1);
            wobbleGrabber.setPosition(1.0);
            sleep(500);
            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 500, power, 1);
            //wobbleGrabber.setPosition(0.0);

            //grabs the wobble from position A
            move.moveForward(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1000,power,1);
            //move.strafe(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,200,power,1, true);
            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1160,power,1,false);
            wobbleGrabber.setPosition(1.0);
            arm.setPower(-0.5); // moves arm to position to get wobble?
            sleep(450); // was 400
            arm.setPower(0.0);
            move.moveForward(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,200,power,-1);
            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 400, power, -1);
            wobbleGrabber.setPosition(0.0);
            sleep(500);
            arm.setPower(1.0);
            sleep(500);
            arm.setPower(0.0);

            // drop second wobble
            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1400,power,1,true);
            move.moveForward(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1900,power,-1);
            arm.setPower(-1.0);
            sleep(300);
            arm.setPower(0.0);
            wobbleGrabber.setPosition(1.0);
            sleep(700);
            arm.setPower(1.0);
            sleep(300);
            arm.setPower(0.0);
            move.moveForward(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,700,power,1);

            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,300,power,1,false);
            move.moveForward(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1200,power,-1);
        }
        else if(zone.equals("B")){
            arm.setPower(1.0);
            sleep(300);
            arm.setPower(0.0);


            // drop wobble, lower arm
            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 2000, power, -1);
            move.strafe(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 1400, power, 1, true); // if multiplier is negative, right: true moves left
            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,200,power,1,true);
            sleep(700);
            wobbleGrabber.setPosition(1.0);
            sleep(200);
            arm.setPower(1.0);
            sleep(500);
            arm.setPower(0.0);
            //wobbleGrabber.setPosition(0.0);
            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 1000, power, 1);
            arm.setPower(-1.0);
            sleep(600);
            arm.setPower(0.0);

            // pick up wobble
            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,2300,power,1,true);
            //move.strafe(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 800, power, -1, true); // if multiplier is negative, right: true moves left
            wobbleGrabber.setPosition(1.0);
            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 1200, power, -1);
            wobbleGrabber.setPosition(0.0);
            sleep(500);
            arm.setPower(1.0);
            sleep(800);
            arm.setPower(0.0);

            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,2200,power,1,false);
            //move.strafe(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 1100, power, -1, true); // if multiplier is negative, right: true moves left
            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 2200, power, -1);
            arm.setPower(-1.0);
            sleep(600);
            arm.setPower(0.0);
            wobbleGrabber.setPosition(1.0);
            sleep(500);
            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 500, power, 1);
        }
        else if(zone.equals("C")){ // Zone C
            //move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,25,power,-1,false);
            arm.setPower(1.0);
            sleep(200);
            arm.setPower(0.0);
            sleep(300);


            /*rightLowerMotor.setPower(1.0);
            sleep(1000);*/
            rightLowerMotor.setPower(0.0);
            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,50,power,-1,true);
            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 1500, 1.0, -1);
            sleep(200);
            //move.strafe(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,700,power,-1, true); // CHANGE BACK
            wobbleGrabber.setPosition(1.0);
            sleep(200);
            arm.setPower(1.0);
            sleep(500);
            //wobbleGrabber.setPosition(0.0);
            arm.setPower(0.0);
            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,50,power,1,false);
            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 2500, power, 1);

            // approaches and picks up the second wobble
            move.moveForward(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1300,power,1);
            move.strafe(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,300,power,1, true);
            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1200,power,1,false);
            wobbleGrabber.setPosition(1.0);
            arm.setPower(-1.0);
            sleep(600);
            arm.setPower(0.0);
            sleep(100);
            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 700, power, -1);
            wobbleGrabber.setPosition(0.0);
            sleep(700);
            arm.setPower(1.0);
            sleep(500);
            arm.setPower(0.0);

            // moves to zone C
            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1200,power,1,true);
            move.strafe(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,700,power,-1, true);
            move.moveForward(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1850,1.0,-1);
            //move.strafe(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,700,power,-1, true); // strafe at the end, makes sure we are in the box

            // drops wobble and parks on line
            arm.setPower(-1.0);
            sleep(300);
            arm.setPower(0.0);
            wobbleGrabber.setPosition(1.0);
            sleep(500);
            arm.setPower(1.0);
            sleep(500);
            arm.setPower(0.0);
            move.moveForward(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1200,power,1);
        }

        else{
            // same as zone a
            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 500, power, -1);
            wobbleGrabber.setPosition(1.0);
            sleep(500);
            wobbleGrabber.setPosition(0.0);
            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 500, power, 1);

            //grabs the wobble from position A
            move.moveForward(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1000,power,1);
            move.strafe(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,200,power,1, true);
            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1000,power,1,false);
            wobbleGrabber.setPosition(1.0);
            arm.setPower(-0.5); // moves arm to position to get wobble?
            sleep(400);
            arm.setPower(0.0);
            move.moveForward(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,200,power,-1);
            move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 500, power, -1);
            wobbleGrabber.setPosition(0.0);
            sleep(500);
            arm.setPower(1.0);
            sleep(500);
            arm.setPower(0.0);

            // drop second wobble
            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1200,power,1,true);
            move.moveForward(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1900,power,-1);
            arm.setPower(-1.0);
            sleep(300);
            arm.setPower(0.0);
            wobbleGrabber.setPosition(1.0);
            sleep(700);
            arm.setPower(1.0);
            sleep(300);
            arm.setPower(0.0);
            move.moveForward(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,700,power,1);

            move.spin(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,300,power,1,false);
            move.moveForward(leftLowerMotor,leftUpperMotor,rightLowerMotor,rightUpperMotor,1200,power,-1);
        }

        //move.strafe(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 300,power , 1, false);
        sleep(50000);

        /*leftLowerMotor.setPower(power);
        leftUpperMotor.setPower(power);
        sleep(350);
        leftLowerMotor.setPower(0.0);
        leftUpperMotor.setPower(0.0);

        //arm.setPower(-0.5);
        wobbleGrabber.setPosition(1.0);
        sleep(700);
        //arm.setPower(0.0);
        //arm.setPower(0.5);
        //sleep(700);
        //arm.setPower(0.0);
        move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 500, power, 1);
        wobbleGrabber.setPosition(0.0);

        //wobbleGrabber.setPosition(0.0);
        sleep(1000);

        move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor, 1500, power, 1);
        //wobbleGrabber.setPosition(0.0);
        //move.moveForward(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor,1000, .75, 1); // in this case, for now, positive multiplier is backwards


        //move.spin(leftLowerMotor, leftUpperMotor, rightLowerMotor, rightUpperMotor,5000, 1, 1, true);

            double motorPower = 1.0;
            int miliSeconds = 1750;
            leftLowerMotor.setPower(motorPower);
            leftUpperMotor.setPower(motorPower);
            rightUpperMotor.setPower(motorPower);
            rightLowerMotor.setPower(motorPower);
            sleep(miliSeconds);
            motorPower = 0.0;
            leftLowerMotor.setPower(0.0);
            leftUpperMotor.setPower(0.0);
            rightUpperMotor.setPower(0.0);
            rightLowerMotor.setPower(0.0);*/


    }
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }
}