package mice;
import maze.Mouse;

public class Mouse_kangjun extends Mouse {
	private int dir = 0;
	
	public Mouse_kangjun() {
		this.dir = 0;
	}
	
	// Mouse가 다음으로 움직일 방향을 정함
		// 0: 제자리 
		// 1: 위쪽
		// 2: 오른쪽
		// 3: 아래쪽
		// 4: 왼쪽
	
	public int nextMove(int x, int y, int[][] smap) {
		int move = 1;
		// 현재 방향을 기준으로 오른쪽을 검사
		// 오른쪽이 비어있으면 dir을 오른쪽으로 설정
		// 오른쪽이 막혀있으면, 직진 방향을 검사
		// 직진방향이 비어있으면 dir을 변함 없이
		// 직진방향이 막혀있으면, dir을 반대 방향 뒤로 돌기 -> nextMove();
		
		// 1: 위쪽
		// 2: 오른쪽
		// 3: 아래쪽
		// 4: 왼쪽
		
		//위 [0] [1]
		//오른 [1] [2]
		//밑 [2] [1]
		//왼쪽 [1] [0]
		if (move == 1) {  //위
			if(smap[1][2] == 0) {
				move = 2;
				return move;
			}
			else if(smap[1][2] == 1) {
				if(smap[0][1] == 1) {
					move = 3;
					return move;
				}
				return move;
			}
			return move;
			
			
		}
	
		if (move == 2) {  //오른
			if(smap[2][1] == 0) {
				move = 3;
				return move;
			}
			else if(smap[2][1] == 1) {
				if(smap[1][2] == 1) {
					move = 4;
					return move;
				}
				return move;
			}
			return move;
		}
		
		if (move == 3) {  //아래
			if(smap[1][0] == 0) {
				move = 4;
				return move;
			}
			else if(smap[1][0] == 1) {
				if(smap[2][1] == 1) {
					move = 1;
					return move;
				}
				return move;
			}
			return move;
		}
		
		if (move == 4) {  //왼
			if(smap[0][1] == 0) {
				move = 1;
				return move;
			}
			else if(smap[0][1] == 1) {
				if(smap[1][0] == 1) {
					move = 2;
					return move;
				}
				return move;
			}
			return move;
		}
		return dir;
	}
}
