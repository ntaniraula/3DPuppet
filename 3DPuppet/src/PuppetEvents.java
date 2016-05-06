import java.awt.*;
import library.*;

import library.Vector3D;

public class PuppetEvents 
{
	Puppet3D puppet;
	Graphics g;
	CvPuppet canvas;
	PuppetEvents(Puppet3D p, CvPuppet c)
	{
		this.puppet=p;	
		this.canvas=c;
	}
	public void takeSride(double length)
	{
		double lLegDist=Vector3D.distance(puppet.endPoint, puppet.lHeel);
		double rLegDist=Vector3D.distance(puppet.endPoint, puppet.rHeel);
		if(lLegDist==rLegDist)moveLeg(puppet.lHeel,length/2);
		else if(lLegDist>rLegDist)moveLeg(puppet.lHeel,length);
		else if(rLegDist>lLegDist) moveLeg(puppet.rHeel,length);
	}
	private void moveLeg(Point3D heel, double strideLen)
	{
		double base=heel.z;												//protect the base
		double dx=strideLen/9;
		//main loop that creates movement and animation
		for(int i=0;i<=8;i++)
		{					
			heel.x+=puppet.direction.x*dx;		//displace heel.x 
			heel.y+=puppet.direction.y*dx;
			
			//displace heel.y, for simulation we moved heel in a half sin wave pattern
			heel.z=Math.round((base+(Math.round((Math.abs(Math.sin(i*9))*5)))));
			
			canvas.paintScene(); 				//draw the puppet with changed parameters
			
			//Thread sleep , to allow the animation to be seen
			try	{Thread.currentThread().sleep(100);	}catch(Exception e){}			
		}
		heel.z=(float)base;
		canvas.paintScene();
	}
	public void standStill()
	{
		
	}
}
