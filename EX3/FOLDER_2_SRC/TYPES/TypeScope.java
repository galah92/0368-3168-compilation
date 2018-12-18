package TYPES;

public class TypeScope extends Type
{
    protected TypeScope(String name) { this.name = name; }

    public static final TypeScope CLASS = new TypeScope("SCOPE-Class");
    public static final TypeScope FUNC = new TypeScope("SCOPE-Func");
    public static final TypeScope IF = new TypeScope("SCOPE-If");
    public static final TypeScope WHILE = new TypeScope("SCOPE-While");
}