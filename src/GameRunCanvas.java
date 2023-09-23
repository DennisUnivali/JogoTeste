import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameRunCanvas extends GCanvas {


	public static Random rnd = new Random();

	BufferedImage imagemcharsets;
	BufferedImage fundo;
	BufferedImage tileset;
	BufferedImage charsetpersonagens;
	public static BufferedImage fumaca;
	public static BufferedImage fumaca2;


	boolean LEFT, RIGHT,UP,DOWN;
	boolean FIRE;


	int MouseX,MouseY;


	//float sqX1 = 0;
	//float sqY1 = 100;

	Personagem heroi;

	//float sqX2 = 0;
	//float sqY2 = 200;
	float velRedBloc = 2000;


	BufferedImage chara1;
	int linhaAnim = 2;
	int collFrame = 0;
	int animationTimer = 0;

	int fireTimer = 0;
	int fireInterval = 50;


	int xTela = 0;
	int yTela = 0;

	TileMap mapa = null;

	public Font font01 = new Font(null, Font.BOLD, 16);

	public static ArrayList<Personagem> listaDePersonagem;
	public static ArrayList<Projetil> listaProjeteis;
	public static ArrayList<Particula> listaParticulas;
	public static ArrayList<ItemMapa> listaItemMapa;

	public static double zoom = 1.0;
	public boolean antialias_on = false;

	int tipoDeTiro = 1;
	
	public GameRunCanvas() {
		chara1 = Constantes.carregaImagem("Chara1.png");
		System.out.println("chara1 -> "+chara1.getColorModel());
		
		charsetpersonagens = Constantes.carregaImagem("chara1c.png");

		tileset = Constantes.carregaImagem("owlishmedia_pixel_tiles.png");
		
		mapa = new TileMap(tileset);
		mapa.carregaMapaJson("./res/mapa01_b.json");
		
		
		MouseX = MouseY = 0;
		
		listaDePersonagem = new ArrayList<>();
		
		heroi = new Personagem(200, 200, mapa, chara1);
		listaDePersonagem.add(heroi);
		
		/*for(int i = 0; i < 500;i++) {
			Personagem p = null;
			
			boolean colidiu = false;
			
			do {
				p = new Personagem(rnd.nextInt(3000), rnd.nextInt(3000), mapa, charsetpersonagens);	
				colidiu = false;
				for(int j = 0; j < listaDePersonagem.size();j++) {
					Personagem p2 = listaDePersonagem.get(j);
					if(p2.colideRet(p)) {
						colidiu = true;
						break;
					}
				}
			}while(colidiu);
			
			
			p.velX = rnd.nextInt(400)-200; 
		    p.velY = rnd.nextInt(400)-200; 
		    
			p.charaX = rnd.nextInt(4)*72; 
		    p.charaY = rnd.nextInt(2)*128; 
			
			listaDePersonagem.add(p);
		}*/
		
		listaProjeteis = new ArrayList<>();
		listaParticulas = new ArrayList<>();
		listaItemMapa = new ArrayList<>();
		
		fumaca = Constantes.carregaImagem("fumaca.png");
		fumaca2 = Constantes.carregaImagem("fumaca2.png");
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation()<0) {
			zoom = zoom*1.1;
			if(zoom>=8) {
				zoom = 8;
			}
			
			Constantes.PWIDTHZ = Constantes.PWIDTH/zoom;
			Constantes.PHEIGHTZ = Constantes.PHEIGHT/zoom;
		}else if(e.getWheelRotation()>0) {
			zoom = zoom*0.9;
			if(zoom<=0.5) {
				zoom = 0.5;
			}
			Constantes.PWIDTHZ = Constantes.PWIDTH/zoom;
			Constantes.PHEIGHTZ = Constantes.PHEIGHT/zoom;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==1) {
			FIRE = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton()==1) {
			FIRE = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
			LEFT = true;
		}
		if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
			RIGHT = true;
		}
		if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
			UP = true;
		}
		if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
			DOWN = true;
		}	
		if(keyCode == KeyEvent.VK_SPACE) {
			FIRE = true;
		}
		if(keyCode == KeyEvent.VK_0) {
			antialias_on = !antialias_on;
		}
		if(keyCode == KeyEvent.VK_1) {
			tipoDeTiro = 1;
		}
		if(keyCode == KeyEvent.VK_2) {
			tipoDeTiro = 2;
		}
		if(keyCode == KeyEvent.VK_3) {
			tipoDeTiro = 3;
		}
		if(keyCode == KeyEvent.VK_M) {
			GamePanel.intace.switchCanvas(new CanvasGameMenu());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
			LEFT = false;
		}
		if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
			RIGHT = false;
		}
		if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
			UP = false;
		}
		if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
			DOWN = false;
		}
		if(keyCode == KeyEvent.VK_SPACE) {
			FIRE = false;
		}
	}

	@Override
	public void desenhaSe(Graphics2D dbg) {
		//Pinta O fundo de Branco
		dbg.setColor(Color.white);
		dbg.fillRect(0, 0, Constantes.PWIDTH, Constantes.PHEIGHT);
		
		AffineTransform trans = dbg.getTransform();
		
		if(antialias_on) {
		    dbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); 
		    dbg.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY); 
		    dbg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC); 
		}else {
		    dbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF); 
		    dbg.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_SPEED);
		    dbg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR); 
		}
	    
		dbg.scale(zoom, zoom);
		
		
		
		//Desenha Layer 1 e 2 do mapa
		mapa.desenhaLayer(dbg, xTela, yTela, 0);
		mapa.desenhaLayer(dbg, xTela, yTela, 1);
		
		// Desenha ItemMapa
		for(int i = 0; i < listaItemMapa.size();i++) {
			listaItemMapa.get(i).desenhaSe(dbg, xTela, yTela);
		}
		
		// Desenha Personagens
		for(int i = 0; i < listaDePersonagem.size();i++) {
			listaDePersonagem.get(i).desenhaSe(dbg, xTela, yTela);
		}
		// Desenha Projeteis
		for(int i = 0; i < listaProjeteis.size();i++) {
			listaProjeteis.get(i).desenhaSe(dbg, xTela, yTela);
		}
		// Desenha Particulas
		for(int i = 0; i < listaParticulas.size();i++) {
			listaParticulas.get(i).desenhaSe(dbg, xTela, yTela);
		}

		
		
		
		//Desenha Layer 3 do mapa
		mapa.desenhaLayer(dbg, xTela, yTela, 2);
		
		
		dbg.setTransform(trans);
		
		
		//Desenha Infos de Interface
		dbg.setFont(font01);
		dbg.setColor(Color.BLUE);
		dbg.drawString("FPS: "+Constantes.FPS+" Tipo Tiro:"+tipoDeTiro+" ---> "+(int)(timerwave/1000), 20, 20);
		//dbg.drawString("LEFT "+LEFT+" RIGHT "+RIGHT+" UP "+UP+" DOWN "+DOWN, 20, 50);
	}

	int tempoEntreWaves = 5000;
	int wave = 0;
	int timerwave = 0;
	
	int wavetype[][] = {
			{10,100,100},//0
			{20,100,120},//1
			{20,200,120},//2
			{20,200,130},//3
			{20,200,130},//4
			{30,300,130},//5
			{40,300,140},//6
			{50,300,145},//7
			{100,400,150},//8
			{100,400,150}//9
	};
	@Override
	public void atualizeSe(long DiffTime) {
		if(RIGHT||LEFT||DOWN||UP) {
			animationTimer+=DiffTime;
		}

		
		
//		int tempoEntreFrames = 100;
//		collFrame = (animationTimer/tempoEntreFrames)%3;
	//	
//		//sqX1+=0.5f;
//		float velocidade = 200;
	//	
//		float oldsqX1 = sqX1;
//		float oldsqY1 = sqY1;
	//	
		float velocidade = 200;
		if(RIGHT) {
			heroi.velX = velocidade;
		}else if(LEFT) {
			heroi.velX = -velocidade;
		}else {
			heroi.velX = 0;
		}
		
		if(DOWN) {
			heroi.velY = velocidade;
		}else if(UP) {
			heroi.velY = -velocidade;
		}else {
			heroi.velY = 0;
		}
		
		
		xTela = (int)(heroi.X+12-Constantes.PWIDTHZ/2);
		yTela = (int)(heroi.Y+24-Constantes.PHEIGHTZ/2);
		
//		if(xTela<0) {
//			xTela = 0;
//		}
//		if(yTela<0) {
//			yTela = 0;
//		}
//		if(xTela+PWIDTHZ>mapa.worldW) {
//			xTela =(int)(mapa.worldW-PWIDTHZ);
//		}
//		if(yTela+PHEIGHTZ>mapa.worldH) {
//			yTela =(int)(mapa.worldH-PHEIGHTZ);
//		}
		
		
		for(int i = 0; i < listaDePersonagem.size();i++) {
			Personagem p = listaDePersonagem.get(i);
			if(p.vivo==false) {
				listaDePersonagem.remove(i);
				i--;
			}else {
				p.atualizeSe(DiffTime);
			}
		}
		
		fireTimer+=DiffTime;
		
		
		if(FIRE&&fireTimer>=fireInterval) {
			//TODO
			if(tipoDeTiro==1) {
				addTiro1();
			}else if(tipoDeTiro==2) {
				addTiro2();
			}else if(tipoDeTiro==3) {
				addTiro3();
			}
			
			fireTimer = 0;
		}

		for(int i = 0; i < listaProjeteis.size();i++) {
			Projetil p = listaProjeteis.get(i);
			if(p.vivo==false) {
				listaProjeteis.remove(i);
				i--;
			}else {
				p.atualizeSe(DiffTime);
			}
		}
		
		for(int i = 0; i < listaParticulas.size();i++) {
			Particula p = listaParticulas.get(i);
			if(p.vivo==false) {
				listaParticulas.remove(i);
				i--;
			}else {
				p.atualizeSe(DiffTime);
			}
		}
		
		timerwave+=(int)DiffTime;
		if(timerwave>=tempoEntreWaves) {
			int wavedata[] = wavetype[wave]; 
			for(int i = 0; i < wavedata[0];i++) {
				Personagem p = null;
				
				boolean colidiu = false;
				
				do {
					p = new Personagem(rnd.nextInt(200), rnd.nextInt(3000), mapa, charsetpersonagens);	
					colidiu = false;
					for(int j = 0; j < listaDePersonagem.size();j++) {
						Personagem p2 = listaDePersonagem.get(j);
						if(p2.colideRet(p)) {
							colidiu = true;
							break;
						}
					}
				}while(colidiu);
				
				
				p.velX = wavedata[2];//rnd.nextInt(400)-200; 
			    p.velY = rnd.nextInt(400)-200; 
			    
				p.charaX = rnd.nextInt(4)*72; 
			    p.charaY = rnd.nextInt(2)*128; 
			    
			    p.vida = wavedata[1];
				
				listaDePersonagem.add(p);
			}
			wave++;
			if(wave>9) {
				wave=9;
			}
			timerwave = 0;
		}
	
	}
	
	private void addTiro1() {
		float mx = (float)(MouseX/zoom+xTela);
		float my = (float)(MouseY/zoom+yTela);
		//System.out.println("MouseX "+MouseX+" xTela "+xTela+" MouseY "+MouseY+" yTela "+yTela);
		//System.out.println("mx "+mx+" my "+my);
		
		float dx = mx - (heroi.X+heroi.charW/2);
		float dy = my - (heroi.Y+heroi.charH/2);
		
		float ang = (float)Math.atan2(dy, dx);
		
		Projetil proj = new ProjetilSimples(mapa,heroi.X+heroi.charW/2, heroi.Y+heroi.charH/2, ang, 500,heroi,20);
		listaProjeteis.add(proj);
	}

	private void addTiro2() {
		float mx = (float)(MouseX/zoom+xTela);
		float my = (float)(MouseY/zoom+yTela);

		
		float dx = mx - (heroi.X+heroi.charW/2);
		float dy = my - (heroi.Y+heroi.charH/2);
		
		float ang = (float)Math.atan2(dy, dx);
		
		Projetil proj = new ProjetilSigmoide(mapa,heroi.X+heroi.charW/2, heroi.Y+heroi.charH/2, ang, 500,heroi,20);
		listaProjeteis.add(proj);
	}

	private void addTiro3() {
		float mx = (float)(MouseX/zoom+xTela);
		float my = (float)(MouseY/zoom+yTela);
		//System.out.println("MouseX "+MouseX+" xTela "+xTela+" MouseY "+MouseY+" yTela "+yTela);
		//System.out.println("mx "+mx+" my "+my);
		
		float dx = mx - (heroi.X+heroi.charW/2);
		float dy = my - (heroi.Y+heroi.charH/2);
		
		float ang = (float)Math.atan2(dy, dx);
		
		Projetil proj = new ProjetilExplosivo(mapa,heroi.X+heroi.charW/2, heroi.Y+heroi.charH/2, ang, 500,heroi,20,200);
		listaProjeteis.add(proj);
	}	

}
