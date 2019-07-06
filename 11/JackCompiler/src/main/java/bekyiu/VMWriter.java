package bekyiu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VMWriter
{
    private BufferedWriter bw;
    private JackTokenizer tokenizer;
//    private long suffix = 0;

    public VMWriter(String outpath, JackTokenizer tokenizer)
    {
        this.tokenizer = tokenizer;
        try
        {
            bw = new BufferedWriter(new FileWriter(new File(outpath)));
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

    private void write(String s) throws IOException
    {
        bw.write(s);
        bw.newLine();
        bw.flush();
    }

    public void writePush(String segment, int index) throws IOException
    {
        write("push " + segment + " " + index);
    }
    public void writePop(String segment, int index) throws IOException
    {
        write("pop " + segment + " " + index);
    }

    public void writeArithmetic(String c) throws IOException
    {
        write(c);
    }

    public void writeLabel(String label) throws IOException
    {
        write("label " + label);
    }

    public void writeGoto(String label) throws IOException
    {
        write("goto " + label);
    }

    public void writeIf(String label) throws IOException
    {
        write("if-goto " + label);
    }

    public void writeCall(String name, int arg) throws IOException
    {
        write("call " + name + " " + arg);
    }

    public void writeFunction(String name, int arg) throws IOException
    {
        write("function " + name + " " + arg);
    }

    public void writeReturn() throws IOException
    {
        write("return");
    }
}
