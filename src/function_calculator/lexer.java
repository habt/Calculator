package function_calculator;

/*****************************************************************
 * Lexer file and class and CheckedToken class
 * lexer Contains lex() and getnext() methods
 * @author Habtegebreil Kassaye Haile 
 *
 *****************************************************************/

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

	
    /******* Class that defines(lists) the tokens with which the expression is expected to contain ********/

	class CheckedTokens
	{
		public String[] Token_regx = new String[]{"-", "/", "\\*", "\\(","\\)","\\+","(^?[\\,\\d]*\\.?\\d*$)","sin","cos","tan","log","exp","sqrt"};//"(^[+]?[\\,\\d]*\\.?\\d*$)"---error
		public String[] Token_name = new String[] {"SUBTRACT","DIVIDE","MULTIPLY", "OPEN_BR", "CLOSED_BR","ADD","NUMBER","SIN","COS","TAN","LOG","EXP","SQRT" };
		public int[] Token_num ={1,2,3,4,5,6,7,8,9,10,11,12,13};
	};
	

	/***** Provides interfaces to identify the components of the input expression 
	 * and return this identified components to the parser one by one******/
	
	public class lexer 
	{
		ArrayList<Elem> al = new ArrayList<Elem>();
		int count;
		int len;
		int openparcount;
		int closedparcount;
		String initstr=new String();
		boolean isdefn;
		
		public lexer(String inpstr, boolean isd)
		{
			count=0;
			len=0;
			openparcount=0;
			closedparcount=0;
			initstr=inpstr;
			isdefn=isd;
		}
		public void lex()throws InvalidOperatorsequence,InvalidOperands
		{
		    /*****divides input string into array of strings*******/
		    
		   // StringTokenizer stk=new StringTokenizer(ex,"%^+()-*/",true);
		    StringTokenizer stk=new StringTokenizer(initstr,"+()-*/",true);
		    CheckedTokens t = new CheckedTokens();	
			String st=new String();
			int count=stk.countTokens();
			
			/*******Loop to match all elements of the string array with a corresponding allowed token*******/
			for (int j=0;j<count;j++)
			{
				st=stk.nextToken();
				int matchcount=0;
				
			/*******Loop to take one token and check it with all twelve valid tokens*****/
				for(int i=0;i<13;i++)
				{
					Pattern p= Pattern.compile(t.Token_regx[i]);
					Matcher m=p.matcher(st);
					boolean b=m.matches();
					
					/****PUT the matched token in an appropriate element and add to the array list of matched element****/
					
					if((st!="exit")&&(b==true))
					{
						matchcount=1;
						if(t.Token_name[i]=="NUMBER"&&!isdefn)
						{
							Elem e = new Elem();
							if(al.size()!=0)
								 e = al.get(al.size()-1);
							if(e.opn==')')
							{
								al.add(new Elem(false,'*',0));
								al.add(new Elem(true,'\n',new Float(st)));
							}
							else
								al.add(new Elem(true,'\n',new Float(st)));
						}
						else if(t.Token_name[i]=="SUBTRACT")
						{
							Elem e = new Elem();
							if(al.size()!=0)
								 e = al.get(al.size()-1);
							if((e.is_value!=true&&e.opn!=')'&&e.opn!='(')&& j!=0) // recognize - sign at the beginning
								throw new InvalidOperatorsequence();
							else if((e.is_value!=true&&e.opn=='(')&& j!=0)
							{
								al.add(new Elem(true,'\n',0));
								al.add(new Elem(false,'-',0));
								System.out.println("zero added before minus sign");
							}
							else
								al.add(new Elem(false,'-',0));
						}
							
						else if(t.Token_name[i]=="DIVIDE")
						{
							Elem e = al.get(al.size()-1);
							if(e.is_value!=true&&e.opn!=')')
								throw new InvalidOperatorsequence();
							else
								al.add(new Elem(false,'/',0));
						}
							
						else if(t.Token_name[i]=="MULTIPLY")
						{
							Elem e = al.get(al.size()-1);
							if(e.is_value!=true&&e.opn!=')')
								throw new InvalidOperatorsequence();
							else
								al.add(new Elem(false,'*',0));
						}
							
						else if(t.Token_name[i]=="OPEN_BR")
						{
							openparcount++;
							Elem e = new Elem();
							if(al.size()!=0)
								e = al.get(al.size()-1);
							if(e.is_value==true)
							{
								al.add(new Elem(false,'*',0));
								al.add(new Elem(false,'(',0));
							}
								//throw new InvalidOperatorsequence();
							else
								al.add(new Elem(false,'(',0));
						}
							
						else if(t.Token_name[i]=="CLOSED_BR")
						{
							closedparcount++;
							al.add(new Elem(false,')',0));
						}
							
						else if(t.Token_name[i]=="ADD")
						{
							Elem e = new Elem();
							if(al.size()!=0)
								 e = al.get(al.size()-1);
							if((e.is_value!=true&&e.opn!=')')&& j!=0)  //recognize + sign at the beginning
								throw new InvalidOperatorsequence();
							else
								al.add(new Elem(false,'+',0));
						}
						else if(t.Token_name[i]=="SIN")
						{
							al.add(new Elem(false,'s',0));
						}
						else if(t.Token_name[i]=="COS")
						{
							al.add(new Elem(false,'c',0));
						}
						else if(t.Token_name[i]=="TAN")
						{
							al.add(new Elem(false,'t',0));
						}
						else if(t.Token_name[i]=="LOG")
						{
							al.add(new Elem(false,'l',0));
						}
						else if(t.Token_name[i]=="EXP")
						{
							al.add(new Elem(false,'e',0));
						}
						else if(t.Token_name[i]=="SQRT")
						{
							al.add(new Elem(false,'q',0));
						}
					}
						
				}//END of for loop checking a single token with all allowed tokens
			
			 if(matchcount==0)
				throw new InvalidOperands();
				
			}//END of for loop checking for all inputed tokens

		}
		
		/****Function that returns the next Element of the arraylist built above to the part building the tree
		 * @throws MissingParenthesis ***/
		
		public Elem getnext() throws MissingParenthesis
		{
			if(openparcount==closedparcount)
			{
				int len = al.size();
				
				if(count<len)
				{
					count++;
					int idx=count-1;
					Elem el = al.get(idx);
					//System.out.println("returning element "+idx+ " from total of "+ len + "  "+el.opn);
					return el;
				}
					
				else
					return null;
			}
			else throw new MissingParenthesis();
			
			
		}

}
