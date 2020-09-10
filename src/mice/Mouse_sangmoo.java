package mice;

import maze.original.Mouse;

public class Mouse_sangmoo extends Mouse {
	public static int map[][];
	public static int dir;

	public Mouse_sangmoo() {
		this.dir = 2;
	}

	// Mouse가 다음으로 움직일 방향을 정함
	// 0: 제자리
	// 1: 위쪽
	// 2: 오른쪽
	// 3: 아래쪽
	// 4: 왼쪽
	public int nextMove(int x, int y, int[][] smap) {
		this.map = smap;

		if (dir == 2) { //오른쪽 뱡향
			if (map[2][1] == 0) {
				dir = 3;
				return dir;
			} else if (map[1][2] == 0) {
				dir = 2;
				return dir;
			} else if (map[0][1] == 0) {
				dir = 1;
				return dir;
			} else {
				dir = 4;
				return dir;
			}
		}

		if (dir == 1) { //위쪽 방향
			if (map[1][2] == 0) {
				dir = 2;
				return dir;
			} else if (map[0][1] == 0) {
				dir = 1;
				return dir;
			} else if (map[1][0] == 0) {
				dir = 4;
				return dir;
			} else {
				dir = 3;
				return dir;
			}
		}
		
	  if(dir == 3) { //아래쪽 방향
		  if(map[1][0] == 0) {
			  dir = 4;
			  return dir;
		  }else if(map[2][1] == 0) {
			  dir = 3;
			  return dir;
		  }else if(map[1][2] == 0) {
			  dir = 2;
			  return dir;
		  }else {
			  dir = 1;
			  return dir;
		  }
	  }
	  
	  if(dir == 4) { //왼쪽 방향
		  if(map[0][1] == 0) {
			  dir = 1;
			  return dir;
		  }else if(map[1][0] == 0) {
			  dir = 4;
			  return dir;
			  
		  }else if(map[2][1] == 0) {
			  dir = 3;
			  return dir;
		  }else {
			  dir = 2;
			  return dir;
		  }
	  }
	return dir;
	}
}
