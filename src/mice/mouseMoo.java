package mice;

public class mouseMoo extends Mouse {
	public static int map[][];
	public static int x;
	public static int y;

	public mouseMoo() {

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

		int dir = right(2);

		return dir;
	}

	public int right(int r) {
		// int x = 0;
		// int y = 0;

		if (map[2][1] == 1) { // 오른쪽을 보고 감
			r = 2;
		} else if (map[2][1] == 0) { // 아래가 뚫려 있으면 밑으로 내려감
			r = 3;
		} else if (map[1][0] == 0 && map[2][1] == 0) {
			r = 4;
		} else if (map[1][0] == -1 && map[0][1] == 1 && map[2][1] == 1) {
			r = 2;
		} else if (map[2][1] == 0 && map[1][0] == 1 && map[1][2] == 0) {
			r = 2;
		} else if (map[0][1] == 1 && map[1][2] == 1 && map[2][1] == 1) {
			r = 4;
		} else if (map[1][0] == 1 && map[1][2] == 0) {
			r = 3;
		}
		return r;
	}
}
