import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.*;
import javax.imageio.ImageIO;


public class GamePanel extends JPanel implements Runnable
{
	
public static GamePanel intace;
	
private Thread animator;
private boolean running = false;
private boolean gameOver = false; 

private BufferedImage dbImage;
private Graphics2D dbg;

public GCanvas canvasAtivo;

public GamePanel()
{
	intace = this;

	//canvasAtivo = new GameRunCanvas();
	canvasAtivo = new CanvasAbertura();
	
	setBackground(Color.white);
	setPreferredSize( new Dimension(Constantes.PWIDTH, Constantes.PHEIGHT));

	// create game components
	setFocusable(true);

	requestFocus(); // JPanel now receives key events
	
	if (dbImage == null){
		dbImage = new BufferedImage(Constantes.PWIDTH, Constantes.PHEIGHT,BufferedImage.TYPE_4BYTE_ABGR);
		if (dbImage == null) {
			System.out.println("dbImage is null");
			return;
		}else{
			dbg = (Graphics2D)dbImage.getGraphics();
		}
	}	
	
//	try {
//		chara1 = ImageIO.read(this.getClass().getResourceAsStream("Chara1.png"));
//		System.out.println("chara1 -> "+chara1.getColorModel());
//		//fundo = ImageIO.read(this.getClass().getResourceAsStream("fundo.png"));
//	} catch (IOException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
	

	
	
	
	// Adiciona um Key Listner
	addKeyListener(new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			canvasAtivo.keyTyped(e);
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			canvasAtivo.keyReleased(e);
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			canvasAtivo.keyPressed(e);
		}
	});
	
	addMouseMotionListener(new MouseMotionListener() {
		
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			canvasAtivo.mouseMoved(e);
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			canvasAtivo.mouseDragged(e);
		}
	});
	
	addMouseListener(new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			canvasAtivo.mouseReleased(e);
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			canvasAtivo.mousePressed(e);
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			canvasAtivo.mouseExited(e);
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			canvasAtivo.mouseEntered(e);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			canvasAtivo.mouseClicked(e);
		}
	});
	
	addMouseWheelListener(new MouseWheelListener() {
		
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			canvasAtivo.mouseWheelMoved(e);
		}
	});
	
} // end of GamePanel()

public void switchCanvas(GCanvas newCanvas) {
	canvasAtivo = newCanvas;
}

public void addNotify()
{
	super.addNotify(); // creates the peer
	startGame(); // start the thread
}

private void startGame()
// initialise and start the thread
{
	if (animator == null || !running) {
		animator = new Thread(this);
		animator.start();
	}
} // end of startGame()

public void stopGame()
// called by the user to stop execution
{ running = false; }


public void run()
/* Repeatedly update, render, sleep */
{
	running = true;
	
	long DifTime,TempoAnterior;
	
	int segundo = 0;
	DifTime = 0;
	TempoAnterior = System.currentTimeMillis();
	
	while(running) {
	
		gameUpdate(DifTime); // game state is updated
		gameRender(); // render to a buffer
		paintImmediately(0, 0, Constantes.PWIDTH, Constantes.PHEIGHT); // paint with the buffer
	
		try {
			Thread.sleep(0); // sleep a bit
		}	
		catch(InterruptedException ex){}
		
		DifTime = System.currentTimeMillis() - TempoAnterior;
		TempoAnterior = System.currentTimeMillis();
		
		if(segundo!=((int)(TempoAnterior/1000))){
			Constantes.FPS = Constantes.SFPS;
			Constantes.SFPS = 1;
			segundo = ((int)(TempoAnterior/1000));
		}else{
			Constantes.SFPS++;
		}
	
	}
System.exit(0); // so enclosing JFrame/JApplet exits
} // end of run()

int timerfps = 0;

private void gameUpdate(long DiffTime)
{
	canvasAtivo.atualizeSe(DiffTime);
}



private void gameRender()
// draw the current frame to an image buffer
{
	canvasAtivo.desenhaSe(dbg);
}


public void paintComponent(Graphics g)
{
	super.paintComponent(g);
	if (dbImage != null)
		g.drawImage(dbImage, 0, 0, null);
}


public static void main(String args[])
{
	GamePanel ttPanel = new GamePanel();

  // create a JFrame to hold the timer test JPanel
  JFrame app = new JFrame("Swing Timer Test");
  app.getContentPane().add(ttPanel, BorderLayout.CENTER);
  app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  app.pack();
  app.setResizable(false);  
  app.setVisible(true);
} // end of main()

} // end of GamePanel class

