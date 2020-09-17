package maze.mazemaker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class RMC_sunyoung2 extends RandomMapCreator{
	public RMC_sunyoung2() {
		this("", 0, 0);
	}

	public RMC_sunyoung2(String mapName, int x_size, int y_size) {
		super(mapName, x_size, y_size);
	}

	public void createMap() {
		// Todo: 여기에서 map을 생성한다.
		int[][] randomMap = new int[x_size][y_size];
		this.map = randomMap;
		for (int i = 0; i < randomMap.length; i++) {
			for (int j = 0; j < randomMap[i].length; j++) {
				int n = (int) ((Math.random() * 2));
				randomMap[i][j] = n;
				if((i==start_x&&j == start_y)||(i==esc_x&&j==esc_y)) {
					randomMap[i][j]= 0 ;
				}
			}
		}
		System.out.println(randomMap.length/2);
		for (int i = 0; i<randomMap.length; i++) {
			randomMap[randomMap.length/2][i]= 1 ;
			randomMap[i][randomMap.length/2]= 1 ;

		}
	

	}

	public static void main(String[] args) {
		RMC_sunyoung2 rmc = new RMC_sunyoung2("random1", 30, 30);

		rmc.createMap();
		rmc.printMap();
//		rmc.makeMapFile();
	}

	public void makeMapFile() {
		// Todo : 맵 파일로 생성 testmap.txt와 동일한 형식으로.
		// 파일로 저장할때 maps 폴더에 .txt확장자를 붙여서 파일로 저장할 것
		// this.mapName 활용

//		File f = new File("C:\\dsem_seminar\\dsem_program_seminar\\maps\\" + mapName + ".txt");
		File f = new File("C:\\Users\\sonyo\\Desktop\\semina\\dsem_program_seminar\\maps\\" + mapName + ".txt");
		
		if (f.exists()) {
			int index = 1;
			mapName = mapName + "_" + index;

			while (true) {
				 f = new File("C:\\Users\\sonyo\\Desktop\\semina\\dsem_program_seminar\\maps\\" + mapName + ".txt");
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
//			FileWriter w = new FileWriter("C:\\dsem_seminar\\dsem_program_seminar\\maps\\" + mapName + ".txt");
			FileWriter w = new FileWriter("C:\\Users\\sonyo\\Desktop\\semina\\dsem_program_seminar\\maps\\" + mapName + ".txt");
			w.write(x_size + " " + y_size + "\n");
			w.write(start_x + " " + start_y + "\n");
			w.write(esc_x + " " + esc_y + "\n");
			for (int i = 0; i < y_size; i++) {
				for (int j = 0; j < x_size; j++) {
					w.write(map[j][i] + " ");
				}
				w.write("\n");
			}
			w.close();

		} catch (IOException e) {
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
		
//		for (int i = 0; i < map.length; i++) {
//			for (int j = 0; j < map[i].length; j++) {
//				System.out.print(map[i][j] + " ");
////				System.out.print(" ");
//			}
//			System.out.println();
//		}
//		System.out.println();
	}
}
