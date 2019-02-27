package AST;

import pcomp.*;


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

    public void logGraphviz()
    {
        if (fields != null) fields.logGraphviz();
        logNode(String.format("ClassDec\n%s\n%s", className, baseName));
        logEdge(fields);
    }

    public Type Semant() throws Exception
    {
        if (!SymbolTable.isGlobalScope()) { throw new SemanticException(); }

        TypeClass baseType = null;
        if (baseName != null)
        {
            Type t = SymbolTable.find(baseName);
            if (!(t instanceof TypeClass)) { throw new SemanticException(); }
            baseType = (TypeClass)t;
        }

        if (SymbolTable.find(className) != null) { throw new SemanticException("symbol already defined"); }

        // enter the class Type to that we could field of same Type
        TypeClass classType = new TypeClass(baseType, className, null);
        SymbolTable.enter(className, classType);

        SymbolTable.beginScope(Type.Scope.CLASS);
        while (baseType != null)
        {
            for (TypeList t = baseType.fields; t != null; t = t.tail)
            {
                if (SymbolTable.findInScope(t.head.name) == null)
                {
                    SymbolTable.enter(t.head.name, t.head instanceof TypeClassMember ? ((TypeClassMember)t.head).varType : t.head);
                }
            }
            baseType = baseType.base;
        }
        TypeList fieldsTypes = fields.SemantDeclaration();
        classType.fields = fieldsTypes;
        fields.SemantBody();
        SymbolTable.endScope();

        return classType;
    }

    @Override
    public TempReg toIR()
    {
        // TODO: implement
        return null;
    }

}
