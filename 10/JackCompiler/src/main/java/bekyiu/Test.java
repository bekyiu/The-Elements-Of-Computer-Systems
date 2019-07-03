package bekyiu;

import java.util.Arrays;

public class Test
{
    public static void main(String[] args)
    {

        String s = "if (key = 81)  { let exit = true; }";

        String[] sp = JackTokenizer.splitPlus(s);
        System.out.println(Arrays.toString(sp));
    }
}
