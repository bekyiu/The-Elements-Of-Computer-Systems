package bekyiu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;

public class CompilationEngine
{
    private BufferedWriter bw;
    private JackTokenizer tokenizer;

    public CompilationEngine(String outpath, JackTokenizer tokenizer)
    {
        this.tokenizer = tokenizer;
        try
        {
            bw = new BufferedWriter(new FileWriter(new File(outpath)));
            compileClass();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
//        finally
//        {
//            bw.close();
//        }
    }

    private void write(String s) throws IOException
    {
        bw.write(s);
        bw.newLine();
        bw.flush();
    }

    // 编译整个class
    public void compileClass() throws IOException
    {
        Queue<String> tokens = tokenizer.getTokens();
        // 应当取出 class
        String clazzToken = tokens.remove();
        if (JackTokenizer.CLASS.equals(clazzToken))
        {
            write("<class>");
            write("<keyword>" + clazzToken + "</keyword>");
            // 应当取出 className
            String token = tokens.remove();
            if (JackTokenizer.isIdentifier(token))
            {
                write("<identifier>" + token + "</identifier>");
            }
            else
            {
                throw new RuntimeException("期望一个合法的identifier, 实际上是" + token);
            }
            // 应当取出 {
            token = tokens.remove();
            if (token.equals("{"))
            {
                write("<symbol>" + token + "</symbol>");
            }
            else
            {
                throw new RuntimeException("期望 { , 实际上是" + token);
            }
            compileClassVarDec();
            compileSubroutine();
            // }
            token = tokens.remove();
            if (token.equals("}"))
            {
                write("<symbol>" + token + "</symbol>");
            }
            else
            {
                throw new RuntimeException("期望 } , 实际上是" + token);
            }
            write("</class>");
        }
        else
        {
            throw new RuntimeException("期望关键字 class , 实际上是" + clazzToken);
        }
    }

    // 编译静态声明或字段声明, 可以没有, 可以有多个
    public void compileClassVarDec() throws IOException
    {
        Queue<String> tokens = tokenizer.getTokens();
        //如果声明了字段, 那么这个token应该是static或者field
        //如果没有声明字段, 则应该是function method constructor } (空的类)
        String token = tokens.element();
        if (JackTokenizer.STATIC.equals(token) || JackTokenizer.FIELD.equals(token))
        {
            tokens.remove();
            write("<classVarDec>");
            write("<keyword>" + token + "</keyword>");
            // type, 基本类型和引用类型
            token = tokens.remove();
            if (JackTokenizer.INT.equals(token) || JackTokenizer.BOOLEAN.equals(token) ||
                    JackTokenizer.CHAR.equals(token))
            {
                write("<keyword>" + token + "</keyword>");
            }
            else
            {
                write("<identifier>" + token + "</identifier>");
            }
            //varName
            token = tokens.remove();
            write("<identifier>" + token + "</identifier>");
            //, or ;
            token = tokens.remove();
            while (!token.equals(";"))
            {
                //,
                write("<symbol>" + token + "</symbol>");
                //varName
                token = tokens.remove();
                write("<identifier>" + token + "</identifier>");
                token = tokens.remove();
            }
            //此时 token等于 ;
            write("<symbol>" + token + "</symbol>");

            write("</classVarDec>");
        }
        else if (!JackTokenizer.FUNCTION.equals(token) && !JackTokenizer.CONSTRUCTOR.equals(token) &&
                !JackTokenizer.METHOD.equals(token) && !"}".equals(token))
        {
            throw new RuntimeException("期望申明 static 或者是 field 变量/对象, 但实际上是" + token);
        }
    }

    // 编译方法, 构造函数, 静态函数 可以没有, 可以有多个
    public void compileSubroutine() throws IOException
    {
        Queue<String> tokens = tokenizer.getTokens();
        // function method constructor }
        String token = tokens.element();
        if (JackTokenizer.FUNCTION.equals(token) || JackTokenizer.CONSTRUCTOR.equals(token) ||
                JackTokenizer.METHOD.equals(token))
        {
            tokens.remove();
            write("<subroutineDec>");
            write("<keyword>" + token + "</keyword>");
            // 函数的返回值, void, 基本类型, 引用
            token = tokens.remove();
            if (JackTokenizer.INT.equals(token) || JackTokenizer.BOOLEAN.equals(token) ||
                    JackTokenizer.CHAR.equals(token) || JackTokenizer.VOID.equals(token))
            {
                write("<keyword>" + token + "</keyword>");
            }
            else
            {
                write("<identifier>" + token + "</identifier>");
            }
            // function name
            token = tokens.remove();
            write("<identifier>" + token + "</identifier>");
            // (
            token = tokens.remove();
            write("<symbol>" + token + "</symbol>");
            compileParameterList();
            write("<symbol>)</symbol>");
            compileSubroutineBody();
            write("</subroutineDec>");
        }
        else if (!token.equals("}"))
        {
            throw new RuntimeException("期望是 function, method, constructor, }, 但实际上是" + token);
        }
    }

    // 函数参数列表 z x, z x, z x 也可以是什么都没有
    public void compileParameterList() throws IOException
    {
        write("<parameterList>");
        Queue<String> tokens = tokenizer.getTokens();
        // type, )
        String token = tokens.remove();

        while (!")".equals(token))
        {
            if (JackTokenizer.INT.equals(token) || JackTokenizer.BOOLEAN.equals(token) ||
                    JackTokenizer.CHAR.equals(token))
            {
                write("<keyword>" + token + "</keyword>");
            }
            else
            {
                write("<identifier>" + token + "</identifier>");
            }
            //varName
            token = tokens.remove();
            write("<identifier>" + token + "</identifier>");
            //, or )
            token = tokens.remove();
            if (",".equals(token))
            {
                write("<symbol>" + token + "</symbol>");
                token = tokens.remove();
            }
        }
        // 此时token是 )
//        write("<symbol>" + token + "</symbol>");
        write("</parameterList>");
    }

    // 编译函数体{...}
    public void compileSubroutineBody()
    {

    }

    public void compileVarDec()
    {

    }

    public void compileStatements()
    {

    }

    public void compileDo()
    {

    }

    public void compileLet()
    {

    }

    public void compileWhile()
    {

    }

    public void compileReturn()
    {

    }

    public void compileIf()
    {

    }

    public void compileExpression()
    {

    }

    public void compileTerm()
    {

    }

    public void compileExpressionList()
    {

    }
}
