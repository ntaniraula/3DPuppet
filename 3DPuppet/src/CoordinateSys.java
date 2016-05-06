
import java.awt.*;
import library.*;

public class CoordinateSys 
{
	private  float rho, d, theta=0.75F, phi=0.75F, rhoMin, rhoMax,
    xMin, xMax, yMin, yMax, zMin, zMax, v11, v12, v13, v21,
    v22, v23, v32, v33, v43, xe, ye, ze, objSize;
	
	Point3D xXis,yXis,zXis,org=new Point3D(0,0,0);
	Point2D origin=new Point2D(0.0f,0.0f),xXis2d,yXis2d,zXis2d;
	boolean isSet=false;
	
	DashLine dLine=new DashLine();
	
	public void setCamera(float theta,float phi, float rho, int d)
	{
		this.rho=rho;
		this.theta=theta;
		this.phi=phi;
		this.d=d+3*phi;
	}
	private  void initPersp()
    {  
		float costh = (float)Math.cos(theta),
             sinth = (float)Math.sin(theta),
             cosph = (float)Math.cos(phi),
             sinph = (float)Math.sin(phi);
       v11 = -sinth; v12 = -cosph * costh; v13 = sinph * costh;
       v21 = costh;  v22 = -cosph * sinth; v23 = sinph * sinth;
                     v32 = sinph;          v33 = cosph;
                                           v43 = -rho;
    }
	public void zoomIn(){d+=1;}
	public void zoomOut(){d-=1;}
	public void camRight(){theta+=0.05F;}
	public void camLeft(){theta-=0.05F;}
	public void camUp(){phi-=0.05F;}
	public void camDown(){phi+=0.05F;}
	
     public Point2D worldToScreen(Point3D P)       // Called in paint method of Canvas class
     {  
    	 initPersp();
    	 Point2D vScr;
    	  float x = v11 * P.x + v21 * P.y;
          float y = v12 * P.x + v22 * P.y + v32 * P.z;
          float z = v13 * P.x + v23 * P.y + v33 * P.z + v43;
          float xScr = -d*x/z, yScr = -d*y/z;
          vScr = new Point2D(origin.x+(xScr), origin.y-(yScr));
          return vScr;
     } 
     public void worldToScreen(Point3D[] w,Point3D[] e, Point2D[] vScr)
     {    	 
    	 initPersp();
         int n = w.length;
         for (int i=0; i<n; i++)
         {  Point3D P = (Point3D)(w[i]);
            float x = v11 * P.x + v21 * P.y;
            float y = v12 * P.x + v22 * P.y + v32 * P.z;
            float z = v13 * P.x + v23 * P.y + v33 * P.z + v43;
            e[i]=new Point3D(x,y,z);
            float xScr = -d*x/z, yScr = -d*y/z;
            vScr[i] = new Point2D(origin.x+(xScr), origin.y-(yScr));
         }    	 
     }
     void setCoordinateSys(int x, int y, int z, Point2D center)
     {
    	 xXis=new Point3D(x,0,0);
    	 yXis=new Point3D(0,y,0);
    	 zXis=new Point3D(0,0,z);
    	 this.origin=new Point2D(center.x,center.y);	
    	 isSet=true;
     }
     void drawAxis(Graphics g)
     {
    	 xXis2d=worldToScreen(xXis);
    	 yXis2d=worldToScreen(yXis);
    	 zXis2d=worldToScreen(zXis);
    	 dLine.drawDLine(g, origin, xXis2d, 5,Color.red);
    	 dLine.drawDLine(g, origin, yXis2d, 5,Color.blue);
    	 dLine.drawDLine(g, origin, zXis2d, 5,Color.yellow);    	 
     }
    public int iX(float x){return Math.round(origin.x + x);}
    public int iY(float y){return Math.round(origin.y - y);}
    
}
