package AST;
import TYPES.*;
import SymbolTable.*;

public abstract class AST_ClassField extends AST_Dec
{
    public abstract Type SemantDeclaration() throws Exception;

	public abstract void SemantBody() throws Exception;
}
