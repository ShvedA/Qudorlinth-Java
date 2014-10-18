package ee.srd.shveda.quadrolinth.Board.Player;

import ee.srd.shveda.quadrolinth.Board.Board.Board;
import ee.srd.shveda.quadrolinth.Board.Board.Tile;
import ee.srd.shveda.quadrolinth.Board.Board.Direction;
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
	private int dynamite;
	private boolean treasure = false;

	public Player(Board board, String name, int height, int width) {
		this.board = board;
		this.name = name;
		shoots = board.getDefaultShoots();
		dynamite = board.getDefaultDynamites();
		board.addPlayer(this);
		tile = board.getTile(height, width);
		System.out.println("ee.srd.shveda.quadrolinth.Board.Player " + name + " started the game at " + tile.getName());
		if (tile.hasTreasure()) {
			System.out.println(name + " has recieved treasure");
			tile.setTreasure(false);
			treasure = true;
		} else if (tile.hasArsenal()) {
			System.out.println(name + " has found arsenal");
			tile.setArsenal(false);
			shoots += board.getDefaultShoots();
			dynamite += board.getDefaultDynamites();
		} else if (tile.getTeleport() != null) {
			System.out.println(name + " has been teleported");
			tile = tile.getTeleport();
		}
	}

	public boolean move(int which){
		Direction direction = Direction.values()[which];
		System.out.println("Trying to move " + direction + " :");
		Tile tileToMove = null;
		switch (direction){
			case NORTH:
				if(tile.getNorth() != null){
					tileToMove = tile.getNorth();
				} else {
					System.out.println("Wall at north");
					return false;
				}
				break;
			case EAST:
				if(tile.getEast() != null){
					tileToMove = tile.getEast();
				}  else {
					System.out.println("Wall at east");
					return false;
				}
				break;
			case SOUTH:
				if(tile.getSouth() != null){
					tileToMove = tile.getSouth();
				}  else {
					System.out.println("Wall at south");
					return false;
				}
				break;
			case WEST:
				if(tile.getWest() != null){
					tileToMove = tile.getWest();
				}  else {
					System.out.println("Wall at west");
					return false;
				}
				break;
		}
		if(tileToMove.getClass().getCanonicalName().equals("ee.srd.shveda.quadrolinth.Board.Board.ExitTile")){
			if(treasure){
				System.out.println("You have won!");
				return true;
			}               else{
				System.out.println("You found an exit, but you don't have a treasure.");
				return false;
			}
		} else{
			tile = tileToMove;
			System.out.println("ee.srd.shveda.quadrolinth.Board.Player " + name + " moved to " + tile);
			if (tile.hasTreasure()) {
				System.out.println(name + " has recieved treasure");
				tile.setTreasure(false);
				treasure = true;
			} else if (tile.hasArsenal()) {
				System.out.println(name + " has found arsenal");
				tile.setArsenal(false);
				shoots += board.getDefaultShoots();
				dynamite += board.getDefaultDynamites();
			} else if (tile.getTeleport() != null) {
				System.out.println(name + " has been teleported");
				tile = tile.getTeleport();
			}
			return true;
		}
	}

	public boolean shoot(int which){
		if(shoots > 0){
			Direction direction = Direction.values()[which];
			System.out.println(name + " shoots to the " + direction);
			shoots--;
			board.shoot(this, which);
			return true;
		} else{
			System.out.println("You have no shoots left");
			return false;
		}
	}

	public Tile getTile() {
		return tile;
	}

	public String toString() {
		return name + "(" + shoots + ")";
	}

	public void setTreasure(boolean treasure) {
		this.treasure = treasure;
	}

	public boolean hasTreasure() {
		return treasure;
	}
}
