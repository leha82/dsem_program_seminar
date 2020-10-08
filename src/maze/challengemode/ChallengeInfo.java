package maze.challengemode;

public class ChallengeInfo {
	private long elapsedTime; // 경과된 시간
	private long startTime; // 시작 시간
//	private int totalSearch;
//	private long totalMove;
	
	private int totalSearchCount;

	private int searchCount;
	private long searchTime[];
	private int searchMove[];

	private long totalSearchTime;
	private int totalSearchMove;

	
	private long challengeTime;
	private int challengeMove;
	
	
	// 선언 및 초기화
	public ChallengeInfo() {
		this.totalSearchCount = 3;
		this.initialize();
	}
	
	public void initialize() {
		this.elapsedTime = 0;
		this.startTime = 0;
		
		this.searchCount = 0;
		this.searchTime = new long[totalSearchCount];
		this.searchMove = new int[totalSearchCount];
		
		this.totalSearchTime = 0;
		this.totalSearchMove = 0;
		
		this.challengeTime = 0;
		this.challengeMove = 0;
	}

	// 초기화
	public void init_time() {
		elapsedTime = 0;
	}

	// 세팅
	public void start() {
		startTime = System.currentTimeMillis();
	}

	public void addSearchCount() {
		searchCount++;
	}

	public void addSearchMove() {
		searchMove[searchCount]++;
		totalSearchMove++;
	}

	// 경과된 시간 구하기
	public long check() {
//	synchronized public long check() {
		elapsedTime = System.currentTimeMillis() - startTime;
		searchTime[searchCount] = elapsedTime;
		
		return elapsedTime;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public int getSearchCount() {
		return searchCount;
	}

	public int getSearchMove() {
		return searchMove[searchCount];
	}
	
	public int getTotalSearchMove( ) {
		return totalSearchMove;
	}
	
	public long getSearchTime() {
		return searchTime[searchCount];
	}
	
	public long getSearchTime(int index) {
		return searchTime[index];
	}
 
	public long getTotalSearchTime() {
		for (int i=0; i<=searchCount; i++) {
			totalSearchTime += searchTime[i];
		}
		
		return totalSearchTime;
	}
}
