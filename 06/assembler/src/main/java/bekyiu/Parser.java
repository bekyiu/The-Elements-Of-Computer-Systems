package bekyiu;

/**
 * 对读取的一行汇编语言命令并对其进行解析
 * 去掉所有的空格和注释 (为了方便先在外面做了)
 */
public class Parser
{
    public static final String A_COMMAND = "A";
    public static final String C_COMMAND = "C";
    public static final String L_COMMAND = "L";

    //返回当前指令的类型
    public String commandType(String command)
    {
        if (command.startsWith("@"))
        {
            return A_COMMAND;
        }
        if (command.startsWith("(") && command.endsWith(")"))
        {
            return L_COMMAND;
        }
        return C_COMMAND;
    }

    //@xx 或 (xx) 的符号或十进制
    public String symbol(String command)
    {
        String type = commandType(command);
        if (type.equals(A_COMMAND))
        {
            return command.substring(1);
        }
        if (type.equals(L_COMMAND))
        {
            return command.substring(command.indexOf("(") + 1,
                    command.lastIndexOf(")"));
        }
        return null;
    }

    //返回当前c指令的jump助记符
    public String jump(String command)
    {
        if (commandType(command).equals(C_COMMAND))
        {
            if (command.contains(";"))
            {
                String[] s = command.split(";");
                return s[1];
            }
            return "";
        }
        return null;
    }

    //返回当前c指令的comp助记符
    public String comp(String command)
    {
        if (commandType(command).equals(C_COMMAND))
        {
            //comp域是必定存在的
            if(command.contains("="))
            {
                //D = D + 1
                if(command.contains(";"))
                {
                    //D = D + 1; JMP
                    return command.substring(command.indexOf("=") + 1,
                            command.lastIndexOf(";"));
                }
                return command.substring(command.indexOf("=") + 1);
            }
            //D; JMP
            if(command.contains(";"))
            {
                return command.split(";")[0];
            }
            //D
            return command;
        }
        return null;
    }

    //返回当前c指令的dest助记符
    public String dest(String command)
    {
        if (commandType(command).equals(C_COMMAND))
        {
            //dest存在
            //D = A + 1
            if(command.contains("="))
            {
                return command.split("=")[0];
            }
            //dest不存在
            //D; JMP
            return "";
        }
        return null;
    }

    public static String escapeSpace(String str)
    {
        return str.replaceAll("\\s*", "");
    }
}
