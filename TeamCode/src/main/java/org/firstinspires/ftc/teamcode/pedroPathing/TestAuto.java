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

@Autonomous(name = "TestAuto")
public class TestAuto extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    private final Pose startPose = new Pose(116.3, 131.8, Math.toRadians(36));
    private final Pose firstScore = new Pose(97, 96, Math.toRadians(47));
    private final Pose pickupPrepare = new Pose(97,83.4, Math.toRadians(0));
    private final Pose firstPickup = new Pose(120, 83.4, Math.toRadians(0));
    private Path ScorePreload, scorePickup1, Pickup2, scorePickup2;
    private PathChain Pickup1;


    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
    public void buildPaths() {

        ScorePreload = new Path(new BezierLine(startPose, firstScore));
        ScorePreload.setLinearHeadingInterpolation(startPose.getHeading(), firstScore.getHeading());


        Pickup1 = follower.pathBuilder()
                .addPath(new BezierCurve(firstScore, pickupPrepare,firstPickup))
                .setLinearHeadingInterpolation(firstPickup.getHeading(), pickupPrepare.getHeading(), firstPickup.getHeading())
                .build();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(ScorePreload);
                setPathState(1);
                break;
            case 1:
                if(!follower.isBusy()) {
                    follower.followPath(Pickup1);
                    setPathState(2);
                }
                break;
            case 2:
                follower.followPath(Pickup1);
                setPathState(-1);
                break;
        }
    }

    @Override
    public void loop() {

        // These loop the movements of the robot, these must be called continuously in order to work
        follower.update();
        autonomousPathUpdate();

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


        follower = Constants.createFollower(hardwareMap);
        buildPaths();
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