package function_calculator;

/******************************************************************************
 * Exceptions file and class ,
 * Contains the different Exception classes included in the program
 * @author Habtegebreil Kassaye Haile
 *
 *******************************************************************************/
class MissingParenthesis extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3086899993228603356L;

	public MissingParenthesis() {}

}

class InvalidOperatorsequence extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3688841883039759069L;

	public InvalidOperatorsequence() {}

}
class InvalidOperands extends Exception{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8860008304940578144L;

	public InvalidOperands() {}

}
class InputNotFound extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 637348911710041732L;

	public InputNotFound() {}

}
