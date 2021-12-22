package ink.aos.boot.ap.util;

import ink.aos.boot.ap.api.EnumDef;
import ink.aos.boot.ap.api.IEnumDef;

public class EnumDefUtil {

    public static EnumDef toObject(IEnumDef iEnumDef) {
        return new EnumDef(iEnumDef.getCode(), iEnumDef.getLabel());
    }

    public static EnumDef[] toObject(IEnumDef[] iEnumDefs) {
        if (iEnumDefs == null) return null;
        EnumDef[] enumDefs = new EnumDef[iEnumDefs.length];
        for (int i = 0; i < iEnumDefs.length; i++) {
            enumDefs[i] = toObject(iEnumDefs[i]);
        }
        return enumDefs;
    }

}
