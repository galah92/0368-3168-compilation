package AST;

import pcomp.*;


public class ClassDec extends Dec
{

    public String className;
    public String baseName;
    public ClassFieldList fields;
    public TypeClass classType;

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

        classType = new TypeClass(baseType, className);
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
        for (ClassFieldList it = fields; it != null; it = it.tail) { it.head.toIR(); }
        
        int numMethods = classType.methods.size();
        if (numMethods > 0)  // create vtable
        {
            String initLabel = IR.uniqueLabel("init_" + className + "_vtable");
            IR.globalVars.add(initLabel);
            IR.add(new IR.label(initLabel));

            // allocate vtable
            String vtableLabel = String.format("_%s_vtable", className);
            IR.add(new IR.declareData(vtableLabel, numMethods * 4));  // allocate vtable
            IRReg vtable = new IRReg.TempReg();
            IR.add(new IR.la(vtable, vtableLabel));  // get vtable address
            IRReg tmpReg = new IRReg.TempReg();
            for (int i = 0; i < numMethods; i++)
            {
                String methodLabel = ((TypeFunc)classType.methods.get(i).type).fullname;
                IR.add(new IR.la(tmpReg, "_" + methodLabel));  // get method address
                IR.add(new IR.sw(tmpReg, vtable, i * 4));  // store in vtable
            }    
            IR.add(new IR.jr(IRReg.ra));
        }
        return null;
    }

}
