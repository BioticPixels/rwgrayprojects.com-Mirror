package ndcp1;

//
//  Need this to be able to write text over the Canvas3D.
//


import java.awt.*;
import javax.media.j3d.*;

import javax.swing.*;

public class MyCanvas3D extends Canvas3D
{

  ndcp theApp;

  Graphics2D g2D;
  int line;
  int incLine;
  int lm;       // left margin of status region
  int indent;

  String sRadius;

  Color tc = new Color(1.0f, 1.0f, 1.0f);


  public MyCanvas3D(ndcp theApp)
  {
     super(null);

     g2D = null;

     this.theApp = theApp;

     incLine = 15;   // space between a line
     indent = 15;    // indentation of a line

  }  // end constructor StatusDisplay

  public void postSwap()
  {
     tc = new Color(theApp.TCr, theApp.TCg, theApp.TCb);

     if (g2D == null)
     {
       Graphics g = getGraphics();
       g2D = (Graphics2D)g;

       g2D.setColor(tc);
     }

     if (!theApp.DisplayStatus) return;

     // We need to display the status info

     line = 25;
     lm = (int)(getSize().getWidth())-250;


     // Display the mouse mode

     g2D.drawString("MouseMode: ", lm, line);


     if (theApp.revolveX)
     {
       g2D.setColor(Color.red);
       g2D.drawString("X", lm+80, line);
     }
     else
     {
       g2D.setColor(tc);
       g2D.drawString("X", lm+80, line);
     }

     if (theApp.revolveY)
     {
       g2D.setColor(Color.red);
       g2D.drawString("Y", lm+100, line);
     }
     else
     {
       g2D.setColor(tc);
       g2D.drawString("Y", lm+100, line);
     }

     if (theApp.revolveZ)
     {
       g2D.setColor(Color.red);
       g2D.drawString("Z", lm+120, line);
     }
     else
     {
       g2D.setColor(tc);
       g2D.drawString("Z", lm+120, line);
     }

     if (theApp.zoomR)
     {
       g2D.setColor(Color.red);
       g2D.drawString("Zoom", lm+140, line);
     }
     else
     {
       g2D.setColor(tc);
       g2D.drawString("Zoom", lm+140, line);
     }


     line += 2 * incLine;
     g2D.setColor(tc);


     // Display the Nucleon info

     if ( (theApp.currentZ <= theApp.nucleusName.length) &&
          (theApp.currentZ > 0) )
       g2D.drawString("NUCLEUS: "+theApp.nucleusName[theApp.currentZ-1], lm, line);
     else
       g2D.drawString("NUCLEUS: ", lm, line);

     line += incLine;
     g2D.drawString("A:"+(theApp.currentN+theApp.currentZ)+" Z:"+theApp.currentZ+" N:"+theApp.currentN, lm+indent, line);

     line += 2 * incLine;
     g2D.drawString("VIEWING MODEL: "+theApp.modelName, lm, line);

     line += incLine;
     if (theApp.showBondType > 0)
     {
       g2D.drawString("Bonds: On", lm+indent, line);
       if (theApp.model == 'F')
       {
         // 0=none, 1=PP, 2=NN, 3=PN, 4=PP&NN, 5=PP*PN, 6=NN&PN, 7=all
         g2D.setColor(tc);
         if ((theApp.showBondType == 1) || (theApp.showBondType == 4) ||
             (theApp.showBondType == 5) || (theApp.showBondType == 7) )
           g2D.setColor(Color.yellow);
         g2D.drawString("PP: "+theApp.cntFCCppBonds, lm+indent+70,  line);

         // 0=none, 1=PP, 2=NN, 3=PN, 4=PP&NN, 5=PP*PN, 6=NN&PN, 7=all
         g2D.setColor(tc);
         if ((theApp.showBondType == 2) || (theApp.showBondType == 4) ||
             (theApp.showBondType == 6) || (theApp.showBondType == 7) )
           g2D.setColor(Color.yellow);
         g2D.drawString("NN: "+theApp.cntFCCnnBonds, lm+indent+120, line);

         // 0=none, 1=PP, 2=NN, 3=PN, 4=PP&NN, 5=PP*PN, 6=NN&PN, 7=all
         g2D.setColor(tc);
         if ((theApp.showBondType == 3) || (theApp.showBondType == 5) ||
             (theApp.showBondType == 6) || (theApp.showBondType == 7) )
           g2D.setColor(Color.yellow);
         g2D.drawString("PN: "+theApp.cntFCCpnBonds, lm+indent+170, line);

       } // end if statement
       if (theApp.model == 'S')
       {
         // 0=none, 1=PP, 2=NN, 3=PN, 4=PP&NN, 5=PP*PN, 6=NN&PN, 7=all
         g2D.setColor(tc);
         if ((theApp.showBondType == 1) || (theApp.showBondType == 4) ||
             (theApp.showBondType == 5) || (theApp.showBondType == 7) )
           g2D.setColor(Color.yellow);
         g2D.drawString("PP: "+theApp.cntSCPppBonds, lm+indent+70,  line);

         // 0=none, 1=PP, 2=NN, 3=PN, 4=PP&NN, 5=PP*PN, 6=NN&PN, 7=all
         g2D.setColor(tc);
         if ((theApp.showBondType == 2) || (theApp.showBondType == 4) ||
             (theApp.showBondType == 6) || (theApp.showBondType == 7) )
           g2D.setColor(Color.yellow);
         g2D.drawString("NN: "+theApp.cntSCPnnBonds, lm+indent+120, line);

         // 0=none, 1=PP, 2=NN, 3=PN, 4=PP&NN, 5=PP*PN, 6=NN&PN, 7=all
         g2D.setColor(tc);
         if ((theApp.showBondType == 3) || (theApp.showBondType == 5) ||
             (theApp.showBondType == 6) || (theApp.showBondType == 7) )
           g2D.setColor(Color.yellow);
         g2D.drawString("PN: "+theApp.cntSCPpnBonds, lm+indent+170, line);

       } // end if statement


     }
     else
     {
       g2D.drawString("Bonds: Off", lm+indent, line);
     }

     g2D.setColor(tc);

     line += 2 * incLine;
     g2D.drawString("Occupancy rate: ", lm, line);

     line += incLine;

     sRadius = theApp.currentNucleonRadius+"";
     if (sRadius.length() > 6) sRadius = sRadius.substring(0,5);
     g2D.drawString("Nucleon radius shown as: "+sRadius+" fm", lm, line);

     if (theApp.model == 'F')
     {
       line += incLine;

       String sLattice = theApp.currentLatticeSpacing+"";
       if (sLattice.length() > 6) sLattice = sLattice.substring(0,5);
       g2D.drawString("FCC lattice spacing: "+sLattice+" fm", lm, line);

     } // end if statement

      // display Fision info

     if ((theApp.whichFissionPlane > -1) &&
         ((theApp.model == 'S') || (theApp.model == 'F')))
     {
       line += 2 * incLine;

       g2D.drawString("Fission Plane #"+(theApp.whichFissionPlane+1), lm, line);
       if (theApp.whichFissionPlane < 9)
       {
         g2D.drawString("Symmetrical", lm+105, line);
       }
       else
       {
         g2D.drawString("Asymmetrical", lm+105, line);
       }
       if ((theApp.whichFissionPlane != 0) &&
           (theApp.whichFissionPlane != 3) &&
           (theApp.whichFissionPlane != 6) &&
           (theApp.whichFissionPlane != 9) &&
           (theApp.whichFissionPlane != 12) &&
           (theApp.whichFissionPlane != 15) &&
           (theApp.whichFissionPlane != 18) )
       {
         g2D.drawString("Offset", lm+180, line);
       } // end if statement

       line += incLine;

       g2D.drawString("Bonds PP: "+theApp.cntFissionPP+ " NN: "+theApp.cntFissionNN+" PN: "+theApp.cntFissionPN, lm+indent, line);
       line += incLine;
       g2D.drawString("Nucleons Left: "+theApp.cntFissionLeft+ " Right: "+theApp.cntFissionRight, lm+indent, line);

     } // end if statement

     line += 2 * incLine;

     g2D.drawString("Nucleon color-coding: ", lm, line);

     switch (theApp.QNCOption)
     {
       // 0=none, 1=n, 2=j, 3=m, 4=l, 5=s, 6=i

       case 1:
               g2D.drawString("n-values", lm+125, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[0][0],
                                      theApp.theColors[0][1],
                                      theApp.theColors[0][2]));
               g2D.drawString("0 = red", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[1][0],
                                      theApp.theColors[1][1],
                                      theApp.theColors[1][2]));
               g2D.drawString("1 = yellow", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[2][0],
                                      theApp.theColors[2][1],
                                      theApp.theColors[2][2]));
               g2D.drawString("2 = purple", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[3][0],
                                      theApp.theColors[3][1],
                                      theApp.theColors[3][2]));
               g2D.drawString("3 = green", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[4][0],
                                      theApp.theColors[4][1],
                                      theApp.theColors[4][2]));
               g2D.drawString("4 = blue", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[5][0],
                                      theApp.theColors[5][1],
                                      theApp.theColors[5][2]));
               g2D.drawString("5 = tan", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[6][0],
                                      theApp.theColors[6][1],
                                      theApp.theColors[6][2]));
               g2D.drawString("6 = magenta", lm+indent, line);

               break;
       case 2:
               g2D.drawString("j-values", lm+125, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[0][0],
                                      theApp.theColors[0][1],
                                      theApp.theColors[0][2]));
               g2D.drawString(" 1/2 = red", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[1][0],
                                      theApp.theColors[1][1],
                                      theApp.theColors[1][2]));
               g2D.drawString(" 3/2 = yellow", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[2][0],
                                      theApp.theColors[2][1],
                                      theApp.theColors[2][2]));
               g2D.drawString(" 5/2 = purple", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[3][0],
                                      theApp.theColors[3][1],
                                      theApp.theColors[3][2]));
               g2D.drawString(" 7/2 = green", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[4][0],
                                      theApp.theColors[4][1],
                                      theApp.theColors[4][2]));
               g2D.drawString(" 9/2 = blue", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[5][0],
                                      theApp.theColors[5][1],
                                      theApp.theColors[5][2]));
               g2D.drawString("11/2 = blue", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[6][0],
                                      theApp.theColors[6][1],
                                      theApp.theColors[6][2]));
               g2D.drawString("13/2 = blue", lm+indent, line);
               line += incLine;

               break;

       case 3:
               g2D.drawString("m-values", lm+125, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[0][0],
                                      theApp.theColors[0][1],
                                      theApp.theColors[0][2]));
               g2D.drawString(" 1/2 = red", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[1][0],
                                      theApp.theColors[1][1],
                                      theApp.theColors[1][2]));
               g2D.drawString(" 3/2 = yellow", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[2][0],
                                      theApp.theColors[2][1],
                                      theApp.theColors[2][2]));
               g2D.drawString(" 5/2 = purple", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[3][0],
                                      theApp.theColors[3][1],
                                      theApp.theColors[3][2]));
               g2D.drawString(" 7/2 = green", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[4][0],
                                      theApp.theColors[4][1],
                                      theApp.theColors[4][2]));
               g2D.drawString(" 9/2 = blue", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[5][0],
                                      theApp.theColors[5][1],
                                      theApp.theColors[5][2]));
               g2D.drawString("11/2 = blue", lm+indent, line);
               line += incLine;
               g2D.setColor(new Color(theApp.theColors[6][0],
                                      theApp.theColors[6][1],
                                      theApp.theColors[6][2]));
               g2D.drawString("13/2 = blue", lm+indent, line);
               line += incLine;
               break;

       case 6:
               g2D.drawString("isospin values", lm+125, line);
               line += incLine;
               g2D.setColor(Color.red);
               g2D.drawString(" 1/2 = red (Proton)", lm+indent, line);
               line += incLine;
               g2D.setColor(Color.blue);
               g2D.drawString("-1/2 = blue (Neutron)", lm+indent, line);

               break;

     } // end switch

     g2D.setColor(tc);

     line += incLine;


  } // end postSwap


} // end class MyCanvas3D



