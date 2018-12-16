package AST;
import TYPES.*;

public abstract class AST_Node
{
	public int SerialNumber = AST_Node_Serial_Number.getFresh();

	public int lineNumber;
	
	public abstract void PrintMe();

	public abstract TYPE SemantMe() throws Exception;

	public class SemanticException extends Exception
	{
		public int lineNumber;

		public SemanticException(int lineNumber)
		{
			this.lineNumber = lineNumber;
		}
	}
}
