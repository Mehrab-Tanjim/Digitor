package LogicEngine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;

import GUI.Console;
import GUI.TruthTablePane;
import Gate.GateObj;

public class GateTraverse {
	private JGraph graph;
	private Console console;
	private TruthTablePane truthtablepane;
	private List<DefaultGraphCell> outputs=new ArrayList();
	
	
	public GateTraverse(){
		
	}

	public JGraph getGraph() {
		return graph;
	}

	public void setGraph(JGraph graph) {
		this.graph = graph;
	}
	public void setConsole(Console console) {
		this.console = console;
	}
	
	
	
	public TruthTablePane getTruthtablepane() {
		return truthtablepane;
	}

	public void setTruthtablepane(TruthTablePane truthtablepane) {
		this.truthtablepane = truthtablepane;
	}

	public void Merge(Map<String,String> m1, Map<String,String> m2){

			for(String key : m2.keySet()) {
				    if(m1.containsKey(key)) {
				    } else {
				        m1.put(key,m2.get(key));
				    }
				}
	}
	
		
		///////we will be using this method for handling all model related tasks//////////
		/////////this is for getting dynamic function built from graph/////////
		public void update(DefaultPort source, DefaultPort target){
			
			DefaultGraphCell sourceCell=(DefaultGraphCell)source.getParent();
			DefaultGraphCell targetCell=(DefaultGraphCell)target.getParent();
			GateObj sourceObj=(GateObj)sourceCell.getUserObject();
			GateObj targetObj=(GateObj)targetCell.getUserObject();
			 
			
			if(!sourceObj.getGate().equals("OUTPUT")&&!targetObj.getGate().equals("OUTPUT")){
				
				if(source.equals(source.getParent().getChildAt(0))&&sourceObj.getOutput()!=null)
				{
					coloredge(source,sourceObj);
					console.Append(sourceObj.getOutput()+": "+sourceObj.isOUTPUT()+'\n');
					
					if(target.equals(target.getParent().getChildAt(1))) 
						{
							targetObj.setINPUT1(sourceObj.isOUTPUT());
							targetObj.setInput1(sourceObj.getOutput());
							targetObj.getInp1Vars().clear();
							targetObj.getInp1Vars().putAll(sourceObj.getOutVars());
						}
					else if(target.equals(target.getParent().getChildAt(2))) 
						{
							targetObj.setINPUT2(sourceObj.isOUTPUT());
							targetObj.setInput2(sourceObj.getOutput());
							targetObj.getInp2Vars().clear();
							targetObj.getInp2Vars().putAll(sourceObj.getOutVars());
						}
					targetObj.getOutVars().clear();
					targetObj.getOutVars().putAll(targetObj.getInp1Vars());
					Merge(targetObj.getOutVars(),targetObj.getInp2Vars());				
					Iterator iter= graph.getModel().edges((DefaultPort)targetCell.getChildAt(0));
					
					
					while (iter.hasNext()) {
						DefaultEdge edge = (DefaultEdge)iter.next();					
						try{
							update((DefaultPort)edge.getSource(),(DefaultPort)edge.getTarget());
						}
						catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(graph, e.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
					
					
				}
				else if(target.equals(target.getParent().getChildAt(0))&&targetObj.getOutput()!=null)
				{
					coloredge(target,targetObj);
					console.Append(targetObj.getOutput()+": "+targetObj.isOUTPUT()+'\n');
					
					if(source.equals(source.getParent().getChildAt(1))) 
						{
							sourceObj.setINPUT1(targetObj.isOUTPUT());
							sourceObj.setInput1(targetObj.getOutput());
							sourceObj.getInp1Vars().clear();
							sourceObj.getInp1Vars().putAll(targetObj.getOutVars());
						}
					else if(source.equals(source.getParent().getChildAt(2)))
						{
							sourceObj.setINPUT2(targetObj.isOUTPUT());
							sourceObj.setInput2(targetObj.getOutput());
							sourceObj.getInp2Vars().clear();
							sourceObj.getInp2Vars().putAll(targetObj.getOutVars());
						}
					sourceObj.getOutVars().clear();
					sourceObj.getOutVars().putAll(sourceObj.getInp1Vars());
					Merge(sourceObj.getOutVars(),sourceObj.getInp2Vars());	
					
					Iterator iter= graph.getModel().edges((DefaultPort)sourceCell.getChildAt(0));
					while (iter.hasNext()) {
						DefaultEdge edge = (DefaultEdge)iter.next();
						try{
							update((DefaultPort)edge.getSource(),(DefaultPort)edge.getTarget());
						}
						catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(graph, e.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
			
			else{
				if(sourceObj.getGate().equals("OUTPUT")&&targetObj.getOutput()!=null)
				{
					
					sourceObj.setInput2(targetObj.getOutput());
					coloredge(target,targetObj);
					sourceObj.setINPUT1(targetObj.isOUTPUT());
					sourceObj.getOutVars().clear();
					sourceObj.getOutVars().putAll(targetObj.getOutVars());
					for(String key : sourceObj.getOutVars().keySet()) {
						System.out.println(key);
					}
					
					
					
					colorOutput(sourceCell,sourceObj.isINPUT1());
					console.Append(sourceObj.getOutput()+": "+sourceObj.isOUTPUT()+'\n');
					//console.setFunction(sourceObj.getOutput());
					if(!outputs.contains(sourceCell)){
						outputs.add(sourceCell);
					}
					truthtablepane.Functions(outputs);
					
				}
		 		else if(targetObj.getGate().equals("OUTPUT")&&sourceObj.getOutput()!=null)
				{
					targetObj.setInput2(sourceObj.getOutput());
					coloredge(source,sourceObj);
					targetObj.setINPUT1(sourceObj.isOUTPUT());
					targetObj.getOutVars().clear();
					targetObj.getOutVars().putAll(sourceObj.getOutVars());
					for(String key : targetObj.getOutVars().keySet()) {
						System.out.println(key);
					}
					colorOutput(targetCell,targetObj.isINPUT1());
					console.Append(targetObj.getOutput()+": "+targetObj.isOUTPUT()+'\n');
					//console.setFunction(targetObj.getOutput());
					if(!outputs.contains(targetCell)){
						outputs.add(targetCell);
					}
					truthtablepane.Functions(outputs);
				}
			}
			
		}
		
	public void update(DefaultEdge Edge){
			
			DefaultPort source=(DefaultPort)Edge.getSource();
			DefaultPort target=(DefaultPort)Edge.getTarget();
			
			DefaultGraphCell sourceCell=(DefaultGraphCell)source.getParent();
			DefaultGraphCell targetCell=(DefaultGraphCell)target.getParent();
			GateObj sourceObj=(GateObj)sourceCell.getUserObject();
			GateObj targetObj=(GateObj)targetCell.getUserObject();
			
			
			if(!sourceObj.getGate().equals("OUTPUT")&&!targetObj.getGate().equals("OUTPUT")){
				
				if(source.equals(source.getParent().getChildAt(0))&&sourceObj.getOutput()!=null)
				{
					coloredge(source,sourceObj);
					console.Append(sourceObj.getOutput()+": "+sourceObj.isOUTPUT()+'\n');
								
					if(target==target.getParent().getChildAt(1)) targetObj.setINPUT1(sourceObj.isOUTPUT());
					else if(target==target.getParent().getChildAt(2)) targetObj.setINPUT2(sourceObj.isOUTPUT());
					
									
					Iterator iter= graph.getModel().edges((DefaultPort)targetCell.getChildAt(0));
					
					
					while (iter.hasNext()) {
						DefaultEdge edge = (DefaultEdge)iter.next();					
						try{
							update(edge);
						}
						catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(graph, e.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
					
					
				}
				else if(target.equals(target.getParent().getChildAt(0))&&targetObj.getOutput()!=null)
				{
					coloredge(target,targetObj);
					console.Append(targetObj.getOutput()+": "+targetObj.isOUTPUT()+'\n');
					
					if(source==source.getParent().getChildAt(1)) sourceObj.setINPUT1(targetObj.isOUTPUT());
					else if(source==source.getParent().getChildAt(2)) sourceObj.setINPUT2(targetObj.isOUTPUT());
					
					Iterator iter= graph.getModel().edges((DefaultPort)sourceCell.getChildAt(0));
					while (iter.hasNext()) {
						DefaultEdge edge = (DefaultEdge)iter.next();
						try{
							update(edge);
						}
						catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(graph, e.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
			
			else{
				if(sourceObj.getGate().equals("OUTPUT")&&targetObj.getOutput()!=null)
				{
					coloredge(target,targetObj);
					sourceObj.setINPUT1(targetObj.isOUTPUT());
					colorOutput(sourceCell,sourceObj.isINPUT1());
					console.Append(sourceObj.getOutput()+": "+sourceObj.isOUTPUT()+'\n');
				}
				else if(targetObj.getGate().equals("OUTPUT")&&sourceObj.getOutput()!=null)
				{
					coloredge(source,sourceObj);
					targetObj.setINPUT1(sourceObj.isOUTPUT());
					colorOutput(targetCell,targetObj.isINPUT1());
					console.Append(targetObj.getOutput()+": "+targetObj.isOUTPUT()+'\n');
				}
			}
			
		}
		

/*	
	///////we will be using this method for handling all model related tasks//////////
	/////////this is for getting dynamic function built from graph/////////
	public void update(DefaultPort source, DefaultPort target){
		
		DefaultGraphCell sourceCell=(DefaultGraphCell)source.getParent();
		DefaultGraphCell targetCell=(DefaultGraphCell)target.getParent();
		GateObj sourceObj=(GateObj)sourceCell.getUserObject();
		GateObj targetObj=(GateObj)targetCell.getUserObject();
		
		
		if(sourceObj.getGate()!="OUTPUT"&&targetObj.getGate()!="OUTPUT"){
			
			if(source==source.getParent().getChildAt(0)&&sourceObj.getOutput()!=null)
			{
				coloredge(source,sourceObj);
				console.Append(sourceObj.getOutput()+": "+sourceObj.isOUTPUT()+'\n');
							
				if(target==target.getParent().getChildAt(1)) 
					{
						targetObj.setINPUT1(sourceObj.isOUTPUT());
						targetObj.setInput1(sourceObj.getOutput());
						System.out.println(sourceObj.getOutput());
					}
				else if(target==target.getParent().getChildAt(2)) 
					{
						targetObj.setINPUT2(sourceObj.isOUTPUT());
						targetObj.setInput2(sourceObj.getOutput());
					}
				
								
				Iterator iter= model.edges((DefaultPort)targetCell.getChildAt(0));
				
				
				while (iter.hasNext()) {
					DefaultEdge edge = (DefaultEdge)iter.next();					
					update((DefaultPort)edge.getSource(),(DefaultPort)edge.getTarget());
				}
				
				
			}
			else if(target==target.getParent().getChildAt(0)&&targetObj.getOutput()!=null)
			{
				coloredge(target,targetObj);
				console.Append(targetObj.getOutput()+": "+targetObj.isOUTPUT()+'\n');
				
				if(source==source.getParent().getChildAt(1)) 
					{
						sourceObj.setINPUT1(targetObj.isOUTPUT());
						sourceObj.setInput1(targetObj.getOutput());
					}
				else if(source==source.getParent().getChildAt(2))
					{
						sourceObj.setINPUT2(targetObj.isOUTPUT());
						sourceObj.setInput2(targetObj.getOutput());
					}
				
				Iterator iter= model.edges((DefaultPort)sourceCell.getChildAt(0));
				while (iter.hasNext()) {
					DefaultEdge edge = (DefaultEdge)iter.next();
					update((DefaultPort)edge.getSource(),(DefaultPort)edge.getTarget());
				}
			}
		}
		
		else{
			if(sourceObj.getGate()=="OUTPUT"&&targetObj.getOutput()!=null)
			{
				sourceObj.setInput2(targetObj.getOutput());
				coloredge(target,targetObj);
				sourceObj.setINPUT1(targetObj.isOUTPUT());
				colorOutput(sourceCell,sourceObj.isINPUT1());
				console.Append(sourceObj.getOutput()+": "+sourceObj.isOUTPUT()+'\n');
				console.setFunction(sourceObj.getOutput());
			}
			else if(targetObj.getGate()=="OUTPUT"&&sourceObj.getOutput()!=null)
			{
				targetObj.setInput2(sourceObj.getOutput());
				coloredge(source,sourceObj);
				targetObj.setINPUT1(sourceObj.isOUTPUT());
				colorOutput(targetCell,targetObj.isINPUT1());
				console.Append(targetObj.getOutput()+": "+targetObj.isOUTPUT()+'\n');
				console.setFunction(targetObj.getOutput());
			}
		}
		
	}
	
public void update(DefaultEdge Edge){
		
		DefaultPort source=(DefaultPort)Edge.getSource();
		DefaultPort target=(DefaultPort)Edge.getTarget();
		
		DefaultGraphCell sourceCell=(DefaultGraphCell)source.getParent();
		DefaultGraphCell targetCell=(DefaultGraphCell)target.getParent();
		GateObj sourceObj=(GateObj)sourceCell.getUserObject();
		GateObj targetObj=(GateObj)targetCell.getUserObject();
		
		
		if(sourceObj.getGate()!="OUTPUT"&&targetObj.getGate()!="OUTPUT"){
			
			if(source==source.getParent().getChildAt(0)&&sourceObj.getOutput()!=null)
			{
				coloredge(source,sourceObj);
				console.Append(sourceObj.getOutput()+": "+sourceObj.isOUTPUT()+'\n');
							
				if(target==target.getParent().getChildAt(1)) targetObj.setINPUT1(sourceObj.isOUTPUT());
				else if(target==target.getParent().getChildAt(2)) targetObj.setINPUT2(sourceObj.isOUTPUT());
				
								
				Iterator iter= model.edges((DefaultPort)targetCell.getChildAt(0));
				
				
				while (iter.hasNext()) {
					DefaultEdge edge = (DefaultEdge)iter.next();					
					update(edge);
				}
				
				
			}
			else if(target==target.getParent().getChildAt(0)&&targetObj.getOutput()!=null)
			{
				coloredge(target,targetObj);
				console.Append(targetObj.getOutput()+": "+targetObj.isOUTPUT()+'\n');
				
				if(source==source.getParent().getChildAt(1)) sourceObj.setINPUT1(targetObj.isOUTPUT());
				else if(source==source.getParent().getChildAt(2)) sourceObj.setINPUT2(targetObj.isOUTPUT());
				
				Iterator iter= model.edges((DefaultPort)sourceCell.getChildAt(0));
				while (iter.hasNext()) {
					DefaultEdge edge = (DefaultEdge)iter.next();
					update(edge);
				}
			}
		}
		
		else{
			if(sourceObj.getGate()=="OUTPUT"&&targetObj.getOutput()!=null)
			{					
			
				sourceObj.setINPUT1(targetObj.isOUTPUT());
				colorOutput(sourceCell,sourceObj.isINPUT1());
				coloredge(target,targetObj);
				console.Append(sourceObj.getOutput()+": "+sourceObj.isOUTPUT()+'\n');
			}
			else if(targetObj.getGate()=="OUTPUT"&&sourceObj.getOutput()!=null)
			{
				
				
				targetObj.setINPUT1(sourceObj.isOUTPUT());				
				colorOutput(targetCell,targetObj.isINPUT1());
				coloredge(source,sourceObj);
				console.Append(targetObj.getOutput()+": "+targetObj.isOUTPUT()+'\n');
			}
		}
		
	}
	*/

	public void colorOutput(DefaultGraphCell cell,boolean b){
		Map map = new Hashtable();
		Map map1 = new Hashtable();
		if(b) {
			ImageIcon icon = new ImageIcon(JGraph.class.getResource("/Images/"
					+ "OUTPUTr" + ".png"));
			GraphConstants.setIcon(map, icon);
			map1.put(cell, map);
			graph.getModel().edit(map1, null, null, null);
			graph.refresh();
			}
		else{
			{
				ImageIcon icon = new ImageIcon(JGraph.class.getResource("/Images/"
						+ "OUTPUT" + ".png"));
				GraphConstants.setIcon(map, icon);
				map1.put(cell, map);
				graph.getModel().edit(map1, null, null, null);
				graph.refresh();
			}
		}
	}

	public void coloredge(DefaultPort Port,GateObj Gate){
		Iterator iter0= graph.getModel().edges(Port);
		
		while(iter0.hasNext()){
		DefaultEdge edge0 = (DefaultEdge)iter0.next();
		if(Gate.isOUTPUT()) {
			Map map=new Hashtable();
			Map map2=new Hashtable();						
			GraphConstants.setLineColor(map2,Color.RED);
			map.put(edge0,map2);
			graph.getModel().edit(map,null,null,null);
			graph.refresh();
		}
		else {
			Map map=new Hashtable();
			Map map2=new Hashtable();						
			GraphConstants.setLineColor(map2,Color.BLUE);
			map.put(edge0,map2);
			graph.getModel().edit(map,null,null,null);
			graph.refresh();
		}
		}
	}
	
}
