package ndcp1;

//
// NDCPView for nuclear lattice model viewer
//
// Written by: R. W. Gray
// Date:       02-23-1999
// Version:    0.01
//
//

import java.awt.*;         // get window tools
import javax.vecmath.*;

public class NDCPView
         extends Canvas
         implements Constants
{

  ndcp theDoc;   // the parent document

  Image workspace;
  Graphics offscreen;  // The graphics context;

  // The view for the document should contain all the "viewing"
  // parameters like Zoom factor, orientation, viewer's coordinates,
  // draw axis or not, etc...

  double perspectiveAngle = initialPerspectiveAngle;

  Point3f viewerLoc;  // viewer's location (x, y, z)

  boolean drawAxis;     // ture = have to draw in the xyz-axis

  double sphereRadius;  // *all* proton, neutron spheres in this view are
                        // to be drawn having this radius.


  //  All the various option varibales are defined here.

  int latticeType;  // what kind of lattice

  // Constructor
  public NDCPView(ndcp theDoc)
  {
    this.theDoc = theDoc;

    // set default viewing setings

    drawAxis = true;

    viewerLoc = new Point3f(200.0f, 200.0f, 200.0f);

    sphereRadius = 0.01;  // FCC distance between spheres = 1.0

    latticeType = LATTICE_FCC; // initial lattice type

  } // end constructor: NDCPView(NDCPDoc)

  public Point3f getViewerLoc()
  {
    return viewerLoc;
  } // end getViewerLoc

  public void addOffscreen(Graphics g, Image w)
  {
    // add the off screen work space image to the view
    offscreen = g;
    workspace = w;
  } // end addOffscreen(Graphics, Image)

  // Draw the document

  public void paint(Graphics g)
  {
    offscreen.setColor(Color.black);
    offscreen.fillRect(0,0, ndcp.windowSize.width, ndcp.windowSize.height);

//    theDoc.draw(offscreen);             // Draw the document offscreen

    drawStatus(offscreen);   // display the status info

    g.drawImage(workspace, 0, 0, null); // display the offscreen image

  }  // end paint(Graphics)


  private void drawStatus(Graphics g)
  {
    // this method draws the status info

    int statusX;
    int statusY;
    int incY;

    statusX = ndcp.windowSize.width - 1 - 275;
    statusY = 50;
    incY = 15;

    g.setColor(Color.white);

    g.drawString("NUCLEUS Z xxx"+" N xxx", statusX,statusY);

    statusY += incY;
    g.drawString("VIEWING MODE: Indepentent-Particle Model",
                            statusX,statusY);
    statusY += incY;
    g.drawString("BONDS off", statusX+20,statusY);

    statusY += incY;
    statusY += incY;
    g.drawString("Occupancy rate: xxx", statusX+20,statusY);

  } // end drawStatus

  public double getRadius()
  {
    return sphereRadius;
  } // end float getRadius()


  public void update(Graphics g)
  {
    paint(g);
  }




} // end class NDCPView
