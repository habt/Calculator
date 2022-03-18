package function_calculator;

import java.util.*;
import java.util.regex.*;
/*
  this class has functions that take the string input, identify the functions used, obtain the numbers for the function
  and substitutes the function by the mathematical expression and the variables in the expression by the numbers
  find the first keyword, determine the substitute number operation , replace the function in the input using the functions regex.
 * 
 */
public class Substituter 
{
	String inp_expr = new String();
	String final_expr = new String();
	String next_expr = new String();
	String new_expr = new String();
	int current_exps=0;
	int prev_exps=0;
	
	public Substituter(String str)
	{
		inp_expr= str; //inp_expr will be the whole mathematical expression containing defined functions
		next_expr = inp_expr;
		new_expr=inp_expr;
	}
	
	public String replacer(Operations tks, String input) throws InvalidOperatorsequence
	{
		 /*if current exps >= prev exps it means prev exps contain defined exps in themselves*/
		
		next_expr=input;  
		
		if(tks.defined_fns.size()>0)
		{
		  for(int i=0;i<tks.defined_fns.size();i++)                //checks for the existence of all defined functions in the input string
			{
				Pattern pat = Pattern.compile(tks.defined_fns.get(i).regex);
				Matcher mat = pat.matcher(next_expr);
				boolean matchfound = mat.find();
				ArrayList<String> found_exp_regex= new ArrayList<String>();
				if(matchfound)
				{
					current_exps+=1;
						String[] s= mat.group(0).split("\\(",2);// 
						s[1]=s[1].split("\\)",2)[0];              // FROM 6,7)+sub(6,4) to 6,7 in s1
						s[1]=s[1].replaceAll("\\(", "\\\\(");     // this lines just in case add(8,cos(0))
						s[1]=s[1].replaceAll("\\)", "\\\\)");
						
						String inp_exp_regx="("+"("+s[0]+")"+"\\("+s[1]+"\\)"+")";
						found_exp_regex.add(inp_exp_regx);
						tks.defined_fns.get(i).found_exps.add(mat.group(0));
						
					for(int j=0;j<tks.defined_fns.get(i).found_exps.size();j++)    // eg for each found ADD exp
					{
						String strl = tks.defined_fns.get(i).found_exps.get(j);          //strl=add(5,2), add(cos(0),1)
						String[] br = strl.split("\\(",2);                               //br[1]=5 , 2) or br[1]=cs(0), 2)+...,br[0]=add
						
						String[] prenum = br[1].split("\\)",2);                         //num = [ 5  2 ], // ths only works if no expressions like add(4,cos(0)) but works for(4,3+7)
						String[] num = prenum[0].split(",");
										
						
						String st = tks.defined_fns.get(i).var_exp;
						for(int k=0;k<tks.defined_fns.get(i).variable_count;k++) // loops until all variables are substituted
						{
							st = st.replaceAll(tks.defined_fns.get(i).variables.get(k), num[k]);
						}
						tks.defined_fns.get(i).new_exps.add(st);
						Pattern pt = Pattern.compile(found_exp_regex.get(j));
						Matcher mt = pt.matcher(next_expr);
						
						if(mt.find()) 
						{
							next_expr=mt.replaceFirst(st);
							input=next_expr;
						}
						  
						else next_expr=input;    //if no match found next_expr= previous st
						 
					final_expr=next_expr;
						 
					}   //end of j for loop
					
				}    //end of if match found
				
				else final_expr=next_expr;
				
				tks.defined_fns.get(i).found_exps.clear();
			}    //end of i for loop
			
		}  // end of if(tks.defined_fns.size()>0)
		
		else final_expr=next_expr;   //if no defined function 
		return final_expr;
	}

}
