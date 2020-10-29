package boot;

public abstract class MouseChallenge extends Mouse {
	private int esc_x, esc_y;
	
	// Mouse�� �������� ������ ������ ���Ѵ�.
	// 0: ���ڸ�
	// 1: ����
	// 2: ������
	// 3: �Ʒ���
	// 4: ����
	// -1 : Ž�� ����

	// Mouse�� nextMove �޼ҵ�� ������忡���� �������� �����ϵ���
	
	// Ž�� ����� ������ ����
	public abstract int nextSearch(int x, int y, int[][] smap);

	public void setEscapePoint(int esc_x, int esc_y) {
		this.esc_x = esc_x;
		this.esc_y = esc_y;
	}
	
	public void printMouseInfo() {
		System.out.println("Escape point x: " + esc_x + ", " + esc_y);
	}
}
