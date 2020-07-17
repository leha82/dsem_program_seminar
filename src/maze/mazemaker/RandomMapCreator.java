package maze.mazemaker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RandomMapCreator {
	private String mapName;
	private int x_size;
	private int y_size;
	private int start_x, start_y;
	private int esc_x, esc_y;

	private int[][] map;

	public RandomMapCreator() {
		this("", 0, 0);
	}

	public RandomMapCreator(String mapName, int x_size, int y_size) {
		this.mapName = mapName;
		this.x_size = x_size;
		this.y_size = y_size;
		this.map = new int[y_size][x_size];
		
		this.start_x = 0;
		this.start_y = 0;
		this.esc_x = x_size-1;
		this.esc_y = y_size-1;
	}

	public void createMap() {
		// Todo: 여기에서 map을 생성한다.
		
	}

	public static void main(String[] args) {
		RandomMapCreator rmc = new RandomMapCreator("NewMap", 10, 10);

		rmc.createMap();
		rmc.printMap();
		rmc.makeMapFile();
	}

	public void makeMapFile() {
	// Todo : 맵 파일로 생성 testmap.txt와 동일한 형식으로.
	// 파일로 저장할때 maps 폴더에 .txt확장자를 붙여서 파일로 저장할 것
		// this.mapName 활용
		
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
		String road = "·";
		String block = "■";

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
