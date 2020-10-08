package boot;

public abstract class MouseChallenge extends Mouse {
	private int esc_x, esc_y;
	
	// Mouse가 다음으로 움직일 방향을 정한다.
	// 0: 제자리
	// 1: 위쪽
	// 2: 오른쪽
	// 3: 아래쪽
	// 4: 왼쪽
	// -1 : 탐색 종료

	// Mouse의 nextMove 메소드는 도전모드에서의 움직임을 리턴하도록
	
	// 탐색 모드의 움직임 리턴
	public abstract int nextSearch(int x, int y, int[][] smap);

	public void setEscapePoint(int esc_x, int esc_y) {
		this.esc_x = esc_x;
		this.esc_y = esc_y;
	}
	
	public void printMouseInfo() {
		System.out.println("Escape point x: " + esc_x + ", " + esc_y);
	}
}
