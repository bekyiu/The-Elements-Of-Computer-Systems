package bekyiu;

// 解析VM指令
public class Parser
{
    public static final String C_ARITHMETIC = "C_ARITHMETIC";
    public static final String C_PUSH = "C_PUSH";
    public static final String C_POP = "C_POP";
    public static final String C_LABEL = "C_LABEL";
    public static final String C_GOTO = "C_GOTO";
    public static final String C_IF = "C_IF";
    public static final String C_FUNCTION = "C_FUNCTION";
    public static final String C_RETURN = "C_RETURN";
    public static final String C_CALL = "C_CALL";

    public static final String ARGUMENT = "argument";
    public static final String LOCAL = "local";
    public static final String STATIC = "static";
    public static final String CONSTANT = "constant";
    public static final String THIS = "this";
    public static final String THAT = "that";
    public static final String POINTER = "pointer";
    public static final String TEMP = "temp";


    // 返回当前命令的类型
    public String commandType(String c)
    {
        if (c.startsWith("push"))
        {
            return C_PUSH;
        }
        if (c.startsWith("pop"))
        {
            return C_POP;
        }
        if (c.startsWith("label"))
        {
            return C_LABEL;
        }
        if (c.startsWith("goto"))
        {
            return C_GOTO;
        }
        if (c.startsWith("if-goto"))
        {
            return C_IF;
        }
        if (c.startsWith("function"))
        {
            return C_FUNCTION;
        }
        if (c.startsWith("call"))
        {
            return C_CALL;
        }
        if (c.startsWith("return"))
        {
            return C_RETURN;
        }
        return C_ARITHMETIC;
    }

    // 返回当前命令的第一个参数
    public String arg1(String c)
    {
        String type = commandType(c);
        if (type.equals(Parser.C_RETURN))
        {
            return null;
        }
        if (type.equals(Parser.C_ARITHMETIC))
        {
            return c;
        }
        return c.split(" ")[1];
    }

    // 返回当前命令的第二个参数
    public Integer arg2(String c)
    {
        String type = commandType(c);
        if (type.equals(Parser.C_PUSH) || type.equals(Parser.C_POP)
                || type.equals(Parser.C_FUNCTION) || type.equals(Parser.C_CALL))
        {
            return Integer.valueOf(c.split(" ")[2]);
        }
        return null;
    }

    public static String escapeSpace(String str)
    {
        return str.replaceAll("\\s*", "");
    }

    // 注释可能会跟在语句后面
    public static String escapeComment(String str)
    {
        if (str.contains("//"))
        {
            return str.substring(0, str.indexOf("/"));
        }
        return str;
    }

    // 去掉前后的空格就可以了, 中间的空格便于解析
    public static String filter(String str)
    {
        str = str.trim();
        return escapeComment(str);
    }
}
