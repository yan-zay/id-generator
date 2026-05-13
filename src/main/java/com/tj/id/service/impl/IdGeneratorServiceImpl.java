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

@Service
@AllArgsConstructor
@Slf4j
public class IdGeneratorServiceImpl implements IdGeneratorService {

    private final ConcurrentHashMap<BizTypeEnum, SegmentIdDTO> map = new ConcurrentHashMap<>();

    private final IdServiceImpl idService;
    private final IdProperties idProperties;

    @Override
    public String nextId(BizTypeEnum type, boolean prefix) {
        SegmentIdDTO segmentId = map.compute(type, (k, v) -> {
            if (v == null || !v.useful()) {
                return idService.getNextSegmentId(type);
            }
            return v;
        });
        init(segmentId);
        long id = segmentId.getCurrentId().getAndAdd(segmentId.getDelta());
        String idStr = String.format("%0" + idProperties.getLength() + "d", id);
        return prefix ? type.getValue() + "_" + idStr : idStr;
    }

    private void init(SegmentIdDTO segmentId) {
        AtomicLong currentId = segmentId.getCurrentId();
        int delta = segmentId.getDelta();
        int remainder = segmentId.getRemainder();
        long id = currentId.get();
        if (id % delta == remainder) {
            return;
        }
        synchronized (segmentId) {
            id = currentId.get();
            if (id % delta == remainder) {
                return;
            }
            for (int i = 0; i <= delta; i++) {
                id = currentId.incrementAndGet();
                if (id % delta == remainder) {
                    currentId.addAndGet(-delta);
                    return;
                }
            }
        }
    }
}
