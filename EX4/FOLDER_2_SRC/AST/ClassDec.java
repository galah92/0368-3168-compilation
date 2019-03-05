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

    @Override
    public void logGraphviz()
    {
        fields.logGraphviz();
        logNode(String.format("ClassDec\n%s\n%s", className, baseName));
        logEdge(fields);
    }

    @Override
    public Type Semant() throws Exception
    {
        if (!SymbolTable.isGlobalScope()) { throw new SemanticException("class definition must be at global scope"); }

        TypeClass baseType = null;
        if (baseName != null)
        {
            Type t = SymbolTable.find(baseName);
            if (!(t instanceof TypeClass)) { throw new SemanticException("symbol is not of type class: " + baseName); }
            baseType = (TypeClass)t;
        }

        if (SymbolTable.find(className) != null) { throw new SemanticException("symbol already defined"); }

        TypeClass classType = new TypeClass(baseType);
        SymbolTable.enter(className, classType);

        SymbolTable.beginScope(Type.Scope.CLASS);
        for (Symbol symbol : classType.members) { SymbolTable.enter(symbol.name, symbol.type); }
        
        fields.SemantDeclaration();
        fields.SemantBody();
        SymbolTable.endScope();

        return classType;
    }

    @Override
    public IRReg toIR()
    {
        // TODO: implement
        return null;
    }

}
