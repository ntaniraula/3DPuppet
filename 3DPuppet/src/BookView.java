// BookView.java: Generating a data file for an open book.
import java.io.*;

public class BookView
{  public static void main(String[] args)
      throws IOException
   {  
//      if (args.length != 4)
//      {  System.out.println(
//         "Supply nr of sheets, width, height and file name\n"+
//         "as program arguments.");
//         System.exit(1);
//       }
       int n;
       float w, h;
       FileWriter fw;
       n = Integer.valueOf(15).intValue();
       w = Float.valueOf(20).floatValue();
       h = Float.valueOf(10).floatValue();
       fw = new FileWriter("book.dat");
       int spineTop = 1, spineBottom = 2, outerTop, outerBottom;
       float theta = (float)Math.PI/(n - 1);
       float xTop = 0, xBottom = h;
       fw.write(spineTop +""+xTop+"00\r\n");
       fw.write(spineBottom +""+ xBottom + " 0 0\r\n");
       for (int i=0; i<n; i++)
       {  float phi=i* theta,
             y = w * (float)Math.cos(phi),
             z = w * (float)Math.sin(phi);
          outerTop = 2 * i + 3; outerBottom = outerTop + 1;
          fw.write(outerTop + " " + xTop + " " +
             y + " " + z + "\r\n");
          fw.write(outerBottom + " " + xBottom + " " +
             y + " " + z + "\r\n");
       }
       fw.write("Faces:\r\n");
       for (int i=0; i<n; i++)
       {  outerTop =2*i+3; outerBottom = outerTop + 1;
          fw.write(spineTop + " " + spineBottom + " "
             + outerBottom + " " + outerTop + ".\r\n");
          fw.write(spineTop + " " + outerTop + " "
             + outerBottom + " " + spineBottom + ".\r\n");
       }
       fw.close();
   }
}

