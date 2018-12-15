package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_ClassFieldList extends AST_Node
{
	public AST_ClassField classField;
	public AST_ClassFieldList tail;

	public AST_ClassFieldList(AST_ClassField classField, AST_ClassFieldList tail)
	{
		this.classField = classField;
		this.tail = tail;
	}

	public void PrintMe()
	{
        classField.PrintMe();
		if (tail != null) tail.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "ClassFieldList\n");
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, classField.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
	}

    public TYPE_LIST SemantMe() throws Exception
	{
        return null; // TODO: I guess now most of the work is here...
	}
}
