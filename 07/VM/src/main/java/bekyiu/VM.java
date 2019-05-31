package bekyiu;

import java.io.*;

public class VM
{
    private Parser parser = new Parser();
    private CodeWriter codeWriter = new CodeWriter();

    public static void main(String[] args)
    {
        new VM().fuck();
    }

    public void fuck()
    {
        File vmFile = new File("StackArithmetic/SimpleAdd/", "SimpleAdd.vm");
        File asmFile = new File("StackArithmetic/SimpleAdd/", "SimpleAdd.asm");
        BufferedReader br = null;
        BufferedWriter bw = null;
        try
        {
            br = new BufferedReader(new FileReader(vmFile));
            bw = new BufferedWriter(new FileWriter(asmFile));
            String line = "";
            while((line = br.readLine()) != null)
            {
                if(line.startsWith("//") || line.equals(""))
                {
                    continue;
                }
                String c = Parser.filter(line);
                System.out.println(c);
                String type = parser.commandType(c);
                String asm = null;
                if(type.equals(Parser.C_ARITHMETIC))
                {
                    asm = codeWriter.writeArithmetic(c);
                }
                else
                {
                    asm = codeWriter.writePushPop(c);
                }
                bw.write(asm);
//                bw.newLine();
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
