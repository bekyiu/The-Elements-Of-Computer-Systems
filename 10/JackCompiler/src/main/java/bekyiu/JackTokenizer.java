package bekyiu;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 把.jack文件转换为token流
public class JackTokenizer
{
    // 用来存放.jack文件中遇到的token
    @Getter
    private List<String> tokens = new ArrayList<>();
    /*
    以下是jack支持的token, 除此以外还有
    integerConstants 0~32767的十进制整数,
    stringConstants 一系列被双引号引起的字符 但不包括双引号和\n
    identifier 由字母数字下划线组成, 但不能以数字开头
     */
    private List<String> keywords = new ArrayList<>();
    private List<String> symbols = new ArrayList<>();

    // token的类型
    public static final String KEYWORD = "KEYWORD";
    public static final String SYMBOL = "SYMBOL";
    public static final String IDENTIFIER = "IDENTIFIER";
    public static final String INT_CONST = "INT_CONST";
    public static final String STRING_CONST = "STRING_CONST";

    // keyword
    public static final String CLASS = "CLASS";
    public static final String METHOD = "METHOD";
    public static final String INT = "INT";
    public static final String FUNCTION = "FUNCTION";
    public static final String BOOLEAN = "BOOLEAN";
    public static final String CONSTRUCTOR = "CONSTRUCTOR";
    public static final String CHAR = "CHAR";
    public static final String VOID = "VOID";
    public static final String VAR = "VAR";
    public static final String STATIC = "STATIC";
    public static final String FIELD = "FIELD";
    public static final String LET = "LET";
    public static final String DO = "DO";
    public static final String IF = "IF";
    public static final String ELSE = "ELSE";
    public static final String WHILE = "WHILE";
    public static final String RETURN = "RETURN";
    public static final String TRUE = "TRUE";
    public static final String FALSE = "FALSE";
    public static final String NULL = "NULL";
    public static final String THIS = "THIS";

    private void init()
    {
        keywords.add("class");
        keywords.add("constructor");
        keywords.add("function");
        keywords.add("method");
        keywords.add("field");
        keywords.add("static");
        keywords.add("var");
        keywords.add("int");
        keywords.add("char");
        keywords.add("boolean");
        keywords.add("void");
        keywords.add("true");
        keywords.add("false");
        keywords.add("null");
        keywords.add("this");
        keywords.add("let");
        keywords.add("do");
        keywords.add("if");
        keywords.add("else");
        keywords.add("while");
        keywords.add("return");

        symbols.add("{");
        symbols.add("}");
        symbols.add("(");
        symbols.add(")");
        symbols.add("[");
        symbols.add("]");
        symbols.add(".");
        symbols.add(",");
        symbols.add(";");
        symbols.add("+");
        symbols.add("-");
        symbols.add("*");
        symbols.add("/");
        symbols.add("&");
        symbols.add("|");
        symbols.add("<");
        symbols.add(">");
        symbols.add("=");
        symbols.add("~");
    }
    public static void main(String[] args)
    {
        System.out.println(new JackTokenizer("ArrayTest/Main.jack"));

    }
    public JackTokenizer(String path)
    {
        init();
        File file = new File(path);
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while((line = br.readLine()) != null)
            {
                if(line.equals("") || line.startsWith("//") || line.startsWith("/*"))
                {
                    continue;
                }
                String s = filter(line);
                System.out.println(s);
                addTokens(s);
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
                br.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void addTokens(String s)
    {
        String[] split = s.split(" ");
        for (String sp : split)
        {
            for (int i = 0; i < sp.length(); i++)
            {

            }
        }
    }

    public static boolean isIntegerConstants(String token)
    {
        Integer integer = null;
        try
        {
            integer = Integer.valueOf(token);
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            return false;
        }
        return integer >= 0 && integer <= 32767;
    }
    public static boolean isStringConstants(String token)
    {
        if(token.startsWith("\"") && token.endsWith("\""))
        {
            if(!token.equals("\"") && !token.equals("\n"))
            {
                return true;
            }
        }
        return false;
    }

    //A~Z 65~90, a~z 97~122, 0~9 48~57, _ 95
    public static boolean isIdentifier(String token)
    {
        for (int i = 0; i < token.length(); i++)
        {
            int ch = token.charAt(i);
            if((ch >= 65 && ch<=90) || (ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57) || ch == 95)
            {
                if(i == 0)
                {
                    if(ch >= 48 && ch <= 57)
                    {
                        return false;
                    }
                }
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    // 仅当type 为keyword时调用
    public String keyword(String token)
    {
        return token.toUpperCase();
    }

    // 仅当type 为symbol是调用
    public String symbol(String token)
    {
        return token;
    }

    public Integer intVal(String token)
    {
        return Integer.valueOf(token);
    }

    public String stringVal(String token)
    {
        return token.substring(1, token.lastIndexOf("\""));
    }
    public String tokenType(String token)
    {
        if(keywords.contains(token))
        {
            return KEYWORD;
        }
        if(symbols.contains(token))
        {
            return SYMBOL;
        }
        if(isIdentifier(token))
        {
            return IDENTIFIER;
        }
        if(isIntegerConstants(token))
        {
            return INT_CONST;
        }
        if(isStringConstants(token))
        {
            return STRING_CONST;
        }
        return null;
    }

    // 注释可能会跟在语句后面
    public static String escapeComment(String str)
    {
        if (str.contains("//"))
        {
            return str.substring(0, str.indexOf("/"));
        }
        return str;
    }

    // 去掉前后的空格就可以了, 中间的空格便于解析
    public static String filter(String str)
    {
        str = escapeComment(str);
        return str.trim();
    }
}
