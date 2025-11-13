package org.firstinspires.ftc.teamcode.pedroPathing; // make sure this aligns with class location

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.ShooterSubsystem;

@Autonomous(name = "TestAuto")
public class TestAuto extends OpMode {

    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    private int pathState;
    private final Pose startPose = new Pose(116.3, 131.8, Math.toRadians(36));

    private ShooterSubsystem Shooter;

    public static Paths PathChain;


    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public static class Paths {

        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;

        public Paths(Follower follower) {
            Path1 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(116.300, 131.800), new Pose(96.300, 95.900))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(36), Math.toRadians(47))
                    .build();

            Path2 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(96.300, 95.900), new Pose(96.300, 83.400))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(47), Math.toRadians(0))
                    .build();

            Path3 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(96.300, 83.400), new Pose(120.000, 83.400))
                    )
                    .setTangentHeadingInterpolation()
                    .build();
        }
    }

    @Override
    public void loop() {

        follower.update();

        switch (pathState) {

            case 0:
                    follower.followPath(PathChain.Path1);
                    Shooter.PatialShoot();
                   // setPathState(1);
                //break;
           /* case 1:   
                if (!follower.isBusy()) {
                    setPathState(2);
                }
                break;
            case 2:
                Shooter.SpinTable();
                if (pathTimer.getElapsedTime() > 8.0) {
                    Shooter.Stop();
                    Shooter.StopSpin();
                }
                    setPathState(3);
                break;
            case 3:
                if (!follower.isBusy()) {
                    setPathState(4);
                }
                break;
            case 4:
                follower.followPath(PathChain.Path2);
                setPathState(5);
                break;
            case 5:
                if (!follower.isBusy()) {
                    setPathState(6);
                }
                break;
            case 6:
                follower.followPath(PathChain.Path3);
                    setPathState(7);
                break;
            case 7:
                if (!follower.isBusy()) {
                    setPathState(8);
                }

            */
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
        follower = Constants.createFollower(hardwareMap);
        PathChain = new Paths(follower);
        follower.setStartingPose(startPose);

    }

    @Override
    public void init_loop() {}

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