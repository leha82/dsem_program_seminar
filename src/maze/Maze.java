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
		// file���� map �о���� #2
		
//		this.width = width;
//		this.height = height;
//		this.map = map;
	}
	
	public int[][] getArea(int x, int y) {
		int[][] smap = new int[3][3];
		// �������� �ش� ��ġ �ֺ��� 9ĭ�� �迭�� ����� �����Ѵ�. #3
		return smap;
	}

}
