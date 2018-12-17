package TYPES;

public class Type
{
	public String name;

	public Type() {}

	public Type(String name)
	{
		this.name = name;
	}

	public static final Type SCOPE = new Type("SCOPE-BOUNDARY");

	// priminite types
	public static final Type INT = new Type("int");
	public static final Type STRING = new Type("string");

	// TODO: this is BAD! there is no such type, must fix
	public static final Type VOID = new Type("void");
}
