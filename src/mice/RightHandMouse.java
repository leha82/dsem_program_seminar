package mice;

import maze.Mouse;

public class RightHandMouse extends Mouse {
	private int dir;
	
	public RightHandMouse() {
		this.dir = 0;
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
	@Override
	public int nextMove(int x, int y, int[][] smap) {
		// ���� ������ �������� �������� �˻�
		
		// �������� ��������� dir�� ���������� ����
		
		// �������� ����������, ���� ������ �˻�
		
		// ���������� ��������� dir�� ���� ����
		
		// ���������� ����������, dir�� �ݴ� ���� �ڷ� ���� -> nextMove();
		
		return dir;
	}

}
