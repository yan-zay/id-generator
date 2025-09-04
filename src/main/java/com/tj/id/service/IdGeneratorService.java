package com.tj.id.service;

import com.tj.id.enums.BizTypeEnum;

/**
 * @Author: zay
 * @Date: 2023/7/18 9:59
 */
public interface IdGeneratorService {

    String nextId(BizTypeEnum type, boolean prefix);
}
