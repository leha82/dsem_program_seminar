package mice;

import maze.Mouse;

public class Kangjun_Mouse extends Mouse {
	private int dir = 1;
	public static int x;
	public static int y;
	
	
	
	public Kangjun_Mouse() {
		this.dir = 0;
	}
	
	// Mouse�� �������� ������ ������ ����
		// 0: ���ڸ� 
		// 1: ����
		// 2: ������
		// 3: �Ʒ���
		// 4: ����
	
	public int nextMove(int x, int y, int[][] smap) {
		
		// ���� ������ �������� �������� �˻�
		
				// �������� ��������� dir�� ���������� ����
				
				// �������� ����������, ���� ������ �˻�
				
				// ���������� ��������� dir�� ���� ����
				
				// ���������� ����������, dir�� �ݴ� ���� �ڷ� ���� -> nextMove();
		// 1: ����
		// 2: ������
		// 3: �Ʒ���
		// 4: ����
		if (dir == 1) {  //��
			if(smap[1][2] == 0)
				dir = 2;
			else if(smap[1][2] == 1) {
				if(smap[0][1] == 1) {
					dir = 3;
					return dir;
				}
				return dir;
			}
			return dir;
		}
	
		if (dir == 2) {  //����
			if(smap[2][1] == 0)
				dir = 3;
			else if(smap[2][1] == 1) {
				if(smap[1][2] == 1) {
					dir = 4;
					return dir;
				}
				return dir;
			}
			return dir;
		}
		
		if (dir == 3) {  //�Ʒ�
			if(smap[1][0] == 0)
				dir = 4;
			else if(smap[1][0] == 1) {
				if(smap[2][1] == 1) {
					dir = 1;
					return dir;
				}
				return dir;
			}
			return dir;
		}
		
		if (dir == 4) {  //��
			if(smap[0][1] == 0)
				dir = 1;
			else if(smap[0][1] == 1) {
				if(smap[1][0] == 1) {
					dir = 2;
					return dir;
				}
				return dir;
			}
			return dir;
		}
		return dir;
	}
}
