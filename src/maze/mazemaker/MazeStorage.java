package maze.mazemaker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import maze.DBManager;

public class MazeStorage {
// Todo : ���ϸ��� string���� �����ϸ� �ش� ������ �о� DB�� ����
// �����ͺ��̽��� ������ �� ������� Ȯ���ڴ� ���ְ� ���� ���ϸ����� maze�̸��� ���� ��
	private static String dirname =  "maps/";
	private static String extension = ".txt";
	private static String filename = "RMCSHsmpl100";
	private int width, height;
	private int start_x, start_y;
	private int esc_x, esc_y;
	private int[][] map;

	
	public void readFile(String filename) {
		try {
			File file = new File(filename);
			BufferedReader br = new BufferedReader(new FileReader(file));

			String line = br.readLine();
			String[] token = line.split(" ");
			width = Integer.parseInt(token[0]);
			height = Integer.parseInt(token[1]);

			line = br.readLine();
			token = line.split(" ");
			start_x = Integer.parseInt(token[0]);
			start_y = Integer.parseInt(token[1]);

			line = br.readLine();
			token = line.split(" ");
			esc_x = Integer.parseInt(token[0]);
			esc_y = Integer.parseInt(token[1]);

			map = new int[this.height][this.width];

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
		// file���� map �о�� #2

	}
	
	public boolean fileToDB(String mapName) {
		DBManager dbm = new DBManager();
		// �����ͺ��̽� ����
		dbm.connectDB();

		String textmap = Arrays.deepToString(map);
		try {
			String sql = "select map_name from map";
			ResultSet rs = dbm.executeQuery(sql);
			
			while(rs.next()) {
				String map_name = rs.getString("map_name");
				if(mapName.equals(map_name)) {
					System.out.println("�ߺ� �� �Դϴ�.");
					return false;
				}
			}
			String sql2 = "insert into map(map_name, x_size, y_size, start_x, start_y, esc_x, esc_y, map) " 
					+ "values ('" + mapName + "'," + width + "," + height + "," + start_x + "," + start_y + ","
					+ esc_x + "," + esc_y + ",'" + textmap + "')";
			dbm.executeUpdate(sql2);
			System.out.println(mapName + " DB�� ���� �Ϸ�");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// �����ͺ��̽� ���� ����
		dbm.disconnectDB();

		return true;
	}
	
	public static void main(String[] args) {
		MazeStorage ms = new MazeStorage();
		ms.readFile(dirname+filename+extension);
		ms.fileToDB(filename);
	}
}
