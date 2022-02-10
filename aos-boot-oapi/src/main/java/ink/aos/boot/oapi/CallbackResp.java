package ink.aos.boot.oapi;

import lombok.Data;

@Data
public class CallbackResp {

    private String code;//结果编码	是	String(4)	0000 表示成功

    private String message;//结果描述	是	String(128)	结果描述

}
