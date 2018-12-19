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

		logNode("ClassFieldList\n");
        logEdge(classField);
		if (tail != null) logEdge(tail);
	}

	public TypeList SemantDeclaration() throws Exception
	{
		Type classFieldType = classField.SemantDeclaration();
		return new TypeList(classFieldType, tail != null ? tail.SemantDeclaration() : null);
	}

	public void SemantBody() throws Exception
	{
		classField.SemantBody();
		if (tail != null) tail.SemantBody();
	}

    public TypeList Semant() throws Exception
	{
		Type classFieldType = classField.Semant();
		return new TypeList(classFieldType, tail != null ? tail.Semant() : null);
	}
}
