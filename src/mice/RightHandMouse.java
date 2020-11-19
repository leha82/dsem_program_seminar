package mice;

import boot.MouseChallenge;

public class RightHandMouse extends MouseChallenge {
	private int dir;

	public RightHandMouse() {
		this.dir = 1;
	}

	@Override
	public void printClassName() {
		System.out.println("RightHandMouse");
	}

	// Mouse가 다음으로 움직일 방향을 정한다.
	// 0: 제자리, 1: 위쪽, 2: 오른쪽, 3: 아래쪽, 4: 왼쪽, -1 : 탐색 종료

	public int nextMove(int[][] smap) {
		int[] rp_X = { 0, 2, 1, 0, 1 }; // right position by current dir
		int[] rp_Y = { 0, 1, 2, 1, 0 };

		int[] sp_X = { 0, 1, 2, 1, 0 }; // straight position by current dir
		int[] sp_Y = { 0, 0, 1, 2, 1 };

		int[] rt_Dir = { 0, 2, 3, 4, 1 };
		int[] ut_Dir = { 0, 3, 4, 1, 2 };

		this.checkMoved();
		
		// 현재 방향을 기준으로 오른쪽을 검사
		if (smap[rp_Y[dir]][rp_X[dir]] != 1) {
			// 오른쪽이 비어있으면 dir을 오른쪽으로 설정
			dir = rt_Dir[dir];
		} else if (smap[sp_Y[dir]][sp_X[dir]] != 1) {
			// 오른쪽이 막혀있으면, 직진 방향을 검사
			// 직진방향이 비어있으면 dir을 변함 없이

		} else {
			// 직진방향이 막혀있으면, dir을 반대 방향 뒤로 돌기 -> nextMove();
			dir = ut_Dir[dir];
			dir = nextMove(smap);
		}

		return dir;
	}
	
	public int nextSearch(int[][] smap) {
		int[] rp_X = { 0, 2, 1, 0, 1 }; // right position by current dir
		int[] rp_Y = { 0, 1, 2, 1, 0 };

		int[] sp_X = { 0, 1, 2, 1, 0 }; // straight position by current dir
		int[] sp_Y = { 0, 0, 1, 2, 1 };

		int[] rt_Dir = { 0, 2, 3, 4, 1 };
		int[] ut_Dir = { 0, 3, 4, 1, 2 };

		this.checkMoved();
		
		// 현재 방향을 기준으로 오른쪽을 검사
		if (smap[rp_Y[dir]][rp_X[dir]] != 1) {
			// 오른쪽이 비어있으면 dir을 오른쪽으로 설정
			dir = rt_Dir[dir];
		} else if (smap[sp_Y[dir]][sp_X[dir]] != 1) {
			// 오른쪽이 막혀있으면, 직진 방향을 검사
			// 직진방향이 비어있으면 dir을 변함 없이

		} else {
			// 직진방향이 막혀있으면, dir을 반대 방향 뒤로 돌기 -> nextMove();
			dir = ut_Dir[dir];
			dir = nextSearch(smap);
		}

		return dir;
	}


}