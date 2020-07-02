package maze;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	private static Statement stmt;
	public static void main(String[] args) {
		LogManager log = new LogManager();
		
		try {
			// �����ͺ��̽� ����
			log.connectDB();
			
			// �����ͺ��̽����� ���̺� ����
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
