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

public class RandomBehavior extends Behavior
{
  private ndcp theApp;

  private TransformGroup targetTG;
  private Transform3D movement = new Transform3D();

  private long elapseTime = 500;   // in milliseconds

  private Vector3f loc = new Vector3f(0.0f, 0.0f, 0.0f);

  private boolean doTrans;

  private float x, y, z;

  // create SimpleBehavior
  RandomBehavior(TransformGroup targetTG, ndcp theApp)
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

      // move all protons

      for (int i = 0; i < theApp.cntP; i++)
      {
        if (!theApp.theProtons[i].currentlyDisplayed) break;

        theApp.theProtons[i].theta += theApp.theProtons[i].dTheta;
        if (theApp.theProtons[i].theta > 360.0f) theApp.theProtons[i].theta -= 360.0f;
        if (theApp.theProtons[i].theta < -360.0f) theApp.theProtons[i].theta += 360.0f;

        theApp.theProtons[i].phi += theApp.theProtons[i].dPhi;
        if (theApp.theProtons[i].phi > 360.0f) theApp.theProtons[i].phi -= 360.0f;
        if (theApp.theProtons[i].phi < -360.0f) theApp.theProtons[i].phi += 360.0f;

        theApp.theProtons[i].cX = (float)(theApp.theProtons[i].r * Math.sin(d2r(theApp.theProtons[i].theta)) * Math.cos(d2r(theApp.theProtons[i].phi)) );
        theApp.theProtons[i].cY = (float)(theApp.theProtons[i].r * Math.sin(d2r(theApp.theProtons[i].theta)) * Math.sin(d2r(theApp.theProtons[i].phi)) );
        theApp.theProtons[i].cZ = (float)(theApp.theProtons[i].r * Math.cos(d2r(theApp.theProtons[i].theta)) );

        loc.set( theApp.theProtons[i].cX, theApp.theProtons[i].cY, theApp.theProtons[i].cZ );

        movement.setTranslation(loc);
        theApp.objPPosition[i].setTransform(movement);

      } // end for  statement

      // move all Neutrons

      for (int i = 0; i < theApp.cntN; i++)
      {
        if (!theApp.theNeutrons[i].currentlyDisplayed) break;

        theApp.theNeutrons[i].theta += theApp.theNeutrons[i].dTheta;
        if (theApp.theNeutrons[i].theta > 360.0f) theApp.theNeutrons[i].theta -= 360.0f;
        if (theApp.theNeutrons[i].theta < -360.0f) theApp.theNeutrons[i].theta += 360.0f;

        theApp.theNeutrons[i].phi += theApp.theNeutrons[i].dPhi;
        if (theApp.theNeutrons[i].phi > 360.0f) theApp.theNeutrons[i].phi -= 360.0f;
        if (theApp.theNeutrons[i].phi < -360.0f) theApp.theNeutrons[i].phi += 360.0f;

        theApp.theNeutrons[i].cX = (float)(theApp.theNeutrons[i].r * Math.sin(d2r(theApp.theNeutrons[i].theta)) * Math.cos(d2r(theApp.theNeutrons[i].phi)) );
        theApp.theNeutrons[i].cY = (float)(theApp.theNeutrons[i].r * Math.sin(d2r(theApp.theNeutrons[i].theta)) * Math.sin(d2r(theApp.theNeutrons[i].phi)) );
        theApp.theNeutrons[i].cZ = (float)(theApp.theNeutrons[i].r * Math.cos(d2r(theApp.theNeutrons[i].theta)) );

        loc.set( theApp.theNeutrons[i].cX, theApp.theNeutrons[i].cY, theApp.theNeutrons[i].cZ );

        movement.setTranslation(loc);
        theApp.objNPosition[i].setTransform(movement);

      } // end for  statement

    } // end if statement

    // always reset the trigger

    this.wakeupOn(new WakeupOnElapsedTime(elapseTime));

  } // end processStimulus

  public double d2r(float angle)
  {
    return( angle * 2.0 * Math.PI / 360.0 );
  } // end d2r

} // end of class SimpleBehaviorX


