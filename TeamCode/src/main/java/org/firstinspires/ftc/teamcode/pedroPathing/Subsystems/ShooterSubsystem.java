package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.controller.PController;
import com.seattlesolvers.solverslib.controller.PDController;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

public class ShooterSubsystem {

    private DcMotor ShooterIntake;
    private DcMotorEx Shooter;
    private DcMotor TurnTable;

    private double targetVelocity = 0.0;
    public void setTargetVelocity(double velocity) {
        this.targetVelocity = velocity;
    }

    public void update() {
    }

    public ShooterSubsystem(HardwareMap hardwareMap) {
        ShooterIntake = hardwareMap.get(DcMotor.class, "ShooterIntake");
        Shooter = hardwareMap.get(DcMotorEx.class, "Shooter");
        TurnTable = hardwareMap.get(DcMotor.class, "TurnTable");
        Shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Shooter.setVelocityPIDFCoefficients(10,0,0,0);
    }

    public void Shoot() {
        ShooterIntake.setPower(-1);
        Shooter.getZeroPowerBehavior();
        Shooter.setVelocity(1200);
    }

    public void ShooterOnly() {
        Shooter.getZeroPowerBehavior();
        Shooter.setVelocity(4000);
    }

    public void FullShoot() {
        ShooterIntake.setPower(-1);
        Shooter.setVelocity(6000);
        Shooter.getZeroPowerBehavior();
    }

    public void PatialShoot() {
        ShooterIntake.setPower(-1);
        Shooter.setPower(.8);
        Shooter.getZeroPowerBehavior();
    }
    public void MiddleShoot() {
        ShooterIntake.setPower(-1);
        Shooter.setPower(.95);
    }

    public void Stop() {
        ShooterIntake.setPower(0);
        Shooter.setPower(0);
    }

    public void StopShooterPID() {
        setTargetVelocity(0);
    }

    public void SpinTable() {
        TurnTable.setPower(.3);
    }
    public void FastSpinTable() {TurnTable.setPower(.6);}

    public void ReverseSpinTable() {
        TurnTable.setPower(-.3);
    }

    public void StopSpin() {
        TurnTable.setPower(0);

    }

}
