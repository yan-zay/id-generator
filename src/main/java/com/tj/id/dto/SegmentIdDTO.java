package com.tj.id.dto;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zay
 * @Date: 2023/7/17 17:06
 */
@Data
public class SegmentIdDTO {

    private volatile long maxId;
    private AtomicLong currentId;
    private int delta;
    private int remainder;

    public boolean useful() {
        return currentId.get() <= maxId;
    }
}
