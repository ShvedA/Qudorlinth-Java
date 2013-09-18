package Board;

import java.util.ArrayList;
import java.util.List;
import Player.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr Shved
 * Date: 16.09.13
 * Time: 23:05
 */
public class Board {

	private static final int DEFAULT_SHOOTS = 3;

	private int height;
	private int width;
	private Tile[][] board;
	private List<Player> players = new ArrayList<Player>();
	private int counter;

	public Board(int height, int width){
		this.height = height;
		this.width = width;
		board = new Tile[height][width];
		counter = 0;
	}

	//building board with tiles
	public void addTile(Tile tile, boolean north, boolean west){
		int heightPosition = counter/height;
		int widthPosition = counter%width;
		board[heightPosition][widthPosition] = tile;
		if (heightPosition > 0 && north == true) {
			tile.setNorth(board[heightPosition - 1][widthPosition]);
			board[heightPosition - 1][widthPosition].setSouth(tile);
		}
		if (widthPosition > 0 && west == true) {
			tile.setWest(board[heightPosition][widthPosition - 1]);
			board[heightPosition][widthPosition - 1].setEast(tile);
		}
		counter++;
	}

	public Tile getTile(int heightIndex, int widthIndex){
		return board[heightIndex][widthIndex];
	}

	public void printBoard(){
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	private int[] findTileOnBoard(Tile tile){
		int[] coordinates = new int[2];
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				if (tile.equals(board[i][j])){
					coordinates[0] = i;
					coordinates[1] = j;
				}
			}
		}
		return coordinates;
	}

	public boolean shootNorth(Tile tile, Player shooter){
		int firstCoordinate = findTileOnBoard(tile)[0];
		int secondCoordinate = findTileOnBoard(tile)[1];
		boolean missed = true;
		for(int i = firstCoordinate; i > -1; i--){
			if(board[i][secondCoordinate].getNorth() != null){
				for(Player player: players){
					if(player.getTile().equals(board[i][secondCoordinate].getNorth())){
						System.out.println(shooter + " killed " + player);
						missed = false;
						if(player.hasTreasure()){
							player.setTreasure(false);
							shooter.setTreasure(true);
						}
					}
				}
				if(!missed)
					return true;
			} else {
				break;
			}
		}
		return false;
	}

	public boolean shootEast(Tile tile, Player shooter){
		int firstCoordinate = findTileOnBoard(tile)[0];
		int secondCoordinate = findTileOnBoard(tile)[1];
		boolean missed = true;
		for(int j = secondCoordinate; j < board[0].length; j++){
			if(board[firstCoordinate][j].getEast() != null){
				for(Player player: players){
					if(player.getTile().equals(board[firstCoordinate][j].getEast())){
						System.out.println(shooter + " killed " + player);
						missed = false;
						if(player.hasTreasure()){
							player.setTreasure(false);
							shooter.setTreasure(true);
						}
					}
				}
				if(!missed)
					return true;
			} else {
				break;
			}
		}
		return false;
	}

	public boolean shootSouth(Tile tile, Player shooter){
		int firstCoordinate = findTileOnBoard(tile)[0];
		int secondCoordinate = findTileOnBoard(tile)[1];
		boolean missed = true;
		for(int i = firstCoordinate; i < board.length; i++){
			if(board[i][secondCoordinate].getSouth() != null){
				for(Player player: players){
					if(player.getTile().equals(board[i][secondCoordinate].getSouth())){
						System.out.println(shooter + " killed " + player);
						missed = false;
						if(player.hasTreasure()){
							player.setTreasure(false);
							shooter.setTreasure(true);
						}
					}
				}
				if(!missed)
					return true;
			} else {
				break;
			}
		}
		return false;
	}

	public boolean shootWest(Tile tile, Player shooter){
		int firstCoordinate = findTileOnBoard(tile)[0];
		int secondCoordinate = findTileOnBoard(tile)[1];
		boolean missed = true;
		for(int j = secondCoordinate; j > -1; j--){
			if(board[firstCoordinate][j].getWest() != null){
				for(Player player: players){
					if(player.getTile().equals(board[firstCoordinate][j].getWest())){
						System.out.println(shooter + " killed " + player);
						missed = false;
						if(player.hasTreasure()){
							player.setTreasure(false);
							shooter.setTreasure(true);
						}
					}
				}
				if(!missed)
					return true;
			} else {
				break;
			}
		}
		return false;
	}

	public Tile[][] getBoard(){
		return board;
	}

	public void addPlayer(Player player){
		players.add(player);
	}

	public List<Player> getPlayers(){
		return players;
	}

	public int getDefaultShoots(){
		return DEFAULT_SHOOTS;
	}

	public void putTreasure(int vertical, int horizontal){
		board[vertical-1][horizontal-1].setTreasure(true);
	}

	public void putArsenal(int vertical, int horizontal){
		board[vertical-1][horizontal-1].setArsenal(true);
	}

	public void makeTeleport(Tile tile1, Tile tile2, Tile tile3){
		tile1.setTeleport(tile2);
		tile2.setTeleport(tile3);
		tile3.setTeleport(tile1);
	}

	public void makeTeleport(Tile tile4, Tile tile5){
		tile4.setTeleport(tile5);
		tile5.setTeleport(tile4);
	}
}
