import java.awt.*;
import java.util.*;
import library.*;

public class Puppet3D 
{
	public Point3D startPoint, endPoint,CG;
	CoordinateSys cordSys;
	Vector2D direction;
	Point3D hip,lHeel, rHeel,lShoulder,rShoulder, lFist, rFist;
	int height;
	
	double maxStrideLength;
	
	Point3D[] w=new Point3D[17];
	Point3D[] e=new Point3D[17];
	Point2D[] vScr=new Point2D[17];
	Vector polyList=new Vector();
	Color[] color=new Color[7];
	
	Puppet3D(CoordinateSys cordinateSys)
	{
		this.cordSys=cordinateSys;
		startPoint=new Point3D();
		endPoint=new Point3D();
		setColors();
	}
	
	//This function sets up the primary functional points on first creation of puppet
	public void makePuppet(int height)
	{
		this.height=height;
		direction = Vector2D.findUnitVector(new Point2D(startPoint.x,startPoint.y), new Point2D(endPoint.x,endPoint.y));
		this.hip=new Point3D(this.startPoint.x,this.startPoint.y,0.5*height);
		this.lHeel=Poly.findPolygonPoint(startPoint, 0.05f*height, direction, Vector2D.CCW);
		this.rHeel=Poly.findPolygonPoint(startPoint, 0.05f*height, direction, Vector2D.CW);
		this.lShoulder=Poly.findPolygonPoint(new Point3D(startPoint.x, startPoint.y, 0.9*height), 0.06f*height, direction, Vector2D.CCW);
		this.rShoulder=Poly.findPolygonPoint(new Point3D(startPoint.x, startPoint.y, 0.9*height), 0.06f*height, direction, Vector2D.CW);
		this.maxStrideLength=0.3f*height;
		this.CG=findCGPoint();
		
	}
	private Point3D findCGPoint()
	{
		float x,y,z;
		x=(lHeel.x + rHeel.x)/2;
		y=(lHeel.y+rHeel.y)/2;
		z=0;
		return new Point3D(x,y,z);
	}
	
	//This function sets up the points each time before drawing puppet
	public void preparePuppet()
	{
		this.CG=findCGPoint();
		this.hip=new Point3D(CG.x,CG.y,CG.z+(0.5f*height));
		this.lShoulder=Poly.findPolygonPoint(new Point3D(CG.x, CG.y, 0.9*height), 0.06f*height, direction, Vector2D.CCW);
		this.rShoulder=Poly.findPolygonPoint(new Point3D(CG.x, CG.y, 0.9*height), 0.06f*height, direction, Vector2D.CW);
		w[0]=new Point3D(rHeel.x,rHeel.y,rHeel.z); 	//right heel
		w[1]=new Point3D(lHeel.x,lHeel.y,lHeel.z);	//left heel
		w[2]=new Point3D(hip.x,hip.y,hip.z);		//hip
		w[3]=Poly.findPolygonPoint(hip, 0.05f*height, direction, Vector2D.CW); //right hip joint
		w[4]=Poly.findPolygonPoint(hip, 0.05f*height, direction, Vector2D.CCW); //left hip joint
		w[5]=findKnee(hip, rHeel);		//right knee
		w[6]=findKnee(hip,lHeel);		//left knee
		w[7]=new Point3D(rShoulder.x,rShoulder.y,rShoulder.z);	//right shoulder
		w[8]=new Point3D(lShoulder.x,lShoulder.y,lShoulder.z);	//left shoulder
		w[9]=Poly.findPolygonPoint(new Point3D(hip.x,hip.y,0.7*height), 0.03f*height, direction, Vector2D.CW);	//right waist
		w[10]=Poly.findPolygonPoint(new Point3D(hip.x,hip.y,0.7*height), 0.03f*height, direction, Vector2D.CCW);	//left waist
		w[11]=Poly.offset(w[3], 0.1f, direction, 1);
		w[12]=Poly.offset(w[4], 0.1f, direction, 1);
		w[13]=Poly.offset(w[9], 0.1f, direction, 1);
		w[14]=Poly.offset(w[10], 0.1f, direction, 1);
		w[15]=Poly.offset(w[7], 0.1f, direction, 1);
		w[16]=Poly.offset(w[8], 0.1f, direction, 1);
		polyList.clear();
		//buildLegs();
		//buildBody();
		//buildHands();
		//buildHead();
		cordSys.worldToScreen(w,e,vScr);
	}
	private void buildLegs()
	{
		Vector vnrs=new Vector();
		vnrs.addElement(0);vnrs.addElement(5);vnrs.addElement(0);
		polyList.addElement(vnrs);
		vnrs.clear();
		vnrs.addElement(5);vnrs.addElement(3);vnrs.addElement(5);
		polyList.addElement(vnrs);
		vnrs.clear();
		vnrs.addElement(1);vnrs.addElement(6);vnrs.addElement(1);
		polyList.addElement(vnrs);
		vnrs.clear();
		vnrs.addElement(6);vnrs.addElement(4);vnrs.addElement(6);
		polyList.addElement(vnrs);		
	}
	private void buildBody()
	{
		Vector vnrs=new Vector();
		vnrs.addElement(15);vnrs.addElement(13);vnrs.addElement(11);
		vnrs.addElement(12);vnrs.addElement(14);vnrs.addElement(16);
		polyList.addElement(vnrs);
		vnrs.clear();
		vnrs.addElement(4);vnrs.addElement(10);vnrs.addElement(8);
		vnrs.addElement(7);vnrs.addElement(9);vnrs.addElement(3);
		polyList.addElement(vnrs);			
		
		System.out.print("@"+polyList.size()+"\n");
	}
	private void drawLine(Graphics g, Point2D A, Point2D B,Color color)
	{
		g.setColor(color);
		Point a=new Point(Math.round(A.x),Math.round(A.y));
		Point b=new Point(Math.round(B.x),Math.round(B.y));
		g.drawLine(a.x, a.y, b.x, b.y);
	}
	public void drawPuppet(Graphics g)
	{
		direction = Vector2D.findUnitVector(new Point2D(startPoint.x,startPoint.y), new Point2D(endPoint.x,endPoint.y));
		Point2D stPt=cordSys.worldToScreen(startPoint);
		Point2D endPt=cordSys.worldToScreen(endPoint);
		drawLine(g, stPt, endPt, Color.black);
		preparePuppet();
//		Point2D[] p;
//		for(int i=0;i<polyList.size();i++)
//		{
//			Polygon poly=new Polygon();
//			Vector v=(Vector)polyList.elementAt(i);
//			p=new Point2D[v.size()];
//			for(int j=0;j<v.size();j++)
//			{
//				p[j]=vScr[(Integer)v.elementAt(j)];
//				poly.addPoint(cordSys.iX(p[j].x), cordSys.iY(p[j].y));
//			}
//			g.setColor(color[i]);
//			System.out.print("$$" +i+ " : "+ Tools2D.area2(p[0], p[1], p[2])+ "\n");
//			if ( p.length < 4 || Tools2D.area2(p[0], p[1], p[2]) > 0  )
//				g.fillPolygon(poly);
//		}
		
//		preparePuppet();
		drawLine(g, vScr[0], vScr[5], color[0]);
		drawLine(g, vScr[1], vScr[6], color[0]);
		drawLine(g, vScr[5], vScr[3], color[0]);
		drawLine(g, vScr[6], vScr[4], color[0]);
		Polygon body = new Polygon();
		body.addPoint(vScr[3].x,vScr[3].y);
		body.addPoint(vScr[9].x,vScr[9].y);
		body.addPoint(vScr[7].x,vScr[7].y);
		body.addPoint(vScr[8].x,vScr[8].y);
		body.addPoint(vScr[10].x,vScr[10].y);
		body.addPoint(vScr[4].x,vScr[4].y);
		body.addPoint(vScr[3].x,vScr[3].y);
		g.setColor(Color.red);
		g.fillPolygon(body);
	}
	
	//Find knee function takes hip and heel to calculate the knee
	private Point3D findKnee(Point3D hip,Point3D heel)
	{
		Point3D knee=new Point3D();
		double x,z,h,s,piece,length;
		length=this.height*0.5f;
		piece=length/2;
		h=Vector3D.distance(hip,heel);
		z=h/2;
		s=(length+h)/2;
		x=Math.max(1,((2.0f/(float)h)*((float)Math.sqrt(Math.abs(s*(s-piece)*(s-piece)*(s-h))))));
		knee.x=heel.x +Math.round(direction.x*x);
		knee.y=heel.y+Math.round(direction.y*x);
		knee.z=(float)(heel.z+z);
		return knee;
	}
	private void setColors()
	{
		color[0]=Color.black; color[1]=Color.black;
		color[2]=Color.black;color[3]=Color.black;
		color[4]=Color.blue;
		color[5]=Color.red;
	}
}
