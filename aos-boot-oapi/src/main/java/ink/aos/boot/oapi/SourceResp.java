package ink.aos.boot.oapi;

import lombok.Data;

@Data
public class SourceResp<T> {

    private String orderNo; // 批次号

    private String code;//结果编码	是	String(4)	0000 表示成功

    private String message;//结果描述	是	String(128)	结果描述

    private T data;//返回的业务参数 加密后的

    private String fileBase64;// 加密压缩的文件ZIP

}
