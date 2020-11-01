package maze.challengemode;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import boot.MouseChallenge;
import maze.challengemode.*;

public class ModePlay {
	private ModeContainer mc;
	private Maze maze;
	private MouseChallenge mouse;
	private ChallengeInfo ci;
	
	public ModePlayThread pt;
	public ModeTimeThread mtt; 
	public TimeThread tt;
	
	private boolean playingNow = false;

	public ModePlay() {
		this.mc = new ModeContainer();
	}

	public ModePlay(Maze maze, MouseChallenge mouse, ChallengeInfo ci, int mode) {
		this.maze = maze;
		this.mouse = mouse;
		this.mouse.initMouse();
		this.ci = ci;
		
		this.mc = new ModeContainer();
		this.pt = new ModePlayThread(maze.getMap(), maze.getStart_x(), maze.getStart_y(), maze.getEsc_x(), maze.getEsc_y(), mode);
		this.mtt = new ModeTimeThread();
		this.tt = new TimeThread(3000);
	}
	
	public int getCurr_x() {
		return pt.curr_x;
	}

	public int getCurr_y() {
		return pt.curr_y;
	}
	
	public boolean isPlaying() {
		return this.playingNow;
	}
	
	public ModeContainer runMode() {
		//		ModeContatiner mc = new ModeContatiner();

		pt.start();
		mtt.start();
		playingNow = true;
		while (playingNow) {
			
//			if (!tt.isAlive()) {
			if (!tt.isPlaying()) {
				pt.timeover();
				mc.addTotalSearch();
				System.out.println("�ð��ʰ�");
				this.playingNow = false;
			} else if (!pt.isAlive()) {
				System.out.println("����");
				mtt.finish();
				tt.finish();
				mc.addTotalSearch();
				this.playingNow = false;
			}
		}
		return mc;
	}
	
	class ModePlayThread extends Thread {
		private int[][] map; 
		private int start_x, start_y; // ���� ��
		private int curr_x, curr_y; // �� ��ġ
		private int esc_x, esc_y; // Ż�� ��ǥ
		private int count; // ��� ������ Ȯ���ϴ� ����
		private boolean finished; // �����ؾ��� Ȯ���ϴ� ����
		private boolean flag; // ������ �����ϱ� ���� ����
		private int mode; // 0: Ž�� ���, 1: ���� ���

		public ModePlayThread(int map[][], int start_x, int start_y, int esc_x, int esc_y, int mode) {
			this.map = map;
			this.start_x = start_x;
			this.start_y = start_y;
			this.esc_x = esc_x;
			this.esc_y = esc_y;
			this.mode = mode;
			
			this.count = 0;
			this.finished = false;
		}

		// Ž������϶� mouse.nextSearch�� -1�̸� Ž�� ����
		// Ž�� ����� �� mouse�� goal���� ���� ��� ������ ��
		
		// ���� ����϶� mouse�� goal���� ���� ����
		public void play(int move) {
			int prev_x = curr_x;
			int prev_y = curr_y;

			int i = 0;
			while (!finished && (i < move || move == -1)) {
				int dir=0;
				
				if (mode == 0) {
					dir = mouse.nextMove(maze.getArea(curr_x, curr_y));
				} else if (mode == 1) {
					dir = mouse.nextSearch(maze.getArea(curr_x, curr_y));
				}

				if (dir == 1 && curr_y > 0) {
					if (map[curr_y - 1][curr_x] == 0)
						curr_y--;
				} else if (dir == 2 && curr_x < maze.getWidth() - 1) {
					if (map[curr_y][curr_x + 1] == 0)
						curr_x++;
				} else if (dir == 3 && curr_y < maze.getHeight() - 1) {
					if (map[curr_y + 1][curr_x] == 0)
						curr_y++;
				} else if (dir == 4 && curr_x > 0) {
					if (map[curr_y][curr_x - 1] == 0)
						curr_x--;
				}

				count++;
				prev_x = curr_x;
				prev_y = curr_y;

				if (mode == 0 && (count >= ci.getLimitSearchMove() || dir == -1)) {
					finished = true;
					flag = true;
				}
				
				if (mode == 1 && (curr_x == this.esc_x) && (curr_y == this.esc_y)) {
					finished = true;
					flag = true;
				}
				
				i++;
			}

		}

		public void timeover() {
			flag = true;
		}

		public void run() {
			while (!flag) {
				//			if(flag) {
				//				return;
				//			}
				play(1); // 1�� ����
				mc.addTotalMove();
			}
		}
	}

	class ModeTimeThread extends Thread {
		//		private ModeContatiner mc;
		private boolean flag = false;
		private int limitTime;

		public ModeTimeThread() {
			this.limitTime=0;
		}

		//		public SearchTimeThread(ModeContatiner mc) {
		//			this.mc = mc;
		//		}

		public void finish() {
			this.flag = true;
		}
		public void setTime(int time) {
			this.limitTime=time;
		}

		public void run() {
			mc.start();
			
			long t = mc.check();
			while (!this.flag) {
				t = mc.check();
				if (t > this.limitTime) {
					this.flag=true;
				}
			}
		}
	}
}
