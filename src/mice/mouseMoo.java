package mice;

public class mouseMoo extends Mouse {
	public static int map[][];
	public static int x;
	public static int y;

	public mouseMoo() {

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

		int dir = right(2);

		return dir;
	}

	public int right(int r) {
		// int x = 0;
		// int y = 0;

		if (map[2][1] == 1) { // �������� ���� ��
			r = 2;
		} else if (map[2][1] == 0) { // �Ʒ��� �շ� ������ ������ ������
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
