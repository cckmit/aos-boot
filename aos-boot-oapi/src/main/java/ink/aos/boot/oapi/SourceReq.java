package ink.aos.boot.oapi;

import lombok.Data;

@Data
public class SourceReq<T> {

    private String sysCode; // 系统标识

    private String orderNo; // 批次号

    private T data;

    private String fileBase64;// 加密压缩的文件ZIP

}
