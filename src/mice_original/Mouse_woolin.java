package mice_original;

import boot.Mouse;

public class Mouse_woolin extends Mouse {
	private int dir = 1;  // 처음 방향성 (1)
	public Mouse_woolin() {

	}
	
	// Mouse가 다음으로 움직일 방향을 정한다.
	// (1) : 위쪽, (2) : 오른쪽, (3) :아래쪽, (4) : 왼쪽
	public int nextMove(int x, int y, int[][] smap) {
		
		if (dir == 1) {  // 만약 현재 방향이 (1)이면
			if (smap[1][2] == 0) {  // 오른쪽이 갈 수 있으면
				dir = 2;
				return dir;
			} else if (smap[0][1] == 0) {  // 직진 방향(위)
				dir = 1;
				return dir;
			} else if (smap[1][0] == 0) {  // 왼쪽 갈 수 있으면
				dir = 4;
				return dir;
			}
			else {  // 갈 수 있는길이 더이상 엎다면 방향 반전해서 뒤로 이동
				dir = 3; 
				return dir;
			}
		}
		
		if (dir == 2) {   // 현재 방향이 (2)이면
			if (smap[2][1] == 0) {
				dir = 3;
				return dir;
			} else if (smap[1][2] == 0) {
				dir = 2;
				return dir;
			} else if (smap[0][1] == 0) {
				dir = 1;
				return dir;
			}
			else {
				dir = 4;
				return dir;
			}
		}
		
		if (dir == 3) {   // 현재 방향이 (3)이면
			if (smap[1][0] == 0) {
				dir = 4;
				return dir;
			} else if(smap[2][1] == 0) {
				dir = 3;
				return dir;
			} else if (smap[1][2] == 0) {
				dir = 2;
				return dir;
			}
			else {
				dir = 1;
				return dir;
			}
		}
		
		if (dir == 4) {   // 현재 방향이 (4)이면
			if (smap[0][1] == 0) {
				dir = 1;
				return dir;
			} else if (smap[1][0] == 0) {
				dir = 4;
				return dir;
			} else if (smap[2][1] == 0) {
				dir = 3;
				return dir;
			}
			else {
				dir = 2;
				return dir;
			}
		}
		
		return dir;
	}

}
