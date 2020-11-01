package maze.challengemode;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import boot.*;

public class RankingGUI extends JFrame {
	BaseModule mec;
	
	public RankingGUI(BaseModule mec) {
		this.mec = mec;
	}
	
	public void showRanking() {
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
		setTitle("·©Å·º¸±â");
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);

		DefaultTableModel model = new DefaultTableModel(row, column);
		JTable table = new JTable(model);
		table.setRowHeight(25);
//		table.getColumnModel().getColumn(0).setPreferredWidth(10);
//		table.getColumnModel().getColumn(4).setPreferredWidth(10);
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