/*
 * www.aos.ink Inc.
 * Copyright (c) 2019 All Rights Reserved.
 */

package ink.aos.boot.oapi;

import lombok.Data;


/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 2019-06-20 09:36
 */
@Data
public class ApiReq {

    private String sysCode; // 系统标识

    private String orderNo; // 批次号

    private long timestamp;

    private String sign; // 签名

    private String data; // 加密后参数

    private String fileBase64;// 加密压缩的文件ZIP

}
