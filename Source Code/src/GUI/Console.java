package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Console extends JPanel {
	private JTextArea textarea=new JTextArea();
		
	public Console(){
		//Dimension dim=new Dimension();
		
		//setMinimumSize(dim);
		setLayout(new BorderLayout());
		add(new JScrollPane(textarea),BorderLayout.CENTER);
		
	}
	public JTextArea getTextarea() {
		return textarea;
	}
	public void Append(String string){
		textarea.append(string);		
	}
	
}
