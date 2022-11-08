// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

import org.ejml.sparse.csc.decomposition.lu.LuUpLooking_DSCC;

// import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private Victor frontRight = new Victor(0);
  private Victor rearRight = new Victor(3);

  // TODO: add all the drivetrain controllers

  private MotorControllerGroup rightGroup = new MotorControllerGroup(frontRight, rearRight);
  // TODO: Add the left group of motor controllers
  private Victor frontleft = new Victor(1);
  private Victor rearleft = new Victor(2);
  private MotorControllerGroup leftGroup = new MotorControllerGroup(frontleft, rearleft);
  // private AHRS ahrs = new AHRS(SPI.Port.kMXP);
  private AHRS ahrs = new AHRS(SPI.Port.kMXP);
  private XboxController controller = new XboxController(0);

  // TODO: Implement "private DifferentialDrive drive = new
  // DifferentialDrive(leftGroup, rightGroup);"
  private DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);

  private Timer autoTimer = new Timer();
  // {

  // }

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    rightGroup.setInverted(true);
    ahrs.reset();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and
   * test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different
   * autonomous modes using the dashboard. The sendable chooser code works with
   * the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the
   * chooser code and
   * uncomment the getString line to get the auto name from the text box below the
   * Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure
   * below with additional strings. If using the SendableChooser make sure to add
   * them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {

    autoTimer.reset();
    autoTimer.start();
    ahrs.reset();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    driveforward(1.0, 3.3);
    turnRight(3.3, 4.2, 90);
    driveforward(4.2, 6.2);
    turnRight(6.2, 7.1, 180);
    driveforward(7.1, 9.1);
    turnRight(9.1, 10.0, 270);
    driveforward(9.9, 11.7);
    turnRight(11.7, 12.6, 360);
    // // First Side of Square
    // if (autoTimer.get() > 1.0 && autoTimer.get() < 3.26) {
    // drive.arcadeDrive(-0.75, -0.2);
    // }
    // // Stop
    // else if (autoTimer.get() < 3.36) {
    // drive.arcadeDrive(0, 0);
    // }
    // // Turn Right 90
    // else if (autoTimer.get() < 4.36) {
    // // drive.arcadeDrive(0, 1);
    // turnRight(90);
    // }

    // // Stop
    // else if (autoTimer.get() < 5.96) {
    // drive.arcadeDrive(0, 0);
    // }

    // // Second Side of Square
    // else if (autoTimer.get() < 8.06) {
    // drive.arcadeDrive(-0.75, -0.2);
    // }
    // // Stop
    // else if (autoTimer.get() < 8.16) {
    // drive.arcadeDrive(0, 0);
    // }

    // // Turn Right 90
    // else if (autoTimer.get() < 9.16) {
    // // drive.arcadeDrive(0, 1);
    // turnRight(180);
    // }
    // // Stop
    // else if (autoTimer.get() < 9.76) {
    // drive.arcadeDrive(0, 0);
    // }
  }

  // public void turnRight(double targtAngle) {
  // double diff = targtAngle - ahrs.getAngle();

  // if (diff > 40) {
  // drive.arcadeDrive(0, 0.75);

  // } else if (diff > 0) {
  // drive.arcadeDrive(0, 0.45);

  // } else {
  // drive.arcadeDrive(0, 0);
  // }
  // }
  public void driveforward(double x, double y) {
    if (autoTimer.get() > x && autoTimer.get() < y) {
      drive.tankDrive(-0.75, -0.71);
    }
  }
  public void turnRight(double x, double y, double targetAngle) {

    if (autoTimer.get() > x && autoTimer.get() < y) {
      double diff = targetAngle - ahrs.getAngle();

      if (diff > 40) {
        drive.arcadeDrive(0, 0.7);

      } else if (diff > 0) {
        drive.arcadeDrive(0, 0.45);
      } else if (diff < -5) {
        drive.arcadeDrive(0, -0.45);

      } else {
        drive.arcadeDrive(0, 0);
      }
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    // TODO: use drive.arcadeDrive( <value for forward/back> , <value to turn>);

    drive.arcadeDrive(controller.getRightY(), controller.getRightX());
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}
