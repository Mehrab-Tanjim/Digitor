package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.undo.CannotUndoException;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;


public class Menu {
	
	private JMenuBar Menubar=new JMenuBar();
	private JMenu File=new JMenu("File");
	private JMenu Edit=new JMenu("Edit");
	private JMenu Operations=new JMenu("Operations");
	private JMenu Window=new JMenu("Window");
	private JMenu Show=new JMenu("Show");
	private JMenu Help=new JMenu("Help");
	private JMenuItem newFile=new JMenuItem("New File");
	private JMenuItem Save=new JMenuItem("Save");
	private JMenuItem Load=new JMenuItem("Load");
	private JMenuItem Exit=new JMenuItem("Exit");
	private JMenuItem Undo=new JMenuItem("Undo");
	private JMenuItem Remove=new JMenuItem("Remove");
	private JMenuItem Redo=new JMenuItem("Redo");
	private JMenuItem Copy=new JMenuItem("Copy");
	private JMenuItem Cut=new JMenuItem("Cut");
	private JMenuItem Paste=new JMenuItem("Paste");
	private JMenuItem Group=new JMenuItem("Group");
	private JMenuItem Ungroup=new JMenuItem("Ungroup");
	private JMenuItem ZoomStd=new JMenuItem("Normal");
	private JMenuItem ZoomIn=new JMenuItem("Zoom In");
	private JMenuItem ZoomOut=new JMenuItem("Zoom Out");
	private JMenuItem Simplify=new JMenuItem("Simplify");
	private JMenuItem GenerateFunction=new JMenuItem("Show Functions");
	private JMenuItem TruthTable=new JMenuItem("Truthtable");
	private JMenuItem GateDiagram=new JMenuItem("GateDiagram");
	private JMenuItem UserGuide=new JMenuItem("User Guide");
	private JMenuItem GiveUsFeedBack=new JMenuItem("FeedBack");
	private JCheckBoxMenuItem ShowTable=new JCheckBoxMenuItem("Show Table");
	private JCheckBoxMenuItem Console=new JCheckBoxMenuItem("Console");
	private GraphModel model;
	private GraphLayoutCache view;
	private int i=0;
	
	public Menu(){
		
		Save.setEnabled(false);
		Undo.setEnabled(false);
		Redo.setEnabled(false);
		Remove.setEnabled(false);
		Copy.setEnabled(false);
		Cut.setEnabled(false);
		Paste.setEnabled(false);
		Group.setEnabled(false);
		Ungroup.setEnabled(false);
		
		File.add(newFile);
		File.add(Save);
		File.add(Load);
		File.addSeparator();
		File.add(Exit);		
		File.setMnemonic(KeyEvent.VK_F);
		Exit.setMnemonic(KeyEvent.VK_X);
		Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		Load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.CTRL_MASK));
		Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));
		
		
		Edit.add(Undo);
		Edit.add(Redo);	
		Edit.add(Copy);
		Edit.add(Cut);
		Edit.add(Paste);
		Edit.add(Remove);
		Edit.add(Group);
		Edit.add(Ungroup);
		Edit.add(ZoomStd);
		Edit.add(ZoomIn);
		Edit.add(ZoomOut);
		Edit.setMnemonic(KeyEvent.VK_E);
		Undo.setMnemonic(KeyEvent.VK_U);
		Redo.setMnemonic(KeyEvent.VK_R);
		Remove.setMnemonic(KeyEvent.VK_D);
		Undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
		Redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,ActionEvent.CTRL_MASK));
		Copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		Cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		Paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
		Remove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.CTRL_MASK));
		Group.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.CTRL_MASK));
		Ungroup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,ActionEvent.CTRL_MASK));
		ZoomStd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		ZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS,ActionEvent.CTRL_MASK));
		ZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS,ActionEvent.CTRL_MASK));
		
		
		Simplify.setEnabled(false);
		GenerateFunction.setEnabled(false);	
		TruthTable.setEnabled(false);
		GateDiagram.setEnabled(false);		
		Operations.add(Simplify);
		Operations.add(TruthTable);
		
		ShowTable.setSelected(true);
		Console.setSelected(true);
		Show.add(ShowTable);
		Show.add(Console);
		Window.add(Show);
		Help.add(UserGuide);
		Help.add(GiveUsFeedBack);
		
		Menubar.add(File);
		Menubar.add(Edit);
		Menubar.add(Operations);
		Menubar.add(Window);
		Menubar.add(Help);
		
	}
	
	
	public JMenuItem getSimplify() {
		return Simplify;
	}


	public void setSimplify(JMenuItem simplify) {
		Simplify = simplify;
	}


	public JMenuBar getMenubar() {
		return Menubar;
	}

	
	
	
	/////All action listeners in menu///////////////

	public JMenuItem getGroup() {
		return Group;
	}


	public void setGroup(JMenuItem group) {
		Group = group;
	}


	public JMenuItem getUngroup() {
		return Ungroup;
	}


	public void setUngroup(JMenuItem ungroup) {
		Ungroup = ungroup;
	}


	public JMenuItem getCut() {
		return Cut;
	}


	public JMenuItem getGenerateFunction() {
		return GenerateFunction;
	}

	

	public JMenuItem getUndo() {
		return Undo;
	}


	public void setUndo(JMenuItem undo) {
		Undo = undo;
	}


	public JMenuItem getRedo() {
		return Redo;
	}


	public void setRedo(JMenuItem redo) {
		Redo = redo;
	}
	
	

	public JMenuItem getSave() {
		return Save;
	}
	
	

	public void setSave(JMenuItem save) {
		Save = save;
	}
	

	public JMenuItem getTruthTable() {
		return TruthTable;
	}


	public JCheckBoxMenuItem getConsole() {
		return Console;
	}

	
	
	public JCheckBoxMenuItem getShowTable() {
		return ShowTable;
	}


	public JMenuItem getRemove() {
		return Remove;
	}

	

	public JMenuItem getCopy() {
		return Copy;
	}


	public void actioninMenu(final Mainframe m){
		final JGraph graph=m.getDiagramEditor().getGraph();
		this.Console.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				
				if (Console.isSelected()){
					m.getSplitpane2().setDividerLocation(m.getHeight()-227);					
				}
				else{
					m.getSplitpane2().setDividerLocation(m.getHeight());	
				}
					
			}
		});
		this.ShowTable.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				
				if (ShowTable.isSelected()){
					m.getSplitpane().setDividerLocation(m.getWidth()-200);					
				}
				else{
					m.getSplitpane().setDividerLocation(m.getWidth());	
				}
					
			}
		});
		this.Undo.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				
				if (Undo.isEnabled()){
					try{
						m.getDiagramEditor().undo();
					}
					catch(CannotUndoException e){
						Undo.setEnabled(false);
					}
				}
					
			}
		});
		
		this.Remove.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				
				if (Remove.isEnabled()){
					try{
						m.getDiagramEditor().remove();
					}
					catch(Exception e){
						Remove.setEnabled(false);
					}
				}
					
			}
		});
		this.Copy.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				Action action=javax.swing.TransferHandler.getCopyAction();
				ev = new ActionEvent(graph, ev.getID(), ev.getActionCommand(), ev
						.getModifiers());
				action.actionPerformed(ev);
				Paste.setEnabled(true);
				
			}
		});
		this.Cut.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				Action action=javax.swing.TransferHandler.getCutAction();
				ev = new ActionEvent(graph, ev.getID(), ev.getActionCommand(), ev
						.getModifiers());
				action.actionPerformed(ev);
				Paste.setEnabled(true);
				
			}
		});
		
		this.Paste.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				Action action=javax.swing.TransferHandler.getPasteAction();
				ev = new ActionEvent(graph, ev.getID(), ev.getActionCommand(), ev
						.getModifiers());
				action.actionPerformed(ev);
				Paste.setEnabled(false);
			}
		});
		
		this.Group.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				
						m.getDiagramEditor().group(graph.getSelectionCells());
					
			}
		});
		this.Ungroup.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				
						m.getDiagramEditor().ungroup(graph.getSelectionCells());
			}
		});
		
		this.ZoomStd.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				
				m.getDiagramEditor().getGraph().setScale(1.0);
					
			}
		});
		
		this.ZoomIn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				
				m.getDiagramEditor().getGraph().setScale(1.2 * m.getDiagramEditor().getGraph().getScale());
				
					
			}
		});
		
		this.ZoomOut.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				
				m.getDiagramEditor().getGraph().setScale(m.getDiagramEditor().getGraph().getScale()/1.2);
					
			}
		});
		
		this.Redo.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				
				if (Redo.isEnabled()){
					try{
						m.getDiagramEditor().redo();
					}
					catch(CannotUndoException e){
						Redo.setEnabled(false);
					}
				}
					
			}
		});
		
		
		this.Save.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				try {
					m.getDiagramEditor().saveFile();
				} 
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally{
					if(m.getDiagramEditor().saved)getSave().setEnabled(false);
				}
			}
			
		});
		
		
		this.Load.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				
				if(getSave().isEnabled()){
                	int a=JOptionPane.showConfirmDialog(null, "Do you want to save before loading?", "Confirm ", JOptionPane.YES_NO_CANCEL_OPTION);
					if(a==JOptionPane.YES_OPTION){
						try {
							m.getDiagramEditor().saveFile();
						} catch (IOException ev) {
							// TODO Auto-generated catch block
							ev.printStackTrace();
						}
						finally{
							try {
								m.getDiagramEditor().openFile();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
					}
				
					else if(a==JOptionPane.NO_OPTION){
						try {
							m.getDiagramEditor().openFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						}
					}
				else{
					try {
						m.getDiagramEditor().openFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
			}
			
		});
		this.newFile.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				model=new DefaultGraphModel();
				view=new GraphLayoutCache(model,
						new DefaultCellViewFactory());
				if(getSave().isEnabled()){
                	int a=JOptionPane.showConfirmDialog(null, "Do you want to save?", "Confirm ", JOptionPane.YES_NO_CANCEL_OPTION);
					if(a==JOptionPane.YES_OPTION){
						try {
							m.getDiagramEditor().saveFile();
						} catch (IOException ev) {
							// TODO Auto-generated catch block
							ev.printStackTrace();
						}
						finally{
								getSave().setEnabled(false);
								m.getDiagramEditor().setGraph(new JGraph(model,view));
						}
						
					}
				
					else if(a==JOptionPane.NO_OPTION){
						m.getDiagramEditor().setGraph(new JGraph(model,view));
						}
					}
				else{
					m.getDiagramEditor().setGraph(new JGraph(model,view));
				}
				
				
			}
			
		});
		this.TruthTable.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				
				//if (TruthTable.isEnabled()){
					m.getTruthtablepane().setVisible(true);
					m.getTruthtablepane().setTable(true);
					m.getTruthtablepane().setSimplify(false);
					
				//}
					
			}
		});
		
		this.Simplify.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				
				//if (Simplify.isEnabled()){
					m.getTruthtablepane().setVisible(true);
					m.getTruthtablepane().setSimplify(true);
					m.getTruthtablepane().setTable(false);
					
						
				//}
					
			}
		});
		
	
		
		this.Exit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				if(Save.isEnabled()){
					int a=JOptionPane.showConfirmDialog(m, "Do you want to save before Exit?", "Confirm Exit", JOptionPane.YES_NO_CANCEL_OPTION);
					if(a==JOptionPane.YES_OPTION){
						try {
							m.getDiagramEditor().saveFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(m.getDiagramEditor().saved) System.exit(0);
					}
				
					else if(a==JOptionPane.NO_OPTION){
						System.exit(0);	
						}
					}
				else{
					System.exit(0);
				}
			}
		});
		
		
		this.UserGuide.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				m.getUserguide().setVisible(true);
				
			}
		});
		
		this.GiveUsFeedBack.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ev) {
				m.getFeedback().setVisible(true);
				
			}
		});
	}

}
