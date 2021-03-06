package ee.srd.shveda.quadrolinth.Board.Board;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr Shved
 * Date: 16.09.13
 * Time: 23:22
 */
public class Tile {

	private String name;
	private Tile north;
	private Tile east;
	private Tile south;
	private Tile west;
	private Tile teleport;
 	private boolean treasure = false;
	private boolean arsenal = false;

	public Tile(String name){
		this.name = name;
	}


	public String toString(){
		return name;
	}

	public String getName() {
		return name;
	}

	public Tile getNorth() {
		return north;
	}

	public void setNorth(Tile north) {
		this.north = north;
	}

	public Tile getEast() {
		return east;
	}

	public void setEast(Tile east) {
		this.east = east;
	}

	public Tile getSouth() {
		return south;
	}

	public void setSouth(Tile south) {
		this.south = south;
	}

	public Tile getWest() {
		return west;
	}

	public void setWest(Tile west) {
		this.west = west;
	}

	public void setTreasure(boolean treasure){
		this.treasure = treasure;
	}

	public boolean hasTreasure(){
		return treasure;
	}

	public boolean hasArsenal() {
		return arsenal;
	}

	public void setArsenal(boolean arsenal) {
		this.arsenal = arsenal;
	}

	public Tile getTeleport() {
		return teleport;
	}

	public void setTeleport(Tile teleport) {
		this.teleport = teleport;
	}
}
