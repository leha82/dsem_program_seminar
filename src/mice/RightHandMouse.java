package mice;

import maze.Mouse;

public class RightHandMouse extends Mouse {
	private int dir;
	
	public RightHandMouse() {
		this.dir = 0;
	}
	
	@Override
	public void printClassName() {
		System.out.println("RightHandMouse");
	}
	
	// Mouse가 다음으로 움직일 방향을 정한다.
	// 0: 제자리
	// 1: 위쪽
	// 2: 오른쪽
	// 3: 아래쪽
	// 4: 왼쪽
	@Override
	public int nextMove(int x, int y, int[][] smap) {
		// 현재 방향을 기준으로 오른쪽을 검사
		
		// 오른쪽이 비어있으면 dir을 오른쪽으로 설정
		
		// 오른쪽이 막혀있으면, 직진 방향을 검사
		
		// 직진방향이 비어있으면 dir을 변함 없이
		
		// 직진방향이 막혀있으면, dir을 반대 방향 뒤로 돌기 -> nextMove();
		
		return dir;
	}

}
