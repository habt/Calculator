package function_calculator;

import java.util.*;
import java.io.*;

//An alternative way to the Functions class
//here we must also produce and save a regular expression that matches the defined function eg. add(x,y) -- "(add\\((.+),(.+)\\))
//i.e  keword\\((.+),(.+)\\))
public class Operations 
{
		ArrayList<Elem_function> defined_fns = new ArrayList<Elem_function>();
		boolean loaded=false;


		// part that takes the whole def line except the the def and saves the right side of equal sign as an arraylist of symbols
		public void define(String defnxtstr)
			{
				//step 1
				String[] splt = defnxtstr.split("=");// right side tree build, left side to get keyword 
				String[] splt2 = splt[0].split("\\(",2); //to get the word before open parenthesis in the expression to the left of = sign
				String[] splt3= splt2[1].split("\\)",2);// to get just x,y...removing the closed parenthesis
				String keyword=splt2[0];

				String[] variables = splt3[0].split(",");
				int var_count=variables.length;
				String reg="("+keyword+"\\(";
				for(int i=0;i<var_count-1;i++)
					reg=reg+"(.+),";
				reg=reg+"(.+)\\))";
					
				//int comma_num=var_count-1;

			
				//step 2---right side of = sign
				StringTokenizer stk2=new StringTokenizer(splt[1],"+()-*/",true);//donot need to divide it save it as it is and later just replace the variables in the string
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
				temp_def_fn.var_exp="("+splt[1]+")";
				temp_def_fn.regex=reg;
				for(int i=0;i<var_count;i++)temp_def_fn.variables.add(variables[i]);
				defined_fns.add(temp_def_fn);
				 System.out.println("function "+defined_fns.get(defined_fns.size()-1).key+ "  defined");
				 System.out.println("with variable count  "+defined_fns.get(defined_fns.size()-1).variable_count);
				 System.out.println("with operation sequence  "+defined_fns.get(defined_fns.size()-1).symb_seq);
				 System.out.println("with operation string  "+defined_fns.get(defined_fns.size()-1).var_exp );
				 System.out.println("with variables  "+defined_fns.get(defined_fns.size()-1).variables);
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
		
		public void load() throws IOException
		{
			if(!loaded)
			{
				loaded=true;
				BufferedReader in = new BufferedReader(
						new FileReader("IODemo.out"));
						String s = new String();
						//Elem_function temp_def_fn=new Elem_function();
						while((s = in.readLine())!= null)
						{
							Elem_function temp_def_fn=new Elem_function();
							temp_def_fn.key=s;
							//for(int i=0;i<4;i++)
							temp_def_fn.variable_count=Integer.parseInt(in.readLine());
							//temp_def_fn.symb_seq=in.readLine();
							temp_def_fn.var_exp=in.readLine();
							temp_def_fn.regex=in.readLine();
							for(int j=0;j<temp_def_fn.variable_count;j++)temp_def_fn.variables.add(in.readLine());
							defined_fns.add(temp_def_fn);
							System.out.println(defined_fns.get(defined_fns.size()-1).key + " function key loaded");
							System.out.println(defined_fns.get(defined_fns.size()-1).variable_count + " function var no is");
							System.out.println(defined_fns.get(defined_fns.size()-1).var_exp + " function expression loaded");
							System.out.println(defined_fns.get(defined_fns.size()-1).regex + " function regular expression loaded");
							System.out.println(defined_fns.get(defined_fns.size()-1).variables + " function variables loaded");
							//temp_def_fn.variables.clear();
							System.out.println();
						}
						
						in.close();
			}
			
		}
		
		
		public void delete(String defnxtstr) throws IOException
		{
			if(!loaded) load();
			for(int i=0;i<defined_fns.size();i++)
			{
				if(defined_fns.get(i).key.equals(defnxtstr))
					defined_fns.remove(i);
			}
		}
		
		public void save() throws IOException
		{
			load();
			try {
				
				PrintWriter out1 = new PrintWriter(
				new BufferedWriter(new FileWriter("IODemo.out")));
				//while((s = in4.readLine()) != null )
				for(int i=0;i<defined_fns.size();i++)
				{
					out1.println(defined_fns.get(i).key);
					out1.println(defined_fns.get(i).variable_count);
					out1.println(defined_fns.get(i).var_exp);
					out1.println(defined_fns.get(i).regex);
					for(int j=0;j<defined_fns.get(i).variable_count;j++)out1.println(defined_fns.get(i).variables.get(j));
					//for(int j=0;j<defined_fns.get(i).variables.size();j++)out1.println(defined_fns.get(i).symb_seq.get(j));
				}
				
				out1.close();
				} catch(EOFException e) {
				System.err.println("End of stream");
				}
		}
		
		public void show() 
		{
			
				for(int i=0;i<defined_fns.size();i++)
				{
					System.out.println(defined_fns.get(i).key);
					System.out.println(defined_fns.get(i).variable_count);
					System.out.println(defined_fns.get(i).var_exp);
					System.out.println(defined_fns.get(i).regex);
					//for(int j=0;j<defined_fns.get(i).variable_count;j++)
						System.out.println(defined_fns.get(i).variables);
					//for(int j=0;j<defined_fns.get(i).variables.size();j++)out1.println(defined_fns.get(i).symb_seq.get(j));
						System.out.println();
				}
				
				
		}
	}



