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
	
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
		
	public int[][] getMap() {
		return this.map;
	}
	
	public int[][] getArea(int x, int y) {
		int[][] smap = new int[3][3];
		// �������� �ش� ��ġ �ֺ��� 9ĭ�� �迭�� ����� �����Ѵ�. #3
		
		int[] index = {-1, 0, 1};
		
//		for (int i=0;i<smap.length;i++) {
//			for (int j=0;j<smap[0].length;j++) {
//				smap[i][j] = map[x+index[i]][y+index[j]];
//			}
//		}
		
		return smap;
	}

}
