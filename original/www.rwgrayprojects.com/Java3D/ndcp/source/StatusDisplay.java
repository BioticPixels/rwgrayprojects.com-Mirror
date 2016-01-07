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

public class StatusDisplay extends JComponent
                      implements Constants
{

  int numProtons;

  public StatusDisplay()
  {
     numProtons = 10;

  }  // end constructor StatusDisplay


  public void update(Graphics g)
  {

  }

  public void repaint()
  {
  }

  public void paint( Graphics g )
  {
/*     setForeground(Color.white);

     Graphics2D g2D = (Graphics2D)g;
     g2D.setColor(Color.white);

     g2D.drawString("This is a new test"+numProtons, 200, 200);
*/
  } // end paint

} // end class StatusDisplay



