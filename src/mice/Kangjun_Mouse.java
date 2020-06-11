package mice;

import maze.Mouse;

public class Kangjun_Mouse extends Mouse {
	private int dir = 1;
	public static int x;
	public static int y;
	
	
	
	public Kangjun_Mouse() {
		this.dir = 0;
	}
	
	// Mouse가 다음으로 움직일 방향을 정함
		// 0: 제자리 
		// 1: 위쪽
		// 2: 오른쪽
		// 3: 아래쪽
		// 4: 왼쪽
	
	public int nextMove(int x, int y, int[][] smap) {
		
		// 현재 방향을 기준으로 오른쪽을 검사
		
				// 오른쪽이 비어있으면 dir을 오른쪽으로 설정
				
				// 오른쪽이 막혀있으면, 직진 방향을 검사
				
				// 직진방향이 비어있으면 dir을 변함 없이
				
				// 직진방향이 막혀있으면, dir을 반대 방향 뒤로 돌기 -> nextMove();
		// 1: 위쪽
		// 2: 오른쪽
		// 3: 아래쪽
		// 4: 왼쪽
		if (dir == 1) {  //위
			if(smap[1][2] == 0)
				dir = 2;
			else if(smap[1][2] == 1) {
				if(smap[0][1] == 1) {
					dir = 3;
					return dir;
				}
				return dir;
			}
			return dir;
		}
	
		if (dir == 2) {  //오른
			if(smap[2][1] == 0)
				dir = 3;
			else if(smap[2][1] == 1) {
				if(smap[1][2] == 1) {
					dir = 4;
					return dir;
				}
				return dir;
			}
			return dir;
		}
		
		if (dir == 3) {  //아래
			if(smap[1][0] == 0)
				dir = 4;
			else if(smap[1][0] == 1) {
				if(smap[2][1] == 1) {
					dir = 1;
					return dir;
				}
				return dir;
			}
			return dir;
		}
		
		if (dir == 4) {  //왼
			if(smap[0][1] == 0)
				dir = 1;
			else if(smap[0][1] == 1) {
				if(smap[1][0] == 1) {
					dir = 2;
					return dir;
				}
				return dir;
			}
			return dir;
		}
		return dir;
	}
}
