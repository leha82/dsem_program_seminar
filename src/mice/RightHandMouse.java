package mice;

import boot.Mouse;

public class RightHandMouse extends Mouse {
	private int dir;

	public RightHandMouse() {
		this.dir = 1;
	}

	@Override
	public void printClassName() {
		System.out.println("RightHandMouse");
	}

	// Mouse�� �������� ������ ������ ���Ѵ�.
	// 0: ���ڸ�
	// 1: ����
	// 2: ������
	// 3: �Ʒ���
	// 4: ����

	public int nextMove(int x, int y, int[][] smap) {
		int[] rp_X = { 0, 2, 1, 0, 1 }; // right position by current dir
		int[] rp_Y = { 0, 1, 2, 1, 0 };

		int[] sp_X = { 0, 1, 2, 1, 0 }; // straight position by current dir
		int[] sp_Y = { 0, 0, 1, 2, 1 };

		int[] rt_Dir = { 0, 2, 3, 4, 1 };
		int[] ut_Dir = { 0, 3, 4, 1, 2 };

		// ���� ������ �������� �������� �˻�
		if (smap[rp_Y[dir]][rp_X[dir]] == 0) {
			// �������� ��������� dir�� ���������� ����
			dir = rt_Dir[dir];
		} else if (smap[sp_Y[dir]][sp_X[dir]] == 0) {
			// �������� ����������, ���� ������ �˻�
			// ���������� ��������� dir�� ���� ����

		} else {
			// ���������� ����������, dir�� �ݴ� ���� �ڷ� ���� -> nextMove();
			dir = ut_Dir[dir];
			dir = nextMove(x, y, smap);
		}

		return dir;
	}

}