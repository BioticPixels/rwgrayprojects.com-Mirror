//
//  Need this to take the sphere's data.
//
package ndcp1;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import java.util.*;

import java.util.Enumeration;


public class MySphere extends Sphere
{

  int n;  // Principal
  int j;  // Total Spin
  int m;  // Azimuthal
  int l;  // Orbital
  int s;  // Spin
  int i;  // Isospin (-1=Proton, 1=Neutron)

  // FCC location
  float fccX;
  float fccY;
  float fccZ;

  // SCP location
  float scpX;
  float scpY;
  float scpZ;


  // current location
  float cX;
  float cY;
  float cZ;

  float r;      // distance to origin
  float theta;  // current theta for location in degrees
  float phi;    // current phi for location in degrees

  float dTheta; // to increament theta in IPB model (sphere motion)
  float dPhi;   // to increament phi in IPB model (sphere motion)


  boolean currentlyDisplayed;  // true=display this sphere

  float MAX_THETA_INTERVAL = 5.0f;  // 10 degrees
  float MAX_PHI_INTERVAL = 5.0f;    // 10 degrees

  public MySphere(int n, int j, int m, int l, int s, int i,
                         float X, float Y, float Z)
  {
     // the 2.828427125f value is the FCC unit distance (2 * sqrt(2)).

     super(2.828427125f, ENABLE_APPEARANCE_MODIFY | GENERATE_NORMALS, null);

     this.n = n;
     this.j = j;
     this.m = m;
     this.l = l;
     this.s = s;
     this.i = i;

     fccX = cX = X;
     fccY = cY = Y;
     fccZ = cZ = Z;

     // Set up the speed and direction for each particle.
     // Used only in IBM model.

     r = (float)Math.sqrt((X * X) + (Y * Y) + (Z * Z));
     theta = r2d(Math.acos((double)(Z / r)));

     if ( (theta < 0.1f) && (theta > -0.1f) ) theta = 0.1f;
     phi = r2d(Math.atan((double)(Y / X)) );

     if ((Y < 0.0f) && (X > 0.0f)) phi = -Math.abs(phi);
     if ((Y < 0.0f) && (X < 0.0f)) phi = 180.0f + Math.abs(phi);
     if ((Y > 0.0f) && (X > 0.0f)) phi =  Math.abs(phi);
     if ((Y > 0.0f) && (X < 0.0f)) phi = 180.0f - Math.abs(phi);

     // Make the motion so that some are + and some are -
     dTheta = (float)(Math.random() - 0.5) * MAX_THETA_INTERVAL;
     dPhi   = (float)(Math.random() - 0.5) * MAX_PHI_INTERVAL;

     // initial display is black
     currentlyDisplayed = false;

     setCapability(Shape3D.ALLOW_APPEARANCE_READ);
     setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);

  }  // end constructor MySphere

  public float r2d(double angle)
  {
     return((float)(angle * 360.0 / (2.0 * Math.PI)));

  } // end r2d

  public float d2r(double angle)
  {
     return((float)(angle * (2.0 * Math.PI) / 360.0 ));

  } // end d2r

} // end class MySphere




