package com.tj.service.id.impl;

import com.tj.dto.SegmentIdDTO;
import com.tj.entity.id.TinyIdDO;
import com.tj.enums.id.BizTypeEnum;
import com.tj.mapper.id.IdMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 由外部调用否则spring事务不生效
 * 生成ID单独方法 getNextSegmentId()，业务调用此方法时，切勿修改
 * 调用this.getNextSegmentId()方法, 自己拼接业务字符串 前缀等返回给对应业务模块
 * 统一写在这里，，现在聚合在这是为了以后方便解耦到各个模块
 * @Author: zay
 * @Date: 2023/7/11 14:57
 * @Version: 1.0.0
 */
@Service
@AllArgsConstructor
@Slf4j
public class IdServiceImpl {

    private final IdMapper idMapper;

    /**
     * 要点
     * 全局唯一的id:无论怎样都不能重复，这是最基本的要求了
     * 高性能:基础服务尽可能耗时少，如果能够本地生成最好
     * 高可用:虽说很难实现100%的可用性，但是也要无限接近于100%的可用性
     * 简单易用: 能够拿来即用，接入方便，同时在系统设计和实现上要尽可能的简单
     * <p>
     * 适用场景:只关心id是数字，趋势递增的系统，可以容忍id不连续，有浪费的场景
     * 不适用场景:类似订单id的业务(因为生成的id大部分是连续的，容易被扫库、或者测算出订单量)
     * 原则：尽量少的依赖
     * JDK1.7+,maven,mysql, java client目前仅依赖jdk
     * <p>
     * 默认9位数，
     * 1 查ID表 查询当前的max_id信息
     * 2 计算新的max_id
     * 3 更新DB中的max_id
     * 4 更新DB中的max_id
     * 5 加了锁不考虑被其他线程获取情况(如果更新失败，则号段可能被其他线程获取，回到步骤A，进行重试)
     * <p>
     * Transactional标记保证query和update使用的是同一连接
     * 事务隔离级别应该为READ_COMMITTED,Spring默认是DEFAULT(取决于底层使用的数据库，mysql的默认隔离级别为REPEATABLE_READ)
     * <p>
     * 如果是REPEATABLE_READ，那么在本次事务中循环调用tinyIdInfoDAO.queryByBizType(bizType)获取的结果是没有变化的，也就是查询不到别的事务提交的内容
     * 所以多次调用tinyIdInfoDAO.updateMaxId也就不会成功
     *
     * @param bizType 代表业务类型，不同的业务的id隔离
     * @return 号段对象
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public SegmentIdDTO getNextSegmentId(BizTypeEnum bizType) {
        //调用方有锁这里不重试
        TinyIdDO tinyIdInfo = idMapper.queryByBizType(bizType);
        if (tinyIdInfo == null) {
//            throw ServiceExceptionUtil.exception(IdErrorCode.NOT_FIND_BIZ_TYPE, bizType);
            throw new RuntimeException();
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
//        throw ServiceExceptionUtil.exception(IdErrorCode.GET_NEXT_SEGMENT_ID_CONFLICT);
        throw new RuntimeException();
    }

    public SegmentIdDTO convert(TinyIdDO idInfo) {
        SegmentIdDTO segmentId = new SegmentIdDTO();
        segmentId.setCurrentId(new AtomicLong(idInfo.getMaxId() - idInfo.getStep()));
        segmentId.setMaxId(idInfo.getMaxId());
        segmentId.setRemainder(idInfo.getRemainder() == null ? 0 : idInfo.getRemainder());
        segmentId.setDelta(idInfo.getDelta() == null ? 1 : idInfo.getDelta());
        return segmentId;
    }
}
