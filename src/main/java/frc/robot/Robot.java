// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.loops.Looper;
import frc.lib.utils.CrashTracker;
import frc.robot.auto.AutoModeExecutor;
import frc.robot.auto.modes.AutoModeBase;
import frc.robot.controlboard.ControlBoard;
import frc.robot.subsystems.SwerveCancoders;
import frc.robot.subsystems.Test;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //TODO ControlBoard

  private final Looper mEnabledLooper = new Looper(Constants.kLooperDt);
  private final Looper mDisabledLooper = new Looper(Constants.kLooperDt);

  private AutoModeExecutor mAutoModeExecutor = new AutoModeExecutor();
  private AutoModeSelector mAutoModeSelector = new AutoModeSelector();

  private ControlBoard mControlBoard = new ControlBoard();

  private final SubsystemManager mSubsystemManager = SubsystemManager.getInstance();
  private final Test mTest;
  private final SwerveCancoders mCancoders;

  public Robot() {
      super();
      // Dirty swerve init hack step 1: WaitForNumBannerSensorsAction for cancoders to init
      mCancoders = SwerveCancoders.getInstance();
      double startInitTs = Timer.getFPGATimestamp();
      System.out.println("* Starting to init cancoders at ts " +  startInitTs);
      while (Timer.getFPGATimestamp() - startInitTs < Constants.kCancoderBootAllowanceSeconds && !mCancoders.allHaveBeenInitialized()) {
          Timer.delay(0.1);
      }
      System.out.println("* Cancoders all inited: Took " + (Timer.getFPGATimestamp() - startInitTs) + " seconds");

      // Dirty swerve init hack step 2: Build all the rest of the subsystems
      mTest = Test.getInstance();
  }
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
      CrashTracker.logRobotInit();
      try {
          mSubsystemManager.setSubsystems(
              mTest
          );
      } catch (Throwable t){
          CrashTracker.logThrowableCrash(t);
          throw t;
      }
  }

  @Override
  public void robotPeriodic() {
      try {
          mSubsystemManager.outputToSmartDashboard();
          mAutoModeSelector.outputToSmartDashboard();
          SmartDashboard.putNumber("Timestamp", Timer.getFPGATimestamp());
      } catch (Throwable t) {
          CrashTracker.logThrowableCrash(t);
          throw t;
      }
  }

  @Override
  public void autonomousInit() {
      try {
          CrashTracker.logAutoInit();

          mDisabledLooper.stop();
          mSubsystemManager.stop();
          mEnabledLooper.start();
          mAutoModeExecutor.start();
      } catch (Throwable t) {
          CrashTracker.logThrowableCrash(t);
          throw t;
      }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
        try {
            CrashTracker.logTeleopInit();
            mDisabledLooper.stop();
            mSubsystemManager.stop();
            mEnabledLooper.start();
        } catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
        }
  }

  @Override
  public void teleopPeriodic() {
        try {
            mControlBoard.onLoop();
        } catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
        }
  }

  @Override
  public void disabledInit() {
        CrashTracker.logDisabledInit();
        try {
            mEnabledLooper.stop();
            mDisabledLooper.start();
            // Reset all auto mode state.
            if (mAutoModeExecutor != null) {
                mAutoModeExecutor.stop();
            }
            mAutoModeSelector.reset();
            mAutoModeSelector.updateModeCreator();
        } catch (Throwable t) {
            CrashTracker.logThrowableCrash(t);
            throw t;
        }
  }

  @Override
  public void disabledPeriodic() {
        mAutoModeSelector.outputToSmartDashboard();
        mAutoModeSelector.updateModeCreator();
        Optional<AutoModeBase> autoMode = mAutoModeSelector.getAutoMode();
        if (autoMode.isPresent() && autoMode.get() != mAutoModeExecutor.getAutoMode()) {
            System.out.println("Set auto mode to: " + autoMode.get().getClass().toString());
            mAutoModeExecutor.setAutoMode(autoMode.get());
        }
  }

  @Override
  public void testInit() {
        CrashTracker.logTestInit();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
