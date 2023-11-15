package frc.robot.controlboard;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;

// Bind the functions to the XboxController.
public class ControlBoard {

    private final DriverControlBoard mDriverControlBoard;
    private final OperatorControlBoard mOperatorControlBoard;
    
    private final XboxController mDriver;
    private final XboxController mOperator;

    public ControlBoard() {
        mDriverControlBoard = new DriverControlBoard();
        mOperatorControlBoard = new OperatorControlBoard();
        mDriver = new XboxController(Constants.kDriverJoystickPort);
        mOperator = new XboxController(Constants.kOperatorJoystickPort);
    }

    public void onLoop() {
        // Driver Controller bindings

        // Operator Controller bindings

    }
}
