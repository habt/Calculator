package function_calculator;

public class ParsenEval 
{
	
	lexer lexelem;
	public ParsenEval(lexer lex)
	{
		lexelem=lex;
	}
	
	public float do_eval() throws InvalidOperatorsequence
	{
		Float answer = (float) 0;
		Parser parse = new Parser();
		BTree ZTree = null;
		Evaluator Ev = new Evaluator();
		try
		{
			Elem temp_elmt=lexelem.getnext();
			parse.temp_elmt=temp_elmt;
			ZTree = parse.expr(lexelem);
			try
			{
				answer =Ev.evaluate(ZTree);
				//System.out.println("\n  ans >> "+ answer+"\n");
			}
			catch (InputNotFound NF)
			{
				System.err.println("\nError : No Input Found !!\n");
			}
			
		}
		catch (MissingParenthesis MS )
		{
			System.err.println("\nError : Please check if opened and closed brackets are equal !!\n");
		}
		return answer;
	}
	

}

