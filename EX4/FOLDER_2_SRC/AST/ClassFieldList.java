package AST;

import pcomp.*;



public class ClassFieldList extends Node
{
    public ClassField value;
    public ClassFieldList next;

    public ClassFieldList(ClassField value, ClassFieldList next)
    {
        this.value = value;
        this.next = next;
    }

    public void logGraphviz()
    {
        value.logGraphviz();
        if (next != null) next.logGraphviz();

        logNode("ClassFieldList\n");
        logEdge(value);
        if (next != null) logEdge(next);
    }

    public TypeList SemantDeclaration() throws Exception
    {
        Type valueType = value.SemantDeclaration();
        return new TypeList(valueType, next != null ? next.SemantDeclaration() : null);
    }

    public void SemantBody() throws Exception
    {
        value.SemantBody();
        if (next != null) next.SemantBody();
    }

    public TypeList Semant() throws Exception
    {
        Type valueType = value.Semant();
        return new TypeList(valueType, next != null ? next.Semant() : null);
    }

    @Override
    public TempReg toIR()
    {
        if (value != null) value.toIR();
        if (next != null) next.toIR();
        return null;
    }

}
