package AST;
import TYPES.*;
import pcomp.*;


public abstract class ClassField extends Dec
{
    public abstract Type SemantDeclaration() throws Exception;

	public abstract void SemantBody() throws Exception;
}
