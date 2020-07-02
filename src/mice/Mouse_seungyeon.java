package mice;

public class Mouse_seungyeon extends Mouse{	
	private int dir;
	
	public Mouse_seungyeon() {
		dir = 2;
	}
	
	public int nextMove(int x, int y, int[][] smap) {
		if(dir==1) {
			if(smap[1][2]==0)
				return ++dir;
			if(smap[0][1]==0)
				return dir;
			if(smap[1][0]==0) {
				dir = 4;
				return dir;
			}
			dir = 3;
		}
		if(dir==2) {
			if(smap[2][1]==0)
				return ++dir;
			if(smap[1][2]==0)
				return dir;
			if(smap[0][1]==0) {
				return --dir;
			}
			dir = 4;
		}
		if(dir==3) {
			if(smap[1][0]==0)
				return ++dir;
			if(smap[2][1]==0)
				return dir;
			if(smap[1][2]==0) {
				return --dir;
			}
			dir = 1;
		}
		if(dir==4) {
			if(smap[0][1]==0) {
				dir = 1;
				return dir;
			}
			if(smap[1][0]==0)
				return dir;
			if(smap[2][1]==0) {
				return --dir;
			}
			dir = 2;
		}
		return dir;
	}
}