package mice;

public class Mouse_seungyeon extends Mouse{
	private int dir, right;
	private int dir_x[] = { 0, 1, 2, 1 };
	private int dir_y[] = { 1, 2, 1, 0};
	
	public Mouse_seungyeon() {
		dir = 3;
		right = 3;
	}
	
	public int nextMove(int x, int y, int[][] smap) {	
		if(smap[dir_x[right]][dir_y[right]]==0) {
			if(right==3) {
				right=0; 
				dir = 4;
			}else if (dir == 4){
				dir = 1;
				right = 1;
			}else { 
				right = dir = dir+1; }
			System.out.println(dir);
			return dir;
		}
		if(smap[dir_x[dir-1]][dir_y[dir-1]]==0) {
			System.out.println(dir);
			return dir;
		}
		while(true) {
			if(dir==1) {
				dir = 4;
			}else {
				dir -= 1;
			}
			if(right == 0) {
				right = 3;
			}
			else {
				right -= 1;
			}
			if(smap[dir_x[right]][dir_y[right]]==0)
				System.out.println(dir);
				return dir;
			
		}
		
	}
}
