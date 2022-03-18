package function_calculator;

/*****************************************************************
 * MainClass file and class ,
 * Contains the main function of the program 
 * @author Habtegebreil Kassaye Haile
 *
 *****************************************************************/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainClass 
{
	public static void main(String[] args)
	{
		//float answer;
		
		String expr = null;
		Classifier cls = new Classifier();
		Operations def_fns=new Operations();
		
		while(true)
		{
	    	System.out.print("Enter the Expression : ");
	    	System.out.println("\n");
	    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    	 try
	    	 {
		         expr = br.readLine();
		     } 
	    	 catch (IOException ioe) 
	    	 {
		         System.out.println("IO error in Input!");
		         System.exit(1);
		      }
	    	 
	    	 /*********************************************
		     Part to remove white space in the code
		     ********************************************/
	    	
	    String input_expr = cls.Process_expression(expr, def_fns);
	    
	   
	    if(input_expr!=null) 
	       {	
	    		String[] splt2 = input_expr.split("\\s");
	    	    int val = splt2.length;
			    String ex=new String();
			    for(int k=0;k<val;k++)
			    	ex=ex+splt2[k];
			    expr=ex;
	    	try
			{
				
					lexer lex = new lexer(expr,false);
					try {
						lex.lex();
					} catch (InvalidOperands Iv) {
						System.err.println("\nError : Invalid Operands/Operators input in Expression !!\n");
					}
					ParsenEval pev = new ParsenEval(lex);
					float ans = pev.do_eval();
					System.out.println("\n  ans >> "+ ans+"\n");
				
			}
			catch (InvalidOperatorsequence IS)
			{
				System.err.println("\nError : Missing Operand/Operator in Expression !!\n");
			}
		
	       }//end of if
	    
		}//end of while
				
	}//end of main


}//end of class
