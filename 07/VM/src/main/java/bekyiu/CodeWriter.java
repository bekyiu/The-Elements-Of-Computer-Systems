package bekyiu;

// 将VM指令翻译成Hack汇编
public class CodeWriter
{
    private static Integer inc = 0;
    public void writeArithmetic(String c)
    {
        String hack = null;
        if(c.equals("add"))
        {
            hack = "@SP\nM = M - 1\nA = M\nD = M\n@SP\nM = M - 1\nA = M\nD = D + M\nM = D\n@SP\nM = M + 1";
        }
        else if(c.equals("sub"))
        {
            hack = "@SP\nM = M - 1\nA = M\nD = M\n@SP\nM = M - 1\nA = M\nD = M - D\nM = D\n@SP\nM = M + 1";
        }
        else if(c.equals("neg"))
        {
            hack = "@SP\nM = M - 1\nA = M\nD = !M\nD = D + 1\nM = D\n@SP\nM = M + 1";
        }
        else if(c.equals("eq"))
        {
            // 注意, 同一个.vm文件中可能会用到多个 eq
            // 这样如果翻译成汇编之后, 标号会重复, 所以要加以区分
            hack = "@SP\nM = M -1\nA = M\nD = M\n@SP\nM = M - 1\nA = M\nD = M - D\n" +
                    "@EQ" + inc + "\n" +
                    "D; JEQ\n@SP\nA = M\nM = 0\n" +
                    "@END" + inc + "\n" +
                    "0; JMP\n" +
                    "(EQ" + inc + ")\n" +
                    "@SP\nA = M\nM = -1\n" +
                    "(END" + inc + ")\n" +
                    "@SP\nM = M + 1";
            CodeWriter.inc++;
        }
        else if(c.equals("gt"))
        {

        }
        else if(c.equals("lt"))
        {

        }
        else if(c.equals("and"))
        {

        }
        else if(c.equals("or"))
        {

        }
        else if(c.equals("not"))
        {

        }
    }

    public static void main(String[] args)
    {
        CodeWriter c = new CodeWriter();
        c.writeArithmetic("eq");
        c.writeArithmetic("eq");

    }
}
