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

    public TYPE SemantMe() throws Exception
	{
		if (!SymbolTable.getInstance().isGlobalScope()) { throw new SemanticException(); }

		TYPE_CLASS baseType = null;
		if (baseName != null)
		{
			TYPE t = SymbolTable.getInstance().find(baseName);
			if (!(t instanceof TYPE_CLASS)) { throw new SemanticException(); }
			baseType = (TYPE_CLASS)t;
		}

		// enter the class type to that we could field of same type
		TYPE_CLASS classType = new TYPE_CLASS(baseType, className, null);
		SymbolTable.getInstance().enter(className, classType);

		SymbolTable.getInstance().beginScope();
		TYPE_LIST fieldsTypes = fields.SemantDeclaration();
		if (baseType != null)
		{
			for (TYPE_LIST t = baseType.fields; t != null; t = t.tail)
			{
				if (SymbolTable.getInstance().findInScope(t.head.name) == null)
				{
					SymbolTable.getInstance().enter(t.head.name, t.head instanceof TYPE_CLASS_VAR_DEC ? ((TYPE_CLASS_VAR_DEC)t.head).varType : t.head);
				}
			}
		}
		fields.SemantBody();
		SymbolTable.getInstance().endScope();

		classType.fields = fieldsTypes;
		return classType;
	}
}
