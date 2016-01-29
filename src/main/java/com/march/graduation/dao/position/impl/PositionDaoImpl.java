package com.march.graduation.dao.position.impl;

import com.march.graduation.base.dao.BaseDao;
import com.march.graduation.dao.position.IPositionDao;
import com.march.graduation.model.Condition;
import com.march.graduation.model.Limit;
import com.march.graduation.model.craw.RecruitmentInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

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

    private static final String POSITION_QUERY_COUNT = "select count(1) from " + TABLE_NAME
            + " where positionName LIKE ? and city = ?";

    private static final String POSITION_QUERY_COUNT_ALL = "select count(1) from " + TABLE_NAME
            + " where positionName LIKE ?";

    private static final String POSITION_QUERY = "select " + ALL_COLUMN + " from " + TABLE_NAME;

    private static final String POSITION_QUERY_SQL_ALL = POSITION_QUERY
            + " where positionName LIKE ? and city = ? ORDER BY deliverCount DESC";

    private static final String POSITION_QUERY_SQL_POSITION = POSITION_QUERY
            + " where positionName LIKE ? ORDER BY deliverCount DESC";

    @Override
    public List<RecruitmentInfo> queryByCondition(Condition condition, Limit limit) {

        List<RecruitmentInfo> recruitmentInfoList;
        if (StringUtils.equals(condition.getSecondValue(), "all")) {
            recruitmentInfoList = this.getJdbcTemplate().query(POSITION_QUERY_SQL_POSITION,
                    new Object[] { "%" + condition.getValue() + "%" },
                    new BeanPropertyRowMapper<RecruitmentInfo>(RecruitmentInfo.class));
        } else {
            recruitmentInfoList = this.getJdbcTemplate().query(POSITION_QUERY_SQL_ALL,
                    new Object[] { "%" + condition.getValue() + "%", condition.getSecondValue() },
                    new BeanPropertyRowMapper<RecruitmentInfo>(RecruitmentInfo.class));
        }
        if (CollectionUtils.isNotEmpty(recruitmentInfoList)) {
            return recruitmentInfoList;
        }

        return null;
    }

    @Override
    public int queryCountByCondition(Condition condition) {
        int count;
        if (StringUtils.equals(condition.getSecondValue(), "all")) {
            count = this.getJdbcTemplate().queryForInt(POSITION_QUERY_COUNT_ALL, "%" + condition.getValue() + "%");
        } else {
            count = this.getJdbcTemplate().queryForInt(POSITION_QUERY_COUNT, "%" + condition.getValue() + "%",
                    condition.getSecondValue());
        }

        return count;
    }
}
