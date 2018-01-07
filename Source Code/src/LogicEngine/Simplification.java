package LogicEngine;

import java.util.Scanner;

public class Simplification{
	private int VAR;//= 4;
	private int TERMS =200;
	private int LEVELS =100;
	
	
	private int sv[][][];//tier,row,column or how many times fully compared,how many numbers compared in a full cycle, the numbers
	private int pos, s;
	private int flag;
	private int c;
	private int r;
	private int t;
	
	private int unmarked[];//=new int[100] ;//is there a number which was unique and not comparable
	private int limit[];//=new int[LEVELS];//how many numbers are there in each levels?
	private int mintermsCol[];//=new int[TERMS];
	private int maxtermsRow[];//=new int[TERMS];
	private int Index[];//=new int[TERMS];
	private int count2;
	private int Checked[][];//=new int[50][50];
	private int mustterms;
	
	
	
	private void bin(int i)
	{
		int q = i;
		while (q != 0)
		{
			sv[0][r][c] = q % 2;
			q = q / 2;
			c--;
		}
		while (c >= 0) { sv[0][r][c] = 0; c--; }
	}
	
	private void save(int i, int j, int f)
	{
		for (c = 0; c<VAR; c++)
		{
			if (c == j) sv[f][limit[f]][c] = 3;//the position where the difference is observed
			else sv[f][limit[f]][c] = sv[f - 1][i][c];
		}
		unmarked[r]++;//one of the two numbers which were compared at least once and thus not unique
		unmarked[s]++;//the other of the two numbers
		if (limit[f]>0){
			for (c = 0; c<VAR && (sv[f][limit[f]][c] == sv[f][limit[f] - 1][c]); c++) count2++;//checks for redundancy
			if (count2 == VAR) limit[f]--;
		}
		count2 = 0;
		limit[f]++;
	}
	
	
	private  void save(int i, int f)//saving the unique number or essential prime
	{
		for (c = 0; c<VAR; c++)
			sv[f][limit[f]][c] = sv[f - 1][i][c];
	
		if (limit[f]>0){
			for (c = 0; c<VAR && (sv[f][limit[f]][c] == sv[f][limit[f] - 1][c]); c++) count2++;
			if (count2 == VAR) limit[f]--;
		}
		count2 = 0;
		limit[f]++;
	
	}
	
	private void compare(int tier, int lim)//comparing the full list of numbers, the number of the numbers in the list is 'lim'
	{
		int count2 = 0;
		for (r = 0; r<lim; r++)
		{
			for (s = r + 1; s<lim; s++)
			{
				for (c = 0; c<VAR; c++)
				if (sv[tier][r][c] != sv[tier][s][c]) { count2++; pos = c; }
				if (count2 == 1){ save(r, pos, tier + 1); flag = 1; }
				count2 = 0;
			}
		}
		for (r = 0; r<lim; r++)
		{
			if (unmarked[r] == 0) save(r, tier + 1);
			else unmarked[r] = 0;
		}
	}
	
	private  void checktable()
	{
		int count2 = 0;
		for (r = 0; r<limit[t]; r++)
		for (s = 0; s<mustterms; s++)
		{
			count2 = 0;
			for (c = 0; c<VAR && ((sv[t][r][c] == sv[0][s][c]) || (sv[t][r][c] == 3)); c++, count2++){}
			if (count2 == VAR) { Checked[r][s] = 1; mintermsCol[s]++; maxtermsRow[r]++; }
			else { Checked[r][s] = 0; }
	
		}
	}
	
	private  void indexsort(int arry[], int lim, boolean a)
	{
		int i, j, temp;
		for (i = 0; i<lim; i++)
		for (j = i + 1; j<lim; j++)
		{
			temp = Index[i];
			if (a){
				if (arry[Index[i]]>arry[Index[j]]) { Index[i] = Index[j]; Index[j] = temp; }
			}
			else { if (arry[Index[i]]<arry[Index[j]]) { Index[i] = Index[j]; Index[j] = temp; } }
		}
		for (i = 0; i<lim; i++) arry[i] = Index[i];
	}
	
	private boolean allchecked(boolean i[])
	{
		int count2 = 0;
		for (count2 = 0; count2<mustterms; count2++)
		{
			if (i[count2] == false) return false;
		}
		return true;
	}
	private void PetricksMethod()
	{
		boolean []checked=new boolean[TERMS];
//		for(int i:TERMS){
//			checked[i]=false;
//		}
		//checked.false };
		int count1 = 0;
		limit[t + 1] = 0;
		while (!allchecked(checked))
		for (s = 0; s<mustterms; s++)
		for (r = 0; r<limit[t]; r++)
		if (Checked[maxtermsRow[r]][mintermsCol[s]] == 1)
		{
			for (pos = 0; pos<mustterms; pos++)
			if (Checked[maxtermsRow[r]][pos] == 1 && checked[pos] == false)
			{
				checked[pos] = true;
				count1++;
				if (count1 == 1)save(r, t + 1);
			}
	
			count1 = 0;
		}
	}
	public String simplified;
	
	
	int Var;
	int terms;
	Integer minterms[];
	int dontcares;
	int dontterms[];
	String[] Variables;
	
	
	
	
	
	public String getSimplified() {
		return simplified;
	}

	public void setVar(int var) {
		this.Var = var;
	}

	public void setTerms(int terms) {
		this.terms = terms;
	}

	public void setMinterms(Integer[] minterms) {
		this.minterms = minterms;
	}

	public void setDontcares(int dontcares) {
		this.dontcares = dontcares;
	}

	public void setDontterms(int[] dontterms) {
		this.dontterms = dontterms;
	}

	public void setVariables(String[] variables) {
		Variables = variables;
	}

	public String simplification()
	{	
		simplified="";
		unmarked=new int[100] ;//is there a number which was unique and not comparable
		limit=new int[LEVELS];//how many numbers are there in each levels?
		mintermsCol=new int[TERMS];
		maxtermsRow=new int[TERMS];
		Index=new int[TERMS];
		Checked=new int[50][50];
		flag = 0;
		c = 0;
		r = 0;
		t = 0;
		count2 = 0;
		TERMS =100;
		LEVELS =100;
		
		
		for (int i = 0; i<TERMS; i++) Index[i] = i;
		r = 0;
		int number, value;
		this.VAR=Var;
		System.out.println(VAR);
		sv=new int[LEVELS][100][VAR];
		mustterms = terms;
		limit[0] = terms;
		int j=0;
		for (; terms>0; terms--)
		{
			c = VAR - 1;
			//System.out.println("Enter a value : ");
			//value=scan.nextInt();
			bin(minterms[j++]);
			r++;
		}
	
		limit[0] += dontcares;
		j=0;
		for (; dontcares>0; dontcares--)
		{
			c = VAR - 1;
			bin(dontterms[j++]);
			r++;
		}
	
		if (limit[0] == 16) {
			System.out.println("Simplified Boolean Terms:"+ 1);
			//exit(0);
		}
		else if (mustterms == 0) {
			System.out.println("Simplified Boolean Terms:" +0);
			//exit(0);
		}
	
		flag = 1;
		while (flag == 1)
		{
			flag = 0;
			compare(t, limit[t]);
			t++;
		}
		t--;
	
		checktable();
	
		indexsort(mintermsCol, mustterms, true);
		for (int i = 0; i<TERMS; i++) Index[i] = i;
		indexsort(maxtermsRow, limit[t], false);
	
		PetricksMethod();
	
		//char term[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	
	
		System.out.println("\nSimplified Boolean Expression :");
		for (r = 0; r<limit[t + 1]; r++)
		{
			for (c = 0; c<VAR; c++)
			{
				if (sv[t + 1][r][c] == 1) simplified+=(Variables[c]);
				else if (sv[t + 1][r][c] == 0) simplified+=(Variables[c]+"'");
			}
			if (r + 1<limit[t + 1]) simplified+="+";
		}
	
		return simplified;
	}
	
}