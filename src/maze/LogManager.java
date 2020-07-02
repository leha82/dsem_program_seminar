package maze;

import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class LogManager {
   private String driver;
   private String url;
   private String userid;
   private String password;

   private Connection conn;
   private Statement stmt;
   ResultSet rs;
   private PreparedStatement pstmt;
   
   public LogManager() {
      this.driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
      this.url = "jdbc:sqlserver://203.234.62.144:1433; databaseName = Maze";
      this.userid = "sonyo";
      this.password = "1234";
   }
   
   public boolean connectDB() {
      this.conn = null;
      try {
         Class.forName(this.driver);
         this.conn = DriverManager.getConnection(this.url, this.userid, this.password);
         this.stmt = this.conn.createStatement();
      } catch (Exception e) {
         System.out.println(e.toString());
         return false;
      }
      return true;
   }

   public boolean disconnectDB() {
      try {
         if (this.stmt != null)
            this.stmt.close();
         if (this.pstmt != null)
            this.pstmt.close();
         if (this.conn != null)
            this.conn.close();
      } catch (Exception e) {
         System.out.println(e.toString());
         return false;
      }
      return true;
   }
   
   public int getCount(String mouseName) {
      int count = 0;
      
      // 데이터베이스 접속
      connectDB();
      
      try {
    	  
         String sql = "select count from Log where mouse_name ='" + mouseName + "'"; 
         pstmt = conn.prepareStatement(sql);
           rs = pstmt.executeQuery();
           
           while(rs.next()) {
              count = rs.getInt("count");
           }
           System.out.println(count);
      } catch (SQLException e) {
         e.printStackTrace();
      }
      
      // 데이터베이스 접속 해제
      disconnectDB();
      return count;
   }
   
   public ArrayList<String> getRankingList() {
      ArrayList<String> rankList = new ArrayList<String>();
      
      // 데이터베이스 접속
      connectDB();

      // 테이블의 데이터 획득

      try {
         String sql = "select * from Log"; 
         pstmt = conn.prepareStatement(sql);
           rs = pstmt.executeQuery();
           
           while(rs.next()) {
              String id = rs.getString("Id");
              String Mouse = rs.getString("mouse_name");
              String timestamp= rs.getString("timestamp");
              int count = rs.getInt("count");
              LogRank logrank = new LogRank(Mouse, timestamp, count);
              rankList.add(id +"," + Mouse +"," + timestamp +"," + count);

           }
           
      } catch (SQLException e) {
         e.printStackTrace();
      }      // 데이터베이스 접속 해제
      
      disconnectDB();
      return rankList;
   }

   public boolean putLog(String mouseName, int count) {
      // 데이터베이스에 접속
      connectDB();
      
      // 시스템에서 시간을 받아 datetime 유형으로 만든다.
      // 테이블에 파라메터의 값을 넣는다
      try {
         SimpleDateFormat current_time = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
         String time_stamp = current_time.format (System.currentTimeMillis());
         String sql = "insert into Log(mouse_name, timestamp, count) values ('" + mouseName + "','" + time_stamp + "', "+ count + ")";
         stmt.executeLargeUpdate(sql);
          
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }   
      // 데이터베이스 접속 해제
      disconnectDB();
      return true;
   }
   
   public static void main(String[] args) {
      LogManager Log = new LogManager();
      Log.getCount("Sunyoung_mouse");
<<<<<<< HEAD
      Log.getRankingList();
      Log.putLog("wolin", 0);
=======
      System.out.println(Log.getRankingList().get(0));
>>>>>>> branch 'logmanage' of https://github.com/leha82/dsem_program_seminar.git
   }
   

}