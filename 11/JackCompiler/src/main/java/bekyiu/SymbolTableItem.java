package bekyiu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SymbolTableItem
{
    private String type;
    private String kind;
    private Integer index = 0;

    public SymbolTableItem(String type, String kind)
    {
        this.type = type;
        this.kind = kind;
    }
}
