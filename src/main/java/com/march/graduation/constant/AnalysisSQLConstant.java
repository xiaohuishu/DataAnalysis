package com.march.graduation.constant;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-27 时间: 下午6:21
//********************************************
public class AnalysisSQLConstant {

    public static final String POSITION_QUERY_COUNTBYCITY = "select count(1) from position_data where city = ?";

    public static final String POSITION_QUERY_COUNTBYCOMPANY = "select count(DISTINCT companyShortName) from position_data where city = ?";

}
