package ink.aos.boot.ap.dto.ref;

import ink.aos.boot.ap.dto.RefUITypeEnum;
import lombok.Data;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 5/7/21
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@Data
public class RefDefVM {

    private String id;
    private String code;
    private String name;
    private RefUITypeEnum uiType;

}
