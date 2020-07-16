package mice;
import maze.Mouse;
import maze.Maze;

public class Mouse_kangjun extends Mouse {
	public static int move;
	public int count=0;
	public Mouse_kangjun() {
		move=1;
	}
	public int nextMove(int x, int y, int[][] smap) {
		// 현재 방향을 기준으로 오른쪽을 검사
		// 오른쪽이 비어있으면 dir을 오른쪽으로 설정
		// 오른쪽이 막혀있으면, 직진 방향을 검사
		// 직진방향이 비어있으면 dir을 변함 없이
		// 직진방향이 막혀있으면, [왼쪽 검사, 왼쪽 막히면] dir을 반대 방향 뒤로 돌기 -> nextMove();
		
		// 1: 위쪽
		// 2: 오른쪽
		// 3: 아래쪽
		// 4: 왼쪽
		
		//위 [0] [1]			1
		//오른 [1] [2]		2
		//밑 [2] [1]			3
		//왼쪽 [1] [0]		4
		if(move==1) {			//위
			if(smap[1][2]==0) {
				return move =2;
			} else if(smap[0][1]==0){
				return move;
			} else if(smap[1][0]==0){
				return move =4;
			} else {
				return move =3;
			}
		}
		if(move==2) {			//오른
			if(smap[2][1]==0) {
				return move =3;
			} else if(smap[1][2]==0){
				return move;
			} else if (smap[0][1]==0){
				return move =1;
			} else {
				return move =4;
			}
		}
		if(move==3) {			//밑
			if(smap[1][0]==0) {
				return move =4;
			} else if(smap[2][1]==0){
				return move;
			} else if(smap[1][2]==0){
				return move =2;
			} else {
				return move =1;
			}
		}
		if(move==4) {			//왼
			if(smap[0][1]==0) {
				return move =1;
			} else if(smap[1][0]==0){
				return move;
			} else if(smap[2][1]==0){
				return move =3;
			} else {
				return move =2;
			}
		}
		return move;
	}
}
