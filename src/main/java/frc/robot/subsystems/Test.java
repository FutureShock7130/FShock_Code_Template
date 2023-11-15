package frc.robot.subsystems;

import frc.lib.drivers.Subsystem;

public class Test extends Subsystem {

    private static Test mInstance = null;

    public static Test getInstance() {
        if (mInstance == null) {
            mInstance = new Test();
        }

        return mInstance;
    }

    public void doNoop() {
        //Noop
    }
    
}
