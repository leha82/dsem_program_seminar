package mice_original;

import boot.Mouse;

public class Mouse_woolin extends Mouse {
	private int dir = 1;  // ó�� ���⼺ (1)
	public Mouse_woolin() {

	}
	
	// Mouse�� �������� ������ ������ ���Ѵ�.
	// (1) : ����, (2) : ������, (3) :�Ʒ���, (4) : ����
	public int nextMove(int x, int y, int[][] smap) {
		
		if (dir == 1) {  // ���� ���� ������ (1)�̸�
			if (smap[1][2] == 0) {  // �������� �� �� ������
				dir = 2;
				return dir;
			} else if (smap[0][1] == 0) {  // ���� ����(��)
				dir = 1;
				return dir;
			} else if (smap[1][0] == 0) {  // ���� �� �� ������
				dir = 4;
				return dir;
			}
			else {  // �� �� �ִ±��� ���̻� ���ٸ� ���� �����ؼ� �ڷ� �̵�
				dir = 3; 
				return dir;
			}
		}
		
		if (dir == 2) {   // ���� ������ (2)�̸�
			if (smap[2][1] == 0) {
				dir = 3;
				return dir;
			} else if (smap[1][2] == 0) {
				dir = 2;
				return dir;
			} else if (smap[0][1] == 0) {
				dir = 1;
				return dir;
			}
			else {
				dir = 4;
				return dir;
			}
		}
		
		if (dir == 3) {   // ���� ������ (3)�̸�
			if (smap[1][0] == 0) {
				dir = 4;
				return dir;
			} else if(smap[2][1] == 0) {
				dir = 3;
				return dir;
			} else if (smap[1][2] == 0) {
				dir = 2;
				return dir;
			}
			else {
				dir = 1;
				return dir;
			}
		}
		
		if (dir == 4) {   // ���� ������ (4)�̸�
			if (smap[0][1] == 0) {
				dir = 1;
				return dir;
			} else if (smap[1][0] == 0) {
				dir = 4;
				return dir;
			} else if (smap[2][1] == 0) {
				dir = 3;
				return dir;
			}
			else {
				dir = 2;
				return dir;
			}
		}
		
		return dir;
	}

}
