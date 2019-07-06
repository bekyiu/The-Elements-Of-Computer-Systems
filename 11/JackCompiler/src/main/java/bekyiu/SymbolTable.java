package bekyiu;

import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable
{
    //key = name(identifier)
    @Getter
    private Map<String, SymbolTableItem> map = new HashMap<>();

    public void define(String name, SymbolTableItem item)
    {
        if (map.containsKey(name))
        {
            throw new RuntimeException(name + " 重定义");
        }

        Integer count = varCount(item.getKind());
        item.setIndex(count);
        map.put(name, item);
    }

    public void define(String name, String type, String kind)
    {
        define(name, new SymbolTableItem(type, kind));
    }

    public void clear()
    {
        map.clear();
    }


    public Integer varCount(String kind)
    {
        Collection<SymbolTableItem> items = map.values();
        Integer sum = 0;
        for (SymbolTableItem item : items)
        {
            if (item.getKind().equals(kind))
            {
                sum++;
            }
        }
        return sum;
    }

    public String kindOf(String name)
    {
        SymbolTableItem symbolTableItem = map.get(name);
        if (symbolTableItem == null)
        {
            throw new RuntimeException(name + " 未定义");
        }
        return symbolTableItem.getKind();
    }

    public String typeOf(String name)
    {
        SymbolTableItem symbolTableItem = map.get(name);
        if (symbolTableItem == null)
        {
            throw new RuntimeException(name + " 未定义");
        }
        return symbolTableItem.getType();
    }

    public Integer indexOf(String name)
    {
        SymbolTableItem symbolTableItem = map.get(name);
        if (symbolTableItem == null)
        {
            throw new RuntimeException(name + " 未定义");
        }
        return symbolTableItem.getIndex();
    }

}
