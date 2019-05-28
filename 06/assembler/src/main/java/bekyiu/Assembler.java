package bekyiu;

import lombok.Getter;

import java.io.*;

public class Assembler
{
    @Getter
    private SymbolTable symbolTable = new SymbolTable();
    private Parser parser = new Parser();
    private Code code = new Code();
    // 从16地址开始, 可以存放用户自定义的变量
    private static Integer varAddr = 16;

    public static void main(String[] args)
    {
        Assembler a = new Assembler();

        a.buildSymbolTable("Max.asm");
        a.buildHack("Max.asm");
    }

    // 输出.hack文件
    private void buildHack(String fileName)
    {
        File asmFile = new File(fileName);
        File hackFile = new File(fileName.split("\\.")[0] + ".hack");
        BufferedWriter bw = null;
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(asmFile));
            bw = new BufferedWriter(new FileWriter(hackFile));
            String line = "";

            while ((line = br.readLine()) != null)
            {
                String binary = getBinaryCode(line);
                if (binary != null)
                {
                    bw.write(binary);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(br, bw);
        }
    }

    //把读到的一行转换成一行二进制
    private String getBinaryCode(String line)
    {
        String c = Parser.filter(line);
        String target = "";
        //不知道为什么会读到 ""
        if (c.equals("") || c.startsWith("//"))
        {
            return null;
        }

        System.out.println(c);
        //A or C or L ?
        String type = parser.commandType(c);

        if (type.equals(Parser.A_COMMAND))
        {
            target = a_cmdBinaryCode(c);
        }
        else if (type.equals(Parser.C_COMMAND))
        {
            target = c_cmdBinaryCode(c);
        }
        else
        {
//            target = l_cmdBinaryCode(c);
        }
        return target;
    }

    // 把a指令转换成对应的二进制
    private String a_cmdBinaryCode(String c)
    {
        String symbol = parser.symbol(c);
        String binary = "";

        if (isStringNumber(symbol))
        {
            binary = Integer.toBinaryString(Integer.valueOf(symbol));

        }
        else
        {
            // 如果有, 说明是label或者是使用之前定义过得变量, 否则就是第一次遇到的变量的定义
            if (symbolTable.contains(symbol))
            {
                Integer addr = symbolTable.getAddress(symbol);
                binary = Integer.toBinaryString(addr);
            }
            else
            {
                symbolTable.addEntry(symbol, varAddr);
                binary = Integer.toBinaryString(varAddr++);
            }
        }
        StringBuilder sb = new StringBuilder(16);
        //补全16位
        for (int i = 0; i < 16 - binary.length(); i++)
        {
            sb.append("0");
        }
        return sb.append(binary).append("\n").toString();
    }

    // 把c指令转换成对应的二进制
    private String c_cmdBinaryCode(String c)
    {
        String comp = code.comp(parser.comp(c));
        String dest = code.dest(parser.dest(c));
        String jump = code.jump(parser.jump(c));
        StringBuilder sb = new StringBuilder();
        sb.append("111").append(comp).append(dest).append(jump).append("\n");
        return sb.toString();
    }


    // 该字符串是否表示的是数字
    private boolean isStringNumber(String s)
    {
        try
        {
            Integer.valueOf(s);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    // 仅仅构建符号表(仅包括label), 不输出二进制
    private void buildSymbolTable(String fileName)
    {
        File asmFile = new File(fileName);
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(asmFile));
            String line = "";
            Integer curAddress = 0;

            while ((line = br.readLine()) != null)
            {
                if (line.startsWith("//") || line.equals(""))
                {
                    continue;
                }
                String c = Parser.filter(line);
                if (parser.commandType(c).equals(Parser.L_COMMAND))
                {
                    String symbol = parser.symbol(line);
                    if (!symbolTable.contains(symbol))
                    {
                        //伪指令是不会翻译成机器语言的, 也就不会占内存
                        symbolTable.addEntry(symbol, curAddress--);
                    }
                }
                curAddress++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(br, null);
        }
    }

    private void close(BufferedReader br, BufferedWriter bw)
    {
        try
        {
            if (br != null)
            {
                br.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (bw != null)
                {
                    bw.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
