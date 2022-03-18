
/****************************************************************************************
 * Parser file and class ,
 * Contains the parsing and tree building functions expr(),term() and primary().
 * @author Habtegebreil Kassaye Haile
 
 ****************************************************************************************/

package function_calculator;


public class Parser 
{
	Elem temp_elmt; //Temporary Elem object to refer to the current object obtained from lexer class object
	
	//Variables used to double check parenthesis mismatch
	int openpar=0;
	int closedpar=0;
	
	public Parser(){}
	
	/* ---------------------------------*/ 
    /* Expr() function   
    /* Used to build the tree along with term() and primary() functions.
    /* Returns an error if a parenthesis is missing or multiple valid operators are inputed without proper operand. 
    /* ---------------------------------*/ 

	BTree expr(lexer lex)throws MissingParenthesis, InvalidOperatorsequence
	{
		BTree left = null;
		try 
		{
			left = term(lex);
		}
		catch (MissingParenthesis e)
		{
			throw e;
		}
		catch (InvalidOperatorsequence I)
		{
			throw I;
		}
		
		 /*****Loop to build a + or - subtree and attach it to the left of a tree node 
		  * of a token found to the left of this operators in the input expression *******/
		
		while(temp_elmt!=null&&(temp_elmt.opn=='+'||temp_elmt.opn=='-'))
		{
			BTree treex = new BTree(temp_elmt);
			if(left!=null)
				treex.addLeft(left);
			temp_elmt = lex.getnext();
			if (temp_elmt==null)
			{
				System.out.println("not found + or -");
				throw new InvalidOperatorsequence();
			}
				
			else
			{
				BTree right = term(lex);
				treex.addRight(right);
				left=treex;
			}	
		}
		
		return left;
	}
	
	
	/* ---------------------------------*/ 
    /* Term() function  
    /* Returns an error if a parenthesis is missing or multiple valid operators are inputed without proper operand                            
    /* ---------------------------------*/ 

	BTree term(lexer lex)throws MissingParenthesis, InvalidOperatorsequence
	{
		BTree left = null;
		try 
		{
			left = primary(lex);
		}
		catch (MissingParenthesis e)
		{
			throw e;
		}
		catch (InvalidOperatorsequence I)
		{
			throw I;
		}
		
		/*****Loop to build a * or / subtree and attach it to the left of a tree node 
		  * of a token found to the left of this operators in the input expression *******/
		
		while(temp_elmt!=null&&(temp_elmt.opn=='*'||temp_elmt.opn=='/'))
		{
			BTree treex=new BTree(temp_elmt);
			if(left!=null)
				treex.addLeft(left);
			temp_elmt = lex.getnext();
			if (temp_elmt==null)
				throw new InvalidOperatorsequence();
			else
			{
				BTree right = primary(lex);
				treex.addRight(right);
				left=treex;
			}
			
		}
		
		return(left);
		
	}
	
	
	/* ---------------------------------*/ 
    /* Primary function
    /* Returns an error if a parenthesis is missing or multiple valid operators are inputed without proper operand.                             
    /* ---------------------------------*/ 

	BTree primary(lexer lex)throws MissingParenthesis, InvalidOperatorsequence
	{
		BTree Default = new BTree(new Elem(true,'\n',0));
		Default = null;
		
		/******Checks if the next element is a number then forms a leaf(node) node representing it******/
		
		if(temp_elmt!=null&&temp_elmt.is_value==true)
		{
			BTree treex = new BTree(temp_elmt);
			temp_elmt = lex.getnext(); 
			return (treex);
		}
		
		/******Builds a subtree from a minus operator node and tokens found to the right, then returns this subtree******/
		
		if(temp_elmt!=null&&temp_elmt.opn=='-')
		{
			BTree treex=new BTree(temp_elmt);
			temp_elmt = lex.getnext();
			if (temp_elmt==null)
				throw new InvalidOperatorsequence();
			else
			{
				BTree right = primary(lex);
				treex.addRight(right);
				return(treex);
			}
		}
		
		/******Builds a subtree from the expression between parenthesis and returns this subtree******/
		
		if(temp_elmt!=null&&temp_elmt.opn=='(')
		{
			temp_elmt = lex.getnext();
			openpar++;
			BTree treex = expr(lex);
			if(temp_elmt!=null&&temp_elmt.opn==')'&&openpar!=0)
			{
				temp_elmt = lex.getnext();
				return (treex);
			}
			else 
			{
				throw new MissingParenthesis();
			}
				
		}
		
		
		//part added for optional operations
		/******Builds a subtree with optional operator as top element of the tree, returns this subtree******/
		
		if(temp_elmt!=null&&((temp_elmt.opn=='s')||(temp_elmt.opn=='c')||(temp_elmt.opn=='l')||(temp_elmt.opn=='t')||(temp_elmt.opn=='e')||(temp_elmt.opn=='q')))
		{
			BTree treex=new BTree(temp_elmt);
			temp_elmt = lex.getnext();
			if (temp_elmt!=null)
			{
				BTree right = primary(lex);
				treex.addRight(right);
				return(treex);
			}
			else
			{
				
				throw new InvalidOperatorsequence();
			}
		}
		
		else 
		{
			return Default;
		}	
	}

}
