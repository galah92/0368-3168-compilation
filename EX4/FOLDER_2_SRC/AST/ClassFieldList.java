package AST;
import TYPES.*;
import SymbolStack.*;


public class ClassFieldList extends Node
{
	public ClassField classField;
	public ClassFieldList tail;

	public ClassFieldList(ClassField classField, ClassFieldList tail)
	{
		this.classField = classField;
		this.tail = tail;
	}

	public void logGraphviz()
	{
        classField.logGraphviz();
		if (tail != null) tail.logGraphviz();

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
