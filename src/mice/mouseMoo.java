package mice;

public class mouseMoo extends Mouse {
	public static int map[][];
	public static int x;
	public static int y;
	public static int dir;
	public mouseMoo() {
		this.dir = 0;
	}

	// Mouse�� �������� ������ ������ ����
	// 0: ���ڸ�
	// 1: ����
	// 2: ������
	// 3: �Ʒ���
	// 4: ����
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
