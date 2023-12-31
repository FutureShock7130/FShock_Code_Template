package frc.robot.auto.actions;

import edu.wpi.first.wpilibj.Timer;

public class WaitAction implements Action{
    private final double mTimeToWait;
    private double mStartTime;

    public WaitAction(double timeToWait) {
        mTimeToWait = timeToWait;
    }

    @Override
    public void start() {
        mStartTime = Timer.getFPGATimestamp();
    }

    @Override
    public void update() {}

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - mStartTime >= mTimeToWait;
    }

    @Override
    public void done() {}
}
