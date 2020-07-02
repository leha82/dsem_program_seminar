package maze;

import java.sql.*;
public class DBManager {
	public static void main(String[] args) {
		LogManager log = new LogManager();
		// �����ͺ��̽� ����
		log.connectDB();			
		
		try {
			// ���̺� ����
			String createSql = "CREATE TABLE Log("
					+ "id int IDENTITY (1,1) NOT NULL,"
					+ "mouse_name varchar(200),"
					+ "timestamp datetime,"
					+ "count int)";
			log.pstmt = log.conn.prepareStatement(createSql);
		}catch (SQLException e) {
			e.printStackTrace();
		}

		// �����ͺ��̽� ���� ����
		log.disconnectDB();
	}
}
