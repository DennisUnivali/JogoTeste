import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

import com.org.json.JSONArray;
import com.org.json.JSONException;
import com.org.json.JSONObject;

public class TileMap {
//	int tilemap[][] = {{1,3,3,3,3,3,3,3,3,1},
//	           {1,3,3,3,3,3,3,3,3,1},
//	           {1,2,2,2,28,28,28,28,28,1},
//	           {1,0,0,0,0,0,0,0,0,1},
//	           {1,0,0,0,0,0,0,0,0,1},
//	           {1,0,0,0,0,0,0,0,0,1},
//	           {1,0,0,0,0,0,0,0,0,1},
//	           {1,0,0,0,0,0,0,0,0,1},
//	           {1,0,0,0,0,0,0,0,0,1},
//	           {1,0,0,0,0,0,0,0,0,1},
//				};
	
	int tilemap[][][];
	
	BufferedImage tileset = null;
	
	Random rnd;
	
	public int height = 0;
	public int width = 0;
	public int tileheight = 0;
	public int tilewidth = 0;
	
	public int nlayers = 0;
	
	public int worldW = 0;
	public int worldH = 0;
	
	public TileMap(BufferedImage tileset) {
		this.tileset = tileset;
	}
	
	public void carregaMapaJson(String nomemapa) {
		try {
			FileReader fr = new FileReader(nomemapa,Charset.forName("UTF-8"));

			BufferedReader bfr = new BufferedReader(fr);
			String line = "";
			String json = "";
			while((line = bfr.readLine())!=null) {
				json+=line+"\n";
			}
		
			//System.out.println(""+json);
			
			JSONObject jso = new JSONObject(json);
			
			height = jso.getInt("height");
			width = jso.getInt("width");
			tileheight = jso.getInt("tileheight");
			tilewidth = jso.getInt("tilewidth");
			
			worldW = width*tilewidth;
			worldH = height*tileheight;
			
			JSONArray jarr = jso.getJSONArray("layers");
			nlayers = jarr.length();
			
			tilemap = new int[nlayers][height][width];
			
			for(int i = 0; i < nlayers;i++) {
				JSONObject layrobject = jarr.getJSONObject(i);
				JSONArray jsondata = layrobject.getJSONArray("data");
				int index = 0;
				for(int yi = 0; yi < height;yi++) {
					for(int xi = 0; xi < width;xi++) {
						tilemap[i][yi][xi] = jsondata.getInt(index)-1;
						index++;
						//System.out.println(""+tilemap[i][yi][xi]);
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void criaMapaRandomico(BufferedImage tileset) {
		rnd = new Random();
		
		tilemap = new int[1][100][100];
		
		for(int yt = 0;yt < 100;yt++) {
			for(int xt = 0;xt < 100;xt++) {
				tilemap[0][yt][xt] = 1;
				if(rnd.nextInt(10)<2) {
					tilemap[0][yt][xt] = 1+rnd.nextInt(50);
				}
			}
		}
		
		worldW = 100*32;
		worldH = 100*32;
	}
	
	public void desenhaSe(Graphics2D dbg, int xTela,int yTela) {
		
		for(int li = 0; li < nlayers;li++) {
			for(int yt = 0;yt < 100;yt++) {
				for(int xt = 0;xt < 100;xt++) {
					int tile = tilemap[li][yt][xt]; 
					
					if(tile==-1) {
						continue;
					}
					
					//System.out.println("layer "+li+" t "+tile);
					
					int tileX = (tile%14)*32;
					int tileY = ((int)(tile/14))*32;
					
					//System.out.println("Tile "+tile+" "+tileX+" "+tileY);
					//dbg.drawImage(tileset, (int)(xt*32-xTela),(int)(yt*32-xTela),(int)(xt*32+32-xTela),(int)(yt*32+32-yTela), 32*tileX, 32*tileY, 32*tileX+32, 32*tileY+32,null);
					
					//dbg.drawImage(tileset, (int)(xt*32),(int)(yt*32),(int)(xt*32+32),(int)(yt*32+32), 0, 0, 32, 32,null);
					dbg.drawImage(tileset, (int)(xt*32)-xTela,(int)(yt*32)-yTela,(int)(xt*32)+32-xTela,(int)(yt*32)+32-yTela,tileX, tileY, tileX+32, tileY+32,null);
				}
			}
		}	
	}
	
	public void desenhaLayer(Graphics2D dbg, int xTela,int yTela, int layer) {
		
			for(int yt = 0;yt < 100;yt++) {
				for(int xt = 0;xt < 100;xt++) {
					int tile = tilemap[layer][yt][xt]; 
					
					if(tile==-1) {
						continue;
					}
					
					//System.out.println("layer "+li+" t "+tile);
					
					int tileX = (tile%14)*32;
					int tileY = ((int)(tile/14))*32;
					
					int tdrawx = (int)(xt*32)-xTela;
					int tdrawy = (int)(yt*32)-yTela;
					
					if(tdrawx>Constantes.PWIDTHZ) {
						continue;
					}
					if(tdrawy>Constantes.PHEIGHTZ) {
						continue;
					}
					if(tdrawx<-tilewidth) {
						continue;
					}
					if(tdrawy<-tileheight) {
						continue;
					}
					
					//System.out.println("Tile "+tile+" "+tileX+" "+tileY);
					//dbg.drawImage(tileset, (int)(xt*32-xTela),(int)(yt*32-xTela),(int)(xt*32+32-xTela),(int)(yt*32+32-yTela), 32*tileX, 32*tileY, 32*tileX+32, 32*tileY+32,null);
					
					//dbg.drawImage(tileset, (int)(xt*32),(int)(yt*32),(int)(xt*32+32),(int)(yt*32+32), 0, 0, 32, 32,null);
					dbg.drawImage(tileset, tdrawx,tdrawy,tdrawx+32,tdrawy+32,tileX, tileY, tileX+32, tileY+32,null);
				}
			}
	}	
}
