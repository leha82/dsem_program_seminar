package mice;

import maze.original.Mouse;

public class Mouse_sangmoo extends Mouse {
	public static int map[][];
	public static int dir;

	public Mouse_sangmoo() {
		this.dir = 2;
	}

	// Mouse�� �������� ������ ������ ����
	// 0: ���ڸ�
	// 1: ����
	// 2: ������
	// 3: �Ʒ���
	// 4: ����
	public int nextMove(int x, int y, int[][] smap) {
		this.map = smap;

		if (dir == 2) { //������ ����
			if (map[2][1] == 0) {
				dir = 3;
				return dir;
			} else if (map[1][2] == 0) {
				dir = 2;
				return dir;
			} else if (map[0][1] == 0) {
				dir = 1;
				return dir;
			} else {
				dir = 4;
				return dir;
			}
		}

		if (dir == 1) { //���� ����
			if (map[1][2] == 0) {
				dir = 2;
				return dir;
			} else if (map[0][1] == 0) {
				dir = 1;
				return dir;
			} else if (map[1][0] == 0) {
				dir = 4;
				return dir;
			} else {
				dir = 3;
				return dir;
			}
		}
		
	  if(dir == 3) { //�Ʒ��� ����
		  if(map[1][0] == 0) {
			  dir = 4;
			  return dir;
		  }else if(map[2][1] == 0) {
			  dir = 3;
			  return dir;
		  }else if(map[1][2] == 0) {
			  dir = 2;
			  return dir;
		  }else {
			  dir = 1;
			  return dir;
		  }
	  }
	  
	  if(dir == 4) { //���� ����
		  if(map[0][1] == 0) {
			  dir = 1;
			  return dir;
		  }else if(map[1][0] == 0) {
			  dir = 4;
			  return dir;
			  
		  }else if(map[2][1] == 0) {
			  dir = 3;
			  return dir;
		  }else {
			  dir = 2;
			  return dir;
		  }
	  }
	return dir;
	}
}
