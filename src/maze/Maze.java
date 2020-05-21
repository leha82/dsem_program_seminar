package maze;

public class Maze {
	private int width, height;
	private int[][] map;
	
	public Maze() {
		this(0,0,null);
	}
	
	public Maze(int width, int height, int[][] map) {
		this.width = width;
		this.height = height;
		this.map = map;
	}
	
	public Maze(String filename) {
		// file에서 map 읽어오기 #2
		
//		this.width = width;
//		this.height = height;
//		this.map = map;
	}
	
	public int[][] getArea(int x, int y) {
		int[][] smap = new int[3][3];
		// 지도에서 해당 위치 주변의 9칸을 배열로 만들어 리턴한다. #3
		return smap;
	}

}
