package maze;

public abstract class Mouse {
	// Mouse�� �������� ������ ������ ���Ѵ�.
	// 0: ���ڸ�
	// 1: ����
	// 2: ������
	// 3: �Ʒ���
	// 4: ����
	public abstract int nextMove(int x, int y, int[][] smap);
	
	public void printClassName() {
		System.out.println("Mouse");
	}
}
