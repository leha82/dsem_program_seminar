package maze.mazemaker;

public class RandomMapCreator {
	private String mapName;
	private int x_size;
	private int y_size;
	private int[][] map;

	public RandomMapCreator() {
		this("",0,0);
	}

	public RandomMapCreator(String mapName, int x_size, int y_size) {
		this.mapName = mapName;
		this.x_size = x_size;
		this.y_size = y_size;
		this.map = new int[y_size][x_size];
	}
	
	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public int getX_size() {
		return x_size;
	}

	public void setX_size(int x_size) {
		this.x_size = x_size;
	}

	public int getY_size() {
		return y_size;
	}

	public void setY_size(int y_size) {
		this.y_size = y_size;
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}
	
	public void createMap() {
		
	}
	
	public static void main(String[] args) {
		RandomMapCreator rmc = new RandomMapCreator("NewMap", 10,10);
		
		rmc.createMap();
		rmc.printMap();
	}

	public void printMap() {
		String road = "¡¤";
		String block = "¡á";
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0) {
					System.out.print(road);
				} else if (map[i][j] == 1) {
					System.out.print(block);
				} 
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
}
