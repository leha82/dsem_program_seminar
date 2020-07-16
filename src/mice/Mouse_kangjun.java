package mice;
import maze.Mouse;
import maze.Maze;

public class Mouse_kangjun extends Mouse {
	public static int move;
	public int count=0;
	public Mouse_kangjun() {
		move=1;
	}
	public int nextMove(int x, int y, int[][] smap) {
		// ���� ������ �������� �������� �˻�
		// �������� ��������� dir�� ���������� ����
		// �������� ����������, ���� ������ �˻�
		// ���������� ��������� dir�� ���� ����
		// ���������� ����������, [���� �˻�, ���� ������] dir�� �ݴ� ���� �ڷ� ���� -> nextMove();
		
		// 1: ����
		// 2: ������
		// 3: �Ʒ���
		// 4: ����
		
		//�� [0] [1]			1
		//���� [1] [2]		2
		//�� [2] [1]			3
		//���� [1] [0]		4
		if(move==1) {			//��
			if(smap[1][2]==0) {
				return move =2;
			} else if(smap[0][1]==0){
				return move;
			} else if(smap[1][0]==0){
				return move =4;
			} else {
				return move =3;
			}
		}
		if(move==2) {			//����
			if(smap[2][1]==0) {
				return move =3;
			} else if(smap[1][2]==0){
				return move;
			} else if (smap[0][1]==0){
				return move =1;
			} else {
				return move =4;
			}
		}
		if(move==3) {			//��
			if(smap[1][0]==0) {
				return move =4;
			} else if(smap[2][1]==0){
				return move;
			} else if(smap[1][2]==0){
				return move =2;
			} else {
				return move =1;
			}
		}
		if(move==4) {			//��
			if(smap[0][1]==0) {
				return move =1;
			} else if(smap[1][0]==0){
				return move;
			} else if(smap[2][1]==0){
				return move =3;
			} else {
				return move =2;
			}
		}
		return move;
	}
}
