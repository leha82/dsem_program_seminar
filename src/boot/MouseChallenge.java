package boot;

public abstract class MouseChallenge {
	// Mouse의 시작 점은 동일하다.
	private boolean moved;
	
	public MouseChallenge() {
		this.moved = false;
	}
	
	// Mouse가 다음으로 움직일 방향을 정한다.
	// 0: 제자리, 1: 위쪽, 2: 오른쪽, 3: 아래쪽, 4: 왼쪽, -1 : 탐색 종료

	public void initMouse() {
		this.moved = false;
	}
	
	public void checkMoved() {
		this.moved = true;
	}
	
	public boolean isMoved() {
		return this.moved;
	}
	
	// smap의 구성
	// 현재 위치 smap의 가운데 (smap이 3x3 일 경우 smap[1][1]에 mouse위치
	// smap[y][x]의 값 0: 길, 1: 벽,  
	// 탐색 모드의 움직임 리턴
	public abstract int nextSearch(int[][] smap);

	// 도전 모드의 움직임 리턴
	public abstract int nextMove(int[][] smap);

	public void printClassName() {
		System.out.println("Mouse");
	}
}
