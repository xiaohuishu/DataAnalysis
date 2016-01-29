package com.march.graduation.service.position.impl;

import com.google.common.collect.Lists;
import com.march.graduation.dao.position.IPositionDao;
import com.march.graduation.execute.FutureHelper;
import com.march.graduation.model.Condition;
import com.march.graduation.model.Limit;
import com.march.graduation.model.craw.RecruitmentInfo;
import com.march.graduation.service.position.IPositionService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;

// ********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-20 时间: 下午2:23
// ********************************************
public class PositionServiceImpl implements IPositionService, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);

    private IPositionDao positionDao;

    public void setPositionDao(IPositionDao positionDao) {
        this.positionDao = positionDao;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(positionDao, "positionDao must be not null");
    }

    @Override
    public boolean batchInsertPositionData(List<RecruitmentInfo> recruitmentInfoList, boolean isAsy) {

        if (CollectionUtils.isEmpty(recruitmentInfoList)) {
            return false;
        }

        if (isAsy) {
            // noinspection unchecked
            FutureHelper.execute(new FutureHelper.FutureHandler<Boolean, RecruitmentInfo>() {
                @Override
                public Boolean handler(RecruitmentInfo... args) {
                    int[] returnData = positionDao.batchInsertPositionData(Lists.newArrayList(args));
                    return !(returnData == null || returnData.length <= 0);
                }

                @Override
                public void onSuccess(Boolean result, RecruitmentInfo... args) {
                    logger.info("execute batch insert success! insert size: {}", args.length);
                }

                @Override
                public void onFailure(Throwable throwable, RecruitmentInfo... args) {
                    logger.error("execute batch insert failure! throwable: {}", throwable.getMessage());
                }
            }, recruitmentInfoList.toArray(new RecruitmentInfo[recruitmentInfoList.size()]));
            return true;

        } else {
            int[] returnData = positionDao.batchInsertPositionData(recruitmentInfoList);
            return !(returnData == null || returnData.length <= 0);
        }
    }

    @Override
    public long insertPositionData(RecruitmentInfo recruitmentInfo) {
        return positionDao.insertPositionData(recruitmentInfo);
    }

    @Override
    public List<RecruitmentInfo> queryByCondition(Condition condition, Limit limit) {
        return positionDao.queryByCondition(condition, limit);
    }

    @Override
    public int queryCountByCondition(Condition condition) {
        return positionDao.queryCountByCondition(condition);
    }

    @Override
    public int queryForInt(String sql, Object... args) {
        return positionDao.queryBySql(sql, args);
    }
}
