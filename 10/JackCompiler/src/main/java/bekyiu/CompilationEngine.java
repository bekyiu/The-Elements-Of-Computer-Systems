package bekyiu;

import jdk.nashorn.internal.parser.Token;

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
        while (JackTokenizer.STATIC.equals(token) || JackTokenizer.FIELD.equals(token))
        {
            compileClassVarDec1();
            //如果声明了字段, 那么这个token应该是static或者field
            //如果没有声明字段, 则应该是function method constructor } (空的类)
            token = tokens.element();
        }
    }

    private void compileClassVarDec1() throws IOException
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
            if (typeCheck(token))
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
            if (typeCheck(token) || JackTokenizer.VOID.equals(token))
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
            if (typeCheck(token))
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
    public void compileSubroutineBody() throws IOException
    {
        Queue<String> tokens = tokenizer.getTokens();
        // {
        String token = tokens.remove();
        if ("{".equals(token))
        {
            write("<subroutineBody>");
            write("<symbol>" + token + "</symbol>");
            compileVarDec();
            compileStatements();
            // }
            token = tokens.remove();
            write("<symbol>" + token + "</symbol>");
            write("</subroutineBody>");
        }
        else
        {
            throw new RuntimeException("期望是 {, 实际上是" + token);
        }
    }

    public void compileVarDec() throws IOException
    {
        Queue<String> tokens = tokenizer.getTokens();
        // var, 不是var(statements)
        String token = tokens.element();
        while (JackTokenizer.VAR.equals(token))
        {
            compileVarDec1();
            token = tokens.element();
        }
    }

    private void compileVarDec1() throws IOException
    {
        Queue<String> tokens = tokenizer.getTokens();
        // 假设是 var
        String token = tokens.remove();
        write("<varDec>");
        write("<keyword>" + token + "</keyword>");
        // type
        token = tokens.remove();
        if (typeCheck(token))
        {
            write("<keyword>" + token + "</keyword>");
        }
        else
        {
            write("<identifier>" + token + "</identifier>");
        }
        // varName
        token = tokens.remove();
        write("<identifier>" + token + "</identifier>");
        // , or ;
        token = tokens.remove();
        while (!token.equals(";"))
        {
            //,
            write("<symbol>" + token + "</symbol>");
            //type
            token = tokens.remove();
            if (typeCheck(token))
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
            token = tokens.remove();
        }
        //此时 token等于 ;
        write("<symbol>" + token + "</symbol>");
        write("</varDec>");
    }

    private boolean typeCheck(String token)
    {
        return JackTokenizer.INT.equals(token) || JackTokenizer.BOOLEAN.equals(token) ||
                JackTokenizer.CHAR.equals(token);
    }

    public void compileStatements() throws IOException
    {
        write("<statements>");
        Queue<String> tokens = tokenizer.getTokens();
        // let if while do return }
        String token = tokens.element();
        if (JackTokenizer.LET.equals(token))
        {
            compileLet();
        }
        else if (JackTokenizer.IF.equals(token))
        {
            compileIf();
        }
        else if (JackTokenizer.WHILE.equals(token))
        {
            compileWhile();
        }
        else if (JackTokenizer.DO.equals(token))
        {
            compileDo();
        }
        else if (JackTokenizer.RETURN.equals(token))
        {
            compileReturn();
        }
        write("</statements>");
    }

    public void compileDo() throws IOException
    {
        Queue<String> tokens = tokenizer.getTokens();
        // do
        String token = tokens.remove();
        write("<doStatement>");
        write("<keyword>" + token + "</keyword>");
        // fun(xx); or xx.fun(xx);
        // funName, className, varName
        token = tokens.remove();
        //( or .
        String nextToken = tokens.element();
        if ("(".equals(nextToken))
        {
            write("<identifier>" + token + "</identifier>");
            // (
            token = tokens.remove();
            write("<symbol>" + token + "</symbol>");
            compileExpressionList();
            // )
            token = tokens.remove();
            write("<symbol>" + token + "</symbol>");
        }
        else if (".".equals(nextToken))
        {
            write("<identifier>" + token + "</identifier>");
            // .
            token = tokens.remove();
            write("<symbol>" + token + "</symbol>");
            // funName
            token = tokens.remove();
            write("<identifier>" + token + "</identifier>");
            // (
            token = tokens.remove();
            write("<symbol>" + token + "</symbol>");
            compileExpressionList();
            // )
            token = tokens.remove();
            write("<symbol>" + token + "</symbol>");

        }
        write("</doStatement>");

    }

    public void compileLet() throws IOException
    {
        Queue<String> tokens = tokenizer.getTokens();
        // let
        String token = tokens.remove();
        write("<letStatement>");
        write("<keyword>" + token + "</keyword>");
        // varName
        token = tokens.remove();
        write("<identifier>" + token + "</identifier>");
        // a = ...., a[1] = ...
        token = tokens.remove();
        if ("[".equals(token))
        {
            write("<symbol>" + token + "</symbol>");
            compileExpression();
            // ]
            token = tokens.remove();
            write("<symbol>" + token + "</symbol>");
            // =
            token = tokens.remove();
        }
        write("<symbol>" + token + "</symbol>");
        // expression
        // token = tokens.remove();
        compileExpression();
        // ;
        token = tokens.remove();
        write("<symbol>" + token + "</symbol>");

        write("</letStatement>");
    }

    public void compileWhile() throws IOException
    {
        Queue<String> tokens = tokenizer.getTokens();
        // while
        String token = tokens.remove();
        write("<whileStatement>");
        write("<keyword>" + token + "</keyword>");
        // (
        token = tokens.remove();
        write("<symbol>" + token + "</symbol>");
        compileExpression();
        // )
        token = tokens.remove();
        write("<symbol>" + token + "</symbol>");
        // {
        token = tokens.remove();
        write("<symbol>" + token + "</symbol>");
        compileStatements();
        // }
        token = tokens.remove();
        write("<symbol>" + token + "</symbol>");
        write("</whileStatement>");
    }

    public void compileReturn() throws IOException
    {
        Queue<String> tokens = tokenizer.getTokens();
        // return
        String token = tokens.remove();
        write("<returnStatement>");
        write("<keyword>" + token + "</keyword>");
        compileExpression();
        // ;
        token = tokens.remove();
        write("<symbol>" + token + "</symbol>");
        write("</returnStatement>");
    }

    public void compileIf() throws IOException
    {
        Queue<String> tokens = tokenizer.getTokens();
        // if
        String token = tokens.remove();
        write("<ifStatement>");
        write("<keyword>" + token + "</keyword>");
        // (
        token = tokens.remove();
        write("<symbol>" + token + "</symbol>");
        compileExpression();
        // )
        token = tokens.remove();
        write("<symbol>" + token + "</symbol>");
        // {
        token = tokens.remove();
        write("<symbol>" + token + "</symbol>");
        compileStatements();
        // }
        token = tokens.remove();
        write("<symbol>" + token + "</symbol>");
        //s是否是else
        token = tokens.element();
        if (JackTokenizer.ELSE.equals(token))
        {
            // else
            token = tokens.remove();
            write("<keyword>" + token + "</keyword>");
            // {
            token = tokens.remove();
            write("<symbol>" + token + "</symbol>");
            compileStatements();
            // }
            token = tokens.remove();
            write("<symbol>" + token + "</symbol>");
        }
        write("</ifStatement>");
    }

    //expressionless
    public void compileExpression() throws IOException
    {
        write("<expression>");
        compileTerm();
        write("</expression>");
    }

    public void compileTerm() throws IOException
    {
        Queue<String> tokens = tokenizer.getTokens();
        // if ( x )
        String token = tokens.remove();
        write("<term>");
        write("<identifier>" + token + "</identifier>");
        write("</term>");
    }

    public void compileExpressionList() throws IOException
    {
        Queue<String> tokens = tokenizer.getTokens();
        // ) or xx
        String token = tokens.element();
        if (!")".equals(token))
        {
            compileExpression();
            //, or )
            token = tokens.element();
            while (!")".equals(token))
            {
                //,
                token = tokens.remove();
                write("<symbol>" + token + "</symbol>");
//                token = tokens.remove();
                compileExpression();
                token = tokens.element();
            }
        }

    }
}
