import java.awt.Color;
import java.awt.Graphics2D;

public class ItemMapa extends Sprite {

	@Override
	public void desenhaSe(Graphics2D dbg, int xTela, int yTela) {
		// TODO Auto-generated method stub
		dbg.setColor(Color.DARK_GRAY);
		dbg.fillRect((int)X-xTela,(int)Y-yTela, 16, 16);
	}

	@Override
	public void atualizeSe(long DiffTime) {
		// TODO Auto-generated method stub

	}
	
	public boolean colideRet(Personagem p2) {
		if(!(p2.X > (X+16)|| (p2.X+p2.charW) < X)&&!(p2.Y > (Y+16)|| (p2.Y+p2.charH) < Y)) {
			return true;
		}
		
		return false;
	}

}
