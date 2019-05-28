//package bekyiu;
//
//import java.io.*;
//
//public class NoSymbolAssembler
//{
//    private Parser parser = new Parser();
//    private Code code = new Code();
//
//    public static void main(String[] args)
//    {
//        new NoSymbolAssembler().build("Add.asm");
//    }
//
//    //构建.hack文件
//    private void build(String fileName)
//    {
//        File asmFile = new File(fileName);
//        File hackFile = new File(fileName.split("\\.")[0] + ".hack");
//        BufferedWriter bw = null;
//        BufferedReader br = null;
//
//        try
//        {
//            br = new BufferedReader(new FileReader(asmFile));
//            bw = new BufferedWriter(new FileWriter(hackFile));
//            String line = "";
//
//            while ((line = br.readLine()) != null)
//            {
//                String binary = getBinaryCode(line);
//                if (binary != null)
//                {
//                    bw.write(binary);
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            close(br, bw);
//        }
//    }
//
//    //把读到的一行转换成一行二进制
//    private String getBinaryCode(String line)
//    {
//        String c = Parser.filter(line);
//        String target = "";
//        //不知道为什么会读到 ""
//        if (c.equals("") || c.startsWith("//"))
//        {
//            return null;
//        }
//
//        System.out.println(c);
//        //A or C or L ?
//        String type = parser.commandType(c);
//
//        if (type.equals(Parser.A_COMMAND))
//        {
//            target = a_cmdBinaryCode(c);
//        }
//        else if (type.equals(Parser.C_COMMAND))
//        {
//            target = c_cmdBinaryCode(c);
//        }
//        return target;
//    }
//
//    // 把a指令转换成对应的二进制
//    private String a_cmdBinaryCode(String c)
//    {
//        String symbol = parser.symbol(c);
//        String binary = Integer.toBinaryString(Integer.valueOf(symbol));
//        StringBuilder sb = new StringBuilder(16);
//        //补全16位
//        for (int i = 0; i < 16 - binary.length(); i++)
//        {
//            sb.append("0");
//        }
//        return sb.append(binary).append("\n").toString();
//    }
//
//    // 把c指令转换成对应的二进制
//    private String c_cmdBinaryCode(String c)
//    {
//        String comp = code.comp(parser.comp(c));
//        String dest = code.dest(parser.dest(c));
//        String jump = code.jump(parser.jump(c));
//        StringBuilder sb = new StringBuilder();
//        sb.append("111").append(comp).append(dest).append(jump).append("\n");
//        return sb.toString();
//    }
//
//    private void close(BufferedReader br, BufferedWriter bw)
//    {
//        try
//        {
//            if (br != null)
//            {
//                br.close();
//            }
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//                if (bw != null)
//                {
//                    bw.close();
//                }
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }
//}
