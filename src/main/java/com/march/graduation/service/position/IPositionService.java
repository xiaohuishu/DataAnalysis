package com.march.graduation.service.position;

import com.march.graduation.model.Condition;
import com.march.graduation.model.Limit;
import com.march.graduation.model.craw.RecruitmentInfo;

import java.util.List;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-20 时间: 下午2:22
//********************************************
public interface IPositionService {

    boolean batchInsertPositionData(List<RecruitmentInfo> recruitmentInfoList, boolean isAsy);

    long insertPositionData(RecruitmentInfo recruitmentInfo);

    List<RecruitmentInfo> queryByCondition(Condition condition, Limit limit);

    int queryCountByCondition(Condition condition);

    int queryForInt(String sql, Object... args);
}
