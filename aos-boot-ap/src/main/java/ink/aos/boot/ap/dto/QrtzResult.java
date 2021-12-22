/*
 * www.aos.ink Inc.
 * Copyright (c) 2019 All Rights Reserved.
 */

package ink.aos.boot.ap.dto;

import lombok.Data;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 2019-06-25 20:57
 */
@Data
public class QrtzResult {

    private boolean flag = false;
    private int successNum = 0;
    private int failNum = 0;
    private int waitNum = 0;
    private String message;

}
