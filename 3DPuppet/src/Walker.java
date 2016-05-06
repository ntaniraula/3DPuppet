import java.awt.*;

import library.Vector3D;

public class Walker implements Runnable
{
	Puppet3D puppet;
	PuppetEvents pEvents;
	CvPuppet canvas;
	Walker(Puppet3D p, CvPuppet canvas)
	{
		this.puppet=p;
		this.canvas=canvas;
		pEvents=new PuppetEvents(p, canvas);
	}

	public void run() 
	{
		while(Vector3D.distance(puppet.endPoint, puppet.CG)>1)
		{
			double dist=Math.max(Vector3D.distance(puppet.endPoint, puppet.lHeel), Vector3D.distance(puppet.endPoint, puppet.rHeel));
			if(dist>7)
			{
				if(dist>puppet.maxStrideLength)
						pEvents.takeSride(puppet.maxStrideLength);
				else pEvents.takeSride(dist);
			}
			else pEvents.standStill();
		}
		pEvents.standStill();
	}
}
