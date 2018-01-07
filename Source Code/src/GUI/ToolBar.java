package GUI;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;


public class ToolBar extends JToolBar implements ActionListener {
	
	private JButton AND;
	private JButton OR;
	private JButton NOT;
	private JButton XOR;
	private JButton NAND;
	private JButton NOR;
	private JButton INPUT;
	private JButton OUTPUT;
	private String GATE;
	private ToolBarListener toolBarListener;
	
	public ToolBar(){
		
		//setBorder(BorderFactory.createEtchedBorder());
		setFloatable(true);
		
		AND=new JButton(createIcon("/Images/AND.png"));
		AND.setToolTipText("AND Gate");
		AND.setRolloverEnabled(false);
		AND.setMargin(new Insets(-2,-1,-1,-2));
		
//		
		OR=new JButton(createIcon("/Images/OR.png"));
		OR.setToolTipText("OR Gate");
		OR.setRolloverEnabled(false);
		OR.setMargin(new Insets(-2,-1,-1,-2));
		
		NOT=new JButton(createIcon("/Images/NOT.png"));
		NOT.setToolTipText("NOT Gate");
		NOT.setRolloverEnabled(false);
		NOT.setMargin(new Insets(-2,-1,-1,-2));
		
		XOR=new JButton(createIcon("/Images/XOR.png"));
		XOR.setToolTipText("XOR Gate");
		XOR.setRolloverEnabled(false);
		XOR.setMargin(new Insets(-2,-1,-1,-2));
				
		NAND=new JButton(createIcon("/Images/NAND.png"));
		NAND.setToolTipText("NAND Gate");
		NAND.setRolloverEnabled(false);
		NAND.setMargin(new Insets(-2,-1,-1,-2));
		
		
		NOR=new JButton(createIcon("/Images/NOR.png"));
		NOR.setToolTipText("NOR Gate");
		NOR.setRolloverEnabled(false);
		NOR.setMargin(new Insets(-2,-1,-1,-2));
		
		INPUT =new JButton(createIcon("/Images/INPUT.png"));
		INPUT.setToolTipText("INPUT");
		INPUT.setRolloverEnabled(false);
		INPUT.setMargin(new Insets(-2,-1,-1,-2));
		
		OUTPUT =new JButton(createIcon("/Images/OUTPUT.png"));
		OUTPUT.setToolTipText("OUTPUT");
		OUTPUT.setRolloverEnabled(false);
		OUTPUT.setMargin(new Insets(-2,-1,-1,-2));
		
		add(AND);
 		add(OR);
		add(NOT);
		addSeparator();
		add(XOR);
		add(NAND);
		add(NOR);
		addSeparator();
		add(INPUT);
		add(OUTPUT);
		

		AND.addActionListener(this);
		OR.addActionListener(this);
		NOT.addActionListener(this);
		XOR.addActionListener(this);
		NAND.addActionListener(this);
		NOR.addActionListener(this);
		INPUT.addActionListener(this);
		OUTPUT.addActionListener(this);
		
	}
	
	public ImageIcon createIcon(String path){
		
		URL url=getClass().getResource(path);
		
		if(url==null) 
		{
			//////here i will put excepton handling code later//////////
		}
		ImageIcon imageicon=new ImageIcon(url);
		
		return imageicon;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		JButton clicked=(JButton)e.getSource();
		
		if(clicked==AND){
			if(toolBarListener!=null) toolBarListener.GateSelected("AND");
		}
		else if(clicked==OR){
			if(toolBarListener!=null) toolBarListener.GateSelected("OR");
		}
		else if(clicked==NOT){
			if(toolBarListener!=null) toolBarListener.GateSelected("NOT");
		}
		else if(clicked==XOR){
			if(toolBarListener!=null) toolBarListener.GateSelected("XOR");	
		}
		else if(clicked==NAND){
			if(toolBarListener!=null) toolBarListener.GateSelected("NAND");		
		}
		else if(clicked==NOR){
			if(toolBarListener!=null) toolBarListener.GateSelected("NOR");	
		}
		else if(clicked==INPUT){
			if(toolBarListener!=null) toolBarListener.GateSelected("INPUT");	
		}
		else if(clicked==OUTPUT){
			if(toolBarListener!=null) toolBarListener.GateSelected("OUTPUT");	
		}
	}

	public String getGATE() {
		return GATE;
	}

	public void setGATE(String gATE) {
		GATE = gATE;
	}

	public void getSelected(ToolBarListener toolBarListener) {
		this.toolBarListener=toolBarListener;
	}
	
	
}
