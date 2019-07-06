package bekyiu;


public class App
{
    public static void main( String[] args ) throws Exception
    {
        SymbolTable table = new SymbolTable();
        table.define("x", "int", "field"); // 0
        table.define("y", "int", "field"); // 1
        table.define("z", "int", "field"); // 2
        table.define("w", "int", "field"); // 3
        table.define("k", "boolean", "static"); // 0
        table.define("c", "char", "static"); // 1
        System.out.println(table.getMap());
        System.out.println(table.varCount("field"));
        System.out.println(table.typeOf("c"));
        System.out.println(table.kindOf("c"));
        System.out.println(table.indexOf("c"));
    }
}
