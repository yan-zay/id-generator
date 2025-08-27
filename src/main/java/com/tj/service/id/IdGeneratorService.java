package com.tj.service.id;

import com.tj.enums.id.BizTypeEnum;

/**
 * @Author: zay
 * @Date: 2023/7/18 9:59
 */
public interface IdGeneratorService {

    String nextId(BizTypeEnum type, boolean prefix);
}
