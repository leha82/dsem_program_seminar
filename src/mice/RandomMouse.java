package mice;

public class RandomMouse extends Mouse {
	public RandomMouse() {
		
	}
	
	// Mouse�� �������� ������ ������ ���Ѵ�.
	// 0: ���ڸ�
	// 1: ����
	// 2: ������
	// 3: �Ʒ���
	// 4: ����
	public int nextMove(int x, int y, int[][] smap) {
		int dir = (int)(Math.random()*100) % 5;
		return dir;
	}
}
