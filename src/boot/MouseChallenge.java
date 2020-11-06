package boot;

public abstract class MouseChallenge {
	// Mouse�� ���� ���� �����ϴ�.
	private boolean moved;
	
	public MouseChallenge() {
		this.moved = false;
	}
	
	// Mouse�� �������� ������ ������ ���Ѵ�.
	// 0: ���ڸ�, 1: ����, 2: ������, 3: �Ʒ���, 4: ����, -1 : Ž�� ����

	public void initMouse() {
		this.moved = false;
	}
	
	public void checkMoved() {
		this.moved = true;
	}
	
	public boolean isMoved() {
		return this.moved;
	}
	
	// smap�� ����
	// ���� ��ġ smap�� ��� (smap�� 3x3 �� ��� smap[1][1]�� mouse��ġ
	// smap[y][x]�� �� 0: ��, 1: ��,  
	// Ž�� ����� ������ ����
	public abstract int nextSearch(int[][] smap);

	// ���� ����� ������ ����
	public abstract int nextMove(int[][] smap);

	public void printClassName() {
		System.out.println("Mouse");
	}
}
