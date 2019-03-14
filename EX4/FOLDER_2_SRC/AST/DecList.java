package AST;

import pcomp.*;


public class DecList extends Node
{
    public Dec head;
    public DecList tail;

    public DecList(Dec head, DecList tail)
    {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public void logGraphviz()
    {
        head.logGraphviz();
        if (tail != null) tail.logGraphviz();
        logNode("DecList");
        logEdge(head);
        if (tail != null) logEdge(tail);
    }

    @Override
    public TypeList Semant() throws Exception
    {
        return new TypeList(head.Semant(), tail != null ? tail.Semant() : null);
    }

    @Override
    public IRReg toIR()
    {
        head.toIR();
        if (tail != null) tail.toIR();
        return null;
    }

}
