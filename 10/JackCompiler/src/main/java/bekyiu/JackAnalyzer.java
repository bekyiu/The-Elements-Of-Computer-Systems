package bekyiu;

public class JackAnalyzer
{
    public static void main(String[] args)
    {
        JackTokenizer tokenizer = new JackTokenizer("ExpressionLessSquare/Square.jack");
        CompilationEngine engine = new CompilationEngine("out.xml", tokenizer);
    }
}
