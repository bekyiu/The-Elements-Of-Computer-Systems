package bekyiu;

import java.io.*;

public class VM
{
    private Parser parser = new Parser();
    private CodeWriter codeWriter = new CodeWriter();

    public static void main(String[] args)
    {

        new VM().fuck("MemoryAccess/StaticTest/", "StaticTest.vm");
    }

    public void fuck(String path, String fileName)
    {
        File vmFile = new File(path, fileName);
        File asmFile = new File(path, fileName.split("\\.")[0] + ".asm");
        codeWriter.setFileName(fileName.split("\\.")[0]);
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
                else if(type.equals(Parser.C_PUSH) || type.equals(Parser.C_POP))
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
