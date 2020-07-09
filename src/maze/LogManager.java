package maze;

import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class LogManager {
	DBManager dbm;

	public LogManager() {
		this.dbm = new DBManager();
	}
	
	public int getCount(String mouseName) {
		int count = 0;

		// �����ͺ��̽� ����
		dbm.connectDB();

		try {

			String sql = "select count from Log where mouse_name ='" + mouseName + "'";
			ResultSet rs = dbm.executeQuery(sql);

			while (rs.next()) {
				count = rs.getInt("count");
			}
//			System.out.println(count);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// �����ͺ��̽� ���� ����
		dbm.disconnectDB();
		
		return count;
	}

	public ArrayList<String> getRankingList() {
		// Todo : ��Ʈ�� arraylist�� LogRank�� ����
		ArrayList<String> rankList = new ArrayList<String>();

		// �����ͺ��̽� ����
		dbm.connectDB();

		// ���̺��� ������ ȹ��
		try {
			// Todo : map�̸��� string Ȥ�� LogRank Ÿ������ ����� �� �ֵ��� ����
			String sql = "select * from Log order by count asc";
			ResultSet rs = dbm.executeQuery(sql);

			while (rs.next()) {
				String id = rs.getString("id");
				String mouse_name = rs.getString("mouse_name");
				String map_name = rs.getString("map_name");
				String timestamp = rs.getString("timestamp");
				int count = rs.getInt("count");
				rankList.add(id + "," + mouse_name + "," + map_name + "," + timestamp + "," + count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} // �����ͺ��̽� ���� ����
		System.out.println(rankList);
		
		dbm.disconnectDB();
		return rankList;
	}

	public boolean putLog(String mouseName, String map_name, int count) {
		boolean result = true;
		
		// �����ͺ��̽��� ����
		dbm.connectDB();

		// Todo : ���̸� �߰��Ͽ� �α׿� �ִ´�.
		// �ý��ۿ��� �ð��� �޾� datetime �������� �����.
		// ���̺� �Ķ������ ���� �ִ´�
		try {
			SimpleDateFormat current_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time_stamp = current_time.format(System.currentTimeMillis());
			String sql = "insert into Log(mouse_name, map_name, timestamp, count) values ('" 
			+ mouseName + "','" + map_name + "','" + time_stamp + "', " + count + ")";
			dbm.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}
		
		// �����ͺ��̽� ���� ����
		dbm.disconnectDB();
		return result;
	}

	public int getMinCount(String mouseName) {
		int min_count = -1;

		// �����ͺ��̽� ����
		dbm.connectDB();

		try {

			String sql = "select count as min_count from Log "
					+ "where mouse_name='" + mouseName + "';";
			ResultSet rs = dbm.executeQuery(sql);

			while (rs.next()) {
				min_count = rs.getInt("min_count");
			}
			System.out.println(min_count);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// �����ͺ��̽� ���� ����
		dbm.disconnectDB();
		return min_count;
	}

//	public static void main(String[] args) {
//		LogManager Log = new LogManager();
//		Log.putLog("woolin", 55);
//		Log.getMinCount("Sunyoung_mouse");
//	}

}