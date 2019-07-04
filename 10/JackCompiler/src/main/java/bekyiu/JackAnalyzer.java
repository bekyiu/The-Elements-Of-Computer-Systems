package bekyiu;

public class JackAnalyzer
{
    public static void main(String[] args)
    {
        JackTokenizer tokenizer = new JackTokenizer("Square/SquareGame.jack");
        CompilationEngine engine = new CompilationEngine("out.xml", tokenizer);
//        String a = "let j = j / 2;//sbdx";
//        System.out.println(a.substring(0, a.lastIndexOf("//")));
    }
}
