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
		this.url = "jdbc:mysql://203.234.62.143:3306/maze?serverTimezone=Asia/Seoul ";
		this.userid = "sojin";
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
		//dbm.createLogTable();
		//dbm.createMaptable();
	}
	
	public void createLogTable() {
		connectDB();			
		
		try {
			// ���̺� ����
			// Todo : ���̺� ���� ���� ���� -> ���̸��� �߰��ǵ���
			//
			String createSql = "CREATE TABLE Log("
					+ "id int IDENTITY (1,1) NOT NULL,"
					+ "mouse_name varchar(200),"
					+ "timestamp datetime,"
					+ "count int)";
			pstmt = conn.prepareStatement(createSql);
			
			System.out.println("����");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}

		// �����ͺ��̽� ���� ����
		disconnectDB();
	}
	
	public void createMapTable() {
		connectDB();			
		
		try {
			// ���̺� ����
			// Todo : ���̺� ���� ���� ���� -> ���̸��� �߰��ǵ���, Map ���̺� ����
			// 			id : autoincrement
			//			map �̸� : varchar
			//			x size : int
			//			y size : int
			//			map : text - ex) 1,1,1,1,1,1,1:1,1,1,1,1,1,1,1 
			
			String createSql = "CREATE TABLE Map(";
//					+ "id int IDENTITY (1,1) NOT NULL,"
//					+ "mouse_name varchar(200),"
//					+ "timestamp datetime,"
//					+ "count int)";
			pstmt = conn.prepareStatement(createSql);
			
			System.out.println("����");
			
		}catch (SQLException e) {
			e.printStackTrace();
		}

		// �����ͺ��̽� ���� ����
		disconnectDB();
	}
}
