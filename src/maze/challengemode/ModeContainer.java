package maze.challengemode;

public class ModeContainer {
	private long elapsedTime; // ����� �ð�
	private long startTime; // ���� �ð�
	private int totalSearch;
	private long totalMove;

	// ���� �� �ʱ�ȭ
	public ModeContainer() {
		totalSearch = 0;
		totalMove = 0;
		this.init_time();
	}

	// �ʱ�ȭ
	public void init_time() {
		elapsedTime = 0;
	}

	// ����
	public void start() {
		startTime = System.currentTimeMillis();
	}

	public void addTotalSearch() {
		totalSearch++;
	}

	public void addTotalMove() {
		totalMove++;
	}

	// ����� �ð� ���ϱ�
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
