package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.jgraph.graph.DefaultGraphCell;

import Gate.GateObj;
import LogicEngine.Simplification;

public class TruthTablePane extends JDialog {
	private JList functions = new JList();
	private DefaultListModel modl = new DefaultListModel();
	private JPanel pane = new JPanel();
	private JButton ok = new JButton("Ok");
	private JButton cancel = new JButton("Cancel");
	private TruthTablePaneListener truthtablepanelistener;
	private DefaultTableModel tablemodel;

	private Map<String, String> map = new HashMap<>();
	private List<DefaultGraphCell> outputs;
	private List<String> listOfVars;
	private String str;
	private int[] arr;
	private int RowCount;
	private Object Values[][];
	private TruthTable truthtable;

	private int terms = 0;
	private List<Integer> minterms = new ArrayList();
	private int dontterms[];
	private String[] Variables;

	private boolean Table = false;
	private boolean Simplify = false;
	private Simplification simplify=new Simplification();

	public void setTable(boolean table) {
		Table = table;
	}

	public void setSimplify(boolean simplify) {
		Simplify = simplify;
	}

	public TruthTablePane(final Mainframe frame) {
		super(frame, "Select A Function", false);
		setSize(350, 600);
		setLocationRelativeTo(frame);
		setLayout(new GridBagLayout());
		setResizable(false);
		GridBagConstraints gc = new GridBagConstraints();

		functions.setModel(modl);
		Dimension dim = new Dimension();
		dim.height = 400;
		dim.width = 240;
		pane.setMinimumSize(dim);
		pane.setLayout(new BorderLayout());
		pane.add(new JScrollPane(functions), BorderLayout.CENTER);

		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = .01;
		gc.fill = GridBagConstraints.HORIZONTAL;

		gc.gridx = 0;
		gc.gridwidth = 2;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 50, 0, 50);
		add(pane, gc);

		// ///////////////
		gc.gridy++;
		gc.gridx = 0;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 50, 0, 0);
		add(ok, gc);
		gc.gridx++;
		gc.gridwidth = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 50);
		add(cancel, gc);

		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DefaultGraphCell selected = outputs.get(functions
						.getSelectedIndex());
				final GateObj gateobj = (GateObj) selected.getUserObject();

				listOfVars = new ArrayList<String>(gateobj.getOutVars()
						.values());

				Variables = new String[listOfVars.size()];
				listOfVars.toArray(Variables);
				RowCount = (int) Math.pow(2, listOfVars.size());
				listOfVars.add(gateobj.getInput1());

				str = gateobj.getInput2();
				arr = new int[listOfVars.size()];
				Values = new Integer[RowCount][listOfVars.size()];

				Thread t1 = new Thread(new Runnable() {

					@Override
					public void run() {
						try {

							// TODO
							terms=0;
							minterms.clear();
							StringManipulator();
							if (Table) {
								tablemodel = new DefaultTableModel(Values,
										listOfVars.toArray());
								truthtable.getTable().setModel(tablemodel);
								tablemodel.fireTableDataChanged();
								if(!frame.getMenu().getShowTable().isSelected())
								{
									frame.getMenu().getShowTable().setSelected(true);
									frame.getSplitpane().setDividerLocation(frame.getWidth()-200);	
								}
							}
							// TODO
							Integer[] MINTERMS = new Integer[minterms.size()];
							minterms.toArray(MINTERMS);
							if (Simplify) {
								simplify=null;
								simplify=new Simplification();
								simplify.setVar(Variables.length);
								simplify.setTerms(terms);
								simplify.setMinterms(MINTERMS);
								simplify.setDontcares(0);
								simplify.setDontterms(null);
								simplify.setVariables(Variables);
								
								JOptionPane.showMessageDialog(frame,
										gateobj.getInput1()+"="+simplify.simplification(), 
										"Simplified Boolean Expression",
										JOptionPane.PLAIN_MESSAGE);
							}
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(frame,
									e.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}

				});

				t1.start();

				setVisible(false);
			}

		});

		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					setVisible(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
	}

	void bcd(int arr[], int n) {
		if (n > 0 && arr[n - 1] == 0) {
			arr[n - 1] |= 1;
			return;
		}
		if (n > 0)
			arr[n - 1] = 0;
		n--;

		if (n > 0)
			bcd(arr, n);
		else
			return;
	}

	public void StringManipulator() {
		String temp = str;
		for (int j = 0; j < RowCount; j++) {
			int i;
			for (i = 0; i < listOfVars.size() - 1; i++) {
				if (listOfVars.get(i) != null) {
					int tmp = arr[i];
					if (tmp == 0) {
						map.put(listOfVars.get(i), "0");
						Values[j][i] = 0;
					} else {
						map.put(listOfVars.get(i), "1");
						Values[j][i] = 1;
					}

				}

			}
			stringModifier();
			manipulate();
			if (str.equals("1")) {
				Values[j][i] = 1;
				terms++;
				minterms.add(j);
			} else
				Values[j][i] = 0;
			bcd(arr, listOfVars.size() - 1);
			str = temp;
		}
	}

	public void stringModifier() {
		for (Entry<String, String> entry : map.entrySet()) {
			str = str.replace(entry.getKey(), entry.getValue());
		}
		map.clear();
	}

	public void manipulate() {
		while (str.length() != 1) {
			str = str.replace("(0.0)", "0");
			str = str.replace("(0.1)", "0");
			str = str.replace("(1.0)", "0");
			str = str.replace("(1.1)", "1");
			str = str.replace("(0+0)", "0");
			str = str.replace("(0+1)", "1");
			str = str.replace("(1+0)", "1");
			str = str.replace("(1+1)", "1");
			str = str.replace("(0(+)0)", "0");
			str = str.replace("(0(+)1)", "1");
			str = str.replace("(1(+)0)", "1");
			str = str.replace("(1(+)1)", "0");
			str = str.replace("(0)'", "1");
			str = str.replace("(1)'", "0");
			str = str.replace("0'", "1");
			str = str.replace("1'", "0");
		}
	}

	

	public void addTruthTablePaneListener(
			TruthTablePaneListener truthtablepanelistener) {
		this.truthtablepanelistener = truthtablepanelistener;
	}

	public void Functions(List<DefaultGraphCell> e) {
		outputs = e;
		Iterator<DefaultGraphCell> iter = e.iterator();
		GateObj gateobj;
		modl.clear();
		int i = 0;
		while (iter.hasNext()) {
			gateobj = (GateObj) iter.next().getUserObject();
			modl.addElement(gateobj.getOutput());
			// System.out.println(modl.getElementAt(i++));
		}
		if (truthtablepanelistener != null)
			truthtablepanelistener.Functions(e);
		else
			System.out.println("?");
	}

	public void setTruthTable(TruthTable truthtable) {
		this.truthtable = truthtable;
	}

}
