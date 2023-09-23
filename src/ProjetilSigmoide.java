
public class ProjetilSigmoide extends Projetil {

	float v1x = 0; 
	float v1y = 0;
	
	float v2x = 0; 
	float v2y = 0;
	
	float centerX = 0;
	float centerY = 0;
	
	int timerPasso = 0;
	
	public ProjetilSigmoide(TileMap mapa, float x, float y, float angulo, float velocidade, Personagem pai,
			float dano) {
	
		super(mapa, x, y, angulo, velocidade, pai, dano);
		// TODO Auto-generated constructor stub
		
		v1x = (float)Math.cos(angulo);
		v1y = (float)Math.sin(angulo);
		
		v2x = (float)Math.cos(angulo+Math.PI/2);
		v2y = (float)Math.sin(angulo+Math.PI/2);
		
		centerX = x;
		centerY = y;
	}
	
	@Override
	public void atualizeSe(long DiffTime) {
		
		timerPasso+=DiffTime;
		
		// TODO Auto-generated method stub
		centerX = centerX + velX*DiffTime/1000.0f;
		centerY = centerY + velY*DiffTime/1000.0f;
		
		X = (float)(centerX + v2x*20*Math.sin(timerPasso/40.0)); 
		Y = (float)(centerY + v2y*20*Math.sin(timerPasso/40.0)); 	
		
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
		
		timerRegressivoFumacao-=DiffTime;
		if(timerRegressivoFumacao<=0) {
			for(int i = 0; i < 3;i++) {
				float novoAngulo = (float)(angulo-Math.PI) - 0.2617f + GameRunCanvas.rnd.nextFloat(0.5235f);
				ParticulaImagem2 partIm = new ParticulaImagem2(mapa, X, Y, novoAngulo, 50, 800+GameRunCanvas.rnd.nextInt(400), GameRunCanvas.fumaca,GameRunCanvas.fumaca2);
				GameRunCanvas.listaParticulas.add(partIm);
			}
			timerRegressivoFumacao = 10+GameRunCanvas.rnd.nextInt(30);
		}
	}

}
