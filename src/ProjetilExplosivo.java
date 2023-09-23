import java.awt.Color;
import java.awt.Graphics2D;

public class ProjetilExplosivo extends Projetil {

	boolean explodindo = false;
	int timerexplodindo = 0;
	int raioExplosao = 100;
	
	public ProjetilExplosivo(TileMap mapa, float x, float y, float angulo, float velocidade, Personagem pai,
			float dano, int raioExplosao) {
		super(mapa, x, y, angulo, velocidade, pai, dano);
		// TODO Auto-generated constructor stub
		explodindo = false;
		this.raioExplosao = raioExplosao;
	}

	@Override
	public void atualizeSe(long DiffTime) {
		
		if(explodindo==false) {
			// TODO Auto-generated method stub
			X = X + velX*DiffTime/1000.0f;
			Y = Y + velY*DiffTime/1000.0f;
			
			for(int i = 0; i < GameRunCanvas.listaDePersonagem.size();i++) {
				Personagem p2 = GameRunCanvas.listaDePersonagem.get(i);
					if(colide(p2)&&p2!=pai) {
						//vivo = false;
						//p2.levaDano(dano);
						explodindo = true;
						break;
					}
			}
			
			if(X>mapa.worldW) {
				//vivo = false;
				explodindo = true;
				return;
			}
			if(X<=0) {
				//vivo = false;
				explodindo = true;
				return;
			}
			if(Y>mapa.worldH) {
				//vivo = false;
				explodindo = true;
				return;
			}
			if(Y<=0) {
				//vivo = false;
				explodindo = true;
				return;
			}
			
			int bx = (int)((X)/mapa.tilewidth); 
			int by = (int)((Y)/mapa.tileheight);
			
			if(bx < mapa.width && by < mapa.height && mapa.tilemap[1][by][bx]!=-1) {
				explodindo = true;
				//vivo = false;
			}
			
			timerRegressivoFumacao-=DiffTime;
			if(timerRegressivoFumacao<=0) {
				for(int i = 0; i < 2;i++) {
					float novoAngulo = (float)(angulo-Math.PI) - 0.2617f + GameRunCanvas.rnd.nextFloat(0.5235f);
					ParticulaImagem2 partIm = new ParticulaImagem2(mapa, X, Y, novoAngulo, 50, 400+GameRunCanvas.rnd.nextInt(200), GameRunCanvas.fumaca,GameRunCanvas.fumaca2);
					GameRunCanvas.listaParticulas.add(partIm);
				}
				timerRegressivoFumacao = 10+GameRunCanvas.rnd.nextInt(30);
			}
		}else {
			timerexplodindo+=DiffTime;
			if(timerexplodindo>500) {
				for(int i = 0; i < GameRunCanvas.listaDePersonagem.size();i++) {
					Personagem p2 = GameRunCanvas.listaDePersonagem.get(i);
						if(colideExp(p2)&&p2!=pai) {
							p2.levaDano(dano);
						}
				}
				vivo = false;
			}
		}
	}
	
	
	
	public boolean colideExp(Personagem p) {
		float dx = (p.X+p.charW/2)-X;
		float dy = (p.Y+p.charH/2)-Y;
		
		float d = dx*dx+dy*dy;
		float sraio = p.raio+raioExplosao;//raio do personagem mais raio do  tiro
		
		if(sraio*sraio>d) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void desenhaSe(Graphics2D dbg, int xTela, int yTela) {
		if(explodindo==false) {
			dbg.setColor(Color.black);
			dbg.fillRect((int)X-xTela,(int)Y-yTela, 4, 4);
		}else {
			dbg.setColor(new Color(1.0f, 0.0f, 0.0f, 0.5f));
			int expsize = (int)(((timerexplodindo/500.0))*raioExplosao);
			dbg.fillOval((int)X-xTela-expsize,(int)Y-yTela-expsize, expsize*2, expsize*2);
		}
		
	}	
}
