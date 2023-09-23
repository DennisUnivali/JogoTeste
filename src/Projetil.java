import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;

public class Projetil extends Sprite {
	float velX = 0;
	float velY = 0;
	
	float angulo = 0;
	float velocidae = 100;
	
	Personagem pai;
	float dano = 4;
	TileMap mapa;
	
	int timerRegressivoFumacao = 20;
	
	public Projetil(TileMap mapa,float x,float y,float angulo,float velocidade,Personagem pai, float dano) {
		this.X = x;
		this.Y = y;
		this.angulo = angulo;
		this.velocidae = velocidade;
		this.pai = pai;
		this.dano = dano;
		this.mapa = mapa;
		
		velX = (float)(velocidade*Math.cos(angulo));
		velY = (float)(velocidade*Math.sin(angulo));
	}
	
	@Override
	public void desenhaSe(Graphics2D dbg, int xTela, int yTela) {
		

		dbg.setColor(Color.black);
		dbg.fillRect((int)X-xTela,(int)Y-yTela, 4, 4);
		

	}

	@Override
	public void atualizeSe(long DiffTime) {
		// TODO Auto-generated method stub
		X = X + velX*DiffTime/1000.0f;
		Y = Y + velY*DiffTime/1000.0f;
		
		for(int i = 0; i < GameRunCanvas.listaDePersonagem.size();i++) {
			Personagem p2 = GameRunCanvas.listaDePersonagem.get(i);
				if(colide(p2)&&p2!=pai) {
					vivo = false;
					p2.levaDano(dano);
					
					float angBase = (float)(angulo-Math.PI);
					
					for(int j = 0; j < 100;j++) {
						float novoAngulo = angBase - 0.2617f + GameRunCanvas.rnd.nextFloat(0.5235f);
						float vel = (velocidae/2) - 200 + GameRunCanvas.rnd.nextInt(400);
						int tvida = 10 + GameRunCanvas.rnd.nextInt(200);
						Particula sangue = new Particula(mapa, X, Y, novoAngulo, vel, tvida);
						GameRunCanvas.listaParticulas.add(sangue);
					}
					break;
				}
		}
		
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
		}
		
//		timerRegressivoFumacao-=DiffTime;
//		if(timerRegressivoFumacao<=0) {
//			for(int i = 0; i < 3;i++) {
//				float novoAngulo = (float)(angulo-Math.PI) - 0.2617f + GamePanel.rnd.nextFloat(0.5235f);
//				ParticulaImagem2 partIm = new ParticulaImagem2(mapa, X, Y, novoAngulo, 50, 800+GamePanel.rnd.nextInt(400), GamePanel.fumaca,GamePanel.fumaca2);
//				GamePanel.listaParticulas.add(partIm);
//			}
//			timerRegressivoFumacao = 10+GamePanel.rnd.nextInt(30);
//		}
	}
	
	public boolean colide(Personagem p) {
		float dx = (p.X+p.charW/2)-X;
		float dy = (p.Y+p.charH/2)-Y;
		
		float d = dx*dx+dy*dy;
		float sraio = p.raio+1;//raio do personagem mais raio do  tiro
		
		if(sraio*sraio>d) {
			return true;
		}else {
			return false;
		}
	}

}
