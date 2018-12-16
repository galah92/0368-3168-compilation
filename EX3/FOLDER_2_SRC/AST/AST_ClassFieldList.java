package AST;
import TYPES.*;
import SymbolTable.*;

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
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, classField.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
	}

	public TYPE_LIST SemantDeclaration() throws Exception
	{
		TYPE classFieldType = classField.SemantDeclaration();
		return new TYPE_LIST(classFieldType, tail != null ? tail.SemantDeclaration() : null);
	}

	public void SemantBody() throws Exception
	{
		classField.SemantBody();
		if (tail != null) tail.SemantBody();
	}

    public TYPE_LIST SemantMe() throws Exception
	{
		TYPE classFieldType = classField.SemantMe();
		return new TYPE_LIST(classFieldType, tail != null ? tail.SemantMe() : null);
	}
}
