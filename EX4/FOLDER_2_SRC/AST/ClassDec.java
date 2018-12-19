package AST;
import TYPES.*;
import SymbolStack.*;


public class ClassDec extends Dec
{
	public String className;
	public String baseName;
	public ClassFieldList fields;

	public ClassDec(String className, String baseName, ClassFieldList fields)
	{
		this.className = className;
		this.baseName = baseName;
		this.fields = fields;
	}

	public void toGraphviz()
	{
        if (fields != null) fields.toGraphviz();
		logNode(String.format("ClassDec\n%s\n%s", className, baseName));
		logEdge(fields);
	}

    public Type Semant() throws Exception
	{
		if (!SymbolStack.isGlobalScope()) { throw new SemanticException(); }

		TypeClass baseType = null;
		if (baseName != null)
		{
			Type t = SymbolStack.find(baseName);
			if (!(t instanceof TypeClass)) { throw new SemanticException(); }
			baseType = (TypeClass)t;
		}

		if (SymbolStack.find(className) != null) { throw new SemanticException("symbol already defined"); }

		// enter the class Type to that we could field of same Type
		TypeClass classType = new TypeClass(baseType, className, null);
		SymbolStack.enter(className, classType);

		SymbolStack.beginScope(Type.Scope.CLASS);
		while (baseType != null)
		{
			for (TypeList t = baseType.fields; t != null; t = t.tail)
			{
				if (SymbolStack.findInScope(t.head.name) == null)
				{
					SymbolStack.enter(t.head.name, t.head instanceof TypeClassVar ? ((TypeClassVar)t.head).varType : t.head);
				}
			}
			baseType = baseType.base;
		}
		TypeList fieldsTypes = fields.SemantDeclaration();
		classType.fields = fieldsTypes;
		fields.SemantBody();
		SymbolStack.endScope();

		return classType;
	}
}
