package mice;

import boot.Mouse;

public class Sunyoung_Mouse extends Mouse {
	int foward = 2;
	int dir = 0;

	public Sunyoung_Mouse() {

	}
	// Mouse가 다음으로 움직일 방향을 정한다.
	// 0: 제자리
	// 1: 위쪽
	// 2: 오른쪽
	// 3: 아래쪽
	// 4: 왼쪽
	public int nextMove(int x, int y, int[][] smap) {

		if (foward == 2) {
			dir = lookR(smap);
			foward = dir;
			System.out.println("dir" + dir);
		} else if (foward == 3) {
			dir = lookD(smap);
			foward = dir;
			System.out.println("dir" + dir);
		} else if (foward == 4) {
			dir = lookL(smap);
			foward = dir;
			System.out.println("dir" + dir);
		} else {
			dir = lookU(smap);
			foward = dir;
			System.out.println("dir" + dir);
		}

		return dir;
	}

	public void printM(int[][] smap) {

		for (int i = 0; i < smap.length; i++) {
			for (int j = 0; j < smap[i].length; j++) {
				System.out.print(smap[i][j] + " ");
			}
			System.out.println();
		}
	}

	public int lookR(int[][] smap) {
		int dir = 2;
		if (smap[2][1] == 0) {
			dir = 3;
		} else if (smap[1][2] == 0) {
			dir = 2;
		} else if (smap[1][2] != 0 && smap[2][1] != 0) {
			dir = 4;
			if (smap[0][1] == 0) {
				dir = 1;
			}

		}

		return dir;

	}

	public int lookD(int[][] smap) {
		int dir = 4;
		if (smap[1][0] == 0) {
			dir = 4;
		} else if (smap[2][1] == 0) {
			dir = 3;
		} else if (smap[1][0] != 0 && smap[2][1] != 0) {
			dir = 1;
			if (smap[1][2] == 0) {
				dir = 2;
			}

		}
		return dir;

	}

	public int lookL(int[][] smap) {
		int dir = 1;
		if (smap[0][1] == 0) {
			dir = 1;
		} else if (smap[1][0] == 0) {
			dir = 4;
		} else if (smap[0][1] != 0 && smap[1][0] != 0) {
			dir = 2;
			if (smap[2][1] == 0) {
				dir = 3;
			}

		}
		return dir;

	}

	public int lookU(int[][] smap) {
		int dir = 4;
		if (smap[1][2] == 0) {
			dir = 2;
		} else if (smap[0][1] == 0) {
			dir = 1;
		} else if (smap[1][2] != 0 && smap[0][1] != 0) {
			dir = 3;
			if (smap[1][0] == 0) {
				dir = 4;
			}

		}
		return dir;

	}
}
