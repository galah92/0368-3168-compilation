package AST;
import TYPES.*;
import SymbolTable.*;

public class AST_ClassDec extends AST_Dec
{
	public String className;
	public String baseName;
	public AST_ClassFieldList fields;

	public AST_ClassDec(String className, String baseName, AST_ClassFieldList fields)
	{
		this.className = className;
		this.baseName = baseName;
		this.fields = fields;
	}

	public void PrintMe()
	{
        if (fields != null) fields.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ClassDec\n%s\n%s", className, baseName));
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, fields.SerialNumber);
	}

    public Type SemantMe() throws Exception
	{
		if (!SymbolTable.getInstance().isGlobalScope()) { throw new SemanticException(); }

		TypeClass baseType = null;
		if (baseName != null)
		{
			Type t = SymbolTable.getInstance().find(baseName);
			if (!(t instanceof TypeClass)) { throw new SemanticException(); }
			baseType = (TypeClass)t;
		}

		// enter the class Type to that we could field of same Type
		TypeClass classType = new TypeClass(baseType, className, null);
		SymbolTable.getInstance().enter(className, classType);

		SymbolTable.getInstance().beginScope();
		TypeList fieldsTypes = fields.SemantDeclaration();
		if (baseType != null)
		{
			for (TypeList t = baseType.fields; t != null; t = t.tail)
			{
				if (SymbolTable.getInstance().findInScope(t.head.name) == null)
				{
					SymbolTable.getInstance().enter(t.head.name, t.head instanceof TypeClassVar ? ((TypeClassVar)t.head).varType : t.head);
				}
			}
		}
		fields.SemantBody();
		SymbolTable.getInstance().endScope();

		classType.fields = fieldsTypes;
		return classType;
	}
}
