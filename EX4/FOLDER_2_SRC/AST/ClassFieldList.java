package AST;
import TYPES.*;
import pcomp.*;
import IR.*;


public class ClassFieldList extends Node
{
    public ClassField head;
    public ClassFieldList tail;

    public ClassFieldList(ClassField head, ClassFieldList tail)
    {
        this.head = head;
        this.tail = tail;
    }

    public void logGraphviz()
    {
        head.logGraphviz();
        if (tail != null) tail.logGraphviz();

        logNode("ClassFieldList\n");
        logEdge(head);
        if (tail != null) logEdge(tail);
    }

    public TypeList SemantDeclaration() throws Exception
    {
        Type headType = head.SemantDeclaration();
        return new TypeList(headType, tail != null ? tail.SemantDeclaration() : null);
    }

    public void SemantBody() throws Exception
    {
        head.SemantBody();
        if (tail != null) tail.SemantBody();
    }

    public TypeList Semant() throws Exception
    {
        Type headType = head.Semant();
        return new TypeList(headType, tail != null ? tail.Semant() : null);
    }

    @Override
    public TempReg toIR()
    {
        if (head != null) head.toIR();
        if (tail != null) tail.toIR();
        return null;
    }

}
