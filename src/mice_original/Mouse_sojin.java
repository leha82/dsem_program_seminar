package mice_original;

import boot.Mouse;

public class Mouse_sojin extends Mouse {
	int mouse_dir = 2;
	
	@Override
	public int nextMove(int x, int y, int[][] smap) {
		int dir = 0;
		if (mouse_dir == 2) {
			if (smap[2][1] == 0) {
				dir = 3;
				mouse_dir = 3;
			} else if (smap[1][2] == 0) {
				dir = 2;
				mouse_dir = 2;
			} else if (smap[0][1] == 0) {
				dir = 1;
				mouse_dir = 1;
			} else {
				dir = 4;
				mouse_dir = 4;
			}
		} else if (mouse_dir == 3) {
			if (smap[1][0] == 0) {
				dir = 4;
				mouse_dir = 4;
			} else if (smap[2][1] == 0) {
				dir = 3;
				mouse_dir = 3;
			} else if (smap[1][2] == 0) {
				dir = 2;
				mouse_dir = 2;
			} else {
				dir = 1;
				mouse_dir = 1;
			}
		} else if (mouse_dir == 4) {
			if (smap[0][1] == 0) {
				dir = 1;
				mouse_dir = 1;
			} else if (smap[1][0] == 0) {
				dir = 4;
				mouse_dir = 4;
			} else if (smap[2][1] == 0) {
				dir = 3;
				mouse_dir = 3;
			} else  {
				dir = 2;
				mouse_dir = 2;
			}
		} else if (mouse_dir == 1) {
			if (smap[1][2] == 0) {
				dir = 2;
				mouse_dir = 2;
			} else if (smap[0][1] == 0) {
				dir = 1;
				mouse_dir = 1;
			} else if (smap[1][0] == 0) {
				dir = 4;
				mouse_dir = 4;
			} else  {
				dir = 3;
				mouse_dir = 3;
			}
		}

		return dir;
	}
}
