package maze.mazemaker;

import java.util.Random;;

public class RMC_woolin extends RandomMapCreator {

	private boolean visited[][];
	public RMC_woolin() {
		this("", 0, 0);
	}

	public RMC_woolin(String mapName, int x_size, int y_size) {
		super(mapName, x_size, y_size);

		this.visited = new boolean[y_size][x_size];
	}
	
	@Override
	public void createMap() {
		// Todo: ���⿡�� map�� �����Ѵ�.
		Random rand = new Random();
		for (int i=0;i<y_size;i++) {
			for (int j=0;j<x_size;j++) {
				map[j][i] = rand.nextInt(2);
			}
		}
		map[0][0] = 0;  map[y_size-1][x_size-1] = 0;	// �������� �������� 0���� ����.

		visited[0][0] = true;	// ��ǥ �湮 ����
		create_maze(0, 0);
	}
	
	public void create_maze(int y, int x) {
		// �ⱸ�� �׸��ϱ�
		if (y == esc_y && x == esc_x) {
			return;
		}
		
		int[] dir = {0, 1, 2, 3};	// 0(��), 1(��), 2(��), 3(��)
		dir = shuffle(dir);
		
		// �� �� ������ �� �������� �̵�
		for (int i=0;i<4;i++) {
			if (dir[i] == 0 && y > 1 && map[y-1][x]==0 && visited[y-1][x] == false) {
				visited[y-1][x] = true;	// �湮 ǥ��
				create_maze(y-1, x);
			}
			if (dir[i] == 1 && y < y_size-1 && map[y+1][x]==0 && visited[y+1][x] == false) {
				visited[y+1][x] = true;
				create_maze(y+1, x);
			}
			if (dir[i] == 2 && x > 1 && map[y][x-1]==0 && visited[y][x-1] == false) {
				visited[y][x-1] = true;
				create_maze(y, x-1);
			}
			if (dir[i] ==3 && x < x_size - 1 && map[y][x+1]==0 && visited[y][x+1] == false) {
				visited[y][x+1] = true;
				create_maze(y, x+1);
			}
		}
		
		// ����� �� ����������
		Random rand = new Random();
		int dir_num = rand.nextInt(4);	// 0~3�� �ƹ��ų� �̱�
		if (dir_num == 0 && y > 1 && visited[y-1][x] == false) {
			visited[y-1][x] = true;	// �湮 ǥ��
			map[y-1][x] = 0;
			create_maze(y-1, x);
		}
		if (dir_num == 1 && y < y_size-1 && visited[y+1][x] == false) {
			visited[y+1][x] = true;
			map[y+1][x] = 0;
			create_maze(y+1, x);
		}
		if (dir_num == 2 && x > 1 && visited[y][x-1] == false) {
			visited[y][x-1] = true;
			map[y][x-1] = 0;
			create_maze(y, x-1);
		}
		if (dir_num ==3 && x < x_size - 1 && visited[y][x+1] == false) {
			visited[y][x+1] = true;
			map[y][x+1] = 0;
			create_maze(y, x+1);
		}
		
		return;
		
	}
	
	// ���� �����ϰ� �����ϵ��� ����
	public int[] shuffle(int[] dir) {
		for (int i=0;i<dir.length;i++) {
			int a = (int)(Math.random()*dir.length);
			int b = (int)(Math.random()*dir.length);
			
			int tmp = dir[a];
			dir[a] = dir[b];
			dir[b] = tmp;
		}
		
		return dir;
	}

	public static void main(String[] args) {
		RMC_woolin rmc = new RMC_woolin("TestMap_woolin", 20, 20);

		rmc.createMap();
		rmc.printMap();
		rmc.makeMapFile();
	}

}
