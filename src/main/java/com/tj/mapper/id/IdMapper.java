package com.tj.mapper.id;

import com.tj.entity.id.TinyIdDO;
import com.tj.enums.id.BizTypeEnum;
import com.tj.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Author: zay
 * @Date: 2023/7/11 14:58
 */
@Mapper
public interface IdMapper extends BaseMapperX<TinyIdDO> {

    default TinyIdDO queryByBizType(BizTypeEnum bizType) {
        return selectOne(TinyIdDO::getBizType, bizType);
    }

    @Update("UPDATE biz_tiny_id " +
            "SET max_id = #{maxId}, update_time = now(), version = version + 1 " +
            "WHERE id = #{param.id} AND max_id = #{param.maxId} AND version = #{param.version} AND biz_type = #{param.bizType}")
    int updateMaxId(@Param("maxId") Long maxId, @Param("param") TinyIdDO condition);
}
