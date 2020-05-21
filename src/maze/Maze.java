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
		// file���� map �о���� #2
		
	}
	
	public int[][] getArea(int x, int y) {
		int[][] smap = new int[3][3];
		// �������� �ش� ��ġ �ֺ��� 9ĭ�� �迭�� ����� �����Ѵ�. #3
		return smap;
	}

}
