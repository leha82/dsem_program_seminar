package mice;

import boot.MouseChallenge;

public class RandomMouse extends MouseChallenge {
	
	public RandomMouse() {
		
	}
	
	// Mouse가 다음으로 움직일 방향을 정한다.
	// 0: 제자리
	// 1: 위쪽
	// 2: 오른쪽
	// 3: 아래쪽
	// 4: 왼쪽
	public int nextMove(int[][] smap) {
		int dir = (int)(Math.random()*100) % 4 + 1;
		checkMoved();
		return dir;
	}
		
	public int nextSearch(int[][] smap) {
		int dir = (int)(Math.random()*100) % 4 + 1;
		checkMoved();
		return dir;
	}


}
