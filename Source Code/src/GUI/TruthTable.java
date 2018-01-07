package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

public class TruthTable extends JPanel {
	private JTable table=new JTable();
	public TruthTable(){
		Dimension dim=getPreferredSize();
		dim.width=200-29;
		setMinimumSize(dim);
		Border outerborder= BorderFactory.createTitledBorder("Truth Table");
		Border innerborder= BorderFactory.createEmptyBorder(0,0,10,10);		
		setBorder(BorderFactory.createCompoundBorder(innerborder,outerborder));
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(table),BorderLayout.CENTER);
	}
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}
	
}
