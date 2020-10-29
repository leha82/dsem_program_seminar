package maze.challengemode;

import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class LogManager {
	DBManager dbm;

	public LogManager() {
		this.dbm = new DBManager();
	}

	public ArrayList<LogRank> getRankingList(String mapName) {
		// Todo : 스트링 arraylist를 LogRank로 수정
		ArrayList<LogRank> rankList = new ArrayList<LogRank>();

		// 데이터베이스 접속
		dbm.connectDB();

		// 테이블의 데이터 획득
		try {
			// Todo : map이름도 string 혹은 LogRank 타입으로 사용할 수 있도록 변경
			String sql = "select * from maze.cmLog where map_name='" + mapName + "' order by moves, timestamp asc;";
			ResultSet rs = dbm.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				String mouse_name = rs.getString("mouse_name");
				String map_name = rs.getString("map_name");
				String timestamp = rs.getString("timestamp");
				int search_count = rs.getInt("search_count");
				int search_time = rs.getInt("search_time");
				int search_moves = rs.getInt("search_moves");
				int record_time = rs.getInt("record_time");
				int moves = rs.getInt("moves");
				rankList.add(new LogRank(id, mouse_name, map_name, timestamp, search_count, search_time, search_moves, record_time,moves));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} // 데이터베이스 접속 해제

		dbm.disconnectDB();
		return rankList;
	}

	public ArrayList<String> getMapNameList() {
		// Todo : 스트링 arraylist를 LogRank로 수정
		ArrayList<String> mapList = new ArrayList<String>();

		// 데이터베이스 접속
		dbm.connectDB();

		// 테이블의 데이터 획득
		try {
			// Todo : map이름도 string 혹은 LogRank 타입으로 사용할 수 있도록 변경
			String sql = "SELECT map_name FROM maze.map;";
			ResultSet rs = dbm.executeQuery(sql);

			while (rs.next()) {
				String map_name = rs.getString("map_name");
				mapList.add(map_name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} // 데이터베이스 접속 해제

		dbm.disconnectDB();
		return mapList;
	}

	public boolean putLog(String mouseName, String map_name, int count) {
		boolean result = true;

		// 데이터베이스에 접속
		dbm.connectDB();

		try {
			SimpleDateFormat current_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time_stamp = current_time.format(System.currentTimeMillis());
			String sql = "insert into maze.log(mouse_name, map_name, timestamp, count) values ('" + mouseName + "','"
					+ map_name + "','" + time_stamp + "', " + count + ")";
			dbm.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}

		// 데이터베이스 접속 해제
		dbm.disconnectDB();
		return result;
	}
	
	public boolean putChallengeLog(String mouseName, String map_name,  int search_time, int search_count, int search_moves, int record_time, int moves) {
		boolean result = true;
		// 데이터베이스에 접속
		dbm.connectDB();

		try {
			SimpleDateFormat current_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time_stamp = current_time.format(System.currentTimeMillis());
			String sql = "insert into maze.cmLog(mouse_name, map_name, timestamp, search_count, search_time, search_moves, record_time, moves) values ('" + mouseName + "','"
					+ map_name + "','" + time_stamp + "', " + search_count + ", " + search_time + ", " + search_moves+ ", " + record_time + ", " + moves + ")";
			dbm.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}

		// 데이터베이스 접속 해제
		dbm.disconnectDB();
		return result;
	}
	// 마우스가 이 맵에 도전한 이력이 있는지 확인
	public boolean checkChallengeLog(String mouseName,String map_name) {
		boolean result=false;
		dbm.connectDB();
		try {
			String sql = "select * from maze.cmLog where mouse_name='" + mouseName + "'AND map_name='" + map_name +"'AND record_time IS NOT NULL";
			ResultSet rs = dbm.executeQuery(sql);
			if(rs.next())
				result=true;
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			result=false;
		}
		dbm.disconnectDB();
		return result;
	}
	// 도전모드 저장
	public boolean putChallengeLog(String mouseName, String map_name, int record_time, int moves) {
		boolean result = true;

		// 데이터베이스에 접속
		dbm.connectDB();

		try {
			SimpleDateFormat current_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time_stamp = current_time.format(System.currentTimeMillis());
			String sql = "insert into maze.cmLog(mouse_name, map_name, timestamp, record_time, moves) values ('" + mouseName + "','"
					+ map_name + "','"+time_stamp+"', "+ record_time + ", " + moves + ")";
			dbm.executeUpdate(sql);
			System.out.println("d");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			result = false;
		}

		// 데이터베이스 접속 해제
		dbm.disconnectDB();
		return result;
	}
	

	public int getMinCount(String mouseName, String mapName) {
		int min_count = -1;

		// 데이터베이스 접속
		dbm.connectDB();

		try {

			String sql = "select min(count) from maze.cmlog " 
						+ "where mouse_name='" + mouseName + "' "
						+ "and map_name='" + mapName + "';";
			ResultSet rs = dbm.executeQuery(sql);

			while (rs.next()) {
				min_count = rs.getInt(1);
			}
			System.out.println(min_count);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 데이터베이스 접속 해제
		dbm.disconnectDB();
		return min_count;
	} 

	public int[] getRank(String mouseName, String mapName) {
//		String timestamp = "";
//		int search_count = -1;
//		int search_time = -1;
//		int search_moves = -1;
//		int record_time = -1;
		int[] rankArray = new int[6];

		// 데이터베이스 접속
		dbm.connectDB();

		try {

			String sql = "select timestamp, search_count, search_time, search_moves, record_time, moves from maze.cmlog " 
						+ "where mouse_name='" + mouseName + "' "
						+ "and map_name='" + mapName + ""
						+ "order by moves asc;'";
			ResultSet rs = dbm.executeQuery(sql);

			while (rs.next()) {
				
				rankArray[0] = Integer.valueOf(rs.getString(1));//timestamp
				rankArray[1] = rs.getInt(1);//search_count
				rankArray[2] = rs.getInt(1);//search_time
				rankArray[3] = rs.getInt(1);//search_moves
				rankArray[4] = rs.getInt(1);//record_time
				rankArray[5] = rs.getInt(1);//moves
			}
			System.out.println(rankArray[5]);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 데이터베이스 접속 해제
		dbm.disconnectDB();
		return rankArray;
	} 
	
	public boolean deleteLog(int id) {
		boolean result = true;

		dbm.connectDB();

		try {
			String sql = "delete from maze.log where id=" + id + ";";
			dbm.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}

		dbm.disconnectDB();
		return result;

	}

//   public static void main(String[] args) {
//      LogManager Log = new LogManager();
//      Log.putLog("woolin", 55);
//      Log.getMinCount("Sunyoung_mouse");
//   }

}