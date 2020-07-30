package maze.mazemaker;

import java.util.Random;

public class RMC_YBH {
	protected String mapName;
	protected int x_size;
	protected int y_size;
	protected int start_x, start_y;
	protected int esc_x, esc_y;

	protected int[][] map;

	public RMC_YBH(String mapName, int x_size, int y_size) {
		this.mapName = mapName;
		this.x_size = x_size;
		this.y_size = y_size;
		this.map = new int[y_size][x_size];

		this.start_x = 0;
		this.start_y = 0;
		this.esc_x = x_size - 1;
		this.esc_y = y_size - 1;
	}

	static Random rd = new Random();

	public static int[][] chamber(int x, int y, int c_map[][]) {
		if (x <= 2 || y <= 2)
			return c_map;
		// ���ڼ��� �� ���� ������������ ����
		int[][] a;
		int mid_x=x/2,mid_y=y/2;
//		boolean WC=true;
//		do {
//		rdn_x = rd.nextInt(x-1); // �̷��� ���κ� �߰� ����
//		rdn_y = rd.nextInt(y-1); // �̷��� ���κ� �߰� ���� 
//		if(rdn_x!=0 && rdn_y!=0)
//			WC=false;
//		}while(WC);
		
		int i, j; // �̷κ���α�
		for (i = 0; i < c_map.length; i++) {
			for (j = 0; j < c_map[i].length; j++)
				c_map[i][j] = 0;
		}
		// ���ڸ�� �������
		for (i = 0; i < c_map[0].length; i++) {
			c_map[mid_y][i] = 1;
		}
		for (i = 0; i < c_map.length; i++) {
			c_map[i][mid_x] = 1;
		}
		
		// ���� �� �߿� 3�� ������ ���� �ձ�
		int rdn=rd.nextInt(4);
		switch(rdn) {
		case(0):{ // ��2 ��и�� �� 3��и� ���� �����ϰ� �ձ�
				rdn = rd.nextInt(mid_y);
				c_map[rdn][mid_x]=0; // 1,2 wall
				rdn = rd.nextInt(x-mid_x-1)+1;
				c_map[mid_y][rdn+mid_x]=0; // 1,4 wall
				rdn = rd.nextInt(y-mid_y-1)+1;
				c_map[rdn+mid_y][mid_x]=0; // 3,4 wall
				break;
			}
		case(1):{ // �� 3��и�� ��4��и� ���� �����ϰ� �ձ�
				rdn =rd.nextInt(mid_x);
				c_map[mid_y][rdn]=0; // 2,3 wall
				rdn = rd.nextInt(mid_y);
				c_map[rdn][mid_x]=0; // 1,2 wall
				rdn = rd.nextInt(x-mid_x-1)+1;
				c_map[mid_y][rdn+mid_x]=0; // 1,4 wall
				break;
			}
		case(2):{// �� 4��и�� �� 1��и� ���� �����ϰ� �ձ�
				rdn =rd.nextInt(mid_x);
				c_map[mid_y][rdn]=0; // 2,3 wall
				rdn = rd.nextInt(mid_y);
				c_map[rdn][mid_x]=0; // 1,2 wall
				rdn = rd.nextInt(y-mid_y-1)+1;
				c_map[rdn+mid_y][mid_x]=0; // 3,4 wall
				break;
			}
		case(3):{// �� 2��и�� �� 1��и� ���� �����ϰ� �ձ�
				rdn =rd.nextInt(mid_x);
				c_map[mid_y][rdn]=0; // 2,3 wall
				rdn = rd.nextInt(x-mid_x-1)+1;
				c_map[mid_y][rdn+mid_x]=0; // 1,4 wall
				rdn = rd.nextInt(y-mid_y-1)+1;
				c_map[rdn+mid_y][mid_x]=0; // 3,4 wall
				break;
			}
		}
		
		// �۰� �߶� �ٽ�
		a = chamber( x - mid_x - 1 , mid_y , new int[ mid_y][ x- mid_x - 1 ]); // �� 1�и�
		for (i = 0; i < a.length; i++) {
			for (j = 0; j < a[i].length; j++)
				c_map[i][mid_x + j + 1] = a[i][j];
		} // ��ġ��
		a = chamber( mid_x, mid_y, new int[ mid_y ][ mid_x]); // �� 2��и�
		for (i = 0; i < a.length; i++) {
			for (j = 0; j < a[i].length; j++)
				c_map[i][j] = a[i][j];
		}
		// ��ġ��
		a = chamber( mid_x , y - mid_y - 1 , new int[ y- mid_y - 1 ][ mid_x]); // �� 3��и�
		for (i = 0; i < a.length; i++) {
			for (j = 0; j < a[i].length; j++)
				c_map[mid_y + i + 1 ][j] = a[i][j];
		} // ��ġ��
		a = chamber( x - mid_x - 1 , y - mid_y - 1 , new int[ y - mid_y - 1 ][ x - mid_x - 1 ]); // �� 4��и�
		for (i = 0; i < a.length; i++) {
			for (j = 0; j < a[i].length; j++)
				c_map[mid_y + i + 1][ mid_x + j + 1 ] = a[i][j];
		}
		// ��ġ��
		return c_map;
	}

	public void createMap() {
		map = chamber(this.x_size, this.y_size, map);

	}

	public void printMap() {
		String road = "��";
		String block = "��";

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0) {
					System.out.print(road);
				} else if (map[i][j] == 1) {
					System.out.print(block);
				}
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		RMC_YBH rmc = new RMC_YBH("NewMap", 30, 30);

		rmc.createMap();
		rmc.printMap();
	}

}
