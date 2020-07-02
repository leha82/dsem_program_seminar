package mice;
import maze.Mouse;

public class Mouse_kangjun extends Mouse {
	private int dir = 0;
	
	public Mouse_kangjun() {
		this.dir = 0;
	}
	
	// Mouse�� �������� ������ ������ ����
		// 0: ���ڸ� 
		// 1: ����
		// 2: ������
		// 3: �Ʒ���
		// 4: ����
	
	public int nextMove(int x, int y, int[][] smap) {
		int move = 1;
		// ���� ������ �������� �������� �˻�
		// �������� ��������� dir�� ���������� ����
		// �������� ����������, ���� ������ �˻�
		// ���������� ��������� dir�� ���� ����
		// ���������� ����������, dir�� �ݴ� ���� �ڷ� ���� -> nextMove();
		
		// 1: ����
		// 2: ������
		// 3: �Ʒ���
		// 4: ����
		
		//�� [0] [1]
		//���� [1] [2]
		//�� [2] [1]
		//���� [1] [0]
		if (move == 1) {  //��
			if(smap[1][2] == 0) {
				move = 2;
				return move;
			}
			else if(smap[1][2] == 1) {
				if(smap[0][1] == 1) {
					move = 3;
					return move;
				}
				return move;
			}
			return move;
			
			
		}
	
		if (move == 2) {  //����
			if(smap[2][1] == 0) {
				move = 3;
				return move;
			}
			else if(smap[2][1] == 1) {
				if(smap[1][2] == 1) {
					move = 4;
					return move;
				}
				return move;
			}
			return move;
		}
		
		if (move == 3) {  //�Ʒ�
			if(smap[1][0] == 0) {
				move = 4;
				return move;
			}
			else if(smap[1][0] == 1) {
				if(smap[2][1] == 1) {
					move = 1;
					return move;
				}
				return move;
			}
			return move;
		}
		
		if (move == 4) {  //��
			if(smap[0][1] == 0) {
				move = 1;
				return move;
			}
			else if(smap[0][1] == 1) {
				if(smap[1][0] == 1) {
					move = 2;
					return move;
				}
				return move;
			}
			return move;
		}
		return dir;
	}
}
