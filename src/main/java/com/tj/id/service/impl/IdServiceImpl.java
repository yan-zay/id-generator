package com.tj.id.service.impl;

import com.tj.id.dto.SegmentIdDTO;
import com.tj.id.entity.TinyIdDO;
import com.tj.id.enums.BizTypeEnum;
import com.tj.id.exception.IdGenerateException;
import com.tj.id.mapper.IdMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicLong;

@Service
@AllArgsConstructor
@Slf4j
public class IdServiceImpl {

    private final IdMapper idMapper;

    /**
     * 从DB获取新号段。READ_COMMITTED隔离级别确保每次重试读到最新maxId。
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SegmentIdDTO getNextSegmentId(BizTypeEnum bizType) {
        //调用方有锁这里不重试
        TinyIdDO tinyIdInfo = idMapper.queryByBizType(bizType);
        if (tinyIdInfo == null) {
            throw new IdGenerateException(404, "未找到业务类型: " + bizType.getValue());
        }
        Long newMaxId = tinyIdInfo.getMaxId() + tinyIdInfo.getStep();
        int row = idMapper.updateMaxId(newMaxId, tinyIdInfo);
        if (row == 1) {
            tinyIdInfo.setMaxId(newMaxId);
            SegmentIdDTO segmentId = convert(tinyIdInfo);
            log.info("获取号段对象成功 TinyIdDO:{} SegmentIdDTO:{}", tinyIdInfo, segmentId);
            return segmentId;
        }
        log.info("未获取到号段对象 TinyIdDO:{}", tinyIdInfo);
        throw new IdGenerateException(409, "获取号段冲突，请重试: " + bizType.getValue());
    }

    private SegmentIdDTO convert(TinyIdDO idInfo) {
        SegmentIdDTO segmentId = new SegmentIdDTO();
        segmentId.setCurrentId(new AtomicLong(idInfo.getMaxId() - idInfo.getStep()));
        segmentId.setMaxId(idInfo.getMaxId());
        segmentId.setRemainder(idInfo.getRemainder() == null ? 0 : idInfo.getRemainder());
        segmentId.setDelta(idInfo.getDelta() == null ? 1 : idInfo.getDelta());
        return segmentId;
    }
}
