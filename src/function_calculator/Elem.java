package function_calculator;

/********************************************************************************************************************
 * Elem file and class ,
 * Defines a message carrying entity between lexer and parser, 
 * Built in lexer and used in parser while building trees as the element identifying a certain tree node.
 * @author Habtegebreil Kassaye Haile
 *
 *********************************************************************************************************************/
public class Elem 
{
	float value;
	char opn;
	boolean is_value;
	
	public Elem(){}
	public Elem(boolean b, char x, float v)
	{
		this.is_value=b;
		this.opn=x;
		this.value=v;
	}
}
