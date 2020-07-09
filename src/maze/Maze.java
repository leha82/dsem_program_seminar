package maze;

import java.io.*;

public class Maze {
	private int width, height;
	private int start_x, start_y;
	public int esc_x;
	private int esc_y;
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
		try {
			File file = new File(filename);
			BufferedReader br = new BufferedReader(new FileReader(file));

			String line = br.readLine();
			String[] token = line.split(" ");
			this.width = Integer.parseInt(token[0]);
			this.height = Integer.parseInt(token[1]);

			line = br.readLine();
			token = line.split(" ");
			this.start_x = Integer.parseInt(token[0]);
			this.start_y = Integer.parseInt(token[1]);

			line = br.readLine();
			token = line.split(" ");
			this.esc_x = Integer.parseInt(token[0]);
			this.esc_y = Integer.parseInt(token[1]);

			
			this.map = new int[this.height][this.width];

			int row = 0;
			while ((line = br.readLine()) != null) {
				token = line.split(" ");
				for (int i = 0; i < token.length; i++) {
					map[row][i] = Integer.parseInt(token[i]);
					//System.out.print(map[row][i]);
				}
				//System.out.println(" ");
				row++;
			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// file에서 map 읽어기 #2
		
	}
	
	
	public void loadMapFromDB(String mapName) {
		// Todo : map 저장하고 불러오면서 2차원 배열로 저장할 수 있도록 수정
//		map 이름
//		x size
//		y size
//		map 1,1,1,1,1,1,1:1,1,1,1,1,1,1,1
		
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
	
	public int getMapPoint(int x, int y) {
		return this.map[y][x];
	}
	
	public int[][] getArea(int x, int y) {
		int[][] smap = new int[3][3];
		
		// 지도에서 해당 위치 주변의 9칸을 배열로 만들어 리턴한다. #3
		int[] index = {-1, 0, 1};
		
//		System.out.println("(" + x + "," + y +")");
		for (int i=0;i<smap.length;i++) {
			for (int j=0;j<smap[0].length;j++) {
				if (x+index[j] < 0 || y+index[i] < 0 || 
					x+index[j] > this.width-1 || y+index[i] > this.height-1) {
					smap[i][j] = -1;
				}
				else {
					smap[i][j] = map[y+index[i]][x+index[j]];
				}
				
				//System.out.print(smap[i][j] + " ");
			}
			//System.out.println();
		}
	//	System.out.print(smap[x+1][y+1] + " ");
		return smap;
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

}
