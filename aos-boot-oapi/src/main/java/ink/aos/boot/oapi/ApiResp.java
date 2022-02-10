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
 * @date: 2019-08-17 11:29
 */
@Data
public class ApiResp {

    private String orderNo; // 批次号

    private String code;//结果编码	是	String(4)	0000 表示成功

    private String message;//结果描述	是	String(128)	结果描述

    private String data;//返回的业务参数 加密后的

    private long timestamp;

    private String sign;

    private String fileBase64;// 加密压缩的文件ZIP

}
