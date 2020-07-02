package maze;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import mice.*;

public class MazeEscapeGUI extends JFrame {
	private JPanel mainPanel;
	private JPanel mapPanel;
	private JPanel infoPanel;
	private JButton btnNext;
	private JButton btnNext10;
	private JButton btnNextAll;
	private JButton showranking;
	private JLabel lbCount;
	private JLabel ranking;
	private JLabel[][] mapLabels;
	private String mapfile = "maps/testmap.txt";
	private int count;
	private Maze maze;
	private ArrayList<String> miceList;
	private Mouse mouse;
	private int start_x, start_y;
	private int curr_x, curr_y;
	private int esc_x, esc_y;
	private ArrayList<String> rankList;
	private boolean finished;
	
	public MazeEscapeGUI() {
		super("Maze Escape");
		this.count = 0;
		this.finished = false;
	}

	public void loadMap() {
		this.maze = new Maze(mapfile);
		
		this.start_x = maze.getStart_x();
		this.start_y = maze.getStart_y();
		this.esc_x = maze.getEsc_x();
		this.esc_y = maze.getEsc_y();
		
		this.curr_x = this.start_x;
		this.curr_y = this.start_y;

	}
	
	public void loadMice() {
		miceList = new ArrayList<String>();
		
		// Todo : bin/mice 폴더안에 .class 파일명들을 리스트업하고 클래스명만 miceList로 넣기
		// 패키지명.클래스명으로 list에 넣기 ex: mice.RandomMouse
		
		this.mouse = new RightHandMouse();
	}
	
	public void initWindow() {
		int[][] map = maze.getMap();
		
		
		// Todo: 메뉴 추가 - Load Mouse - miceList를 나열
		// mouse를 선택할 경우 - this.mouse에 해당 클래스 생성 및 지정
		// mouse 선택 후 맵 초기화
		
		
		mapPanel = new JPanel();
		mapPanel.setLayout(new GridBagLayout());

		mapLabels = new JLabel[map.length][];
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		for (int i=0; i<map.length; i++) {
			mapLabels[i] = new JLabel[map[i].length];
			
			for (int j=0; j<map[i].length; j++) {
				gbc.gridx=j;
				gbc.gridy=i;

				if (curr_x == j && curr_y == i) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/mouse.jpg"));
				} else if (esc_x == j && esc_y == i) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/goal.jpg"));
				} else if (map[i][j]==1) {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/wall.jpg"));
				} else {
					mapLabels[i][j] = new JLabel(new ImageIcon("res/way.jpg"));
				}
				mapPanel.add(mapLabels[i][j], gbc);
			}
		}

		btnNext = new JButton("다음 이동");
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!finished) {
					if (e.getSource() == btnNext)
						play(1);
				}
			}
		});
		
		btnNext10 = new JButton("다음 10번 이동");
		btnNext10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!finished) {
					if (e.getSource() == btnNext10)
						play(10);
				}
			}
		});

		btnNextAll = new JButton("끝까지 이동");
		btnNextAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!finished) {
					if (e.getSource() == btnNextAll)
						play(-1);
				}
			}
		});
		// 랭킹보기 버튼 추가
		// 랭킹보기 버튼을 클릭하면 팝업창이 떠서 RankList를 출력한다.
		class showrank extends  JFrame {
			public showrank() {
				LogManager log = new LogManager();
				rankList = log.getRankingList();
				String []column = {"id","mouse_name","timestamp","count"};
				String [][]row = new String[rankList.size()][4];
				for(int i =0;i<rankList.size();i++) {
					String listline = rankList.get(i);
					String[] line = listline.split(",");
					for (int j =0;j <4; j++) {
						row[i][j] = line[j];
					}
				}
				setTitle("랭킹보기");
				DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); 
			    dtcr.setHorizontalAlignment(SwingConstants.CENTER);

				DefaultTableModel model = new DefaultTableModel(row,column);
				JTable table = new JTable(model);
				table.setRowHeight(25);
				table.getColumnModel().getColumn(0).setPreferredWidth(20);
				table.getColumnModel().getColumn(3).setPreferredWidth(20);
				table.getColumnModel().getColumn(0).setCellRenderer(dtcr);
				table.getColumnModel().getColumn(3).setCellRenderer(dtcr);
				JScrollPane sc = new JScrollPane(table);
				Container c = getContentPane();
				String a1 = rankList.get(0);
				ranking = new JLabel(a1);
				c.add(ranking);
				c.add(sc);
				setSize(500, 600);
				setVisible(true);
			}
		}
		
		showranking = new JButton("랭킹보기");
		showranking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new showrank();
			}
		});
		
		lbCount = new JLabel("이동횟수 : " + count);
		infoPanel = new JPanel();
		infoPanel.add(btnNext);
		infoPanel.add(btnNext10);
		infoPanel.add(btnNextAll);
		infoPanel.add(showranking);
		infoPanel.add(lbCount);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(mapPanel,"Center");
		mainPanel.add(infoPanel,"South");
		
		add(mainPanel);
		
		setSize (map[0].length * 60 + 100, map.length * 60 + 50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setWindow(int prev_x, int prev_y, int [][] map) {
		GridBagConstraints gbc = new GridBagConstraints();
		
		mapPanel.remove(mapLabels[prev_y][prev_x]);
		mapLabels[prev_y][prev_x] = new JLabel(new ImageIcon("res/way.jpg"));
		gbc.gridx=prev_x;
		gbc.gridy=prev_y;
		mapPanel.add(mapLabels[prev_y][prev_x], gbc);

		mapPanel.remove(mapLabels[curr_y][curr_x]);
		mapLabels[curr_y][curr_x] = new JLabel(new ImageIcon("res/mouse.jpg"));
		gbc.gridx=curr_x;
		gbc.gridy=curr_y;
		mapPanel.add(mapLabels[curr_y][curr_x], gbc);

		lbCount.setText("이동횟수 : " + count);
		
		revalidate();
		repaint();
	}
	
	public void play(int move) {
		int[][] map = maze.getMap();
		int prev_x = curr_x;
		int prev_y = curr_y;
		
		int i=0;
		while (!finished && (i < move || move == -1)) {
			int dir = mouse.nextMove(curr_x, curr_y, maze.getArea(curr_x, curr_y) );
			
			if (dir==1 && curr_y > 0) {
				if (map[curr_y-1][curr_x]==0)
					curr_y--;	
			} else if (dir==2 && curr_x < maze.getWidth()-1) {
				if (map[curr_y][curr_x+1]==0)
					curr_x++;
			} else if (dir==3 && curr_y < maze.getHeight()-1) {
				if (map[curr_y+1][curr_x]==0)
					curr_y++;	
			} else if (dir==4 && curr_x > 0) {
				if (map[curr_y][curr_x-1]==0)
					curr_x--;
			}
			
			count++;
			this.setWindow(prev_x, prev_y, map); 
			prev_x = curr_x;
			prev_y = curr_y;

			if ((curr_x == this.esc_x) && (curr_y == this.esc_y)) {
				JOptionPane.showMessageDialog(null, "탈출에 성공했습니다. 총 이동 횟수 : " + count);
				
			// 랭킹 업로드 메소트
			LogManager log = new LogManager();	
			if (count < log.getminCount("f")) {
				
			}
				
			finished = true;
			}
			
			i++;
		}

	}
	

	public static void main(String[] args) {
		MazeEscapeGUI me = new MazeEscapeGUI();
		
		me.loadMap();
		me.loadMice();
		me.initWindow();
	}
}
