package mice;

public class mouseMoo extends Mouse {
	public static int map[][];
	public static int x;
	public static int y;
	public static int dir;
	public mouseMoo() {
		this.dir = 0;
	}

	// Mouse가 다음으로 움직일 방향을 정함
	// 0: 제자리
	// 1: 위쪽
	// 2: 오른쪽
	// 3: 아래쪽
	// 4: 왼쪽
	public int nextMove(int x, int y, int[][] smap) {
		this.map = smap;
		this.x = x;
		this.y = y;

		if(map[2][1] == 0) {
			dir = 3;
		}else if(map[1][2] == 0) {
			dir = 2;
		}else if(map[2][1] == 1 && map[1][2] == 1){
			dir = 4;
			nextMove(x, y, map);
		}
		
		return dir;
	}
}
