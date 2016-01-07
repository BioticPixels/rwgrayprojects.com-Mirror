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

public class SimpleMouseBehavior extends Behavior
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
  double d;
  double angle;

  private float  radius;
  private float  theta;
  private float  phi;

  private char      theKey;
  private boolean   doTrans;

  private int startX;
  private int endX;

  // create SimpleBehavior
  SimpleMouseBehavior(ndcp theApp, SimpleUniverse SU, TransformGroup targetTG, float radius)
  {
    this.theApp = theApp;
    this.SU = SU;
    this.targetTG = targetTG;

    this.radius = radius;
    theta = 0.0f;
    phi = 0.0f;

    startX = 0;
    endX = 0;

  }  // end constructor SimpleMouseBehavior

  // initialize the Behavior
  //     set initial wakeup condition
  //     called when behavior beacomes live
  public void initialize()
  {
    // set initial wakeup condition
    this.wakeupOn(new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED));
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

        if (evt[i] instanceof MouseEvent)
        {

// System.out.println("Event: "+evt[i]);
// System.out.println(" X: "+((MouseEvent)evt[i]).getX() );
// System.out.println(" Y: "+((MouseEvent)evt[i]).getY() );
//System.out.println("  mod: "+((KeyEvent)evt[i]).getKeyModifiersText(((KeyEvent)evt[i]).getModifiers()) );
//System.out.println(" text: "+((KeyEvent)evt[i]).getKeyText(((KeyEvent)evt[i]).getKeyCode()) );

//          theKey = ((KeyEvent)evt[i]).getKeyChar();
//          String modifier = ((KeyEvent)evt[i]).getKeyModifiersText(((KeyEvent)evt[i]).getModifiers());
//          String text = ((KeyEvent)evt[i]).getKeyText(((KeyEvent)evt[i]).getKeyCode());

           endX = startX;
           startX =  ((MouseEvent)evt[i]).getX();

           d = distance();

           if ((!theApp.revolveOn) && (theApp.revolveX))
           {
               doRevolveX(endX - startX);
           }
           if ((!theApp.revolveOn) && (theApp.revolveY))
           {
               doRevolveY(endX - startX);
           }
           if ((!theApp.revolveOn) && (theApp.revolveZ))
           {
               doRevolveZ(endX - startX);
           }
           if ((!theApp.revolveOn) && (theApp.zoomR) && (d > 1.0) && (d < 100.0))
           {

              if ((endX - startX) < 0)
              {
                 theApp.vpX = theApp.vpX * (1.0f - theApp.vpRadiusInc);
                 theApp.vpY = theApp.vpY * (1.0f - theApp.vpRadiusInc);
                 theApp.vpZ = theApp.vpZ * (1.0f - theApp.vpRadiusInc);
              }
              else
              {
                 theApp.vpX = theApp.vpX * (1.0f + theApp.vpRadiusInc);
                 theApp.vpY = theApp.vpY * (1.0f + theApp.vpRadiusInc);
                 theApp.vpZ = theApp.vpZ * (1.0f + theApp.vpRadiusInc);
              }

           }  // end if zooming

           vEye.set(theApp.vpX, theApp.vpY, theApp.vpZ);
           vCenter.set(0.0, 0.0, 0.0);
           vUp.set(theApp.voX, theApp.voY, theApp.voZ);
           T3D.lookAt(vEye, vCenter, vUp);

           // It is not known why the invert() makes lookAt()
           // work, but it does and is needed.

           T3D.invert();

           targetTG.setTransform(T3D);

            // reset WakeUp event
           this.wakeupOn(new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED));

           return;

        } // end if MouseEvent
      } // end for statement
    } // end while statement


    this.wakeupOn(new WakeupOnAWTEvent(MouseEvent.MOUSE_DRAGGED));

  }

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

} // end of class SimpleBehaviorX


