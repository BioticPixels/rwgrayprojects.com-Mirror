package ndcp1;

//
// Constants for nuclear lattice model viewer
//
// Written by: R. W. Gray
// Date:       02-23-1999
// Version:    0.01
//
//

import java.awt.*;         // window tools

public interface Constants
{
  // Program constant definitions

  int MAX_PROTONS  = 200;  // maximum number of protons
  int MAX_NEUTRONS = 300;  // maximum number of neutrons
  int MAX_NUCLEONS = 501;  // maximum number of nucleons

  int PROTON  = 0;  // indicates what kind of sphere
  int NEUTRON = 1;  // indicates what kind of sphere

  int LATTICE_FCC = 0;  // The various lattice types
  int LATTICE_SCP = 1;

  double initialPerspectiveAngle = 10.0; // init perspectv angle (degrees)

  // Physical constant values would be defined here

  double LIGHT_SPEED = 6.0;   // (m/sec) the speed of light in vacuum

} // end interface Constants



