package maze.challengemode;


import java.util.ArrayList;

import javax.swing.JOptionPane;

import maze.original.LogManager;
import maze.original.LogRank;
import maze.original.Maze;
import maze.original.Mouse;

public class ChallengeMode {
	private static Mouse mouse;
	private static Maze maze;

	public ChallengeMode(Maze maze, Mouse mouse) {
		this.maze=maze;
		this.mouse=mouse;
	};
	public static void main(String[] agrs) {
		ChallengeModeContainer cmc = new ChallengeModeContainer();
		ChallengePlayThread spt = new ChallengePlayThread(maze, mouse, cmc);
		ChallengeTimeThread stt = new ChallengeTimeThread(cmc);
		spt.start();
		stt.start();
		while(true) {
			if(!stt.isAlive()) {
				spt.timeover();
				cmc.addTotalSearch();
				break;
			}
			else if(!spt.isAlive()) {
				System.out.println("탐색 종료");
				cmc.addTotalSearch();
				break;
			}
		}

	}
}

class ChallengePlayThread extends Thread {
	private Mouse mouse;			//mouse객체
	private int start_x, start_y;	//시작 점
	private int curr_x, curr_y;		//현 위치
	private int esc_x, esc_y;		//탈출 좌표
	private Maze maze;				//maze 객체
	private int count;				//몇번 갔는지 확인하는 변수
	private boolean finished;		//도착해쓴지 확인하는 변수
	private boolean flag;			//쓰래드 종류하기 위한 변수
	private ChallengeModeContainer cmc;
	public ChallengePlayThread(Maze maze,Mouse mouse, ChallengeModeContainer cmc) {
		this.maze = maze;
		this.mouse = mouse;
		this.cmc=cmc;
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
			// this.setWindow(prev_x, prev_y, map); // 나중에 실시간으로 보여줄때 필요
			prev_x = curr_x;
			prev_y = curr_y;

			if ((curr_x == this.esc_x) && (curr_y == this.esc_y)) {
				// 성공 할 시 머할지 상의해서 할 것
				/*
				 * JOptionPane.showMessageDialog(null, "탈출에 성공했습니다. 총 이동 횟수 : " + count); //
				 * maze.storeMapToDB(mapName, map); // 랭킹 업로드 메소드 LogManager log = new
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
				flag=true;
			}
			i++;
		}

	}
	public void timeover() {flag=true;}
	public void run() {
		while(true) {
			if(flag==true) {
				return;
			}
			play(1); // 1번 실행
			cmc.addTotalMove();
		}
	}
}

class ChallengeTimeThread extends Thread {
	private ChallengeModeContainer cmc;
	private boolean flag = false;

	public ChallengeTimeThread(ChallengeModeContainer cmc) {
		this.cmc = cmc;
	}

	public void finish() {
		flag = true;
	}

	public void run() {
		cmc.start();
		long t=cmc.check();
		while (true) {
			try {
				t=cmc.check();
				if(t>120000)
					return;
				sleep(10);
				if (flag == true)
					cmc.check();
				return;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
// 탐색모드에 필요한 정보들 모음집
class ChallengeModeContainer {
	private long elapsedTime; // 경과된 시간
	private long startTime; // 시작 시간
	private int totalSearch;
	private int totalMove;
	
	// 선언 및 초기화
	public ChallengeModeContainer() {
		totalSearch=0;
		totalMove=0;
		this.init_time();
	}
	// 초기화
	public void init_time() {
		elapsedTime = 0;
	}
	// 세팅
	public void start() {
		startTime = System.currentTimeMillis();
	}
	public void addTotalSearch() {
		totalSearch++;
	}
	public void addTotalMove() {
		totalMove++;
	}
	// 경과된 시간 구하기
	synchronized public long check() {
		elapsedTime = startTime - System.currentTimeMillis();
		return elapsedTime;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}
	public int getTotalSearch() {
		return totalSearch;
	}
	public int getTotalMove() {
		return totalMove;
	}
}
