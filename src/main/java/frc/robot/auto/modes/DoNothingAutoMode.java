package frc.robot.auto.modes;

import frc.robot.auto.AutoModeEndedException;

public class DoNothingAutoMode extends AutoModeBase {
    @Override
    protected void routine() throws AutoModeEndedException {
        System.out.println("Do nothing auto mode");
    }
}
