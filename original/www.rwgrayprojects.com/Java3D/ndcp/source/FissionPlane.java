package ndcp1;

import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;

public class FissionPlane
{
  ndcp theApp;
  int whichOne;

  float epsilon;


      // norm = (A, B, C)  D = norm * (x1, y1, z1)
      // distance from a point (x, y, z) to plane defined by norm and (x1, y1, z1)
      // dist = abs(Ax + By + Cz - D) / sqrt(AA + BB + CC)
      // this is how we tell if fission plane cuts the bond.
      // Values are set when we select which bond to display.  And counts are
      // calculated when we determine display masks.


  float fissionNormA;
  float fissionNormB;
  float fissionNormC;
  float fissionNormD;


  public FissionPlane(ndcp theApp, int whichOne)
  {

    this.theApp = theApp;
    this.whichOne = whichOne;

    epsilon = 0.001f;

  } // end constructor FissionPlane


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

    // Symmetrical

    // XY

    if (whichOne == 0)
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

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      fissionNormA = 0.0f;
      fissionNormB = 0.0f;
      fissionNormC = 1.0f;
      fissionNormD = epsilon;

    }

    float offset = 2.0f;

    if (whichOne == 1)
    {
      v1.set( theApp.axisLength,  theApp.axisLength, epsilon + offset);
      v2.set(-theApp.axisLength,  theApp.axisLength, epsilon + offset);
      v3.set(-theApp.axisLength, -theApp.axisLength, epsilon + offset);
      v4.set( theApp.axisLength, -theApp.axisLength, epsilon + offset);

      v8.set( theApp.axisLength,  theApp.axisLength, -epsilon + offset);
      v7.set(-theApp.axisLength,  theApp.axisLength, -epsilon + offset);
      v6.set(-theApp.axisLength, -theApp.axisLength, -epsilon + offset);
      v5.set( theApp.axisLength, -theApp.axisLength, -epsilon + offset);

      norms[0] = new Vector3f(0.0f, 0.0f,  1.0f);
      norms[4] = new Vector3f(0.0f, 0.0f, -1.0f);


      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      fissionNormA = 0.0f;
      fissionNormB = 0.0f;
      fissionNormC = 1.0f;
      fissionNormD = epsilon + offset;

    }

    if (whichOne == 2)
    {
      v1.set( theApp.axisLength,  theApp.axisLength, epsilon - offset);
      v2.set(-theApp.axisLength,  theApp.axisLength, epsilon - offset);
      v3.set(-theApp.axisLength, -theApp.axisLength, epsilon - offset);
      v4.set( theApp.axisLength, -theApp.axisLength, epsilon - offset);

      v8.set( theApp.axisLength,  theApp.axisLength, -epsilon - offset);
      v7.set(-theApp.axisLength,  theApp.axisLength, -epsilon - offset);
      v6.set(-theApp.axisLength, -theApp.axisLength, -epsilon - offset);
      v5.set( theApp.axisLength, -theApp.axisLength, -epsilon - offset);

      norms[0] = new Vector3f(0.0f, 0.0f,  1.0f);
      norms[4] = new Vector3f(0.0f, 0.0f, -1.0f);


      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      fissionNormA = 0.0f;
      fissionNormB = 0.0f;
      fissionNormC = 1.0f;
      fissionNormD = epsilon - offset;

    }


    // YZ

    if (whichOne == 3)
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


      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      fissionNormA = 1.0f;
      fissionNormB = 0.0f;
      fissionNormC = 0.0f;
      fissionNormD = epsilon;

    }

    if (whichOne == 4)
    {
      v1.set(epsilon + offset,  theApp.axisLength,  theApp.axisLength);
      v2.set(epsilon + offset, -theApp.axisLength,  theApp.axisLength);
      v3.set(epsilon + offset, -theApp.axisLength, -theApp.axisLength);
      v4.set(epsilon + offset,  theApp.axisLength, -theApp.axisLength);

      v8.set(-epsilon + offset,  theApp.axisLength,  theApp.axisLength);
      v7.set(-epsilon + offset, -theApp.axisLength,  theApp.axisLength);
      v6.set(-epsilon + offset, -theApp.axisLength, -theApp.axisLength);
      v5.set(-epsilon + offset,  theApp.axisLength, -theApp.axisLength);

      norms[0] = new Vector3f( 1.0f, 0.0f, 0.0f);
      norms[4] = new Vector3f(-1.0f, 0.0f, 0.0f);


      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      fissionNormA = 1.0f;
      fissionNormB = 0.0f;
      fissionNormC = 0.0f;
      fissionNormD = epsilon + offset;

    }

    if (whichOne == 5)
    {
      v1.set(epsilon - offset,  theApp.axisLength,  theApp.axisLength);
      v2.set(epsilon - offset, -theApp.axisLength,  theApp.axisLength);
      v3.set(epsilon - offset, -theApp.axisLength, -theApp.axisLength);
      v4.set(epsilon - offset,  theApp.axisLength, -theApp.axisLength);

      v8.set(-epsilon - offset,  theApp.axisLength,  theApp.axisLength);
      v7.set(-epsilon - offset, -theApp.axisLength,  theApp.axisLength);
      v6.set(-epsilon - offset, -theApp.axisLength, -theApp.axisLength);
      v5.set(-epsilon - offset,  theApp.axisLength, -theApp.axisLength);

      norms[0] = new Vector3f( 1.0f, 0.0f, 0.0f);
      norms[4] = new Vector3f(-1.0f, 0.0f, 0.0f);


      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      fissionNormA = 1.0f;
      fissionNormB = 0.0f;
      fissionNormC = 0.0f;
      fissionNormD = epsilon - offset;

    }


    // XZ

    if (whichOne == 6)
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


      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      fissionNormA = 0.0f;
      fissionNormB = -1.0f;
      fissionNormC = 0.0f;
      fissionNormD = epsilon;

    }

    if (whichOne == 7)
    {
      v1.set( theApp.axisLength,  epsilon + offset,  theApp.axisLength);
      v2.set(-theApp.axisLength,  epsilon + offset,  theApp.axisLength);
      v3.set(-theApp.axisLength,  epsilon + offset, -theApp.axisLength);
      v4.set( theApp.axisLength,  epsilon + offset, -theApp.axisLength);

      v8.set( theApp.axisLength,  -epsilon + offset,  theApp.axisLength);
      v7.set(-theApp.axisLength,  -epsilon + offset,  theApp.axisLength);
      v6.set(-theApp.axisLength,  -epsilon + offset, -theApp.axisLength);
      v5.set( theApp.axisLength,  -epsilon + offset, -theApp.axisLength);

      norms[0] = new Vector3f(0.0f, -1.0f, 0.0f);
      norms[4] = new Vector3f(0.0f,  1.0f, 0.0f);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      fissionNormA = 0.0f;
      fissionNormB = -1.0f;
      fissionNormC = 0.0f;
      fissionNormD = epsilon + offset;

    }

    if (whichOne == 8)
    {
      v1.set( theApp.axisLength,  epsilon - offset,  theApp.axisLength);
      v2.set(-theApp.axisLength,  epsilon - offset,  theApp.axisLength);
      v3.set(-theApp.axisLength,  epsilon - offset, -theApp.axisLength);
      v4.set( theApp.axisLength,  epsilon - offset, -theApp.axisLength);

      v8.set( theApp.axisLength,  -epsilon - offset,  theApp.axisLength);
      v7.set(-theApp.axisLength,  -epsilon - offset,  theApp.axisLength);
      v6.set(-theApp.axisLength,  -epsilon - offset, -theApp.axisLength);
      v5.set( theApp.axisLength,  -epsilon - offset, -theApp.axisLength);

      norms[0] = new Vector3f(0.0f, -1.0f, 0.0f);
      norms[4] = new Vector3f(0.0f,  1.0f, 0.0f);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      fissionNormA = 0.0f;
      fissionNormB = -1.0f;
      fissionNormC = 0.0f;
      fissionNormD = epsilon - offset;

    }




    // Assymmetrical

   float x1, y1, z1;
   float x2, y2, z2;
   float x3, y3, z3;
   float x4, y4, z4;
   float nx, ny, nz;

   float scaleFactor = theApp.axisLength;
   float os2 = 1.0f / (float)Math.sqrt(2.0);

   float delta = 1.0f / (float)Math.sqrt(3.0);


   float mag = 0.0f;

   x1 = 0.0f; y1 = 0.0f; z1 = 0.0f;
   x2 = 0.0f; y2 = 0.0f; z2 = 0.0f;
   x3 = 0.0f; y3 = 0.0f; z3 = 0.0f;
   x4 = 0.0f; y4 = 0.0f; z4 = 0.0f;

   nx = 0.0f; ny = 0.0f; nz = 0.0f;

   if ((whichOne == 9) || (whichOne == 10) || (whichOne == 11))
   {
     x1 =  0.0f * scaleFactor;
     y1 = -os2  * scaleFactor;
     z1 =  os2  * scaleFactor;

     x2 =  os2  * scaleFactor;
     y2 =  0.0f * scaleFactor;
     z2 = -os2  * scaleFactor;

     x3 =  0.0f * scaleFactor;
     y3 =  os2  * scaleFactor;
     z3 = -os2  * scaleFactor;

     x4 = -os2  * scaleFactor;
     y4 =  0.0f * scaleFactor;
     z4 =  os2  * scaleFactor;

     nx = y2 * z3 - y3 * z2;
     ny = x3 * z2 - x2 * z3;
     nz = x2 * y3 - x3 * y2;

     mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

     nx = nx / mag;
     ny = ny / mag;
     nz = nz / mag;

   } // end if statement

    // (+, +, 0)

    if (whichOne == 9)
    {
      nx = nx * delta + epsilon;
      ny = ny * delta + epsilon;
      nz = nz * delta + epsilon;

      v1.set( x1 + nx,  y1 + ny,  z1 + nz);
      v2.set( x2 + nx,  y2 + ny,  z2 + nz);
      v3.set( x3 + nx,  y3 + ny,  z3 + nz);
      v4.set( x4 + nx,  y4 + ny,  z4 + nz);

      nx = nx - 2.0f * epsilon;
      ny = ny - 2.0f * epsilon;
      nz = nz - 2.0f * epsilon;

      v8.set( x1 + nx,  y1 + ny,  z1 + nz);
      v7.set( x2 + nx,  y2 + ny,  z2 + nz);
      v6.set( x3 + nx,  y3 + ny,  z3 + nz);
      v5.set( x4 + nx,  y4 + ny,  z4 + nz);

      norms[0] = new Vector3f( nx,  ny,  nz);
      norms[4] = new Vector3f(-nx, -ny, -nz);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      fissionNormA = -nx / mag;
      fissionNormB = -ny / mag;
      fissionNormC = -nz / mag;
      fissionNormD = (-nx * (x1 + nx) + -ny * (y1 + ny) + -nz * (z1 + nz)) / mag;

    }


    if (whichOne == 10)
    {
      nx = nx * (5.0f * delta) +  epsilon;
      ny = ny * (5.0f * delta) +  epsilon;
      nz = nz * (5.0f * delta) +  epsilon;

      v1.set( x1 + nx,  y1 + ny,  z1 + nz);
      v2.set( x2 + nx,  y2 + ny,  z2 + nz);
      v3.set( x3 + nx,  y3 + ny,  z3 + nz);
      v4.set( x4 + nx,  y4 + ny,  z4 + nz);

      nx = nx - 2.0f * epsilon;
      ny = ny - 2.0f * epsilon;
      nz = nz - 2.0f * epsilon;

      v8.set( x1 + nx,  y1 + ny,  z1 + nz);
      v7.set( x2 + nx,  y2 + ny,  z2 + nz);
      v6.set( x3 + nx,  y3 + ny,  z3 + nz);
      v5.set( x4 + nx,  y4 + ny,  z4 + nz);

      norms[0] = new Vector3f( nx,  ny,  nz);
      norms[4] = new Vector3f(-nx, -ny, -nz);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      fissionNormA = -nx / mag;
      fissionNormB = -ny / mag;
      fissionNormC = -nz / mag;
      fissionNormD = (-nx * (x1 + nx) + -ny * (y1 + ny) + -nz * (z1 + nz)) / mag;

    }


    if (whichOne == 11)
    {
      nx = -nx * (3.0f * delta) + epsilon;
      ny = -ny * (3.0f * delta) + epsilon;
      nz = -nz * (3.0f * delta) + epsilon;

      v1.set( x1 + nx,  y1 + ny,  z1 + nz);
      v2.set( x2 + nx,  y2 + ny,  z2 + nz);
      v3.set( x3 + nx,  y3 + ny,  z3 + nz);
      v4.set( x4 + nx,  y4 + ny,  z4 + nz);

      nx = nx - 2.0f * epsilon;
      ny = ny - 2.0f * epsilon;
      nz = nz - 2.0f * epsilon;

      v8.set( x1 + nx,  y1 + ny,  z1 + nz);
      v7.set( x2 + nx,  y2 + ny,  z2 + nz);
      v6.set( x3 + nx,  y3 + ny,  z3 + nz);
      v5.set( x4 + nx,  y4 + ny,  z4 + nz);

      norms[0] = new Vector3f(-nx, -ny, -nz);
      norms[4] = new Vector3f( nx,  ny,  nz);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      fissionNormA = nx / mag;
      fissionNormB = ny / mag;
      fissionNormC = nz / mag;
      fissionNormD = (nx * (x1 + nx) + ny * (y1 + ny) + nz * (z1 + nz)) / mag;

    }


    //

   if ((whichOne == 12) || (whichOne == 13) || (whichOne == 14))
   {
     x1 = -os2  * scaleFactor;
     y1 =  0.0f * scaleFactor;
     z1 =  os2  * scaleFactor;

     x2 =  0.0f * scaleFactor;
     y2 = -os2  * scaleFactor;
     z2 = -os2  * scaleFactor;

     x3 =  os2  * scaleFactor;
     y3 =  0.0f * scaleFactor;
     z3 = -os2  * scaleFactor;

     x4 =  0.0f * scaleFactor;
     y4 =  os2  * scaleFactor;
     z4 =  os2  * scaleFactor;

     nx = y2 * z3 - y3 * z2;
     ny = x3 * z2 - x2 * z3;
     nz = x2 * y3 - x3 * y2;

     mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

     nx = nx / mag;
     ny = ny / mag;
     nz = nz / mag;

   } // end if statement

    // (-, +, 0)

    if (whichOne == 12)
    {
      nx = -nx * delta + epsilon;
      ny = -ny * delta + epsilon;
      nz = -nz * delta + epsilon;

      v1.set( x1 + nx,  y1 + ny,  z1 + nz);
      v2.set( x2 + nx,  y2 + ny,  z2 + nz);
      v3.set( x3 + nx,  y3 + ny,  z3 + nz);
      v4.set( x4 + nx,  y4 + ny,  z4 + nz);

      nx = nx - 2.0f * epsilon;
      ny = ny - 2.0f * epsilon;
      nz = nz - 2.0f * epsilon;

      v8.set( x1 + nx,  y1 + ny,  z1 + nz);
      v7.set( x2 + nx,  y2 + ny,  z2 + nz);
      v6.set( x3 + nx,  y3 + ny,  z3 + nz);
      v5.set( x4 + nx,  y4 + ny,  z4 + nz);

      norms[0] = new Vector3f(-nx, -ny, -nz);
      norms[4] = new Vector3f( nx,  ny,  nz);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      fissionNormA = nx / mag;
      fissionNormB = ny / mag;
      fissionNormC = nz / mag;
      fissionNormD = (nx * (x1 + nx) + ny * (y1 + ny) + nz * (z1 + nz)) / mag;

    }


    if (whichOne == 13)
    {
      nx = nx * 3.0f * delta + epsilon;
      ny = ny * 3.0f * delta + epsilon;
      nz = nz * 3.0f * delta + epsilon;

      v1.set( x1 + nx,  y1 + ny,  z1 + nz);
      v2.set( x2 + nx,  y2 + ny,  z2 + nz);
      v3.set( x3 + nx,  y3 + ny,  z3 + nz);
      v4.set( x4 + nx,  y4 + ny,  z4 + nz);

      nx = nx - 2.0f * epsilon;
      ny = ny - 2.0f * epsilon;
      nz = nz - 2.0f * epsilon;

      v8.set( x1 + nx,  y1 + ny,  z1 + nz);
      v7.set( x2 + nx,  y2 + ny,  z2 + nz);
      v6.set( x3 + nx,  y3 + ny,  z3 + nz);
      v5.set( x4 + nx,  y4 + ny,  z4 + nz);

      norms[0] = new Vector3f( nx,  ny,  nz);
      norms[4] = new Vector3f(-nx, -ny, -nz);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      fissionNormA = -nx / mag;
      fissionNormB = -ny / mag;
      fissionNormC = -nz / mag;
      fissionNormD = (-nx * (x1 + nx) + -ny * (y1 + ny) + -nz * (z1 + nz)) / mag;

    }


    if (whichOne == 14)
    {
      nx = -(nx * 5.0f * delta + epsilon);
      ny = -(ny * 5.0f * delta + epsilon);
      nz = -(nz * 5.0f * delta + epsilon);

      v1.set( x1 + nx,  y1 + ny,  z1 + nz);
      v2.set( x2 + nx,  y2 + ny,  z2 + nz);
      v3.set( x3 + nx,  y3 + ny,  z3 + nz);
      v4.set( x4 + nx,  y4 + ny,  z4 + nz);

      nx = nx - 2.0f * epsilon;
      ny = ny - 2.0f * epsilon;
      nz = nz - 2.0f * epsilon;

      v8.set( x1 + nx,  y1 + ny,  z1 + nz);
      v7.set( x2 + nx,  y2 + ny,  z2 + nz);
      v6.set( x3 + nx,  y3 + ny,  z3 + nz);
      v5.set( x4 + nx,  y4 + ny,  z4 + nz);

      norms[0] = new Vector3f(-nx, -ny, -nz);
      norms[4] = new Vector3f( nx,  ny,  nz);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      fissionNormA = nx / mag;
      fissionNormB = ny / mag;
      fissionNormC = nz / mag;
      fissionNormD = (nx * (x1 + nx) + ny * (y1 + ny) + nz * (z1 + nz)) / mag;

    }


    //

   if ((whichOne == 15) || (whichOne == 16) || (whichOne == 17))
   {
     x1 =  0.0f * scaleFactor;
     y1 =  os2  * scaleFactor;
     z1 =  os2  * scaleFactor;

     x2 = -os2  * scaleFactor;
     y2 =  0.0f * scaleFactor;
     z2 = -os2  * scaleFactor;

     x3 =  0.0f * scaleFactor;
     y3 = -os2  * scaleFactor;
     z3 = -os2  * scaleFactor;

     x4 =  os2  * scaleFactor;
     y4 =  0.0f * scaleFactor;
     z4 =  os2  * scaleFactor;

     nx = y2 * z3 - y3 * z2;
     ny = x3 * z2 - x2 * z3;
     nz = x2 * y3 - x3 * y2;

     mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

     nx = nx / mag;
     ny = ny / mag;
     nz = nz / mag;

   } // end if statement

    // (-, +, 0)

    if (whichOne == 15)
    {
      nx = nx * delta + epsilon;
      ny = ny * delta + epsilon;
      nz = nz * delta + epsilon;

      v1.set( x1 + nx,  y1 + ny,  z1 + nz);
      v2.set( x2 + nx,  y2 + ny,  z2 + nz);
      v3.set( x3 + nx,  y3 + ny,  z3 + nz);
      v4.set( x4 + nx,  y4 + ny,  z4 + nz);

      nx = nx - 2.0f * epsilon;
      ny = ny - 2.0f * epsilon;
      nz = nz - 2.0f * epsilon;

      v8.set( x1 + nx,  y1 + ny,  z1 + nz);
      v7.set( x2 + nx,  y2 + ny,  z2 + nz);
      v6.set( x3 + nx,  y3 + ny,  z3 + nz);
      v5.set( x4 + nx,  y4 + ny,  z4 + nz);

      norms[0] = new Vector3f( nx,  ny,  nz);
      norms[4] = new Vector3f(-nx, -ny, -nz);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      fissionNormA = -nx / mag;
      fissionNormB = -ny / mag;
      fissionNormC = -nz / mag;
      fissionNormD = (-nx * (x1 + nx) + -ny * (y1 + ny) + -nz * (z1 + nz)) / mag;

    }


    if (whichOne == 16)
    {
      nx = nx * 5.0f * delta + epsilon;
      ny = ny * 5.0f * delta + epsilon;
      nz = nz * 5.0f * delta + epsilon;

      v1.set( x1 + nx,  y1 + ny,  z1 + nz);
      v2.set( x2 + nx,  y2 + ny,  z2 + nz);
      v3.set( x3 + nx,  y3 + ny,  z3 + nz);
      v4.set( x4 + nx,  y4 + ny,  z4 + nz);

      nx = nx - 2.0f * epsilon;
      ny = ny - 2.0f * epsilon;
      nz = nz - 2.0f * epsilon;

      v8.set( x1 + nx,  y1 + ny,  z1 + nz);
      v7.set( x2 + nx,  y2 + ny,  z2 + nz);
      v6.set( x3 + nx,  y3 + ny,  z3 + nz);
      v5.set( x4 + nx,  y4 + ny,  z4 + nz);

      norms[0] = new Vector3f( nx,  ny,  nz);
      norms[4] = new Vector3f(-nx, -ny, -nz);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      fissionNormA = -nx / mag;
      fissionNormB = -ny / mag;
      fissionNormC = -nz / mag;
      fissionNormD = (-nx * (x1 + nx) + -ny * (y1 + ny) + -nz * (z1 + nz)) / mag;

    }


    if (whichOne == 17)
    {
      nx = -(nx * 3.0f * delta + epsilon);
      ny = -(ny * 3.0f * delta + epsilon);
      nz = -(nz * 3.0f * delta + epsilon);

      v1.set( x1 + nx,  y1 + ny,  z1 + nz);
      v2.set( x2 + nx,  y2 + ny,  z2 + nz);
      v3.set( x3 + nx,  y3 + ny,  z3 + nz);
      v4.set( x4 + nx,  y4 + ny,  z4 + nz);

      nx = nx - 2.0f * epsilon;
      ny = ny - 2.0f * epsilon;
      nz = nz - 2.0f * epsilon;

      v8.set( x1 + nx,  y1 + ny,  z1 + nz);
      v7.set( x2 + nx,  y2 + ny,  z2 + nz);
      v6.set( x3 + nx,  y3 + ny,  z3 + nz);
      v5.set( x4 + nx,  y4 + ny,  z4 + nz);

      norms[0] = new Vector3f(-nx, -ny, -nz);
      norms[4] = new Vector3f( nx,  ny,  nz);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      fissionNormA = nx / mag;
      fissionNormB = ny / mag;
      fissionNormC = nz / mag;
      fissionNormD = (nx * (x1 + nx) + ny * (y1 + ny) + nz * (z1 + nz)) / mag;

    }


    //

   if ((whichOne == 18) || (whichOne == 19) || (whichOne == 20))
   {
     x1 =  os2  * scaleFactor;
     y1 =  0.0f * scaleFactor;
     z1 =  os2  * scaleFactor;

     x2 =  0.0f * scaleFactor;
     y2 =  os2  * scaleFactor;
     z2 = -os2  * scaleFactor;

     x3 = -os2  * scaleFactor;
     y3 =  0.0f * scaleFactor;
     z3 = -os2  * scaleFactor;

     x4 =  0.0f * scaleFactor;
     y4 = -os2  * scaleFactor;
     z4 =  os2  * scaleFactor;

     nx = y2 * z3 - y3 * z2;
     ny = x3 * z2 - x2 * z3;
     nz = x2 * y3 - x3 * y2;

     mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

     nx = nx / mag;
     ny = ny / mag;
     nz = nz / mag;

   } // end if statement

    // (-, +, 0)

    if (whichOne == 18)
    {
      nx = -nx * delta + epsilon;
      ny = -ny * delta + epsilon;
      nz = -nz * delta + epsilon;

      v1.set( x1 + nx,  y1 + ny,  z1 + nz);
      v2.set( x2 + nx,  y2 + ny,  z2 + nz);
      v3.set( x3 + nx,  y3 + ny,  z3 + nz);
      v4.set( x4 + nx,  y4 + ny,  z4 + nz);

      nx = nx - 2.0f * epsilon;
      ny = ny - 2.0f * epsilon;
      nz = nz - 2.0f * epsilon;

      v8.set( x1 + nx,  y1 + ny,  z1 + nz);
      v7.set( x2 + nx,  y2 + ny,  z2 + nz);
      v6.set( x3 + nx,  y3 + ny,  z3 + nz);
      v5.set( x4 + nx,  y4 + ny,  z4 + nz);

      norms[0] = new Vector3f(-nx, -ny, -nz);
      norms[4] = new Vector3f( nx,  ny,  nz);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      fissionNormA = nx / mag;
      fissionNormB = ny / mag;
      fissionNormC = nz / mag;
      fissionNormD = (nx * (x1 + nx) + ny * (y1 + ny) + nz * (z1 + nz)) / mag;

    }


    if (whichOne == 19)
    {
      nx = nx * 3.0f * delta  + epsilon;
      ny = ny * 3.0f * delta  + epsilon;
      nz = nz * 3.0f * delta  + epsilon;

      v1.set( x1 + nx,  y1 + ny,  z1 + nz);
      v2.set( x2 + nx,  y2 + ny,  z2 + nz);
      v3.set( x3 + nx,  y3 + ny,  z3 + nz);
      v4.set( x4 + nx,  y4 + ny,  z4 + nz);

      nx = nx - 2.0f * epsilon;
      ny = ny - 2.0f * epsilon;
      nz = nz - 2.0f * epsilon;

      v8.set( x1 + nx,  y1 + ny,  z1 + nz);
      v7.set( x2 + nx,  y2 + ny,  z2 + nz);
      v6.set( x3 + nx,  y3 + ny,  z3 + nz);
      v5.set( x4 + nx,  y4 + ny,  z4 + nz);

      norms[0] = new Vector3f( nx,  ny,  nz);
      norms[4] = new Vector3f(-nx, -ny, -nz);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      fissionNormA = -nx / mag;
      fissionNormB = -ny / mag;
      fissionNormC = -nz / mag;
      fissionNormD = (-nx * (x1 + nx) + -ny * (y1 + ny) + -nz * (z1 + nz)) / mag;

    }


    if (whichOne == 20)
    {
      nx = -(nx * 5.0f * delta + epsilon);
      ny = -(ny * 5.0f * delta + epsilon);
      nz = -(nz * 5.0f * delta + epsilon);

      v1.set( x1 + nx,  y1 + ny,  z1 + nz);
      v2.set( x2 + nx,  y2 + ny,  z2 + nz);
      v3.set( x3 + nx,  y3 + ny,  z3 + nz);
      v4.set( x4 + nx,  y4 + ny,  z4 + nz);

      nx = nx - 2.0f * epsilon;
      ny = ny - 2.0f * epsilon;
      nz = nz - 2.0f * epsilon;

      v8.set( x1 + nx,  y1 + ny,  z1 + nz);
      v7.set( x2 + nx,  y2 + ny,  z2 + nz);
      v6.set( x3 + nx,  y3 + ny,  z3 + nz);
      v5.set( x4 + nx,  y4 + ny,  z4 + nz);

      norms[0] = new Vector3f(-nx, -ny, -nz);
      norms[4] = new Vector3f( nx,  ny,  nz);

      // norm = (A, B, C)  D = norm * (x1, y1, z1)

      mag = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      fissionNormA = nx / mag;
      fissionNormB = ny / mag;
      fissionNormC = nz / mag;
      fissionNormD = (nx * (x1 + nx) + ny * (y1 + ny) + nz * (z1 + nz)) / mag;

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
     float red   = Color.yellow.getRed() / 255;
     float green = Color.yellow.getGreen() / 255;
     float blue  = Color.yellow.getBlue() / 255;

     Appearance A = new Appearance();
     A.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
     A.setCapability(Appearance.ALLOW_MATERIAL_READ);
     A.setCapability(Appearance.ALLOW_MATERIAL_WRITE);

     RenderingAttributes RA = new RenderingAttributes();
     RA.setDepthBufferEnable(true);
     A.setRenderingAttributes(RA);

     Material theMaterial = new Material();
     theMaterial.setCapability(Material.ALLOW_COMPONENT_READ);
     theMaterial.setCapability(Material.ALLOW_COMPONENT_WRITE);

     theMaterial.setAmbientColor(0.25f, 0.25f, 0.25f);
     theMaterial.setDiffuseColor(red, green, blue);
//     theMaterial.setEmissiveColor(0.0f, 0.0f, 0.0f);
//     theMaterial.setLightingEnable(true);
//     theMaterial.setShininess(0.05f);
//     theMaterial.setSpecularColor(0.08f, 0.08f, 0.08f);

     A.setMaterial(theMaterial);

     A.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
                                                   PolygonAttributes.CULL_BACK, 0.01f, false));

     TransparencyAttributes TA = new TransparencyAttributes(TransparencyAttributes.NICEST, 0.6f);
     TA.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
     A.setTransparencyAttributes(TA);

     return(A);

  } // end createAppearanceSolid


} // end FissionPlan






