import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import library.*;

import library.DashLine;

public class Puppet3DTest extends Frame
{
	public static void main(String[] args){	new Puppet3DTest();}
	Puppet3DTest()
	{
		 super("Puppet 3D");
	      addWindowListener(new WindowAdapter()
	         {public void windowClosing(WindowEvent e){System.exit(0);}});
	      
	      Dimension dim = getToolkit().getScreenSize();
	      setSize(dim.width, dim.height);
	      setLocation(0, 0);
	      add("Center", new CvPuppet());
	      show();
	}

}
class CvPuppet extends Canvas implements MouseListener,KeyListener,MouseMotionListener,MouseWheelListener
{
	CoordinateSys cordSys;
	Puppet3D puppet;
	Walker walker;
	Graphics g;
	BufferedImage image;
	Graphics imgGraphics;
	int countClick=0;
	Dimension dimScene;
	boolean puppetSet=false;
	boolean panScreen=false;
	CvPuppet()
	{
		cordSys=new CoordinateSys();
		puppet=new Puppet3D(cordSys);
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		repaint();
	}
	public void paint(Graphics g)
	{
		dimScene=new Dimension(this.getSize().width,this.getSize().height);
		image=new BufferedImage(dimScene.width,dimScene.height-40,BufferedImage.TYPE_INT_RGB);
		imgGraphics=image.getGraphics();
		this.g=this.getGraphics();
	}
	public void paintScene()
	{
		imgGraphics.setColor(Color.white);
		imgGraphics.fillRect(0, 0, dimScene.width, dimScene.height);
		if(cordSys.isSet)cordSys.drawAxis(imgGraphics);
		if(puppetSet)puppet.drawPuppet(imgGraphics);
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), this);
	}
	
	public void mouseClicked(MouseEvent evt) 
	{
		if(!panScreen)
		{
			if(countClick==0)
			{
				cordSys.setCamera(0.75F,0.75F, 200,200);
				cordSys.setCoordinateSys(200, 200, 200, new Point2D(evt.getX(),evt.getY()));
			}
			else if(countClick==1)
			{
				puppet.startPoint.x=Vector3D.mapTo3D(cordSys.origin,cordSys.org, cordSys.xXis2d,cordSys.xXis, new Point2D(evt.getX(),evt.getY()));
			}
			else if(countClick==2)
			{
				puppet.startPoint.y=Vector3D.mapTo3D(cordSys.origin,cordSys.org, cordSys.yXis2d,cordSys.xXis, new Point2D(evt.getX(),evt.getY()));
			}
			else if(countClick==3)
			{
				puppet.endPoint.x=Vector3D.mapTo3D(cordSys.origin,cordSys.org, cordSys.xXis2d,cordSys.xXis, new Point2D(evt.getX(),evt.getY()));
			}
			else if(countClick==4)
			{
				puppet.endPoint.y=Vector3D.mapTo3D(cordSys.origin,cordSys.org, cordSys.yXis2d,cordSys.xXis, new Point2D(evt.getX(),evt.getY()));
			}
			else if(countClick==5)
			{
				puppet.makePuppet(50);
				walker=new Walker(puppet,this);
				Thread t=new Thread(walker);
				t.start();			
				puppetSet=true;
			}
			countClick++;
			paintScene();
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent evt)
	{
		if(evt.getKeyCode()==KeyEvent.VK_UP)
			cordSys.camUp();
		else if(evt.getKeyCode()==KeyEvent.VK_DOWN)
			cordSys.camDown();
		else if(evt.getKeyCode()==KeyEvent.VK_LEFT)
			cordSys.camLeft();
		else if(evt.getKeyCode()==KeyEvent.VK_RIGHT)
			cordSys.camRight();
		paintScene();
		if(evt.getKeyCode()==KeyEvent.VK_SPACE)
			panScreen=!panScreen;
			
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		//panScreen=false;
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		if(panScreen==true)
		{
			cordSys.origin=new Point2D(e.getX(),e.getY());
			paintScene();
		}
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent evt) 
	{
		if(evt.getWheelRotation()>0)cordSys.zoomOut();
		else if(evt.getWheelRotation()<0)cordSys.zoomIn();
		paintScene();
	}
}
