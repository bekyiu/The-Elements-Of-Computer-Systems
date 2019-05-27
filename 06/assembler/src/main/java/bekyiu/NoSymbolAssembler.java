package bekyiu;

import java.io.*;
import java.util.Scanner;

public class NoSymbolAssembler
{
    public static void main(String[] args)
    {
        File asmFile = new File("demo.asm");
        File hackFile = new File("demo.hack");
        Writer out = null;
        Scanner sc = null;
        try
        {
            sc = new Scanner(asmFile);
            out = new FileWriter(hackFile);
            while(sc.hasNext())
            {
                String s = Parser.escapeSpace(sc.nextLine());
                if(s.startsWith("//"))
                {
                    continue;
                }
                System.out.println(s);
                s = s + "!";
                out.write(s);
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
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            sc.close();
        }
    }
}
