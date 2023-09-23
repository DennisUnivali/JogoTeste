import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Constantes {
	
	public static int PWIDTH = 1020;
	public static int PHEIGHT = 720;
	public static double PWIDTHZ = 1020;
	public static double PHEIGHTZ = 720;
	
	public static int FPS,SFPS;
	public static int fpscount;
	
	public static GameRunCanvas gameruncanvas = null;
	
	public static BufferedImage carregaImagem(String imageName) {
		
		try {
			BufferedImage imageTmp = ImageIO.read( GamePanel.class.getResource(imageName) );
			BufferedImage imageFinal = new BufferedImage(imageTmp.getWidth(), imageTmp.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			imageFinal.getGraphics().drawImage(imageTmp, 0, 0, null);
			
			return imageFinal;
		}
		catch(IOException e) {
			System.out.println("Load Image error:");
		}
		return null;
	}
}
