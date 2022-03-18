package function_calculator;
import java.util.*;

public class Functions 
{
	ArrayList<Elem_function> defined_fns = new ArrayList<Elem_function>();


	// part that takes the whole def line except the the def and saves the right side of equal sign as an arraylist of symbols
	public void define(String defnxtstr)
		{
			//step 1
			String[] splt = defnxtstr.split("=");// right side tree build, left side to get keyword 
			String[] splt2 = splt[0].split("\\(",2); //to get the word before open parenthesis in the expression to the left of = sign
			String keyword=splt2[0];

			StringTokenizer stk=new StringTokenizer(splt2[1],", ",false);
			int var_count=stk.countTokens();
			//int comma_num=var_count-1;

		
			//step 2---right side of = sign
			StringTokenizer stk2=new StringTokenizer(splt[1],"+()-*/",true);
			ArrayList<String> opn_seq = new ArrayList<String>();
			String st= new String();
			//int count=stk2.countTokens();
			
			//for(int i=0;i<count;i++)
			while(stk2.hasMoreTokens())
			{
					st=stk2.nextToken();	
					opn_seq.add(st);	
			}
			
			
			Elem_function temp_def_fn=new Elem_function();
			temp_def_fn.key=keyword;
			temp_def_fn.variable_count=var_count;
			temp_def_fn.symb_seq=opn_seq;
			defined_fns.add(temp_def_fn);
			 System.out.println("function "+defined_fns.get(defined_fns.size()-1).key+ "  defined");
			 System.out.println("with variable count  "+defined_fns.get(defined_fns.size()-1).variable_count);
			 System.out.println("with operation sequence  "+defined_fns.get(defined_fns.size()-1).symb_seq);
		}
	

	//part that takes the key and returns the index of the prototype expression in the arraylist---e-example takes the key word "add" and returs the index this is found in the defined_fns array list
	public int getkeywordindex(String key)
		{
			int sze = defined_fns.size();
			int idx= 5000;//random high number
			for(int i=0;i<sze;i++)
				{
					if(defined_fns.get(i).key.equals(key))
						idx=i;

				}
			return idx; //returns the index of the matched key
		}
	
	public void load(String defnxtstr)
	{
		
	}
	
	
	public void delete(String defnxtstr)
	{
		
	}

}
