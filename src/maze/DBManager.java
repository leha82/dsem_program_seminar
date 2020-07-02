package maze;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	private static Statement stmt;
	public static void main(String[] args) {
		LogManager log = new LogManager();
		
		try {
			// 데이터베이스 접속
			log.connectDB();
			
			// 데이터베이스에서 테이블 생성
			String sql = "CREATE TABEL Log("
					+ "id int IDENTITY (1,1) NOT NULL,"
					+ "mouse_name varchar(200),"
					+ "timestamp datetime"
					+ "count int"
					+ ")";
			
			ResultSet rs = stmt.executeQuery(sql);
			stmt.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
