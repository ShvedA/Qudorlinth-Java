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

}
