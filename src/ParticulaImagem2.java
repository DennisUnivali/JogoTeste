import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ParticulaImagem2 extends Particula {

	BufferedImage imagem1 = null;
	BufferedImage imagem2 = null;
	
	public ParticulaImagem2(TileMap mapa, float x, float y, float angulo, float velocidade, int tempoDeVida,BufferedImage imagem1,BufferedImage imagem2) {
		super(mapa, x, y, angulo, velocidade, tempoDeVida);
		
		this.imagem1 = imagem1;
		this.imagem2 = imagem2;
	}
	
	@Override
	public void desenhaSe(Graphics2D dbg, int xTela, int yTela) {
		Composite comp = dbg.getComposite();
		
		float prop = tempoDeVida/(float)tempoDeVidaInicial;
		if(prop<=0) {
			prop = 0;
		}
		
		AlphaComposite acomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f+0.5f*prop);
		
		dbg.setComposite(acomp);
		
		AffineTransform trans = dbg.getTransform();
		
		
		dbg.translate((int)X-xTela, (int)Y-yTela);

		dbg.scale(0.2+(1-prop)*3, 0.1+(1-prop)*3);
		
		if(prop>0.7) {
			dbg.drawImage(imagem2, -imagem2.getWidth()/2,-imagem2.getHeight()/2, imagem2.getWidth(), imagem2.getHeight(), null);
		}else {
			dbg.drawImage(imagem1, -imagem1.getWidth()/2,-imagem1.getHeight()/2, imagem1.getWidth(), imagem1.getHeight(), null);
		}
		
		dbg.setComposite(comp);
		dbg.setTransform(trans);
	}

}
