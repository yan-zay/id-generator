package com.tj.id.entity.base;

import java.io.Serializable;

/**
 * 基础实体对象
 *
 *
 */
public interface Logic extends Serializable {

    Boolean getDeleted();

    void setDeleted(Boolean deleted);

}
