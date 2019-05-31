package bekyiu;

// 将VM指令翻译成Hack汇编
public class CodeWriter
{
    // 注意, 同一个.vm文件中可能会用到多个 eq
    // 这样如果翻译成汇编之后, 标号会重复, 所以要加以区分
    private static Integer inc = 0;
    private Parser parser = new Parser();

    public String writeArithmetic(String c)
    {
        String hack = null;
        if (c.equals("add"))
        {
            hack = arithmeticTemplate(c, "D = M + D");
        }
        else if (c.equals("sub"))
        {
            hack = arithmeticTemplate(c, "D = M - D");
        }
        else if (c.equals("neg"))
        {
            hack = arithmeticTemplate(c, "D = D + 1");
        }
        else if (c.equals("eq") || c.equals("gt") || c.equals("lt"))
        {
            hack = arithmeticTemplate(c, "");
        }
        else if (c.equals("and"))
        {
            //x & y
            hack = arithmeticTemplate(c, "D = M & D");
        }
        else if (c.equals("or"))
        {
            hack = arithmeticTemplate(c, "D = M | D");
        }
        else if (c.equals("not"))
        {
            hack = arithmeticTemplate(c, "");
        }
        return hack;
    }

    public String writePushPop(String c)
    {
        String arg1 = parser.arg1(c);
        Integer arg2 = parser.arg2(c);
        return pushPopTemplate(parser.commandType(c), arg1, arg2);
    }

    // push or pop
    private String pushPopTemplate(String type, String arg1, Integer arg2)
    {
        if(type.equals(Parser.C_PUSH))
        {
            if(arg1.equals("constant"))
            {
                return "@" + arg2 + "\n" +
                        "D = A\n" +
                        "@SP\n" +
                        "A = M\n" +
                        "M = D\n" +
                        "@SP\n" +
                        "M = M + 1\n";
            }
        }
        else
        {

        }
        return null;
    }

    private String arithmeticTemplate(String c, String replace)
    {
        // D = M - D; x - y
        if (c.equals("add") || c.equals("sub") || c.equals("and") || c.equals("or"))
        {
            return "@SP\n" +
                    "M = M - 1\n" +
                    "A = M\n" +
                    "D = M\n" +
                    "@SP\n" +
                    "M = M - 1\n" +
                    "A = M\n" +
                    replace + "\n" +
                    "M = D\n" +
                    "@SP\n" +
                    "M = M + 1\n";
        }
        if (c.equals("eq") || c.equals("gt") || c.equals("lt"))
        {
            //a-instruction
            String jmpA = "@" + c.toUpperCase() + CodeWriter.inc + "\n";
            String endA = "@" + "END" + CodeWriter.inc + "\n";
            //label to goto
            String jmpLabel = "(" + c.toUpperCase() + CodeWriter.inc + ")\n";
            String endLabel = "(" + "END" + CodeWriter.inc + ")\n";

            String jump = "J" + c.toUpperCase() + "\n";

            CodeWriter.inc++;

            return "@SP\n" +
                    "M = M -1\n" +
                    "A = M\n" +
                    "D = M\n" +
                    "@SP\n" +
                    "M = M - 1\n" +
                    "A = M\n" +
                    "D = M - D\n" +
                    jmpA +
                    "D; " + jump +
                    "@SP\n" +
                    "A = M\n" +
                    "M = 0\n" +
                    endA +
                    "0; JMP\n" +
                    jmpLabel +
                    "@SP\n" +
                    "A = M\n" +
                    "M = -1\n" +
                    endLabel +
                    "@SP\n" +
                    "M = M + 1\n";
        }
        // neg or not
        return "@SP\n" +
                "M = M - 1\n" +
                "A = M\n" +
                "D = !M\n" +
                replace + "\n" +
                "M = D\n" +
                "@SP\n" +
                "M = M + 1\n";
    }

}
