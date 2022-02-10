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
public class PtApiReq extends ApiReq {

    private String targetSysCode; // 目标系统标识

    private String targetSysUrl; // 目标系统接口地址

}
