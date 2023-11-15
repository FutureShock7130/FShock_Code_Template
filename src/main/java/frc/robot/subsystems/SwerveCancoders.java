package frc.robot.subsystems;

import frc.lib.drivers.Subsystem;

public class SwerveCancoders extends Subsystem {
    //TODO done this shit

    private static SwerveCancoders mInstance = null;

    public static SwerveCancoders getInstance() {
        if (mInstance == null) {
            mInstance = new SwerveCancoders();
        }

        return mInstance;
    }
    
    public boolean allHaveBeenInitialized() {
        return true;
    }
}
