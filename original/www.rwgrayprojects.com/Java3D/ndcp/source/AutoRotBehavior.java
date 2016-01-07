package ndcp1;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import java.awt.event.*;
import java.awt.AWTEvent;
import java.util.Enumeration;

public class AutoRotBehavior extends Behavior
{
  private ndcp theApp;

  private TransformGroup targetTG;
  private Transform3D movement = new Transform3D();

  private Transform3D    T3D = new Transform3D();
  private Point3d        vEye = new Point3d();
  private Point3d        vCenter = new Point3d();
  private Vector3d       vUp = new Vector3d();

  private long elapseTime = 100;   // in milliseconds

//  private Vector3f loc = new Vector3f(0.0f, 0.0f, 0.0f);

  private boolean doTrans;

  private double x, y, z;
  private double d;
  private double angle;

  // create SimpleBehavior
  AutoRotBehavior(TransformGroup targetTG, ndcp theApp)
  {
    this.targetTG = targetTG;
    this.theApp = theApp;
  }

  // initialize the Behavior
  //     set initial wakeup condition
  //     called when behavior beacomes live
  public void initialize()
  {
    // set initial wakeup condition
    this.wakeupOn(new WakeupOnElapsedTime(elapseTime));
  }

  // behave
  // called by Java 3D when appropriate stimulus occures
  public void processStimulus(Enumeration criteria)
  {
    WakeupCriterion wakeup;

    // decode event

    while (criteria.hasMoreElements())
    {
      // check to see if one is WakeupOnElapsedTime

      wakeup = (WakeupCriterion)criteria.nextElement();

      if (!(wakeup instanceof WakeupOnElapsedTime))
      {
        // not the right kind of event
        continue;
      } // end if statement

       doTrans = true;

    } // end while statement

    // do what is necessary
    if (doTrans)
    {
      // move view platform

      d = distance();

      if (theApp.revolveX)
      {
         doRevolveX(1);
      }
      if (theApp.revolveY)
      {
         doRevolveY(1);
      }
      if (theApp.revolveZ)
      {
         doRevolveZ(1);
      }

      vEye.set(theApp.vpX, theApp.vpY, theApp.vpZ);
      vCenter.set(0.0, 0.0, 0.0);
      vUp.set(theApp.voX, theApp.voY, theApp.voZ);
      T3D.lookAt(vEye, vCenter, vUp);

      // It is not known why the invert() makes lookAt()
      // work, but it does and is needed.

      T3D.invert();
      targetTG.setTransform(T3D);

       // reset WakeUp event
      this.wakeupOn(new WakeupOnElapsedTime(elapseTime));

      return;

    } // end if statement

    // always reset the trigger

    this.wakeupOn(new WakeupOnElapsedTime(elapseTime));

  } // end processStimulus

  public double d2r(float angle)
  {
    return( angle * 2.0 * Math.PI / 360.0 );
  } // end d2r



  public double d2r(double angle)
  {
    return(angle * 2.0 * Math.PI / 360.0);
  } // end dr2


  public double distance()
  {
    return(Math.sqrt(theApp.vpX * theApp.vpX +
                     theApp.vpY * theApp.vpY +
                     theApp.vpZ * theApp.vpZ));
  } // end distance

  public void doRevolveX(int dif)
  {
    if (dif > 0) angle = theApp.revolveSpeed;
    else angle = -theApp.revolveSpeed;

    x = theApp.vpX;
    y = theApp.vpY;
    z = theApp.vpZ;

    theApp.vpX = (float)x;
    theApp.vpY = (float)(y * Math.cos(d2r(angle)) - z * Math.sin(d2r(angle)));
    theApp.vpZ = (float)(y * Math.sin(d2r(angle)) + z * Math.cos(d2r(angle)));

    theApp.lightD1.setDirection(-theApp.vpX, -theApp.vpY, -theApp.vpZ);


    x = theApp.voX;
    y = theApp.voY;
    z = theApp.voZ;

    theApp.voX = (float)x;
    theApp.voY = (float)(y * Math.cos(d2r(angle)) - z * Math.sin(d2r(angle)));
    theApp.voZ = (float)(y * Math.sin(d2r(angle)) + z * Math.cos(d2r(angle)));


  } // end doRevolveX

  public void doRevolveY(int dif)
  {
    if (dif > 0) angle = theApp.revolveSpeed;
    else angle = -theApp.revolveSpeed;

    x = theApp.vpX;
    y = theApp.vpY;
    z = theApp.vpZ;

    theApp.vpX = (float)(x * Math.cos(d2r(angle)) - z * Math.sin(d2r(angle)));
    theApp.vpY = (float)y;
    theApp.vpZ = (float)(x * Math.sin(d2r(angle)) + z * Math.cos(d2r(angle)));

    theApp.lightD1.setDirection(-theApp.vpX, -theApp.vpY, -theApp.vpZ);


    x = theApp.voX;
    y = theApp.voY;
    z = theApp.voZ;

    theApp.voX = (float)(x * Math.cos(d2r(angle)) - z * Math.sin(d2r(angle)));
    theApp.voY = (float)y;
    theApp.voZ = (float)(x * Math.sin(d2r(angle)) + z * Math.cos(d2r(angle)));

  } // end doRevolveY

  public void doRevolveZ(int dif)
  {
    if (dif > 0) angle = theApp.revolveSpeed;
    else angle = -theApp.revolveSpeed;

    x = theApp.vpX;
    y = theApp.vpY;
    z = theApp.vpZ;

    theApp.vpX =  (float)(x * Math.cos(d2r(angle)) + y * Math.sin(d2r(angle)));
    theApp.vpY = (float)(-x * Math.sin(d2r(angle)) + y * Math.cos(d2r(angle)));
    theApp.vpZ =  (float)z;

    theApp.lightD1.setDirection(-theApp.vpX, -theApp.vpY, -theApp.vpZ);

    x = theApp.voX;
    y = theApp.voY;
    z = theApp.voZ;

    theApp.voX =  (float)(x * Math.cos(d2r(angle)) + y * Math.sin(d2r(angle)));
    theApp.voY = (float)(-x * Math.sin(d2r(angle)) + y * Math.cos(d2r(angle)));
    theApp.voZ =  (float)z;

  } // end doRevolveZ



} // end of class AutoRotBehavior


