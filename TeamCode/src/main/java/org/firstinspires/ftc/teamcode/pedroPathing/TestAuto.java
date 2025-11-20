package org.firstinspires.ftc.teamcode.pedroPathing; // make sure this aligns with class location

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.paths.PathConstraints;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Subsystems.ShooterSubsystem;

@Autonomous(name = "TestAuto")
public class TestAuto extends OpMode {

    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    private int pathState;
    private final Pose startPose = new Pose(116.3, 131.8, Math.toRadians(36));

    private ShooterSubsystem Shooter;
    private IntakeSubsystem Intake;

    public static Paths PathChain;



    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public static class Paths {

        private static final PathConstraints SLOW_PATH_CONSTRAINTS = new PathConstraints(
                0.495,
                6
        );

        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;

        public Paths(Follower follower) {
            Path1 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(116.300, 131.800), new Pose(101, 100))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(36), Math.toRadians(45))
                    .build();

            Path2 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(101.4, 100), new Pose(101.4, 83.400))
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
                    .build();

            Path3 = follower
                    .pathBuilder()
                    .addPath(
                            new BezierLine(new Pose(101.4, 83.400), new Pose(120.000, 83.400))
                    )
                    .setConstraints(SLOW_PATH_CONSTRAINTS)
                    .setTangentHeadingInterpolation()
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
                    follower.followPath(PathChain.Path1);
                    Shooter.Shoot();
                    setPathState(2);
                break;

            case 2:
                if (!follower.isBusy()) {
                        setPathState(3);
                }
                break;
            case 3:
                Shooter.Shoot();
                Shooter.SpinTable();
                telemetry.addData("Current Elapsed Time", pathTimer);
                mStateTime.reset();
                v_state++;
                setPathState(4);
                break;

            case 4:
                if (mStateTime.time() >= 8.0) {
                    Shooter.Stop();
                   setPathState(5);
                }
                break;

            case 5:
                follower.followPath(PathChain.Path2);
                setPathState(6);
                break;
            case 6:
                if (!follower.isBusy()) {
                    setPathState(7);
                }
                break;
            case 7:
                Intake.In();
                follower.followPath(PathChain.Path3);
                    setPathState(8);
                break;
            case 8:
                if (!follower.isBusy()) {
                    setPathState(9);
                }
                break;
            case 9:
                Intake.stop();
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