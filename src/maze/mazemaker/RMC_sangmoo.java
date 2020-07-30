package maze.mazemaker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RMC_sangmoo extends RandomMapCreator {
	protected String mapName;
	protected int x_size;
	protected int y_size;
	protected int start_x, start_y;
	protected int esc_x, esc_y;

	protected int[][] map;

	public RMC_sangmoo() {
		this("", 0, 0);
	}

	public RMC_sangmoo(String mapName, int x_size, int y_size) {
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
		int maskMap1[][] ={{0,1,0},
						   {1,0,1},
						   {0,1,0}};
		
		int maskMap2[][] ={{1,0,1},
				   		   {1,1,1},
				   		   {0,1,1}};
				   
		int mask_size = maskMap1.length;
		int mask_side = mask_size / 2;
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				map[i][j] = 1;
			}
		}
		if(map[start_x][start_y] == 1 || map[esc_x][esc_y] == 1) {
			map[start_x][start_y] = 0;
			map[esc_x][esc_y] = 0;
		}
		//int maski = 0;
		//int maskj = 0;
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				for(int k = 0; k < mask_size*mask_size; k++ ) {
					//maski = i + (k / mask_size);
				   // maskj = j + (k % mask_size);
				//	System.out.println(maski + " , " + maskj);
					if(map[i][j] == maskMap2[k / mask_size][k % mask_size]) {
					   map[i][j] = (int) (Math.random() * 2);
					}
					
				}
			}
		}
		if(map[start_x][start_y] == 1 || map[esc_x][esc_y] == 1) {
			map[start_x][start_y] = 0;
			map[esc_x][esc_y] = 0;
		}
	}
	public static void main(String[] args) {
		RMC_sangmoo rmc = new RMC_sangmoo("RMCsangmoo10", 30, 30);

		rmc.createMap();
		rmc.printMap();
		//rmc.makeMapFile();
	}

	public void makeMapFile() {
	// Todo : 맵 파일로 생성 testmap.txt와 동일한 형식으로.
	// 파일로 저장할때 maps 폴더에 .txt확장자를 붙여서 파일로 저장할 것
		// this.mapName 활용
		String filename = "maps\\" + mapName + ".txt";

		int index = 0;
		boolean loop = true;
		while(loop) {
			File f = new File(filename);
			if (f.exists()) {
				index++;
				filename = "maps\\" + mapName + "_" + index + ".txt";
			} else {
				loop = false;
			}
		}
		
		try {
			FileWriter w = new FileWriter(filename);
			
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
}