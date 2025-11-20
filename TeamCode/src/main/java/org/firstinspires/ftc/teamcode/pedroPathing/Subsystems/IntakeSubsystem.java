package org.firstinspires.ftc.teamcode.pedroPathing.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem {

    private DcMotor Intake;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        Intake = hardwareMap.get(DcMotor.class, "Intake");
    }

    public void In() {
        Intake.setPower(1);
    }

    public void stop() {
        Intake.setPower(0);
    }



}
