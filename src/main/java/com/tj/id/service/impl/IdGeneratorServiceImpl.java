package com.tj.id.service.impl;

import com.tj.id.dto.SegmentIdDTO;
import com.tj.id.enums.BizTypeEnum;
import com.tj.id.service.IdGeneratorService;
import com.tj.id.properties.IdProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zay
 * @Date: 2023/7/18 10:01
 */
@Service
@AllArgsConstructor
@Slf4j
public class IdGeneratorServiceImpl implements IdGeneratorService {

    private final static ConcurrentHashMap<BizTypeEnum, SegmentIdDTO> map = new ConcurrentHashMap<>();

    private final IdServiceImpl idService;
    private final IdProperties idProperties;

    /**
     * 通过此方法调用IdServiceImpl否则spring事务不生效
     * 加锁
     * 先看map里面是否有当前号段对象
     * 无去获取
     * 有 处理后返回
     */
    @Override
//    @Lock4j(keys = "'IdGeneratorService:nextId:'+ #type.getValue()", expire = 10000, acquireTimeout = 5000)
    public String nextId(BizTypeEnum type, boolean prefix) {
        if (!map.containsKey(type) || !map.get(type).useful()) {
            SegmentIdDTO segmentIdDTO = this.idService.getNextSegmentId(type);
            map.put(type, segmentIdDTO);
        }
        SegmentIdDTO segmentId = map.get(type);
        AtomicLong currentId = segmentId.getCurrentId();
        init(segmentId);
        long id = currentId.getAndAdd(segmentId.getDelta());
        String idStr = String.format("%0" + idProperties.getLength() + "d", id);
        return prefix ? type.getValue() + "_" + idStr : idStr;
    }

    /**
     * 这个方法主要为了1,4,7,10...这种序列准备的
     * 设置好初始值之后，会以delta的方式递增，保证无论开始id是多少都能生成正确的序列
     * 如当前是号段是(1000,2000]，delta=3, remainder=0，则经过这个方法后，currentId会先递增到1002,之后每次增加delta
     * 因为currentId会先递增，所以会浪费一个id，所以做了一次减delta的操作，实际currentId会从999开始增，第一个id还是1002
     */
    private void init(SegmentIdDTO segmentId) {
        AtomicLong currentId = segmentId.getCurrentId();
        int delta = segmentId.getDelta();
        int remainder = segmentId.getRemainder();
        long id = currentId.get();
        if (id % delta == remainder) {
            return;
        }
        for (int i = 0; i <= delta; i++) {
            id = currentId.incrementAndGet();
            if (id % delta == remainder) {
                // 避免浪费 减掉系统自己占用的一个id
                currentId.addAndGet(-delta);
                return;
            }
        }
    }
}
