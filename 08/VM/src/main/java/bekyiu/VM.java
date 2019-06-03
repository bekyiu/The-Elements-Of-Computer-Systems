package bekyiu;

import java.io.*;

public class VM
{
    private Parser parser = new Parser();
    private CodeWriter codeWriter = new CodeWriter();

    public static void main(String[] args)
    {

        //ProgramFlow/FibonacciSeries/FibonacciSeries.vm
        new VM().build("ProgramFlow/FibonacciSeries/", "FibonacciSeries.vm");
    }

    public void build(String path, String fileName)
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
            while ((line = br.readLine()) != null)
            {
                String asm = getAsmCode(line);
                if (asm != null)
                {
                    bw.write(asm);
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

    private String getAsmCode(String line)
    {
        if (line.startsWith("//") || line.equals(""))
        {
            return null;
        }
        String c = Parser.filter(line);
        System.out.println(c);
        String type = parser.commandType(c);
        String asm = null;
        if (type.equals(Parser.C_ARITHMETIC))
        {
            asm = codeWriter.writeArithmetic(c);
        }
        else if (type.equals(Parser.C_PUSH) || type.equals(Parser.C_POP))
        {
            asm = codeWriter.writePushPop(c);
        }
        else if(type.equals(Parser.C_LABEL))
        {
            asm = codeWriter.writeLabel(c);
        }
        else if(type.equals(Parser.C_GOTO))
        {
            asm = codeWriter.writeGoto(c);
        }
        else if(type.equals(Parser.C_IF))
        {
            asm = codeWriter.writeIf(c);
        }
        return asm;
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
