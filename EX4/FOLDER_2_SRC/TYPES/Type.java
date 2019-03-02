package pcomp;


public class Type
{

    public final String name;
    
    public Type(String name) { this.name = name; }

    public static final Type NIL = new Type("nil");
    public static final Type VOID = new Type("void");

    public static class Primitive extends Type
    {
        protected Primitive(String name) { super(name); }
    }

    public static final Primitive INT = new Primitive("int");
    public static final Primitive STRING = new Primitive("string");

    public static class Scope extends Type
    {
        protected Scope(String name) { super(name); }

        public static final Scope CLASS = new Scope("SCOPE-Class");
        public static final Scope FUNC = new Scope("SCOPE-Func");
        public static final Scope IF = new Scope("SCOPE-If");
        public static final Scope WHILE = new Scope("SCOPE-While");
    }
    
}
