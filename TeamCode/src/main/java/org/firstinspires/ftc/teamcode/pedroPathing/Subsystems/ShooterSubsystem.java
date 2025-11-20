package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

public class ShooterSubsystem {

    private DcMotor ShooterIntake;
    private DcMotor Shooter;
    private DcMotor TurnTable;

    public ShooterSubsystem(HardwareMap hardwareMap) {
        ShooterIntake = hardwareMap.get(DcMotor.class, "ShooterIntake");
        Shooter = hardwareMap.get(DcMotor.class, "Shooter");
        TurnTable = hardwareMap.get(DcMotor.class, "TurnTable");
    }

    public void Shoot() {
        ShooterIntake.setPower(-1);
        Shooter.setPower(.9);
        Shooter.getZeroPowerBehavior();
    }

    public void FullShoot() {
        ShooterIntake.setPower(-1);
        Shooter.setPower(1);
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
