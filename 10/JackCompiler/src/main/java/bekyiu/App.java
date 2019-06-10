package bekyiu;


import java.io.File;
import java.io.RandomAccessFile;

public class App
{
    public static void main( String[] args ) throws Exception
    {
        File f = new File("test.xml");
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
//        String s = raf.readLine();
//        System.out.println("s = " + s);
//        System.out.println(raf.getFilePointer());
//        raf.write("sb".getBytes());
//        System.out.println(raf.getFilePointer());
//        raf.seek(raf.length() - 2);
//        System.out.println(raf.readChar());
//        raf.seek(0);
        long where = raf.length() - 9;
        raf.seek(where);
        String s = raf.readLine();
        System.out.println(s);
        raf.seek(where - 1);
        raf.write("\n11".getBytes());
        raf.seek(where - 1 + 3);
//        raf.write("\n<z>xx</z>".getBytes());
        raf.write(s.getBytes());
    }
}
