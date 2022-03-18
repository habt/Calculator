package function_calculator;

//import java.util.*;
import java.util.regex.*;

public class Identify 
{
	
	//Functions tsks = new Functions();
	String final_expr = new String();
	String inp_expr = new String();
	String regex_str = new String();
	String proposed_key=new String();;
	public Identify(String str)
	{
		inp_expr= str; //inp_expr will be the whole mathematical expression containing defined functions
		//tsks=tsk_obj;
	}


	public String replacer(Operations tks)
		{
			System.out.println("begining of replacer "+inp_expr);
			if(tks.defined_fns.size()>0)
			{
			for(int i=0;i<tks.defined_fns.size();i++)//checks for the existence of all defined functions in the input string
			{
				//String[] st=str.split(tsks.defined_fns.key)
				//String regex_str = new String();
				proposed_key=tks.defined_fns.get(i).key;
				Pattern p1= Pattern.compile(proposed_key);
				Matcher m1=p1.matcher(inp_expr);
				if(m1.matches())  // keyword found
				{
					for(int j=0;j<tks.defined_fns.get(i).variable_count-1;i++) // number of commas is equal to number of variables minus one
						{
							regex_str=regex_str+"(.+),";
						}
		
					regex_str =proposed_key + "\\(" +regex_str + "(.+)\\)";// the double backlash to express that it is not a grouping bracket
					Pattern p2= Pattern.compile(regex_str);
					Matcher m2=p2.matcher(inp_expr);
					String found_exp = m2.group(); // this is the expression like (3,6,sin(45))----this will be sent to other functions
					String_to_Expression ste = new String_to_Expression(tks);
					//final_exp = ste.substituter(found,inter_key);
					inp_expr=m2.replaceFirst(ste.substituter(found_exp,proposed_key));//substituter returns (3+6+sin45)----and replace first replaces
					System.out.println("reached replacer() ");
				}
				
			}
			}
			return inp_expr;
		}
}
