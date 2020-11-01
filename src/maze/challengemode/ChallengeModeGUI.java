package maze.challengemode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.*;

import boot.*;
import maze.challengemode.*;

public class ChallengeModeGUI extends JFrame {
	private MazeEscapeChallenge mec;

	private GridBagConstraints gbc;
	private JMenuBar menubar;
	private JMenu menuMouse;
	private JMenuItem[] mitemsMouse;
	
	private JMenu menuMap;
	private JMenuItem[] mitemsMap; 
	
	private JPanel plMain;
	private JPanel plMap;
	private JPanel plInfo1;
	private JPanel plInfo2;
	private JPanel plInfo3;
	private JPanel plSearch1;
	private JPanel plSearch2;
	private JPanel plSearch3;
	private JButton btnInit;
	private JButton btnShowRanking;
	private JButton btnSearch;
	private JButton btnChallenge;
	private JLabel lbFileName;
	private JLabel lbMouseName;
	private JLabel[][] lbsMap;
	
	private JLabel lbChallengeResult;
	private JLabel lbChallengeTime;
	private JLabel lbChallengeMoveCount;
	
	private JLabel lbTotalSearchCount;
	private JLabel lbTotalSearchTime;
	private JLabel lbTotalSearchMoves;
	private JLabel[] lbsSearchCount;
	private JLabel[] lbsSearchTime;
	private JLabel[] lbsSearchMoveCount;
//	private JScrollPane scroll;
	
	private static int imgSize;
	private static int setX, setY;

	private int prev_x, prev_y;

	public ChallengeModeGUI() {

	}

	public ChallengeModeGUI(MazeEscapeChallenge mec) {
		super(mec.appTitle);
		this.mec = mec;
	}

	public void initWindow() {
		// window�� panel�� �ʱ�ȭ �ϴ°��� ã�� �� ��
//      maze.loadMapFromDB(mapName);
		int[][] map = mec.maze.getMap();
		ArrayList<String> miceList = mec.miceList;
//      LoadMouseMenuActionListener loadMouseListener = new LoadMouseMenuActionListener();
		menubar = new JMenuBar();

		menuMouse = new JMenu("Load Mouse");
		mitemsMouse = new JMenuItem[miceList.size()];

		for (int i = 0; i < miceList.size(); i++) {
			mitemsMouse[i] = new JMenuItem(miceList.get(i));
			mitemsMouse[i].addActionListener(new LoadMouseMenuActionListener());
			menuMouse.add(mitemsMouse[i]);
		}
		menubar.add(menuMouse);

		ArrayList<String> mapList = mec.mapList;
		menuMap = new JMenu("Load Map");
		mitemsMap = new JMenuItem[mapList.size()];
		for (int i = 0; i < mapList.size(); i++) {
			mitemsMap[i] = new JMenuItem(mapList.get(i));
			mitemsMap[i].addActionListener(new LoadMapMenuActionListener());
			menuMap.add(mitemsMap[i]);
		}

		menubar.add(menuMap);
		setJMenuBar(menubar);
		setSize(250, 250);
		setVisible(true);

		plMap = new JPanel();
		plMap.setLayout(new GridBagLayout());
		lbsMap = new JLabel[map.length][];

		gbc = new GridBagConstraints();

		for (int i = 0; i < map.length; i++) {
			lbsMap[i] = new JLabel[map[i].length];

			for (int j = 0; j < map[i].length; j++) {
				gbc.gridx = j;
				gbc.gridy = i;
				changeImageSize(map);
				if (mec.curr_x == j && mec.curr_y == i) {
					lbsMap[i][j] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
				} else if (mec.esc_x == j && mec.esc_y == i) {
					lbsMap[i][j] = new JLabel(new ImageIcon("res/goal" + imgSize + ".jpg"));
				} else if (map[i][j] == 1) {
					lbsMap[i][j] = new JLabel(new ImageIcon("res/wall" + imgSize + ".jpg"));
				} else {
					lbsMap[i][j] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
				}
				plMap.add(lbsMap[i][j], gbc);
			}
		}

		btnInit = new JButton("�ʱ�ȭ");
		btnInit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Todo : �ʱ�ȭ ��ư�� �������� ���� �߰�
				initBoard();
				initLabels();
				
				revalidate();
				repaint();
			}
		});

		
		btnShowRanking = new JButton("��ŷ����");
		btnShowRanking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RankingGUI ranking = new RankingGUI(mec);
				ranking.showRanking();
			}
		});
		
		
		btnSearch = new JButton("Ž��");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChallengeInfo ci = mec.ci;
				
				if(ci.isChallengeDone()) {
					System.out.println("�̹� ������ �Ϸ��");
					return;
				}
				
				if(ci.getSearchCount() >= ci.getLimitSearchCount()-1) {
					System.out.println("Ž�� Ƚ�� �ʰ�");
					return;
				}
//				
//				initBoard();
//				revalidate();
//				repaint();
				
				TimeThread timeThread = new TimeThread(ci.getLimitSearchTime());
				PlayThread playThread = new PlayThread(mec, 0);
				
				SearchTimerActionListener pal = new SearchTimerActionListener(timeThread, playThread);
				Timer playTimer = new Timer (0, pal);
				pal.setTimer(playTimer);
				
				try {
					timeThread.start();
					playThread.start();
					playTimer.start();

				} catch (Exception e2) {
					System.out.println(e2.getMessage());
					e2.printStackTrace();
				}
			}
		});

		
		btnChallenge = new JButton("����");
		btnChallenge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChallengeInfo ci = mec.ci;
				
				if(ci.isChallengeDone()) {
					System.out.println("�̹� ������ �Ϸ��");
					return;
				}
				
				TimeThread timeThread = new TimeThread(ci.getLimitChallengeTime());
				PlayThread playThread = new PlayThread(mec, 1);
				
				ChallengeTimerActionListener ctal = new ChallengeTimerActionListener(timeThread, playThread); 
				Timer playTimer = new Timer (0, ctal);
				ctal.setTimer(playTimer);
				
				try {
					timeThread.start();
					playThread.start();
					playTimer.start();

				} catch (Exception e2) {
					System.out.println(e2.getMessage());
					e2.printStackTrace();
				}
			}
		});
		
		
		lbTotalSearchCount = new JLabel("�� Ž�� Ƚ��: ");
		lbTotalSearchTime = new JLabel("�� Ž�� �ð�: ");
		lbTotalSearchMoves = new JLabel("�� Ž�� �̵���: ");
		lbFileName = new JLabel("    �� �̸� : " + mec.mapName + "    ");
		lbMouseName = new JLabel("    ���콺 �̸� : " + mec.mouseClassName + "    ");

		plInfo1 = new JPanel();
		plInfo2 = new JPanel();
		plInfo3 = new JPanel();
		plSearch1 = new JPanel();
		plSearch2 = new JPanel();
		plSearch3 = new JPanel();
		
		plInfo2.add(lbFileName);
		plInfo2.add(lbMouseName);
		BoxLayout boxLayout1 = new BoxLayout(plInfo2, BoxLayout.Y_AXIS);
		BoxLayout boxLayout2 = new BoxLayout(plSearch1, BoxLayout.Y_AXIS);
		BoxLayout boxLayout3 = new BoxLayout(plSearch2, BoxLayout.Y_AXIS);
		BoxLayout boxLayout4 = new BoxLayout(plSearch3, BoxLayout.Y_AXIS);
		plInfo2.setLayout(boxLayout1);
		plSearch1.setLayout(boxLayout2);
		plSearch2.setLayout(boxLayout3);
		plSearch3.setLayout(boxLayout4);
		
		plInfo1.add(btnInit);
		plInfo1.add(btnSearch);
		plInfo1.add(btnChallenge);
		plInfo1.add(btnShowRanking);
		
		lbChallengeResult = new JLabel("    ���� ���");
		lbChallengeTime = new JLabel("    ���� �ð�: ms");
		lbChallengeMoveCount = new JLabel("    ���� �̵� ��: ");
		lbsSearchCount = new JLabel[mec.ci.getLimitSearchCount()];
		lbsSearchMoveCount = new JLabel[mec.ci.getLimitSearchCount()];
		lbsSearchTime = new JLabel[mec.ci.getLimitSearchCount()];
		plInfo2.add(lbChallengeResult);
		plInfo2.add(lbChallengeTime);
		plInfo2.add(lbChallengeMoveCount);
		
		for(int i = 0; i<mec.ci.getLimitSearchCount(); i++) {
			lbsSearchCount[i] = new JLabel("Ž�� Ƚ��:          ");
			lbsSearchTime[i] = new JLabel("�ð�:  ms         ");
			lbsSearchMoveCount[i] = new JLabel("�̵���:          ");
			plSearch1.add(lbsSearchCount[i]);
			plSearch2.add(lbsSearchTime[i]);
			plSearch3.add(lbsSearchMoveCount[i]);
		}
		
		plInfo3.add(plSearch1);
		plInfo3.add(plSearch2);
		plInfo3.add(plSearch3);
		plSearch1.add(lbTotalSearchCount);
		plSearch2.add(lbTotalSearchTime);
		plSearch3.add(lbTotalSearchMoves);
		
		plMain = new JPanel();
		plMain.setLayout(new BorderLayout());
		plMain.add(plMap, "North");
		plMain.add(plInfo2, "West");
		plMain.add(plInfo1, "South");
		plMain.add(plInfo3, "East");
		
		Container ct = getContentPane();
		ct.removeAll();
		ct.revalidate();
		ct.repaint();
		ct.add(new JScrollPane(plMap), "North");
		ct.add(plMain);
		
//		setSize(setX * 60 + 100, setY * 60 + 50);
		setSize(setX-190 , setY+30);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initBoard() {
		int map[][] = mec.maze.getMap();

		mec.initMap();

		plMap.remove(lbsMap[mec.curr_y][mec.curr_x]);
		lbsMap[mec.curr_y][mec.curr_x] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
		gbc.gridx = mec.curr_x;
		gbc.gridy = mec.curr_y;
		plMap.add(lbsMap[mec.curr_y][mec.curr_x], gbc);

		plMap.remove(lbsMap[mec.curr_y][mec.curr_x]);
		lbsMap[mec.curr_y][mec.curr_x] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
		gbc.gridx = mec.curr_x;
		gbc.gridy = mec.curr_y;
		plMap.add(lbsMap[mec.curr_y][mec.curr_x], gbc);

		plMap.remove(lbsMap[mec.esc_y][mec.esc_x]);
		lbsMap[mec.esc_y][mec.esc_x] = new JLabel(new ImageIcon("res/goal" + imgSize + ".jpg"));
		gbc.gridx = mec.esc_x;
		gbc.gridy = mec.esc_y;
		plMap.add(lbsMap[mec.esc_y][mec.esc_x], gbc);
	}
	
	public void initLabels() {
		lbFileName.setText("    �� �̸� : " + mec.mapName + "    ");
		lbMouseName.setText("    ���콺 �̸� : " + mec.mouseClassName + "    ");

		mec.initChallengeInfo();

		lbChallengeResult.setText("    ���� ���");
		lbChallengeTime.setText("    ���� �ð�: ");
		lbChallengeMoveCount.setText("    ���� �̵� ��: ");
		
		for(int i = 0; i<mec.ci.getLimitSearchCount(); i++) {
			lbsSearchCount[i].setText("Ž�� Ƚ��:          ");
			lbsSearchTime[i].setText("�ð�:             ");
			lbsSearchMoveCount[i].setText("�̵���:          ");
			lbTotalSearchCount.setText("�� Ž�� Ƚ��: ");
			lbTotalSearchTime.setText("�� Ž�� �ð�: ");
			lbTotalSearchMoves.setText("�� Ž�� �̵���: ");
		}
	}
	
	/* item Action Listener */
	class LoadMouseMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			mec.mouseClassName = e.getActionCommand();
			System.out.println("Choice -> " + mec.mouseClassName);

			mec.loadMouseClass(mec.mouseClassName);
			
			initBoard();
			initLabels();

			revalidate();
			repaint();
		}
	}

	public void paintMap(int map[][]) {
		plMap = new JPanel();
		plMap.setLayout(new GridBagLayout());

		lbsMap = new JLabel[map.length][];

//		GridBagConstraints gbc = new GridBagConstraints();

		for (int i = 0; i < map.length; i++) {
			lbsMap[i] = new JLabel[map[i].length];

			for (int j = 0; j < map[i].length; j++) {
				gbc.gridx = j;
				gbc.gridy = i;

				if (mec.curr_x == j && mec.curr_y == i) {
					lbsMap[i][j] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
				} else if (mec.esc_x == j && mec.esc_y == i) {
					lbsMap[i][j] = new JLabel(new ImageIcon("res/goal" + imgSize + ".jpg"));
				} else if (map[i][j] == 1) {
					lbsMap[i][j] = new JLabel(new ImageIcon("res/wall" + imgSize + ".jpg"));
				} else {
					lbsMap[i][j] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
				}
				plMap.add(lbsMap[i][j], gbc);
			}
		}
		
		Container ct = getContentPane();
		ct.removeAll();
		ct.revalidate();
		ct.repaint();
		ct.add(new JScrollPane(plMap), "North");
		ct.add(plMain);

//		mainPanel.add(mapPanel, "North");
//		setSize(setX * 60 + 100, setY * 60 + 50);
		lbFileName.setText("    �� �̸� : " + mec.mapName + "    ");
		lbMouseName.setText("    ���콺 �̸� : " + mec.mouseClassName + "    ");
		
		setSize(setX-190,setY+30);
	}

	public void changeImageSize(int[][] map) {
		if (map.length <= 10) {
			imgSize = 50;
			setX = 10 * 60 + 100;
			setY = 10 * 60 + 50;
		} else if (map.length <= 50) {
			imgSize = 16;
			setX = 15 * 60 + 100;
			setY = 15 * 60 + 50;
		} else {
			imgSize = 9;
			setX = 15 * 60 + 100;
			setY = 16 * 60 + 50;
		}
	}

	class LoadMapMenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String mapName = e.getActionCommand();
			// Todo : mapName�� DB�κ��� �޾ƿͼ� maze�� ����� �� �ֵ��� �Ѵ�.
			mec.loadMap(mapName);
			mec.initChallengeInfo();
			
			initLabels();
//			
//			lbChallengeResult.setText("    ���� ���");
//			lbChallengeTime.setText("    ���� �ð� : ");
//			lbChallengeMoveCount.setText("    ���� �̵� ��: ");
//			for(int i = 0; i<mec.ci.getLimitSearchCount(); i++) {
//				lbsSearchCount[i].setText("Ž�� Ƚ�� :          ");
//				lbsSearchTime[i].setText("�ð�:  ms         ");
//				lbsSearchMoveCount[i].setText("�̵���:          ");
//				lbTotalSearchCount.setText("�� Ž�� Ƚ��: ");
//				lbTotalSearchTime.setText("�� �ð�: ");
//				lbTotalSearchMoves.setText("�� �̵���: ");
//			}
			
			int[][] map = mec.maze.getMap();
			changeImageSize(map);

			plMain.remove(plMap);
			paintMap(map);
			plMain.revalidate();
		}
	}
	
//	public void setWindow(int prev_x, int prev_y, int[][] map) {
	public void changeTiles(int[][] map) {
//		GridBagConstraints gbc = new GridBagConstraints();

		plMap.remove(lbsMap[prev_y][prev_x]);
		lbsMap[prev_y][prev_x] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
		gbc.gridx = prev_x;
		gbc.gridy = prev_y;
		plMap.add(lbsMap[prev_y][prev_x], gbc);

		plMap.remove(lbsMap[mec.curr_y][mec.curr_x]);
		lbsMap[mec.curr_y][mec.curr_x] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
		gbc.gridx = mec.curr_x;
		gbc.gridy = mec.curr_y;
		prev_x = mec.curr_x;
		prev_y = mec.curr_y;
		plMap.add(lbsMap[mec.curr_y][mec.curr_x], gbc);

//		lbFileName.setText("    �� �̸� : " + mec.mapName + "    ");
//		lbMouseName.setText("    ���콺 �̸� : " + mec.mouseClassName + "    ");

		revalidate();
		repaint();
	}
	
	class SearchTimerActionListener implements ActionListener {
		private ChallengeInfo ci;
		private Timer timer;
		private TimeThread timeThread;
		private PlayThread playThread;
		
		public SearchTimerActionListener(TimeThread timeThread, PlayThread playThread) {
			this.ci = mec.ci;
			
			this.timeThread = timeThread;
			this.playThread = playThread;

			prev_x = mec.curr_x;
			prev_y = mec.curr_y;
		}
		
		public void setTimer(Timer timer) {
			this.timer = timer;
		}
		
		public void finishSearch() {
			timeThread.finish();
			playThread.finish();
			this.timer.stop();

			ci.calculateTotalSearch();

			System.out.println("[Search #" + (ci.getSearchCount()+1) + "] Moves / Elaspsed Time : " 
					+ ci.getLastSearchMove() + " / " + ci.getLastSearchTime());
			
			lbTotalSearchCount.setText("�� Ž�� Ƚ�� : " + (ci.getSearchCount()+1));
			lbTotalSearchTime.setText("�� �ð� : " + ci.getTotalSearchTime() + " ms");
			lbTotalSearchMoves.setText("�� �̵��� : " + ci.getTotalSearchMove());
			
			lbsSearchCount[ci.getSearchCount()].setText("Ž�� Ƚ�� : "+ (ci.getSearchCount()+1) +"         ");
			lbsSearchTime[ci.getSearchCount()].setText("�ð� : " + ci.getLastSearchTime()+ " ms         ");
			lbsSearchMoveCount[ci.getSearchCount()].setText("�̵��� : " + ci.getLastSearchMove() +"         ");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				// �ش�ð��� ������ ����
				if (!playThread.isAlive()) {
					finishSearch();
					System.out.println("Ž�� ���� : ���� ����");
					return;
				} else if (!timeThread.isAlive()) {
					// �ð� �����̸� ������ �����Ѵ�
					ci.setLastSearchTime(ci.getLimitSearchTime());

					finishSearch();
					System.out.println("Ž�� ���� : �ð� �ʰ�(" + ci.getLimitSearchTime() + " ms)");

					return;
				} 

				ci.setLastSearchTime(timeThread.getElapsedTime());
				
				// ȭ�鿡 ����Ѵ�
				changeTiles(mec.maze.getMap());
				
				lbsSearchCount[ci.getSearchCount()].setText("Ž�� Ƚ�� : "+ (ci.getSearchCount()+1) +"         ");
				lbsSearchTime[ci.getSearchCount()].setText("�ð� : " + ci.getLastSearchTime()+ " ms         ");
				lbsSearchMoveCount[ci.getSearchCount()].setText("�̵��� : " + ci.getLastSearchMove() +"         ");
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
				e2.printStackTrace();
			}

		}
	}
	
	class ChallengeTimerActionListener implements ActionListener {
		private ChallengeInfo ci;
		private Timer timer;
		private TimeThread timeThread;
		private PlayThread playThread;
		
		public ChallengeTimerActionListener(TimeThread timeThread, PlayThread playThread) {
			this.ci = mec.ci;
			
			this.timeThread = timeThread;
			this.playThread = playThread;

			prev_x = mec.curr_x;
			prev_y = mec.curr_y;
		}
		
		public void setTimer(Timer timer) {
			this.timer = timer;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				// �ش�ð��� ������ ����
				if (!playThread.isAlive()) {
					finishChallenge(true);
					System.out.println("���� ���� : ��ǥ ����");
					return;
				} else if (!timeThread.isAlive()) {
					// �ð� �����̸� ������ �����Ѵ�
					ci.setChallengeTime(ci.getLimitChallengeTime());
					
					finishChallenge(false);
					System.out.println("���� ���� : �ð� �ʰ�(" + ci.getLimitChallengeTime() + " ms)");
					return;
				} 
				
				ci.setChallengeTime(timeThread.getElapsedTime());
//				System.out.println("challenge time : " + ci.getChallengeTime());
//				int currX = mec.curr_x;
//				int currY = mec.curr_y;
				// ȭ�鿡 ����Ѵ�
//				setWindow(prev_x, prev_y, mec.maze.getMap());
				changeTiles(mec.maze.getMap());
				
//				prev_x = currX;
//				prev_y = currY;		
				
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
				e2.printStackTrace();
			}

		}
		
		public void finishChallenge(boolean isSuccess) {
			timeThread.finish();
			playThread.finish();
			this.timer.stop();
			
			lbChallengeTime.setText("    ���� �ð� : " + (int) ci.getChallengeTime() + " ms");
			lbChallengeMoveCount.setText("    ���� �̵� �� : " + ci.getChallengeMove());

			System.out.println("[Challenge] Moves / Elaspsed Time : " 
					+ ci.getChallengeMove() + " / " + ci.getChallengeTime());

			// �����̸� �α� ����
			if (isSuccess) {
				lbChallengeResult.setText("    ���� ��� : ����");
				// cmLog �ִ� �κ�
				mec.putLog2();
			} else {
				lbChallengeResult.setText("    ���� ��� : ����");
			}
		}
	}
}
