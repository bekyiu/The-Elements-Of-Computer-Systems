package bekyiu;

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

// 将VM指令翻译成Hack汇编
public class CodeWriter
{
    // 注意, 同一个.vm文件中可能会用到多个 eq
    // 这样如果翻译成汇编之后, 标号会重复, 所以要加以区分
    private static Integer inc = 0;
    private Parser parser = new Parser();
    @Setter
    private String fileName;    //static 会用到

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
        Map<String, String> map = new HashMap<>();
        map.put("local", "LCL");
        map.put("argument", "ARG");
        map.put("this", "THIS");
        map.put("that", "THAT");
        map.put("0", "THIS");
        map.put("1", "THAT");

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
            if(arg1.equals("local") || arg1.equals("argument") || arg1.equals("this")
                    || arg1.equals("that"))
            {
                return "@" + arg2 + "\n" +
                        "D = A\n" +
                        "@" + map.get(arg1) + "\n" +
                        "A = D + M\n" +
                        "D = M\n" +
                        "@SP\n" +
                        "A = M\n" +
                        "M = D\n" +
                        "@SP\n" +
                        "M = M + 1\n";
            }
            if(arg1.equals("pointer"))
            {
                return "@" + map.get(String.valueOf(arg2)) + "\n" +
                        "D = M\n" +
                        "@SP\n" +
                        "A = M\n" +
                        "M = D\n" +
                        "@SP\n" +
                        "M = M + 1\n";
            }
            if(arg1.equals("temp"))
            {
                return "@" + arg2 + "\n" +
                        "D = A\n" +
                        "@5\n" +
                        "A = D + A\n" +
                        "D = M\n" +
                        "@SP\n" +
                        "A = M\n" +
                        "M = D\n" +
                        "@SP\n" +
                        "M = M + 1\n";
            }
            if(arg1.equals("static"))
            {
                String label = fileName + "." + arg2;
                return "@" + label + "\n" +
                        "D = M\n" +
                        "@SP\n" +
                        "A = M\n" +
                        "M = D\n" +
                        "@SP\n" +
                        "M = M + 1\n";
            }
        }
        else
        {
            if(arg1.equals("local") || arg1.equals("argument") || arg1.equals("this")
                    || arg1.equals("that"))
            {
                return "@" + arg2 + "\n" +
                        "D = A\n" +
                        "@" + map.get(arg1) + "\n" +
                        "D = D + M\n" +
                        "@addr\n" +
                        "M = D\n" +
                        "@SP\n" +
                        "M = M - 1\n" +
                        "A = M\n" +
                        "D = M\n" +
                        "@addr\n" +
                        "A = M\n" +
                        "M = D\n";
            }
            if(arg1.equals("pointer"))
            {
                return "@SP\n" +
                        "M = M - 1\n" +
                        "A = M\n" +
                        "D = M\n" +
                        "@" + map.get(String.valueOf(arg2)) + "\n" +
                        "M = D\n";
            }
            if(arg1.equals("temp"))
            {
                return "@" + arg2 + "\n" +
                        "D = A\n" +
                        "@5\n" +
                        "D = D + A\n" +
                        "@addr\n" +
                        "M = D\n" +
                        "@SP\n" +
                        "M = M - 1\n" +
                        "A = M\n" +
                        "D = M\n" +
                        "@addr\n" +
                        "A = M\n" +
                        "M = D\n";
            }
            if(arg1.equals("static"))
            {
                String label = fileName + "." + arg2;
                return "@SP\n" +
                        "M = M - 1\n" +
                        "A = M\n" +
                        "D = M\n" +
                        "@" + label + "\n" +
                        "M = D\n";
            }
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
