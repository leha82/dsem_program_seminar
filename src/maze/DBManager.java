package maze;

import java.sql.*;
public class DBManager {
	public static void main(String[] args) {
		LogManager log = new LogManager();
		// 单捞磐海捞胶 立加
		log.connectDB();			
		
		try {
			// 抛捞喉 积己
			String createSql = "CREATE TABLE Log("
					+ "id int IDENTITY (1,1) NOT NULL,"
					+ "mouse_name varchar(200),"
					+ "timestamp datetime,"
					+ "count int)";
			log.pstmt = log.conn.prepareStatement(createSql);
		}catch (SQLException e) {
			e.printStackTrace();
		}

		// 单捞磐海捞胶 立加 秦力
		log.disconnectDB();
	}
}
