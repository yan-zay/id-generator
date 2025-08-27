package com.tj.entity.base;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public interface Time extends Serializable {

    Date getCreateTime();

    void setCreateTime(Date createTime);

    Date getUpdateTime();

    void setUpdateTime(Date updateTime);
}
