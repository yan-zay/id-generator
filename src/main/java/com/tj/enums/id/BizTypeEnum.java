package com.tj.enums.id;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: zay
 * @Date: 2023/7/11 14:53
 */
@Getter
@AllArgsConstructor
public enum BizTypeEnum {

    GOODS("goods", "商品新增ID"),

    ORDER("order", "订单新增ID"),
    ORDER_GOODS_ITEM("order_goods_item", "订单商品项新增ID"),
    ;

    @EnumValue
    private final String value;
    private final String desc;
}
