package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.seattlesolvers.solverslib.controller.PController;
import com.seattlesolvers.solverslib.controller.PDController;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;

public class ShooterSubsystem {

    private DcMotor ShooterIntake;
    private DcMotorEx Shooter;

    private RevTouchSensor magSensor;
    private DcMotor TurnTable;
    private HardwareMap hardwareMap;

    private double targetVelocity = 0.0;
    public void setTargetVelocity(double velocity) {
        this.targetVelocity = velocity;
    }

    public void update() {
    }


    private static final double NOMINAL_VOLTAGE = 13.0;


    public ShooterSubsystem(HardwareMap hardwareMap) {
        ShooterIntake = hardwareMap.get(DcMotor.class, "ShooterIntake");
        Shooter = hardwareMap.get(DcMotorEx.class, "Shooter");
        TurnTable = hardwareMap.get(DcMotor.class, "TurnTable");

        magSensor = hardwareMap.get(RevTouchSensor.class, "magSensor");

        Shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Shooter.setVelocityPIDFCoefficients(20,0,1.5,1.5);


    }

    /*public void ShootPID() {
        ShooterIntake.setPower(-1);
        Shooter.getZeroPowerBehavior();
        Shooter.setVelocity(3050);
    }
    public void AutoShootPID() {
        ShooterIntake.setPower(-1);
        Shooter.getZeroPowerBehavior();
        Shooter.setVelocity(3000);
    }

     */

    public void ShooterOnly() {
        Shooter.getZeroPowerBehavior();
        Shooter.setVelocity(2700);
    }

    public void PartialShooterOnly() {
        Shooter.getZeroPowerBehavior();
        Shooter.setVelocity(2500);
    }

    /*public void FullShootPID() {
        ShooterIntake.setPower(-1);
        Shooter.setVelocity(4800);
        Shooter.getZeroPowerBehavior();
    }

     */

    public void FullShooterOnly() {
        Shooter.getZeroPowerBehavior();
        Shooter.setVelocity(3200);
    }

    /*public void MaxShootPID() {
        ShooterIntake.setPower(-1);
        Shooter.getZeroPowerBehavior();
        Shooter.setVelocity(4000);
    }
    public void AutoMaxShootPID() {
        ShooterIntake.setPower(-1);
        Shooter.getZeroPowerBehavior();
        Shooter.setVelocity(3875);
    }

     */
    public void MaxShooterOnly() {
        Shooter.getZeroPowerBehavior();
        Shooter.setVelocity(3100);
    }

    public void ShooterOnlyFull() {
        Shooter.getZeroPowerBehavior();
        Shooter.setVelocity(4800);

    }

    /*public void PartialShoot() {
        ShooterIntake.setPower(-1);
        Shooter.getZeroPowerBehavior();
        Shooter.setVelocity(2850);
    }

     */
        public void Stop () {
            ShooterIntake.setPower(0);
            Shooter.setPower(0);
        }
        public void ShooterStop() {
        Shooter.setPower(0);
        }
        public void ShooterIntakeStop() {
        ShooterIntake.setPower(0);
        }
        public void Rubber() {
            ShooterIntake.setPower(-1);
        }

        public void StopShooterPID () {
            setTargetVelocity(0);
        }

        public void Home() {
            if (magSensor.isPressed()) TurnTable.setPower(0);
            else TurnTable.setPower(.5);
        }

        public void SpinTable () {
            TurnTable.setPower(.4);
        }
        public void FasterSpinTable() {
            TurnTable.setPower(.45);
        }
        public void FastSpinTable () {
            TurnTable.setPower(.6);
        }

        public void ReverseSpinTable () {
            TurnTable.setPower(-.3);
        }

        public void StopSpin () {
            TurnTable.setPower(0);

        }
    }

