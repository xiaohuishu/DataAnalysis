package com.march.graduation.dao.position.impl;

import com.march.graduation.base.dao.BaseDao;
import com.march.graduation.dao.position.IPositionDao;
import com.march.graduation.model.craw.RecruitmentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// ********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-22 时间: 下午1:10
// ********************************************
public class PositionDaoImpl extends BaseDao implements IPositionDao {

    private static final Logger logger = LoggerFactory.getLogger(PositionDaoImpl.class);

    private static final String TABLE_NAME = "position_data";

    private static final String ALL_COLUMN = " orderBy, leaderName, companySize, deliverCount, flowScore, workYear,"
            + " education, financeStage, pvScore, city, createTimeSort, companyId,"
            + " industryField, score, relScore, formatCreateTime, salary, positionName,"
            + " companyName, jobNature, positionTypesMap, totalCount, positionFirstType,"
            + " createTime, positionId, companyShortName, showCount, haveDeliver, hrScore,"
            + " plus, positionType, positionAdvantage ";

    private static final String POSITION_INSERT_SQL = "insert into " + TABLE_NAME + "(" + ALL_COLUMN + ")"
            + " values(:orderBy, :leaderName, :companySize, :deliverCount, :flowScore, :workYear, :education,"
            + " :financeStage, :pvScore, :city, :createTimeSort, :companyId, :industryField, :score, :relScore,"
            + " :formatCreateTime, :salary, :positionName, :companyName, :jobNature, :positionTypesMap, :totalCount,"
            + " :positionFirstType, :createTime, :positionId, :companyShortName, :showCount, :haveDeliver, :hrScore,"
            + " :plus, :positionType, :positionAdvantage)";

    @Override
    public int[] batchInsertPositionData(List<RecruitmentInfo> recruitmentInfoList) {
        logger.info("start batch insertPosition...");
        return this.batchInsertImpl(recruitmentInfoList, POSITION_INSERT_SQL);
    }

    @Override
    public long insertPositionData(RecruitmentInfo recruitmentInfo) {
        return this.insertAndReturnKeyImpl(recruitmentInfo, POSITION_INSERT_SQL);
    }
}
