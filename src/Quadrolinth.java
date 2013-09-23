import Board.Board;
import Board.Tile;
import Player.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr Shved
 * Date: 16.09.13
 * Time: 23:03
 *
 * TODO: Control, that teleports, treasure and arsenal are different tiles.
 *
 */
public class Quadrolinth {

	public static void main(String[] args) {

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
		board.makeTeleport(board.getTile(0,0),board.getTile(0, 1));
		Player tom = new Player(board, "Tom", 0, 0);
		tom.move(1);
		tom.move(3);
		tom.move(3);


	}

}
