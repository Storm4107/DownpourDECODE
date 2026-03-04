
package org.firstinspires.ftc.teamcode.pedroPathing; // make sure this aligns with class location

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.ShooterSubsystem;

@Autonomous(name = "BlueTwelveBall")
public class BlueTwelveBall extends OpMode {

    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    private int pathState;
    private final Pose startPose = new Pose(27.8, 131.8, Math.toRadians(145));

    private ShooterSubsystem Shooter;
    private IntakeSubsystem Intake;

    public static Paths PathChain;
    private RevTouchSensor magSensor;



    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public static class Paths {

        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;
        public PathChain Path4;
        public PathChain Path5;
        public PathChain Path6;
        public PathChain Path7;
        public PathChain Path8;
        public PathChain Path9;
        public PathChain Path10;
        public PathChain Path11;

        public Paths(Follower follower) {
            Path1 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(27.800, 131.800),
                                    new Pose(48.200, 95.700)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(145), Math.toRadians(135))
                    .build();

            Path2 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(48.200, 95.700),
                                    new Pose(52.000, 87.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            Path3 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(52.000, 87.000),
                                    new Pose(24.000, 87.000)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path4 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(24.000, 87.000),
                                    new Pose(48.200, 95.700)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();

            Path5 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(48.200, 95.700),
                                    new Pose(52.000, 62.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            Path6 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(52.000, 62.000),
                                    new Pose(24.000, 62.000)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path7 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(24.000, 62.000),
                                    new Pose(15.000, 73.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .build();

            Path8 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(15.000, 73.000),
                                    new Pose(48.200, 95.700)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();

            Path9 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(48.200, 95.700),
                                    new Pose(52.000, 35.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                    .build();

            Path10 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(52.000, 35.000),
                                    new Pose(24.000, 35.000)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path11 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(24.000, 35.000),
                                    new Pose(48.200, 95.700)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();
        }
    }
    ElapsedTime mStateTime = new ElapsedTime();
    int v_state = 0;

    @Override
    public void loop() {
        telemetry.addData("Current Elapsed Time", pathTimer);
        telemetry.update();
        follower.update();
        switch (pathState) {
            case 1:
                //follower.followPath(PathChain.Path1);
                follower.followPath(PathChain.Path1);
                Shooter.SecondAutoShooterOnly();
                setPathState(2);
                break;

            case 2:
                if (!follower.isBusy()) {
                    setPathState(3);
                }
                break;
            case 3:
                Shooter.Rubber();
                Shooter.FastSpinTable();
                telemetry.addData("Current Elapsed Time", pathTimer);
                mStateTime.reset();
                v_state++;
                setPathState(4);
                break;

            case 4:
                if (mStateTime.time() >= 3.5) {
                    Shooter.ShooterIntakeStop();
                    Shooter.StopSpin();
                    setPathState(5);
                }
                break;

            case 5:
                follower.followPath(PathChain.Path2, 1,true);
                setPathState(6);
                break;
            case 6:
                if (!follower.isBusy()) {
                    setPathState(7);
                }
                break;
            case 7:
                Intake.In();
                follower.followPath(PathChain.Path3,.7,true);
                Shooter.SpinTable();
                Intake.In();
                mStateTime.reset();
                v_state++;
                setPathState(8);
                break;
            case 8:
                if (mStateTime.time() >= 1.5) {
                    Shooter.StopSpin();
                    setPathState(9);
                }
                break;
            case 9:
                follower.followPath(PathChain.Path4, 1,true);
                Shooter.Home();
                mStateTime.reset();
                v_state++;
                setPathState(10);
                break;

            case 10:
                if (mStateTime.time() >= 1) {
                    setPathState(11);
                }
                break;
            case 11:
                Shooter.Rubber();
                Shooter.FastSpinTable();
                telemetry.addData("Current Elapsed Time", pathTimer);
                mStateTime.reset();
                v_state++;
                setPathState(12);
                break;
            case 12:
                if (mStateTime.time() >= 4) {
                    Shooter.ShooterIntakeStop();
                    Intake.In();
                    Shooter.StopSpin();
                    setPathState(13);
                }
                break;
            case 13:
                follower.followPath(PathChain.Path5, 1, true);
                setPathState(14);
                break;
            case 14:
                if (mStateTime.time() >= .75) {
                    Shooter.ShooterIntakeStop();
                    Intake.In();
                    Shooter.StopSpin();
                    setPathState(15);
                }
                break;
            case 15:
                if (!follower.isBusy()) {
                    setPathState(16);
                }
                break;
            case 16:
                Intake.In();
                follower.followPath(PathChain.Path6,.7,true);
                Shooter.FastSpinTable();
                Intake.In();
                mStateTime.reset();
                v_state++;
                setPathState(17);
                break;
            case 17:
                if (mStateTime.time() >= 1.5) {
                    Shooter.StopSpin();
                    setPathState(18);
                }
                break;
            case 18:
                if (!follower.isBusy()) {
                    setPathState(19);
                }
                break;
            case 19:
                follower.followPath(PathChain.Path7, 1, true);
                Shooter.Home();
                mStateTime.reset();
                v_state++;
                setPathState(20);
                break;
            case 20:
                if (mStateTime.time() >= 1) {
                    setPathState(21);
                }
                break;
            case 21:
                follower.followPath(PathChain.Path8,1, true);
                Shooter.Home();
                mStateTime.reset();
                v_state++;
                setPathState(22);
                break;
            case 22:
                if (mStateTime.time() >= 1) {
                    setPathState(23);
                }
                break;
            case 23:
                if (!follower.isBusy()) {
                    setPathState(24);
                }
                break;
            case 24:
                Shooter.Rubber();
                Intake.In();
                Shooter.FastSpinTable();
                telemetry.addData("Current Elapsed Time", pathTimer);
                mStateTime.reset();
                v_state++;
                setPathState(25);
                break;
            case 25:
                if (mStateTime.time() >= 3.5) {
                    Shooter.StopSpin();
                    Shooter.ShooterIntakeStop();
                    setPathState(26);
                }
                break;
            case 26:
                if (!follower.isBusy()) {
                    setPathState(27);
                }
                break;
            case 27:
                follower.followPath(PathChain.Path9, 1,false);
                setPathState(28);
                break;
            case 28:
                if (!follower.isBusy()) {
                    setPathState(29);
                }
                break;
            case 29:
                Intake.In();
                follower.followPath(PathChain.Path10,.7,true);
                Shooter.SpinTable();
                Intake.In();
                mStateTime.reset();
                v_state++;
                setPathState(30);
                break;
            case 30:
                if (mStateTime.time() >= 2) {
                    Shooter.StopSpin();
                    setPathState(31);
                }
                break;
            case 31:
                if (!follower.isBusy()) {
                    Intake.Reverse();
                    setPathState(32);
                }
                break;
            case 32:
                follower.followPath(PathChain.Path11, true);
                Shooter.Home();
                mStateTime.reset();
                v_state++;
                setPathState(33);
                break;

            case 33:
                if (mStateTime.time() >= 2) {
                    setPathState(34);
                }
                break;
            case 34:
                Shooter.Rubber();
                Shooter.FastSpinTable();
                telemetry.addData("Current Elapsed Time", pathTimer);
                mStateTime.reset();
                v_state++;
                setPathState(35);
                break;
            case 35:
                if (mStateTime.time() >= 3.5) {
                    Shooter.ShooterIntakeStop();
                    Intake.In();
                    Shooter.StopSpin();
                    setPathState(36);
                }
                break;
            case 36:
                if (!follower.isBusy()) {
                    setPathState(37);
                }
                break;

        }



        // These loop the movements of the robot, these must be called continuously in order to work
        follower.update();
        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Shooter = new ShooterSubsystem(hardwareMap);
        Intake = new IntakeSubsystem(hardwareMap);
        follower = Constants.createFollower(hardwareMap);
        PathChain = new Paths(follower);
        follower.setStartingPose(startPose);
        magSensor = hardwareMap.get(RevTouchSensor.class, "magSensor");


        telemetry.addData("Magnet", magSensor.getValue());
        telemetry.update();
    }

    @Override
    public void init_loop() {
        if (magSensor.isPressed()) Shooter.StopSpin();
        else Shooter.FastSpinTable();
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setpathState(0);
    }

    public void setpathState(int i) {
        pathState++;
        telemetry.addData("Path State Set", pathState);
    }

    @Override
    public void stop() {}


}
