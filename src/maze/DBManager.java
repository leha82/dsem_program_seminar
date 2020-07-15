package maze;

import java.sql.*;
public class DBManager {
	private String driver;
	private String url;
	private String userid;
	private String password;

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	
	public DBManager() {

		// Todo : DB�� mysql �� �ٲٰ�, ���� �ּ� ����		
		this.driver = "com.mysql.cj.jdbc.Driver";
		this.url = "jdbc:mysql://203.234.62.134:3306/maze?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&useSSL=false";
		this.userid = "root";
		this.password = "1234";
	}
	
	public boolean connectDB() {
		this.conn = null;
		try {
			Class.forName(this.driver);
			this.conn = DriverManager.getConnection(this.url, this.userid, this.password);
			System.out.println("Connection Successed  -- mysql --");
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
	
	public ResultSet executeQuery(String sql) throws SQLException {
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		return rs;
	}
		
	public void executeUpdate(String sql) throws SQLException {
//		Statement stmt = this.conn.createStatement();
		stmt.executeUpdate(sql);
	}
	
	public static void main(String[] args) {
		DBManager dbm = new DBManager();
		// �����ͺ��̽� ����
		dbm.connectDB();
		// log, map ���̺� ����
		//dbm.createLogTable();
		//dbm.createMapTable();
	}
	
	public void createLogTable() {
		connectDB();			
		
		try {
			// ���̺� ����
			// Todo : ���̺� ���� ���� ���� -> ���̸��� �߰��ǵ���
			// id, mouse_name, map_name, timestamp, count
			String sql = "CREATE TABLE maze.log("
					+ "id INT AUTO_INCREMENT,"
					+ "mouse_name VARCHAR(200),"
					+ "map_name VARCHAR(200),"
					+ "timestamp DATETIME,"
					+ "count INT,"
					+ "PRIMARY KEY(id))";
			stmt.execute(sql);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		disconnectDB();
	}
	
	public void createMapTable() {
		connectDB();			
		
		try {
			// ���̺� ����
			// Todo : ���̺� ���� ���� ���� -> ���̸��� �߰��ǵ���, Map ���̺� ����
			// id, map_name, x_size, y_size, map
			
			String sql = "CREATE TABLE maze.map("
					+ "id INT AUTO_INCREMENT,"
					+ "map_name VARCHAR(200),"
					+ "x_size INT,"
					+ "y_size INT,"
					+ "map TEXT,"
					+ "PRIMARY KEY(id))";
			stmt.execute(sql);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		disconnectDB();
	}
}
