package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public abstract class AST_ClassField extends AST_Dec
{
    public abstract TYPE SemantDeclaration() throws Exception;

	public abstract void SemantBody() throws Exception;
}
