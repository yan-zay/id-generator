package com.tj.entity.id;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tj.enums.id.BizTypeEnum;
import lombok.*;

/**
 * @Author: zay
 * @Date: 2023/7/11 15:00
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "biz_tiny_id", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TinyIdDO extends PhysicsTimeBaseDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private BizTypeEnum bizType;//业务类型
    private Long beginId;
    private Long maxId;
    private Integer step;
    private Integer delta;
    private Integer remainder;
    private Long version;
}
