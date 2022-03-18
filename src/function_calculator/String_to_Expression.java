package function_calculator;

import java.util.*;
import java.util.regex.*;


public class String_to_Expression 
{
			Operations tasks = new Operations(); //contains an arraylist with defined keywords and sequence(arraylist) of symbols associated with the keyword
	        ArrayList<String> num_seq = new ArrayList<String>();


		public String_to_Expression (Operations tks)
		   {
			tasks=tks;
		   }
		 void numberextractor(String with_comma)//extracts a number(operands between commas) sequence from bracket and comma and number string
			{
				StringTokenizer stk=new StringTokenizer(with_comma,", ",false);
				
				int count=stk.countTokens();
				for(int i=0;i<count;i++)
				{
					num_seq.add(stk.nextToken());
				}
				String temp=num_seq.get(0);
				String[] s=temp.split("(");
				num_seq.set(0,s[0]);
				int sz=num_seq.size();
				temp=num_seq.get(sz-1);
				s=temp.split(")");
				num_seq.set(sz-1,s[0]);
			}
		




		//another substituter---better substituter---takes number sequency and operation with variables, then substitutes variables by numbers
		 
		 public String substituter(String instr, String key)
			{
				numberextractor(instr); // builds the number sequence arraylist----num_seq 
				String opt = new String();
				opt=num_seq.get(0);
				int j=0;
				int keywordindx=tasks.getkeywordindex(key);// this function to be written later it determines the keyword of the function   from left side of equal sign
				int num1 = tasks.defined_fns.get(keywordindx).symb_seq.size();
				Pattern p= Pattern.compile("[+()-*/]");	
				for(int i=0;i<num1;i++)
					{
						//Pattern p= Pattern.compile(t.Token_regx[i]);
						String interm =tasks.defined_fns.get(keywordindx).symb_seq.get(i);
						Matcher m=p.matcher(interm);
						boolean b=m.matches();
						if(b)
						   opt=opt+interm;
						else 
						{
							opt=opt+num_seq.get(j); 
							j++;
						}
							
					}
				return opt;
			}

	}

	
