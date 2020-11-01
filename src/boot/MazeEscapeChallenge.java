package boot;

import java.io.File;
import java.util.ArrayList;

import maze.challengemode.*;

public class MazeEscapeChallenge {
	public static String appTitle = "Maze Escape Challenge mode";
//  private static String defaultMapFile = "maps/testmap2.txt";
	private static String defaultMap = "testmap2";
	private static String defaultMouseDirectory = "bin/mice";
	public static String defaultMousePackage = "mice.";
	private static String defaultMouse = "RightHandMouse";
	
	public String mouseClassName;
	public String mapName;
	public Maze maze;
	public ArrayList<String> miceList;
	public ArrayList<String> mapList;
	
	public MouseChallenge mouse;
	public int start_x, start_y;
	public int curr_x, curr_y;
	public int esc_x, esc_y;
	
	public ChallengeInfo ci;
	
	public MazeEscapeChallenge() {
		// TODO Auto-generated constructor stub
		this.mouseClassName = defaultMouse;
		this.mapName = defaultMap;
		this.ci = new ChallengeInfo();
	}
	
	public void initChallengeInfo() {
		this.ci.initialize();
		this.ci.setLimitSearchMove(this.maze.getHeight()*this.maze.getWidth()/3);
	}

	public void loadMapList() {
		// Todo : DB로부터 Map List를 받아 this.mapList로 만든다.
		LogManager log = new LogManager();
		this.mapList = log.getMapNameList();
	}
	
	
	public void loadMap() {
		this.maze = new Maze();
		this.maze.loadMapFromDB(mapName);
		initMap();
	}
	
	public void loadMap(String mapName) {
		this.mapName = mapName;
		this.maze = new Maze();
		this.maze.loadMapFromDB(mapName);
		initMap();
	}
	
	public void initMap() {
		this.start_x = maze.getStart_x();
		this.start_y = maze.getStart_y();
		this.esc_x = maze.getEsc_x();
		this.esc_y = maze.getEsc_y();

		this.curr_x = this.start_x;
		this.curr_y = this.start_y;

//		this.ci.initialize();
//		this.ci.setLimitSearchMove(this.maze.getHeight()*this.maze.getWidth()/3);

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

		loadMouseClass(mouseClassName);
	}

	public void loadMouseClass(String mouseClassName) {
		String mouseName = defaultMousePackage + mouseClassName;
		try {
			Class<?> cls = Class.forName(mouseName);
			Object obj = cls.newInstance();

			mouse = (MouseChallenge) obj;
			
			mouse.printClassName();

			this.ci.initialize();
			this.ci.setLimitSearchMove(this.maze.getHeight()*this.maze.getWidth()/3);
		} catch (Exception e1) {
			System.out.println("Error: " + e1.getMessage());
			e1.printStackTrace();
		}
	}
	
	public void putLog() {
		LogManager log = new LogManager();
		System.out.println("putlog:" + mouseClassName + " / " + mapName + " / " + ci.getChallengeMove());
		ArrayList<LogRank> rankList = log.getRankingList(mapName);

		//ci를 활용하여 log 생성
//		for (int k = 0; k < rankList.size(); k++) {
//			LogRank lr = rankList.get(k);
//			if (lr.getMouse().contains(mouseClassName)) {
//				log.deleteLog(lr.getId());
//			}
//		}
//
//		log.putLog(mouseClassName, mapName, count);
	}

	public void putLog2() {
		LogManager log = new LogManager();
		System.out.println("putlog:" + mouseClassName + " / " + mapName + " / " + ci.getChallengeMove());

		// 완료되면 주석 풀 것
//		log.putChallengeLog(this.mouseClassName, this.mapName, (int) ci.getChallengeTime(),
//				(int) ci.getSearchCount(), (int) ci.getTotalSearchMove(),
//				(int) ci.getChallengeTime(), (int) ci.getChallengeMove());
	}
	
	
	public void readySearch() {
//		count = 0;
		
		curr_x = start_x;
		curr_y = start_y;
		
		ci.addSearchCount();
	}
	
	public boolean playSearch(int move) {
		int[][] map = maze.getMap();

		for (int i=0; i<move; i++) {
			int dir = mouse.nextSearch(maze.getArea(curr_x, curr_y));

			if (dir == 1 && curr_y > 0) {
				if (map[curr_y - 1][curr_x] == 0) curr_y--;
			} else if (dir == 2 && curr_x < maze.getWidth() - 1) {
				if (map[curr_y][curr_x + 1] == 0) curr_x++;
			} else if (dir == 3 && curr_y < maze.getHeight() - 1) {
				if (map[curr_y + 1][curr_x] == 0) curr_y++;
			} else if (dir == 4 && curr_x > 0) {
				if (map[curr_y][curr_x - 1] == 0) curr_x--;
			} else if (dir == -1) {
				return false;
			}

//			count++;
			ci.addOneSearchMove();
		}
		
		return true;
	}
	
	
	public boolean isMouseGoal() {
		if (curr_x == esc_x && curr_y == esc_y) {
			return true;
		}
		
		return false;
	}
	
	public boolean playChallenge(int move) {
		int[][] map = maze.getMap();

		for (int i=0; i<move; i++) {
			int dir = mouse.nextMove(maze.getArea(curr_x, curr_y));

			if (dir == 1 && curr_y > 0) {
				if (map[curr_y - 1][curr_x] == 0) curr_y--;
			} else if (dir == 2 && curr_x < maze.getWidth() - 1) {
				if (map[curr_y][curr_x + 1] == 0) curr_x++;
			} else if (dir == 3 && curr_y < maze.getHeight() - 1) {
				if (map[curr_y + 1][curr_x] == 0) curr_y++;
			} else if (dir == 4 && curr_x > 0) {
				if (map[curr_y][curr_x - 1] == 0) curr_x--;
			}

//			count++;
			ci.addOneChallengeMove();
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		MazeEscapeChallenge mec = new MazeEscapeChallenge();
		ChallengeModeGUI cm = new ChallengeModeGUI(mec);
		mec.loadMap();
		mec.loadMiceList();
		mec.loadMapList();
		cm.initWindow();
	}
}
