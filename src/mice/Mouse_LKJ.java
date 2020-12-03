package mice;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import boot.MouseChallenge;

public class Mouse_LKJ extends MouseChallenge {
	private int[][] map;
	private int dir;
	private int start_x, start_y;
	private int x, y;
	private boolean isfocusfog;
	Scanner sc = new Scanner(System.in);

	private int searchCount;
	private int focus_x,focus_y;

	private Stack<Integer> dirStack;

//	final int PATHWAY = 0;
//	final int WALL = 1;
//	final int BLOCKED = 2;
//	final int PATH = 3;

	public Mouse_LKJ() {
		this.map = new int[20][20];
		this.dir = 1;
		this.searchCount = 0;
		start_x = 1;
		start_y = 1;
		x = start_x;
		y = start_y;
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = -1;
			}
		}
	}

	public void moveXY(int dir) {
		if (dir == 1 && y > 0) {
			if (map[y - 1][x] != 1)
				y--;
		} else if (dir == 2 && x < map[0].length - 1) {
			if (map[y][x + 1] != 1)
				x++;
		} else if (dir == 3 && y < map.length - 1) {
			if (map[y + 1][x] != 1)
				y++;
		} else if (dir == 4 && x > 0) {
			if (map[y][x - 1] != 1)
				x--;
		}
	}

	@Override
	public void printClassName() {
		System.out.println("MOUSE_LKJ");
	}

	public void printMap(int[][] pmap, int mx, int my) {
		for (int i = 0; i < pmap.length; i++) {
			for (int j = 0; j < pmap[i].length; j++) {
				String tile = "- ";
				if (mx == j && my == i) {
					tile = "H ";
				} else if (pmap[i][j] == 0) {
					tile = "0 ";
				} else if (pmap[i][j] == 1) {
					tile = "1 ";
				} else if (pmap[i][j] == 2) {
					tile = "D ";
				}

				System.out.print(tile);
			}
			System.out.println();
		}
		System.out.println();
	}

	public void initMouse() {
		x= start_x;
		y= start_y;
		searchCount++;
		isfocusfog=false;
		dir=1;
	}

	public void overlabMap(int[][] smap) {
		for (int i = 0; i < smap.length; i++) {
			for (int j = 0; j < smap[i].length; j++) {
				int map_x = x - (smap[i].length / 2) + j;
				int map_y = y - (smap.length / 2) + i;
//				System.out.print(map_x + ", " + map_y + "\t");
				map[map_y][map_x] = smap[i][j];
			}
		}
//		System.out.println();
	}

	// Mouse가 다음으로 움직일 방향을 정한다.
	// 0: 제자리, 1: 위쪽, 2: 오른쪽, 3: 아래쪽, 4: 왼쪽, -1 : 탐색 종료

//  ==============Recursive Method===================	
//	const int PATHWAY = 0; // 지나갈 수 있는 길                               ->상수 [final)
//  const int WALL = 1; // 벽						   ->상수 [final)
//  const int BLOCKED = 2; // 돌아오면서 막은 길                             ->상수 [final)
//  const int PATH = 3; //지나가면서 표시한 길                                   ->상수 [final)

//	public boolean findMapPath(int x, int y) {
//		int N = 2;
//
//		if(x<0 || y<0 ||x>=N || y>=N) {
//			return false;
//			
//		} else if(map[x][y] != PATHWAY) {
//			return false;
//			
//		} else if( x == N-1 && y == N-1) {
//			map[x][y] = PATH;
//			return true;
//			
//		} else {
//			map[x][y] = PATH;
//			if(findMapPath(x-1, y) || findMapPath(x, y+1) || findMapPath(x+1, y) || findMapPath(x, y-1)) {
//				return true;
//				
//			}
//			map[x][y] = BLOCKED;
//			return false;
//		}
//	}

//	int findMapPath(int x, int y) {
//	    if(x<0 || y<0 || x>=N || y>=N) {
//	        return 0;
//	    }else if(map[x][y] != PATHWAY) return 0;
//	    else if(x == N-1 && y == N-1) {
//	        map[x][y] = PATH;
//	        return 1;
//	    }else {
//	        map[x][y] = PATH;
//	        if(findMapPath(x-1, y) || findMapPath(x, y+1)
//	            || findMapPath(x+1, y) || findMapPath(x, y-1)) {
//	            return 1;
//	        }
//	    map[x][y] = BLOCKED;
//	    return 0;
//	    }
//	}
// ====================================================
	public void findFog() {
		
		int width = map[0].length;
		int height = map.length;
		int[][] traceMap = new int[height][width];
		
		Queue<Integer> q_x = new LinkedList<Integer>();
		Queue<Integer> q_y = new LinkedList<Integer>();
		
		int curr_x = x;
		int curr_y = y;
		
		focus_x=0;
		focus_y=0;

		// bfs를 이용하여 goal까지 진행
		q_x.add(curr_x);
		q_y.add(curr_y);
		traceMap[curr_y][curr_x] = 1;

		while (!q_x.isEmpty()) {
			curr_x = q_x.poll();
			curr_y = q_y.poll();

			int index = traceMap[curr_y][curr_x];
			 
			// 현재점이 fog에 도달하면 최단거리를 찾은 것임
			if (map[curr_y][curr_x] == -1) {
				focus_x = curr_x;// 도달 지점
				focus_y = curr_y;// 도달 지점
				isfocusfog=true;// 도달 할 점 찾은 상태
				break;
			}

			if (curr_y > 0 && map[curr_y-1][curr_x] != 1 && traceMap[curr_y-1][curr_x] == 0) {
				q_x.add(curr_x);
				q_y.add(curr_y-1);
				traceMap[curr_y-1][curr_x] = index+1;
			}

			if (curr_x < width - 1 && map[curr_y][curr_x+1] != 1 && traceMap[curr_y][curr_x+1] == 0) {
				q_x.add(curr_x+1);
				q_y.add(curr_y);
				traceMap[curr_y][curr_x+1] = index+1;
			}

			if (curr_y < height-1 && map[curr_y+1][curr_x] != 1 && traceMap[curr_y+1][curr_x] == 0) {
				q_x.add(curr_x);
				q_y.add(curr_y+1);
				traceMap[curr_y+1][curr_x] = index+1;
			} 
			
			if (curr_x > 0 && map[curr_y][curr_x-1] != 1 && traceMap[curr_y][curr_x-1] == 0) {
				q_x.add(curr_x-1);
				q_y.add(curr_y);
				traceMap[curr_y][curr_x-1] = index+1;
			}

//			printMap(map, curr_x, curr_y);
//			printCheckMap(traceMap);
//			sc.nextLine();
		}

		dirStack = new Stack<Integer>();
		while(curr_x!=x || curr_y!=y) {
			int index = traceMap[curr_y][curr_x];
			
			if (curr_y > 0 && traceMap[curr_y-1][curr_x] == index-1) {
				dirStack.push(3);
				curr_y--;
			} else if (curr_x < width - 1 && traceMap[curr_y][curr_x+1] == index-1) {
				dirStack.push(4);
				curr_x++;
			} else if (curr_y < height-1 && traceMap[curr_y+1][curr_x] == index-1) {
				dirStack.push(1);
				curr_y++;
			} else if (curr_x > 0 && traceMap[curr_y][curr_x-1] == index-1) {
				dirStack.push(2);
				curr_x--;
			}
		}

		
		System.out.println("focus : " + focus_x + "," + focus_y);
		System.out.println("curr : " + curr_x + "," + curr_y);

		//System.out.print("path: ");
		//for (int i=dirStack.size()-1; i>=0; i--) {
		//	System.out.print(dirStack.get(i) + "->");
		//}
		//System.out.println();
	}

	public void printCheckMap(int[][] pmap) {
		for (int i = 0; i < pmap.length; i++) {
			for (int j = 0; j < pmap[i].length; j++) {
				System.out.printf("%2d ", pmap[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	public int nextMove(int[][] smap) {
		
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
	public boolean discoverDone() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0 || map[i][j] == 2) {
					if (map[i - 1][j] == -1 || map[i][j - 1] == -1 || map[i + 1][j] == -1 || map[i][j + 1] == -1) {
//						System.out.println("dd : " + j + "," + i);
						return false;
					}
				}
			}
		}

		// 모든 지도의 탐색이 끝났다면, 최단거리를 찾아 dir로 queue를 만들어야 한다.
		System.out.println("Map Discovery is over. Finding Shortest Path...");

		return true;
	}

	public int nextSearch(int[][] smap) {

		System.out.println("x:"+x+"y:"+y);
		int[] rp_X = { 0, 2, 1, 0, 1 }; // right position by current dir
		int[] rp_Y = { 0, 1, 2, 1, 0 };

		int[] sp_X = { 0, 1, 2, 1, 0 }; // straight position by current dir
		int[] sp_Y = { 0, 0, 1, 2, 1 };

		int[] lp_X = { 0, 0, 1, 2, 1 }; // left position by current dir
		int[] lp_Y = { 0, 1, 0, 1, 2 };

		int[] rt_Dir = { 0, 2, 3, 4, 1 }; // 오른쪽 설쩡
		int[] ut_Dir = { 0, 3, 4, 1, 2 };
		int[] lt_Dir = { 0, 4, 1, 2, 3 }; // 왼쪽 설정
		this.overlabMap(smap);
//		this.printMap(map,x,y);
		if (discoverDone()) {
			dir = -1;
			return dir;
		}
		// 첫번째 탐색 - 오른손 법칙
		if (searchCount == 1) {
			// 현재 방향을 기준으로 오른쪽을 검사
			if (smap[rp_Y[dir]][rp_X[dir]] != 1) {
				// 오른쪽이 비어있으면 dir을 오른쪽으로 설정
				dir = rt_Dir[dir];
				this.moveXY(dir);
			} else if (smap[sp_Y[dir]][sp_X[dir]] != 1) {
				// 오른쪽이 막혀있으면, 직진 방향을 검사
				// 직진방향이 비어있으면 dir을 변함 없이
				this.moveXY(dir);
			} else {
				// 직진방향이 막혀있으면, dir을 반대 방향 뒤로 돌기 -> nextMove();
				dir = ut_Dir[dir];
				dir = nextSearch(smap);
			}
		}

		// Mouse가 다음으로 움직일 방향을 정한다.
		// 두번째 탐색 - 왼손 법칙
		if (searchCount == 2) {
			// 현재 방향을 기준으로 왼쪽을 검사
			if (smap[lp_Y[dir]][lp_X[dir]] != 1) {
				// 왼쪽이 비어있으면 dir을 왼쪽으로 설정
				dir = lt_Dir[dir];
				this.moveXY(dir);
			} else if (smap[sp_Y[dir]][sp_X[dir]] != 1) {
				// 왼쪽이 막혀있으면, 직진 방향을 검사
				// 직진방향이 비어있으면 dir을 변함 없이
				this.moveXY(dir);
			} else {
				// 직진방향이 막혀있으면, dir을 반대 방향 뒤로 돌기 -> nextMove();
				dir = ut_Dir[dir];
				dir = nextSearch(smap);

			}
		}

		// 세번째 탐색 - BFS
		if (searchCount == 3) {
			if(!isfocusfog) {
				findFog();
				dir= dirStack.pop();
				this.moveXY(dir);
			}else if((focus_x==x-1 && focus_y==y)||(focus_x==x && focus_y==y+1)||(focus_x==x+1 && focus_y==y)||(focus_x==x && focus_y==y-1)) {
				findFog();
				dir= dirStack.pop();
				this.moveXY(dir);
			}
			else {
				dir= dirStack.pop();
				this.moveXY(dir);
			}
		}

		if (searchCount == 1)
			System.out.println("오른손 법칙");
		else if (searchCount == 2)
			System.out.println("왼손 법칙");
		else if (searchCount == 3)
			System.out.println("너비 우선 탐색");

		return dir;
	}
	public void printSMap(int[][] smap) {
		for (int i = 0; i < smap.length; i++) {
			for (int j = 0; j < smap[i].length; j++) {
				System.out.print(smap[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}