package bekyiu;

import java.util.Arrays;

public class Test
{
    public static void main(String[] args)
    {

        String s = "let length = Keyboard.readInt(\"HOW MANY NUMBERS? z\nz\", abc, \"w  ww\");";

        String[] sp = JackTokenizer.splitPlus(s);
        System.out.println(Arrays.toString(sp));
    }
}
