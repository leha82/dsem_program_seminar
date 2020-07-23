package maze.mazemaker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;;

public class RandomMapCreator_woolin {
	private String mapName;
	private int x_size;
	private int y_size;
	private int start_x, start_y;
	private int esc_x, esc_y;

	private int[][] map;
	private boolean visited[][];
	public RandomMapCreator_woolin() {
		this("", 0, 0);
	}

	public RandomMapCreator_woolin(String mapName, int x_size, int y_size) {
		this.mapName = mapName;
		this.x_size = x_size;
		this.y_size = y_size;
		this.map = new int[y_size][x_size];
		this.visited = new boolean[y_size][x_size];
		
		this.start_x = 0;
		this.start_y = 0;
		this.esc_x = x_size-1;
		this.esc_y = y_size-1;
	}
	
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
		RandomMapCreator_woolin rmc = new RandomMapCreator_woolin("NewMap", 10, 10);

		rmc.createMap();
		rmc.printMap();
		//rmc.makeMapFile();
	}

	public void makeMapFile() {
	// Todo : �� ���Ϸ� ���� testmap.txt�� ������ ��������.
	// ���Ϸ� �����Ҷ� maps ������ .txtȮ���ڸ� �ٿ��� ���Ϸ� ������ ��
		// this.mapName Ȱ��
		
		File f = new File("C:\\dsem_seminar\\dsem_program_seminar\\maps\\" + mapName + ".txt");
		if (f.exists()) {
			int index = 1;
			mapName = mapName + "_" + index;
			
			while(true) {
				f = new File("C:\\dsem_seminar\\dsem_program_seminar\\maps\\" + mapName + ".txt");
				if (f.exists()) {
					index++;
					mapName = mapName.substring(0, mapName.lastIndexOf("_"));
					mapName = mapName + "_" + index;
				} else {
					break;
				}
			}
		}
		
		try {
			FileWriter w = new FileWriter("C:\\dsem_seminar\\dsem_program_seminar\\maps\\" + mapName + ".txt");
			
			w.write(x_size + " " + y_size + "\n");
			w.write(start_x + " " + start_y + "\n");
			w.write(esc_x + " " + esc_y + "\n");
			for (int i=0;i<y_size;i++) {
				for (int j=0;j<x_size;j++) {
					w.write(map[j][i] + " ");
				}
				w.write("\n");
			}
			w.close();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printMap() {
		String road = "��";
		String block = "��";

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

	public int getStart_x() {
		return start_x;
	}

	public void setStart_x(int start_x) {
		this.start_x = start_x;
	}

	public int getStart_y() {
		return start_y;
	}

	public void setStart_y(int start_y) {
		this.start_y = start_y;
	}

	public int getEsc_x() {
		return esc_x;
	}

	public void setEsc_x(int esc_x) {
		this.esc_x = esc_x;
	}

	public int getEsc_y() {
		return esc_y;
	}

	public void setEsc_y(int esc_y) {
		this.esc_y = esc_y;
	}
	
	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

}
