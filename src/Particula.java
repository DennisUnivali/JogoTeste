import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;

public class Particula extends Sprite {
	float velX = 0;
	float velY = 0;
	
	float angulo = 0;
	float velocidae = 100;
	
	TileMap mapa;
	
	int tempoDeVida;
	int tempoDeVidaInicial;
	
	int size = 10;
	Color cor = Color.red;
	
	public Particula(TileMap mapa,float x,float y,float angulo,float velocidade,int tempoDeVida) {
		this.X = x;
		this.Y = y;
		this.angulo = angulo;
		this.velocidae = velocidade;

		this.mapa = mapa;
		this.tempoDeVida = tempoDeVida;
		this.tempoDeVidaInicial = tempoDeVida;
		
		velX = (float)(velocidade*Math.cos(angulo));
		velY = (float)(velocidade*Math.sin(angulo));
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
		dbg.setColor(cor);
		dbg.fillRect((int)X-xTela-size/2,(int)Y-yTela-size/2, size, size);
		
		dbg.setComposite(comp);
	}

	@Override
	public void atualizeSe(long DiffTime) {
		// TODO Auto-generated method stub
		X = X + velX*DiffTime/1000.0f;
		Y = Y + velY*DiffTime/1000.0f;
		
		tempoDeVida -=DiffTime;
		
		if(tempoDeVida<=0) {
			vivo = false;
		}
		
		/*
		if(X>mapa.worldW) {
			vivo = false;
			return;
		}
		if(X<=0) {
			vivo = false;
			return;
		}
		if(Y>mapa.worldH) {
			vivo = false;
			return;
		}
		if(Y<=0) {
			vivo = false;
			return;
		}
		
		int bx = (int)((X)/mapa.tilewidth); 
		int by = (int)((Y)/mapa.tileheight);
		
		if(bx < mapa.width && by < mapa.height && mapa.tilemap[1][by][bx]!=-1) {
			vivo = false;
		}*/
	}
	
	/*public boolean colide(Personagem p) {
		float dx = (p.X+p.charW/2)-X;
		float dy = (p.Y+p.charH/2)-Y;
		
		float d = dx*dx+dy*dy;
		float sraio = p.raio+1;//raio do personagem mais raio do  tiro
		
		if(sraio*sraio>d) {
			return true;
		}else {
			return false;
		}
	}*/

}
