package com.tj.dto;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zay
 * @Date: 2023/7/17 17:06
 */
@Data
public class SegmentIdDTO {

    private long maxId;
    private AtomicLong currentId;
    /**
     * increment by
     */
    private int delta;
    /**
     * mod num
     */
    private int remainder;

    private volatile boolean isInit;

    public boolean useful() {
        return currentId.get() <= maxId;
    }
}
