package ink.aos.boot.ap.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EnumDef implements IEnumDef {

    private String name;
    private String label;

}
