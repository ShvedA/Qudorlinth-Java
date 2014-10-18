package ee.srd.shveda.quadrolinth.Board.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr Shved
 * Date: 03.10.13
 * Time: 0:17
 */
public class ImageLoader {
	public static Bitmap tilesPlain;
	public static Bitmap[][] tilesCut;

	public static void init(){
		tilesPlain = load("board8.png");
		tilesCut = load("board8.png", 8, 8);
	}

	private static Bitmap[][] load(String string, int w, int h) {
		try{
			BufferedImage bi = ImageIO.read(ImageLoader.class.getResource(string));
			int xTiles = bi.getWidth() / w;
			int yTiles = bi.getHeight() / h;

			Bitmap[][] b = new Bitmap[xTiles][yTiles];

		   for(int x = 0;x<xTiles;x++){
			   for(int y =0;y<yTiles;y++){
				   b[x][y] = new Bitmap(w,h);
			bi.getRGB(x*w,y*h, w, h,b[x][y].pixels,0,w);
			   }
		   }

			return b;
		}                                     catch( IOException e){
			e.printStackTrace();
		}
		return null;  //To change body of created methods use File | Settings | File Templates.
	}

	private static Bitmap load(String string) {
		try{
			BufferedImage bi = ImageIO.read(ImageLoader.class.getResource(string));
			int width = bi.getWidth();
			int height = bi.getHeight();

			Bitmap b = new Bitmap(width, height);
			bi.getRGB(0,0, width, height,b.pixels,0,width);
			return b;
		}                                     catch( IOException e){
			e.printStackTrace();
		}
		return null;  //To change body of created methods use File | Settings | File Templates.
	}
}
