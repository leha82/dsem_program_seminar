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

      // 데이터베이스 접속
      dbm.connectDB();

      try {

         String sql = "select count from maze.log where mouse_name ='" + mouseName + "'";
         ResultSet rs = dbm.executeQuery(sql);

         while (rs.next()) {
            count = rs.getInt("count");
         }
//         System.out.println(count);
      } catch (SQLException e) {
         e.printStackTrace();
      }

      // 데이터베이스 접속 해제
      dbm.disconnectDB();
      
      return count;
   }

   public ArrayList<LogRank> getRankingList(String mapName) {
      // Todo : 스트링 arraylist를 LogRank로 수정
      ArrayList<LogRank> rankList = new ArrayList<LogRank>();

      // 데이터베이스 접속
      dbm.connectDB();

      // 테이블의 데이터 획득
      try {
         // Todo : map이름도 string 혹은 LogRank 타입으로 사용할 수 있도록 변경
         String sql = "select * from maze.log where map_name='" + mapName 
        		 	+ "' order by count, timestamp asc;";
         ResultSet rs = dbm.executeQuery(sql);

         while (rs.next()) {
            int id = rs.getInt("id");
            String mouse_name = rs.getString("mouse_name");
            String map_name = rs.getString("map_name");
            String timestamp = rs.getString("timestamp");
            int count = rs.getInt("count");
            rankList.add(new LogRank(id,mouse_name,map_name,timestamp, count));
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

      // Todo : 맵이름 추가하여 로그에 넣는다.
      // 시스템에서 시간을 받아 datetime 유형으로 만든다.
      // 테이블에 파라메터의 값을 넣는다
      try {
         SimpleDateFormat current_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String time_stamp = current_time.format(System.currentTimeMillis());
         String sql = "insert into maze.log(mouse_name, map_name, timestamp, count) values ('" 
         + mouseName + "','" + map_name + "','" + time_stamp + "', " + count + ")";
         dbm.executeUpdate(sql);
      } catch (SQLException e) {
         e.printStackTrace();
         result = false;
      }
      
      // 데이터베이스 접속 해제
      dbm.disconnectDB();
      return result;
   }

   public int getMinCount(String mouseName) {
      int min_count = -1;

      // 데이터베이스 접속
      dbm.connectDB();

      try {

         String sql = "select count from maze.log "
               + "where mouse_name='" + mouseName + "';";
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

//   public static void main(String[] args) {
//      LogManager Log = new LogManager();
//      Log.putLog("woolin", 55);
//      Log.getMinCount("Sunyoung_mouse");
//   }

}