package mice;

import java.util.Vector;

import boot.MouseChallenge;

public class mouse_YBH extends MouseChallenge {
	private int dir;
	private int cur_x, cur_y;
	private graph g;
	private boolean answer, finish;
	private boolean[] visited;
	Vector<Integer> stack; // 미로 탐색 시 갔던 정점들 쌓는 스택
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
		// 탐색 순서 
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
		// 끝에 갔는데 없으면 그 요소 인덱스 삭제
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
		Vector<vertex> v = new Vector<vertex>(); // 미로 탐색을 통해 그래프가 저장되는 곳 연결리스트로 구현됨
		// 0=빈공간 1=벽 2=골인지점

		public graph() {
			v.add(new vertex(0, 0, 0));
		}

		// v.indexOf(vertex); 로 인자가 들어있는지 확인
		// 그래프트에 정점을 추가하는 메소드
		public void addVertex(int x, int y, int info, vertex cur_v) {
			int n = v.indexOf(new vertex(x, y, info));
			vertex vr;
			// 추가하기 전에 그래프트에 넣을 정점의 좌표가 있는지 확인
			if (n != -1) {
				// 정점이 있다면 그 정점과 현 위치에 있는 정점을 간선으로 연결
				vr = v.elementAt(n);
				// 연결된 리스트 끝에 추가
				while (vr.v != null) {
					// 만약 넣을려는 정점이 이미 간선이 생성되어 있으면 중단
					if (cur_v == vr)
						return;
					vr = vr.v;
				}
				// 양쪽다 간선 추가
				vr.v = cur_v;
				cur_v.v = vr;
			}
			// 없으면
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

		// 현재 방향을 기준으로 오른쪽을 검사
		if (smap[rp_Y[dir]][rp_X[dir]] != 1) {
			// 오른쪽이 비어있으면 dir을 오른쪽으로 설정
			cur_x+=r_x[dir];
			cur_y+=r_y[dir];
			stack.add(g.v.indexOf(new vertex(cur_x,cur_y,smap[rp_Y[dir]][rp_X[dir]])));
			dir = rt_Dir[dir];
		} else if (smap[sp_Y[dir]][sp_X[dir]] != 1) {
			// 오른쪽이 막혀있으면, 직진 방향을 검사
			// 직진방향이 비어있으면 dir을 변함 없이

			cur_x+=s_x[dir];
			cur_y+=s_y[dir];
			stack.add(g.v.indexOf(new vertex(cur_x,cur_y,smap[sp_Y[dir]][sp_X[dir]])));
		} else {
			// 직진방향이 막혀있으면, dir을 반대 방향 뒤로 돌기 -> nextMove();
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
		
		// 현재 방향을 기준으로 오른쪽을 검사
		if (smap[rp_Y[dir]][rp_X[dir]] != 1) {
			// 오른쪽이 비어있으면 dir을 오른쪽으로 설정
			dir = rt_Dir[dir];
		} else if (smap[sp_Y[dir]][sp_X[dir]] != 1) {
			// 오른쪽이 막혀있으면, 직진 방향을 검사
			// 직진방향이 비어있으면 dir을 변함 없이

		} else {
			// 직진방향이 막혀있으면, dir을 반대 방향 뒤로 돌기 -> nextMove();
			dir = ut_Dir[dir];
			dir = nextMove(smap);
		}

		return dir;
	}

}
