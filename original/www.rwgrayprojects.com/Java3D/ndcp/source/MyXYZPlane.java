package ndcp1;

import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;

public class MyXYZPlane
{
  ndcp theApp;
  String whichOne;

  float epsilon;

  public MyXYZPlane(ndcp theApp, String whichOne)
  {

    this.theApp = theApp;
    this.whichOne = whichOne;

    epsilon = 0.001f;

  } // end constructor Tetra1


  public Geometry Solid()
  {

    int NV = 4 * 2;

    Point3f[] coord  = new Point3f[NV];
    Vector3f[] norms = new Vector3f[NV];

    QuadArray qa = new QuadArray(NV, QuadArray.COORDINATES | QuadArray.NORMALS);

    Point3f v1  = new Point3f( 0.0f,  0.0f,  0.0f);
    Point3f v2  = new Point3f( 0.0f,  0.0f,  0.0f);
    Point3f v3  = new Point3f( 0.0f,  0.0f,  0.0f);
    Point3f v4  = new Point3f( 0.0f,  0.0f,  0.0f);

    Point3f v5  = new Point3f( 0.0f,  0.0f,  0.0f);
    Point3f v6  = new Point3f( 0.0f,  0.0f,  0.0f);
    Point3f v7  = new Point3f( 0.0f,  0.0f,  0.0f);
    Point3f v8  = new Point3f( 0.0f,  0.0f,  0.0f);

    if (whichOne.equals("XY"))
    {
      v1.set( theApp.axisLength,  theApp.axisLength, epsilon);
      v2.set(-theApp.axisLength,  theApp.axisLength, epsilon);
      v3.set(-theApp.axisLength, -theApp.axisLength, epsilon);
      v4.set( theApp.axisLength, -theApp.axisLength, epsilon);

      v8.set( theApp.axisLength,  theApp.axisLength, -epsilon);
      v7.set(-theApp.axisLength,  theApp.axisLength, -epsilon);
      v6.set(-theApp.axisLength, -theApp.axisLength, -epsilon);
      v5.set( theApp.axisLength, -theApp.axisLength, -epsilon);


      norms[0] = new Vector3f(0.0f, 0.0f,  1.0f);
      norms[4] = new Vector3f(0.0f, 0.0f, -1.0f);
    }

    if (whichOne.equals("YZ"))
    {
      v1.set(epsilon,  theApp.axisLength,  theApp.axisLength);
      v2.set(epsilon, -theApp.axisLength,  theApp.axisLength);
      v3.set(epsilon, -theApp.axisLength, -theApp.axisLength);
      v4.set(epsilon,  theApp.axisLength, -theApp.axisLength);

      v8.set(-epsilon,  theApp.axisLength,  theApp.axisLength);
      v7.set(-epsilon, -theApp.axisLength,  theApp.axisLength);
      v6.set(-epsilon, -theApp.axisLength, -theApp.axisLength);
      v5.set(-epsilon,  theApp.axisLength, -theApp.axisLength);

      norms[0] = new Vector3f( 1.0f, 0.0f, 0.0f);
      norms[4] = new Vector3f(-1.0f, 0.0f, 0.0f);
    }

    if (whichOne.equals("XZ"))
    {
      v1.set( theApp.axisLength,  epsilon,  theApp.axisLength);
      v2.set(-theApp.axisLength,  epsilon,  theApp.axisLength);
      v3.set(-theApp.axisLength,  epsilon, -theApp.axisLength);
      v4.set( theApp.axisLength,  epsilon, -theApp.axisLength);

      v8.set( theApp.axisLength,  -epsilon,  theApp.axisLength);
      v7.set(-theApp.axisLength,  -epsilon,  theApp.axisLength);
      v6.set(-theApp.axisLength,  -epsilon, -theApp.axisLength);
      v5.set( theApp.axisLength,  -epsilon, -theApp.axisLength);

      norms[0] = new Vector3f(0.0f, -1.0f, 0.0f);
      norms[4] = new Vector3f(0.0f,  1.0f, 0.0f);
    }

    coord[0] = v1;
    coord[1] = v2;
    coord[2] = v3;
    coord[3] = v4;

    coord[4] = v5;
    coord[5] = v6;
    coord[6] = v7;
    coord[7] = v8;

    qa.setCoordinates(0, coord);

    // need to calculate all normals

    norms[1] = norms[0];
    norms[2] = norms[0];
    norms[3] = norms[0];

    norms[5] = norms[4];
    norms[6] = norms[4];
    norms[7] = norms[4];

    qa.setNormals(0, norms);

    return(qa);

  } // end Solid()


  public Appearance createAppearanceSolid()
  {
     Appearance A = new Appearance();
     A.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
     A.setCapability(Appearance.ALLOW_MATERIAL_WRITE);

     RenderingAttributes RA = new RenderingAttributes();
     RA.setDepthBufferEnable(true);
     A.setRenderingAttributes(RA);

     Material theMaterial = new Material();
     theMaterial.setCapability(Material.ALLOW_COMPONENT_WRITE);

     theMaterial.setAmbientColor(0.05f, 0.05f, 0.05f);
     theMaterial.setDiffuseColor(1.0f, 1.0f, 1.0f);
     theMaterial.setEmissiveColor(0.0f, 0.0f, 0.0f);
     theMaterial.setLightingEnable(true);
     theMaterial.setShininess(0.05f);
     theMaterial.setSpecularColor(0.08f, 0.08f, 0.08f);

     A.setMaterial(theMaterial);

     A.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
                                                   PolygonAttributes.CULL_BACK, 0.01f, false));

     TransparencyAttributes TA = new TransparencyAttributes(TransparencyAttributes.NICEST, 0.6f);
     TA.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
     A.setTransparencyAttributes(TA);

     return(A);

  } // end createAppearanceSolid


} // end MyXYZPlan






