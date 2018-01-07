package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JViewport;
import javax.swing.event.UndoableEditEvent;
import javax.swing.filechooser.FileFilter;

import org.jgraph.JGraph;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.GraphUndoManager;

import Gate.GateObj;
import LogicEngine.GateTraverse;
import Wire.Connection;

public class DiagramEditor implements GraphSelectionListener, KeyListener {

	// Jgraph instance
	public GraphModel model = new DefaultGraphModel();
	public GraphLayoutCache view = new GraphLayoutCache(model,
			new DefaultCellViewFactory());
	public JGraph graph = new JGraph(model, view);
	// Undomanager
	private GraphUndoManager undoManager;

	// Actions which Change State
	private Action undo, redo, remove, group, ungroup, tofront, toback, cut,
			copy, paste;

	// Gates
	private String gAte;
	private boolean resetCursor = false;
	private Connection _connection = new Connection();
	private GateTraverse gatetraverse = new GateTraverse();
	private char input = 'A';
	private int i = 0;
	private int j = 0;
	private DefaultGraphCell source = null;
	private GateObj another0 = null;
	private JFileChooser fileChooser = null;
	private Reset reset = new Reset();
	private MouseAction mouseaction = new MouseAction();
	private DiagramListener diagramlistener;
	public boolean saved;
	
	//TODO
	///list of outputs
	// TODO  
	
	public Connection get_connection() {
		return _connection;
	}

	public void set_connection(Connection _connection) {
		this._connection = _connection;
	}
	
	
	

	public DiagramEditor() {

		graph.setGridEnabled(true);
		graph.setGridVisible(true);
	//	graph.setMoveBeyondGraphBounds(true);
//		graph.set

		undoManager = new GraphUndoManager() {
			// Override Superclass
			public void undoableEditHappened(UndoableEditEvent e) {
				// First Invoke Superclass
				super.undoableEditHappened(e);
				// Then Update Undo/Redo Buttons
				updateMenuHistoryButton();
			}
		};

		// /setting up the edge connection & model handling classes////////////
		_connection.setGraph(graph);
		gatetraverse=_connection.getUpdate();

		// /////graph listener for this editor///////////
		graph.setMarqueeHandler(_connection);

		installListeners(graph);

	}

	public DefaultGraphCell getSourcePortAt(Point2D point) {

		DefaultGraphCell result = (DefaultGraphCell) graph
				.getFirstCellForLocation(point.getX(), point.getY());
		return result;
	}

	// /////inserting new nodes/////////////
	public void insert(String Gate, int x, int y) {
		// ////what gate to insert////////
		GateObj gate = new GateObj(Gate);// /user object
		DefaultGraphCell cell = new DefaultGraphCell(gate);
		ImageIcon icon = new ImageIcon(JGraph.class.getResource("/Images/"
				+ Gate + ".png"));
		GraphConstants.setIcon(cell.getAttributes(), icon);
		GraphConstants.setGradientColor(cell.getAttributes(), Color.WHITE);
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(
				x, y, 0, 0));
		GraphConstants.setOpaque(cell.getAttributes(), false);
		GraphConstants.setSizeableAxis(cell.getAttributes(), 1);
		GraphConstants.setAutoSize(cell.getAttributes(), true);
		GraphConstants.setLabelEnabled(cell.getAttributes(), false);
		// GraphConstants.setEditable(cell.getAttributes(),false);

		if (Gate.equals("NOT")) {

			DefaultPort port0 = new DefaultPort();
			// Point2D point=new Point2D.Double(GraphConstants.PERMILLE,1000/2);
			Point2D point = new Point2D.Double(50, 15);
			GraphConstants.setAbsolute(port0.getAttributes(), true);
			GraphConstants.setOffset(port0.getAttributes(), point);

			DefaultPort port1 = new DefaultPort();
			Point2D point1 = new Point2D.Double(0, 15);
			GraphConstants.setAbsolute(port1.getAttributes(), true);
			GraphConstants.setOffset(port1.getAttributes(), point1);

			cell.add(port0);
			cell.add(port1);
		} 
		
		else if (Gate.equals("INPUT")) {

			DefaultPort port0 = new DefaultPort();
			Point2D point = new Point2D.Double(20, 10);
			GraphConstants.setBorderColor(cell.getAttributes(), Color.blue);
			GraphConstants.setAbsolute(port0.getAttributes(), true);
			GraphConstants.setOffset(port0.getAttributes(), point);
			gate.setInput1("" + input++);
			gate.setINPUT1(false);
			cell.add(port0);
		}

		else if (Gate.equals("OUTPUT")) {
			DefaultPort port0 = new DefaultPort();
			Point2D point = new Point2D.Double(0, 10);
			GraphConstants.setAbsolute(port0.getAttributes(), true);
			GraphConstants.setOffset(port0.getAttributes(), point);
			gate.setInput1("F" + i++);
			cell.add(port0);
			//TODO
			
			
		}

		else {
			DefaultPort port0 = new DefaultPort();
			Point2D point;
			if (Gate.equals("NAND")||Gate.equals("NOR")||Gate.equals("XOR")){
				point = new Point2D.Double(60, 20);
			}
			else {
				point = new Point2D.Double(50, 20);
			}
			GraphConstants.setAbsolute(port0.getAttributes(), true);
			GraphConstants.setOffset(port0.getAttributes(), point);

			DefaultPort port1 = new DefaultPort();
			Point2D point1 = new Point2D.Double(0, 10);
			GraphConstants.setAbsolute(port1.getAttributes(), true);
			GraphConstants.setOffset(port1.getAttributes(), point1);

			DefaultPort port2 = new DefaultPort();
			Point2D point2 = new Point2D.Double(0, 30);
			GraphConstants.setAbsolute(port2.getAttributes(), true);
			GraphConstants.setOffset(port2.getAttributes(), point2);

			cell.add(port0);
			cell.add(port1);
			cell.add(port2);
		}

		graph.getGraphLayoutCache().insert(cell);
		graph.setCursor(null);

	}

	public JGraph getGraph() {
		return graph;
	}
	
	

	public void setGraph(JGraph graph) {
		Container parent = this.graph.getParent();
		//Connection connection = (Connection)graph.getMarqueeHandler();
		try {
			uninstallListeners(this.graph);
			parent.remove(this.graph);
			this.graph = graph;
			_connection.setGraph(this.graph);
			this.graph.setMarqueeHandler(_connection);
			this.graph.setGridEnabled(true);
			this.graph.setGridVisible(true);
			diagramlistener.DiagramChanged(new DiagramEvent(false,false));
			if (parent instanceof JViewport) {
				JViewport viewPort = (JViewport) parent;
				viewPort.setView(this.graph);
			} else {
				parent.add(this.graph);
			}
			installListeners(this.graph);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.graph, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// ////taking input from toolbar what gate to insert & setting up the
	// cursor////////////
	public void setGate(String gATE) {
		this.gAte = gATE;

		Image image = Toolkit.getDefaultToolkit().createImage(
				getClass().getResource("/Images/" + gATE + "1.png"));

		Cursor gateCursor = graph.getToolkit().createCustomCursor(image,
				new Point(0, 0), "");
		graph.setCursor(gateCursor);
		resetCursor = true;
	}

	public ImageIcon createIcon(String path) {

		URL url = getClass().getResource(path);

		if (url == null) {
			// ////here i will put excepton handling code later//////////
		}
		ImageIcon imageicon = new ImageIcon(url);

		return imageicon;
	}

	public class Reset implements GraphModelListener {

		public void graphChanged(GraphModelEvent e) {
			
			if (e != null) {
				graph.refresh();				
				if (diagramlistener != null)
					diagramlistener.DiagramChanged(new DiagramEvent(true));
			}
			if (another0 != null) {
				if (source.getUserObject().toString() != null
						& another0.toString() != source.getUserObject()
								.toString()) {
					another0.setLabel(source.getUserObject().toString());
					source.setUserObject(another0);
					if (another0.getGate().equals("INPUT")||another0.getGate().equals("OUTPUT")) {
						another0.setInput1(another0.getLabel());
//TODO
						Iterator iter = model.edges((DefaultPort) source
								.getChildAt(0));
						while (iter.hasNext()) {
							DefaultEdge edge = (DefaultEdge) iter.next();
							
							_connection.getUpdate().update(
									(DefaultPort) edge.getSource(),
									(DefaultPort) edge.getTarget());
						}

					}
					
					another0 = null;
				}

			}
		}

	}
	public class MouseAction extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			if (gAte == null) {
				// gate is equal to null so we are working on existing gates on
				// diagram

				source = getSourcePortAt(e.getPoint());// getting the source
														// cell
				GateObj another = null;
				if (source != null)
					another = (GateObj) source.getUserObject();// and saving its
																// gateobj
				another0 = another;
				if (another != null && another.getGate().equals("INPUT")) {

					Map map = new Hashtable();
					Map map2 = new Hashtable();

					if (another.isOUTPUT()) {
						GraphConstants.setBorderColor(map2, Color.blue);
						
						map.put(source, map2);
						model.edit(map, null, null, null);
						graph.refresh();
						another.setOUTPUT(false);
						another.setINPUT1(false);

						Iterator iter = model.edges((DefaultPort) source
								.getChildAt(0));
						while (iter.hasNext()) {
							DefaultEdge edge = (DefaultEdge) iter.next();
							_connection.getUpdate().update(edge);
						}

					} else {
						GraphConstants.setBorderColor(map2, Color.red);
						map.put(source, map2);
						model.edit(map, null, null, null);
						graph.refresh();
						another.setOUTPUT(true);
						another.setINPUT1(true);
						Iterator iter = model.edges((DefaultPort) source
								.getChildAt(0));
						while (iter.hasNext()) {
							DefaultEdge edge = (DefaultEdge) iter.next();
							_connection.getUpdate().update(edge);
						}
					}
				}

			} else {
				insert(gAte, e.getX(), e.getY());

				graph.setCursor(null);
				gAte = null;
			}	
		}
	};
	
	
	
	
	public void installListeners(JGraph graph) {
		graph.getModel().addGraphModelListener(reset);
		graph.getModel().addUndoableEditListener(undoManager);
		graph.getSelectionModel().addGraphSelectionListener(this);
		graph.addKeyListener(this);
		graph.addMouseListener(mouseaction);
	}

	public void uninstallListeners(JGraph graph) {
		graph.getModel().removeGraphModelListener(reset);
		graph.getModel().removeUndoableEditListener(undoManager);
		graph.getSelectionModel().removeGraphSelectionListener(this);
		graph.removeKeyListener(this);
		graph.removeMouseListener(mouseaction);
	}

	public void group(Object[] cells) {
		// Order Cells by Model Layering
		cells = graph.order(cells);
		// If Any Cells in View
		if (cells != null && cells.length > 0) {
			DefaultGraphCell group = new DefaultGraphCell();
			// Insert into model
			graph.getGraphLayoutCache().insertGroup(group, cells);
		}
	}

	public void ungroup(Object[] cells) {
		graph.getGraphLayoutCache().ungroup(cells);
	}

	// Determines if a Cell is a Group
	public boolean isGroup(Object cell) {
		// Map the Cell to its View
		CellView view = graph.getGraphLayoutCache().getMapping(cell, false);
		if (view != null)
			return !view.isLeaf();
		return false;
	}

	// Brings the Specified Cells to Front
	public void toFront(Object[] c) {
		graph.getGraphLayoutCache().toFront(c);
	}

	public void toBack(Object[] c) {
		graph.getGraphLayoutCache().toBack(c);
	}

	public void undo() {
		try {
			undoManager.undo(graph.getGraphLayoutCache());
			//TODO
			
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			updateMenuHistoryButton();
		}
	}

	// Redo the last Change to the Model or the View
	public void redo() {
		try {
			undoManager.redo(graph.getGraphLayoutCache());
			//TODO
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			updateMenuHistoryButton();
		}
	}
	
	
	
	public void updateMenuHistoryButton() {
		if (diagramlistener != null)
			diagramlistener.DiagramChanged(new DiagramEvent(undoManager
					.canUndo(graph.getGraphLayoutCache()), undoManager
					.canRedo(graph.getGraphLayoutCache())));
	}
	
	
	public void addDiagramListener(DiagramListener diagramlistener) {
		this.diagramlistener = diagramlistener;
	}
	
	
	public void saveFile() throws IOException {
		int returnValue = JFileChooser.CANCEL_OPTION;
		initFileChooser();
		returnValue = fileChooser.showSaveDialog(graph);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			Container parent = graph.getParent();
			BasicMarqueeHandler marquee = graph.getMarqueeHandler();
			graph.setMarqueeHandler(null);
			try {
				uninstallListeners(graph);
				parent.remove(graph);
				ObjectOutputStream out = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(
								fileChooser.getSelectedFile())));
				out.writeObject(graph);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(graph, e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				graph.setMarqueeHandler(marquee);
				if (parent instanceof JViewport) {
					JViewport viewPort = (JViewport) parent;
					viewPort.setView(graph);
				} else {
					parent.add(graph);
				}
				installListeners(graph);
				saved=true;
			}
		}
		else if (returnValue == JFileChooser.CANCEL_OPTION){
				saved=false;
		}
		
		
	}

	
	public void openFile() throws IOException{
		int returnValue = JFileChooser.CANCEL_OPTION;
		initFileChooser();
		returnValue = fileChooser.showOpenDialog(graph);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			Container parent = graph.getParent();
			//Connection connection = (Connection)graph.getMarqueeHandler();
			try {
				uninstallListeners(graph);
				parent.remove(graph);
				ObjectInputStream in = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(fileChooser
								.getSelectedFile())));
				graph = (JGraph) in.readObject();
				_connection.setGraph(graph);
				graph.setMarqueeHandler(_connection);
				diagramlistener.DiagramChanged(new DiagramEvent(false,false));
				if (parent instanceof JViewport) {
					JViewport viewPort = (JViewport) parent;
					viewPort.setView(graph);
				} else {
					parent.add(graph);
				}
				installListeners(graph);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(graph, e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

  

	protected void initFileChooser() {
		if (fileChooser == null) {
			fileChooser = new JFileChooser();
			FileFilter fileFilter = new FileFilter() {
				
				public boolean accept(File f) {
					if (f == null)
						return false;
					if (f.getName() == null)
						return false;
					if (f.getName().endsWith(".dlc"))
						return true;
					if (f.isDirectory())
						return true;

					return false;
				}

				public String getDescription() {
					return "Digital Logic Circuit file (.dlc)";
				}
			};
			fileChooser.setFileFilter(fileFilter);
		}
	}

	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DELETE)
			// Execute Remove Action on Delete Key Press
		{
				if (!graph.isSelectionEmpty()) {
					Object[] cells = graph.getSelectionCells();
					cells = graph.getDescendants(cells);
					graph.getModel().remove(cells);
				}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueChanged(GraphSelectionEvent e) {
		// TODO Auto-generated method stub
		if(!graph.isSelectionEmpty()){
				diagramlistener.DiagramChanged(new DiagramEvent(true,true,true,true,graph.getSelectionCount() > 1,undoManager
						.canUndo(graph.getGraphLayoutCache()), undoManager
						.canRedo(graph.getGraphLayoutCache())));
			
		}
		else{
			diagramlistener.DiagramChanged(new DiagramEvent(false, false ,false,false,false,undoManager
					.canUndo(graph.getGraphLayoutCache()), undoManager
					.canRedo(graph.getGraphLayoutCache())));
		}
//		
		
	}

	public void remove() throws Exception {
		// TODO Auto-generated method stub
		if(!graph.isSelectionEmpty()){
			Object[] cells = graph.getSelectionCells();
			cells = graph.getDescendants(cells);
			graph.getModel().remove(cells);
		}
		else{
			throw new Exception();
		}
		
	}
	
	

}
