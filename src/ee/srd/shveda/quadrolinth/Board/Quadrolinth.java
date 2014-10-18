package ee.srd.shveda.quadrolinth.Board;

import ee.srd.shveda.quadrolinth.Board.Board.Board;
import ee.srd.shveda.quadrolinth.Board.Board.Tile;
import ee.srd.shveda.quadrolinth.Board.Image.ImageLoader;
import ee.srd.shveda.quadrolinth.Board.Image.Screen;
import ee.srd.shveda.quadrolinth.Board.Player.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr Shved
 * Date: 16.09.13
 * Time: 23:03
 * <p/>
 * TODO: Control, that teleports, treasure and arsenal are different tiles.
 */
public class Quadrolinth extends Canvas implements Runnable {

	private static final int WIDTH = 400, HEIGHT = 300, SCALE = 2;
	private static final String NAME = "Qudrolinth :: Version 0.01";
	private static boolean running = false;
	private Screen screen;

	private void initialise() {
		ImageLoader.init();
		screen = new Screen(WIDTH, HEIGHT);
		running = true;
		new Thread(this).start();
	}


	@Override
	public void run() {
		while(running){
			update();
			render();
		}
		System.exit(0);
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			requestFocus();
			return;
		}

		//screen.fill(0xffff00ff);

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		int tempWidth = WIDTH * SCALE;
		int tempHeight = HEIGHT * SCALE;
		int w1 = (getWidth() - tempWidth) / 2;
		int h1 = (getHeight() - tempHeight) / 2;
		g.drawImage(screen.image, w1, h1, tempWidth, tempHeight, null);
		g.dispose();
		bs.show();
	}

	private void update() {
		for(int i = 0; i < screen.pixels.length; i++){
			screen.pixels[i] = new Random().nextInt();
		}
	}

	public static void main(String[] args) {

		Quadrolinth quadrolinth = new Quadrolinth();
		/*quadrolinth.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		quadrolinth.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		quadrolinth.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(Quadrolinth.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(quadrolinth, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		quadrolinth.setBackground(Color.LIGHT_GRAY);

		quadrolinth.initialise();*/
        quadrolinth.makeTest();
	}

	public void makeTest() {
		Board board = new Board(5, 5);
		board.addTile(new Tile("A1"), false, false);
		board.addTile(new Tile("A2"), false, true);
		board.addTile(new Tile("A3"), false, true);
		board.addTile(new Tile("A4"), false, false);
		board.addTile(new Tile("A5"), false, true);
		board.addTile(new Tile("B1"), true, false);
		board.addTile(new Tile("B2"), false, false);
		board.addTile(new Tile("B3"), true, false);
		board.addTile(new Tile("B4"), true, true);
		board.addTile(new Tile("B5"), false, true);
		board.addTile(new Tile("C1"), true, false);
		board.addTile(new Tile("C2"), true, true);
		board.addTile(new Tile("C3"), false, false);
		board.addTile(new Tile("C4"), true, true);
		board.addTile(new Tile("C5"), true, false);
		board.addTile(new Tile("D1"), false, false);
		board.addTile(new Tile("D2"), true, true);
		board.addTile(new Tile("D3"), true, true);
		board.addTile(new Tile("D4"), true, false);
		board.addTile(new Tile("D5"), false, true);
		board.addTile(new Tile("E1"), false, true);
		board.addTile(new Tile("E2"), true, false);
		board.addTile(new Tile("E3"), false, true);
		board.addTile(new Tile("E4"), true, true);
		board.addTile(new Tile("E5"), true, false);
		board.addExitTiles();

		System.out.println("Printing the board:");
		board.printBoard();
		board.putTreasure(3, 3);
		board.putArsenal(1, 2);
		Player player = new Player(board, "Jack", 1, 2);
		player.move(1);
		player.move(1);
		player.move(1);
		player.move(2);
		player.move(2);
		player.move(1);
		player.move(3);
		player.move(0);
		Player bob = new Player(board, "Bob", 3, 1);
		Player peter = new Player(board, "Peter", 2, 1);
		bob.shoot(0);
		peter.shoot(0);
		board.makeTeleport(board.getTile(0, 0), board.getTile(0, 1));
		Player tom = new Player(board, "Tom", 0, 0);
		tom.move(1);
		tom.move(3);
		tom.move(3);
	}

}
