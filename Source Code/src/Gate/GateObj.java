package Gate;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GateObj implements Serializable{
	private String Gate;
	private String Input1;
	private String Input2;
	private String Output;
	private boolean INPUT1;
	private boolean INPUT2;
	private boolean OUTPUT;
	private String label;
	private Map<String, String> OutVars=new HashMap();
	private Map<String, String> Inp1Vars=new HashMap();
	private Map<String, String> Inp2Vars=new HashMap();
	
	public GateObj(String Gate){
		this.Gate=Gate;
		this.Input1=null;
		this.Input2=null;
		this.Output=null;
		this.label=null;
	}
	


	
	public String getInput1() {
		return Input1;
	}




	public String getInput2() {
		return Input2;
	}




	public Map<String, String> getOutVars() {
		return OutVars;
	}




	public void setOutVars(Map<String, String> outVars) {
		OutVars = outVars;
	}




	public Map<String, String> getInp1Vars() {
		return Inp1Vars;
	}




	public void setInp1Vars(Map<String, String> inp1Vars) {
		Inp1Vars = inp1Vars;
	}




	public Map<String, String> getInp2Vars() {
		return Inp2Vars;
	}




	public void setInp2Vars(Map<String, String> inp2Vars) {
		Inp2Vars = inp2Vars;
	}








	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String toString(){
		return label;
		
	}
	
	public String getGate(){
		return Gate;
		
	}
	
	
	
	public boolean isINPUT1() {
		return INPUT1;
	}


	public void setInput1(String input1) {
		Input1 = input1;
	}


	public void setInput2(String input2) {
		Input2 = input2;
	}


	public void setINPUT1(boolean iNPUT1) {
		INPUT1 = iNPUT1;
	}


	public void setINPUT2(boolean iNPUT2) {
		INPUT2 = iNPUT2;
	}


	public boolean isOUTPUT() {
		return OUTPUT;
	}
	
	

	public void setOUTPUT(boolean oUTPUT) {
		OUTPUT = oUTPUT;
	}


	public String getOutput(){
		if(Input1!=null||Input2!=null){
		if(Gate.equals("AND"))
		{	
			if(Input1!=null&&Input2!=null)
			this.Output= "("+Input1+"."+Input2+")";
			else if(Input1!=null)
				this.Output= null;
			else if(Input2!=null)
				this.Output= null;
			this.OUTPUT=INPUT1&INPUT2;
			
		}
		else if(Gate.equals("OR"))
		{
			if(Input1!=null&&Input2!=null)
				this.Output= "("+Input1+"+"+Input2+")";
			else if(Input1!=null)
				this.Output= Input1;
			else if(Input2!=null)
				this.Output= Input2;
			this.OUTPUT=INPUT1|INPUT2;
			
		}
		else if(Gate.equals("NOR"))
		{
			if(Input1!=null&&Input2!=null)
				this.Output= "("+Input1+"+"+Input2+")"+"'";
			else if(Input1!=null)
				this.Output= Input1+"'";
			else if(Input2!=null)
				this.Output= Input2+"'";
			this.OUTPUT=!(INPUT1|INPUT2);
			
		}
		else if(Gate.equals("NAND"))
		{
			if(Input1!=null&&Input2!=null)
				this.Output= "("+Input1+"."+Input2+")"+"'";
			else if(Input1!=null)
				this.Output= Input1+"'";
			else if(Input2!=null)
				this.Output= Input2+"'";
			this.OUTPUT=!(INPUT1&INPUT2);
			
		}
		else if(Gate.equals("XOR"))
		{
			if(Input1!=null&&Input2!=null)
				this.Output= "("+Input1+"(+)"+Input2+")";
			else if(Input1!=null)
				this.Output= Input1;
			else if(Input2!=null)
				this.Output= Input2;
			this.OUTPUT=(INPUT1&!INPUT2)|(!INPUT1&INPUT2);
			
		}
		else if(Gate.equals("OUTPUT")){
			
			if(Input2!=null){
			this.Output=Input1+"="+Input2;			
			this.OUTPUT=INPUT1;
			}
		}
		
		else if(Gate.equals("NOT"))
		{
			if(Input1!=null)
				{
				this.Output= "("+Input1+")"+"'";
				this.OUTPUT=!INPUT1;
				}
			
		}
		else if(Gate.equals("INPUT")){
			
			this.Output=Input1;
			OutVars.clear();
			OutVars.put(Input1,Input1);
			this.OUTPUT=INPUT1;
		}
		}
		
		return Output;
		
	}
	
}

