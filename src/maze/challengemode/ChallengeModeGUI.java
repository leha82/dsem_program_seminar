package maze.challengemode;

import java.awt.BorderLayout;
import java.awt.Container;
//import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import boot.*;
import maze.challengemode.*;

public class ChallengeModeGUI extends JFrame {
	MazeEscapeChallenge mec;

	private static int imgSize;
	private static int setX;
	private static int setY;
	private int totalSearchTime;
	private int totalMoveCount;
	private int totalSearchCount;
	private long[] searchTimeArray;
	private long[] searchMoveArray;
	private JPanel mainPanel;
	private JPanel mapPanel;
	private JPanel infoPanel;
	private JPanel infoPanel2;
	private JPanel infoPanel3;
	private JPanel search1;
	private JPanel search2;
	private JPanel search3;
	private JButton btnInit;
	private JButton btnShowRanking;
	private JButton btnSearch;
	private JButton btnChallenge;
	private JLabel lbFileName;
	private JLabel lbMouseName;
	private JLabel[][] mapLabels;
	private JLabel challengeResult;
	private JLabel challengeTime;
	private JLabel challengemoveCount;
	
	
	private JLabel[] totalSearch;
	private JLabel[] searchCount;
	private JLabel[] searchTime;
	private JLabel[] searchMoveCount;
//	private JScrollPane scroll;

	public ChallengeModeGUI() {

	}

	public ChallengeModeGUI(MazeEscapeChallenge mec) {
		this.mec = mec;
	}

	public void initWindow() {
		// window나 panel을 초기화 하는것을 찾아 볼 것
//      maze.loadMapFromDB(mapName);
		int[][] map = mec.maze.getMap();
		ArrayList<String> miceList = mec.miceList;
//      LoadMouseMenuActionListener loadMouseListener = new LoadMouseMenuActionListener();
		JMenuBar mousemenubar = new JMenuBar();

		JMenu mouseMenu = new JMenu("Load Mouse");
		JMenuItem item[] = new JMenuItem[miceList.size()];

		for (int i = 0; i < miceList.size(); i++) {
			item[i] = new JMenuItem(miceList.get(i));
			item[i].addActionListener(new LoadMouseMenuActionListener());
			mouseMenu.add(item[i]);
		}
		mousemenubar.add(mouseMenu);

		ArrayList<String> mapList = mec.mapList;
		JMenu mapMenu = new JMenu("Load Map");
		JMenuItem Mapitem[] = new JMenuItem[mapList.size()];
		for (int i = 0; i < mapList.size(); i++) {
			Mapitem[i] = new JMenuItem(mapList.get(i));
			Mapitem[i].addActionListener(new LoadMapMenuActionListener());
			mapMenu.add(Mapitem[i]);
		}

		mousemenubar.add(mapMenu);
		setJMenuBar(mousemenubar);
		setSize(250, 250);
		setVisible(true);

		mapPanel = new JPanel();
		mapPanel.setLayout(new GridBagLayout());
		mapLabels = new JLabel[map.length][];

		GridBagConstraints gbc = new GridBagConstraints();

		for (int i = 0; i < map.length; i++) {
			mapLabels[i] = new JLabel[map[i].length];

			for (int j = 0; j < map[i].length; j++) {
				gbc.gridx = j;
				gbc.gridy = i;
				changeImageSize(map);
				if (mec.curr_x == j && mec.curr_y == i) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
				} else if (mec.esc_x == j && mec.esc_y == i) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/goal" + imgSize + ".jpg"));
				} else if (map[i][j] == 1) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/wall" + imgSize + ".jpg"));
				} else {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
				}
				mapPanel.add(mapLabels[i][j], gbc);
			}
		}
//		scroll = new JScrollPane();
//		Dimension size = new Dimension();
//		size.setSize(500,500);
//		mapPanel.setPreferredSize(size);
//		scroll.setViewportView(mapPanel);

		btnInit = new JButton("초기화");
		btnInit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Todo : 초기화 버튼을 눌렀을때 동작 추가
				int map[][] = mec.maze.getMap();
				mapPanel.remove(mapLabels[mec.curr_y][mec.curr_x]);
				mapLabels[mec.curr_y][mec.curr_x] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
				gbc.gridx = mec.curr_x;
				gbc.gridy = mec.curr_y;
				mapPanel.add(mapLabels[mec.curr_y][mec.curr_x], gbc);

				mec.initMap();

				mapPanel.remove(mapLabels[mec.curr_y][mec.curr_x]);
				mapLabels[mec.curr_y][mec.curr_x] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
				gbc.gridx = mec.curr_x;
				gbc.gridy = mec.curr_y;
				mapPanel.add(mapLabels[mec.curr_y][mec.curr_x], gbc);

				mapPanel.remove(mapLabels[mec.esc_y][mec.esc_x]);
				mapLabels[mec.esc_y][mec.esc_x] = new JLabel(new ImageIcon("res/goal" + imgSize + ".jpg"));
				gbc.gridx = mec.esc_x;
				gbc.gridy = mec.esc_y;
				mapPanel.add(mapLabels[mec.esc_y][mec.esc_x], gbc);

				lbFileName.setText("    맵 이름 : " + mec.mapName + "    ");
				lbMouseName.setText("    마우스 이름 : " + mec.mouseClassName + "    ");

				challengeResult.setText("");
				challengeTime.setText("");
				challengemoveCount.setText("");
				
				for(int i = 0; i<3; i++) {
					searchTimeArray[i] = 0;
					searchMoveArray[i] = 0;
					searchCount[i].setText("");
					searchTime[i].setText("");
					searchMoveCount[i].setText("");
					totalSearch[i].setText("");
					totalSearchTime = 0;
					totalMoveCount = 0;
					totalSearchCount = 0;
				}
				revalidate();
				repaint();
			}
		});

		btnShowRanking = new JButton("랭킹보기");
		btnShowRanking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ShowRanking();
			}
		});
		btnSearch = new JButton("탐색");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchMode sm = new SearchMode(mec.maze, mec.mouse);
				SearchModeContainer smc = sm.runSearchMode();
				try {
					int prev_x = sm.getCurr_x();
					int prev_y = sm.getCurr_y();
					while (sm.isAlive()) {
						mec.curr_x = sm.getCurr_x();
						mec.curr_y = sm.getCurr_y();
						setWindow(prev_x, prev_y, mec.maze.getMap());

						prev_x = mec.curr_x;
						prev_y = mec.curr_y;
						Thread.sleep(100);
					}
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}

				System.out.println("Total Move : " + smc.getTotalMove());
				System.out.println("Total Search : " + smc.getTotalSearch());
				System.out.println("Total ElapsedTime : " + smc.getElapsedTime());
				
				if(totalSearchCount<3) {
					searchMoveArray[totalSearchCount] =  smc.getTotalMove();
					searchTimeArray[totalSearchCount] = smc.getElapsedTime();
					totalSearchCount += 1;
					
				} else {
					searchMoveArray[0] = searchMoveArray[1];
					searchMoveArray[1] = searchMoveArray[2];
					searchTimeArray[0] = searchTimeArray[1];
					searchTimeArray[1] = searchTimeArray[2];
					searchMoveArray[2] = smc.getTotalMove();
					searchTimeArray [2] = smc.getElapsedTime();
				}
				// label에 표시하도록

				for(int i = 0; i< 3; i++) {
					searchCount[i].setText("탐색 횟수: "+ (i+1) +"         ");
					searchTime[i].setText("시간: " + searchTimeArray[i]+ " ms         ");
					searchMoveCount[i].setText("이동수: " + searchMoveArray[i]+"         ");
				}
				totalMoveCount += smc.getTotalMove();
				totalSearchTime += smc.getElapsedTime();
				totalSearch[0].setText("총 탐색 횟수: " + totalSearchCount);
				totalSearch[1].setText("총 시간: " + totalSearchTime);
				totalSearch[2].setText("총 이동수: " + totalMoveCount);

				
			}
		});

		btnChallenge = new JButton("도전");
		btnChallenge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChallengeMode cm = new ChallengeMode(mec.maze, mec.mouse);
				ChallengeModeContainer cms = new ChallengeModeContainer();
				challengeResult.setText("    도전 결과");
				challengeTime.setText("    도전 시간: " + cms.getElapsedTime()+ " ms");
				challengemoveCount.setText("    도전 이동 수: "+ cms.getTotalMove());
				
				
				// rank 에 넣도록
				
			}
		});
		totalSearchTime = 0;
		totalMoveCount = 0;
		totalSearchCount = 0;
		searchTimeArray = new long[3];
		searchMoveArray = new long[3];
		totalSearch = new JLabel[3];
		lbFileName = new JLabel("    맵 이름 : " + mec.mapName + "    ");
		lbMouseName = new JLabel("    마우스 이름 : " + mec.mouseClassName + "    ");

		infoPanel = new JPanel();
		infoPanel2 = new JPanel();
		infoPanel3 = new JPanel();
		search1 = new JPanel();
		search2 = new JPanel();
		search3 = new JPanel();
		infoPanel2.add(lbFileName);
		infoPanel2.add(lbMouseName);
		BoxLayout boxLayout1 = new BoxLayout(infoPanel2, BoxLayout.Y_AXIS);
		BoxLayout boxLayout2 = new BoxLayout(search1, BoxLayout.Y_AXIS);
		BoxLayout boxLayout3 = new BoxLayout(search2, BoxLayout.Y_AXIS);
		BoxLayout boxLayout4 = new BoxLayout(search3, BoxLayout.Y_AXIS);
		infoPanel2.setLayout(boxLayout1);
		search1.setLayout(boxLayout2);
		search2.setLayout(boxLayout3);
		search3.setLayout(boxLayout4);
		infoPanel.add(btnInit);
		infoPanel.add(btnSearch);
		infoPanel.add(btnChallenge);
		infoPanel.add(btnShowRanking);
		challengeResult = new JLabel("");
		challengeTime = new JLabel("");
		challengemoveCount = new JLabel("");
		searchCount = new JLabel[3];
		searchMoveCount = new JLabel[3];
		searchTime = new JLabel[3];
		infoPanel2.add(challengeResult);
		infoPanel2.add(challengeTime);
		infoPanel2.add(challengemoveCount);
		for(int i = 0; i<3; i++) {
			searchCount[i] = new JLabel("");
			searchTime[i] = new JLabel("");
			searchMoveCount[i] = new JLabel("");
			totalSearch[i] = new JLabel("");
			search1.add(searchCount[i]);
			search2.add(searchTime[i]);
			search3.add(searchMoveCount[i]);
		}
		infoPanel3.add(search1);
		infoPanel3.add(search2);
		infoPanel3.add(search3);
		search1.add(totalSearch[0]);
		search2.add(totalSearch[1]);
		search3.add(totalSearch[2]);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(mapPanel, "North");
		mainPanel.add(infoPanel2, "West");
		mainPanel.add(infoPanel, "South");
		mainPanel.add(infoPanel3, "East");
		add(mainPanel);
		setSize(setX * 60 + 100, setY * 60 + 50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/* item Action Listener */
	class LoadMouseMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			mec.mouseClassName = e.getActionCommand();
			System.out.println("Choice -> " + mec.mouseClassName);

			mec.changeMouseClass(mec.defaultMousePackage + mec.mouseClassName);

			GridBagConstraints gbc = new GridBagConstraints();

			int map[][] = mec.maze.getMap();
			mapPanel.remove(mapLabels[mec.curr_y][mec.curr_x]);
			mapLabels[mec.curr_y][mec.curr_x] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
			gbc.gridx = mec.curr_x;
			gbc.gridy = mec.curr_y;
			mapPanel.add(mapLabels[mec.curr_y][mec.curr_x], gbc);

			mec.initMap();

			mapPanel.remove(mapLabels[mec.curr_y][mec.curr_x]);
			mapLabels[mec.curr_y][mec.curr_x] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
			gbc.gridx = mec.curr_x;
			gbc.gridy = mec.curr_y;
			mapPanel.add(mapLabels[mec.curr_y][mec.curr_x], gbc);

			mapPanel.remove(mapLabels[mec.esc_y][mec.esc_x]);
			mapLabels[mec.esc_y][mec.esc_x] = new JLabel(new ImageIcon("res/goal" + imgSize + ".jpg"));
			gbc.gridx = mec.esc_x;
			gbc.gridy = mec.esc_y;
			mapPanel.add(mapLabels[mec.esc_y][mec.esc_x], gbc);

			lbFileName.setText("    맵 이름 : " + mec.mapName + "    ");
			lbMouseName.setText("    마우스 이름 : " + mec.mouseClassName + "    ");

			revalidate();
			repaint();
//         initWindow();
		}
	}

	public void paintMap(int map[][]) {
		mapPanel = new JPanel();
		mapPanel.setLayout(new GridBagLayout());

		mapLabels = new JLabel[map.length][];

		GridBagConstraints gbc = new GridBagConstraints();

		for (int i = 0; i < map.length; i++) {
			mapLabels[i] = new JLabel[map[i].length];

			for (int j = 0; j < map[i].length; j++) {
				gbc.gridx = j;
				gbc.gridy = i;

				if (mec.curr_x == j && mec.curr_y == i) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
				} else if (mec.esc_x == j && mec.esc_y == i) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/goal" + imgSize + ".jpg"));
				} else if (map[i][j] == 1) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/wall" + imgSize + ".jpg"));
				} else {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
				}
				mapPanel.add(mapLabels[i][j], gbc);
			}
		}

		mainPanel.add(mapPanel, "North");
		setSize(setX * 60 + 100, setY * 60 + 50);
		lbFileName.setText("    맵 이름 : " + mec.mapName + "    ");
		lbMouseName.setText("    마우스 이름 : " + mec.mouseClassName + "    ");
	}

	public void changeImageSize(int[][] map) {
		if (map.length <= 10) {
			imgSize = 50;
			setX = 10;
			setY = 10;
		} else if (map.length <= 50) {
			imgSize = 16;
			setX = 15;
			setY = 15;
		} else {
			imgSize = 9;
			setX = 15;
			setY = 16;
		}
	}

	class LoadMapMenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mec.mapName = e.getActionCommand();
			// Todo : mapName을 DB로부터 받아와서 maze에 저장될 수 있도록 한다.
			mec.loadMap();
			int[][] map = mec.maze.getMap();
			changeImageSize(map);

			mainPanel.remove(mapPanel);
			paintMap(map);
			mainPanel.revalidate();
		}
	}

	public void setWindow(int prev_x, int prev_y, int[][] map) {
		GridBagConstraints gbc = new GridBagConstraints();

		mapPanel.remove(mapLabels[prev_y][prev_x]);
		mapLabels[prev_y][prev_x] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
		gbc.gridx = prev_x;
		gbc.gridy = prev_y;
		mapPanel.add(mapLabels[prev_y][prev_x], gbc);

		mapPanel.remove(mapLabels[mec.curr_y][mec.curr_x]);
		mapLabels[mec.curr_y][mec.curr_x] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
		gbc.gridx = mec.curr_x;
		gbc.gridy = mec.curr_y;
		mapPanel.add(mapLabels[mec.curr_y][mec.curr_x], gbc);

		lbFileName.setText("    맵 이름 : " + mec.mapName + "    ");
		lbMouseName.setText("    마우스 이름 : " + mec.mouseClassName + "    ");

		revalidate();
		repaint();
	}
	class ShowRanking extends JFrame {
		public ShowRanking() {
			LogManager log = new LogManager();
			ArrayList<LogRank> rankList = log.getRankingList(mec.mapName);

			String[] column = { "Rank", "Mouse", "Map", "Registered time", "Searching count", "Searching time",
					"Searching moves", "Record time", "Moves" };
			String[][] row = new String[rankList.size()][9];
			for (int i = 0; i < rankList.size(); i++) {
				LogRank listline = rankList.get(i);
				row[i][0] = Integer.toString(i + 1);
				row[i][1] = listline.getMouse();
				row[i][2] = listline.getMapname();
				row[i][3] = listline.getTimestamp();
				row[i][4] = Integer.toString(listline.getSearch_count());
				row[i][5] = Integer.toString(listline.getSearch_time());
				row[i][6] = Integer.toString(listline.getSearch_moves());
				row[i][7] = Integer.toString(listline.getRecord_time());
				row[i][8] = Integer.toString(listline.getMoves());
			}
			setTitle("랭킹보기");
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);

			DefaultTableModel model = new DefaultTableModel(row, column);
			JTable table = new JTable(model);
			table.setRowHeight(25);
//			table.getColumnModel().getColumn(0).setPreferredWidth(10);
//			table.getColumnModel().getColumn(4).setPreferredWidth(10);
			for (int i = 0; i < column.length; i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(dtcr);
			}

			JScrollPane sc = new JScrollPane(table);
			Container c = getContentPane();
			c.add(sc);
			setSize(1000, 600);
			setVisible(true);
		}
	}

}
