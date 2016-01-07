package ndcp1;

import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;

public class Bond extends MyCylinder
{
  ndcp theApp;

  int type;   // 1 = pp, 2 = nn, 3 = pn

  int p1;
  int p2;

  int n1;
  int n2;


  public Bond(ndcp theApp, int a1, int a2, int type)
  {
    super();

    switch (type)
    {
      case 1:
              setup((double)theApp.theProtons[a1].fccX,
                    (double)theApp.theProtons[a1].fccY,
                    (double)theApp.theProtons[a1].fccZ,
                    (double)theApp.theProtons[a2].fccX,
                    (double)theApp.theProtons[a2].fccY,
                    (double)theApp.theProtons[a2].fccZ,
                    (double)theApp.bondRadius);
              p1 = a1;
              p2 = a2;
              break;

      case 2:
             setup((double)theApp.theNeutrons[a1].fccX,
                   (double)theApp.theNeutrons[a1].fccY,
                   (double)theApp.theNeutrons[a1].fccZ,
                   (double)theApp.theNeutrons[a2].fccX,
                   (double)theApp.theNeutrons[a2].fccY,
                   (double)theApp.theNeutrons[a2].fccZ,
                   (double)theApp.bondRadius);
              n1 = a1;
              n2 = a2;
              break;

      case 3:
              setup((double)theApp.theProtons[a1].fccX,
                    (double)theApp.theProtons[a1].fccY,
                    (double)theApp.theProtons[a1].fccZ,
                    (double)theApp.theNeutrons[a2].fccX,
                    (double)theApp.theNeutrons[a2].fccY,
                    (double)theApp.theNeutrons[a2].fccZ,
                    (double)theApp.bondRadius);
              p1 = a1;
              n1 = a2;
              break;

      case 4:
              setup((double)theApp.theProtons[a1].scpX,
                    (double)theApp.theProtons[a1].scpY,
                    (double)theApp.theProtons[a1].scpZ,
                    (double)theApp.theProtons[a2].scpX,
                    (double)theApp.theProtons[a2].scpY,
                    (double)theApp.theProtons[a2].scpZ,
                    (double)theApp.bondRadius);
              p1 = a1;
              p2 = a2;
              break;

      case 5:
             setup((double)theApp.theNeutrons[a1].scpX,
                   (double)theApp.theNeutrons[a1].scpY,
                   (double)theApp.theNeutrons[a1].scpZ,
                   (double)theApp.theNeutrons[a2].scpX,
                   (double)theApp.theNeutrons[a2].scpY,
                   (double)theApp.theNeutrons[a2].scpZ,
                   (double)theApp.bondRadius);
              n1 = a1;
              n2 = a2;
              break;

      case 6:
              setup((double)theApp.theProtons[a1].scpX,
                    (double)theApp.theProtons[a1].scpY,
                    (double)theApp.theProtons[a1].scpZ,
                    (double)theApp.theNeutrons[a2].scpX,
                    (double)theApp.theNeutrons[a2].scpY,
                    (double)theApp.theNeutrons[a2].scpZ,
                    (double)theApp.bondRadius);
              p1 = a1;
              n1 = a2;
              break;

    } // end switch


    this.type = type;

  } // end constructor bond


  public void setRadius(double CR)
  {
    radius = CR;
  } // end setRadius

  public Appearance createAppearanceSolid()
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


} // end Bond






