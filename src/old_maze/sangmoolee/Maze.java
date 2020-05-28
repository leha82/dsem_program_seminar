package old_maze.sangmoolee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Maze {
	public static int[][] map2;
	public static String mapfile = "maps/testmap.txt";
	public static int width, height;

	public static void main(String[] args) {
		map2 = new int[7][7];
		try {
			File file = new File(mapfile);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			line = br.readLine();
			int row = 0;

			while ((line = br.readLine()) != null) {
				String[] token = line.split(" ");
				for (int i = 0; i < token.length; i++) {
					map2[row][i] = Integer.parseInt(token[i]);
				}
				for (int i = 0; i < token.length; i++) {
					System.out.print(map2[row][i]);
				}
				System.out.println(" ");
				row++;
			}
			
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// file에서 map 읽어오기 #2
//		this.width = width;
//		this.height = height;

	}
}
