public interface TokenNames {

  public static final int LPAREN = 0;
  public static final int RPAREN = 1;
  public static final int LBRACK = 2;
  public static final int RBRACK = 3;
  public static final int LBRACE = 4;
  public static final int RBRACE = 5;
  public static final int NIL = 6;
  public static final int PLUS = 7;
  public static final int MINUS = 8;
  public static final int TIMES = 9;
  public static final int DIVIDE = 10;
  public static final int COMMA = 11;
  public static final int DOT = 12;
  public static final int SEMICOLON = 13;
  public static final int ASSIGN = 14;
  public static final int EQ = 15;
  public static final int LT = 16;
  public static final int GT = 17;
  public static final int ARRAY = 18;
  public static final int CLASS = 19;
  public static final int EXTENDS = 20;
  public static final int RETURN = 21;
  public static final int WHILE = 22;
  public static final int IF = 23;
  public static final int NEW = 24;
  public static final int INT = 25;
  public static final int STRING = 26;
  public static final int ID = 27;
  public static final int COMMENT = 28;
  public static final int EOF = 99;

  public static String toString(int token) {
    switch (token) {
        case LPAREN: return "LPAREN";
        case RPAREN: return "RPAREN";
        case LBRACK: return "LBRACK";
        case RBRACK: return "RBRACK";
        case LBRACE: return "LBRACE";
        case RBRACE: return "RBRACE";
        case NIL: return "NIL";
        case PLUS: return "PLUS";
        case MINUS: return "MINUS";
        case TIMES: return "TIMES";
        case DIVIDE: return "DIVIDE";
        case COMMA: return "COMMA";
        case DOT: return "DOT";
        case SEMICOLON: return "SEMICOLON";
        case ASSIGN: return "ASSIGN";
        case EQ: return "EQ";
        case LT: return "LT";
        case GT: return "GT";
        case ARRAY: return "ARRAY";
        case CLASS: return "CLASS";
        case EXTENDS: return "EXTENDS";
        case RETURN: return "RETURN";
        case WHILE: return "WHILE";
        case IF: return "IF";
        case NEW: return "NEW";
        case INT: return "INT";
        case STRING: return "STRING";
        case ID: return "ID";
        case COMMENT: return "COMMENT";
        case EOF: return "EOF";
        default: return "ERROR";
    }
  }

}

