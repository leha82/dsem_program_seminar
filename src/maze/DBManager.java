package maze;

import java.sql.*;
public class DBManager {

	public static void main(String[] args) {
		LogManager log = new LogManager();
		// 데이터베이스 접속
		log.connectDB();			
		
		try {
			// 테이블 생성
			// Todo : 테이블 생성 쿼리 수정 -> 맵이름이 추가되도록, Map 테이블 생성
			//
			String createSql = "CREATE TABLE Log("
					+ "id int IDENTITY (1,1) NOT NULL,"
					+ "mouse_name varchar(200),"
					+ "timestamp datetime,"
					+ "count int)";
			log.pstmt = log.conn.prepareStatement(createSql);
			
			System.out.println("생성");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}

		// 데이터베이스 접속 해제
		log.disconnectDB();
	}
}
