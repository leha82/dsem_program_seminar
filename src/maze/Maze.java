package maze;

import java.io.*;

public class Maze {
	private int width, height;
	private int[][] map;
	private String[] token;
	public Maze() {
		this(0,0,null);
	}
	
	public Maze(int width, int height, int[][] map) {
		this.width = width;
		this.height = height;
		this.map = map;
	}
	
	public Maze(String filename) {
		map = new int[7][7];
		try {
			File file = new File(filename);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			line = br.readLine();
			int row = 0;

			while ((line = br.readLine()) != null) {
				token = line.split(" ");
				for (int i = 0; i < token.length; i++) {
					map[row][i] = Integer.parseInt(token[i]);
				}
				for (int i = 0; i < token.length; i++) {
					System.out.print(map[row][i]);
				}
				System.out.println(" ");
				row++;
			}
			this.width =token.length;
			this.height = token.length;
			this.map = map;	
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// file에서 map 읽어오기 #2
		
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
		
		// 지도에서 해당 위치 주변의 9칸을 배열로 만들어 리턴한다. #3
		int[] index = {-1, 0, 1};
		
		for (int i=0;i<smap.length;i++) {
			for (int j=0;j<smap[0].length;j++) {
				if (x+index[i] < 0 || y+index[j] < 0 || x+index[i] > 6 || y+index[j] > 6) {
					smap[i][j] = -1;
				}
				else {
					smap[i][j] = map[x+index[i]][y+index[j]];
				}
			}
		}
		
		return smap;
	}

}
