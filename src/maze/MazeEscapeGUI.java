package maze;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import mice.*;

public class MazeEscapeGUI extends JFrame {
   private static String appTitle = "Maze Escape";
//   private static String defaultMapFile = "maps/testmap2.txt";
   private static String defaultMap = "testmap2";
   private static String defaultMouseDirectory = "bin/mice";
   private static String defaultMousePackage = "mice.";
   private static String defaultMouse = "RightHandMouse";
   private static int imgSize = 10;
   private static int setX = 10;
   private static int setY = 10;
   
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
   private JLabel lbRanking;
   private JLabel[][] mapLabels;
   private String mouseClassName;
   private String mapName;
   private int count;
   private Maze maze;
   private ArrayList<String> miceList;
   private ArrayList<String> mapList;
   private Mouse mouse;
   private int start_x, start_y;
   private int curr_x, curr_y;
   private int esc_x, esc_y;

   private boolean finished;

   public MazeEscapeGUI() {
      super(appTitle);
      this.mouseClassName = defaultMouse;
      this.mapName = defaultMap;
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

   public void initWindow() {
      // window나 panel을 초기화 하는것을 찾아 볼 것
//      maze.loadMapFromDB(mapName);
      int[][] map = maze.getMap();
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

            if (curr_x == j && curr_y == i) {
               mapLabels[i][j] = new JLabel(new ImageIcon("res/mouse"+imgSize+".jpg"));
            } else if (esc_x == j && esc_y == i) {
               mapLabels[i][j] = new JLabel(new ImageIcon("res/goal"+imgSize+".jpg"));
            } else if (map[i][j] == 1) {
               mapLabels[i][j] = new JLabel(new ImageIcon("res/wall"+imgSize+".jpg"));
            } else {
               mapLabels[i][j] = new JLabel(new ImageIcon("res/way"+imgSize+".jpg"));
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
      
      btnInit = new JButton("초기화");
      btnInit.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // Todo : 초기화 버튼을 눌렀을때 동작 추가
           int map[][] = maze.getMap();
            mapPanel.remove(mapLabels[curr_y][curr_x]);
            mapLabels[curr_y][curr_x] = new JLabel(new ImageIcon("res/way"+ imgSize +".jpg"));
            gbc.gridx = curr_x;
            gbc.gridy = curr_y;
            mapPanel.add(mapLabels[curr_y][curr_x], gbc);
            
            initMap();

            mapPanel.remove(mapLabels[curr_y][curr_x]);
            mapLabels[curr_y][curr_x] = new JLabel(new ImageIcon("res/mouse"+imgSize+".jpg"));
            gbc.gridx = curr_x;
            gbc.gridy = curr_y;
            mapPanel.add(mapLabels[curr_y][curr_x], gbc);

            mapPanel.remove(mapLabels[esc_y][esc_x]);
            mapLabels[esc_y][esc_x] = new JLabel(new ImageIcon("res/goal"+imgSize+".jpg"));
            gbc.gridx = esc_x;
            gbc.gridy = esc_y;
            mapPanel.add(mapLabels[esc_y][esc_x], gbc);
   
            lbFileName.setText("맵 이름 : " + mapName + "    ");
            lbMouseName.setText("마우스 이름 : " + mouseClassName + "    ");
            lbCount.setText("이동횟수 : " + count);
            
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

      lbFileName = new JLabel("맵 이름 : " + mapName + "    ");
      lbMouseName = new JLabel("마우스 이름 : " + mouseClassName + "    ");
      lbCount = new JLabel("이동횟수 : " + count);

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
         mapLabels[curr_y][curr_x] = new JLabel(new ImageIcon("res/way"+ imgSize +".jpg"));
         gbc.gridx = curr_x;
         gbc.gridy = curr_y;
         mapPanel.add(mapLabels[curr_y][curr_x], gbc);

         initMap();

         mapPanel.remove(mapLabels[curr_y][curr_x]);
         mapLabels[curr_y][curr_x] = new JLabel(new ImageIcon("res/mouse"+imgSize+".jpg"));
         gbc.gridx = curr_x;
         gbc.gridy = curr_y;
         mapPanel.add(mapLabels[curr_y][curr_x], gbc);

         mapPanel.remove(mapLabels[esc_y][esc_x]);
         mapLabels[esc_y][esc_x] = new JLabel(new ImageIcon("res/goal"+imgSize+".jpg"));
         gbc.gridx = esc_x;
         gbc.gridy = esc_y;
         mapPanel.add(mapLabels[esc_y][esc_x], gbc);
         
         lbFileName.setText("맵 이름 : " + mapName + "    ");
         lbMouseName.setText("마우스 이름 : " + mouseClassName + "    ");
         lbCount.setText("이동횟수 : " + count);
         
         revalidate();
         repaint();
//         initWindow();
      }
   }
   public  void paintMap(int map[][]) {
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
                mapLabels[i][j] = new JLabel(new ImageIcon("res/mouse"+imgSize+".jpg"));
             } else if (esc_x == j && esc_y == i) {
                mapLabels[i][j] = new JLabel(new ImageIcon("res/goal"+imgSize+".jpg"));
             } else if (map[i][j] == 1) {
                mapLabels[i][j] = new JLabel(new ImageIcon("res/wall"+imgSize+".jpg"));
             } else {
                mapLabels[i][j] = new JLabel(new ImageIcon("res/way"+imgSize+".jpg"));
             }
             mapPanel.add(mapLabels[i][j], gbc);
          }
       }
      
      mainPanel.add(mapPanel, "North");
      setSize(setX * 60 + 100, setY * 60 + 50);
      lbFileName.setText("맵 이름 : " + mapName + "    ");
      lbMouseName.setText("마우스 이름 : " + mouseClassName + "    ");
      lbCount.setText("이동횟수 : " + count);
   }
   
   class LoadMapMenuActionListener implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
         mapName = e.getActionCommand();
         // Todo : mapName을 DB로부터 받아와서 maze에 저장될 수 있도록 한다.
         loadMap();
         int[][] map = maze.getMap();
         
         if(map.length <= 10 ) {
        	 imgSize = 10;
        	 setX = 10;
        	 setY = 10;
         }else if(map.length <= 50 ) {
        	 imgSize = 50;
        	 setX = 15;
        	 setY = 15;		 
         }else {
        	 imgSize = 100;
        	 setX = 15;
        	 setY = 10;		
         }
         
         mainPanel.remove(mapPanel);
         paintMap(map);
         mainPanel.revalidate();
      }
   }

   public void setWindow(int prev_x, int prev_y, int[][] map) {
      GridBagConstraints gbc = new GridBagConstraints();

      mapPanel.remove(mapLabels[prev_y][prev_x]);
      mapLabels[prev_y][prev_x] = new JLabel(new ImageIcon("res/way"+ imgSize+ ".jpg"));
      gbc.gridx = prev_x;
      gbc.gridy = prev_y;
      mapPanel.add(mapLabels[prev_y][prev_x], gbc);

      mapPanel.remove(mapLabels[curr_y][curr_x]);
      mapLabels[curr_y][curr_x] = new JLabel(new ImageIcon("res/mouse"+ imgSize +".jpg"));
      gbc.gridx = curr_x;
      gbc.gridy = curr_y;
      mapPanel.add(mapLabels[curr_y][curr_x], gbc);

      lbFileName.setText("맵 이름 : " + mapName + "    ");
      lbMouseName.setText("마우스 이름 : " + mouseClassName + "    ");
      lbCount.setText("이동횟수 : " + count);
      
      revalidate();
      repaint();
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
         this.setWindow(prev_x, prev_y, map);
         prev_x = curr_x;
         prev_y = curr_y;

         if ((curr_x == this.esc_x) && (curr_y == this.esc_y)) {
            JOptionPane.showMessageDialog(null, "탈출에 성공했습니다. 총 이동 횟수 : " + count);
            //maze.storeMapToDB(mapName, map);
            // 랭킹 업로드 메소드
            LogManager log = new LogManager();
            int mincount = log.getMinCount(mouseClassName, mapName);
            System.out.println(mincount);

            if (count < mincount || mincount <= 0) {
               System.out.println("putlog:" + mouseClassName + " / " + mapName + " / " + count);
               ArrayList<LogRank> rankList = log.getRankingList(mapName);
               
               for(int k=0;k<rankList.size();k++) {
            	   LogRank lr = rankList.get(k);
            	   if(lr.getMouse().contains(mouseClassName)){
            		   log.deleteLog(lr.getId());
            	   }
               }

               log.putLog(mouseClassName, mapName, count);
            }
            finished = true;
            
         }
         i++;
      }

   }

   class ShowRanking extends JFrame {
      public ShowRanking() {
         LogManager log = new LogManager();
         ArrayList<LogRank> rankList = log.getRankingList(mapName);

         String[] column = { "Rank", "Mouse", "Map", "Record time", "Moves" };
         String[][] row = new String[rankList.size()][5];
         for (int i = 0; i < rankList.size(); i++) {
            LogRank listline = rankList.get(i);
            row[i][0] = Integer.toString(i+1);
            row[i][1] = listline.getMouse();
            row[i][2] = listline.getMapname();
            row[i][3] = listline.getTimestamp();
            row[i][4] = Integer.toString(listline.getCount());
         }
         setTitle("랭킹보기");
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

   public static void main(String[] args) {
      MazeEscapeGUI me = new MazeEscapeGUI();
      me.loadMap();
      me.loadMiceList();
      me.loadMapList();
      me.initWindow();
   }

}