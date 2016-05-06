// Wireframe.java: Perspective drawing using an input file that lists
//    vertices and faces.
// Uses: Point2D (Section 1.5),
//       Triangle, Tools2D (Section 2.13),
//       Point3D (Section 3.9),
//       Input, Obj3D, Tria, Polygon3D, Canvas3D, Fr3D (Section 5.5),
//       CvWireframe (Section 5.6).
import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import library.Point2D;
import library.Point3D;

public class Wireframe extends Frame
{  public static void main(String[] args)
   {  new Fr3D(args.length  > 0 ? args[0] : null, new CvWireframe(),
        "Wire-frame model");
   }
}

//CvWireframe.java: Canvas class for class Wireframe.


class CvWireframe extends Canvas3D
{  private int maxX, maxY, centerX, centerY;
   private Obj3D obj;
   private Point2D imgCenter;

   Obj3D getObj(){return obj;}
   void setObj(Obj3D obj){this.obj = obj;}
   int iX(float x){return Math.round(centerX + x - imgCenter.x);}
   int iY(float y){return Math.round(centerY - y + imgCenter.y);}

   public void paint(Graphics g)
   {  if (obj == null) return;
      Vector polyList = obj.getPolyList();
      if (polyList == null) return;
      int nFaces = polyList.size();
      if (nFaces == 0) return;
      Dimension dim = getSize();
      maxX = dim.width - 1; maxY = dim.height - 1;
      centerX = maxX/2; centerY = maxY/2;
      // ze-axis towards eye, so ze-coordinates of
      // object points are all negative.
      // obj is a java object that contains all data:
      // - Vector w       (world coordinates)
      // - Array e        (eye coordinates)
      // - Array vScr     (screen coordinates)
      // - Vector polyList (Polygon3D objects)

      // Every Polygon3D value contains:
      // - Array 'nrs' for vertex numbers
      // - Values a, b, c, h for the plane ax+by+cz=h.
      // (- Array t (with nrs.length-2 elements of type Tria))

      obj.eyeAndScreen(dim);
            // Computation of eye and screen coordinates.

      imgCenter = obj.getImgCenter();
      obj.planeCoeff();    // Compute a, b, c and h.
      Point3D[] e = obj.getE();
      Point2D[] vScr = obj.getVScr();

      g.setColor(Color.black);

      for (int j=0; j<nFaces; j++)
      {  Polygon3D pol = (Polygon3D)(polyList.elementAt(j));
         int nrs[] = pol.getNrs();
         if (nrs.length < 3)
            continue;
         for (int iA=0; iA<nrs.length; iA++)
         {  int iB = (iA + 1) % nrs.length;
            int na = Math.abs(nrs[iA]), nb = Math.abs(nrs[iB]);
            // abs in view of minus signs discussed in Section 6.4.
            Point2D a = vScr[na], b = vScr[nb];
            g.drawLine(iX(a.x), iY(a.y), iX(b.x), iY(b.y));
         }
      }
   }
}

