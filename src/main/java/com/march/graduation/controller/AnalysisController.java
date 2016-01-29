package com.march.graduation.controller;

// ********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-26 时间: 下午8:12
// ********************************************

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.march.graduation.constant.AnalysisSQLConstant;
import com.march.graduation.constant.CacheKeyConstant;
import com.march.graduation.model.AnalysisResultCode;
import com.march.graduation.model.Condition;
import com.march.graduation.model.Limit;
import com.march.graduation.model.SalaryType;
import com.march.graduation.model.craw.RecruitmentInfo;
import com.march.graduation.redis.ShardJedisClient;
import com.march.graduation.service.position.IPositionService;
import com.march.graduation.view.JsonAndView;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/analysis")
public class AnalysisController implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisController.class);

    private ShardJedisClient shardJedisClient;

    private IPositionService positionService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView analysis() {
        
        Collection collection = Collections2.filter(shardJedisClient.lrange(CacheKeyConstant.CITY_LIST_KEY, 0, -1), new Predicate<String>() {
            @Override public boolean apply(String input) {
                return !StringUtils.equals("全国", input);
            }
        });

        return new ModelAndView("analysis/stats").addObject("cityList", collection).addObject("psTypeList",
                shardJedisClient.lrange(CacheKeyConstant.POSITION_LIST_KEY, 0, -1));
    }

    @RequestMapping(value = "/analysisDemandByCity", method = RequestMethod.POST)
    public JsonAndView analysisDemandByCity(@RequestParam(value = "citys", defaultValue = "") String citys) {

        if (StringUtils.isBlank(citys)) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }

        String[] cityArray = StringUtils.split(citys, ",");
        Map<String, Object> cityDemandMap = new HashMap<>(cityArray.length);

        for (String city : cityArray) {
            if(StringUtils.isNotBlank(city)) {
                int positionCount = positionService.queryForInt(AnalysisSQLConstant.POSITION_QUERY_COUNTBYCITY, city.trim());
                int companyCount = positionService.queryForInt(AnalysisSQLConstant.POSITION_QUERY_COUNTBYCOMPANY, city.trim());
                cityDemandMap.put(city, positionCount + "," + companyCount);
            }
        }
        if (MapUtils.isEmpty(cityDemandMap)) {
            return AnalysisResultCode.NOT_EXIST_ERROR.getJsonAndView();
        }
        JsonAndView jsonAndView = AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
        jsonAndView.addAllData(cityDemandMap);

        return jsonAndView;
    }


    @RequestMapping(value = "/analysisSalaryByPosition", method = RequestMethod.POST)
    public JsonAndView analysisSalaryByPosition(@RequestParam(value = "psType", defaultValue = "") String psType,
            @RequestParam(value = "citys", defaultValue = "") String citys) {

        if(StringUtils.isBlank(psType) || StringUtils.isBlank(citys)) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }

        psType = replaceSpecial(psType.toLowerCase().replace(".", "").replace("-", ""));
        String[] cityArray = StringUtils.split(citys, ",");
        Condition condition = new Condition();
        condition.setValue(psType);
        Limit limit = new Limit(0);
        Map<String, Object> salaryTrendMap = new HashMap<>(cityArray.length);

        for(String city : cityArray) {
            if(StringUtils.isNotBlank(city)) {
                condition.setSecondValue(city);
                List<RecruitmentInfo> recruitmentInfoList = positionService.queryByCondition(condition, limit);
                Collection<String> salaryList = null;
                if(CollectionUtils.isNotEmpty(recruitmentInfoList)) {
                    salaryList = Collections2.transform(recruitmentInfoList, new Function<RecruitmentInfo, String>() {
                        @Override public String apply(RecruitmentInfo input) {
                            return StringUtils.defaultString(input.getSalary());
                        }
                    });
                }
                salaryTrendMap.put(city, SalaryType.caculSalarySize(salaryList));
            }
        }
        if (MapUtils.isEmpty(salaryTrendMap)) {
            return AnalysisResultCode.NOT_EXIST_ERROR.getJsonAndView();
        }
        JsonAndView jsonAndView = AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
        jsonAndView.addAllData(salaryTrendMap);

        return jsonAndView;
    }

    private String replaceSpecial(String psTypePinyin) {
        if(StringUtils.equals(psTypePinyin, "cplus")) {
            psTypePinyin = "c++";
        }
        if(StringUtils.equals(psTypePinyin, "cs")) {
            psTypePinyin = "c#";
        }
        return psTypePinyin;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(shardJedisClient, "shardJedisClient must be not null");
        Assert.notNull(positionService, "positionService must be not null");
    }

    public void setShardJedisClient(ShardJedisClient shardJedisClient) {
        this.shardJedisClient = shardJedisClient;
    }

    public void setPositionService(IPositionService positionService) {
        this.positionService = positionService;
    }
}
