package maze;

import java.util.*;

public class LogManager {
	
	
	public LogManager() {
	}
	
	public boolean connectDB() {
		return true;
	}
	
	public boolean disconnectDB() {
		return true;
	}
	
	public int getCount(String mouseName) {
		int count = 0;
		
		// 데이터베이스 접속
		
		// 해당 이름을 이용하여 count를 받아옴
		
		// 데이터베이스 접속 해제
		
		return count;
	}
	
	public ArrayList<LogRank> getRankingList() {
		ArrayList<LogRank> rankList = new ArrayList<LogRank>();
		// 데이터베이스 접속
		
		// 테이블의 데이터 획득
		
		// rankList를 만든다 
		
		// 데이터베이스 접속 해제
		return rankList;
	}

	public boolean putLog(String mouseName, int count) {
		// 데이터베이스에 접속
		
		// 시스템에서 시간을 받아 datetime 유형으로 만든다.
		
		// 테이블에 파라메터의 값을 넣는다
		
		// 데이터베이스 접속 해제
		
		return true;
	}
	

}
