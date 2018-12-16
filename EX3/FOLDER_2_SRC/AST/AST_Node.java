package AST;
import TYPES.*;

public abstract class AST_Node
{
	private static int instanceCount = 0;
	public final int SerialNumber = instanceCount++;
	public int lineNumber;
	
	public abstract void PrintMe();

	public abstract Type SemantMe() throws Exception;

	public class SemanticException extends Exception
	{
		public SemanticException() { super(); }
		public SemanticException(String message) { super(message); }

		public int getLineNumber() { return lineNumber + 1; }
	}
}
