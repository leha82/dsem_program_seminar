package maze.challengemode;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import boot.*;
import maze.challengemode.*;

public class ChallengeModeGUI extends JFrame {
	MazeEscapeChallenge mec;
	
	private static int imgSize;
	private static int setX;
	private static int setY;

	private JPanel mainPanel;
	private JPanel mapPanel;
	private JPanel infoPanel;
	private JPanel infoPanel2;
	private JButton btnNext;
	private JButton btnNext10;
	private JButton btnNextAll;
	private JButton btnInit;
	private JButton btnShowRanking;
	private JLabel lbCount;
	private JLabel lbMouseName;
	private JLabel lbFileName;
	private JLabel[][] mapLabels;	
	
	public ChallengeModeGUI() {
	}
	
	public ChallengeModeGUI(MazeEscapeChallenge mec) {
		this.mec = mec;
	}

	
	public void initWindow() {
		// window�� panel�� �ʱ�ȭ �ϴ°��� ã�� �� ��
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
				if (curr_x == j && curr_y == i) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
				} else if (esc_x == j && esc_y == i) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/goal" + imgSize + ".jpg"));
				} else if (map[i][j] == 1) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/wall" + imgSize + ".jpg"));
				} else {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
				}
				mapPanel.add(mapLabels[i][j], gbc);
			}
		}

		btnNext = new JButton("���� �̵�");
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!finished) {
					if (e.getSource() == btnNext) {
						int prev_x = mec.getCurr_x();
						int prev_y = mec.getCurr_y();
						mec.play(1);
						this.setWindow(prev_x, prev_y, mec.getCurr_x(), mec.getCurr_y(), map);
						
						if ((curr_x == this.esc_x) && (curr_y == this.esc_y)) {
							JOptionPane.showMessageDialog(null, "Ż�⿡ �����߽��ϴ�. �� �̵� Ƚ�� : " + count);
							// maze.storeMapToDB(mapName, map);
							// ��ŷ ���ε� �޼ҵ�
							LogManager log = new LogManager();
							int mincount = log.getMinCount(mouseClassName, mapName);
							System.out.println(mincount);

							if (count < mincount || mincount <= 0) {
								mec.putLog();
							}
							finished = true;
						}
					}
						
				}
			}
		});

		btnNext10 = new JButton("���� 10�� �̵�");
		btnNext10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!finished) {
					if (e.getSource() == btnNext10) {
						for (int i=0; i<10; i++) {
							int prev_x = mec.getCurr_x();
							int prev_y = mec.getCurr_y();
							mec.play(1);
							this.setWindow(prev_x, prev_y, mec.getCurr_x(), mec.getCurr_y(), map);
	
							if ((curr_x == this.esc_x) && (curr_y == this.esc_y)) {
								JOptionPane.showMessageDialog(null, "Ż�⿡ �����߽��ϴ�. �� �̵� Ƚ�� : " + count);
								// maze.storeMapToDB(mapName, map);
								// ��ŷ ���ε� �޼ҵ�
								LogManager log = new LogManager();
								int mincount = log.getMinCount(mouseClassName, mapName);
								System.out.println(mincount);
	
								if (count < mincount || mincount <= 0) {
									mec.putLog();
								}
								finished = true;
							}
						}
					}
				}
			}
		});

		btnNextAll = new JButton("������ �̵�");
		btnNextAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!finished) {
					if (e.getSource() == btnNextAll)
						// 10�� �̵� ��ư ����
						play(-1);
				}
			}
		});

		btnInit = new JButton("�ʱ�ȭ");
		btnInit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Todo : �ʱ�ȭ ��ư�� �������� ���� �߰�
				int map[][] = maze.getMap();
				mapPanel.remove(mapLabels[curr_y][curr_x]);
				mapLabels[curr_y][curr_x] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
				gbc.gridx = curr_x;
				gbc.gridy = curr_y;
				mapPanel.add(mapLabels[curr_y][curr_x], gbc);

				initMap();

				mapPanel.remove(mapLabels[curr_y][curr_x]);
				mapLabels[curr_y][curr_x] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
				gbc.gridx = curr_x;
				gbc.gridy = curr_y;
				mapPanel.add(mapLabels[curr_y][curr_x], gbc);

				mapPanel.remove(mapLabels[esc_y][esc_x]);
				mapLabels[esc_y][esc_x] = new JLabel(new ImageIcon("res/goal" + imgSize + ".jpg"));
				gbc.gridx = esc_x;
				gbc.gridy = esc_y;
				mapPanel.add(mapLabels[esc_y][esc_x], gbc);

				lbFileName.setText("�� �̸� : " + mapName + "    ");
				lbMouseName.setText("���콺 �̸� : " + mouseClassName + "    ");
				lbCount.setText("�̵�Ƚ�� : " + count);

				revalidate();
				repaint();
			}
		});

		btnShowRanking = new JButton("��ŷ����");
		btnShowRanking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ShowRanking();
			}
		});

		lbFileName = new JLabel("�� �̸� : " + mapName + "    ");
		lbMouseName = new JLabel("���콺 �̸� : " + mouseClassName + "    ");
		lbCount = new JLabel("�̵�Ƚ�� : " + count);

		infoPanel = new JPanel();
		infoPanel2 = new JPanel();
		infoPanel2.add(lbFileName);
		infoPanel2.add(lbMouseName);
		infoPanel2.add(lbCount);
		infoPanel.add(btnInit);
		infoPanel.add(btnNext);
		infoPanel.add(btnNext10);
		infoPanel.add(btnNextAll);
		infoPanel.add(btnShowRanking);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(mapPanel, "North");
		mainPanel.add(infoPanel2, "Center");
		mainPanel.add(infoPanel, "South");

		add(mainPanel);
		setSize(setX * 60 + 100, setY * 60 + 50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/* item Action Listener */
	class LoadMouseMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			mouseClassName = e.getActionCommand();
			System.out.println("Choice -> " + mouseClassName);

			changeMouseClass(defaultMousePackage + mouseClassName);

			GridBagConstraints gbc = new GridBagConstraints();

			int map[][] = maze.getMap();
			mapPanel.remove(mapLabels[curr_y][curr_x]);
			mapLabels[curr_y][curr_x] = new JLabel(new ImageIcon("res/way" + imgSize + ".jpg"));
			gbc.gridx = curr_x;
			gbc.gridy = curr_y;
			mapPanel.add(mapLabels[curr_y][curr_x], gbc);

			initMap();

			mapPanel.remove(mapLabels[curr_y][curr_x]);
			mapLabels[curr_y][curr_x] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
			gbc.gridx = curr_x;
			gbc.gridy = curr_y;
			mapPanel.add(mapLabels[curr_y][curr_x], gbc);

			mapPanel.remove(mapLabels[esc_y][esc_x]);
			mapLabels[esc_y][esc_x] = new JLabel(new ImageIcon("res/goal" + imgSize + ".jpg"));
			gbc.gridx = esc_x;
			gbc.gridy = esc_y;
			mapPanel.add(mapLabels[esc_y][esc_x], gbc);

			lbFileName.setText("�� �̸� : " + mapName + "    ");
			lbMouseName.setText("���콺 �̸� : " + mouseClassName + "    ");
			lbCount.setText("�̵�Ƚ�� : " + count);

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

				if (curr_x == j && curr_y == i) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
				} else if (esc_x == j && esc_y == i) {
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
		lbFileName.setText("�� �̸� : " + mapName + "    ");
		lbMouseName.setText("���콺 �̸� : " + mouseClassName + "    ");
		lbCount.setText("�̵�Ƚ�� : " + count);
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
			mapName = e.getActionCommand();
			// Todo : mapName�� DB�κ��� �޾ƿͼ� maze�� ����� �� �ֵ��� �Ѵ�.
			loadMap();
			int[][] map = maze.getMap();
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

		mapPanel.remove(mapLabels[curr_y][curr_x]);
		mapLabels[curr_y][curr_x] = new JLabel(new ImageIcon("res/mouse" + imgSize + ".jpg"));
		gbc.gridx = curr_x;
		gbc.gridy = curr_y;
		mapPanel.add(mapLabels[curr_y][curr_x], gbc);

		lbFileName.setText("�� �̸� : " + mapName + "    ");
		lbMouseName.setText("���콺 �̸� : " + mouseClassName + "    ");
		lbCount.setText("�̵�Ƚ�� : " + count);

		revalidate();
		repaint();
	}
	
	class ShowRanking extends JFrame {
		public ShowRanking() {
			LogManager log = new LogManager();
			ArrayList<LogRank> rankList = log.getRankingList(mapName);

			String[] column = { "Rank", "Mouse", "Map", "Record time", "Moves" };
			String[][] row = new String[rankList.size()][5];
			for (int i = 0; i < rankList.size(); i++) {
				LogRank listline = rankList.get(i);
				row[i][0] = Integer.toString(i + 1);
				row[i][1] = listline.getMouse();
				row[i][2] = listline.getMapname();
				row[i][3] = listline.getTimestamp();
				row[i][4] = Integer.toString(listline.getCount());
			}
			setTitle("��ŷ����");
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);

			DefaultTableModel model = new DefaultTableModel(row, column);
			JTable table = new JTable(model);
			table.setRowHeight(25);
			table.getColumnModel().getColumn(0).setPreferredWidth(10);
			table.getColumnModel().getColumn(4).setPreferredWidth(10);
			for (int i = 0; i < column.length; i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(dtcr);
			}

			JScrollPane sc = new JScrollPane(table);
			Container c = getContentPane();
			c.add(sc);
			setSize(700, 600);
			setVisible(true);
		}
	}



}
