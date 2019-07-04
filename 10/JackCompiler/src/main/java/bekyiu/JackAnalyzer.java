package bekyiu;

public class JackAnalyzer
{
    public static void main(String[] args)
    {
        //ArrayTest/Main.jack
        //Square/Main.jack
        //Square/Square.jack
        JackTokenizer tokenizer = new JackTokenizer("Square/Square.jack");
//        System.out.println(tokenizer.getTokens());
        CompilationEngine engine = new CompilationEngine("out.xml", tokenizer);
//        String a = "let j = j / 2;//sbdx";
//        System.out.println(a.substring(0, a.lastIndexOf("//")));
    }
}
