package maze.challengemode;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import boot.Mouse;
import maze.challengemode.*;

public class ModeThread extends Thread {
	private ModeContainer mc;
	public PlayThread pt;
	public TimeThread tt; 

	public ModeThread() {
		this.mc = new ModeContainer();
		//		this.spt = new SearchPlayThread(maze, mouse);// , mc);
		//		this.stt = new SearchTimeThread();// mc);
	}

	public ModeThread(Maze maze, Mouse mouse) {
		this.mc = new ModeContainer();
		this.pt = new PlayThread(maze, mouse);// , mc);
		this.tt = new TimeThread();// mc);
	}
	
	public int getCurr_x() {
		return pt.curr_x;
	}

	public int getCurr_y() {
		return pt.curr_y;
	}

	public ModeContainer runMode() {
		//		ModeContatiner mc = new ModeContatiner();

		pt.start();
		tt.start();
		while (true) {
			if (!tt.isAlive()) {
				pt.timeover();
				mc.addTotalSearch();
				System.out.println("�ð��ʰ�");
				break;
			} else if (!pt.isAlive()) {
				System.out.println("����");
				mc.addTotalSearch();
				break;
			}
		}
		return mc;
	}
	
	class PlayThread extends Thread {
		private Mouse mouse; // mouse��ü
		private int start_x, start_y; // ���� ��
		public int curr_x, curr_y; // �� ��ġ
		private int esc_x, esc_y; // Ż�� ��ǥ
		private Maze maze; // maze ��ü
		private int count; // ��� ������ Ȯ���ϴ� ����
		private boolean finished; // �����ؾ��� Ȯ���ϴ� ����
		private boolean flag; // ������ �����ϱ� ���� ����
		// private ModeContatiner mc;

		public PlayThread(Maze maze, Mouse mouse) {// , ModeContatiner mc) {
			this.maze = maze;
			this.mouse = mouse;
			//		this.mc = mc;
			this.start_x = maze.getStart_x();
			this.start_y = maze.getStart_y();
			this.esc_x = maze.getEsc_x();
			this.esc_y = maze.getEsc_y();

			count = 0;
			finished = false;
		}


		public void play(int move) {
			int[][] map = maze.getMap();
			int prev_x = curr_x;
			int prev_y = curr_y;

			int i = 0;
			while (!finished && (i < move || move == -1)) {
				int dir = mouse.nextMove(curr_x, curr_y, maze.getArea(curr_x, curr_y));

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
				// this.setWindow(prev_x, prev_y, map); // ���߿� �ǽð����� �����ٶ� �ʿ�
				prev_x = curr_x;
				prev_y = curr_y;

				if ((curr_x == this.esc_x) && (curr_y == this.esc_y)) {
					// ���� �� �� ������ �����ؼ� �� ��
					/*
					 * JOptionPane.showMessageDialog(null, "Ż�⿡ �����߽��ϴ�. �� �̵� Ƚ�� : " + count); //
					 * maze.storeMapToDB(mapName, map); // ��ŷ ���ε� �޼ҵ� LogManager log = new
					 * LogManager(); int mincount = log.getMinCount(mouseClassName, mapName);
					 * System.out.println(mincount);
					 * 
					 * if (count < mincount || mincount <= 0) { System.out.println("putlog:" +
					 * mouseClassName + " / " + mapName + " / " + count); ArrayList<LogRank>
					 * rankList = log.getRankingList(mapName);
					 * 
					 * for (int k = 0; k < rankList.size(); k++) { LogRank lr = rankList.get(k); if
					 * (lr.getMouse().contains(mouseClassName)) { log.deleteLog(lr.getId()); } }
					 * 
					 * log.putLog(mouseClassName, mapName, count); }
					 */
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

	class TimeThread extends Thread {
		//		private ModeContatiner mc;
		private boolean flag = false;
		private int LimitTime;

		public TimeThread() {
			LimitTime=0;
		}

		//		public SearchTimeThread(ModeContatiner mc) {
		//			this.mc = mc;
		//		}

		public void finish() {
			flag = true;
		}
		public void setTime(int time) {
			LimitTime=time;
		}

		public void run() {
			mc.start();
			
			long t = mc.check();
			while (true) {
				//				try {
				t = mc.check();
				
				if (t > LimitTime) {
					break;
				}
				//					sleep(10);
				if (flag == true) {
					mc.check();
					break;
				}
				//				} catch (InterruptedException e) {
				//					e.printStackTrace();
				//				}
			}
		}
	}
}
