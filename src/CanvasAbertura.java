import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class CanvasAbertura extends GCanvas {

	public Font font01 = new Font(null, Font.BOLD, 40);
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		Constantes.gameruncanvas = new GameRunCanvas();
		GamePanel.intace.switchCanvas(Constantes.gameruncanvas);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void desenhaSe(Graphics2D dbg) {
		// TODO Auto-generated method stub
		dbg.setColor(Color.GRAY);
		dbg.fillRect(0, 0, Constantes.PWIDTH, Constantes.PHEIGHT);
		
		dbg.setFont(font01);
		dbg.setColor(Color.white);
		dbg.drawString("PRESS ANY KEY", 200, 350);
		
	}

	@Override
	public void atualizeSe(long DiffTime) {
	
	}

}
