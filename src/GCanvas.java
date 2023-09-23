import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

public abstract class GCanvas implements MouseWheelListener,MouseMotionListener,MouseListener,KeyListener{
	public abstract void desenhaSe(Graphics2D dbg);
	public abstract void atualizeSe(long DiffTime);
}
