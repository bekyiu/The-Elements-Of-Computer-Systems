package bekyiu;

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

// 将VM指令翻译成Hack汇编
public class CodeWriter
{
    // 注意, 同一个.vm文件中可能会用到多个 eq
    // 这样如果翻译成汇编之后, 标号会重复, 所以要加以区分
    private static Long inc = 0L;
    private Parser parser = new Parser();
    @Setter
    private String fileName;    //翻译文件的名称 static 会用到

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

    // 写在整个文件的开头
    public String writeInit()
    {
        // 这个sp的值, 测试文件里写的是261不是256, 坑
        return "@261\n" +
                "D = A\n" +
                "@SP\n" +
                "M = D\n" +
                "@Sys.init\n" +
                "0; JMP\n";
    }

    /*
    注意: 函数的名称, label 在翻译的过程中都是有规范的
    在Xxx.vm文件中的函数f, 那么他的入口标号应该翻译为Xxx.f
    在Xxx.vm文件中的函数f中的所有label Z, 都应该翻译为Xxx.f$Z
    在Xxx.vm文件中的函数f, 他的返回地址应该为Xxx.f$ret.i

    不过我假定给出的vm code的函数名称都为Xxx.f
    函数中的label都为Xxx.f$Z
    vm code是compiler的输出, 所以会在compiler中实现
     */
    public String writeCall(String c)
    {
        String funcName = parser.arg1(c);
        Integer nums = parser.arg2(c);

        String returnSymbol = funcName + "$ret." + inc++;
        String pushTemplate = "@SP\nA = M\nM = D\n@SP\nM = M + 1\n";
        String pushAddr =  "@" + returnSymbol + "\nD = A\n" + pushTemplate;
        String pushLCL = "@LCL\nD = M\n" + pushTemplate;
        String pushARG = "@ARG\nD = M\n" + pushTemplate;
        String pushTHIS = "@THIS\nD = M\n" + pushTemplate;
        String pushTHAT = "@THAT\nD = M\n" + pushTemplate;
        // ARG = SP - n - 5
        String exp1 = "@SP\n" +
                "D = M\n" +
                "@" + nums + "\n" +
                "D = D - A\n" +
                "@5\n" +
                "D = D - A\n" +
                "@ARG\n" +
                "M = D\n";
        // LCL = SP
        String exp2 = "@SP\n" +
                "D = M\n" +
                "@LCL\n" +
                "M = D\n";
        // goto f
        String exp3 = "@" + funcName + "\n0; JMP\n";
        String returnLabel = "(" + returnSymbol + ")\n";
        return pushAddr + pushLCL + pushARG + pushTHIS + pushTHAT +
                exp1 + exp2 + exp3 + returnLabel;

    }

    //f在XX.vm中应被翻译为XX.f 可以交给compiler来做, 这里不作处理
    public String writeFunction(String c)
    {
        String funcName = parser.arg1(c);
        Integer nums = parser.arg2(c);
        String s = "(" + funcName + ")\n" +
                "@" + nums + "\n" +
                "D = A\n" +
                "@times\n" +
                "M = D\n" +
                "(LOOP" + inc + ")\n" +
                "@times\n" +
                "D = M\n" +
                "@END" + inc + "\n" +
                "D; JEQ\n" +
                "@0\n" +
                "D = A\n" +
                "@SP\n" +
                "A = M\n" +
                "M = D\n" +
                "@SP\n" +
                "M = M + 1\n" +
                "@times\n" +
                "M = M - 1\n" +
                "@LOOP" + inc + "\n" +
                "0; JMP\n" +
                "(END" + inc + ")\n";
        inc++;
        return s;
    }

    public String writeReturn()
    {
        return "@LCL\n" +
                "D = M\n" +
                "@frame\n" +
                "M = D\n" +
                "@5\n" +
                "D = D - A\n" +
                "A = D\n" +
                "D = M\n" +
                "@ret\n" +
                "M = D\n" +
                "@SP\n" +
                "M = M - 1\n" +
                "A = M\n" +
                "D = M\n" +
                "@ARG\n" +
                "A = M\n" +
                "M = D\n" +
                "@ARG\n" +
                "D = M + 1\n" +
                "@SP\n" +
                "M = D\n" +
                "@frame\n" +
                "D = M\n" +
                "D = D - 1\n" +
                "A = D\n" +
                "D = M\n" +
                "@THAT\n" +
                "M = D\n" +
                "@frame\n" +
                "D = M\n" +
                "@2\n" +
                "D = D - A\n" +
                "A = D\n" +
                "D = M\n" +
                "@THIS\n" +
                "M = D\n" +
                "@frame\n" +
                "D = M\n" +
                "@3\n" +
                "D = D - A\n" +
                "A = D\n" +
                "D = M\n" +
                "@ARG\n" +
                "M = D\n" +
                "@frame\n" +
                "D = M\n" +
                "@4\n" +
                "D = D - A\n" +
                "A = D\n" +
                "D = M\n" +
                "@LCL\n" +
                "M = D\n" +
                "@ret\n" +
                "A = M\n" +
                "0; JMP\n";
    }

    public String writeLabel(String c)
    {
        String label = parser.arg1(c);
        return "(" + label + ")\n";
    }

    public String writeGoto(String c)
    {
        String a_ins = "@" + parser.arg1(c) + "\n";
        return a_ins + "0; JMP\n";
    }

    public String writeIf(String c)
    {
        String a_ins = "@" + parser.arg1(c) + "\n";
        return "@SP\n" +
                "M = M - 1\n" +
                "A = M\n" +
                "D = M\n" +
                a_ins +
                "D; JNE\n";
    }

}
