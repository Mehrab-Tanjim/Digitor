package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import org.jgraph.graph.DefaultGraphCell;


public class Mainframe extends JFrame {

	private ToolBar toolbar = new ToolBar();
	private DiagramEditor diagramEditor = new DiagramEditor();
	private TruthTable truthTable = new TruthTable();
	private JScrollPane scrollpane = new JScrollPane(diagramEditor.getGraph());
	private JSplitPane splitpane;
	private JSplitPane splitpane2;
	private JTabbedPane tabPane = new JTabbedPane();
	private Console console = new Console();
	private FeedBack feedback=new FeedBack(this);
	private TruthTablePane truthtablepane=new TruthTablePane(this);
	private UserGuide userguide=new UserGuide(this);
	private Menu menu;

	// private Controller controller=new Controller();
	public Mainframe() {
		super("Digital Logic Simulator");

		// ////just creating the main window///////////

		setMinimumSize(new Dimension(400, 300));
		setLayout(new BorderLayout());
		setSize(1200, 800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Image image = Toolkit.getDefaultToolkit().createImage(
				getClass().getResource("/Images/Digitor.png"));
		// If Valid URL
		if (image != null) {
			// Load Icon
			//ImageIcon jgraphIcon =image;// new ImageIcon(jgraphUrl);
			// Use in Window
			setIconImage(image);
		}
		else{
			System.out.println("LOL");
		}
		
		addWindowListener(new WindowAdapter() {
			
            public void windowClosing(WindowEvent ev) {
                if(menu.getSave().isEnabled()){
                	int a=JOptionPane.showConfirmDialog(null, "Do you want to save before Exit?", "Confirm Exit", JOptionPane.YES_NO_CANCEL_OPTION);
					if(a==JOptionPane.YES_OPTION){
						try {
							getDiagramEditor().saveFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						finally{
							if(getDiagramEditor().saved) System.exit(0);
						}
						
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
		
		getContentPane().addHierarchyBoundsListener(new HierarchyBoundsListener(){
			 
            
            public void ancestorResized(HierarchyEvent e) {
            	if(e!=null){
	                if(menu.getConsole().isSelected()){//
	                	
	                	splitpane2.setDividerLocation(getHeight()-227);
	                }
	                else{
	                	splitpane2.setDividerLocation(getHeight());
	                }
	                if(menu.getShowTable().isSelected()){
	                	splitpane.setDividerLocation(getWidth()-200);
	                }
	                else{
	                	splitpane.setDividerLocation(getWidth());
	                }
            	}  
            }

			@Override
			public void ancestorMoved(HierarchyEvent arg0) {
				// TODO Auto-generated method stub
				
			}           
        });
		
		setLocationRelativeTo(null);
		////////main window creation finished/////////////

		//////setting up all other components for the frame///////////
		Border outerborder = BorderFactory.createTitledBorder("Diagram Editor");		
		Border innerborder = BorderFactory.createEmptyBorder(0, 0, 10, 10);
		scrollpane.setBorder(BorderFactory.createCompoundBorder(innerborder,
				outerborder));
		
		
		truthtablepane.setTruthTable(truthTable);
		truthtablepane.addTruthTablePaneListener(new TruthTablePaneListener(){

			@Override
			public void Functions(List<DefaultGraphCell> e) {
				if(e!=null)
					{
						menu.getTruthTable().setEnabled(true);
						menu.getSimplify().setEnabled(true);
					}
								
			}
			
		});
		
		
		
		diagramEditor.addDiagramListener(new DiagramListener(){

			@Override
			public void DiagramChanged(DiagramEvent e) {
				if(e.isRedo())
					menu.getRedo().setEnabled(true);
				else
					menu.getRedo().setEnabled(false);
				if(e.isUndo())
					menu.getUndo().setEnabled(true);
				else
					menu.getUndo().setEnabled(false);
				if(e.isSave())
					menu.getSave().setEnabled(true);	
				if(e.isRemove())
					menu.getRemove().setEnabled(true);
				else
					menu.getRemove().setEnabled(false);
				if(e.isCopy())
					menu.getCopy().setEnabled(true);
				else
					menu.getCopy().setEnabled(false);
				if(e.isCut())
					menu.getCut().setEnabled(true);
				else
					menu.getCut().setEnabled(false);
				if(e.isGroup())
					menu.getGroup().setEnabled(true);
				else
					menu.getGroup().setEnabled(false);
				if(e.isUngroup())
					menu.getUngroup().setEnabled(true);
				else
					menu.getUngroup().setEnabled(false);
					
			}
			
		});
		
		
		tabPane.add("Console", console);
		diagramEditor.get_connection().getUpdate().setConsole(console);
		diagramEditor.get_connection().getUpdate().setTruthtablepane(truthtablepane);
		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollpane,
				truthTable);
		splitpane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitpane,
				tabPane);
		splitpane.setDividerLocation(getWidth()-200);
		splitpane2.setDividerLocation(getHeight()-227);
		
		////////Passing the model of the graph to controller whose main job is
		// to set all necessary things ///////

		// controller.setModel(diagramEditor.getModel());

		/////adding menubar & its actionlistener/////////
		menu = new Menu();
		setJMenuBar(menu.getMenubar());
		menu.actioninMenu(this);

		////adding all others thigns////
		add(toolbar, BorderLayout.PAGE_START);
		
		toolbar.getSelected(new ToolBarListener() {
			public void GateSelected(String gATE) {
				diagramEditor.setGate(gATE);
			}
		});
		splitpane.setOneTouchExpandable(true);
		splitpane2.setOneTouchExpandable(true);
		add(splitpane2, BorderLayout.CENTER);

	}

	

	public Console getConsole() {
		return console;
	}

	public DiagramEditor getDiagramEditor() {
		return diagramEditor;
	}
	
	public FeedBack getFeedback() {
		return feedback;
	}

	public JSplitPane getSplitpane() {
		return splitpane;
	}

	public JSplitPane getSplitpane2() {
		return splitpane2;
	}

	public TruthTablePane getTruthtablepane() {
		return truthtablepane;
	}



	public Menu getMenu() {
		return menu;
	}



	public UserGuide getUserguide() {
		return userguide;
	}
	
	

	
}
