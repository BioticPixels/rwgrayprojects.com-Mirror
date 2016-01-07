package ndcp1;

//
// DisplayFrame for nuclear lattice model viewer
//
// Written by: R. W. Gray
// Date:       02-23-1999
// Version:    0.01
//
//


import java.awt.*;         // get window tools
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;


import javax.media.j3d.*;
import javax.vecmath.*;

import java.util.*;


public class NDCPMenu extends JMenuBar
                      implements  ActionListener, Constants
{

  ndcp theApp;                       // the application object

  private Transform3D movement = new Transform3D();
  private Vector3f loc = new Vector3f(0.0f, 0.0f, 0.0f);

  Transform3D T3DScale = new Transform3D();

  int whichXYZPlanes;  // 0=none, 1=XY, 2=YZ, 3=XZ, 4=XY&YZ, 5=XY&XZ, 6=YZ&XZ, 7=all

  // Constructor

  public NDCPMenu(ndcp theApp)
  {

    this.theApp = theApp;   // Store the application object

    // construct the FILE pull down menu

    JMenu fileMenu = new JMenu("File");
    JMenuItem item;

    Font itemFont = new Font("Courier", Font.BOLD, 14);

    fileMenu.add("New");
    fileMenu.add("Open");
    fileMenu.add(item = new JMenuItem("Close"));
    item.setFont(itemFont);
    item.addActionListener(this);
    fileMenu.addSeparator();
    fileMenu.add(item = new JMenuItem("Save"));
    item.setFont(itemFont);
    item.addActionListener(this);
    fileMenu.add(item = new JMenuItem("Save As..."));
    item.setFont(itemFont);
    item.addActionListener(this);
    fileMenu.addSeparator();
    fileMenu.add(item = new JMenuItem("Close"));
    item.setFont(itemFont);
    item.addActionListener(this);

    // construct the MODE pull down menu

    JMenu modeMenu = new JMenu("Mode");

    String[] modeItems = {  "Gas (IPM)            G",
                            "Liquid (LDM)         L",
                            "Solid (FCC and SCP)  S+",
                            "Alpha Clusters       A",
                            "Bosons (IBM)         B",
                            "Quarks               Q"
                         };

    modeMenu.add(item = new JMenuItem(modeItems[0]));
    item.setFont(itemFont);
    item.setActionCommand("G");
    item.addActionListener(this);

    modeMenu.add(item = new JMenuItem(modeItems[1]));
    item.setFont(itemFont);
    item.setActionCommand("L");
    item.addActionListener(this);

    modeMenu.add(item = new JMenuItem(modeItems[2]));
    item.setFont(itemFont);
    item.setActionCommand("S");
    item.addActionListener(this);

    modeMenu.addSeparator();

    modeMenu.add(item = new JMenuItem(modeItems[3]));
    item.setFont(itemFont);
    item.setActionCommand("A");
    item.addActionListener(this);

    modeMenu.add(item = new JMenuItem(modeItems[4]));
    item.setFont(itemFont);
    item.setActionCommand("B");
    item.addActionListener(this);

    modeMenu.add(item = new JMenuItem(modeItems[5]));
    item.setFont(itemFont);
    item.setActionCommand("Q");
    item.addActionListener(this);


    // construct the Nucleons pull down menu

    JMenu buildupMenu = new JMenu("Build-up");

    String[] buildupItems = { "Subtract Proton    F1+",
                              "Add Proton         F2+",
                              "Subtract Neutron   F3+",
                              "Add Neutron        F4+",
                              "Subtract Shell     F5+",
                              "Add Shell          F6+"
                            };

    buildupMenu.add(item = new JMenuItem(buildupItems[0]));
    item.setFont(itemFont);
    item.setActionCommand("F1");
    item.addActionListener(this);

    buildupMenu.add(item = new JMenuItem(buildupItems[1]));
    item.setFont(itemFont);
    item.setActionCommand("F2");
    item.addActionListener(this);

    buildupMenu.addSeparator();

    buildupMenu.add(item = new JMenuItem(buildupItems[2]));
    item.setFont(itemFont);
    item.setActionCommand("F3");
    item.addActionListener(this);

    buildupMenu.add(item = new JMenuItem(buildupItems[3]));
    item.setFont(itemFont);
    item.setActionCommand("F4");
    item.addActionListener(this);

    buildupMenu.addSeparator();

    buildupMenu.add(item = new JMenuItem(buildupItems[4]));
    item.setFont(itemFont);
    item.setActionCommand("F5");
    item.addActionListener(this);

    buildupMenu.add(item = new JMenuItem(buildupItems[5]));
    item.setFont(itemFont);
    item.setActionCommand("F6");
    item.addActionListener(this);

//    buildupMenu.addSeparator();

    // construct the Bonds pull down menu

    JMenu bondsMenu  = new JMenu("Bonds");

    String[] bondsItems = { "Bonds Toggle                b+",
                            "Variable Thickness Bonds    v",
                            "Thinner Bonds              F7+",
                            "Thicker Bonds              F8+"
                          };

    bondsMenu.add(item = new JMenuItem(bondsItems[0]));
    item.setFont(itemFont);
    item.setActionCommand("b");
    item.addActionListener(this);

    bondsMenu.add(item = new JMenuItem(bondsItems[1]));
    item.setFont(itemFont);
    item.setActionCommand("v");
    item.addActionListener(this);

    bondsMenu.add(item = new JMenuItem(bondsItems[2]));
    item.setFont(itemFont);
    item.setActionCommand("F7");
    item.addActionListener(this);

    bondsMenu.add(item = new JMenuItem(bondsItems[3]));
    item.setFont(itemFont);
    item.setActionCommand("F8");
    item.addActionListener(this);


    // construct the Spheres pull down menu

    JMenu spheresMenu = new JMenu("Spheres");

    String[] spheresItems = { "Decrease Nucleon Radius  F9+",
                              "Increase Nucleon Radius  F10+"
                            };

    spheresMenu.add(item = new JMenuItem(spheresItems[0]));
    item.setFont(itemFont);
    item.setActionCommand("F9");
    item.addActionListener(this);

    spheresMenu.add(item = new JMenuItem(spheresItems[1]));
    item.setFont(itemFont);
    item.setActionCommand("F10");
    item.addActionListener(this);


    // construct the Action pull down menu

    JMenu actionMenu = new JMenu("Action");

    String[] actionItems = { "Occupancy Toggle    O+",
                             "   60% occupancy",
                             "   70% occupancy",
                             "   80% occupancy",
                             "   90% occupancy",
                             "Collective Motion   C+",
                             "   z-axis only",
                             "   xy-axes",
                             "   xyz-axes",
                             "   proton-neutron"
                         };

    actionMenu.add(item = new JMenuItem(actionItems[0]));
    item.setFont(itemFont);
    item.setActionCommand("O");
    item.addActionListener(this);

    actionMenu.add(item = new JMenuItem(actionItems[1]));
    item.setFont(itemFont);
    item.setActionCommand("O60");
    item.addActionListener(this);

    actionMenu.add(item = new JMenuItem(actionItems[2]));
    item.setFont(itemFont);
    item.setActionCommand("O70");
    item.addActionListener(this);

    actionMenu.add(item = new JMenuItem(actionItems[3]));
    item.setFont(itemFont);
    item.setActionCommand("O80");
    item.addActionListener(this);

    actionMenu.add(item = new JMenuItem(actionItems[4]));
    item.setFont(itemFont);
    item.setActionCommand("O90");
    item.addActionListener(this);

    actionMenu.addSeparator();

    actionMenu.add(item = new JMenuItem(actionItems[5]));
    item.setFont(itemFont);
    item.setActionCommand("C");
    item.addActionListener(this);

    actionMenu.add(item = new JMenuItem(actionItems[6]));
    item.setFont(itemFont);
    item.setActionCommand("CZ");
    item.addActionListener(this);

    actionMenu.add(item = new JMenuItem(actionItems[7]));
    item.setFont(itemFont);
    item.setActionCommand("CXY");
    item.addActionListener(this);

    actionMenu.add(item = new JMenuItem(actionItems[8]));
    item.setFont(itemFont);
    item.setActionCommand("CXYZ");
    item.addActionListener(this);

    actionMenu.add(item = new JMenuItem(actionItems[9]));
    item.setFont(itemFont);
    item.setActionCommand("CPN");
    item.addActionListener(this);

    // construct the Quantum pull down menu

    JMenu quantumMenu = new JMenu("Quantum");

    String[] quantumItems = { "Principal    n",
                              "Total Spin   j",
                              "Azimuthal    m",
                              "Orbital      l",
                              "Spin         s",
                              "Isospin      i"
                            };

    quantumMenu.add(item = new JMenuItem(quantumItems[0]));
    item.setFont(itemFont);
    item.setActionCommand("n");
    item.addActionListener(this);

    quantumMenu.add(item = new JMenuItem(quantumItems[1]));
    item.setFont(itemFont);
    item.setActionCommand("j");
    item.addActionListener(this);

    quantumMenu.add(item = new JMenuItem(quantumItems[2]));
    item.setFont(itemFont);
    item.setActionCommand("m");
    item.addActionListener(this);

    quantumMenu.add(item = new JMenuItem(quantumItems[3]));
    item.setFont(itemFont);
    item.setActionCommand("l");
    item.addActionListener(this);

    quantumMenu.add(item = new JMenuItem(quantumItems[4]));
    item.setFont(itemFont);
    item.setActionCommand("s");
    item.addActionListener(this);

    quantumMenu.add(item = new JMenuItem(quantumItems[5]));
    item.setFont(itemFont);
    item.setActionCommand("i");
    item.addActionListener(this);

    // construct the Shells pull down menu

    JMenu shellsMenu = new JMenu("Shells");
    String[] shellsItems = {"1   shell 1",
                            "2   shell 2",
                            "3   shell 3",
                            "4   shell 4",
                            "5   shell 5",
                            "6   shell 6",
                            "7   shell 7",
                            "8   all shells"
                           };

    shellsMenu.add(item = new JMenuItem(shellsItems[0]));
    item.setFont(itemFont);
    item.setActionCommand("1");
    item.addActionListener(this);

    shellsMenu.add(item = new JMenuItem(shellsItems[1]));
    item.setFont(itemFont);
    item.setActionCommand("2");
    item.addActionListener(this);

    shellsMenu.add(item = new JMenuItem(shellsItems[2]));
    item.setFont(itemFont);
    item.setActionCommand("3");
    item.addActionListener(this);

    shellsMenu.add(item = new JMenuItem(shellsItems[3]));
    item.setFont(itemFont);
    item.setActionCommand("4");
    item.addActionListener(this);

    shellsMenu.add(item = new JMenuItem(shellsItems[4]));
    item.setFont(itemFont);
    item.setActionCommand("5");
    item.addActionListener(this);

    shellsMenu.add(item = new JMenuItem(shellsItems[5]));
    item.setFont(itemFont);
    item.setActionCommand("6");
    item.addActionListener(this);

    shellsMenu.add(item = new JMenuItem(shellsItems[6]));
    item.setFont(itemFont);
    item.setActionCommand("7");
    item.addActionListener(this);

    shellsMenu.add(item = new JMenuItem(shellsItems[7]));
    item.setFont(itemFont);
    item.setActionCommand("8");
    item.addActionListener(this);


    // construct the Rotate pull down menu

    JMenu rotateMenu = new JMenu("Rotate");

    String[] rotateItems = {"Nuclear Revolution Toggle    page  down",
                            "   faster                    right arrow",
                            "   slower                    left  arrow",
                            "   around X-axis      X",
                            "   around Y-axis      Y",
                            "   around Z-axis      Z"
                           };

    rotateMenu.add(item = new JMenuItem(rotateItems[0]));
    item.setFont(itemFont);
    item.setActionCommand("Page Down");
    item.addActionListener(this);

    rotateMenu.add(item = new JMenuItem(rotateItems[1]));
    item.setFont(itemFont);
    item.setActionCommand("Right");
    item.addActionListener(this);

    rotateMenu.add(item = new JMenuItem(rotateItems[2]));
    item.setFont(itemFont);
    item.setActionCommand("Left");
    item.addActionListener(this);

    rotateMenu.add(item = new JMenuItem(rotateItems[3]));
    item.setFont(itemFont);
    item.setActionCommand("X");
    item.addActionListener(this);

    rotateMenu.add(item = new JMenuItem(rotateItems[4]));
    item.setFont(itemFont);
    item.setActionCommand("Y");
    item.addActionListener(this);

    rotateMenu.add(item = new JMenuItem(rotateItems[5]));
    item.setFont(itemFont);
    item.setActionCommand("Z");
    item.addActionListener(this);


    // construct the Window pull down menu

    JMenu windowMenu = new JMenu("Window");

    // construct the Axes pull down menu

    JMenu axesMenu = new JMenu("Axes");

    String[] axesItems = {"Toggle all axes  a+",
                          "Toggle x-axis    x+",
                          "Toggle y-axis    y+",
                          "Toggle z-axis    z+",
                          "Shorten axes     k+",
                          "Lengthen axes    K+",
                          "Toggle planes    H+"
                         };

    axesMenu.add(item = new JMenuItem(axesItems[0]));
    item.setFont(itemFont);
    item.setActionCommand("a");
    item.addActionListener(this);

    axesMenu.add(item = new JMenuItem(axesItems[1]));
    item.setFont(itemFont);
    item.setActionCommand("x");
    item.addActionListener(this);

    axesMenu.add(item = new JMenuItem(axesItems[2]));
    item.setFont(itemFont);
    item.setActionCommand("y");
    item.addActionListener(this);

    axesMenu.add(item = new JMenuItem(axesItems[3]));
    item.setFont(itemFont);
    item.setActionCommand("z");
    item.addActionListener(this);

    axesMenu.add(item = new JMenuItem(axesItems[4]));
    item.setFont(itemFont);
    item.setActionCommand("k");
    item.addActionListener(this);

    axesMenu.add(item = new JMenuItem(axesItems[5]));
    item.setFont(itemFont);
    item.setActionCommand("K");
    item.addActionListener(this);

    axesMenu.add(item = new JMenuItem(axesItems[6]));
    item.setFont(itemFont);
    item.setActionCommand("H");
    item.addActionListener(this);

    // construct the Demos pull down menu

    JMenu demosMenu = new JMenu("Demos");

    // construct the Miscellaneous pull down menu

    JMenu miscellaneousMenu = new JMenu("Miscellaneous");

    String[] miscItems = {"Text Toggle                  t+",
                          "Text Color                   Ctrl-t",
                          "Background Color             Ctrl-b",
                          "Lighten Background           `",
                          "Darken Background            ~",
                          "Fission Plane Color          Ctrl-f",
                          "Fission Plane Transparency   Atl-f",
                          "Shell Order Colors           Ctrl-s",
                          "Shell Transparency           Alt-s"
                         };

    miscellaneousMenu.add(item = new JMenuItem(miscItems[0]));
    item.setFont(itemFont);
    item.setActionCommand("t");
    item.addActionListener(this);

    miscellaneousMenu.add(item = new JMenuItem(miscItems[1]));
    item.setFont(itemFont);
    item.setActionCommand("TC");
    item.addActionListener(this);

    miscellaneousMenu.addSeparator();

    miscellaneousMenu.add(item = new JMenuItem(miscItems[2]));
    item.setFont(itemFont);
    item.setActionCommand("BC");
    item.addActionListener(this);

    miscellaneousMenu.add(item = new JMenuItem(miscItems[3]));
    item.setFont(itemFont);
    item.setActionCommand("BQ");   // "Back Quote"
    item.addActionListener(this);

    miscellaneousMenu.add(item = new JMenuItem(miscItems[4]));
    item.setFont(itemFont);
    item.setActionCommand("SBQ");  // "Shift Back Quote"
    item.addActionListener(this);

    miscellaneousMenu.addSeparator();

    miscellaneousMenu.add(item = new JMenuItem(miscItems[5]));
    item.setFont(itemFont);
    item.setActionCommand("FPC");
    item.addActionListener(this);

    miscellaneousMenu.add(item = new JMenuItem(miscItems[6]));
    item.setFont(itemFont);
    item.setActionCommand("FPT");
    item.addActionListener(this);

    miscellaneousMenu.addSeparator();

    miscellaneousMenu.add(item = new JMenuItem(miscItems[7]));
    item.setFont(itemFont);
    item.setActionCommand("SC");
    item.addActionListener(this);

    miscellaneousMenu.add(item = new JMenuItem(miscItems[8]));
    item.setFont(itemFont);
    item.setActionCommand("ST");
    item.addActionListener(this);

    // add the menus to the menu bar in order

    add(fileMenu);
    add(modeMenu);
    add(buildupMenu);
    add(bondsMenu);
    add(spheresMenu);
    add(actionMenu);
    add(quantumMenu);
    add(shellsMenu);
    add(rotateMenu);
    add(windowMenu);
    add(axesMenu);
    add(demosMenu);
    add(miscellaneousMenu);

  } // end constructor: NDCPmENeNU(String)


  public void actionPerformed(ActionEvent e)
  {
    String actionName = e.getActionCommand();
    String modifier = new String("");

    if (actionName.equals("G") || actionName.equals("L") ||
        actionName.equals("S") || actionName.equals("A") ||
        actionName.equals("B") || actionName.equals("Q") ||
        actionName.equals("F") || actionName.equals("M") ||
        actionName.equals("X") || actionName.equals("Y") ||
        actionName.equals("Z") || actionName.equals("K") ||
        actionName.equals("H") || actionName.equals("N") ||
        actionName.equals("D") || actionName.equals("R")    )
      modifier = new String("Shift");

    if (actionName.equals("t")) actionName = "T";

    if (actionName.equals("n")) actionName = "N";
    if (actionName.equals("j")) actionName = "J";
    if (actionName.equals("l")) actionName = "L";
    if (actionName.equals("m")) actionName = "M";
    if (actionName.equals("s")) actionName = "S";
    if (actionName.equals("i")) actionName = "I";

    if (actionName.equals("a")) actionName = "A";
    if (actionName.equals("k")) actionName = "K";

    if (actionName.equals("x")) actionName = "X";
    if (actionName.equals("y")) actionName = "Y";
    if (actionName.equals("z")) actionName = "Z";

    // menu items for color changes

    if (actionName.equals("BC"))
    {
      actionName = "B";
      modifier = "Ctrl";
    } // end if statement


   if (actionName.equals("BQ"))
    {
      actionName = "Back Quote";
      modifier = "";
    } // end if statement

   if (actionName.equals("SBQ"))
    {
      actionName = "Back Quote";
      modifier = "Shift";
    } // end if statement

    if (actionName.equals("SC"))
    {
      actionName = "S";
      modifier = "Ctrl";
    } // end if statement

    if (actionName.equals("ST"))
    {
      actionName = "S";
      modifier = "Alt";
    } // end if statement


    if (actionName.equals("FPC"))
    {
      actionName = "F";
      modifier = "Ctrl";
    } // end if statement

    if (actionName.equals("FPT"))
    {
      actionName = "F";
      modifier = "Alt";
    } // end if statement


    changeOption(modifier, actionName);

  } // end actionPerformed


  // Called from actionPerformed and SimpleBehaviorZ
  public void changeOption(String modifier, String text)
  {

System.out.println("changeModifier called: M="+modifier+" T="+text);


//    if (modifier.equals("Shift") && text.equals("Shift")) return;
//    if (modifier.equals("Ctrl") && text.equals("Ctrl")) return;
//    if (modifier.equals("Alt") && text.equals("Alt")) return;

//System.out.println("changeModifier called: M="+modifier+" T="+text);

    // Adjust color and transparency commands



    if (text.equals("T") && modifier.equals("Ctrl"))
    {
      doTextColor();

      theApp.groupProtons.setWhichChild(Switch.CHILD_MASK);
      theApp.groupProtons.setChildMask(theApp.getProtonMask());

      return;
    } // end if statement

    if (text.equals("B") && modifier.equals("Ctrl"))
    {
      doBackgroundColor();
      return;
    } // end if statement

    if (text.equals("Back Quote") && !modifier.equals("Shift"))
    {
      // lighten background

      theApp.BGCr += 0.1;
      if (theApp.BGCr > 1.0f) theApp.BGCr = 1.0f;

      theApp.BGCg += 0.1;
      if (theApp.BGCg > 1.0f) theApp.BGCg = 1.0f;

      theApp.BGCb += 0.1;
      if (theApp.BGCb > 1.0f) theApp.BGCb = 1.0f;


      theApp.BG.setColor(theApp.BGCr, theApp.BGCg, theApp.BGCb);

      return;

    } // end if statement


    if (text.equals("Back Quote") && modifier.equals("Shift"))
    {
      // darken background
      theApp.BGCr -= 0.1;
      if (theApp.BGCr < 0.0f) theApp.BGCr = 0.0f;

      theApp.BGCg -= 0.1;
      if (theApp.BGCg < 0.0f) theApp.BGCg = 0.0f;

      theApp.BGCb -= 0.1;
      if (theApp.BGCb < 0.0f) theApp.BGCb = 0.0f;

      theApp.BG.setColor(theApp.BGCr, theApp.BGCg, theApp.BGCb);

      return;

    } // end if statement


    if (text.equals("S") && modifier.equals("Ctrl"))
    {
      doShellColor();
      return;
    } // end if statement

    if (text.equals("S") && modifier.equals("Alt"))
    {
      doShellTrans();
      return;
    } // end if statement

    if (text.equals("F") && modifier.equals("Ctrl"))
    {
      doFissionPlaneColor();
      return;
    } // end if statement

    if (text.equals("F") && modifier.equals("Alt"))
    {
      doFissionPlaneTrans();
      return;
    } // end if statement


    ////////////////////

    if (text.equals("Page Down"))
    {
      // Toggle self rotation

      theApp.revolveOn = !theApp.revolveOn;

      if (theApp.revolveOn)
      {
        theApp.myAutoRotBehavior.setEnable(true);
      }
      else
      {
        theApp.myAutoRotBehavior.setEnable(false);
      }

      return;

    } // end "Page Down"

    if (text.equals("Left"))
    {
      // decrease revolution speed
      theApp.revolveSpeed *= 0.90;  // decrease by 10 percent

      return;
    } // end "Left"

    if (text.equals("Right"))
    {
      // Increase revolution speed
      theApp.revolveSpeed *= 1.10;  // increase by 10 percent

      return;
    } // end "Right"



    if ( (text.equals("F")) && (modifier.equals("Shift")))
    {
      // Display fission planes

      theApp.whichFissionPlane++;
      if (theApp.whichFissionPlane > 20) theApp.whichFissionPlane = -1;

      theApp.groupFissionPlanes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupFissionPlanes.setChildMask(theApp.getFissionPlaneMask());

      return;
    } // end if F


    if ( (text.equals("G")) && (modifier.equals("Shift")))
    {
      doG();
      return;
    } // end if G

    if ((text.equals("S"))  && (modifier.equals("Shift")) )
    {
      doS();
      return;
    } // end if S


    if ((text.equals("A"))  && (modifier.equals("Shift")) )
    {
      // Alpha Clusters
      doA();
      return;
    } // end if S



    if (text.equals("B") && (!modifier.equals("Shift")))
    {
      // Toggle bonds

      // showBondType: 0=none, 1=PP, 2=NN, 3=PN, 4=PP&NN, 5=PP&PN, 6=NN&PN, 7=all

      theApp.showBondType++;
      if (theApp.showBondType > 7) theApp.showBondType = 0;

      theApp.groupFCCBonds.setWhichChild(Switch.CHILD_MASK);
      theApp.groupFCCBonds.setChildMask(theApp.getFCCBondMask());
      theApp.groupSCPBonds.setWhichChild(Switch.CHILD_MASK);
      theApp.groupSCPBonds.setChildMask(theApp.getSCPBondMask());

      return;
    } // end if b


    if (text.equals("T") && (!modifier.equals("Shift")))
    {
      // Switch text status display
      theApp.DisplayStatus = !theApp.DisplayStatus;

      theApp.groupProtons.setWhichChild(Switch.CHILD_MASK);
      theApp.groupProtons.setChildMask(theApp.getProtonMask());
//      theApp.groupNeutrons.setWhichChild(Switch.CHILD_MASK);
//      theApp.groupNeutrons.setChildMask(theApp.getNeutronMask());

      return;
    } // end if T


    if (text.equals("A") && (!modifier.equals("Shift")))
    {
      // Toggle all axes

      if (theApp.xOn && theApp.yOn && theApp.zOn)
      {
        theApp.xOn = false;
        theApp.yOn = false;
        theApp.zOn = false;
      }
      else
      {
        theApp.xOn = true;
        theApp.yOn = true;
        theApp.zOn = true;
      }

      theApp.groupAxes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupAxes.setChildMask(theApp.getAxesMask());

      return;
    } // end if a

    if (text.equals("X") && (!modifier.equals("Shift")))
    {
      // Display x-axis
      theApp.xOn = !theApp.xOn;

      theApp.groupAxes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupAxes.setChildMask(theApp.getAxesMask());

      return;
    } // end if x

    if (text.equals("Y") && (!modifier.equals("Shift")))
    {
      // Display y-axis
      theApp.yOn = !theApp.yOn;

      theApp.groupAxes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupAxes.setChildMask(theApp.getAxesMask());

      return;
    } // end if y

    if (text.equals("Z") && (!modifier.equals("Shift")))
    {
      // Display z-axis
      theApp.zOn = !theApp.zOn;

      theApp.groupAxes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupAxes.setChildMask(theApp.getAxesMask());

      return;
    } // end if z

    if (text.equals("K") && (!modifier.equals("Shift")))
    {
      // Shorten axes
      theApp.axisLength -= 1.0f;  // Reduce by 1
      if (theApp.axisLength < 1.0f) theApp.axisLength = 1.0f;

      MyAxes anAxis = new MyAxes(theApp, 'X');
      theApp.theAxes[0].setGeometry(anAxis.Line());

      anAxis = new MyAxes(theApp, 'Y');
      theApp.theAxes[1].setGeometry(anAxis.Line());

      anAxis = new MyAxes(theApp, 'Z');
      theApp.theAxes[2].setGeometry(anAxis.Line());

      // change XYZ plane dimensions

      MyXYZPlane aPlane = new MyXYZPlane(theApp, "XY");
      theApp.theAxes[3].setGeometry(aPlane.Solid());

      aPlane = new MyXYZPlane(theApp, "YZ");
      theApp.theAxes[4].setGeometry(aPlane.Solid());

      aPlane = new MyXYZPlane(theApp, "XZ");
      theApp.theAxes[5].setGeometry(aPlane.Solid());


      // Adjust Fission plane sizes

      for (int i = 0; i < 21; i++)
      {

        theApp.theFissionPlanes[i].setGeometry(theApp.fPlanes[i].Solid());

      } // end for statement

      //


      theApp.groupAxes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupAxes.setChildMask(theApp.getAxesMask());

      return;
    } // end if k

    if (text.equals("K"))
    {
      // Lengthen axes
      theApp.axisLength += 1.0f;  // increase by 1

      MyAxes anAxis = new MyAxes(theApp, 'X');
      theApp.theAxes[0].setGeometry(anAxis.Line());

      anAxis = new MyAxes(theApp, 'Y');
      theApp.theAxes[1].setGeometry(anAxis.Line());

      anAxis = new MyAxes(theApp, 'Z');
      theApp.theAxes[2].setGeometry(anAxis.Line());

      // change XYZ plane dimensions

      MyXYZPlane aPlane = new MyXYZPlane(theApp, "XY");
      theApp.theAxes[3].setGeometry(aPlane.Solid());

      aPlane = new MyXYZPlane(theApp, "YZ");
      theApp.theAxes[4].setGeometry(aPlane.Solid());

      aPlane = new MyXYZPlane(theApp, "XZ");
      theApp.theAxes[5].setGeometry(aPlane.Solid());


      // Adjust Fission plane sizes

      for (int i = 0; i < 21; i++)
      {

        theApp.theFissionPlanes[i].setGeometry(theApp.fPlanes[i].Solid());

      } // end for statement

      //

      theApp.groupAxes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupAxes.setChildMask(theApp.getAxesMask());

      return;
    } // end if K


    if (text.equals("H"))
    {
      // display XY, XZ, YZ planes

      whichXYZPlanes++;  // 0=none, 1=XY, 2=YZ, 3=XZ, 4=XY&YZ, 5=XY&XZ, 6=YZ&XZ, 7=all
      if (whichXYZPlanes > 7) whichXYZPlanes = 0;

      switch (whichXYZPlanes)
      {
        case 0: theApp.xyOn = false;
                theApp.yzOn = false;
                theApp.xzOn = false;
                break;
        case 1: theApp.xyOn = true;
                theApp.yzOn = false;
                theApp.xzOn = false;
                break;
        case 2: theApp.xyOn = false;
                theApp.yzOn = true;
                theApp.xzOn = false;
                break;
        case 3: theApp.xyOn = false;
                theApp.yzOn = false;
                theApp.xzOn = true;
                break;
        case 4: theApp.xyOn = true;
                theApp.yzOn = true;
                theApp.xzOn = false;
                break;
        case 5: theApp.xyOn = true;
                theApp.yzOn = false;
                theApp.xzOn = true;
                break;
        case 6: theApp.xyOn = false;
                theApp.yzOn = true;
                theApp.xzOn = true;
                break;
        case 7: theApp.xyOn = true;
                theApp.yzOn = true;
                theApp.xzOn = true;
                break;

      } // end switch

      theApp.groupAxes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupAxes.setChildMask(theApp.getAxesMask());

      return;
    } // end if H


    if (text.equals("N") && (!modifier.equals("Shift")))
    {
      // n quantum number

      // Display chnage sphere color
      // QNCOption = 6;  // 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

      theApp.QNCOption = 1;

      // set sphere colors

      changeSphereColors();

      theApp.groupAxes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupAxes.setChildMask(theApp.getAxesMask());

      return;
    } // end if n

    if (text.equals("J") && (!modifier.equals("Shift")))
    {
      // j quantum number

      // Display change sphere color
      // QNCOption = 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

      theApp.QNCOption = 2;

      // set sphere colors

      changeSphereColors();

      theApp.groupAxes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupAxes.setChildMask(theApp.getAxesMask());

      return;
    } // end if j


    if (text.equals("M") && (!modifier.equals("Shift")))
    {
      // m quantum number

      // Display change sphere color
      // QNCOption = 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

      theApp.QNCOption = 3;

      // set sphere colors

      changeSphereColors();

      theApp.groupAxes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupAxes.setChildMask(theApp.getAxesMask());

      return;
    } // end if m



    if (text.equals("S") && (!modifier.equals("Shift")))
    {
      // s quantum number

      // Display change sphere color
      // QNCOption = 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

      theApp.QNCOption = 5;

      // set sphere colors

      changeSphereColors();

      theApp.groupAxes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupAxes.setChildMask(theApp.getAxesMask());

      return;
    } // end if m



    if (text.equals("I") && (!modifier.equals("Shift")))
    {
      // i quantum number

      // Display change sphere color
      // QNCOption = 6;  // 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

      theApp.QNCOption = 6;

      // set proton colors

      for (int i = 0; i < theApp.cntP; i++)
      {

        Appearance PA = theApp.theProtons[i].getAppearance();
        Material theMaterial = PA.getMaterial();
        theMaterial.setDiffuseColor(1.0f, 0.0f, 0.0f);
        theMaterial.setLightingEnable(true);

      } // end for statement


      // set neutron colors

      for (int i = 0; i < theApp.cntN; i++)
      {
        Appearance PA = theApp.theNeutrons[i].getAppearance();
        Material theMaterial = PA.getMaterial();
        theMaterial.setDiffuseColor(0.0f, 0.0f, 1.0f);
        theMaterial.setLightingEnable(true);

      } // end for statement


      theApp.groupAxes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupAxes.setChildMask(theApp.getAxesMask());

      return;
    } // end if i


    if (text.equals("1") || text.equals("2") || text.equals("3") ||
        text.equals("4") || text.equals("5") || text.equals("6") ||
        text.equals("7") )
    {
      // Toggle display of quantum substructures:
      // n=spheres, j=cylinders, m=cones, s=circles, i=circles

      // QNCOption: 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

      // First turn off any shells currently displayed

      theApp.showSubstructure = false;

      theApp.groupNSpheres.setWhichChild(Switch.CHILD_MASK);
      theApp.groupNSpheres.setChildMask(theApp.getNSpheresMask());

      theApp.groupJCylinders.setWhichChild(Switch.CHILD_MASK);
      theApp.groupJCylinders.setChildMask(theApp.getJCylindersMask());

      theApp.groupMCones.setWhichChild(Switch.CHILD_MASK);
      theApp.groupMCones.setChildMask(theApp.getMConesMask());

      theApp.groupSCircles.setWhichChild(Switch.CHILD_MASK);
      theApp.groupSCircles.setChildMask(theApp.getSCirclesMask());

      theApp.groupICircles.setWhichChild(Switch.CHILD_MASK);
      theApp.groupICircles.setChildMask(theApp.getICirclesMask());

      // now turn on the one we want

      BitSet mask = new BitSet(6);

      int i = Integer.valueOf(text).intValue();

      mask.set(i-1);

      switch (theApp.QNCOption)
      {
        case 1: theApp.groupNSpheres.setWhichChild(Switch.CHILD_MASK);
                theApp.groupNSpheres.setChildMask(mask);
                break;

        case 2: theApp.groupJCylinders.setWhichChild(Switch.CHILD_MASK);
                theApp.groupJCylinders.setChildMask(mask);
                break;

        case 3: theApp.groupMCones.setWhichChild(Switch.CHILD_MASK);
                theApp.groupMCones.setChildMask(mask);
                break;

        case 5: theApp.groupSCircles.setWhichChild(Switch.CHILD_MASK);
                theApp.groupSCircles.setChildMask(mask);
                break;

        case 6: theApp.groupICircles.setWhichChild(Switch.CHILD_MASK);
                theApp.groupICircles.setChildMask(mask);
                break;

      } // end switch statement

      return;

    } // end if 1, 2, 3, 4, 5, 6



    if (text.equals("8") && (!modifier.equals("Shift")))
    {
      // Toggle display of quantum substructures:
      // n=spheres, j=cylinders, m=cones, s=circles, i=circles

      // QNCOption: 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

      theApp.showSubstructure = !theApp.showSubstructure;

      // need to show or hide substructures

      theApp.groupNSpheres.setWhichChild(Switch.CHILD_MASK);
      theApp.groupNSpheres.setChildMask(theApp.getNSpheresMask());

      theApp.groupJCylinders.setWhichChild(Switch.CHILD_MASK);
      theApp.groupJCylinders.setChildMask(theApp.getJCylindersMask());

      theApp.groupMCones.setWhichChild(Switch.CHILD_MASK);
      theApp.groupMCones.setChildMask(theApp.getMConesMask());

      theApp.groupSCircles.setWhichChild(Switch.CHILD_MASK);
      theApp.groupSCircles.setChildMask(theApp.getSCirclesMask());

      theApp.groupICircles.setWhichChild(Switch.CHILD_MASK);
      theApp.groupICircles.setChildMask(theApp.getICirclesMask());

      return;

    } // end if 8


    if (text.equals("X") && (modifier.equals("Shift")))
    {
      // Switch rotation axis to X-axis
      theApp.revolveX = true;
      theApp.revolveY = false;
      theApp.revolveZ = false;
      theApp.zoomR    = false;

      theApp.groupProtons.setWhichChild(Switch.CHILD_MASK);
      theApp.groupProtons.setChildMask(theApp.getProtonMask());

      return;
    } // end if X


    if (text.equals("Y") && (modifier.equals("Shift")))
    {
      // Switch rotation axis to Y

      theApp.revolveX = false;
      theApp.revolveY = true;
      theApp.revolveZ = false;
      theApp.zoomR    = false;

      theApp.groupProtons.setWhichChild(Switch.CHILD_MASK);
      theApp.groupProtons.setChildMask(theApp.getProtonMask());


      return;
    } // end if Y

    if (text.equals("Z") && (modifier.equals("Shift")))
    {
      // Switch rotation axis to Z or zoom
      if (!theApp.revolveZ)
      {
        theApp.revolveX = false;
        theApp.revolveY = false;
        theApp.revolveZ = true;
        theApp.zoomR    = false;
      }
      else
      {
        theApp.revolveX = false;
        theApp.revolveY = false;
        theApp.revolveZ = false;
        theApp.zoomR    = true;
      }


      theApp.groupProtons.setWhichChild(Switch.CHILD_MASK);
      theApp.groupProtons.setChildMask(theApp.getProtonMask());
//      theApp.groupNeutrons.setWhichChild(Switch.CHILD_MASK);
//      theApp.groupNeutrons.setChildMask(theApp.getNeutronMask());

      return;
    } // end if Z


    if (text.equals("F1"))
    {
      doF1();
      return;
    } // end if F1

    if (text.equals("F2"))
    {
      doF2();
      return;
    } // end if F2

    if (text.equals("F3"))
    {
      doF3();
      return;
    } // end if F3

    if (text.equals("F4"))
    {
      doF4();
      return;
    } // end if F4

    if (text.equals("F5"))
    {
      doF5();
      return;
    } // end if F5

    if (text.equals("F6"))
    {
      doF6();
      return;
    } // end if F6


    if (text.equals("F7"))
    {
      doF7();
      return;
    } // end if F7

    if (text.equals("F8"))
    {
      doF8();
      return;
    } // end if F8


    if (text.equals("F9"))
    {
      doF9();
      return;
    } // end if F9

    if (text.equals("F10"))
    {
      doF10();
      return;
    } // end if F10

  } // end changeOption


  public void doG()
  {
    // shift to Gass model

    theApp.model = 'G';
    theApp.modelName = "Independent Particle";

    theApp.myRandomBehavior.setEnable(true);

    // see if we have to chnage bonds

    if (theApp.showBondType > 0)
    {
       theApp.groupFCCBonds.setWhichChild(Switch.CHILD_MASK);
       theApp.groupFCCBonds.setChildMask(theApp.getFCCBondMask());
       theApp.groupSCPBonds.setWhichChild(Switch.CHILD_MASK);
       theApp.groupSCPBonds.setChildMask(theApp.getSCPBondMask());

    } // end if

    theApp.groupFissionPlanes.setWhichChild(Switch.CHILD_MASK);
    theApp.groupFissionPlanes.setChildMask(theApp.getFissionPlaneMask());


  } // end doG


  public void doA()
  {

    // shift to FCC

    theApp.model = 'S';  //

    doS();

    theApp.modelName = "Alpha Cluster model";

    theApp.model = 'A';

    theApp.groupAlphas.setWhichChild(Switch.CHILD_MASK);
    theApp.groupAlphas.setChildMask(theApp.getAlphaMask());

    return;

  }  // end doA



  public void doS()
  {
    // shift to FCC or SCP model

    theApp.myRandomBehavior.setEnable(false);

    if (theApp.model != 'F')
    {
      theApp.model = 'F';
    } // end if statement
    else
    {
      theApp.model = 'S';
    } // end else part

    if (!theApp.modelName.equals("FCC model"))
    {
      // changing from SCP model to FCC model

      theApp.modelName = "FCC model";

      // move all protons to FCC location

      for (int i = 0; i < theApp.cntP; i++)
      {

        float x = theApp.theProtons[i].cX = theApp.theProtons[i].fccX;
        float y = theApp.theProtons[i].cY = theApp.theProtons[i].fccY;
        float z = theApp.theProtons[i].cZ = theApp.theProtons[i].fccZ;

        float r = theApp.theProtons[i].r;
        float theta = theApp.theProtons[i].theta = r2d(Math.acos((double)(z / r)));

        if ( (theta < 0.1f) && (theta > -0.1f) ) theta = 0.1f;
        float phi = r2d(Math.atan((double)(y / x)) );

        if ((y < 0.0f) && (x > 0.0f)) phi = -Math.abs(phi);
        if ((y < 0.0f) && (x < 0.0f)) phi = 180.0f + Math.abs(phi);
        if ((y > 0.0f) && (x > 0.0f)) phi =  Math.abs(phi);
        if ((y > 0.0f) && (x < 0.0f)) phi = 180.0f - Math.abs(phi);

        theApp.theProtons[i].phi = phi;

        loc.set(x, y, z);

        movement.setTranslation(loc);
        theApp.objPPosition[i].setTransform(movement);

      } // end for  statement

      // move all Neutrons

      for (int i = 0; i < theApp.cntN; i++)
      {

        float x = theApp.theNeutrons[i].cX = theApp.theNeutrons[i].fccX;
        float y = theApp.theNeutrons[i].cY = theApp.theNeutrons[i].fccY;
        float z = theApp.theNeutrons[i].cZ = theApp.theNeutrons[i].fccZ;

        float r = theApp.theNeutrons[i].r;
        float theta = theApp.theNeutrons[i].theta = r2d(Math.acos((double)(z / r)));

        if ( (theta < 0.1f) && (theta > -0.1f) ) theta = 0.1f;
        float phi = r2d(Math.atan((double)(y / x)) );

        if ((y < 0.0f) && (x > 0.0f)) phi = -Math.abs(phi);
        if ((y < 0.0f) && (x < 0.0f)) phi = 180.0f + Math.abs(phi);
        if ((y > 0.0f) && (x > 0.0f)) phi =  Math.abs(phi);
        if ((y > 0.0f) && (x < 0.0f)) phi = 180.0f - Math.abs(phi);

        theApp.theNeutrons[i].phi = phi;

        loc.set(x, y, z);

        movement.setTranslation(loc);
        theApp.objNPosition[i].setTransform(movement);

      } // end for  statement

      // if bonds are displayed, change them to FCC bonds

      if (theApp.showBondType > 0)
      {
         theApp.groupFCCBonds.setWhichChild(Switch.CHILD_MASK);
         theApp.groupFCCBonds.setChildMask(theApp.getFCCBondMask());
         theApp.groupSCPBonds.setWhichChild(Switch.CHILD_MASK);
         theApp.groupSCPBonds.setChildMask(theApp.getSCPBondMask());

      } // end if statement

      theApp.groupFissionPlanes.setWhichChild(Switch.CHILD_MASK);
      theApp.groupFissionPlanes.setChildMask(theApp.getFissionPlaneMask());

      return;

    } // end if statement


    // changing from FCC model to SCP model

    theApp.modelName = "SCP model";

    // move all protons to SCP location

    for (int i = 0; i < theApp.cntP; i++)
    {

      float x = theApp.theProtons[i].cX = theApp.theProtons[i].scpX;
      float y = theApp.theProtons[i].cY = theApp.theProtons[i].scpY;
      float z = theApp.theProtons[i].cZ = theApp.theProtons[i].scpZ;

      float r = theApp.theProtons[i].r;
      float theta = theApp.theProtons[i].theta = r2d(Math.acos((double)(z / r)));

      if ( (theta < 0.1f) && (theta > -0.1f) ) theta = 0.1f;
      float phi = r2d(Math.atan((double)(y / x)) );

      if ((y < 0.0f) && (x > 0.0f)) phi = -Math.abs(phi);
      if ((y < 0.0f) && (x < 0.0f)) phi = 180.0f + Math.abs(phi);
      if ((y > 0.0f) && (x > 0.0f)) phi =  Math.abs(phi);
      if ((y > 0.0f) && (x < 0.0f)) phi = 180.0f - Math.abs(phi);

      theApp.theProtons[i].phi = phi;

      loc.set(x, y, z);

      movement.setTranslation(loc);
      theApp.objPPosition[i].setTransform(movement);

    } // end for  statement

    // move all Neutrons

    for (int i = 0; i < theApp.cntN; i++)
    {

      float x = theApp.theNeutrons[i].cX = theApp.theNeutrons[i].scpX;
      float y = theApp.theNeutrons[i].cY = theApp.theNeutrons[i].scpY;
      float z = theApp.theNeutrons[i].cZ = theApp.theNeutrons[i].scpZ;

      float r = theApp.theNeutrons[i].r;
      float theta = theApp.theNeutrons[i].theta = r2d(Math.acos((double)(z / r)));

      if ( (theta < 0.1f) && (theta > -0.1f) ) theta = 0.1f;
      float phi = r2d(Math.atan((double)(y / x)) );

      if ((y < 0.0f) && (x > 0.0f)) phi = -Math.abs(phi);
      if ((y < 0.0f) && (x < 0.0f)) phi = 180.0f + Math.abs(phi);
      if ((y > 0.0f) && (x > 0.0f)) phi =  Math.abs(phi);
      if ((y > 0.0f) && (x < 0.0f)) phi = 180.0f - Math.abs(phi);

      theApp.theNeutrons[i].phi = phi;

      loc.set(x, y, z);

      movement.setTranslation(loc);
      theApp.objNPosition[i].setTransform(movement);

    } // end for  statement

    // display scp bonds if on

    if (theApp.showBondType > 0)
    {
      theApp.groupSCPBonds.setWhichChild(Switch.CHILD_MASK);
      theApp.groupSCPBonds.setChildMask(theApp.getSCPBondMask());
      theApp.groupFCCBonds.setWhichChild(Switch.CHILD_MASK);
      theApp.groupFCCBonds.setChildMask(theApp.getFCCBondMask());
    } // end if statement


    theApp.groupFissionPlanes.setWhichChild(Switch.CHILD_MASK);
    theApp.groupFissionPlanes.setChildMask(theApp.getFissionPlaneMask());

  } // end doS



  public void doF1()
  {
    // subtract Proton

    int i;

    for (i = 0; i < theApp.cntP; i++)
    {
     if (!theApp.theProtons[i].currentlyDisplayed) break;
    } // end for statement

    i--;

    if (i > -1)
    {
      theApp.theProtons[i].currentlyDisplayed = false;
      theApp.currentZ--;
    } // end if statement

    theApp.groupProtons.setWhichChild(Switch.CHILD_MASK);
    theApp.groupProtons.setChildMask(theApp.getProtonMask());
    theApp.groupNeutrons.setWhichChild(Switch.CHILD_MASK);
    theApp.groupNeutrons.setChildMask(theApp.getNeutronMask());


    if (theApp.showBondType > 0)
    {
       theApp.groupFCCBonds.setWhichChild(Switch.CHILD_MASK);
       theApp.groupFCCBonds.setChildMask(theApp.getFCCBondMask());
       theApp.groupSCPBonds.setWhichChild(Switch.CHILD_MASK);
       theApp.groupSCPBonds.setChildMask(theApp.getSCPBondMask());

    } // end if

    theApp.groupFissionPlanes.setWhichChild(Switch.CHILD_MASK);
    theApp.groupFissionPlanes.setChildMask(theApp.getFissionPlaneMask());


  } // end doF1


  public void doF2()
  {
    // add a Proton

    int i;

    for (i = 0; i < theApp.cntP; i++)
    {
     if (!theApp.theProtons[i].currentlyDisplayed) break;
    } // end for statement

    theApp.theProtons[i].currentlyDisplayed = true;

    theApp.currentZ++;

System.out.println(" Proton number: "+i);
System.out.println(" ");


    theApp.groupProtons.setWhichChild(Switch.CHILD_MASK);
    theApp.groupProtons.setChildMask(theApp.getProtonMask());
    theApp.groupNeutrons.setWhichChild(Switch.CHILD_MASK);
    theApp.groupNeutrons.setChildMask(theApp.getNeutronMask());


    if (theApp.showBondType > 0)
    {
       theApp.groupFCCBonds.setWhichChild(Switch.CHILD_MASK);
       theApp.groupFCCBonds.setChildMask(theApp.getFCCBondMask());
       theApp.groupSCPBonds.setWhichChild(Switch.CHILD_MASK);
       theApp.groupSCPBonds.setChildMask(theApp.getSCPBondMask());

    } // end if

    theApp.groupFissionPlanes.setWhichChild(Switch.CHILD_MASK);
    theApp.groupFissionPlanes.setChildMask(theApp.getFissionPlaneMask());

  } // end doF2



  public void doF3()
  {
    // subtract Neutron

    int i;

    for (i = 0; i < theApp.cntN; i++)
    {
     if (!theApp.theNeutrons[i].currentlyDisplayed) break;
    } // end for statement

    i--;

    if (i > -1)
    {
      theApp.theNeutrons[i].currentlyDisplayed = false;

      theApp.currentN--;
    } // end if statement

    theApp.groupProtons.setWhichChild(Switch.CHILD_MASK);
    theApp.groupProtons.setChildMask(theApp.getProtonMask());
    theApp.groupNeutrons.setWhichChild(Switch.CHILD_MASK);
    theApp.groupNeutrons.setChildMask(theApp.getNeutronMask());


    if (theApp.showBondType > 0)
    {
       theApp.groupFCCBonds.setWhichChild(Switch.CHILD_MASK);
       theApp.groupFCCBonds.setChildMask(theApp.getFCCBondMask());
       theApp.groupSCPBonds.setWhichChild(Switch.CHILD_MASK);
       theApp.groupSCPBonds.setChildMask(theApp.getSCPBondMask());

    } // end if

    theApp.groupFissionPlanes.setWhichChild(Switch.CHILD_MASK);
    theApp.groupFissionPlanes.setChildMask(theApp.getFissionPlaneMask());

  } // end doF3


  public void doF4()
  {
    // add a Neutron

    int i;

    for (i = 0; i < theApp.cntN; i++)
    {
     if (!theApp.theNeutrons[i].currentlyDisplayed) break;
    } // end for statement

    theApp.theNeutrons[i].currentlyDisplayed = true;

    theApp.currentN++;

System.out.println(" Neutron number: "+i);
System.out.println(" ");

    theApp.groupProtons.setWhichChild(Switch.CHILD_MASK);
    theApp.groupProtons.setChildMask(theApp.getProtonMask());
    theApp.groupNeutrons.setWhichChild(Switch.CHILD_MASK);
    theApp.groupNeutrons.setChildMask(theApp.getNeutronMask());


    if (theApp.showBondType > 0)
    {
       theApp.groupFCCBonds.setWhichChild(Switch.CHILD_MASK);
       theApp.groupFCCBonds.setChildMask(theApp.getFCCBondMask());
       theApp.groupSCPBonds.setWhichChild(Switch.CHILD_MASK);
       theApp.groupSCPBonds.setChildMask(theApp.getSCPBondMask());

    } // end if

    theApp.groupFissionPlanes.setWhichChild(Switch.CHILD_MASK);
    theApp.groupFissionPlanes.setChildMask(theApp.getFissionPlaneMask());


  } // end doF4





  public void doF5()
  {
      // subtract a shell to display

      int sP = 99999;  // assume no shell currently displayed
      int sN = 99999;

      for (int i = (theApp.cntP-1); i >= 0; i--)
      {
        if (sP != theApp.theProtons[i].n) sP = theApp.theProtons[i].n;
        if (theApp.theProtons[i].currentlyDisplayed) break;
      } // end for statement


      for (int i = (theApp.cntN-1); i >=0; i--)
      {
        if (sN != theApp.theNeutrons[i].n) sN = theApp.theNeutrons[i].n;
        if (theApp.theNeutrons[i].currentlyDisplayed) break;
      } // end for statement

      if (sN > sP) sP = sN;

      // sP = the shell to turn off

      theApp.currentZ = 0;

      for (int i = 0; i < theApp.cntP; i++)
      {
        if (theApp.theProtons[i].n == sP)
        {
          theApp.theProtons[i].currentlyDisplayed = false;
        }

        if (theApp.theProtons[i].currentlyDisplayed)
        {
           theApp.currentZ++;
        }

      } // end for statement


      theApp.currentN = 0;

      for (int i = 0; i < theApp.cntN; i++)
      {
        if (theApp.theNeutrons[i].n == sP)
        {
          theApp.theNeutrons[i].currentlyDisplayed = false;
        }

        if (theApp.theNeutrons[i].currentlyDisplayed)
        {
           theApp.currentN++;
        }

      } // end for statement

      // Determine which to make visible

      theApp.groupProtons.setWhichChild(Switch.CHILD_MASK);
      theApp.groupProtons.setChildMask(theApp.getProtonMask());
      theApp.groupNeutrons.setWhichChild(Switch.CHILD_MASK);
      theApp.groupNeutrons.setChildMask(theApp.getNeutronMask());


      if (theApp.showBondType > 0)
      {
         theApp.groupFCCBonds.setWhichChild(Switch.CHILD_MASK);
         theApp.groupFCCBonds.setChildMask(theApp.getFCCBondMask());
         theApp.groupSCPBonds.setWhichChild(Switch.CHILD_MASK);
         theApp.groupSCPBonds.setChildMask(theApp.getSCPBondMask());

      } // end if

    theApp.groupFissionPlanes.setWhichChild(Switch.CHILD_MASK);
    theApp.groupFissionPlanes.setChildMask(theApp.getFissionPlaneMask());


  } // end doF5



  public void doF6()
  {
      // add a shell to display

      // determine what shell is currently completely displayed

      int sP = -1;  // assume no shell currently displayed
      int sN = -1;


      for (int i = 0; i < theApp.cntP; i++)
      {
        if (sP != theApp.theProtons[i].n) sP = theApp.theProtons[i].n;
        if (!theApp.theProtons[i].currentlyDisplayed) break;
      } // end for statement


      for (int i = 0; i < theApp.cntN; i++)
      {
        if (sN != theApp.theNeutrons[i].n) sN = theApp.theNeutrons[i].n;
        if (!theApp.theNeutrons[i].currentlyDisplayed) break;
      } // end for statement

      if (sN < sP) sP = sN;

      theApp.currentZ = 0;

      for (int i = 0; i < theApp.cntP; i++)
      {
        if (theApp.theProtons[i].n <= sP)
        {
          theApp.theProtons[i].currentlyDisplayed = true;
          theApp.currentZ++;
        }
        else
        {
          break;
        }
      } // end for statement


      theApp.currentN = 0;

      for (int i = 0; i < theApp.cntN; i++)
      {
        if (theApp.theNeutrons[i].n <= sP)
        {
          theApp.theNeutrons[i].currentlyDisplayed = true;
          theApp.currentN++;
        }
        else
        {
           break;
        }
      } // end for statement

      // Determine which to make visible

      theApp.groupProtons.setWhichChild(Switch.CHILD_MASK);
      theApp.groupProtons.setChildMask(theApp.getProtonMask());
      theApp.groupNeutrons.setWhichChild(Switch.CHILD_MASK);
      theApp.groupNeutrons.setChildMask(theApp.getNeutronMask());

      if (theApp.showBondType > 0)
      {
         theApp.groupFCCBonds.setWhichChild(Switch.CHILD_MASK);
         theApp.groupFCCBonds.setChildMask(theApp.getFCCBondMask());
         theApp.groupSCPBonds.setWhichChild(Switch.CHILD_MASK);
         theApp.groupSCPBonds.setChildMask(theApp.getSCPBondMask());

      } // end if

    theApp.groupFissionPlanes.setWhichChild(Switch.CHILD_MASK);
    theApp.groupFissionPlanes.setChildMask(theApp.getFissionPlaneMask());


  } // end doF6




  public void doF7()
  {
      // Decrease bond width

      if (theApp.showBondType == 0) return;
      if ((theApp.model != 'F') && (theApp.model != 'S')) return;

      // Decrease by 10%
      theApp.bondRadius -= (0.1 * theApp.bondRadius);

      for (int i = 0; i < theApp.numFCCBonds; i++)
      {
         theApp.fccBonds[i].setRadius(theApp.bondRadius);
         theApp.allFCCBonds[i].setGeometry(theApp.fccBonds[i].Solid());

      } // end for statement

      for (int i = 0; i < theApp.numSCPBonds; i++)
      {
         theApp.scpBonds[i].setRadius(theApp.bondRadius);
         theApp.allSCPBonds[i].setGeometry(theApp.scpBonds[i].Solid());

      } // end for statement

  } // end doF7



  public void doF8()
  {
      // Increase bond width

      if (theApp.showBondType == 0) return;
      if ((theApp.model != 'F') && (theApp.model != 'S')) return;

      // Increase by 10%
      theApp.bondRadius += (0.1 * theApp.bondRadius);

      for (int i = 0; i < theApp.numFCCBonds; i++)
      {
         theApp.fccBonds[i].setRadius(theApp.bondRadius);
         theApp.allFCCBonds[i].setGeometry(theApp.fccBonds[i].Solid());

      } // end for statement

      for (int i = 0; i < theApp.numSCPBonds; i++)
      {
         theApp.scpBonds[i].setRadius(theApp.bondRadius);
         theApp.allSCPBonds[i].setGeometry(theApp.scpBonds[i].Solid());

      } // end for statement

  } // end doF8




  public void doF9()
  {
      // Decrease nucleon size

      // Decrease by 10%
      theApp.currentNucleonRadius -= (0.1 * theApp.currentNucleonRadius);

      T3DScale.setScale(theApp.currentNucleonRadius / theApp.currentLatticeSpacing);

      for (int i = 0; i < theApp.cntP; i++)
      {

        theApp.objPSize[i].setTransform(T3DScale);

      } // end for statement

      for (int i = 0; i < theApp.cntN; i++)
      {

        theApp.objNSize[i].setTransform(T3DScale);

      } // end for statement

  } // end doF9



  public void doF10()
  {
      // Increase nucleon size

      // Increase by 10%
      theApp.currentNucleonRadius += (0.1 * theApp.currentNucleonRadius);

      T3DScale.setScale(theApp.currentNucleonRadius / theApp.currentLatticeSpacing);

      for (int i = 0; i < theApp.cntP; i++)
      {

        theApp.objPSize[i].setTransform(T3DScale);

      } // end for statement


      for (int i = 0; i < theApp.cntN; i++)
      {

        theApp.objNSize[i].setTransform(T3DScale);

      } // end for statement


  } // end doF10



  public float r2d(double angle)
  {
     return((float)(angle * 360.0 / (2.0 * Math.PI)));

  } // end r2d

  public float d2r(double angle)
  {
     return((float)(angle * (2.0 * Math.PI) / 360.0 ));

  } // end d2r


  public void doBackgroundColor()
  {
     // Set the background color

     Color theColor = JColorChooser.showDialog(null, "Select Color", Color.white);

     if (theColor == null) return;

     float red = theColor.getRed() / 255.0f;
     float green = theColor.getGreen() / 255.0f;
     float blue  = theColor.getBlue() / 255.0f;

     theApp.BG.setColor(red, green, blue);

     theApp.BGCr = red;
     theApp.BGCg = green;
     theApp.BGCb = blue;

     return;

  } // end doBackgroundColor


  public void doShellColor()
  {
     // Set the background color

     float red;
     float green;
     float blue;

     for (int i = 0; i < 7; i++)
     {
        Color theColor = JColorChooser.showDialog(null, "Select Shell "+i+" Color", Color.white);

        if (theColor == null) continue;

        red = theColor.getRed() / 255.0f;
        green = theColor.getGreen() / 255.0f;
        blue  = theColor.getBlue() / 255.0f;

        theApp.theColors[i][0] = red;
        theApp.theColors[i][1] = green;
        theApp.theColors[i][2] = blue;

     } // end for statement

     // change all sphere colors
     changeSphereColors();

     // change Shell colors:

     setShellAppearance();

     theApp.groupProtons.setWhichChild(Switch.CHILD_MASK);
     theApp.groupProtons.setChildMask(theApp.getProtonMask());

     return;

  } // end doShellColor


  public void doShellTrans()
  {

     String aNum = JOptionPane.showInputDialog(null, "Enter transparency value between 0.1 and 0.9.",
                                                     "Current value: "+theApp.SubStructureTransparency,
                                                     JOptionPane.QUESTION_MESSAGE);

     if (aNum == null) return;

     float theNum;

     try
     {
       theNum = Float.valueOf(aNum).floatValue();
     }
     catch(Exception exp)
     {
      return;
     } // end catch

     if (theNum < 0.1f) theNum = 0.1f;
     if (theNum > 0.9f) theNum = 0.9f;

     theApp.SubStructureTransparency = theNum;

     setShellAppearance();

     theApp.groupProtons.setWhichChild(Switch.CHILD_MASK);
     theApp.groupProtons.setChildMask(theApp.getProtonMask());


  } // end doShellTrans


  public void doTextColor()
  {
     // Set the basic text color

     Color theColor = JColorChooser.showDialog(null, "Select Text Color", Color.white);

     if (theColor == null) return;

     theApp.TCr = theColor.getRed() / 255.0f;
     theApp.TCg = theColor.getGreen() / 255.0f;
     theApp.TCb = theColor.getBlue() / 255.0f;

     return;

  } // end doTextColor


  public void doFissionPlaneColor()
  {
     // Set the basic text color

     Color theColor = JColorChooser.showDialog(null, "Select Fission Plan Color", Color.white);

     if (theColor == null) return;

     for (int i = 0; i < 21; i++)
     {

       theApp.FPCr = theColor.getRed() / 255.0f;
       theApp.FPCg = theColor.getGreen() / 255.0f;
       theApp.FPCb = theColor.getBlue() / 255.0f;

       Appearance  AP = theApp.theFissionPlanes[i].getAppearance();
       Material theMaterial = AP.getMaterial();
       theMaterial.setDiffuseColor(theApp.FPCr, theApp.FPCg, theApp.FPCb);

//       theFissionPlanes[i].setAppearance(AP);


     } // for statement

     return;

  } // end doFissionPlaneColor


  public void doFissionPlaneTrans()
  {

     String aNum = JOptionPane.showInputDialog(null, "Enter fission plane transparency value between 0.1 and 0.9.",
                                                     "Current value: "+theApp.FPTransparency,
                                                     JOptionPane.QUESTION_MESSAGE);

     if (aNum == null) return;

     float theNum;

     try
     {
       theNum = Float.valueOf(aNum).floatValue();
     }
     catch(Exception exp)
     {
      return;
     } // end catch

     if (theNum < 0.1f) theNum = 0.1f;
     if (theNum > 0.9f) theNum = 0.9f;

     theApp.FPTransparency = theNum;

     Appearance AP;

     for (int i = 0; i < 21; i++)
     {
       AP = theApp.theFissionPlanes[i].getAppearance();
       TransparencyAttributes TA = new TransparencyAttributes(TransparencyAttributes.NICEST, theApp.FPTransparency);
       TA.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
       AP.setTransparencyAttributes(TA);

     } // end for statement

  } // end doFissionPlaneTrans


  public void changeSphereColors()
  {
      // QNCOption = 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

      float red;;
      float green;
      float blue;

      int index = 0;

      for (int i = 0; i < theApp.cntP; i++)
      {

         switch (theApp.QNCOption)
         {
           case 1: index = theApp.theProtons[i].n;
                   break;
           case 2: index = theApp.theProtons[i].j / 2;
                   break;
           case 3: index = Math.abs(theApp.theProtons[i].m) / 2;
                   break;
           case 4: index = theApp.theProtons[i].l / 2;
                   break;
           case 5: index = theApp.theProtons[i].s / 2;
System.out.println("s: "+theApp.theProtons[i].s);
                   break;
           case 6: index = theApp.theProtons[i].i / 2;
                   break;

         } // end switch

         if (index > 6)
         {
           System.out.println("Shell index = "+index);
           index = 6;
         } // end if statement

         red   = theApp.theColors[index][0];
         green = theApp.theColors[index][1];
         blue  = theApp.theColors[index][2];

        Appearance PA = theApp.theProtons[i].getAppearance();
        Material theMaterial = PA.getMaterial();
        theMaterial.setDiffuseColor(red, green, blue);
        theMaterial.setLightingEnable(true);

      } // end for statement

      // set neutron colors

      for (int i = 0; i < theApp.cntN; i++)
      {
         switch (theApp.QNCOption)
         {
           case 1: index = theApp.theNeutrons[i].n;
                   break;
           case 2: index = theApp.theNeutrons[i].j / 2;
                   break;
           case 3: index = Math.abs(theApp.theNeutrons[i].m) / 2;
                   break;
           case 4: index = theApp.theNeutrons[i].l / 2;
                   break;
           case 5: index = theApp.theNeutrons[i].s / 2;
System.out.println("s: "+theApp.theNeutrons[i].s);
                   break;
           case 6: index = theApp.theNeutrons[i].i / 2;
                   break;

         } // end switch

         if (index > 6)
         {
           System.out.println("Shell index = "+index);
           index = 6;
         } // end if statement

         red   = theApp.theColors[index][0];
         green = theApp.theColors[index][1];
         blue  = theApp.theColors[index][2];

         Appearance PA = theApp.theNeutrons[i].getAppearance();
         Material theMaterial = PA.getMaterial();
         theMaterial.setDiffuseColor(red, green, blue);
         theMaterial.setLightingEnable(true);

      } // end for statement

  } // end changeSphereColors()


  public void setShellAppearance()
  {

     Appearance AP;
     Material theMaterial;

     for (int i = 0; i < 7; i++)
     {
       AP = theApp.theShellAppearances[i];
       theMaterial = AP.getMaterial();

       theMaterial.setDiffuseColor(theApp.theColors[i][0],
                                   theApp.theColors[i][1],
                                   theApp.theColors[i][2]);

       TransparencyAttributes TA = new TransparencyAttributes(TransparencyAttributes.NICEST, theApp.SubStructureTransparency);
       AP.setTransparencyAttributes(TA);

       theApp.nSpheres[i].setAppearance(AP);
       theApp.jCylinders[i].setAppearance(AP);
//       theApp.mCones[i].setAppearance(AP);

     } // end for statement

   } // end setShellAppearance


} // end class NDCPFrame



