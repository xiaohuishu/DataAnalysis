package com.march.graduation.dao.position;

import com.march.graduation.model.Condition;
import com.march.graduation.model.Limit;
import com.march.graduation.model.craw.RecruitmentInfo;

import java.util.List;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-22 时间: 下午1:10
//********************************************
public interface IPositionDao {

    int[] batchInsertPositionData(List<RecruitmentInfo> recruitmentInfoList);

    long insertPositionData(RecruitmentInfo recruitmentInfo);

    List<RecruitmentInfo> queryByCondition(Condition condition, Limit limit);

    int queryCountByCondition(Condition condition);

    int queryBySql(String sql, Object... args);
}
