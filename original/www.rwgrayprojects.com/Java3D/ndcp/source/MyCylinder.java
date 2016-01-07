package ndcp1;

import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;

public class MyCylinder
{

  double x1;
  double y1;
  double z1;
  double x2;
  double y2;
  double z2;

  double radius;

  public MyCylinder()
  {

  }

  public MyCylinder(double x1, double y1, double z1,
                    double x2, double y2, double z2)
  {

    this.x1 = x1;
    this.y1 = y1;
    this.z1 = z1;

    this.x2 = x2;
    this.y2 = y2;
    this.z2 = z2;

    radius = 0.25;

  } // end constructor Cube1


  public MyCylinder(float x1, float y1, float z1,
                    float x2, float y2, float z2, float radius)
  {

    this.x1 = x1;
    this.y1 = y1;
    this.z1 = z1;

    this.x2 = x2;
    this.y2 = y2;
    this.z2 = z2;

    this.radius = radius;

  } // end constructor Cube1

  public void setup(double x1, double y1, double z1,
                    double x2, double y2, double z2, double radius)
  {

    this.x1 = x1;
    this.y1 = y1;
    this.z1 = z1;

    this.x2 = x2;
    this.y2 = y2;
    this.z2 = z2;

    this.radius = radius;
  } // end setup

  public Geometry Solid()
  {
    int NV = 4 * 6;

    Point3f[] coord  = new Point3f[NV];
    Vector3f[] norms = new Vector3f[NV];

    QuadArray qa = new QuadArray(NV, QuadArray.COORDINATES | QuadArray.NORMALS);

    Vector3d h1 = new Vector3d(x1, y1, z1);
    Vector3d h2 = new Vector3d(x2, y2, z2);

    Vector3d h3 = new Vector3d(0.0, 0.0, 0.0);
    Vector3d h4 = new Vector3d(0.0, 0.0, 0.0);

    h3.cross(h1, h2);
    h4.cross(h2, h1);


    Point3f v1  = new Point3f(-2.0f, -2.0f, -2.0f);
    Point3f v2  = new Point3f( 2.0f, -2.0f, -2.0f);
    Point3f v3  = new Point3f( 2.0f,  2.0f, -2.0f);
    Point3f v4  = new Point3f(-2.0f,  2.0f, -2.0f);
    Point3f v5  = new Point3f(-2.0f, -2.0f,  2.0f);
    Point3f v6  = new Point3f( 2.0f, -2.0f,  2.0f);
    Point3f v7  = new Point3f( 2.0f,  2.0f,  2.0f);
    Point3f v8  = new Point3f(-2.0f,  2.0f,  2.0f);

    coord[0]  = v1;
    coord[1]  = v4;
    coord[2]  = v3;
    coord[3]  = v2;

    coord[4]  = v1;
    coord[5]  = v2;
    coord[6]  = v6;
    coord[7]  = v5;

    coord[8]  = v1;
    coord[9]  = v5;
    coord[10] = v8;
    coord[11] = v4;

    coord[12] = v4;
    coord[13] = v8;
    coord[14] = v7;
    coord[15] = v3;

    coord[16] = v3;
    coord[17] = v7;
    coord[18] = v6;
    coord[19] = v2;

    coord[20] = v5;
    coord[21] = v6;
    coord[22] = v7;
    coord[23] = v8;

    qa.setCoordinates(0, coord);

    // need to calculate all normals

    for (int i = 0; i < 6; i++)
    {
      // For each quad face, calculate 4 normals

      int j = 4 * i;

      Vector3f hold1 = new Vector3f(coord[j].x - coord[j+1].x,
                                    coord[j].y - coord[j+1].y,
                                    coord[j].z - coord[j+1].z);


      Vector3f hold2 = new Vector3f(coord[j+1].x - coord[j+2].x,
                                    coord[j+1].y - coord[j+2].y,
                                    coord[j+1].z - coord[j+2].z);

      Vector3f hold3 = new Vector3f(coord[j+2].x - coord[j+3].x,
                                    coord[j+2].y - coord[j+3].y,
                                    coord[j+2].z - coord[j+3].z);

      Vector3f hold4 = new Vector3f(coord[j+3].x - coord[j].x,
                                    coord[j+3].y - coord[j].y,
                                    coord[j+3].z - coord[j].z);

      Vector3f hold5 = new Vector3f(0.0f, 0.0f, 0.0f);

      hold5.cross(hold1, hold2);
      norms[j] = new Vector3f(hold5);

      hold5.cross(hold2, hold3);
      norms[j+1] = new Vector3f(hold5);

      hold5.cross(hold3, hold4);
      norms[j+2] = new Vector3f(hold5);

      hold5.cross(hold4, hold1);
      norms[j+3] = new Vector3f(hold5);

      norms[j].normalize();
      norms[j+1].normalize();
      norms[j+2].normalize();
      norms[j+3].normalize();

    } // end for statement

    qa.setNormals(0, norms);

    return(qa);

  } // end Solid()

} // end Cube2





