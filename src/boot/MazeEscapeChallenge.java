package boot;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import maze.challengemode.*;

public class MazeEscapeChallenge {
	private static String appTitle = "Maze Escape Challenge mode";
//  private static String defaultMapFile = "maps/testmap2.txt";
	private static String defaultMap = "testmap2";
	private static String defaultMouseDirectory = "bin/mice";
	private static String defaultMousePackage = "mice.";
	private static String defaultMouse = "RightHandMouse";
	
	public String mouseClassName;
	public String mapName;
	public int count;
	public Maze maze;
	public ArrayList<String> miceList;
	public ArrayList<String> mapList;
	public Mouse mouse;
	public int start_x, start_y;
	public int curr_x, curr_y;
	public int esc_x, esc_y;

	private boolean finished;
	
	public MazeEscapeChallenge() {
		// TODO Auto-generated constructor stub
	}
	
	public void loadMap() {
		this.maze = new Maze();
		this.maze.loadMapFromDB(mapName);
		this.start_x = maze.getStart_x();
		this.start_y = maze.getStart_y();
		this.esc_x = maze.getEsc_x();
		this.esc_y = maze.getEsc_y();

		this.curr_x = this.start_x;
		this.curr_y = this.start_y;

		this.count = 0;
		this.finished = false;
	}

	public void initMap() {
		this.start_x = 0;
		this.start_y = 0;
		this.esc_x = maze.getEsc_x();
		this.esc_y = maze.getEsc_y();

		this.curr_x = this.start_x;
		this.curr_y = this.start_y;

		this.count = 0;
		this.finished = false;
	}

	public void loadMiceList() {
		miceList = new ArrayList<String>();

		File folder = new File(defaultMouseDirectory);
		File[] listOfFiles = folder.listFiles();
		String name;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
//            name = defaultMousePackage + listOfFiles[i].getName().replaceAll(".class", "");
				name = listOfFiles[i].getName().replaceAll(".class", "");
				miceList.add(name);
			}
		}

		changeMouseClass(defaultMousePackage + mouseClassName);
	}

	public void loadMapList() {
		// Todo : DB로부터 Map List를 받아 this.mapList로 만든다.
		LogManager log = new LogManager();
		this.mapList = log.getMapNameList();
	}

	private void changeMouseClass(String mouseName) {
		try {
			Class<?> cls = Class.forName(mouseName);
			Object obj = cls.newInstance();

			mouse = (Mouse) obj;
			mouse.printClassName();
			// setWindow(curr_x, curr_y, map);

		} catch (Exception e1) {
			System.out.println("Error: " + e1.getMessage());
			e1.printStackTrace();
		}
	}
	
	public void putLog() {
		System.out.println("putlog:" + mouseClassName + " / " + mapName + " / " + count);
		ArrayList<LogRank> rankList = log.getRankingList(mapName);

		for (int k = 0; k < rankList.size(); k++) {
			LogRank lr = rankList.get(k);
			if (lr.getMouse().contains(mouseClassName)) {
				log.deleteLog(lr.getId());
			}
		}

		log.putLog(mouseClassName, mapName, count);
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
//			this.setWindow(prev_x, prev_y, map);
//			prev_x = curr_x;
//			prev_y = curr_y;
//
//			if ((curr_x == this.esc_x) && (curr_y == this.esc_y)) {
//				JOptionPane.showMessageDialog(null, "탈출에 성공했습니다. 총 이동 횟수 : " + count);
//				// maze.storeMapToDB(mapName, map);
//				// 랭킹 업로드 메소드
//				LogManager log = new LogManager();
//				int mincount = log.getMinCount(mouseClassName, mapName);
//				System.out.println(mincount);
//
//				if (count < mincount || mincount <= 0) {
//					System.out.println("putlog:" + mouseClassName + " / " + mapName + " / " + count);
//					ArrayList<LogRank> rankList = log.getRankingList(mapName);
//
//					for (int k = 0; k < rankList.size(); k++) {
//						LogRank lr = rankList.get(k);
//						if (lr.getMouse().contains(mouseClassName)) {
//							log.deleteLog(lr.getId());
//						}
//					}
//
//					log.putLog(mouseClassName, mapName, count);
//				}
//				finished = true;
//
//			}
			i++;
		}

	}
	
	public static void main(String[] args) {
		MazeEscapeChallenge mec = new MazeEscapeChallenge();
		ChallengeModeGUI cm = new ChallengeModeGUI(mec);
//		cm.loadMap();
//		cm.loadMiceList();
//		cm.loadMapList();
//		cm.initWindow();
	}
}
