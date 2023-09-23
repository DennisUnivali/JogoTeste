import java.awt.Graphics2D;

public abstract class Sprite {
	float X;
	float Y;
	
	boolean vivo = true;
	
	public abstract void desenhaSe(Graphics2D dbg, int xTela,int yTela);
	public abstract void atualizeSe(long DiffTime);
}
