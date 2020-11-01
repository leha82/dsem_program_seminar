package maze.challengemode;

import java.io.File;
import java.util.ArrayList;

import boot.*;

public class BaseModule {
//  private static String defaultMapFile = "maps/testmap2.txt";
	private static String defaultMap = "testmap2";
	private static String defaultMouseDirectory = "bin/mice";
	public static String defaultMousePackage = "mice.";
	private static String defaultMouse = "RightHandMouse";

	public ArrayList<String> miceList;
	public ArrayList<String> mapList;

	public String mouseClassName;
	public String mapName;
	public Maze maze;

	public MouseChallenge mouse;
	
//	public int curr_x, curr_y;
	
//	private ChallengeInfo ci;
	
	public BaseModule() {
		// TODO Auto-generated constructor stub
		this.mouseClassName = defaultMouse;
		this.mapName = defaultMap;
	}
	
	public void initialize() {
		// map을 불러온다
		loadMapList();
		loadMap(defaultMap);

		// mouse를 불러온다
		loadMiceList();
		loadMouseClass(mouseClassName);
	}
	
	/*
	 * Maze 및 Map에 관련된 부분
	 */

	public Maze getMaze() {
		return this.maze;
	}
	
	public void loadMapList() {
		// Todo : DB로부터 Map List를 받아 this.mapList로 만든다.
		LogManager log = new LogManager();
		this.mapList = log.getMapNameList();
	}
	
	public void loadMap(String mapName) {
		this.mapName = mapName;
		this.maze = new Maze();
		this.maze.loadMapFromDB(mapName);
	}

	public int[][] getMap() {
		return this.maze.getMap();
	}
	
	public int getMapHeight() {
		return this.maze.getHeight();
	}
	
	public int getMapWidth() {
		return this.maze.getWidth();
	}
	
	public int getStart_x() {
		return this.maze.getStart_x();
	}

	public int getStart_y() {
		return this.maze.getStart_y();
	}
	
	public int getEsc_x() {
		return this.maze.getEsc_x();
	}
	
	public int getEsc_y() {
		return this.maze.getEsc_y();
	}
	
	
	/*
	 * MouseChallenge 를 위한 메소드들
	 */
	
	public MouseChallenge getMouse() {
		return this.mouse;
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
	}

	public void loadMouseClass(String mouseClassName) {
		String mouseName = defaultMousePackage + mouseClassName;
		try {
			Class<?> cls = Class.forName(mouseName);
			Object obj = cls.newInstance();

			mouse = (MouseChallenge) obj;
			
			mouse.printClassName();
		} catch (Exception e1) {
			System.out.println("Error: " + e1.getMessage());
			e1.printStackTrace();
		}
	}
	
	/*
	 * Log와 관련된 메소드들
	 */
	
	public void putLog(ChallengeInfo ci) {
		LogManager log = new LogManager();
		System.out.println("putlog:" + mouseClassName + " / " + mapName + " / " + ci.getChallengeMove());

		// 완료되면 주석 풀 것
//		log.putChallengeLog(this.mouseClassName, this.mapName, (int) ci.getChallengeTime(),
//				(int) ci.getSearchCount(), (int) ci.getTotalSearchMove(),
//				(int) ci.getChallengeTime(), (int) ci.getChallengeMove());
	}

}
