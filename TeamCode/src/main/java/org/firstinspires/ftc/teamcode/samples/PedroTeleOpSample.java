package org.firstinspires.ftc.teamcode.samples;


import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.util.TelemetryData;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.ShooterSubsystem;

@TeleOp
public class PedroTeleOpSample extends CommandOpMode {
    Follower follower;
    TelemetryData telemetryData = new TelemetryData(telemetry);
    private IntakeSubsystem Intake;
    private ShooterSubsystem Shooter;

    private RevTouchSensor magSensor;


    @Override
    public void initialize() {
        follower = Constants.createFollower(hardwareMap);
        super.reset();

        follower.startTeleopDrive();
        Intake = new IntakeSubsystem(hardwareMap);
        Shooter = new ShooterSubsystem(hardwareMap);
        magSensor = hardwareMap.get(RevTouchSensor.class, "magSensor");
    }

    @Override
    public void run() {
        super.run();

        /* Robot-Centric Drive
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        */

        // Field-Centric Drive
        follower.setTeleOpDrive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, false);
        follower.update();

        if (gamepad2.left_bumper) Intake.In();
        else Intake.stop();

        if (gamepad2.x) Shooter.Shoot();
        else if (gamepad2.b) Shooter.FullShoot();
        else if (gamepad2.a) Shooter.PatialShoot();
        else Shooter.Stop();


        if (gamepad2.dpad_right) Shooter.SpinTable();
        else if (gamepad2.dpad_left) Shooter.ReverseSpinTable();
        else Shooter.StopSpin();

        if (gamepad2.dpad_up && magSensor.isPressed()) Shooter.StopSpin();
        else if (gamepad2.dpad_up) Shooter.FastSpinTable();



        telemetryData.addData("X", follower.getPose().getX());
        telemetryData.addData("Y", follower.getPose().getY());
        telemetryData.addData("Heading", follower.getPose().getHeading());
        telemetryData.addData("Magnet", magSensor.getValue());
        telemetryData.update();
    }
}