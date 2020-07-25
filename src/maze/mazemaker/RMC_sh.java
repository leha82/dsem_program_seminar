package maze.mazemaker;

public class RMC_sh extends RandomMapCreator {
	private int[][] map;

	public RMC_sh(String mapName, int height, int weight) {
		super(mapName, height, weight);
		
	}

	public static void main(String[] args) {
		RMC_sh rmc = new RMC_sh("RMCSH01", 50, 50);
	}
}
