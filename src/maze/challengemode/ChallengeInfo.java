package maze.challengemode;

public class ChallengeInfo {
	private int limitSearchCount;
	private int limitSearchMove;
	private int limitSearchTime;
	private int limitChallengeTime;
	
	private int delaySearch;
	private int delayChallenge;

	private int searchCount;
	private long searchTime[];
	private int searchMove[];

	private long totalSearchTime;
	private int totalSearchMove;
	
	private long challengeTime;
	private int challengeMove;
	private boolean challengeDone;
	
	
	// 선언 및 초기화
	public ChallengeInfo() {
		this.limitSearchCount = 3;
		this.limitSearchMove = 30;
		this.limitSearchTime = 5000;
		this.limitChallengeTime = 5000;
		this.delaySearch = 1;
		this.delayChallenge = 1;

		this.initialize();
	}
	
	public void initialize() {
		this.searchCount = -1;
		this.searchTime = new long[limitSearchCount];
		this.searchMove = new int[limitSearchCount];
		
		this.totalSearchTime = 0;
		this.totalSearchMove = 0;
		
		this.challengeTime = 0;
		this.challengeMove = 0;
		
		this.challengeDone = false;
	}

	public void calculateTotalSearch() {
		this.totalSearchTime = 0;
		this.totalSearchMove = 0;
		
		for (int i=0; i<=searchCount; i++) {
			this.totalSearchTime += this.searchTime[i];
			this.totalSearchMove += this.searchMove[i];
		}
	}
	
	public void addSearchCount() {
		searchCount++;
	}
	
	public void addOneSearchMove() {
		searchMove[searchCount]++;
	}
	
	public void addOneChallengeMove() {
		challengeDone = true;
		challengeMove++;
	}
		
	public boolean isChallengeDone() {
		return this.challengeDone;
	}
		
	public int getLimitSearchCount() {
		return limitSearchCount;
	}

	public void setLimitSearchCount(int limitSearchCount) {
		this.limitSearchCount = limitSearchCount;
	}

	public int getLimitSearchMove() {
		return limitSearchMove;
	}

	public void setLimitSearchMove(int limitSearchMove) {
		this.limitSearchMove = limitSearchMove;
	}

	public int getLimitSearchTime() {
		return limitSearchTime;
	}

	public void setLimitSearchTime(int limitSearchTime) {
		this.limitSearchTime = limitSearchTime;
	}

	public int getLimitChallengeTime() {
		return limitChallengeTime;
	}

	public void setLimitChallengeTime(int limitChallengeTime) {
		this.limitChallengeTime = limitChallengeTime;
	}

	public int getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}

	public long[] getSearchTime() {
		return searchTime;
	}
	
	public long getLastSearchTime() {
		return searchTime[searchCount];
	}

	public void setLastSearchTime(long time) {
		this.searchTime[searchCount] = time;
	}
	
	public void setSearchTime(long[] searchTime) {
		this.searchTime = searchTime;
	}

	public int[] getSearchMove() {
		return searchMove;
	}

	public int getLastSearchMove() {
		return searchMove[searchCount];
	}
	
	public void setLastSearchMove(int move) {
		this.searchMove[searchCount] = move;
	}
	
	public void setSearchMove(int[] searchMove) {
		this.searchMove = searchMove;
	}

	public long getTotalSearchTime() {
		return totalSearchTime;
	}

	public void setTotalSearchTime(long totalSearchTime) {
		this.totalSearchTime = totalSearchTime;
	}

	public int getTotalSearchMove() {
		return totalSearchMove;
	}

	public void setTotalSearchMove(int totalSearchMove) {
		this.totalSearchMove = totalSearchMove;
	}

	public long getChallengeTime() {
		return challengeTime;
	}

	public void setChallengeTime(long challengeTime) {
		this.challengeTime = challengeTime;
	}

	public int getChallengeMove() {
		return challengeMove;
	}

	public void setChallengeMove(int challengeMove) {
		this.challengeMove = challengeMove;
	}

	public int getDelaySearch() {
		return delaySearch;
	}

	public void setDelaySearch(int delaySearch) {
		this.delaySearch = delaySearch;
	}

	public int getDelayChallenge() {
		return delayChallenge;
	}

	public void setDelayChallenge(int delayChallenge) {
		this.delayChallenge = delayChallenge;
	}

}
