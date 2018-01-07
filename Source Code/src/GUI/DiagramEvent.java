package GUI;

import java.util.ArrayList;
import java.util.List;

import org.jgraph.graph.DefaultGraphCell;

public class DiagramEvent {
	private boolean isUndo;
	private boolean isRedo;
	private boolean isSave;
	private boolean isTruthTable;
	private boolean isSimplify;
	private boolean isFunction;
	private boolean isRemove;
	private boolean isCut;
	private boolean isCopy;
	private boolean isGroup;
	private boolean isUngroup;
	
	
	
	
	public boolean isGroup() {
		return isGroup;
	}
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	public boolean isUngroup() {
		return isUngroup;
	}
	public void setUngroup(boolean isUngroup) {
		this.isUngroup = isUngroup;
	}
	public boolean isCut() {
		return isCut;
	}
	public void setCut(boolean isCut) {
		this.isCut = isCut;
	}
	public boolean isCopy() {
		return isCopy;
	}
	public void setCopy(boolean isCopy) {
		this.isCopy = isCopy;
	}
	public boolean isRemove() {
		return isRemove;
	}
	public void setRemove(boolean isRemove) {
		this.isRemove = isRemove;
	}
	private List<DefaultGraphCell> outputs=new ArrayList();
	
	
	public List<DefaultGraphCell> getOutputs() {
		return outputs;
	}
	public void setOutputs(List<DefaultGraphCell> outputs) {
		this.outputs = outputs;
	}
	public boolean isTruthTable() {
		return isTruthTable;
	}
	public void setTruthTable(boolean isTruthTable) {
		this.isTruthTable = isTruthTable;
	}
	public boolean isSimplify() {
		return isSimplify;
	}
	public void setSimplify(boolean isSimplify) {
		this.isSimplify = isSimplify;
	}
	public boolean isFunction() {
		return isFunction;
	}
	public void setFunction(boolean isFunction) {
		this.isFunction = isFunction;
	}
	public boolean isUndo() {
		return isUndo;
	}
	public void setUndo(boolean isUndo) {
		this.isUndo = isUndo;
	}
	public boolean isRedo() {
		return isRedo;
	}
	public void setRedo(boolean isRedo) {
		this.isRedo = isRedo;
	}
	
	public boolean isSave() {
		return isSave;
	}
	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}
	public DiagramEvent(boolean isUndo, boolean isRedo) {
		super();
		this.isUndo = isUndo;
		this.isRedo = isRedo;
	}
	
	public DiagramEvent(boolean isCopy, boolean isCut,boolean isRemove,boolean isUngroup,boolean isGroup,boolean isUndo, boolean isRedo) {
		super();
		this.isCopy = isCopy;
		this.isCut = isCut;
		this.isRemove=isRemove;
		this.isUndo = isUndo;
		this.isRedo = isRedo;
		this.isUngroup=isUngroup;
		this.isGroup=isGroup;
	}
	
	public DiagramEvent(boolean isSave) {
		super();
		this.isSave = isSave;
	}
	public DiagramEvent(List<DefaultGraphCell> outputs){
		this.outputs=outputs;
		if(!this.outputs.isEmpty()){
			this.isTruthTable=true;
			this.isSimplify=true;
			this.isFunction=true;
		}
		else{
			this.isTruthTable=false;
			this.isSimplify=false;
			this.isFunction=false;
		}
	}
	
}
