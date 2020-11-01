package maze.challengemode;

import boot.*;

public class PlayThread extends Thread {
	MazeEscapeChallenge mec;
	Maze maze;
	MouseChallenge mouse;
	ChallengeInfo ci;
	int mode;
	boolean playing;
	int[][] map;

	// mode 0 : searching mode, mode 1 : challenge mode
	public PlayThread(MazeEscapeChallenge mec, int mode) {
		this.mec = mec;
		this.maze = mec.maze;
		this.mouse = mec.mouse;
		this.ci = mec.ci;
		this.mode = mode;
		this.playing = true;

		this.map = maze.getMap();
		mec.curr_x = mec.start_x;
		mec.curr_y = mec.start_y;

		ci.addSearchCount();
	}

	private void moveMouse(int[][] map, int dir) {
		if (dir == 1 && mec.curr_y > 0) {
			if (map[mec.curr_y - 1][mec.curr_x] == 0)
				mec.curr_y--;
		} else if (dir == 2 && mec.curr_x < maze.getWidth() - 1) {
			if (map[mec.curr_y][mec.curr_x + 1] == 0)
				mec.curr_x++;
		} else if (dir == 3 && mec.curr_y < maze.getHeight() - 1) {
			if (map[mec.curr_y + 1][mec.curr_x] == 0)
				mec.curr_y++;
		} else if (dir == 4 && mec.curr_x > 0) {
			if (map[mec.curr_y][mec.curr_x - 1] == 0)
				mec.curr_x--;
		}
	}

	@Override
	public void run() {
		int dir = 0;
		try {
			while (playing) {
				// search mode에서 play
				if (mode == 0) {
					dir = mouse.nextSearch(maze.getArea(mec.curr_x, mec.curr_y));
					if (dir == -1) {
						playing = false;
						break;
					}

					this.moveMouse(maze.getMap(), dir);

					ci.addOneSearchMove();

					if (ci.getLastSearchMove() >= ci.getLimitSearchMove()) {
						playing = false;
						break;
					}
					sleep(ci.getDelaySearch());
				} else if (mode == 1) {
					// challenge mode에서 Play
					dir = mouse.nextMove(maze.getArea(mec.curr_x, mec.curr_y));

					this.moveMouse(maze.getMap(), dir);

					ci.addOneChallengeMove();

					if (isMouseGoal()) {
						playing = false;
						break;
					}
					
					sleep(ci.getDelayChallenge());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public void finish() {
		playing = false;
//		System.out.println("pt : finish");
	}

	public boolean isMouseGoal() {
		if (mec.curr_x == mec.esc_x && mec.curr_y == mec.esc_y) {
			return true;
		}

		return false;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
}
