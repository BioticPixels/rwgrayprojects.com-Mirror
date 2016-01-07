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

public class SimpleBehaviorZ extends Behavior
{
  private ndcp theApp;
  private SimpleUniverse SU;
  private TransformGroup targetTG;
  private Transform3D    T3D = new Transform3D();
  private Point3d        vEye = new Point3d();
  private Point3d        vCenter = new Point3d();
  private Vector3d       vUp = new Vector3d();


  private Vector3d       moveTo = new Vector3d();


  double x, y, z;

  private float  radius;
  private float  theta;
  private float  phi;

  private char           theKey;
  private boolean        doTrans;

  // create SimpleBehavior
  SimpleBehaviorZ(ndcp theApp, SimpleUniverse SU, TransformGroup targetTG, float radius)
  {
    this.theApp = theApp;
    this.SU = SU;
    this.targetTG = targetTG;

    this.radius = radius;
    theta = 0.0f;
    phi = 0.0f;

  }  // end constructor SimpleBehavior

  // initialize the Behavior
  //     set initial wakeup condition
  //     called when behavior beacomes live
  public void initialize()
  {
    // set initial wakeup condition
    this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
  }

  // behave
  // called by Java 3D when appropriate stimulus occures
  public void processStimulus(Enumeration criteria)
  {
    WakeupCriterion wakeup;
    AWTEvent[] evt = null;

    theKey = ' ';
    doTrans = false;

    // decode event

    while (criteria.hasMoreElements())
    {
      // check to see if one is to rotate about Y-axis

      wakeup = (WakeupCriterion)criteria.nextElement();

      if (!(wakeup instanceof WakeupOnAWTEvent))
      {
        // not the right kind of event
        continue;
      } // end if statement

      evt = ((WakeupOnAWTEvent)wakeup).getAWTEvent();

      if (evt == null) continue;

      for (int i = 0; i < evt.length; i++)
      {

        if (evt[i] instanceof KeyEvent)
        {

//System.out.println("Event: "+evt[i]);
//System.out.println(" char: "+((KeyEvent)evt[i]).getKeyChar() );
//System.out.println(" code: "+((KeyEvent)evt[i]).getKeyCode() );
//System.out.println("  mod: "+((KeyEvent)evt[i]).getKeyModifiersText(((KeyEvent)evt[i]).getModifiers()) );
//System.out.println(" text: "+((KeyEvent)evt[i]).getKeyText(((KeyEvent)evt[i]).getKeyCode()) );

          theKey = ((KeyEvent)evt[i]).getKeyChar();
          String modifier = ((KeyEvent)evt[i]).getKeyModifiersText(((KeyEvent)evt[i]).getModifiers());
          String text = ((KeyEvent)evt[i]).getKeyText(((KeyEvent)evt[i]).getKeyCode());

          if (!(theKey == '9') && !(theKey == '0'))
          {
            theApp.theMenu.changeOption(modifier, text);

            // reset WakeUp event
//            this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));

//            return;
          } // other than view behavior

          if ((theKey == '9') || (theKey == '0'))
          {
            doTrans = true;
            break;
          } // end if '9' or '0'
        } // end if KeyEvent
      } // end for statement
    } // end while statement



/*
    // do what is necessary
    if (doTrans)
    {

      if (theKey == '0')
      {
        if (radius < 500.0f)
        {
          radius += theApp.vRadiusInc;
        }
      }
      if (theKey == '9')
      {
        if (radius > 3.0f)
        {
          radius -= theApp.vRadiusInc;
        }
      }

      x = radius * Math.sin(d2r(theta)) * Math.cos(d2r(phi));
      y = radius * Math.sin(d2r(theta)) * Math.sin(d2r(phi));
      z = radius * Math.cos(d2r(theta));

//System.out.println("x: "+x+" y: "+y+" z: "+z);

      vEye.set(x, y, z);

      vCenter.set(0.0, 0.0, 0.0);

      vUp.set(0.0, 1.0, 0.0);

      T3D.lookAt(vEye, vCenter, vUp);

      // It is not known why the invert() makes lookAt()
      // work, but it does and is needed.

      T3D.invert();

//      phi += 1.0f;
//      theta += 1.0f;

      targetTG.setTransform(T3D);

    } // end if statement
*/
    // always reset the trigger

    this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));

  }

  public double d2r(float angle)
  {
    return(angle * 2.0 * Math.PI / 360.0);
  } // end dr2

} // end of class SimpleBehaviorX


