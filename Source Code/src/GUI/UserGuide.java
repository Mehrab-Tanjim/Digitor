package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class UserGuide extends JDialog {
	public UserGuide(final Mainframe frame){
		super(frame, "User's Guide", false);
		setSize(900, 600);
		setLocationRelativeTo(frame);
		setLayout(new GridBagLayout());
		setResizable(true);
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = .01;
		gc.fill = GridBagConstraints.HORIZONTAL;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 50, 0, 50);
		add(new JLabel("1. Click on any gate in the toolbar & move your cursor in the Diagram Editor. You'll see your selected gate as cursor.")
				,gc);

		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 50, 0, 50);
		add(new JLabel("Click again in suitable spot to place the gate"), gc);
		// ///////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 50, 0, 0);
		add(new JLabel("2.Move your cursor near the input or output of any gate in editor. You'll notice a hand cursor wll appear."
				+ " Now press & drag to connect."), gc);
		
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 50, 0, 0);
		add(new JLabel("3.You can click on the input gates.In one click it will switch on & will switch off in another."
				+ " You can see corresponding wires changing their color"
				), gc);
						
		gc.gridy++;
		gc.gridx = 0;
		gc.weighty=.01;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 50, 0, 0);
		add(new JLabel("(red for 1 & blue for 0, if there is no effect of the input on the wire the wire "
								+ "will simply be black)."), gc);						
					
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 50, 0, 0);
		add(new JLabel("4.You can also double click on any gate or wire to bring up the in place editor."
				+ "You can name your gate or wire anything you like."
				), gc);
		
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 50, 0, 0);
		add(new JLabel("5.To select multiple gates use your right mouse button. You can also hold shift key & click on one gate at a time."
					), gc);
		
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 50, 0, 0);
		add(new JLabel("6.You can make a group with multiple gates. To do this, select the gates you want to group & press ctrl+g or you can click group"
				+ " in the edit menu."
					), gc);
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 50, 0, 0);
		add(new JLabel("To ungroup simply press ctrl+u or click on ungroup in the edit menu"
					), gc);
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 50, 0, 0);
		add(new JLabel("7.You can drag(by holding the shaded area) the toolbar out of main window & place it anywhere you like.")
				,gc);
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 50, 0, 0);
		add(new JLabel("To put it back on just drag it on the main window & a shape will appear showing that toolbar can be attached there."
				), gc);
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 50, 0, 0);
		add(new JLabel("For any kind of help or sharing your experience dont hesitate to give us feedback"
				), gc);
		
	}
}
