package bekyiu;

import java.io.*;

public class NoSymbolAssembler
{
    private static Parser parser = new Parser();
    private static Code code = new Code();

    public static void main(String[] args)
    {
        File asmFile = new File("demo.asm");
        File hackFile = new File("demo.hack");
        BufferedWriter bw = null;
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(asmFile));
            bw = new BufferedWriter(new FileWriter(hackFile));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                String c = Parser.escapeSpace(line);
                String target = "";
                //不知道为什么会读到 ""
                if (c.equals("") || c.startsWith("//"))
                {
                    continue;
                }
                System.out.println(c);
                String type = parser.commandType(c);

                if (type.equals(Parser.A_COMMAND))
                {
                    String symbol = parser.symbol(c);
                    String binary = Integer.toBinaryString(Integer.valueOf(symbol));
                    StringBuilder sb = new StringBuilder(16);
                    //补全16位
                    for (int i = 0; i < 16 - binary.length(); i++)
                    {
                        sb.append("0");
                    }
                    target = sb.append(binary).toString();
                }
                else if (type.equals(Parser.C_COMMAND))
                {
                    String comp = code.comp(parser.comp(c));
                    String dest = code.dest(parser.dest(c));
                    String jump = code.jump(parser.jump(c));
                    target = "111" + comp + dest + jump;
                }
                bw.write(target + "\n");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                bw.close();
                br.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
