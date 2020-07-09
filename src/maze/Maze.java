package maze;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class Maze {
	private int width, height;
	private int start_x, start_y;
	public int esc_x;
	private int esc_y;
	private int[][] map;

	public Maze() {
		this(0, 0, null);
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
					// System.out.print(map[row][i]);
				}
				// System.out.println(" ");
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

		// Todo : map 저장하고 불러오면서 2차원 배열(this.map)로 저장할 수 있도록 수정
		// mapName으로 쿼리를 날려 map의 내용을 받고, 파싱하여 this.map에 저장할 수 있도록 함
		// id : autoincrement
		// map 이름 : varchar
		// x size : int
		// y size : int
		// map : text - ex) 1,1,1,1,1,1,1:1,1,1,1,1,1,1,1

		DBManager dbm = new DBManager();
		// 데이터베이스 접속
		dbm.connectDB();
		String map = "";
		int x_size = 0;
		int y_size = 0;

		try {

			String sql = "select x_size, y_size, map from map where map_name ='" + mapName + "'";
			ResultSet rs = dbm.executeQuery(sql);

			while (rs.next()) {
				x_size = rs.getInt("x_size");
				y_size = rs.getInt("y_size");
				map = rs.getString("map");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 데이터베이스 접속 해제
		dbm.disconnectDB();

		int[][] loadmap = new int[y_size][x_size];
		String[] line_array = map.split(":");
		
		for (int i = 0; i < line_array.length; i++) {
			String[] array = line_array[i].split(",");
			for (int j = 0; j < array.length; j++) {
				loadmap[i][j] = Integer.parseInt(array[j]);
			}

		}
		this.map = loadmap;
	}

	public void storeMapToDB(String mapName, int[][] newMap) {
		// Todo : DB에 mapName으로 newMap을 저장한다.
		// 받아온 2차원 newMap[][]을 db에 저장할 수 있는 포맷으로 바꾸어 저장
		
		DBManager dbm = new DBManager();
		// 데이터베이스 접속
		dbm.connectDB();
		int y_size = newMap.length;
		int x_size = newMap[0].length;
		String arraymap = Arrays.deepToString(newMap);
		String textmap = arraymap.replace("[", "").replace("],", ":").replace("]", "").replace(" ", "");

		try {
			String sql = "insert into map(map_name, x_size, y_size, map) values ('" + mapName + "'," + x_size + ","+ y_size + ",'" + textmap + "')";
			dbm.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 데이터베이스 접속 해제
		dbm.disconnectDB();

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
		int[] index = { -1, 0, 1 };

//		System.out.println("(" + x + "," + y +")");
		for (int i = 0; i < smap.length; i++) {
			for (int j = 0; j < smap[0].length; j++) {
				if (x + index[j] < 0 || y + index[i] < 0 || x + index[j] > this.width - 1
						|| y + index[i] > this.height - 1) {
					smap[i][j] = -1;
				} else {
					smap[i][j] = map[y + index[i]][x + index[j]];
				}

				// System.out.print(smap[i][j] + " ");
			}
			// System.out.println();
		}
		// System.out.print(smap[x+1][y+1] + " ");
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
