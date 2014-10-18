package ee.srd.shveda.quadrolinth.Board.Image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr Shved
 * Date: 02.10.13
 * Time: 23:55
 */
public class Screen extends Bitmap {

	public BufferedImage image;


	public Screen(int width, int height) {
		super(width, height);
		        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
}
