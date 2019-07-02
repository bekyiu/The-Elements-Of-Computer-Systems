package bekyiu;

import lombok.Getter;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void main(String[] args) throws Exception
    {
        JackTokenizer jackTokenizer = new JackTokenizer("ArrayTest/Main.jack");
        List<String> tokens = jackTokenizer.getTokens();
        BufferedWriter bw = new BufferedWriter(new FileWriter("test.xml"));
        for (String token : tokens)
        {
            String type = jackTokenizer.tokenType(token);
            String line = "<" + type.toLowerCase() + "> " + token + " <" + type.toLowerCase() + "/>";
            bw.write(line);
            bw.newLine();
            bw.flush();
        }
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
            while ((line = br.readLine()) != null)
            {
                if (line.equals("") || line.startsWith("//") || line.startsWith("/*"))
                {
                    continue;
                }
                String s = filter(line);
                System.out.println(s);
                if (!s.equals(""))
                {
                    addTokens(s);
                }
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
                if (br != null)
                {

                    br.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void addTokens(String s)
    {
        // 按空格分割, 但是排除字符串中的空格
        String[] split = JackTokenizer.splitPlus(s);
        for (String sp : split)
        {
            // 如果sp是下列之一, 就直接添加了
            if (symbols.contains(sp) || keywords.contains(sp) || isIdentifier(sp) || isIntegerConstants(sp))
            {
                tokens.add(sp);
                continue;
            }

            //
            int start = 0;
            for (int end = 0; end < sp.length(); end++)
            {
                String sub = sp.substring(start, end + 1);
                if (symbols.contains(sub) || keywords.contains(sub))
                {
                    tokens.add(sub);
                    start = end + 1;
                    continue;
                }

                // 如果当前end + 1指向的是一个symbol, 那么从start到end一定是个identifier
                // 第一个条件用于短路, 不然后面 charAt可能会越界
                if (end + 1 < sp.length() && symbols.contains(String.valueOf(sp.charAt(end + 1))))
                {
                    tokens.add(sp.substring(start, end + 1));
                    start = end + 1;
                    continue;
                }

                // 处理字符串, 此时start指向 ", end + 1指向 " 的下一个字符
                if (sub.startsWith("\""))
                {
                    String temp = "";
                    for (int j = end + 1; ; j++)
                    {
                        if (sp.charAt(j) != '"')
                        {
                            temp += sp.charAt(j);
                        }
                        else
                        {
                            start = j + 1;
                            end = j;
                            break;
                        }
                    }
                    tokens.add(temp);
                    continue;
                }

                // 可能有while (temp < 10)中 (temp 的这种情况
                // 如果start - 1指向的是symbol, 并且start指向的不是 " , 那么从start到sp.length
                // 或者从start到下一个symbol之间的一定是一个identifier
                if (start - 1 >= 0 && symbols.contains(String.valueOf(sp.charAt(start - 1))))
                {
                    int j = 0;
                    for (; j < sp.length(); j++)
                    {
                        if()
                    }
                    tokens.add(sub);
                    start = end + 1;
                    continue;
                }
            }
        }
    }

    public static boolean isIntegerConstants(String token)
    {
        if (Pattern.matches("\\d+", token))
        {
            Integer integer = Integer.valueOf(token);
            return integer >= 0 && integer <= 32767;
        }
        return false;
    }

    // 好像没什么卵用
    public static boolean isStringConstants(String token)
    {
        return Pattern.matches("\"([\\s\\S]*?)\"", token);
    }

    //A~Z 65~90, a~z 97~122, 0~9 48~57, _ 95
    public static boolean isIdentifier(String token)
    {
        char first = token.charAt(0);
        if (first >= 48 && first <= 57)
        {
            return false;
        }
        return Pattern.matches("\\w+", token);
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
        if (keywords.contains(token))
        {
            return KEYWORD;
        }
        if (symbols.contains(token))
        {
            return SYMBOL;
        }
        if (isIdentifier(token))
        {
            return IDENTIFIER;
        }
        if (isIntegerConstants(token))
        {
            return INT_CONST;
        }
//        if (isStringConstants(token))
//        {
//            return STRING_CONST;
//        }
        // token是不带双引号的 所以没有办法通过isStringConstants判断
        return STRING_CONST;
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

    // 按空格分割, 但是排除字符串中的空格
    public static String[] splitPlus(String s)
    {
        // 先取出双引号及其内容 "([\s\S]*?)"
        Pattern p = Pattern.compile("\"([\\s\\S]*?)\"");
        Matcher matcher = p.matcher(s);
        Queue<String> reps = new ArrayDeque<>();

        // 将所有的 "xxxx" 一律替换为 !***!
        while (matcher.find())
        {
            //"xxxx"
            String temp = matcher.group();
            reps.add(temp);
            s = s.replace(temp, "!***!");
        }

        String[] sp = s.split(" ");
        // 再替换回来
        for (int i = 0; i < sp.length; i++)
        {
            if (sp[i].contains("!***!"))
            {
                sp[i] = sp[i].replace("!***!", reps.remove());

            }
        }
        return sp;
    }
}
