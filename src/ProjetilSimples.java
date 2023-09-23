
public class ProjetilSimples extends Projetil {

	public ProjetilSimples(TileMap mapa, float x, float y, float angulo, float velocidade, Personagem pai, float dano) {
		super(mapa, x, y, angulo, velocidade, pai, dano);
		// TODO Auto-generated constructor stub
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
	}
}
