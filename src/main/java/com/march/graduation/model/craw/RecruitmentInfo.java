package com.march.graduation.model.craw;

import java.io.Serializable;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-21 时间: 下午5:17
//********************************************
public class RecruitmentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int orderBy;
    private String leaderName;
    private String companySize;
    private int deliverCount;
    private int flowScore;
    private String workYear;
    private String education;
    private String financeStage;
    private double pvScore;
    private String city;
    private long createTimeSort;
    private long companyId;
    private String industryField;
    private int score;
    private int relScore;
    private String formatCreateTime;
    private String salary;
    private String positionName;
    private String companyName;
    private String jobNature;
    private String positionTypesMap;
    private int totalCount;
    private String positionFirstType;
    private String createTime;
    private long positionId;
    private String companyShortName;
    private int showCount;
    private String haveDeliver;
    private int hrScore;
    private String plus;
    private String positionType;
    private String positionAdvantage;

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public int getFlowScore() {
        return flowScore;
    }

    public void setFlowScore(int flowScore) {
        this.flowScore = flowScore;
    }

    public int getDeliverCount() {
        return deliverCount;
    }

    public void setDeliverCount(int deliverCount) {
        this.deliverCount = deliverCount;
    }

    public String getWorkYear() {
        return workYear;
    }

    public void setWorkYear(String workYear) {
        this.workYear = workYear;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getFinanceStage() {
        return financeStage;
    }

    public void setFinanceStage(String financeStage) {
        this.financeStage = financeStage;
    }

    public double getPvScore() {
        return pvScore;
    }

    public void setPvScore(double pvScore) {
        this.pvScore = pvScore;
    }

    public long getCreateTimeSort() {
        return createTimeSort;
    }

    public void setCreateTimeSort(long createTimeSort) {
        this.createTimeSort = createTimeSort;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getIndustryField() {
        return industryField;
    }

    public void setIndustryField(String industryField) {
        this.industryField = industryField;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRelScore() {
        return relScore;
    }

    public void setRelScore(int relScore) {
        this.relScore = relScore;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPositionFirstType() {
        return positionFirstType;
    }

    public void setPositionFirstType(String positionFirstType) {
        this.positionFirstType = positionFirstType;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public String getPositionAdvantage() {
        return positionAdvantage;
    }

    public void setPositionAdvantage(String positionAdvantage) {
        this.positionAdvantage = positionAdvantage;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getPlus() {
        return plus;
    }

    public void setPlus(String plus) {
        this.plus = plus;
    }

    public int getHrScore() {
        return hrScore;
    }

    public void setHrScore(int hrScore) {
        this.hrScore = hrScore;
    }

    public String getHaveDeliver() {
        return haveDeliver;
    }

    public void setHaveDeliver(String haveDeliver) {
        this.haveDeliver = haveDeliver;
    }

    public int getShowCount() {
        return showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getJobNature() {
        return jobNature;
    }

    public void setJobNature(String jobNature) {
        this.jobNature = jobNature;
    }

    public String getPositionTypesMap() {
        return positionTypesMap;
    }

    public void setPositionTypesMap(String positionTypesMap) {
        this.positionTypesMap = positionTypesMap;
    }

    public String getFormatCreateTime() {
        return formatCreateTime;
    }

    public void setFormatCreateTime(String formatCreateTime) {
        this.formatCreateTime = formatCreateTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
