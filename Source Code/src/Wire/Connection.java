package Wire;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.jgraph.JGraph;
import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.PortView;

import Gate.GateObj;
import LogicEngine.GateTraverse;

public class Connection extends BasicMarqueeHandler {

	private Point2D start, current;
	private PortView port, firstPort;
	private JGraph graph;

	private GateTraverse Update = new GateTraverse();

	public GateTraverse getUpdate() {
		return Update;
	}

	public JGraph getGraph() {
		return graph;
	}

	public boolean isForceMarqueeEvent(MouseEvent e) {
		port = getSourcePortAt(e.getPoint());
 		if (SwingUtilities.isLeftMouseButton(e)&&port!=null) {
			return true;
		}
		if (port != null && graph.isPortsVisible())
			return true;
		return super.isForceMarqueeEvent(e);
	}

	public void setGraph(JGraph graph) {
		this.graph = graph;
		Update.setGraph(graph);
	}

	public void mouseMoved(MouseEvent e) {
		if (e != null && getSourcePortAt(e.getPoint()) != null) {
			graph.setCursor(new Cursor(Cursor.HAND_CURSOR));
			e.consume();
		} else
			super.mouseMoved(e);
	}

	public DefaultGraphCell getSourceAt(Point2D point) {

		DefaultGraphCell result = (DefaultGraphCell) graph
				.getFirstCellForLocation(point.getX(), point.getY());
		return result;
	}
	
	
	public void mousePressed(final MouseEvent e) {
		// If Right Mouse Button
		if (SwingUtilities.isLeftMouseButton(e)&&port!=null) {
			
			// Else if in ConnectMode and Remembered Port is Valid
		//} else if (port != null && graph.isPortsVisible()) {
			// Remember Start Location
			start = graph.toScreen(port.getLocation());
			// Remember First Port
			firstPort = port;
		} else if(SwingUtilities.isRightMouseButton(e)){
			
			super.mousePressed(e);
		}
	}
	
	public JPopupMenu createPopupMenu(final Point pt, final Object cell) {
		JPopupMenu menu = new JPopupMenu();
		if (cell != null) {
			// Edit
			menu.add(new AbstractAction("Rename") {
				public void actionPerformed(ActionEvent e) {
					graph.startEditingAtCell(cell);
				}
			});
		}
		// Remove
		if (!graph.isSelectionEmpty()) {
			menu.addSeparator();
			menu.add(new AbstractAction("Remove") {
				public void actionPerformed(ActionEvent e) {
					if (!graph.isSelectionEmpty()) {
						Object[] cells = graph.getSelectionCells();
						cells = graph.getDescendants(cells);
						graph.getModel().remove(cells);
					}
				}
			});
		}
		//menu.addSeparator();
		// Insert
		menu.add(new AbstractAction("Replace") {
			public void actionPerformed(ActionEvent ev) {
				//insert(pt);
			}
		});
		return menu;
	}

	public void mouseDragged(MouseEvent e) {
		if (start != null) {
			Graphics g = graph.getGraphics();
			PortView newPort = getTargetPortAt(e.getPoint());
			if (newPort == null || newPort != port) {
				paintConnector(Color.black, graph.getBackground(), g);
				port = newPort;
				if (port != null)
					current = graph.toScreen(port.getLocation());
				else
					current = graph.snap(e.getPoint());
				paintConnector(graph.getBackground(), Color.black, g);
			}
		}
		super.mouseDragged(e);
	}

	protected void paintConnector(Color fg, Color bg, Graphics g) {
		if (graph.isXorEnabled()) {
			g.setColor(fg);
			g.setXORMode(bg);
			paintPort(graph.getGraphics());

			drawConnectorLine(g);
		}
	}

	protected void drawConnectorLine(Graphics g) {
		if (firstPort != null && start != null && current != null) {
			g.drawLine((int) start.getX(), (int) start.getY(),
					(int) current.getX(), (int) current.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {

		if (e != null && port != null && firstPort != null && firstPort != port) {
			DefaultPort source = (DefaultPort) firstPort.getCell();
			DefaultPort target = (DefaultPort) port.getCell();
			DefaultGraphCell sourceCell = (DefaultGraphCell) source.getParent();
			DefaultGraphCell targetCell = (DefaultGraphCell) target.getParent();
			GateObj sourceObj = (GateObj) sourceCell.getUserObject();
			GateObj targetObj = (GateObj) targetCell.getUserObject();
			if (source.equals(source.getParent().getChildAt(0))
					&& target.equals(target.getParent().getChildAt(0))
					&&!( sourceObj.getGate().equals("OUTPUT")||
					 targetObj.getGate().equals("OUTPUT"))) {
				JOptionPane.showMessageDialog(graph, "Both Ports Are Outputs!",
						"Error", JOptionPane.ERROR_MESSAGE);
				graph.repaint();
				firstPort = port = null;
				start = current = null;
			} else {
				connect(source, target);
			}

			e.consume();
		} else
			graph.repaint();
		firstPort = port = null;
		start = current = null;
		super.mouseReleased(e);
	}

	public PortView getSourcePortAt(Point2D point) {
		graph.setJumpToDefaultPort(false);
		PortView result;
		try {
			result = graph.getPortViewAt(point.getX(), point.getY());
		} finally {
			graph.setJumpToDefaultPort(true);
		}
		return result;
	}

	protected PortView getTargetPortAt(Point2D point) {
		return graph.getPortViewAt(point.getX(), point.getY());
	}

	public void connect(final DefaultPort source, final DefaultPort target) {

		DefaultEdge edge = createDefaultEdge();
		if (graph.getModel().acceptsSource(edge, source)
				&& graph.getModel().acceptsTarget(edge, target)) {
			edge.getAttributes().applyMap(createEdgeAttributes());
			graph.getGraphLayoutCache().insertEdge(edge, source, target);
		}

		// ////thread////////////
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Update.update(source, target);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(graph, e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		t1.start();

		// ///end of thread//////////////////

	}

	protected DefaultEdge createDefaultEdge() {
		return new DefaultEdge();
	}

	public Map createEdgeAttributes() {
		Map map = new Hashtable();
		GraphConstants.setEndFill(map, true);
		GraphConstants.setLineStyle(map, GraphConstants.STYLE_ORTHOGONAL);
		GraphConstants.setRouting(map, GraphConstants.ROUTING_SIMPLE);
		GraphConstants.setBendable(map, false);
		return map;
	}

	public void paint(JGraph graph, Graphics g) {
		super.paint(graph, g);

		if (!graph.isXorEnabled()) {
			g.setColor(Color.black);
			drawConnectorLine(g);
		}
	}

	protected void paintPort(Graphics g) {
		if (port != null) {
			boolean o = (GraphConstants.getOffset(port.getAllAttributes()) != null);
			Rectangle2D r = (o) ? port.getBounds() : port.getParentView()
					.getBounds();
			r = graph.toScreen((Rectangle2D) r.clone());
			r.setFrame(r.getX() - 3, r.getY() - 3, r.getWidth() + 6,
					r.getHeight() + 6);
			graph.getUI().paintCell(g, port, r, true);
		}
	}

}