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

public class SimpleBehaviorX extends Behavior
{

  private TransformGroup targetTG;
  private Transform3D rotation = new Transform3D();
  private double angle = 0.0;

  private char theKey;
  private boolean doRot;

  // create SimpleBehavior
  SimpleBehaviorX(TransformGroup targetTG)
  {
    this.targetTG = targetTG;
  }

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
    doRot = false;

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

        if (evt[0] instanceof KeyEvent)
        {
          theKey = ((KeyEvent)evt[0]).getKeyChar();

          if ((theKey == '1') || (theKey == '2'))
          {
            doRot = true;
            break;
          } // end if '1' or '2'
        } // end if KeyEvent
      } // end for statement
    } // end while statement

    // do what is necessary
    if (doRot)
    {

      if (theKey == '2')
      {
        angle += 0.05;
        if (angle > (2.0 * Math.PI)) angle = angle - 2.0 * Math.PI;
      }
      else
      {
        angle -= 0.05;
        if (angle < (-2.0 * Math.PI)) angle = angle + 2.0 * Math.PI;
      }

      rotation.rotX(angle);
      targetTG.setTransform(rotation);
    } // end if statement

    // always reset the trigger

    this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));

  }

} // end of class SimpleBehaviorX


