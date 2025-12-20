
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

@Autonomous(name = "RedLong")
public class RedLong extends OpMode {

    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    private int pathState;
    private final Pose startPose = new Pose(87, 9, Math.toRadians(90));

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

        public Paths(Follower follower) {
            Path1 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(87.000, 9.000), new Pose(85.000, 15.000))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(67))
                    .build();

            Path2 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(85.000, 15.000), new Pose(98.000, 35.000))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(67), Math.toRadians(0))
                    .build();

            Path3 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(98.000, 35.000), new Pose(120.000, 35.000))
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path4 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(120.000, 35.000), new Pose(85.000, 15.000))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(67))
                    .build();

            Path5 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(85.000, 15.000), new Pose(100, 15.000))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(67), Math.toRadians(0))
                    .build();

            Path6 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(100, 15.000), new Pose(135.000, 15.000))
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path7 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(135.000, 15.000), new Pose(100, 16.000))
                    )
                    
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90))
                    .build();
            /*Path7 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(135.000, 15.000), new Pose(85.000, 15.000))
                    )

                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(67))
                    .build();

             */
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
                follower.followPath(PathChain.Path1);
                Shooter.MaxShooterOnly();
                mStateTime.reset();
                v_state++;
                setPathState(2);
                break;
            case 2:
                if (mStateTime.time() >= 1.5) {
                    setPathState(3);
                }
                break;
            case 3:
                Shooter.Rubber();
                Shooter.FasterSpinTable();
                telemetry.addData("Current Elapsed Time", pathTimer);
                mStateTime.reset();
                v_state++;
                setPathState(4);
                break;

            case 4:
                if (mStateTime.time() >= 6.0) {
                    Shooter.ShooterIntakeStop();
                    Shooter.StopSpin();
                    setPathState(5);
                }
                break;

            case 5:
                follower.followPath(PathChain.Path2,1,true);
                setPathState(6);
                break;
            case 6:
                if (!follower.isBusy()) {
                    setPathState(7);
                }
                break;
            case 7:
                Intake.In();
                follower.followPath(PathChain.Path3,.35,true);
                Shooter.FastSpinTable();
                Intake.In();
                mStateTime.reset();
                v_state++;
                setPathState(8);
                break;
            case 8:
                if (mStateTime.time() >= 3.5) {
                    Intake.stop();
                    Shooter.StopSpin();
                    setPathState(9);
                }
                break;
            case 9:
                follower.followPath(PathChain.Path4);
                Shooter.Home();
                mStateTime.reset();
                v_state++;
                setPathState(10);
                break;

            case 10:
                if (mStateTime.time() >= 2) {
                    setPathState(11);
                }
                break;
            case 11:
                Shooter.Rubber();
                Shooter.FasterSpinTable();
                telemetry.addData("Current Elapsed Time", pathTimer);
                mStateTime.reset();
                v_state++;
                setPathState(12);
                break;
            case 12:
                if (mStateTime.time() >= 6.0) {
                    Shooter.Stop();
                    Shooter.StopSpin();
                    setPathState(13);
                }
                break;
            case 13:
                follower.followPath(PathChain.Path5,1,true);
                setPathState(14);
                break;
            case 14:
                if (!follower.isBusy()) {
                    setPathState(15);
                }
            case 15:
                Intake.In();
                follower.followPath(PathChain.Path6,.4,true);
                Shooter.FastSpinTable();
                Intake.In();
                mStateTime.reset();
                v_state++;
                setPathState(16);
                break;
            case 16:
                if (mStateTime.time() >= 4) {
                    Intake.stop();
                    Shooter.StopSpin();
                    setPathState(17);
                }
                break;
            case 17:
                follower.followPath(PathChain.Path7,1,true);
                setPathState(18);
                break;
            case 18:
                if (!follower.isBusy()) {
                    setPathState(19);
                }
                break;
            /*case 17:
                follower.followPath(PathChain.Path7);
                Shooter.MaxShooterOnly();
                Shooter.Home();
                mStateTime.reset();
                v_state++;
                setPathState(18);
                break;
            case 18:
                if (mStateTime.time() >= 2) {
                    setPathState(19);
                }
                break;
            case 19:
                Shooter.MaxShootPID();
                Shooter.FasterSpinTable();
                telemetry.addData("Current Elapsed Time", pathTimer);
                mStateTime.reset();
                v_state++;
                setPathState(20);
                break;
            case 20:
                if (mStateTime.time() >= 6.0) {
                    Shooter.Stop();
                    Shooter.StopSpin();
                    setPathState(21);
                }

             */

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
