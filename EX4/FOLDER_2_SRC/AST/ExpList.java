package AST;

import pcomp.*;


public class ExpList extends Node
{
    public Exp head;
    public ExpList tail;

    public ExpList(Exp head, ExpList tail)
    {
        this.head = head;
        this.tail = tail;
    }
    
    public void logGraphviz()
    {
        if (head != null) head.logGraphviz();
        if (tail != null) tail.logGraphviz();
        logNode("ExpList");
        if (head != null) logEdge(head);
        if (tail != null) logEdge(tail);
    }

    public TypeList Semant() throws Exception
    {
        return new TypeList(head.Semant(), tail != null ? tail.Semant() : null);
    }

    @Override
    public IRReg toIR()
    {
        if (head != null) head.toIR();
        if (tail != null) tail.toIR();
        return null;
    }

}
