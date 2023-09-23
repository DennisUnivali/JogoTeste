import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ParticulaImagem extends Particula {

	BufferedImage imagem = null;
	
	public ParticulaImagem(TileMap mapa, float x, float y, float angulo, float velocidade, int tempoDeVida,BufferedImage imagem) {
		super(mapa, x, y, angulo, velocidade, tempoDeVida);
		
		this.imagem = imagem;
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
		
		dbg.drawImage(imagem, -imagem.getWidth()/2,-imagem.getHeight()/2, imagem.getWidth(), imagem.getHeight(), null);

		
		dbg.setComposite(comp);
		dbg.setTransform(trans);
	}

}
