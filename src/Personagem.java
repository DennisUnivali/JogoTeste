import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Personagem extends Sprite {
	
	float velX = 0;
	float velY = 0;
	TileMap mapa = null;
	BufferedImage charset;
	
	int collFrame = 0;
	int linhaAnim = 0;
	int animationTimer = 0;
	int tempoEntreFrames = 100;
	
	int charW = 24;
	int charH = 32;
	
	//72x128
	int charaX = 0;
	int charaY = 0;
	
	int raio = 12;
	
	float vida = 100;
	float vidaMaxima = 100;
	
	public Personagem(int x,int y,TileMap mapa,BufferedImage charset) {
		this.mapa = mapa;
		X = x;
		Y = y;
		this.charset = charset;
		
		vida = 200;
		vidaMaxima = 200;
	}

	@Override
	public void desenhaSe(Graphics2D dbg, int xTela, int yTela) {
		dbg.drawImage(charset, (int)X-xTela,(int)Y-yTela,(int)X+charW-xTela,(int)Y+charH-yTela,charaX+ charW*collFrame,charaY+charH*linhaAnim,charaX+ charW*collFrame+charW,charaY+charH*linhaAnim+charH,null);
		
		dbg.setColor(Color.yellow);
		dbg.drawRect((int)X-xTela,(int)Y-yTela, charW, charH);
		
		dbg.setColor(Color.red);
		//dbg.drawRect((int)X-xTela+charW/2,(int)Y-yTela+charH/2, charW, charH);
		dbg.drawOval((int)X-xTela+charW/2-raio,(int)Y-yTela+charH/2-raio,2*raio,2*raio);
		
		
		dbg.setColor(Color.red);
		dbg.fillRect((int)X-xTela,(int)Y-yTela-10, 25, 5);
		
		int barlife = (int)((vida/(float)vidaMaxima)*25);
		
		dbg.setColor(Color.green);
		dbg.fillRect((int)X-xTela+25-barlife,(int)Y-yTela-10, barlife, 5);
	}

	@Override
	public void atualizeSe(long DiffTime) {
		animationTimer+=DiffTime;
		collFrame = (animationTimer/tempoEntreFrames)%3;
		
		float oldX = X;
		float oldY = Y;
		
		X = X + velX*DiffTime/1000.0f;
		Y = Y + velY*DiffTime/1000.0f;
		
		
		boolean colidiu = false;
		if(X>mapa.worldW-charW) {
			colidiu = true;
		}
		if(X<=0) {
			colidiu = true;
		}
		if(Y>mapa.worldH-charH) {
			colidiu = true;
		}
		if(Y<=0) {
			colidiu = true;
		}
		
		int bx = (int)((X+12)/mapa.tilewidth); 
		int by = (int)((Y+24)/mapa.tileheight);
		
		if(bx < mapa.width && by < mapa.height && mapa.tilemap[1][by][bx]!=-1) {
			colidiu = true;
		}
		
		for(int i = 0; i < GameRunCanvas.listaDePersonagem.size();i++) {
			Personagem p2 = GameRunCanvas.listaDePersonagem.get(i);
			if(p2!=this) {
				if(colideRet(p2)) {
					colidiu = true;
					break;
				}
			}
		}
		
		if(colidiu) {
			X = oldX;
			Y = oldY;
			velX = -velX;
			velY = -velY;
		}
		
		
		for(int i = 0; i < GameRunCanvas.listaItemMapa.size();i++) {
			ItemMapa item = GameRunCanvas.listaItemMapa.get(i);
			if(item.colideRet(this)) {
				vida+=100;
				GameRunCanvas.listaItemMapa.remove(i);
				break;
			}
		}
		

		
		
		if(Math.abs(velX)>Math.abs(velY)) {
			if(velX>0) {
				linhaAnim = 1;
			}else {
				linhaAnim = 3;
			}
		}else {
			if(velY<0) {
				linhaAnim = 0;
			}else {
				linhaAnim = 2;
			}
		}
		

	}
	
	public void levaDano(float dano) {
		vida-=dano;
		if(vida<=0) {
			vivo = false;
			ItemMapa item = new ItemMapa();
			item.X = X;
			item.Y = Y;
			GameRunCanvas.listaItemMapa.add(item);
		}
	}
	
	public boolean colideRet(Personagem p2) {
		if(!(p2.X > (X+charW)|| (p2.X+p2.charW) < X)&&!(p2.Y > (Y+charH)|| (p2.Y+p2.charH) < Y)) {
			return true;
		}
		
		return false;
	}

}
