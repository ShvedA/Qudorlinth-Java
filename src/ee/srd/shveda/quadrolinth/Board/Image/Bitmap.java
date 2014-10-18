package ee.srd.shveda.quadrolinth.Board.Image;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr Shved
 * Date: 02.10.13
 * Time: 23:55
 */
public class Bitmap {
	private int width, height;
	public int[] pixels;

	public Bitmap(int width, int height){
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void fill(int color){
		Arrays.fill(pixels, color);
	}
}
