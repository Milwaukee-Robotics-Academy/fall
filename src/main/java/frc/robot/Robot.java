// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

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
  private Victor rearRight = new Victor(1);
  private Victor frontLeft = new Victor(2);
  private Victor rearLeft = new Victor(3);

  private MotorControllerGroup rightGroup = new MotorControllerGroup(frontRight, rearRight);
  private MotorControllerGroup leftGroup = new MotorControllerGroup(frontLeft, rearLeft);
  private AHRS ahrs;
  private XboxController controller = new XboxController(0);
  private DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);
  private static final double kP = 0.011; // propotional turning constant

  

  private Timer autoTimer = new Timer();
  {

  }

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    rightGroup.setInverted(true);


    /**
     * Setting up a PID controller to support turn-to-angle
     */
  try{
    ahrs = new AHRS(SPI.Port.kMXP); 
  } catch (RuntimeException ex ) {
      DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
  }
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
    if (autoTimer.get()<2.0)
    {
      this.driveStraight(0.6, 0);
    } else if (autoTimer.get()>2.0 && autoTimer.get()<4.0) {
      this.turn(90);
    } else {
      drive.tankDrive(0, 0);
    }
  }
  

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
   
    drive.arcadeDrive(controller.getRightTriggerAxis() - controller.getLeftTriggerAxis(), controller.getLeftX());

    
  }

  void driveStraight(double speed, double desiredAngle) {
    double error = (desiredAngle - ahrs.getAngle());

    System.out.println("Error:" + error);
    System.out.print("angle:"+ahrs.getAngle());

    
    if (error > 2) {
      drive.tankDrive(speed+.05, speed-.05);
      System.out.print("| Left:"+ (speed+.05));
      System.out.println("| Right:"+ (speed-.05));
    } else if (error < 2) {
      drive.tankDrive(speed-.05, speed+.05);
      System.out.print("| Left:"+ (speed-.05));
      System.out.println("| Right:"+ (speed+.05));
    } else {
      drive.tankDrive(speed, speed);
      System.out.print("| Left:"+ (speed));
      System.out.println("| Right:"+ (speed));
    }
  }
  void turn(double desiredAngle) {
    double turn = (desiredAngle - ahrs.getAngle()) * kP;
    System.out.println("Turn:" + turn);
    System.out.print("angle:"+ahrs.getAngle());

    if (turn != 0) {
      drive.tankDrive(turn, -turn);
    } else {
      drive.tankDrive(0,0);
    }
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
