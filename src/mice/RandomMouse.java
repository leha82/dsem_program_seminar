package mice;

import boot.MouseChallenge;

public class RandomMouse extends MouseChallenge {
	
	public RandomMouse() {
		
	}
	
	// Mouse�� �������� ������ ������ ���Ѵ�.
	// 0: ���ڸ�
	// 1: ����
	// 2: ������
	// 3: �Ʒ���
	// 4: ����
	public int nextMove(int[][] smap) {
		int dir = (int)(Math.random()*100) % 4 + 1;
		checkMoved();
		return dir;
	}
		
	public int nextSearch(int[][] smap) {
		int dir = (int)(Math.random()*100) % 4 + 1;
		checkMoved();
		return dir;
	}


}
