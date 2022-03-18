package function_calculator;

/************************************************************************************************************
 * Evaluator Class
 * Contains evaluate() method
 * Purpose of Evaluator function - computes the output of the mathematical Expression represented by the tree.
 * @authors  Habtegebreil Kassaye Haile
 *
 ***********************************************************************************************************/

public class Evaluator 
{
	 /******This method will be called repetitively from with in itself 
	  until final answer is calculated********/
	float evaluate(BTree treex) throws InputNotFound
    {
    	float result=0;
    	if(treex==null)
    		throw new InputNotFound();
    	if(treex.root!=null&&treex.root.elem.is_value)
        		result = treex.root.elem.value;          //returns the number value when a number tree node is reached
    	
    	 /******When an operator node is found it will recursively go through the the tree 
    	  * until a number node is reached then evaluates and returns the value to an upper 
    	  * tree operator node until final answer is calculated********/
    	
    	
    	else if(treex.root!=null)
    	{
    		float left, right;
    		char opert;
    		opert = treex.root.elem.opn;
    		
    		left = evaluate(treex.getLeftSubtree());
    		right = evaluate(treex.getRightSubtree());
    		
    		
    		switch(opert)
    		{
    		case '-': result = left-right;break;
    		case '*': result = left*right;break;
    		case '/': result = left/right;break;
    		case '+': result = left+right;break;
    		case 's': result = (float) Math.sin(Math.toRadians((double)right));break;
    		case 'c': result = (float) Math.cos(Math.toRadians((double)right));break;
    		case 't': result = (float) Math.tan(Math.toRadians((double)right));break;
    		case 'l': result = (float) Math.log((double)right);break;
    		case 'e': result = (float) Math.exp((double)right);break;
    		case 'q': result = (float) Math.sqrt((double)right);break;
    		}
    	}
    	else return result;
    	return result;
    }

}
