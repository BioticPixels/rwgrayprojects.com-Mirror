package ndcp1;

import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;

public class MyAxes
{

  ndcp theApp;
  Color theColor;
  float lineWidth;

  char whichOne;

  public MyAxes(ndcp theApp, char whichOne)
  {
    // Set default values

    this.theApp = theApp;
    this.whichOne = whichOne;  // 'X', 'Y', or 'Z'

    theColor  = new Color(0,0,0);
    theColor = Color.lightGray;
    lineWidth = 3.0f;

  } // end constructor Tetra1


  public Geometry Line()
  {

    // Define a line
    int NV = 2 * 1;

    Point3f[] coord  = new Point3f[NV];

    LineArray la = new LineArray(NV, LineArray.COORDINATES);

    Point3f v1 = new Point3f(0.0f, 0.0f, 0.0f);
    Point3f v2 = new Point3f(0.0f, 0.0f, 0.0f);

    if (whichOne == 'X')
    {
       v1.set( theApp.axisLength, 0.0f, 0.0f);
       v2.set(-theApp.axisLength, 0.0f, 0.0f);
    }

    if (whichOne == 'Y')
    {
       v1.set(0.0f,  theApp.axisLength,  0.0f);
       v2.set(0.0f, -theApp.axisLength,  0.0f);
    }

    if (whichOne == 'Z')
    {
      v1.set(0.0f,  0.0f,  theApp.axisLength);
      v2.set(0.0f,  0.0f, -theApp.axisLength);
    }

    coord[0] = v1;
    coord[1] = v2;

    la.setCoordinates(0, coord);

    return(la);

  } // end Line()



  public Appearance createAppearanceLine()
  {
     float red   = theColor.getRed() / 255.0f;
     float green = theColor.getGreen() / 255.0f;
     float blue  = theColor.getBlue() / 255.0f;

     Appearance A = new Appearance();
     A.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);

     RenderingAttributes RA = new RenderingAttributes();
     RA.setDepthBufferEnable(true);
     A.setRenderingAttributes(RA);

     ColoringAttributes CA =  new ColoringAttributes(red, green, blue,
                                       ColoringAttributes.SHADE_GOURAUD);

     CA.setCapability(ColoringAttributes.ALLOW_COLOR_WRITE);

     A.setColoringAttributes(CA);

     LineAttributes LA = new LineAttributes();
     LA.setLineWidth(lineWidth);
     LA.setLinePattern(LineAttributes.PATTERN_SOLID);
     LA.setLineAntialiasingEnable(true);
     LA.setCapability(LineAttributes.ALLOW_WIDTH_WRITE);

     A.setLineAttributes(LA);

     return(A);

  } // end createAppearanceLine

} // end MyAxes





