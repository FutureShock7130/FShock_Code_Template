package frc.robot.controlboard;

import frc.robot.subsystems.Test;

// Define the structure functions here, and bind it to the Controller at ControlBoard Class.
public class DriverControlBoard {

    private final Test mTest = Test.getInstance();
    
    public void Function1() {
        mTest.doNoop();
    }
}
