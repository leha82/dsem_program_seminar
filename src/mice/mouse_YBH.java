package mice;

import java.util.Vector;

import boot.MouseChallenge;

public class mouse_YBH extends MouseChallenge {
	private int dir;
	private int cur_x, cur_y;
	private graph g;
	private boolean answer, finish;
	private boolean[] visited;
	Vector<Integer> stack; // �̷� Ž�� �� ���� ������ �״� ����
	private int queue;

	public mouse_YBH() {
		dir = 1;
		g = new graph();
		answer = false;
		stack = new Vector<Integer>();
		stack.add(0);
		finish=false;
		queue=0;
	}
	
	public void initMouse() {
		cur_x = 0;
		cur_y = 0;
		stack = new Vector<Integer>();
		if(answer==true) {
			visited=new boolean[g.v.capacity()];
			dfs(0);
		}
	}

	
	public void dfs(int n) {
		visited[n]=true;
		// Ž�� ���� 
		stack.add(n);
		if (g.v.elementAt(n).info==2 || finish ) {
			finish=true;
			return;
		}
		vertex vr=g.v.elementAt(n);
		while(vr.v!= null) {
			vr=vr.v;
			dfs(g.v.indexOf(vr));
		}
		// ���� ���µ� ������ �� ��� �ε��� ����
		stack.remove(stack.capacity()-1);
	}
	
	
	
	class vertex {
		public int x;
		public int y;
		public int info;
		public vertex v;

		public vertex(int x, int y, int info) {
			this.x = x;
			this.y = y;
			this.info = info;
		}
	}
	class graph {
		Vector<vertex> v = new Vector<vertex>(); // �̷� Ž���� ���� �׷����� ����Ǵ� �� ���Ḯ��Ʈ�� ������
		// 0=����� 1=�� 2=��������

		public graph() {
			v.add(new vertex(0, 0, 0));
		}

		// v.indexOf(vertex); �� ���ڰ� ����ִ��� Ȯ��
		// �׷���Ʈ�� ������ �߰��ϴ� �޼ҵ�
		public void addVertex(int x, int y, int info, vertex cur_v) {
			int n = v.indexOf(new vertex(x, y, info));
			vertex vr;
			// �߰��ϱ� ���� �׷���Ʈ�� ���� ������ ��ǥ�� �ִ��� Ȯ��
			if (n != -1) {
				// ������ �ִٸ� �� ������ �� ��ġ�� �ִ� ������ �������� ����
				vr = v.elementAt(n);
				// ����� ����Ʈ ���� �߰�
				while (vr.v != null) {
					// ���� �������� ������ �̹� ������ �����Ǿ� ������ �ߴ�
					if (cur_v == vr)
						return;
					vr = vr.v;
				}
				// ���ʴ� ���� �߰�
				vr.v = cur_v;
				cur_v.v = vr;
			}
			// ������
			else {
				v.add(new vertex(x, y, info));
				cur_v.v = v.lastElement();
			}
		}
	}

	@Override
	public int nextSearch(int[][] smap) {

		int[] rp_X = { 0, 2, 1, 0, 1 }; // right position by current dir
		int[] rp_Y = { 0, 1, 2, 1, 0 };

		int[] sp_X = { 0, 1, 2, 1, 0 }; // straight position by current dir
		int[] sp_Y = { 0, 0, 1, 2, 1 };

		int[] rt_Dir = { 0, 2, 3, 4, 1 };
		int[] ut_Dir = { 0, 3, 4, 1, 2 };
		
		int[] r_x= {0,0,1,0,-1};
		int[] r_y= {0,1,0,-1,0};
		
		int[] s_x= {0,-1,0,1,0};
		int[] s_y= {0,0,1,0,-1};
		this.checkMoved();

		if (smap[1][0] != 1) {
			g.addVertex(cur_x, cur_y-1, smap[1][0], g.v.elementAt(stack.lastElement()));
		}
		if (smap[1][2] != 1) {
			g.addVertex(cur_x , cur_y+1, smap[1][2], g.v.elementAt(stack.lastElement()));
		}
		if (smap[2][1] != 1) {
			g.addVertex(cur_x+1, cur_y, smap[2][1], g.v.elementAt(stack.lastElement()));
		}
		if(smap[0][1]!=1) {
			g.addVertex(cur_x -1, cur_y, smap[0][1], g.v.elementAt(stack.lastElement()));
		}

		// ���� ������ �������� �������� �˻�
		if (smap[rp_Y[dir]][rp_X[dir]] != 1) {
			// �������� ��������� dir�� ���������� ����
			cur_x+=r_x[dir];
			cur_y+=r_y[dir];
			stack.add(g.v.indexOf(new vertex(cur_x,cur_y,smap[rp_Y[dir]][rp_X[dir]])));
			dir = rt_Dir[dir];
		} else if (smap[sp_Y[dir]][sp_X[dir]] != 1) {
			// �������� ����������, ���� ������ �˻�
			// ���������� ��������� dir�� ���� ����

			cur_x+=s_x[dir];
			cur_y+=s_y[dir];
			stack.add(g.v.indexOf(new vertex(cur_x,cur_y,smap[sp_Y[dir]][sp_X[dir]])));
		} else {
			// ���������� ����������, dir�� �ݴ� ���� �ڷ� ���� -> nextMove();
			stack.removeElementAt(stack.capacity()-1);
			dir = ut_Dir[dir];
			dir = nextMove(smap);
		}

		return dir;
	}

	@Override
	public int nextMove(int[][] smap) {
		if(answer==true) {
			vertex vi=g.v.elementAt(stack.indexOf(queue++));
			
			if (smap[1][0] != 1) {
				g.addVertex(cur_x, cur_y-1, smap[1][0], g.v.elementAt(stack.lastElement()));
			}
			if (smap[1][2] != 1) {
				g.addVertex(cur_x , cur_y+1, smap[1][2], g.v.elementAt(stack.lastElement()));
			}
			if (smap[2][1] != 1) {
				g.addVertex(cur_x+1, cur_y, smap[2][1], g.v.elementAt(stack.lastElement()));
			}
			if(smap[0][1]!=1) {
				g.addVertex(cur_x -1, cur_y, smap[0][1], g.v.elementAt(stack.lastElement()));
			}
			
		}
		int[] rp_X = { 0, 2, 1, 0, 1 }; // right position by current dir
		int[] rp_Y = { 0, 1, 2, 1, 0 };

		int[] sp_X = { 0, 1, 2, 1, 0 }; // straight position by current dir
		int[] sp_Y = { 0, 0, 1, 2, 1 };

		int[] rt_Dir = { 0, 2, 3, 4, 1 };
		int[] ut_Dir = { 0, 3, 4, 1, 2 };

		this.checkMoved();
		
		// ���� ������ �������� �������� �˻�
		if (smap[rp_Y[dir]][rp_X[dir]] != 1) {
			// �������� ��������� dir�� ���������� ����
			dir = rt_Dir[dir];
		} else if (smap[sp_Y[dir]][sp_X[dir]] != 1) {
			// �������� ����������, ���� ������ �˻�
			// ���������� ��������� dir�� ���� ����

		} else {
			// ���������� ����������, dir�� �ݴ� ���� �ڷ� ���� -> nextMove();
			dir = ut_Dir[dir];
			dir = nextMove(smap);
		}

		return dir;
	}

}
