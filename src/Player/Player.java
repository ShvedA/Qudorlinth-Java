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
	private int shoots;
	private boolean treasure = false;

	public Player(Board board, String name, int height, int width) {
		this.board = board;
		this.name = name;
		shoots = board.getDefaultShoots();
		board.addPlayer(this);
		tile = board.getTile(height - 1, width - 1);
		System.out.println("Player " + name + " started the game at " + tile.getName());
		if(tile.hasTreasure()){
			System.out.println(name + " has recieved treasure");
			tile.setTreasure(false);
			treasure = true;
		}
		if(tile.hasArsenal()){
			System.out.println(name + " has found arsenal");
			tile.setArsenal(false);
			shoots += board.getDefaultShoots();
		}
	}

	public boolean moveNorth() {
		System.out.println("Trying to move north:");
		if (tile.getNorth() == null) {
			System.out.println("Wall at the north");
			return false;
		}
		tile = tile.getNorth();
		System.out.println("Player " + name + " moved to " + tile);
		if(tile.hasTreasure()){
			System.out.println(name + " has recieved treasure");
			tile.setTreasure(false);
			treasure = true;
		}
		if(tile.hasArsenal()){
			System.out.println(name + " has found arsenal");
			tile.setArsenal(false);
			shoots += board.getDefaultShoots();
		}
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
		if(tile.hasTreasure()){
			System.out.println(name + " has recieved treasure");
			tile.setTreasure(false);
			treasure = true;
		}
		if(tile.hasArsenal()){
			System.out.println(name + " has found arsenal");
			tile.setArsenal(false);
			shoots += board.getDefaultShoots();
		}
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
		if(tile.hasTreasure()){
			System.out.println(name + " has recieved treasure");
			tile.setTreasure(false);
			treasure = true;
		}
		if(tile.hasArsenal()){
			System.out.println(name + " has found arsenal");
			tile.setArsenal(false);
			shoots += board.getDefaultShoots();
		}
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
		if(tile.hasTreasure()){
			System.out.println(name + " has recieved treasure");
			tile.setTreasure(false);
			treasure = true;
		}
		if(tile.hasArsenal()){
			System.out.println(name + " has found arsenal");
			tile.setArsenal(false);
			shoots += board.getDefaultShoots();
		}
		return true;
	}

	public boolean shootNorth() {
		if (shoots > 0) {
			System.out.println(name + " shoots to the north");
			shoots--;
			board.shootNorth(tile, this);
		} else {
			System.out.println("You have no shoots left");
		}
		return false;
	}

	public boolean shootEast() {
		if (shoots > 0) {
			System.out.println(name + " shoots to the east");
			shoots--;
			board.shootEast(tile, this);
		} else {
			System.out.println("You have no shoots left");
		}
		return false;
	}

	public boolean shootSouth() {
		if (shoots > 0) {
			System.out.println(name + " shoots to the south");
			shoots--;
			board.shootSouth(tile, this);
		} else {
			System.out.println("You have no shoots left");
		}
		return false;
	}

	public boolean shootWest() {
		if (shoots > 0) {
			System.out.println(name + " shoots to the west");
			shoots--;
			board.shootWest(tile, this);
		} else {
			System.out.println("You have no shoots left");
		}
		return false;
	}

	public Tile getTile(){
		return tile;
	}

	public String toString(){
		return name + "(" + shoots + ")";
	}

	public void setTreasure(boolean treasure){
		this.treasure = treasure;
	}

	public boolean hasTreasure(){
		return treasure;
	}
}
