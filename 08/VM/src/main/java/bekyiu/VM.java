package bekyiu;

import java.io.*;

public class VM
{
    private Parser parser = new Parser();
    private CodeWriter codeWriter = new CodeWriter();

    public static void main(String[] args)
    {
        VM vm = new VM();
        // vm.buildSingle("FunctionCalls/SimpleFunction/", "SimpleFunction.vm")
        vm.build("FunctionCalls/StaticsTest/");
    }

    // 先将每个.vm都编译出一个.asm, 最后再合并为out.asm
    public void build(String path)
    {
        File directory = new File(path);
        if (directory.isDirectory())
        {
            // 先清除所有的.asm文件
            deleteFiles(directory, "asm");
            //翻译所有.vm文件
            translateAll(directory);
            //merge并删除中间过程的.asm文件
            mergeAndDelete(directory, "asm");
        }
    }

    // 删除directory目录下, 指定后缀的文件
    private void deleteFiles(File directory, String suffix)
    {
        File[] asmFiles = directory.listFiles((dir, name) ->
        {
            String[] split = name.split("\\.");
            return split[1].equals(suffix);
        });
        for (File file : asmFiles)
        {
            file.delete();
        }
    }

    // 翻译directory目录下的所有.vm文件
    private void translateAll(File directory)
    {
        File[] vmFiles = directory.listFiles((dir, name) ->
        {
            String[] split = name.split("\\.");
            return split[1].equals("vm");
        });
        for (File file : vmFiles)
        {
            codeWriter.setFileName(file.getName().split("\\.")[0]);
            translateSingle(directory.getPath(), file.getName());
        }
    }

    // merge directory目录下 所有后缀为suffix的文件(一边merge一边删除)
    // 最终生成out.asm文件
    private void mergeAndDelete(File directory, String suffix)
    {
        File[] files = directory.listFiles((dir, name) ->
        {
            String[] split = name.split("\\.");
            return split[1].equals(suffix);
        });

        File out = new File(directory.getPath(), "out.asm");
        BufferedReader br = null;
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new FileWriter(out, true));
            // 写入bootstrap code
            bw.write(codeWriter.writeInit());
            for (File file : files)
            {
                br = new BufferedReader(new FileReader(file));
                String line = "";
                while ((line = br.readLine()) != null)
                {
                    bw.write(line);
                    bw.newLine();
                }
                close(br, null);
                file.delete();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(br, bw);
        }
    }


    public void translateSingle(String path, String fileName)
    {
        File vmFile = new File(path, fileName);
        File asmFile = new File(path, fileName.split("\\.")[0] + ".asm");
        codeWriter.setFileName(fileName.split("\\.")[0]);

        BufferedReader br = null;
        BufferedWriter bw = null;

        try
        {
            br = new BufferedReader(new FileReader(vmFile));
            bw = new BufferedWriter(new FileWriter(asmFile));
            String line = "";
            while ((line = br.readLine()) != null)
            {
//                if(line.startsWith("//") || line.equals(""))
//                {
//                    continue;
//                }
//                bw.write("// " + line);
//                bw.newLine();
                String asm = getAsmCode(line);
                if (asm != null)
                {
                    bw.write(asm);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(br, bw);
        }
    }

    private String getAsmCode(String line)
    {
        if (line.startsWith("//") || line.equals(""))
        {
            return null;
        }
        String c = Parser.filter(line);
        System.out.println(c);
        String type = parser.commandType(c);
        String asm = null;
        if (type.equals(Parser.C_ARITHMETIC))
        {
            asm = codeWriter.writeArithmetic(c);
        }
        else if (type.equals(Parser.C_PUSH) || type.equals(Parser.C_POP))
        {
            asm = codeWriter.writePushPop(c);
        }
        else if (type.equals(Parser.C_LABEL))
        {
            asm = codeWriter.writeLabel(c);
        }
        else if (type.equals(Parser.C_GOTO))
        {
            asm = codeWriter.writeGoto(c);
        }
        else if (type.equals(Parser.C_IF))
        {
            asm = codeWriter.writeIf(c);
        }
        else if (type.equals(Parser.C_CALL))
        {
            asm = codeWriter.writeCall(c);
        }
        else if (type.equals(Parser.C_FUNCTION))
        {
            asm = codeWriter.writeFunction(c);
        }
        else if (type.equals(Parser.C_RETURN))
        {
            asm = codeWriter.writeReturn();
        }
        return asm;
    }

    private void close(BufferedReader br, BufferedWriter bw)
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
        finally
        {
            try
            {
                if (bw != null)
                {
                    bw.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
