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

		JButton btnInit = new JButton("�ʱ�ȭ");
		btnInit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Todo : �ʱ�ȭ ��ư�� �������� ���� �߰�
				initChallengeInfo();
				initWindow();
				pt=null;
				prev_x = bm.getStart_x();
				prev_y = bm.getStart_y();
			}
		});

		
		JButton btnSearchMove1 = new JButton("Ž�� 1ĭ");
		btnSearchMove1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pt == null) {
					pt = new PlayThread(bm, ci, 0);
				}
				
				if(ci.isChallengeDone()) {
					JOptionPane.showMessageDialog(null, "�̹� ������ �Ϸ�Ǿ����ϴ�.");
					System.out.println("�̹� ������ �Ϸ��");
					return;
				}
				
				if(ci.getSearchCount() >= ci.getLimitSearchCount()) {
					JOptionPane.showMessageDialog(null, "Ž�� Ƚ���� �ʰ��Ǿ����ϴ�.");
					System.out.println("Ž�� Ƚ�� �ʰ�");
					return;
				}

				if(ci.getLastSearchMove() >= ci.getLimitSearchMove()) {
					JOptionPane.showMessageDialog(null, "Ž�� �̵� Ƚ���� �ʰ��Ǿ����ϴ�. Ž���� �����մϴ�.");
					System.out.println("Ž�� �̵� Ƚ�� �ʰ�. Ž�� ����");
					pt = null;
					return;
				}
				
				try {
					pt.playSearch(1);
					
					changeTiles(pt.getCurr_x(), pt.getCurr_y());
					
					lbsSearchCount[ci.getSearchCount()].setText("Ž�� Ƚ�� : "+ (ci.getSearchCount()+1) +"         ");
					lbsSearchTime[ci.getSearchCount()].setText("�ð� : " + ci.getLastSearchTime()+ " ms         ");
					lbsSearchMoveCount[ci.getSearchCount()].setText("�̵��� : " + ci.getLastSearchMove() +"         ");
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
					e2.printStackTrace();
				}
			}
		});
		
		JButton btnSearchMove10 = new JButton("Ž�� 10ĭ");
		btnSearchMove10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pt == null) {
					pt = new PlayThread(bm, ci, 0);
				}
				
				if(ci.isChallengeDone()) {
					JOptionPane.showMessageDialog(null, "�̹� ������ �Ϸ�Ǿ����ϴ�.");
					System.out.println("�̹� ������ �Ϸ��");
					return;
				}
				
				if(ci.getSearchCount() >= ci.getLimitSearchCount()) {
					JOptionPane.showMessageDialog(null, "Ž�� Ƚ���� �ʰ��Ǿ����ϴ�.");
					System.out.println("Ž�� Ƚ�� �ʰ�");
					return;
				}

				if(ci.getLastSearchMove() >= ci.getLimitSearchMove()) {
					JOptionPane.showMessageDialog(null, "Ž�� �̵� Ƚ���� �ʰ��Ǿ����ϴ�. Ž���� �����մϴ�.");
					System.out.println("Ž�� �̵� Ƚ�� �ʰ�. Ž�� ����");
					pt = null;
					return;
				}
				
				try {
					pt.playSearch(10);
					
					changeTiles(pt.getCurr_x(), pt.getCurr_y());
					
					lbsSearchCount[ci.getSearchCount()].setText("Ž�� Ƚ�� : "+ (ci.getSearchCount()+1) +"         ");
					lbsSearchTime[ci.getSearchCount()].setText("�ð� : " + ci.getLastSearchTime()+ " ms         ");
					lbsSearchMoveCount[ci.getSearchCount()].setText("�̵��� : " + ci.getLastSearchMove() +"         ");
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
					e2.printStackTrace();
				}
			}
		});
		
		JButton btnSearchMovetoEnd = new JButton("Ž�� ������");
		btnSearchMovetoEnd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pt == null) {
					pt = new PlayThread(bm, ci, 0);
				}
				
				if(ci.isChallengeDone()) {
					JOptionPane.showMessageDialog(null, "�̹� ������ �Ϸ�Ǿ����ϴ�.");
					System.out.println("�̹� ������ �Ϸ��");
					return;
				}
				
				if(ci.getSearchCount() >= ci.getLimitSearchCount()) {
					JOptionPane.showMessageDialog(null, "Ž�� Ƚ���� �ʰ��Ǿ����ϴ�.");
					System.out.println("Ž�� Ƚ�� �ʰ�");
					return;
				}

				if(ci.getLastSearchMove() >= ci.getLimitSearchMove()) {
					JOptionPane.showMessageDialog(null, "Ž�� �̵� Ƚ���� �ʰ��Ǿ����ϴ�. Ž���� �����մϴ�.");
					System.out.println("Ž�� �̵� Ƚ�� �ʰ�. Ž�� ����");
					pt = null;
					return;
				}
				
				try {
					pt.playSearch(-1);
					
					changeTiles(pt.getCurr_x(), pt.getCurr_y());
					
					lbsSearchCount[ci.getSearchCount()].setText("Ž�� Ƚ�� : "+ (ci.getSearchCount()+1) +"         ");
					lbsSearchTime[ci.getSearchCount()].setText("�ð� : " + ci.getLastSearchTime()+ " ms         ");
					lbsSearchMoveCount[ci.getSearchCount()].setText("�̵��� : " + ci.getLastSearchMove() +"         ");
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
					e2.printStackTrace();
				}
			}
		});

		JButton btnSearchStop = new JButton("Ž�� ����");
		btnSearchStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pt = null;
			}
		});

		
		JButton btnChallenge = new JButton("����");
		btnChallenge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(ci.isChallengeDone()) {
					JOptionPane.showMessageDialog(null, "�̹� ������ �Ϸ�Ǿ����ϴ�.");
					System.out.println("�̹� ������ �Ϸ��");
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

		
		JButton btnShowRanking = new JButton("��ŷ����");
		btnShowRanking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RankingGUI ranking = new RankingGUI(bm);
				ranking.showRanking();
			}
		});
		
		
		lbFileName = new JLabel("    �� �̸� : " + bm.mapName + "    ");
		lbMouseName = new JLabel("    ���콺 �̸� : " + bm.mouseClassName + "    ");
		lbChallengeResult = new JLabel("    ���� ���");
		lbChallengeTime = new JLabel("    ���� �ð�: ms");
		lbChallengeMoveCount = new JLabel("    ���� �̵� ��: ");

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
			lbsSearchCount[i] = new JLabel("Ž�� Ƚ�� :        ");
			lbsSearchTime[i] = new JLabel("�ð� :  ms       ");
			lbsSearchMoveCount[i] = new JLabel("�̵��� :        ");
			plSearch1.add(lbsSearchCount[i]);
			plSearch2.add(lbsSearchTime[i]);
			plSearch3.add(lbsSearchMoveCount[i]);
		}
		
		lbTotalSearchCount = new JLabel("�� Ž�� Ƚ�� : ");
		lbTotalSearchTime = new JLabel("�� Ž�� �ð� : ");
		lbTotalSearchMoves = new JLabel("�� Ž�� �̵��� : ");

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

		lbFileName.setText("    �� �̸� : " + bm.mapName + "    ");
		lbMouseName.setText("    ���콺 �̸� : " + bm.mouseClassName + "    ");
		lbChallengeResult.setText("    ���� ���");
		lbChallengeTime.setText("    ���� �ð� : ");
		lbChallengeMoveCount.setText("    ���� �̵� �� : ");
		
		for(int i = 0; i<ci.getLimitSearchCount(); i++) {
			lbsSearchCount[i].setText("Ž�� Ƚ�� :          ");
			lbsSearchTime[i].setText("�ð� :             ");
			lbsSearchMoveCount[i].setText("�̵��� :          ");
			lbTotalSearchCount.setText("�� Ž�� Ƚ�� : ");
			lbTotalSearchTime.setText("�� Ž�� �ð� : ");
			lbTotalSearchMoves.setText("�� Ž�� �̵��� : ");
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
			// Todo : mapName�� DB�κ��� �޾ƿͼ� maze�� ����� �� �ֵ��� �Ѵ�.
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
				changeTiles(playThread.getCurr_x(), playThread.getCurr_y());
				
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

				// ȭ�鿡 ����Ѵ�
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
			
			lbChallengeTime.setText("    ���� �ð� : " + (int) ci.getChallengeTime() + " ms");
			lbChallengeMoveCount.setText("    ���� �̵� �� : " + ci.getChallengeMove());

			System.out.println("[Challenge] Moves / Elaspsed Time : " 
					+ ci.getChallengeMove() + " / " + ci.getChallengeTime());

			// �����̸� �α� ����
			if (isSuccess) {
				lbChallengeResult.setText("    ���� ��� : ����");
				// cmLog �ִ� �κ�
				bm.putLog(ci);
			} else {
				lbChallengeResult.setText("    ���� ��� : ����");
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
