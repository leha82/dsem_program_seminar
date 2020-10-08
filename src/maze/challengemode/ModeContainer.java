package maze.challengemode;

public class ModeContainer {
	private long elapsedTime; // 경과된 시간
	private long startTime; // 시작 시간
	private int totalSearch;
	private long totalMove;

	// 선언 및 초기화
	public ModeContainer() {
		totalSearch = 0;
		totalMove = 0;
		this.init_time();
	}

	// 초기화
	public void init_time() {
		elapsedTime = 0;
	}

	// 세팅
	public void start() {
		startTime = System.currentTimeMillis();
	}

	public void addTotalSearch() {
		totalSearch++;
	}

	public void addTotalMove() {
		totalMove++;
	}

	// 경과된 시간 구하기
	public long check() {
//	synchronized public long check() {
		elapsedTime = System.currentTimeMillis() - startTime;
		return elapsedTime;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public int getTotalSearch() {
		return totalSearch;
	}

	public long getTotalMove() {
		return totalMove;
	}
}
