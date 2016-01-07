package ndcp1;

import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;

public class MyCircle
{
  double dist;
  double radius;
  char axis;

  int nFaces;

  public MyCircle()
  {

  } // end MyCircle


  public MyCircle(char axis, double dist, double radius)
  {

    setup(axis, dist, radius);

  } // end MyCone


  public void setup(char axis, double dist, double radius)
  {

    this.dist = dist;
    this.radius = radius;
    this.axis = axis;

    nFaces = 20;

    if (radius > 1.0)
    {
      nFaces = 40;
    } // end if statement

  } // end setup


  public Geometry Solid()
  {
    // 2 cones, using triangle array, nFaces triangles each
    int NV = 2 * 3 * nFaces;

    Point3f[] coord  = new Point3f[NV];
    Vector3f[] norms = new Vector3f[NV];

    TriangleArray ta = new TriangleArray(NV, TriangleArray.COORDINATES | TriangleArray.NORMALS);

    double x = 0.0;
    double y = 0.0;
    double z = 0.0;

    double px = 0.0;
    double py = 0.0;
    double pz = 0.0;

    double theta  = 0.0;
    double dtheta = 2.0 * Math.PI / (double)nFaces;

   for (int i = 0; i < nFaces; i++)
    {
      theta = (double)i * 2.0 * Math.PI / (double)nFaces;

      if (axis == 'X')
      {
        x = dist;
        y = radius * Math.cos(theta);
        z = radius * Math.sin(theta);

        px = dist;
        py = radius * Math.cos(theta - dtheta);
        pz = radius * Math.sin(theta - dtheta);

        coord[3*i + 0] = new Point3f((float)dist, 0.0f, 0.0f);
        coord[3*i + 1] = new Point3f((float)x, (float)y, (float)z);
        coord[3*i + 2] = new Point3f((float)px, (float)py, (float)pz);

        norms[3*i + 0] = new Vector3f(1.0f, 0.0f, 0.0f);
        norms[3*i + 1] = new Vector3f(1.0f, 0.0f, 0.0f);
        norms[3*i + 2] = new Vector3f(1.0f, 0.0f, 0.0f);

        coord[3*nFaces + 3*i + 0] = new Point3f((float)(-dist), 0.0f, 0.0f);
        coord[3*nFaces + 3*i + 1] = new Point3f((float)(-x), (float)y, (float)z);
        coord[3*nFaces + 3*i + 2] = new Point3f((float)(-px), (float)py, (float)pz);

        norms[3*nFaces + 3*i + 0] = new Vector3f(-1.0f, 0.0f, 0.0f);
        norms[3*nFaces + 3*i + 1] = new Vector3f(-1.0f, 0.0f, 0.0f);
        norms[3*nFaces + 3*i + 2] = new Vector3f(-1.0f, 0.0f, 0.0f);
      } // end if statement


      if (axis == 'Z')
      {
        // for i q.n.
        z = dist;
        y = radius * Math.cos(theta);
        x = radius * Math.sin(theta);

        pz = dist;
        py = radius * Math.cos(theta - dtheta);
        px = radius * Math.sin(theta - dtheta);

        coord[3*i + 0] = new Point3f(0.0f, 0.0f, (float)dist);
        coord[3*i + 1] = new Point3f((float)x, (float)y, (float)z);
        coord[3*i + 2] = new Point3f((float)px, (float)py, (float)pz);

        norms[3*i + 0] = new Vector3f(0.0f, 0.0f, 1.0f);
        norms[3*i + 1] = new Vector3f(0.0f, 0.0f, 1.0f);
        norms[3*i + 2] = new Vector3f(0.0f, 0.0f, 1.0f);

        coord[3*nFaces + 3*i + 0] = new Point3f(0.0f, 0.0f, (float)(-dist));
        coord[3*nFaces + 3*i + 1] = new Point3f((float)x, (float)y, (float)(-z));
        coord[3*nFaces + 3*i + 2] = new Point3f((float)px, (float)py, (float)(-pz));

        norms[3*nFaces + 3*i + 0] = new Vector3f(0.0f, 0.0f, -1.0f);
        norms[3*nFaces + 3*i + 1] = new Vector3f(0.0f, 0.0f, -1.0f);
        norms[3*nFaces + 3*i + 2] = new Vector3f(0.0f, 0.0f, -1.0f);

      } // end if statement


    } // end for statement

    ta.setCoordinates(0, coord);
    ta.setNormals(0, norms);

    return(ta);

  } // end Solid()

} // end MyCircle







