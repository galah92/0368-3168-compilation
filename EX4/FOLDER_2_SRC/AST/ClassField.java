package AST;
import TYPES.*;
import SymbolStack.*;


public abstract class ClassField extends Dec
{
    public abstract Type SemantDeclaration() throws Exception;

	public abstract void SemantBody() throws Exception;
}
