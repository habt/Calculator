package function_calculator;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Habtegebreil massage Haile
 *this class has method to
  1.to identify what kind of expression it is--def, load, del or expression with defined functions or normal simple addition
  2. depending on the above identified condition it calls the next function or returns the case to main so that main can do the calling
 */
//

//this will be called in the main

public class Classifier 
{
	public String Process_expression(String input_expr,Operations tks)
	{
		String[] splt = input_expr.split("\\s",2);
		if(splt[0].equals("def"))
			{tks.define(splt[1]);return null;}
		
		
		else if(splt[0].equals("load"))
			{try {
				tks.load();
			} catch (IOException e) {
				 System.err.println("IO error in Input!");
		         //System.exit(1);
			}return null;}
		
		
		else if(splt[0].equals("del"))
			{try {
				tks.delete(splt[1]);
			} catch (IOException e) {
				 System.err.println("IO error in Input!");
			}return null;}
		
		
		else if(splt[0].equals("save"))
		{try {
			tks.save();
		} catch (IOException e) {
			System.err.println("IO error in Input!");
			//e.printStackTrace();
		}return null;}
		
		else if(splt[0].equals("show"))
		{tks.show();return null;}
		
		else
		{
			String returned = input_expr;
			boolean matchfnd = true;
			while(matchfnd)
			{
				matchfnd = false;
				for(int i=0;i<tks.defined_fns.size();i++)
				{
					Pattern p = Pattern.compile(tks.defined_fns.get(i).regex);
					Matcher m = p.matcher(input_expr);
					matchfnd = m.find();
					if(matchfnd) break;
				}
					if (matchfnd)
					{
						try {
							Substituter sub = new Substituter(input_expr);
							returned = sub.replacer(tks,input_expr);
						} catch (InvalidOperatorsequence IS)
						{
							System.err.println("\nError : Missing Operand/Operator in Expression !!\n");
						}
						input_expr=returned;
					}
				
				
				//if(!matchfnd) break;
			}
			
			return returned;
		}
	}

}
