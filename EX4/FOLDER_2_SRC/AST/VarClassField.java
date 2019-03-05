package AST;

import pcomp.*;


public class VarClassField extends Var
{

    public Var instanceName;
    public String fieldName;
    
    public VarClassField(Var instanceName, String fieldName)
    {
        this.instanceName = instanceName;
        this.fieldName = fieldName;
    }

    public void logGraphviz()
    {
        instanceName.logGraphviz();
        logNode(String.format("VarClassField\n%s", fieldName));
        logEdge(instanceName);
    }

    public Type Semant() throws Exception
    {
        Type t = instanceName.Semant();
        if (!(t instanceof TypeClass)) { throw new SemanticException(); }
        TypeClass typeClass = (TypeClass)t;
        return typeClass.getMember(fieldName);
    }

    @Override
    public IRReg toIR()
    {
		// TODO: implement
		return null;
    }
    
}
