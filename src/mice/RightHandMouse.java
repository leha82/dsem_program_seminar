package mice;

public class RightHandMouse extends Mouse {
	private int dir;
	
	public RightHandMouse() {
		this.dir = 0;
	}
	
	// Mouse�� �������� ������ ������ ���Ѵ�.
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
		
		return dir;
	}

}
