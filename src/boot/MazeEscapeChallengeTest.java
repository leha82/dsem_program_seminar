package boot;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
//import javax.swing.table.*;

import maze.challengemode.*;

public class MazeEscapeChallengeTest extends JFrame {
	private static final long serialVersionUID = -4289218043414710940L;

	public static String appTitle = "Maze Escape Challenge mode";

	private BaseModule bm;
	private ChallengeInfo ci;
	private PlayThread pt;
	
//	private JPanel plMain;
	private JPanel plMap;
//	private JPanel plButtons;
//	private JPanel plInfo1;
//	private JPanel plInfo2;
//	private JPanel plSearch1;
//	private JPanel plSearch2;
//	private JPanel plSearch3;
//	private JButton btnInit;
//	private JButton btnShowRanking;
//	private JButton btnSearch;
//	private JButton btnChallenge;

	private JLabel[][] lbsMap;
	private GridBagConstraints gbc;

	private JLabel lbFileName;
	private JLabel lbMouseName;
	
	private JLabel lbChallengeResult;
	private JLabel lbChallengeTime;
	private JLabel lbChallengeMoveCount;
	
	private JLabel lbTotalSearchCount;
	private JLabel lbTotalSearchTime;
	private JLabel lbTotalSearchMoves;
	private JLabel[] lbsSearchCount;
	private JLabel[] lbsSearchTime;
	private JLabel[] lbsSearchMoveCount;
	
	private static int imgSize;
	private static int setX, setY;

	private int prev_x, prev_y;
	
	public MazeEscapeChallengeTest(BaseModule bm, ChallengeInfo ci) {
		super(appTitle);
		
		initialize(bm, ci);
	}

	public void initialize(BaseModule bm, ChallengeInfo ci) {
		this.bm = bm;
		this.bm.initialize();
		
		this.ci = ci;
		initChallengeInfo();
	}
	
	public void initChallengeInfo() {
		this.ci.initialize();
		this.ci.setLimitSearchMove(bm.getMapHeight() * bm.getMapWidth() / 3);
	}
	
	public void initWindow() {
		JMenuBar menubar = new JMenuBar();

		JMenu menuMouse = new JMenu("Load Mouse");
		JMenuItem[] mitemsMouse = new JMenuItem[bm.miceList.size()];

		for (int i = 0; i < bm.miceList.size(); i++) {
			mitemsMouse[i] = new JMenuItem(bm.miceList.get(i));
			mitemsMouse[i].addActionListener(new LoadMouseMenuActionListener());
			menuMouse.add(mitemsMouse[i]);
		}
		menubar.add(menuMouse);

		JMenu menuMap = new JMenu("Load Map");
		JMenuItem[] mitemsMap = new JMenuItem[bm.mapList.size()];
		for (int i = 0; i < bm.mapList.size(); i++) {
			mitemsMap[i] = new JMenuItem(bm.mapList.get(i));
			mitemsMap[i].addActionListener(new LoadMapMenuActionListener());
			menuMap.add(mitemsMap[i]);
		}

		menubar.add(menuMap);
		setJMenuBar(menubar);

		int[][] map = bm.getMap();
		
		plMap = new JPanel();
		plMap.setLayout(new GridBagLayout());
		lbsMap = new JLabel[map.length][];
		
		changeImageSize(bm.getMapWidth(), bm.getMapHeight());
//		System.out.println(bm.getMapWidth() + "," + bm.getMapHeight());
		gbc = new GridBagConstraints();
		
		for (int i = 0; i < map.length; i++) {
			lbsMap[i] = new JLabel[map[i].length];

			for (int j = 0; j < map[i].length; j++) {
				gbc.gridx = j;
				gbc.gridy = i;
				
				if (bm.getStart_x() == j && bm.getStart_y() == i) {
					lbsMap[i][j] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
				} else if (bm.getEsc_x() == j && bm.getEsc_y() == i) {
					lbsMap[i][j] = new JLabel(new ImageIcon("res/goal" + imgSize + ".jpg"));
				} else if (map[i][j] == 1) {
					lbsMap[i][j] = new JLabel(new ImageIcon("res/wall" + imgSize + ".jpg"));
				} else {
					lbsMap[i][j] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
				}
				plMap.add(lbsMap[i][j], gbc);
			}
		}

		JButton btnInit = new JButton("초기화");
		btnInit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Todo : 초기화 버튼을 눌렀을때 동작 추가
				initChallengeInfo();
				initWindow();
				pt=null;
				prev_x = bm.getStart_x();
				prev_y = bm.getStart_y();
			}
		});

		
		JButton btnSearchMove1 = new JButton("탐색 1칸");
		btnSearchMove1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pt == null) {
					pt = new PlayThread(bm, ci, 0);
				}
				
				if(ci.isChallengeDone()) {
					JOptionPane.showMessageDialog(null, "이미 도전이 완료되었습니다.");
					System.out.println("이미 도전이 완료됨");
					return;
				}
				
				if(ci.getSearchCount() >= ci.getLimitSearchCount()) {
					JOptionPane.showMessageDialog(null, "탐색 횟수가 초과되었습니다.");
					System.out.println("탐색 횟수 초과");
					return;
				}

				if(ci.getLastSearchMove() >= ci.getLimitSearchMove()) {
					JOptionPane.showMessageDialog(null, "탐색 이동 횟수가 초과되었습니다. 탐색을 종료합니다.");
					System.out.println("탐색 이동 횟수 초과. 탐색 종료");
					pt = null;
					return;
				}
				
				try {
					pt.playSearch(1);
					
					changeTiles(pt.getCurr_x(), pt.getCurr_y());
					
					lbsSearchCount[ci.getSearchCount()].setText("탐색 횟수 : "+ (ci.getSearchCount()+1) +"         ");
					lbsSearchTime[ci.getSearchCount()].setText("시간 : " + ci.getLastSearchTime()+ " ms         ");
					lbsSearchMoveCount[ci.getSearchCount()].setText("이동수 : " + ci.getLastSearchMove() +"         ");
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
					e2.printStackTrace();
				}
			}
		});
		
		JButton btnSearchMove10 = new JButton("탐색 10칸");
		btnSearchMove10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pt == null) {
					pt = new PlayThread(bm, ci, 0);
				}
				
				if(ci.isChallengeDone()) {
					JOptionPane.showMessageDialog(null, "이미 도전이 완료되었습니다.");
					System.out.println("이미 도전이 완료됨");
					return;
				}
				
				if(ci.getSearchCount() >= ci.getLimitSearchCount()) {
					JOptionPane.showMessageDialog(null, "탐색 횟수가 초과되었습니다.");
					System.out.println("탐색 횟수 초과");
					return;
				}

				if(ci.getLastSearchMove() >= ci.getLimitSearchMove()) {
					JOptionPane.showMessageDialog(null, "탐색 이동 횟수가 초과되었습니다. 탐색을 종료합니다.");
					System.out.println("탐색 이동 횟수 초과. 탐색 종료");
					pt = null;
					return;
				}
				
				try {
					pt.playSearch(10);
					
					changeTiles(pt.getCurr_x(), pt.getCurr_y());
					
					lbsSearchCount[ci.getSearchCount()].setText("탐색 횟수 : "+ (ci.getSearchCount()+1) +"         ");
					lbsSearchTime[ci.getSearchCount()].setText("시간 : " + ci.getLastSearchTime()+ " ms         ");
					lbsSearchMoveCount[ci.getSearchCount()].setText("이동수 : " + ci.getLastSearchMove() +"         ");
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
					e2.printStackTrace();
				}
			}
		});
		
		JButton btnSearchMovetoEnd = new JButton("탐색 끝까지");
		btnSearchMovetoEnd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pt == null) {
					pt = new PlayThread(bm, ci, 0);
				}
				
				if(ci.isChallengeDone()) {
					JOptionPane.showMessageDialog(null, "이미 도전이 완료되었습니다.");
					System.out.println("이미 도전이 완료됨");
					return;
				}
				
				if(ci.getSearchCount() >= ci.getLimitSearchCount()) {
					JOptionPane.showMessageDialog(null, "탐색 횟수가 초과되었습니다.");
					System.out.println("탐색 횟수 초과");
					return;
				}

				if(ci.getLastSearchMove() >= ci.getLimitSearchMove()) {
					JOptionPane.showMessageDialog(null, "탐색 이동 횟수가 초과되었습니다. 탐색을 종료합니다.");
					System.out.println("탐색 이동 횟수 초과. 탐색 종료");
					pt = null;
					return;
				}
				
				try {
					pt.playSearch(-1);
					
					changeTiles(pt.getCurr_x(), pt.getCurr_y());
					
					lbsSearchCount[ci.getSearchCount()].setText("탐색 횟수 : "+ (ci.getSearchCount()+1) +"         ");
					lbsSearchTime[ci.getSearchCount()].setText("시간 : " + ci.getLastSearchTime()+ " ms         ");
					lbsSearchMoveCount[ci.getSearchCount()].setText("이동수 : " + ci.getLastSearchMove() +"         ");
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
					e2.printStackTrace();
				}
			}
		});

		JButton btnSearchStop = new JButton("탐색 종료");
		btnSearchStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pt = null;
			}
		});

		
		JButton btnChallenge = new JButton("도전");
		btnChallenge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ci.isChallengeDone()) {
					JOptionPane.showMessageDialog(null, "이미 도전이 완료되었습니다.");
					System.out.println("이미 도전이 완료됨");
					return;
				}
				
				TimeThread timeThread = new TimeThread(ci.getLimitChallengeTime());
				PlayThread playThread = new PlayThread(bm, ci, 1);
				
				ChallengeTimerActionListener ctal = new ChallengeTimerActionListener(timeThread, playThread); 
				Timer playTimer = new Timer (ci.getDelayShow(), ctal);
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

		
		JButton btnShowRanking = new JButton("랭킹보기");
		btnShowRanking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RankingGUI ranking = new RankingGUI(bm);
				ranking.showRanking();
			}
		});
		
		
		lbFileName = new JLabel("    맵 이름 : " + bm.mapName + "    ");
		lbMouseName = new JLabel("    마우스 이름 : " + bm.mouseClassName + "    ");
		lbChallengeResult = new JLabel("    도전 결과");
		lbChallengeTime = new JLabel("    도전 시간: ms");
		lbChallengeMoveCount = new JLabel("    도전 이동 수: ");

		JPanel plInfo1 = new JPanel();
		plInfo1.add(lbFileName);
		plInfo1.add(lbMouseName);
		plInfo1.add(lbChallengeResult);
		plInfo1.add(lbChallengeTime);
		plInfo1.add(lbChallengeMoveCount);

		plInfo1.setLayout(new BoxLayout(plInfo1, BoxLayout.Y_AXIS));

		
		JPanel plInfo2 = new JPanel();
		JPanel plSearch1 = new JPanel();
		JPanel plSearch2 = new JPanel();
		JPanel plSearch3 = new JPanel();
		
		plSearch1.setLayout(new BoxLayout(plSearch1, BoxLayout.Y_AXIS));
		plSearch2.setLayout(new BoxLayout(plSearch2, BoxLayout.Y_AXIS));
		plSearch3.setLayout(new BoxLayout(plSearch3, BoxLayout.Y_AXIS));

		lbsSearchCount = new JLabel[ci.getLimitSearchCount()];
		lbsSearchMoveCount = new JLabel[ci.getLimitSearchCount()];
		lbsSearchTime = new JLabel[ci.getLimitSearchCount()];
		
		for(int i = 0; i<ci.getLimitSearchCount(); i++) {
			lbsSearchCount[i] = new JLabel("탐색 횟수 :        ");
			lbsSearchTime[i] = new JLabel("시간 :  ms       ");
			lbsSearchMoveCount[i] = new JLabel("이동수 :        ");
			plSearch1.add(lbsSearchCount[i]);
			plSearch2.add(lbsSearchTime[i]);
			plSearch3.add(lbsSearchMoveCount[i]);
		}
		
		lbTotalSearchCount = new JLabel("총 탐색 횟수 : ");
		lbTotalSearchTime = new JLabel("총 탐색 시간 : ");
		lbTotalSearchMoves = new JLabel("총 탐색 이동수 : ");

		plSearch1.add(lbTotalSearchCount);
		plSearch2.add(lbTotalSearchTime);
		plSearch3.add(lbTotalSearchMoves);

		plInfo2.add(plSearch1);
		plInfo2.add(plSearch2);
		plInfo2.add(plSearch3);
		
		
		JPanel plButtons = new JPanel();
		plButtons.add(btnInit);
		plButtons.add(btnSearchMove1);
		plButtons.add(btnSearchMove10);
		plButtons.add(btnSearchMovetoEnd);
		plButtons.add(btnSearchStop);
		plButtons.add(btnChallenge);
		plButtons.add(btnShowRanking);
		
		JPanel plMain = new JPanel();
		plMain.setLayout(new BorderLayout());
//		plMain.add(plMap, "North");
		plMain.add(plInfo1, "West");
		plMain.add(plInfo2, "East");
		plMain.add(plButtons, "South");
		
		plMain.setPreferredSize(new Dimension(800,400));
		
		Container ct = getContentPane();
		ct.removeAll();
		
		JScrollPane plScroll = new JScrollPane(plMap);
		plScroll.setAutoscrolls(true);
		ct.add(plScroll, "North");
		
		ct.add(plMain);
		
		ct.revalidate();
		ct.repaint();
//		setSize(setX * 60 + 100, setY * 60 + 50);
		setSize(setX , setY + 60);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void initLabels() {
		initChallengeInfo();

		lbFileName.setText("    맵 이름 : " + bm.mapName + "    ");
		lbMouseName.setText("    마우스 이름 : " + bm.mouseClassName + "    ");
		lbChallengeResult.setText("    도전 결과");
		lbChallengeTime.setText("    도전 시간 : ");
		lbChallengeMoveCount.setText("    도전 이동 수 : ");
		
		for(int i = 0; i<ci.getLimitSearchCount(); i++) {
			lbsSearchCount[i].setText("탐색 횟수 :          ");
			lbsSearchTime[i].setText("시간 :             ");
			lbsSearchMoveCount[i].setText("이동수 :          ");
			lbTotalSearchCount.setText("총 탐색 횟수 : ");
			lbTotalSearchTime.setText("총 탐색 시간 : ");
			lbTotalSearchMoves.setText("총 탐색 이동수 : ");
		}
	}
	
	/* item Action Listener */
	class LoadMouseMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			bm.mouseClassName = e.getActionCommand();
			System.out.println("Choice -> " + bm.mouseClassName);

			bm.loadMouseClass(bm.mouseClassName);
			initChallengeInfo();
			
			initWindow();
		}
	}

	public void changeImageSize(int x, int y) {
		if (x <= 10) {
			imgSize = 50;
			setX = 10 * 60 + 50;
		} else if (x <= 50) {
			imgSize = 16;
			setX = 15 * 60 + 50;
		} else {
			imgSize = 9;
			setX = 15 * 60 + 50;
		}
		
		if (y <= 10) {
			setY = 10 * 60 + 50;
		} else if (y <= 50) {
			setY = 15 * 60 + 50;
		} else {
			setY = 16 * 60 + 50;
		}
	}

	class LoadMapMenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String mapName = e.getActionCommand();
			// Todo : mapName을 DB로부터 받아와서 maze에 저장될 수 있도록 한다.
			bm.loadMap(mapName);
			initChallengeInfo();
			
			System.out.println("Load Map Name : " + mapName);
			System.out.println("Map size : " + bm.getMapHeight() + ", " + bm.getMapWidth());

			initWindow();
		}
	}
	
	public void changeTiles(int curr_x, int curr_y) {
		plMap.remove(lbsMap[prev_y][prev_x]);
		lbsMap[prev_y][prev_x] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
		gbc.gridx = prev_x;
		gbc.gridy = prev_y;
		plMap.add(lbsMap[prev_y][prev_x], gbc);

		plMap.remove(lbsMap[curr_y][curr_x]);
		lbsMap[curr_y][curr_x] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
		gbc.gridx = curr_x;
		gbc.gridy = curr_y;
		prev_x = curr_x;
		prev_y = curr_y;

		plMap.add(lbsMap[curr_y][curr_x], gbc);

		revalidate();
		repaint();
	}
	
	class SearchTimerActionListener implements ActionListener {
		private Timer timer;
		private TimeThread timeThread;
		private PlayThread playThread;
		
		public SearchTimerActionListener(TimeThread timeThread, PlayThread playThread) {
			this.timeThread = timeThread;
			this.playThread = playThread;

			prev_x = this.playThread.getCurr_x();
			prev_y = this.playThread.getCurr_y();
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
			
			lbTotalSearchCount.setText("총 탐색 횟수 : " + (ci.getSearchCount()+1));
			lbTotalSearchTime.setText("총 시간 : " + ci.getTotalSearchTime() + " ms");
			lbTotalSearchMoves.setText("총 이동수 : " + ci.getTotalSearchMove());
			
			lbsSearchCount[ci.getSearchCount()].setText("탐색 횟수 : "+ (ci.getSearchCount()+1) +"         ");
			lbsSearchTime[ci.getSearchCount()].setText("시간 : " + ci.getLastSearchTime()+ " ms         ");
			lbsSearchMoveCount[ci.getSearchCount()].setText("이동수 : " + ci.getLastSearchMove() +"         ");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				// 해당시간이 지나면 종료
				if (!playThread.isAlive()) {
					finishSearch();
					System.out.println("탐색 종료 : 게임 종료");
					return;
				} else if (!timeThread.isAlive()) {
					// 시간 오버이면 게임을 종료한다
					ci.setLastSearchTime(ci.getLimitSearchTime());

					finishSearch();
					System.out.println("탐색 종료 : 시간 초과(" + ci.getLimitSearchTime() + " ms)");

					return;
				} 

				ci.setLastSearchTime(timeThread.getElapsedTime());
				
				// 화면에 출력한다
				changeTiles(playThread.getCurr_x(), playThread.getCurr_y());
				
				lbsSearchCount[ci.getSearchCount()].setText("탐색 횟수 : "+ (ci.getSearchCount()+1) +"         ");
				lbsSearchTime[ci.getSearchCount()].setText("시간 : " + ci.getLastSearchTime()+ " ms         ");
				lbsSearchMoveCount[ci.getSearchCount()].setText("이동수 : " + ci.getLastSearchMove() +"         ");
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
				e2.printStackTrace();
			}

		}
	}
	
	class ChallengeTimerActionListener implements ActionListener {
		private Timer timer;
		private TimeThread timeThread;
		private PlayThread playThread;
		
		public ChallengeTimerActionListener(TimeThread timeThread, PlayThread playThread) {
			this.timeThread = timeThread;
			this.playThread = playThread;

			prev_x = this.playThread.getCurr_x();
			prev_y = this.playThread.getCurr_y();
		}
		
		public void setTimer(Timer timer) {
			this.timer = timer;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				// 해당시간이 지나면 종료
				if (!playThread.isAlive()) {
					finishChallenge(true);
					System.out.println("도전 성공 : 목표 도착");
					return;
				} else if (!timeThread.isAlive()) {
					// 시간 오버이면 게임을 종료한다
					ci.setChallengeTime(ci.getLimitChallengeTime());
					
					finishChallenge(false);
					System.out.println("도전 실패 : 시간 초과(" + ci.getLimitChallengeTime() + " ms)");
					return;
				} 
				
				ci.setChallengeTime(timeThread.getElapsedTime());
//				System.out.println("challenge time : " + ci.getChallengeTime());

				// 화면에 출력한다
				changeTiles(playThread.getCurr_x(), playThread.getCurr_y());
				
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
				e2.printStackTrace();
			}

		}
		
		public void finishChallenge(boolean isSuccess) {
			timeThread.finish();
			playThread.finish();
			this.timer.stop();
			
			lbChallengeTime.setText("    도전 시간 : " + (int) ci.getChallengeTime() + " ms");
			lbChallengeMoveCount.setText("    도전 이동 수 : " + ci.getChallengeMove());

			System.out.println("[Challenge] Moves / Elaspsed Time : " 
					+ ci.getChallengeMove() + " / " + ci.getChallengeTime());

			// 성공이면 로그 쓰기
			if (isSuccess) {
				lbChallengeResult.setText("    도전 결과 : 성공");
				// cmLog 넣는 부분
				bm.putLog(ci);
			} else {
				lbChallengeResult.setText("    도전 결과 : 실패");
			}
		}
	}
	
	
	public static void main(String[] args) {
		ChallengeInfo ci = new ChallengeInfo(); 
		BaseModule bm = new BaseModule();
		
		MazeEscapeChallengeTest mec = new MazeEscapeChallengeTest(bm, ci);
		
		mec.initWindow();
	}
}
