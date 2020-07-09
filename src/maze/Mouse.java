package maze;

public abstract class Mouse {
	// Mouse가 다음으로 움직일 방향을 정한다.
	// 0: 제자리
	// 1: 위쪽
	// 2: 오른쪽
	// 3: 아래쪽
	// 4: 왼쪽
	public abstract int nextMove(int x, int y, int[][] smap);
	
	public void printClassName() {
		System.out.println("Mouse");
	}
}
