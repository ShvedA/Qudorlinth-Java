package Player;

import Board.Board;
import Board.Tile;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr Shved
 * Date: 16.09.13
 * Time: 23:04
 */
public class Player {

	private String name;
	private Tile tile;
	private Board board;

	public Player(Board board, String name, int height, int width) {
		this.board = board;
		this.name = name;
		tile = board.getTile(height - 1, width - 1);
		System.out.println("Player " + name + " started the game at " + tile.getName());
	}

	public boolean moveNorth() {
		System.out.println("Trying to move north:");
		if (tile.getNorth() == null) {
			System.out.println("Wall at the north");
			return false;
		}
		tile = tile.getNorth();
		System.out.println("Player " + name + " moved to " + tile);
		return true;
	}

	public boolean moveEast() {
		System.out.println("Trying to move east:");
		if (tile.getEast() == null) {
			System.out.println("Wall at the east");
			return false;
		}
		tile = tile.getEast();
		System.out.println("Player " + name + " moved to " + tile);
		return true;
	}

	public boolean moveSouth() {
		System.out.println("Trying to move south:");
		if (tile.getSouth() == null) {
			System.out.println("Wall at the south");
			return false;
		}
		tile = tile.getSouth();
		System.out.println("Player " + name + " moved to " + tile);
		return true;
	}

	public boolean moveWest() {
		System.out.println("Trying to move west:");
		if (tile.getWest() == null) {
			System.out.println("Wall at the west");
			return false;
		}
		tile = tile.getWest();
		System.out.println("Player " + name + " moved to " + tile);
		return true;
	}

}
