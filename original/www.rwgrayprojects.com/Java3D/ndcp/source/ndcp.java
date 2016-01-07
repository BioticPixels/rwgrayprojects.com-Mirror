package ndcp1;


//
// Viewer for nuclear lattice models
//
// Written by: R. W. Gray
// Date:       02-23-1999
// Version:    0.01
//

import java.awt.*;
import java.awt.event.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import java.util.*;

//import java.awt.AWTEvent;
import java.util.Enumeration;
//import com.sun.j3d.utils.behaviors.keyboard.*;






public class ndcp extends JFrame
                  implements  Constants
{

  static Dimension windowSize;

  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();

  static ndcp   theApp;    // The application object
  static JFrame theFrame;  // The application frame

  static NDCPMenu theMenu;

  // The nucleon tables are [nucleon][x,y,z][p,n]

  static int[][][] FCC  = new int[MAX_NUCLEONS][3][2];
  static int[][][] SCP  = new int[MAX_NUCLEONS][3][2];
  static int[][][] IPLG = new int[MAX_NUCLEONS][3][2];

  // [which-nucleon][0=currently-displayed, 1=transparence-value]
  MySphere[] theProtons  = new MySphere[MAX_NUCLEONS];
  MySphere[] theNeutrons = new MySphere[MAX_NUCLEONS];

  Shape3D[] allFCCBonds = new Shape3D[2500];
  Bond[] fccBonds = new Bond[2500];
  int numFCCBonds;
  int cntFCCppBonds = 0;
  int cntFCCnnBonds = 0;
  int cntFCCpnBonds = 0;

  Shape3D[] allSCPBonds = new Shape3D[2500];
  Bond[] scpBonds = new Bond[2500];
  int numSCPBonds;
  int cntSCPppBonds = 0;
  int cntSCPnnBonds = 0;
  int cntSCPpnBonds = 0;

  double bondRadius = 0.10;

  int showBondType = 0;  // 0=none, 1=PP, 2=NN, 3=PN, 4=PP&NN, 5=PP*PN, 6=NN&PN, 7=all

  // 21 Fission planes

  Shape3D[] theFissionPlanes = new Shape3D[21];
  FissionPlane[] fPlanes = new FissionPlane[21];
  int whichFissionPlane = -1; // -1=none, 0, 1, ..., 20
  int cntFissionPP = 0;
  int cntFissionNN = 0;
  int cntFissionPN = 0;
  int cntFissionLeft  = 0;
  int cntFissionRight = 0;

  float FPCr = 0.0f;  // fission plane color: red component
  float FPCg = 0.0f;  // fission plane color: green component
  float FPCb = 0.0f;  // fission plane color: blue component
  float FPTransparency = 0.7f; // fission plane transparency


  //

  TransformGroup[] objPPosition = new TransformGroup[MAX_NUCLEONS];
  TransformGroup[] objNPosition = new TransformGroup[MAX_NUCLEONS];

  TransformGroup[] objPSize = new TransformGroup[MAX_NUCLEONS];
  TransformGroup[] objNSize = new TransformGroup[MAX_NUCLEONS];


  RandomBehavior myRandomBehavior;
  AutoRotBehavior myAutoRotBehavior;

  Switch groupProtons;
  Switch groupNeutrons;
  Switch groupAxes;
  Switch groupFCCBonds;
  Switch groupSCPBonds;
  Switch groupFissionPlanes;

  Switch groupNSpheres;
  Switch groupJCylinders;
  Switch groupMCones;
  Switch groupSCircles;
  Switch groupICircles;

  Switch groupAlphas;

  DirectionalLight lightD1;

  int cntP;   // the number of protons in the array
  int cntN;   // the number of neutrons in the array

  // Current options/state values, i.e. what model is selected, etc.
  char model;  // G=IPM, L=LDM, F=FCC, S=SCP, A=Alpha, B=IBM, Q=Quarks
  String modelName;

  // Display status info

  boolean DisplayStatus;
  String[] nucleusName = {"(H) Hydrogen", "(He) Helium", "(Li) Lithium",
                          "(Be) Beryllium", "(B) Boron", "(C) Carbon",
                          "(N) Nitrogen", "(O) Oxygen", "(F) Fluorine",
                          "(Ne) Neon", "(Na) Sodium", "(Mg) Magnesium",
                          "(Al) Aluminum", "(Si) Silicon", "(P) Phosphorus",
                          "(S) Sulfur", "(Cl) Chlorine", "(Ar) Argon",
                          "(K) Potassium", "(Ca) Calcium", "(Sc) Scandium",
                          "(Ti) Titanium", "(V) Vanadium", "(Cr) Chromium",
                          "(Mn) Manganese", "(Fe) Iron", "(Co) Cobalt",
                          "(Ni) Nickel", "(Cu) Copper", "(Zn) Zinc",
                          "(Ga) Gallium", "(Ge) Germanium", "(As) Arsenic",
                          "(Se) Selenium", "(Br) Bromine", "(Kr) Krypton",
                          "(Rb) Rubidium", "(Sr) Strontium", "(Y) Yttrium",
                          "(Zr) Zirconium", "(Nb) Niobium", "(Mo) Molybdenum",
                          "(Tc) Technetium", "(Ru) Ruthenium", "(Rh) Rhodium",
                          "(Pd) Palladium", "(Ag) Silver", "(Cd) Cadmium",
                          "(In) Indium", "(Sn) Tin", "(Sb) Antimony",
                          "(Te) Tellurium", "(I) Iodine", "(Xe) Xenon",
                          "(Cs) Cesium", "(Ba) Barium", "(La) Lanthanum",
                          "(Ce) Cerium", "(Pr) Praseodymium", "(Nd) Neodymium",
                          "(Pr) Promethium", "(Sm) Samarium", "(Eu) Europium",
                          "(Gd) Gadolinium", "(Tb) Terbium", "(Dy) Dysprosium",
                          "(Ho) Holmium", "(Er) Erbium", "(Tm) Thulium",
                          "(Yb) Ytterbium", "(Lu) Lutecium", "(Hf) Hafnium",
                          "(Ta) Tantalum", "(W) Tungsten", "(Re) Rhenium",
                          "(Os) Osmium", "(Ir) Iridium", "(Pt) Platinum",
                          "(Au) Gold", "(Hg) Mercury", "(Tl) Thallium",
                          "(Pb) Lead", "(Bi) Bismuth", "(Po) Polonium",
                          "(At) Astatine", "(Rn) Radon", "(Fr) Francium",
                          "(Ra) Radium", "(Ac) Actinium", "(Th) Thorium",
                          "(Pa) Protactinium", "(U) Uranium", "(Np) Neptunium",
                          "(Pu) Plutonium", "(Am) Americium", "(Cm) Curium",
                          "(Bk) Berkelium", "(Cf) Californium", "(Es) Einsteinium",
                          "(Fm) Fermium", "(Md) Mendelevium", "(No) Nobelium",
                          "(Lr) Lawrencium"
                         };

  int currentZ;
  int currentN;

  double fccSpacing;              // with the fcc coordinates used, this is 2 * sqrt(2)
  double currentLatticeSpacing;   // in fm units, distance to nearest nucleon
  double currentNucleonRadius;    // in fm units


  // view platform location
  float vInitZLoc;    // initial Z-axis location

  float vpX = 0.0f;
  float vpY = 0.0f;
  float vpZ = 25.0f;

  float vpRadiusInc = 0.025f;  // Pecentage of radius for change

  float voX = 0.0f;
  float voY = 1.0f;
  float voZ = 0.0f;


  // Revolve parameters

  double revolveSpeed = 5.0;   // in degrees

  boolean revolveOn = false;

  boolean revolveX = true;
  boolean revolveY = false;
  boolean revolveZ = false;
  boolean zoomR    = false;

  // Axes parameters

  // 0=x, 1=y, 2=z, 3=xy, 4=yz, 5=xz
  Shape3D[] theAxes = new Shape3D[6];

  float AXISCr = 0.0f;  // axis color: red component
  float AXISCg = 0.0f;  // axis color: green component
  float AXISCb = 0.0f;  // axis color: blue component


  float axisLength = 5.0f;

  boolean xOn = false;
  boolean yOn = false;
  boolean zOn = false;

  boolean xyOn = false;
  boolean yzOn = false;
  boolean xzOn = false;


  // Shells: Sub-Structure Parameters

  // Quantum Number Color Options: Which quantum numbers to color.
  int QNCOption = 6;  // 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

  // At most, there are 7 sub-structures to be displayed, e.g. n=0, 1, ..., 6
  // Each sub-structure/shell needs a different color.
  // Each type of shell (n, j, m, s, i) can share the sam appearance.
  float[][] theColors = new float[7][3];  // [level][r,g,b]
  Appearance[] theShellAppearances = new Appearance[7];

  boolean showSubstructure = false;
  float SubStructureTransparency = 0.3f;

  Sphere[] nSpheres = new Sphere[7];
  float[]  nRadius = new float[7];  // radius for each n value

  float[] jRadius = new float[7];  // radius for each j vlaue
  Shape3D[] jCylinders = new Shape3D[7];

  Shape3D[] mCones = new Shape3D[7];

  Shape3D[] iCircles = new Shape3D[7];

  Shape3D[] sCircles = new Shape3D[7];




  // alpha clusters

  int MAXALPHA = 10;
  Sphere[] alphaSphere = new Sphere[MAXALPHA];  // use the same sphere for all alpha clusters
  float[][] alphaCenter = new float[MAXALPHA][3];  // (x, y, z) for each alpha
  int numAlphas;                     // number of alphas defined
  float alphaRadius;                 // current alpha radius

  TransformGroup AlphaTG;  // Used to set and change the size of all alpha spheres

  boolean DisplayAlpahaAsWire = false;
  Appearance alphaWire;
  Appearance alphaSolid;





  // Background Parameters

  Background BG;      // the background;
  float BGCr = 0.0f;  // background color: red component
  float BGCg = 0.0f;  // background color: green component
  float BGCb = 0.0f;  // background color: blue component


  // Text Colors

  float TCr = 1.0f;  // text color: red component
  float TCg = 1.0f;  // text color: green component
  float TCb = 1.0f;  // text color: blue component


  // main() is only called if running as an application
/*
  public static void main(String[] args)
  {
    theApp = new NDCP();
    theApp.init();             // call the init method
  } // end main

*/
  public static void main(String[] p)
  {
    theApp = new ndcp();
  }

  /**Construct the frame*/
  public ndcp()
  {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e)
  {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      System.exit(0);
    }
  }



  private void jbInit() throws Exception
  {
    //setIconImage(Toolkit.getDefaultToolkit().createImage(ndcp.class.getResource("[Your Icon]")));
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(400, 300));
    this.setTitle("Nuclear Display Computer Program");

    theApp = this;

    currentZ = 0;
    currentN = 0;

    model = 'G';  // Start with IPM
    modelName = new String("Independent Particle");

    fccSpacing = 2.0 * Math.sqrt(2.0);
    currentLatticeSpacing = 2.026;       // 2.026 fm between nearest fcc lattice sites.
    currentNucleonRadius = 0.86;         // in fm units.

    showBondType = 0;

    DisplayStatus = true;

    // The structures/substructures are to be colored in the
    // following color order.

    // red
    theColors[0][0] = 1.0f;
    theColors[0][1] = 0.0f;
    theColors[0][2] = 0.0f;

    // yellow
    theColors[1][0] = 1.0f;
    theColors[1][1] = 1.0f;
    theColors[1][2] = 0.0f;

    // purple
    theColors[2][0] = 219.0f / 255.0f;
    theColors[2][1] =   0.0f / 255.0f;
    theColors[2][2] = 219.0f / 255.0f;

    // green
    theColors[3][0] = 0.0f;
    theColors[3][1] = 1.0f;
    theColors[3][2] = 0.0f;

    // blue
    theColors[4][0] = 0.0f;
    theColors[4][1] = 0.0f;
    theColors[4][2] = 1.0f;

    // tan
    theColors[5][0] = 255.0f / 255.0f;
    theColors[5][1] = 180.0f / 255.0f;
    theColors[5][2] = 104.0f / 255.0f;

    // magenta
    theColors[6][0] = 1.0f;
    theColors[6][1] = 0.0f;
    theColors[6][2] = 1.0f;


    for (int i = 0; i < 7; i++)
    {
      theShellAppearances[i] = createShellAppearance(theColors[i][0],
                                                     theColors[i][1],
                                                     theColors[i][2]);
    } // end for statement

    // initial view platform location

    vInitZLoc  = 25.0f; // Initial view platform z-axis location

    // before we do *anything*, get the window size.

    theFrame = new JFrame("Nuclear Display Computer Program");
    theFrame.getContentPane().setBackground(Color.black);
    theFrame.getContentPane().setLayout(new BorderLayout());

    Toolkit theKit = theFrame.getToolkit();
    windowSize = theKit.getScreenSize();

    theFrame.setBounds(0, 0, windowSize.width, windowSize.height);


    FCCcoordinates();
    SCPcoordinates();

    // define all bonds;
    // FCC Bonds

    numFCCBonds = 0;

    // PP bonds

    for (int i = 0; i < (cntP-1); i++)
    {
      double x1 = theProtons[i].fccX;
      double y1 = theProtons[i].fccY;
      double z1 = theProtons[i].fccZ;

      for (int j = i+1; j < cntP; j++)
      {

        double x2 = theProtons[j].fccX;
        double y2 = theProtons[j].fccY;
        double z2 = theProtons[j].fccZ;

        double d = Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1) + (z2 - z1)*(z2 - z1));

        if (d < 3.0)
        {
          fccBonds[numFCCBonds] = new Bond(theApp, i, j, 1);
          numFCCBonds++;
        } //  end if statement

      } // end for statement

    } // end for statement


    // NN bonds

    for (int i = 0; i < (cntN-1); i++)
    {
      double x1 = theNeutrons[i].fccX;
      double y1 = theNeutrons[i].fccY;
      double z1 = theNeutrons[i].fccZ;

      for (int j = i+1; j < cntN; j++)
      {

        double x2 = theNeutrons[j].fccX;
        double y2 = theNeutrons[j].fccY;
        double z2 = theNeutrons[j].fccZ;

        double d = Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1) + (z2 - z1)*(z2 - z1));

        if (d < 3.0)
        {
          fccBonds[numFCCBonds] = new Bond(theApp, i, j, 2);
          numFCCBonds++;
        } //  end if statement

      } // end for statement

    } // end for statement


    // PN bonds

    for (int i = 0; i < cntP; i++)
    {
      double x1 = theProtons[i].fccX;
      double y1 = theProtons[i].fccY;
      double z1 = theProtons[i].fccZ;

      for (int j = 0; j < cntN; j++)
      {

        double x2 = theNeutrons[j].fccX;
        double y2 = theNeutrons[j].fccY;
        double z2 = theNeutrons[j].fccZ;

        double d = Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1) + (z2 - z1)*(z2 - z1));

        if (d < 3.0)
        {
          fccBonds[numFCCBonds] = new Bond(theApp, i, j, 3);
          numFCCBonds++;
        } //  end if statement

      } // end for statement

    } // end for statement

    //
    // SCP Bonds
    //

    numSCPBonds = 0;

    // PP bonds

    for (int i = 0; i < (cntP-1); i++)
    {
      double x1 = theProtons[i].scpX;
      double y1 = theProtons[i].scpY;
      double z1 = theProtons[i].scpZ;

      for (int j = i+1; j < cntP; j++)
      {

        double x2 = theProtons[j].scpX;
        double y2 = theProtons[j].scpY;
        double z2 = theProtons[j].scpZ;

        double d = Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1) + (z2 - z1)*(z2 - z1));

        if (d < 2.1)
        {
          scpBonds[numSCPBonds] = new Bond(theApp, i, j, 4);
          numSCPBonds++;
        } //  end if statement

      } // end for statement

    } // end for statement


    // NN bonds

    for (int i = 0; i < (cntN-1); i++)
    {
      double x1 = theNeutrons[i].scpX;
      double y1 = theNeutrons[i].scpY;
      double z1 = theNeutrons[i].scpZ;

      for (int j = i+1; j < cntN; j++)
      {

        double x2 = theNeutrons[j].scpX;
        double y2 = theNeutrons[j].scpY;
        double z2 = theNeutrons[j].scpZ;

        double d = Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1) + (z2 - z1)*(z2 - z1));

        if (d < 2.1)
        {
          scpBonds[numSCPBonds] = new Bond(theApp, i, j, 5);
          numSCPBonds++;
        } //  end if statement

      } // end for statement

    } // end for statement


    // PN bonds

    for (int i = 0; i < cntP; i++)
    {
      double x1 = theProtons[i].scpX;
      double y1 = theProtons[i].scpY;
      double z1 = theProtons[i].scpZ;

      for (int j = 0; j < cntN; j++)
      {

        double x2 = theNeutrons[j].scpX;
        double y2 = theNeutrons[j].scpY;
        double z2 = theNeutrons[j].scpZ;

        double d = Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1) + (z2 - z1)*(z2 - z1));

        if (d < 2.1)
        {
          scpBonds[numSCPBonds] = new Bond(theApp, i, j, 6);
          numSCPBonds++;

if ((i == 3) && (j == 12))
{
System.out.println("P3-N12 SCP bond found: #"+(numSCPBonds-1));
}

if ((i == 12) && (j == 1))
{
System.out.println("P12-N1 SCP bond found #"+(numSCPBonds-1));
}

        } //  end if statement

      } // end for statement

    } // end for statement

System.out.println("Num fcc bonds: "+numFCCBonds);
System.out.println("Num scp bonds: "+numSCPBonds);


    // define radii for each n value

    float[] accumR = new float[7];
    int[] countsR = {0, 0, 0, 0, 0, 0, 0};

    for (int i = 0; i < cntP; i++)
    {
      float x = theProtons[i].fccX;
      float y = theProtons[i].fccY;
      float z = theProtons[i].fccZ;

      accumR[theProtons[i].n] += Math.sqrt(x * x + y * y + z * z);

      countsR[theProtons[i].n]++;
    } // end for statement


    for (int i = 0; i < cntN; i++)
    {
      float x = theNeutrons[i].fccX;
      float y = theNeutrons[i].fccY;
      float z = theNeutrons[i].fccZ;

      accumR[theNeutrons[i].n] += Math.sqrt(x * x + y * y + z * z);

      countsR[theNeutrons[i].n]++;
    } // end for statement


    for (int i = 0; i < 7; i++)
    {
      nRadius[i] = accumR[i] / countsR[i];

    } // end for statement


    // define cylinder radii for each j value
    float[] accumX = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    float[] accumY = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    int[] counts = {0, 0, 0, 0, 0, 0, 0};

    float[] accumRr = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};

    for (int i = 0; i < cntP; i++)
    {
       int j = (int)((theProtons[i].j / 2.0f) + 0.5f) - 1;

       accumRr[j] += Math.sqrt(theProtons[i].fccX * theProtons[i].fccX + theProtons[i].fccY * theProtons[i].fccY);

       counts[j]++;

    } // end for statement i

    for (int i = 0; i < cntN; i++)
    {
       int j = (int)((theNeutrons[i].j / 2.0f) + 0.5f) - 1;

       accumRr[j] += Math.sqrt(theNeutrons[i].fccX * theNeutrons[i].fccX + theNeutrons[i].fccY * theNeutrons[i].fccY);

       counts[j]++;

    } // end for statement i

    for (int i = 0; i < 7; i++)
    {

      jRadius[i] = accumRr[i] / counts[i];

    } // end for statement



    // Define all alpha clusters

    numAlphas = 0;

    alphaWire = createAlphaWire();
    alphaSolid = createAlphaSolid();

    alphaRadius = (float)Math.sqrt(2.0);

    alphaSphere[0] = new Sphere(alphaRadius, Sphere.ENABLE_APPEARANCE_MODIFY | Sphere.GENERATE_NORMALS, null);
    alphaSphere[0].setAppearance(alphaWire);

    alphaCenter[numAlphas][0] = 0.0f;  // (x, y, z) for each alpha
    alphaCenter[numAlphas][1] = 0.0f;
    alphaCenter[numAlphas][2] = 0.0f;

    numAlphas++;

    alphaSphere[1] = new Sphere(alphaRadius, Sphere.ENABLE_APPEARANCE_MODIFY | Sphere.GENERATE_NORMALS, null);
    alphaSphere[1].setAppearance(alphaWire);

    alphaCenter[numAlphas][0] = 2.0f;  // (x, y, z) for each alpha
    alphaCenter[numAlphas][1] = 0.0f;
    alphaCenter[numAlphas][2] = 0.0f;

    numAlphas++;


// Start Testing

    float hz = 0.0f;

    for (int i = 0; i < cntN; i++)
    {
      float x = theNeutrons[i].fccX;
      float y = theNeutrons[i].fccY;
      float z = theNeutrons[i].fccZ;

      if (theNeutrons[i].fccZ == 13.0f)
      {
        System.out.println("("+theNeutrons[i].fccX+", "+theNeutrons[i].fccY+", "+theNeutrons[i].fccZ+")");
        hz = theNeutrons[i].fccZ;
      } // end if statement

    } // end for statement



// End Testing


    // SimpleUniverse is a Convenience Utility class

    MyCanvas3D canvas3D = new MyCanvas3D(theApp);
    SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

    // creat the menu

    // This is required!  Otherwise the heaveyweight canvas3D
    // sits on to of the lightweight menus and you never see them.

    JPopupMenu.setDefaultLightWeightPopupEnabled(false);

    theMenu = new NDCPMenu(theApp);

    // create all the geometry

    BranchGroup scene = createSceneGraph(simpleU);
    simpleU.addBranchGraph(scene);


    theFrame.setJMenuBar(theMenu);

    theFrame.getContentPane().add("Center", canvas3D);

    theFrame.setVisible(true);

  } // end init()


 public BranchGroup createSceneGraph(SimpleUniverse SU)
 {
   // Create the root of the branch graph
   BranchGroup objRoot = new BranchGroup();

   // setup the background color

   BoundingSphere worldBounds = new BoundingSphere(new Point3d( 0.0, 0.0, 0.0 ), 1000.0 );

   BG = new Background(BGCr, BGCg, BGCb);
   BG.setCapability(Background.ALLOW_COLOR_WRITE);
   BG.setApplicationBounds(worldBounds);
   objRoot.addChild(BG);

   // Create a light source

   AmbientLight lightA = new AmbientLight();
   lightA.setInfluencingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0));
   objRoot.addChild(lightA);

   lightD1 = new DirectionalLight();
   lightD1.setInfluencingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0));
   lightD1.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
   objRoot.addChild(lightD1);


   // Construct the FCC model

   // ***** Sphere branch ******

   // add a transform object to allow scaling the spheres
//   Transform3D T3DScale = new Transform3D();
//   T3DScale.setScale(currentNucleonRadius);

   TransformGroup objBehave = new TransformGroup();
   objBehave.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

   objRoot.addChild(objBehave);

   myRandomBehavior = new RandomBehavior(objBehave, theApp);
   myRandomBehavior.setSchedulingBounds(worldBounds);
   objBehave.addChild(myRandomBehavior);


   // Add switch to allow render picking of protons

   groupProtons = new Switch();
   groupProtons.setCapability(Switch.ALLOW_SWITCH_WRITE);
   objBehave.addChild(groupProtons);

   for (int i = 0; i < cntP; i++)
   {
     // add a transform group to give location of sphere

     float x = theProtons[i].fccX;
     float y = theProtons[i].fccY;
     float z = theProtons[i].fccZ;

     Transform3D T3DP = new Transform3D();
     T3DP.setTranslation(new Vector3f(x, y, z));
     objPPosition[i] = new TransformGroup(T3DP);
     objPPosition[i].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

     groupProtons.addChild(objPPosition[i]);

     Transform3D T3DPSize = new Transform3D();
     T3DPSize.setScale(currentNucleonRadius / currentLatticeSpacing);
     objPSize[i] = new TransformGroup(T3DPSize);
     objPSize[i].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

     objPPosition[i].addChild(objPSize[i]);

     theProtons[i].setAppearance(createAppearanceProton());
     theProtons[i].setCapability(Shape3D.ALLOW_APPEARANCE_READ);
     theProtons[i].setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
     objPSize[i].addChild(theProtons[i]);

   } // end for statement


   // Add switch to allow render picking of neutrons

   groupNeutrons = new Switch();
   groupNeutrons.setCapability(Switch.ALLOW_SWITCH_WRITE);
   objBehave.addChild(groupNeutrons);

   for (int i = 0; i < cntN; i++)
   {
     // add a transform group to give location of sphere

     float x = theNeutrons[i].fccX;
     float y = theNeutrons[i].fccY;
     float z = theNeutrons[i].fccZ;

     Transform3D T3DN = new Transform3D();
     T3DN.setTranslation(new Vector3f(x, y, z));
     objNPosition[i] = new TransformGroup(T3DN);
     objNPosition[i].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

     groupNeutrons.addChild(objNPosition[i]);


     Transform3D T3DNSize = new Transform3D();
     T3DNSize.setScale(currentNucleonRadius / currentLatticeSpacing);
     objNSize[i] = new TransformGroup(T3DNSize);
     objNSize[i].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

     objNPosition[i].addChild(objNSize[i]);

     theNeutrons[i].setAppearance(createAppearanceNeutron());
     theNeutrons[i].setCapability(Shape3D.ALLOW_APPEARANCE_READ);
     theNeutrons[i].setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
     objNSize[i].addChild(theNeutrons[i]);

   } // end for statement


   // Determine which to make visible

   groupProtons.setWhichChild(Switch.CHILD_MASK);
   groupProtons.setChildMask(getProtonMask());
   groupNeutrons.setWhichChild(Switch.CHILD_MASK);
   groupNeutrons.setChildMask(getNeutronMask());


   // Set the initial viewing platform location

   TransformGroup vpTrans = null;
   Transform3D T3D = new Transform3D();
   Vector3f translate = new Vector3f();

   vpTrans = SU.getViewingPlatform().getViewPlatformTransform();
   translate.set(0.0f, 0.0f, vInitZLoc);
   T3D.setTranslation(translate);
   vpTrans.setTransform(T3D);


   myAutoRotBehavior = new  AutoRotBehavior(vpTrans, theApp);
   myAutoRotBehavior.setSchedulingBounds(worldBounds);
   myAutoRotBehavior.setEnable(false);
   objRoot.addChild(myAutoRotBehavior);

   SimpleBehaviorZ myKeyInputBehavior = new  SimpleBehaviorZ(theApp, SU, vpTrans, vpZ);
   myKeyInputBehavior.setSchedulingBounds(worldBounds);
   objRoot.addChild(myKeyInputBehavior);

   SimpleMouseBehavior myMouseBehavior = new  SimpleMouseBehavior(theApp, SU, vpTrans, vpZ);
   myMouseBehavior.setSchedulingBounds(worldBounds);
   objRoot.addChild(myMouseBehavior);


   SU.getViewer().getView().setDepthBufferFreezeTransparent(false);

   // set up the axes and planes

   groupAxes = new Switch();
   groupAxes.setCapability(Switch.ALLOW_SWITCH_WRITE);
   objRoot.addChild(groupAxes);

   MyAxes anAxis = new MyAxes(theApp, 'X');
   theAxes[0] = new Shape3D();
   theAxes[0].setGeometry(anAxis.Line());
   theAxes[0].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
   theAxes[0].setAppearance(anAxis.createAppearanceLine());
   groupAxes.addChild(theAxes[0]);

   anAxis = new MyAxes(theApp, 'Y');
   theAxes[1] = new Shape3D();
   theAxes[1].setGeometry(anAxis.Line());
   theAxes[1].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
   theAxes[1].setAppearance(anAxis.createAppearanceLine());
   groupAxes.addChild(theAxes[1]);

   anAxis = new MyAxes(theApp, 'Z');
   theAxes[2] = new Shape3D();
   theAxes[2].setGeometry(anAxis.Line());
   theAxes[2].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
   theAxes[2].setAppearance(anAxis.createAppearanceLine());
   groupAxes.addChild(theAxes[2]);

   MyXYZPlane aPlane = new MyXYZPlane(theApp, "XY");
   theAxes[3] = new Shape3D();
   theAxes[3].setGeometry(aPlane.Solid());
   theAxes[3].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
   theAxes[3].setAppearance(aPlane.createAppearanceSolid());
   groupAxes.addChild(theAxes[3]);

   aPlane = new MyXYZPlane(theApp, "YZ");
   theAxes[4] = new Shape3D();
   theAxes[4].setGeometry(aPlane.Solid());
   theAxes[4].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
   theAxes[4].setAppearance(aPlane.createAppearanceSolid());
   groupAxes.addChild(theAxes[4]);

   aPlane = new MyXYZPlane(theApp, "XZ");
   theAxes[5] = new Shape3D();
   theAxes[5].setGeometry(aPlane.Solid());
   theAxes[5].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
   theAxes[5].setAppearance(aPlane.createAppearanceSolid());
   groupAxes.addChild(theAxes[5]);

   // Determine which to make visible

   groupAxes.setWhichChild(Switch.CHILD_MASK);
   groupAxes.setChildMask(getAxesMask());

   // Define all bonds

   // FCC Bonds

   groupFCCBonds = new Switch();
   groupFCCBonds.setCapability(Switch.ALLOW_SWITCH_WRITE);
   objRoot.addChild(groupFCCBonds);

   Appearance anApp;
   Material theMaterial;

   for (int i = 0; i < numFCCBonds; i++)
   {
     allFCCBonds[i] = new Shape3D();
     allFCCBonds[i].setGeometry(fccBonds[i].Solid());
     allFCCBonds[i].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
     anApp = fccBonds[i].createAppearanceSolid();

     theMaterial = anApp.getMaterial();
     if (fccBonds[i].type == 1)
       theMaterial.setDiffuseColor(1.0f, 0.0f, 0.0f);
     if (fccBonds[i].type == 2)
       theMaterial.setDiffuseColor(0.0f, 0.0f, 1.0f);
     if (fccBonds[i].type == 3)
       theMaterial.setDiffuseColor(0.7f, 0.7f, 0.7f);
     allFCCBonds[i].setAppearance(anApp);
     groupFCCBonds.addChild(allFCCBonds[i]);

   } // end for statement

   // Determine which bonds to display
   groupFCCBonds.setWhichChild(Switch.CHILD_MASK);
   groupFCCBonds.setChildMask(getFCCBondMask());


   // SCP Bonds

   groupSCPBonds = new Switch();
   groupSCPBonds.setCapability(Switch.ALLOW_SWITCH_WRITE);
   objRoot.addChild(groupSCPBonds);

   for (int i = 0; i < numSCPBonds; i++)
   {
     allSCPBonds[i] = new Shape3D();
     allSCPBonds[i].setGeometry(scpBonds[i].Solid());
     allSCPBonds[i].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
     anApp = scpBonds[i].createAppearanceSolid();

     theMaterial = anApp.getMaterial();
     if (scpBonds[i].type == 4)
       theMaterial.setDiffuseColor(1.0f, 0.0f, 0.0f);
     if (scpBonds[i].type == 5)
       theMaterial.setDiffuseColor(0.0f, 0.0f, 1.0f);
     if (scpBonds[i].type == 6)
       theMaterial.setDiffuseColor(0.7f, 0.7f, 0.7f);
     allSCPBonds[i].setAppearance(anApp);
     groupSCPBonds.addChild(allSCPBonds[i]);
   } // end for statement

   // Determine which bonds to display

   groupSCPBonds.setWhichChild(Switch.CHILD_MASK);
   groupSCPBonds.setChildMask(getSCPBondMask());


   //
   //  there are 21 plans for fission: 12 assym, 9 sym = 21.
   //

   groupFissionPlanes = new Switch();
   groupFissionPlanes.setCapability(Switch.ALLOW_SWITCH_WRITE);
   objRoot.addChild(groupFissionPlanes);


   for (int i = 0; i < 21; i++)
   {

     fPlanes[i] = new FissionPlane(theApp, i);
     theFissionPlanes[i] = new Shape3D();
     theFissionPlanes[i].setGeometry(fPlanes[i].Solid());
     theFissionPlanes[i].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
     theFissionPlanes[i].setCapability(Shape3D.ALLOW_APPEARANCE_READ);
     theFissionPlanes[i].setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
     theFissionPlanes[i].setAppearance(fPlanes[i].createAppearanceSolid());
     groupFissionPlanes.addChild(theFissionPlanes[i]);


   } // end for statement

   groupFissionPlanes.setWhichChild(Switch.CHILD_MASK);
   groupFissionPlanes.setChildMask(getFissionPlaneMask());


   // setup the initial substructure spheres, cylinders, cones, circles.

   // 7 N spheres:

   groupNSpheres = new Switch();
   groupNSpheres.setCapability(Switch.ALLOW_SWITCH_WRITE);
   objRoot.addChild(groupNSpheres);

   for (int i = 0; i < 7; i++)
   {
     nSpheres[i] = new Sphere(nRadius[i], Sphere.ENABLE_APPEARANCE_MODIFY | Sphere.GENERATE_NORMALS, null);

     nSpheres[i].setAppearance(theShellAppearances[i]);
     groupNSpheres.addChild(nSpheres[i]);
   } // end for statement

   groupNSpheres.setWhichChild(Switch.CHILD_MASK);
   groupNSpheres.setChildMask(getNSpheresMask());

   // 7 J Cylinders

   groupJCylinders = new Switch();
   groupJCylinders.setCapability(Switch.ALLOW_SWITCH_WRITE);
   objRoot.addChild(groupJCylinders);

   for (int i = 0; i < 7; i++)
   {
     MyCylinder myCyl = new MyCylinder(0.0f, 0.0f, -2.0f, 0.0f, 0.0f, 2.0f, jRadius[i]);
     jCylinders[i] = new Shape3D();
     jCylinders[i].setGeometry(myCyl.Solid());
     jCylinders[i].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
     jCylinders[i].setAppearance(theShellAppearances[i]);
     jCylinders[i].setCapability(Shape3D.ALLOW_APPEARANCE_READ);
     jCylinders[i].setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);

     groupJCylinders.addChild(jCylinders[i]);
   } // end for statement

   groupJCylinders.setWhichChild(Switch.CHILD_MASK);
   groupJCylinders.setChildMask(getJCylindersMask());

   // M Cones:

   groupMCones = new Switch();
   groupMCones.setCapability(Switch.ALLOW_SWITCH_WRITE);
   objRoot.addChild(groupMCones);

   for (int i = 0; i < 7; i++)
   {
     MyCone myCone = new MyCone((float)(2*i + 1), 2.0f);
     mCones[i] = new Shape3D();
     mCones[i].setGeometry(myCone.Solid());
     mCones[i].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
     mCones[i].setAppearance(theShellAppearances[i]);
     mCones[i].setCapability(Shape3D.ALLOW_APPEARANCE_READ);
     mCones[i].setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);

     groupMCones.addChild(mCones[i]);

   } // end for statement

   groupMCones.setWhichChild(Switch.CHILD_MASK);
   groupMCones.setChildMask(getMConesMask());

   // S Circles

   groupSCircles = new Switch();
   groupSCircles.setCapability(Switch.ALLOW_SWITCH_WRITE);
   objRoot.addChild(groupSCircles);

   for (int i = 0; i < 7; i++)
   {
     MyCircle myCircle = new MyCircle('X', 1.0f*(i+1), 1.0f);
     sCircles[i] = new Shape3D();
     sCircles[i].setGeometry(myCircle.Solid());
     sCircles[i].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
     sCircles[i].setAppearance(theShellAppearances[i]);
     sCircles[i].setCapability(Shape3D.ALLOW_APPEARANCE_READ);
     sCircles[i].setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);

     groupSCircles.addChild(sCircles[i]);

   } // end for statement

   groupSCircles.setWhichChild(Switch.CHILD_MASK);
   groupSCircles.setChildMask(getSCirclesMask());

   // I Circles

   groupICircles = new Switch();
   groupICircles.setCapability(Switch.ALLOW_SWITCH_WRITE);
   objRoot.addChild(groupICircles);

   for (int i = 0; i < 7; i++)
   {
     MyCircle myCircle = new MyCircle('Z', 1.0f*(i+1), 1.0f);
     iCircles[i] = new Shape3D();
     iCircles[i].setGeometry(myCircle.Solid());
     iCircles[i].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
     iCircles[i].setAppearance(theShellAppearances[i]);
     iCircles[i].setCapability(Shape3D.ALLOW_APPEARANCE_READ);
     iCircles[i].setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);

     groupICircles.addChild(iCircles[i]);

   } // end for statement

   groupICircles.setWhichChild(Switch.CHILD_MASK);
   groupICircles.setChildMask(getICirclesMask());


   //
   //

   // Alpha Cluster Spheres

   // Scale all alphas

   Transform3D T3DASize = new Transform3D();
   T3DASize.setScale(alphaRadius);
   AlphaTG = new TransformGroup(T3DASize);
   AlphaTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

   objRoot.addChild(AlphaTG);

   // Allow each alpha cluster sphere to be turned on and off

   groupAlphas = new Switch();
   groupAlphas.setCapability(Switch.ALLOW_SWITCH_WRITE);
   AlphaTG.addChild(groupAlphas);


   for (int i = 0; i < numAlphas; i++)
   {
     // add a transform group to give location of alpha sphere

     float x = alphaCenter[i][0];
     float y = alphaCenter[i][1];
     float z = alphaCenter[i][2];

     Transform3D T3DA = new Transform3D();
     T3DA.setTranslation(new Vector3f(x, y, z));
     TransformGroup TG = new TransformGroup(T3DA);

     groupAlphas.addChild(TG);

     TG.addChild(alphaSphere[i]);

   } // end for statement

   groupAlphas.setWhichChild(Switch.CHILD_MASK);
   groupAlphas.setChildMask(getAlphaMask());


   //

   objRoot.compile();

   return(objRoot);

 } // end of createSceneGraph method



  public void FCCcoordinates()
  {

    cntP = 0;
    cntN = 0;

    // generate all the (x, y, z) coordinates for the nucleons.

    int Shells = 6;

    int K = 0;

    for (int N = 0; N <= Shells; N++)
    {
      for (int L = N; L >=0; L--)
      {
        int JJ = (int)(2.0 * (double)L + 1.0);

        for (int M = (0-JJ); M<=JJ; M=M+2)
        {
          // for protons, i = -1

          for (int i = -1; i<=1; i=i+2)
          {
            double x = Math.abs(M) * Math.pow(-1.0, ((double)M)/2.0 + 0.5);

            double y = ((double)(JJ) + 1.0 - Math.abs(x)) *
                        Math.pow(-1.0, (double)(-i)/2.0+(double)(JJ)/2.0+(double)(M)/2.0+0.5);

            double z = (2.0*(double)(N) + 3.0 - Math.abs(x) - Math.abs(y)) *
                      Math.pow(-1.0, (double)(-i)/2.0 + (double)(N) - (double)(JJ)/2.0 - 1.0);

            int f = (i + 1) / 2;

            FCC[K][0][f] = (int)x;
            FCC[K][1][f] = (int)y;
            FCC[K][2][f] = (int)z;

            if (i == -1)
            {
              // create a proton

              theProtons[cntP] = new MySphere(N, JJ, M, L, 1, i,
                                              (float)x, (float)y, (float)z);
              cntP++;
            }
            else
            {
              // create a neutron

              theNeutrons[cntN] = new MySphere(N, JJ, M, L, 1, i,
                                              (float)x, (float)y, (float)z);

              cntN++;
            } // end if else

          } // end for i

          K = K + 1;

        } // end for M statement
      } // end for L statement
    } // end for N statement

  } // end FCCcoordinates

  public void SCPcoordinates()
  {
    // called only after FCCcoordinates() has been called

    int j = -1;

//    for (int k = 0; k<=167; k++)
    for (int k = 0; k<=(cntP/2 - 1); k++)
    {
      j = j + 1;

      SCP[j][0][0] = FCC[k][0][0];
      SCP[j][1][0] = FCC[k][1][0];
      SCP[j][2][0] = FCC[k][2][0];

      theProtons[j].scpX = FCC[k][0][0];
      theProtons[j].scpY = FCC[k][1][0];
      theProtons[j].scpZ = FCC[k][2][0];

      SCP[j][0][1] = FCC[k][0][1];
      SCP[j][1][1] = FCC[k][1][1];
      SCP[j][2][1] = FCC[k][2][1];

      theNeutrons[j].scpX = FCC[k][0][1];
      theNeutrons[j].scpY = FCC[k][1][1];
      theNeutrons[j].scpZ = FCC[k][2][1];


      j = j + 1;
      SCP[j][0][0] = -FCC[k][0][0];
      SCP[j][1][0] = FCC[k][1][0];
      SCP[j][2][0] = FCC[k][2][0];

      theProtons[j].scpX = -FCC[k][0][0];
      theProtons[j].scpY =  FCC[k][1][0];
      theProtons[j].scpZ =  FCC[k][2][0];


      SCP[j][0][1] = FCC[k][0][1];
      SCP[j][1][1] = -FCC[k][1][1];
      SCP[j][2][1] = FCC[k][2][1];

      theNeutrons[j].scpX =  FCC[k][0][1];
      theNeutrons[j].scpY = -FCC[k][1][1];
      theNeutrons[j].scpZ =  FCC[k][2][1];



    } // end for k statement

  } // end SCPcoordinates



  public Appearance createAppearanceProton()
  {

     Appearance A = new Appearance();
     A.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
     A.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
     A.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_READ);
     A.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
     A.setCapability(Appearance.ALLOW_MATERIAL_READ);
     A.setCapability(Appearance.ALLOW_MATERIAL_WRITE);


     RenderingAttributes RA = new RenderingAttributes();
     RA.setDepthBufferEnable(true);
     A.setRenderingAttributes(RA);


     Material theMaterial = new Material();
     theMaterial.setCapability(Material.ALLOW_COMPONENT_WRITE);

//     theMaterial.setAmbientColor(1.0f, 1.0f, 1.0f);

     theMaterial.setDiffuseColor(1.0f, 0.0f, 0.0f);

//     theMaterial.setEmissiveColor(1.0f, 1.0f, 1.0f);
     theMaterial.setLightingEnable(true);
//     theMaterial.setShininess(0.05f);
//     theMaterial.setSpecularColor(1.0f, 1.0f, 1.0f);


     A.setMaterial(theMaterial);


     A.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
                                                   PolygonAttributes.CULL_NONE, 0.01f, false));

     TransparencyAttributes TA = new TransparencyAttributes(TransparencyAttributes.SCREEN_DOOR, 0.0f);
     TA.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
     A.setTransparencyAttributes(TA);

     return(A);

  } // end createAppearanceProton


  public Appearance createAppearanceNeutron()
  {

     Appearance A = new Appearance();
     A.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
     A.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
     A.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_READ);
     A.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
     A.setCapability(Appearance.ALLOW_MATERIAL_READ);
     A.setCapability(Appearance.ALLOW_MATERIAL_WRITE);


     RenderingAttributes RA = new RenderingAttributes();
     RA.setDepthBufferEnable(true);
     A.setRenderingAttributes(RA);


     Material theMaterial = new Material();
     theMaterial.setCapability(Material.ALLOW_COMPONENT_WRITE);

//     theMaterial.setAmbientColor(1.0f, 1.0f, 1.0f);

     theMaterial.setDiffuseColor(0.0f, 0.0f, 1.0f);

//     theMaterial.setEmissiveColor(1.0f, 1.0f, 1.0f);
     theMaterial.setLightingEnable(true);
//     theMaterial.setShininess(0.05f);
//     theMaterial.setSpecularColor(1.0f, 1.0f, 1.0f);


     A.setMaterial(theMaterial);


     A.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
                                                   PolygonAttributes.CULL_NONE, 0.01f, false));

     TransparencyAttributes TA = new TransparencyAttributes(TransparencyAttributes.SCREEN_DOOR, 0.0f);
     TA.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
     A.setTransparencyAttributes(TA);

     return(A);

  } // end createAppearanceNeutron




  public Appearance createAlphaWire()
  {

     Appearance A = new Appearance();
     A.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
     A.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
     A.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_READ);
     A.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
     A.setCapability(Appearance.ALLOW_MATERIAL_READ);
     A.setCapability(Appearance.ALLOW_MATERIAL_WRITE);


     RenderingAttributes RA = new RenderingAttributes();
     RA.setDepthBufferEnable(true);
     A.setRenderingAttributes(RA);


     Material theMaterial = new Material();
     theMaterial.setCapability(Material.ALLOW_COMPONENT_WRITE);

//     theMaterial.setAmbientColor(1.0f, 1.0f, 1.0f);

     theMaterial.setDiffuseColor(0.50f, 0.70f, 1.0f);

//     theMaterial.setEmissiveColor(1.0f, 1.0f, 1.0f);
     theMaterial.setLightingEnable(true);
//     theMaterial.setShininess(0.05f);
//     theMaterial.setSpecularColor(1.0f, 1.0f, 1.0f);


     A.setMaterial(theMaterial);


     A.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_LINE,
                                                   PolygonAttributes.CULL_NONE, 0.01f, false));

//     TransparencyAttributes TA = new TransparencyAttributes(TransparencyAttributes.SCREEN_DOOR, 0.0f);
//     TA.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
//     A.setTransparencyAttributes(TA);

     return(A);

  } // end createAlphaWire





  public Appearance createAlphaSolid()
  {

     Appearance A = new Appearance();
     A.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
     A.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
     A.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_READ);
     A.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
     A.setCapability(Appearance.ALLOW_MATERIAL_READ);
     A.setCapability(Appearance.ALLOW_MATERIAL_WRITE);


     RenderingAttributes RA = new RenderingAttributes();
     RA.setDepthBufferEnable(true);
     A.setRenderingAttributes(RA);


     Material theMaterial = new Material();
     theMaterial.setCapability(Material.ALLOW_COMPONENT_WRITE);

//     theMaterial.setAmbientColor(1.0f, 1.0f, 1.0f);

     theMaterial.setDiffuseColor(0.50f, 0.70f, 1.0f);

//     theMaterial.setEmissiveColor(1.0f, 1.0f, 1.0f);
     theMaterial.setLightingEnable(true);
//     theMaterial.setShininess(0.05f);
//     theMaterial.setSpecularColor(1.0f, 1.0f, 1.0f);


     A.setMaterial(theMaterial);


     A.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
                                                   PolygonAttributes.CULL_NONE, 0.01f, false));

     TransparencyAttributes TA = new TransparencyAttributes(TransparencyAttributes.SCREEN_DOOR, 0.0f);
     TA.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
     A.setTransparencyAttributes(TA);

     return(A);

  } // end createAlphaSolid





  public Appearance createShellAppearance(float red, float green, float blue)
  {
     Appearance A = new Appearance();
     A.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
     A.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
//     A.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_READ);
//     A.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
     A.setCapability(Appearance.ALLOW_MATERIAL_READ);
     A.setCapability(Appearance.ALLOW_MATERIAL_WRITE);

     RenderingAttributes RA = new RenderingAttributes();
     RA.setDepthBufferEnable(true);
     A.setRenderingAttributes(RA);

     Material theMaterial = new Material();
     theMaterial.setCapability(Material.ALLOW_COMPONENT_READ);
     theMaterial.setCapability(Material.ALLOW_COMPONENT_WRITE);

     theMaterial.setAmbientColor(0.75f, 0.75f, 0.75f);
     theMaterial.setDiffuseColor(red, green, blue);
//     theMaterial.setLightingEnable(true);
//     theMaterial.setSpecularColor(0.05f, 0.05f, 0.05f);

     A.setMaterial(theMaterial);

     A.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
                                                   PolygonAttributes.CULL_NONE, 0.01f, true));

     TransparencyAttributes TA = new TransparencyAttributes(TransparencyAttributes.NICEST, SubStructureTransparency);
//     TA.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
     A.setTransparencyAttributes(TA);

     return(A);

  } // end createShellAppearance



  public BitSet getProtonMask()
  {

     BitSet mask = new BitSet(6);

     for (int i = 0; i < cntP; i++)
     {
       if (theProtons[i].currentlyDisplayed)
       {
         mask.set(i);
       } // end if sttaement
     } // end for statement

     return(mask);

  }  // end getProtonsMask


  public BitSet getNeutronMask()
  {

     BitSet mask = new BitSet(6);

     for (int i = 0; i < cntN; i++)
     {
       if (theNeutrons[i].currentlyDisplayed)
       {
         mask.set(i);
       } // end if sttaement
     } // end for statement

     return(mask);

  }  // end getNeutronMask



  public BitSet getAxesMask()
  {

     BitSet mask = new BitSet(6);

     if (xOn)
     {
       mask.set(0);
     } // end if sttaement
     if (yOn)
     {
       mask.set(1);
     } // end if sttaement
     if (zOn)
     {
       mask.set(2);
     } // end if sttaement
     if (xyOn)
     {
       mask.set(3);
     } // end if sttaement

     if (yzOn)
     {
       mask.set(4);
     } // end if sttaement
     if (xzOn)
     {
       mask.set(5);
     } // end if sttaement

     return(mask);

  }  // end getAxesMask



  public BitSet getFCCBondMask()
  {
     cntFCCppBonds = 0;
     cntFCCnnBonds = 0;
     cntFCCpnBonds = 0;

     BitSet mask = new BitSet(12);


     // showBondType: 0=none, 1=PP, 2=NN, 3=PN, 4=PP&NN, 5=PP&PN, 6=NN&PN, 7=all

     if (showBondType == 0) return(mask);

     if (theApp.model != 'F') return(mask);

     for (int i = 0; i < numFCCBonds; i++)
     {
       switch (fccBonds[i].type)
       {
         case 1:
                 if ((theProtons[fccBonds[i].p1].currentlyDisplayed) &&
                     (theProtons[fccBonds[i].p2].currentlyDisplayed) )
                 {
                   if ((showBondType == 1) || (showBondType == 4) ||
                       (showBondType == 5) || (showBondType == 7))
                   {
                     mask.set(i);
                     cntFCCppBonds++;
                   } // end if statement
                 } // end if statement
                 break;

         case 2:
                 if ((theNeutrons[fccBonds[i].n1].currentlyDisplayed) &&
                     (theNeutrons[fccBonds[i].n2].currentlyDisplayed) )
                 {
                   if ((showBondType == 2) || (showBondType == 4) ||
                       (showBondType == 6) || (showBondType == 7))
                   {
                     mask.set(i);
                     cntFCCnnBonds++;
                   }
                 } // end if statement
                 break;

         case 3:
                 if ((theProtons[fccBonds[i].p1].currentlyDisplayed) &&
                     (theNeutrons[fccBonds[i].n1].currentlyDisplayed) )
                 {
                   if ((showBondType == 3) || (showBondType == 5) ||
                       (showBondType == 6) || (showBondType == 7))
                   {
                     mask.set(i);
                     cntFCCpnBonds++;
                   }
                 } // end if statement
                 break;

       } // end switch

     } // end for statement

     return(mask);

  }  // end getFCCBondMask



  public BitSet getSCPBondMask()
  {
     cntSCPppBonds = 0;
     cntSCPnnBonds = 0;
     cntSCPpnBonds = 0;

     BitSet mask = new BitSet(16);

     // showBondType: 0=none, 1=PP, 2=NN, 3=PN, 4=PP&NN, 5=PP&PN, 6=NN&PN, 7=all
     if (showBondType == 0) return(mask);

     if (theApp.model != 'S') return(mask);

     for (int i = 0; i < numSCPBonds; i++)
     {
       switch (scpBonds[i].type)
       {
         case 4:
                 if ((theProtons[scpBonds[i].p1].currentlyDisplayed) &&
                     (theProtons[scpBonds[i].p2].currentlyDisplayed) )
                 {
                   if ((showBondType == 1) || (showBondType == 4) ||
                       (showBondType == 5) || (showBondType == 7))
                   {
                     mask.set(i);
                     cntSCPppBonds++;
                   }
                 } // end if statement
                 break;

         case 5:
                 if ((theNeutrons[scpBonds[i].n1].currentlyDisplayed) &&
                     (theNeutrons[scpBonds[i].n2].currentlyDisplayed) )
                 {
                   if ((showBondType == 2) || (showBondType == 4) ||
                       (showBondType == 6) || (showBondType == 7))
                   {
                     mask.set(i);
                     cntSCPnnBonds++;
                   }
                 } // end if statement
                 break;

         case 6:
                 if ((theProtons[scpBonds[i].p1].currentlyDisplayed) &&
                     (theNeutrons[scpBonds[i].n1].currentlyDisplayed) )
                 {

if ((scpBonds[i].p1 == 3) && (scpBonds[i].n1 == 12))
{
System.out.println("P3-N12 Checking for display: Bond #"+i);
}
if ((scpBonds[i].p1 == 12) && (scpBonds[i].n1 == 1))
{
System.out.println("P12-N1 Checking for display: Bond #"+i);
}


                   if ((showBondType == 3) || (showBondType == 5) ||
                       (showBondType == 6) || (showBondType == 7))
                   {
                     mask.set(i);
                     cntSCPpnBonds++;
System.out.println("To be displayed");
                   } // end if statement
else
{
System.out.println("Not to be displayed");
}
                 } // end if statement
                 break;

       } // end switch

     } // end for statement

     return(mask);

  }  // end getSCPBondMask



  public BitSet getFissionPlaneMask()
  {
     BitSet mask = new BitSet(6);

     if (whichFissionPlane < 0) return(mask);
     if ((model != 'S') && (model != 'F')) return(mask);
     if ((model == 'S') && (whichFissionPlane > 8))
     {
        whichFissionPlane = -1;
        return(mask);
     } // end if statement

     mask.set(whichFissionPlane);

     // need to count intersecting bonds.

     cntFissionLeft  = 0;
     cntFissionRight = 0;


     if (theApp.model == 'F')
     {
       cntFissionPP = 0;
       cntFissionNN = 0;
       cntFissionPN = 0;

       for (int i = 0; i < numFCCBonds; i++)
       {
          if ( (fccBonds[i].type == 1) &&
               (theProtons[fccBonds[i].p1].currentlyDisplayed) &&
               (theProtons[fccBonds[i].p2].currentlyDisplayed) &&
                intersectsFP(theProtons[fccBonds[i].p1].fccX,
                             theProtons[fccBonds[i].p1].fccY,
                             theProtons[fccBonds[i].p1].fccZ,
                             theProtons[fccBonds[i].p2].fccX,
                             theProtons[fccBonds[i].p2].fccY,
                             theProtons[fccBonds[i].p2].fccZ)      )
          {
            cntFissionPP++;
          } // end if statement

          if ( (fccBonds[i].type == 2) &&
               (theNeutrons[fccBonds[i].n1].currentlyDisplayed) &&
               (theNeutrons[fccBonds[i].n2].currentlyDisplayed) &&
                intersectsFP(theNeutrons[fccBonds[i].n1].fccX,
                             theNeutrons[fccBonds[i].n1].fccY,
                             theNeutrons[fccBonds[i].n1].fccZ,
                             theNeutrons[fccBonds[i].n2].fccX,
                             theNeutrons[fccBonds[i].n2].fccY,
                             theNeutrons[fccBonds[i].n2].fccZ)       )
          {
            cntFissionNN++;
          } // end if statement

          if ( (fccBonds[i].type == 3) &&
               (theProtons[fccBonds[i].p1].currentlyDisplayed) &&
               (theNeutrons[fccBonds[i].n1].currentlyDisplayed) &&
                intersectsFP(theProtons[fccBonds[i].p1].fccX,
                             theProtons[fccBonds[i].p1].fccY,
                             theProtons[fccBonds[i].p1].fccZ,
                             theNeutrons[fccBonds[i].n1].fccX,
                             theNeutrons[fccBonds[i].n1].fccY,
                             theNeutrons[fccBonds[i].n1].fccZ)      )
          {
            cntFissionPN++;
          } // end if statement



       } // end for statement

     } // end if statement


     // SCP bonds

     if (theApp.model == 'S')
     {
       cntFissionPP = 0;
       cntFissionNN = 0;
       cntFissionPN = 0;

       for (int i = 0; i < numSCPBonds; i++)
       {
          if ( (scpBonds[i].type == 4) &&
               (theProtons[scpBonds[i].p1].currentlyDisplayed) &&
               (theProtons[scpBonds[i].p2].currentlyDisplayed) &&
                intersectsFP(theProtons[scpBonds[i].p1].scpX,
                             theProtons[scpBonds[i].p1].scpY,
                             theProtons[scpBonds[i].p1].scpZ,
                             theProtons[scpBonds[i].p2].scpX,
                             theProtons[scpBonds[i].p2].scpY,
                             theProtons[scpBonds[i].p2].scpZ)      )
          {
            cntFissionPP++;
          } // end if statement

          if ( (scpBonds[i].type == 5) &&
               (theNeutrons[scpBonds[i].n1].currentlyDisplayed) &&
               (theNeutrons[scpBonds[i].n2].currentlyDisplayed) &&
                intersectsFP(theNeutrons[scpBonds[i].n1].scpX,
                             theNeutrons[scpBonds[i].n1].scpY,
                             theNeutrons[scpBonds[i].n1].scpZ,
                             theNeutrons[scpBonds[i].n2].scpX,
                             theNeutrons[scpBonds[i].n2].scpY,
                             theNeutrons[scpBonds[i].n2].scpZ)     )
          {
            cntFissionNN++;
          } // end if statement

          if ( (scpBonds[i].type == 6) &&
               (theProtons[scpBonds[i].p1].currentlyDisplayed) &&
               (theNeutrons[scpBonds[i].n1].currentlyDisplayed) &&
                intersectsFP(theProtons[scpBonds[i].p1].scpX,
                             theProtons[scpBonds[i].p1].scpY,
                             theProtons[scpBonds[i].p1].scpZ,
                             theNeutrons[scpBonds[i].n1].scpX,
                             theNeutrons[scpBonds[i].n1].scpY,
                             theNeutrons[scpBonds[i].n1].scpZ)     )
          {
            cntFissionPN++;
          } // end if statement

       } // end for statement

     } // end if statement

     countLeftRight();

     return(mask);

  }  // end getFissionPlaneMask


  public boolean intersectsFP(float x1, float y1, float z1, float x2, float y2, float z2)
  {

     float fA =  fPlanes[whichFissionPlane].fissionNormA;
     float fB =  fPlanes[whichFissionPlane].fissionNormB;
     float fC =  fPlanes[whichFissionPlane].fissionNormC;
     float fD =  fPlanes[whichFissionPlane].fissionNormD;


     // norm = (A, B, C)  D = norm * (x1, y1, z1)
     // distance from a point (x, y, z) to plane defined by norm and (x1, y1, z1)
     // dist = abs(Ax + By + Cz - D) / sqrt(AA + BB + CC)
     // this is how we tell if fission plane cuts the bond.

     double hld1 = Math.abs(fA * x1 + fB * y1 + fC * z1 - fD);
     hld1 = hld1 / Math.sqrt(fA * fA + fB * fB + fC * fC);

     if (hld1 > 1.5) return (false);

     double hld2 = Math.abs(fA * x2 + fB * y2 + fC * z2 - fD);
     hld2 = hld2 / Math.sqrt(fA * fA + fB * fB + fC * fC);

     if (hld2 > 1.5) return (false);


     // Spheres are at right distance.
     // Check that they are on opposite sides of the fission plane

     // adjust the norm vector length
     double dx = fA * hld1;
     double dy = fB * hld1;
     double dz = fC * hld1;

     // now add norm to position

     double x3 = x1 + dx;
     double y3 = y1 + dy;
     double z3 = z1 + dz;


     double x4 = x2 + dx;
     double y4 = y2 + dy;
     double z4 = z2 + dz;


     // now put this point into equation for the plane

     double hld_d1 = fA * x3 + fB * y3 + fC * z3;
     double hld_d2 = fA * x4 + fB * y4 + fC * z4;


     if ( (Math.abs(fD - hld_d1) < 0.1) && (Math.abs(fD - hld_d2) < 0.1) ) return(false);
     if ( (Math.abs(fD - hld_d1) > 0.1) && (Math.abs(fD - hld_d2) > 0.1) ) return(false);


     return(true);

  } // end intersectsFP


  public void countLeftRight()
  {
     cntFissionLeft  = 0;
     cntFissionRight = 0;

     float fA =  fPlanes[whichFissionPlane].fissionNormA;
     float fB =  fPlanes[whichFissionPlane].fissionNormB;
     float fC =  fPlanes[whichFissionPlane].fissionNormC;
     float fD =  fPlanes[whichFissionPlane].fissionNormD;

     double x1, y1, z1;

    for (int i = 0; i < cntP; i++)
    {
      if (!theProtons[i].currentlyDisplayed) break;

      if (model == 'F')
      {
        x1 = theProtons[i].fccX;
        y1 = theProtons[i].fccY;
        z1 = theProtons[i].fccZ;
      } // end if statement
      else
      {
        x1 = theProtons[i].scpX;
        y1 = theProtons[i].scpY;
        z1 = theProtons[i].scpZ;
      } // end else part


     // norm = (A, B, C)  D = norm * (x1, y1, z1)
     // distance from a point (x, y, z) to plane defined by norm and (x1, y1, z1)
     // dist = abs(Ax + By + Cz - D) / sqrt(AA + BB + CC)
     // this is how we tell if fission plane cuts the bond.

     double hld1 = Math.abs(fA * x1 + fB * y1 + fC * z1 - fD);
     hld1 = hld1 / Math.sqrt(fA * fA + fB * fB + fC * fC);

     // adjust the norm vector length
     double dx = fA * hld1;
     double dy = fB * hld1;
     double dz = fC * hld1;

     // now add norm to position

     double x3 = x1 + dx;
     double y3 = y1 + dy;
     double z3 = z1 + dz;

     // now put this point into equation for the plane

     double hld_d1 = fA * x3 + fB * y3 + fC * z3;

     if (Math.abs(fD - hld_d1) < 0.1)
     {
       cntFissionLeft++;
     }
     else
     {
       cntFissionRight++;
     } // end else part


    } // end for statement


    for (int i = 0; i < cntN; i++)
    {
      if (!theNeutrons[i].currentlyDisplayed) break;

      if (model == 'F')
      {
        x1 = theNeutrons[i].fccX;
        y1 = theNeutrons[i].fccY;
        z1 = theNeutrons[i].fccZ;
      } // end if statement
      else
      {
        x1 = theNeutrons[i].scpX;
        y1 = theNeutrons[i].scpY;
        z1 = theNeutrons[i].scpZ;
      } // end else part


     // norm = (A, B, C)  D = norm * (x1, y1, z1)
     // distance from a point (x, y, z) to plane defined by norm and (x1, y1, z1)
     // dist = abs(Ax + By + Cz - D) / sqrt(AA + BB + CC)
     // this is how we tell if fission plane cuts the bond.

     double hld1 = Math.abs(fA * x1 + fB * y1 + fC * z1 - fD);
     hld1 = hld1 / Math.sqrt(fA * fA + fB * fB + fC * fC);

     // adjust the norm vector length
     double dx = fA * hld1;
     double dy = fB * hld1;
     double dz = fC * hld1;

     // now add norm to position

     double x3 = x1 + dx;
     double y3 = y1 + dy;
     double z3 = z1 + dz;

     // now put this point into equation for the plane

     double hld_d1 = fA * x3 + fB * y3 + fC * z3;

     if (Math.abs(fD - hld_d1) < 0.2)
     {
       cntFissionLeft++;
     }
     else
     {
       cntFissionRight++;
     } // end else part

    } // end for statement


  } // end countLeftRight


  public BitSet getNSpheresMask()
  {
     // QNCOption: 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

     BitSet mask = new BitSet(6);

     if (!showSubstructure) return(mask);
     if (model != 'F') return(mask);
     if (QNCOption != 1) return(mask);

     // only display those structures for which there are
     // spheres displayed.

     // determine max n value displayed

     int maxN = -1;

System.out.println("Counting N");

     for (int i = 0; i < cntP; i++)
     {
       if (!theProtons[i].currentlyDisplayed) break;
       if (theProtons[i].n > maxN) maxN = theProtons[i].n;
     } // end for statement

     for (int i = 0; i < cntN; i++)
     {
       if (!theNeutrons[i].currentlyDisplayed) break;
       if (theNeutrons[i].n > maxN) maxN = theNeutrons[i].n;
     } // end for statement

System.out.println("Max N"+maxN);

     for (int i = 0; i <= maxN; i++)
     {
       mask.set(i);
     } // end for statement

     return(mask);

  } // end getNSpheresMask


  public BitSet getJCylindersMask()
  {
     // QNCOption: 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

     BitSet mask = new BitSet(6);

     if (!showSubstructure) return(mask);
     if (model != 'F') return(mask);
     if (QNCOption != 2) return(mask);

     // only display those structures for which there are
     // spheres displayed.

     int maxJ = 0;
     float[] maxZ = new float[7];

     for (int i = 0; i < cntP; i++)
     {
       if (!theProtons[i].currentlyDisplayed) break;
       if (theProtons[i].j > maxJ) maxJ = theProtons[i].j;
       int hldJ = (int)((float)theProtons[i].j / 2.0f);
       if (Math.abs(theProtons[i].fccZ) > maxZ[hldJ]) maxZ[hldJ] = Math.abs(theProtons[i].fccZ);
     } // end for statement

     for (int i = 0; i < cntN; i++)
     {
       if (!theNeutrons[i].currentlyDisplayed) break;
       if (theNeutrons[i].j > maxJ) maxJ = theNeutrons[i].j;
       int hldJ = (int)((float)theNeutrons[i].j / 2.0f);
       if (Math.abs(theNeutrons[i].fccZ) > maxZ[hldJ]) maxZ[hldJ] = Math.abs(theNeutrons[i].fccZ);
     } // end for statement

     maxJ = (int)((float)maxJ / 2.0f) + 2;

     for (int i = 0; i < maxJ-1; i++)
     {
       mask.set(i);

       MyCylinder myCyl = new MyCylinder(0.0f, 0.0f, -maxZ[i]-1, 0.0f, 0.0f, maxZ[i]+1, jRadius[i]);
       jCylinders[i].setGeometry(myCyl.Solid());

       // The apperance is set and modified as needed in the menu
       // functions to change shell color and transparency.

     } // end for statement

     return(mask);

  } // end getJCylindersMask


  public BitSet getMConesMask()
  {
     // QNCOption: 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

     BitSet mask = new BitSet(6);

     if (!showSubstructure) return(mask);
     if (model != 'F') return(mask);
     if (QNCOption != 3) return(mask);

     // only display those structures for which there are
     // spheres displayed.

     int maxM = -1;
     int hldM = 0;
     float[] r = new float[7];
     float[] height = new float[7];
     float hldR;

     for (int i = 0; i < cntP; i++)
     {
       if (!theProtons[i].currentlyDisplayed) break;
       if (Math.abs(theProtons[i].m) > maxM) maxM = Math.abs(theProtons[i].m);
       hldM = Math.abs(theProtons[i].m) / 2;

       hldR = (float)Math.sqrt(theProtons[i].fccY * theProtons[i].fccY + theProtons[i].fccZ * theProtons[i].fccZ);
       if (hldR > r[hldM]) r[hldM] = hldR;

       if (Math.abs(theProtons[i].fccX) > height[hldM]) height[hldM] = Math.abs(theProtons[i].fccX);
     } // end for statement

     for (int i = 0; i < cntN; i++)
     {
       if (!theNeutrons[i].currentlyDisplayed) break;
       if (Math.abs(theNeutrons[i].m) > maxM) maxM = Math.abs(theNeutrons[i].m);
       hldM = Math.abs(theProtons[i].m) / 2;

       hldR = (float)Math.sqrt(theProtons[i].fccY * theProtons[i].fccY + theProtons[i].fccZ * theProtons[i].fccZ);
       if (hldR > r[hldM]) r[hldM] = hldR;

       if (Math.abs(theProtons[i].fccX) > height[hldM]) height[hldM] = Math.abs(theProtons[i].fccX);

     } // end for statement


     for (int i = 0; i < maxM; i++)
     {
       mask.set(i);

       MyCone myCone = new MyCone(height[i], r[i]);
       mCones[i].setGeometry(myCone.Solid());

       // The apperance is set and modified as needed in the menu
       // functions to change shell color and transparency.

     } // end for statement

     return(mask);

  } // end getMConesMask


  public BitSet getSCirclesMask()
  {
     // QNCOption: 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

     BitSet mask = new BitSet(6);

     if (!showSubstructure) return(mask);
     if (model != 'F') return(mask);
     if (QNCOption != 5) return(mask);

     // only display those structures for which there are
     // spheres displayed.

     int maxPos = -1;
     int hldPos = 0;
     float[] r = new float[7];
     float[] height = new float[7];
     float hldR;

     for (int i = 0; i < cntP; i++)
     {
       if (!theProtons[i].currentlyDisplayed) break;
       if (Math.abs(theProtons[i].fccX) > maxPos) maxPos = (int)Math.abs(theProtons[i].fccX);
System.out.println("X value: "+(int)Math.abs(theProtons[i].fccX));
       hldPos = (int)Math.abs(theProtons[i].fccX) / 2;

       hldR = (float)Math.sqrt(theProtons[i].fccY * theProtons[i].fccY + theProtons[i].fccZ * theProtons[i].fccZ);
       if (hldR > r[hldPos]) r[hldPos] = hldR;

       if (Math.abs(theProtons[i].fccX) > height[hldPos]) height[hldPos] = Math.abs(theProtons[i].fccX);

     } // end for statement


     for (int i = 0; i < cntN; i++)
     {
       if (!theNeutrons[i].currentlyDisplayed) break;
       if (Math.abs(theNeutrons[i].fccX) > maxPos) maxPos = (int)Math.abs(theNeutrons[i].fccX);
System.out.println("X value: "+(int)Math.abs(theNeutrons[i].fccX));
       hldPos = (int)Math.abs(theNeutrons[i].fccX) / 2;

       hldR = (float)Math.sqrt(theNeutrons[i].fccY * theNeutrons[i].fccY + theNeutrons[i].fccZ * theNeutrons[i].fccZ);
       if (hldR > r[hldPos]) r[hldPos] = hldR;

       if (Math.abs(theNeutrons[i].fccX) > height[hldPos]) height[hldPos] = Math.abs(theNeutrons[i].fccX);

     } // end for statement


     for (int i = 1; i <= maxPos; i=i+2)
     {
       int index = i / 2;

       mask.set(index);

       MyCircle myCircle = new MyCircle('X', i, r[index]);
       sCircles[index].setGeometry(myCircle.Solid());

       // The apperance is set and modified as needed in the menu
       // functions to change shell color and transparency.

     } // end for statement

     return(mask);

  } // end getSCirclesMask



  public BitSet getICirclesMask()
  {
     // QNCOption: 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

     BitSet mask = new BitSet(6);

     if (!showSubstructure) return(mask);
     if (model != 'F') return(mask);
     if (QNCOption != 6) return(mask);

     // only display those structures for which there are
     // spheres displayed.


     // only display those structures for which there are
     // spheres displayed.

     int maxPos = -1;
     int hldPos = 0;
     float[] r = new float[7];
     float[] height = new float[7];
     float hldR;

     for (int i = 0; i < cntP; i++)
     {
       if (!theProtons[i].currentlyDisplayed) break;
       if (Math.abs(theProtons[i].fccZ) > maxPos) maxPos = (int)Math.abs(theProtons[i].fccZ);
System.out.println("Z value: "+(int)Math.abs(theProtons[i].fccZ));
       hldPos = (int)Math.abs(theProtons[i].fccZ) / 2;

       hldR = (float)Math.sqrt(theProtons[i].fccX * theProtons[i].fccX + theProtons[i].fccY * theProtons[i].fccY);
       if (hldR > r[hldPos]) r[hldPos] = hldR;

       if (Math.abs(theProtons[i].fccZ) > height[hldPos]) height[hldPos] = Math.abs(theProtons[i].fccZ);

     } // end for statement


     for (int i = 0; i < cntN; i++)
     {
       if (!theNeutrons[i].currentlyDisplayed) break;
       if (Math.abs(theNeutrons[i].fccZ) > maxPos) maxPos = (int)Math.abs(theNeutrons[i].fccZ);
System.out.println("Z value: "+(int)Math.abs(theNeutrons[i].fccZ));
       hldPos = (int)Math.abs(theNeutrons[i].fccZ) / 2;

       hldR = (float)Math.sqrt(theNeutrons[i].fccX * theNeutrons[i].fccX + theNeutrons[i].fccY * theNeutrons[i].fccY);
       if (hldR > r[hldPos]) r[hldPos] = hldR;

       if (Math.abs(theNeutrons[i].fccZ) > height[hldPos]) height[hldPos] = Math.abs(theNeutrons[i].fccZ);

     } // end for statement


     for (int i = 1; i <= maxPos; i=i+2)
     {
       int index = i / 2;

       mask.set(index);

       MyCircle myCircle = new MyCircle('Z', i, r[index]);
       iCircles[index].setGeometry(myCircle.Solid());

       // The apperance is set and modified as needed in the menu
       // functions to change shell color and transparency.

     } // end for statement

     return(mask);

  } // end getICirclesMask



  public BitSet getAlphaMask()
  {
     BitSet mask = new BitSet(6);

     if (model != 'A') return(mask);

     // only display those structures for which there are
     // spheres displayed.

     mask.set(0);
     mask.set(1);

     return(mask);

  } // end getAlphaMask


} // end class NDCP






