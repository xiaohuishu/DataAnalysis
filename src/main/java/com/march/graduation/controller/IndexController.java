package com.march.graduation.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import com.march.graduation.constant.CacheKeyConstant;
import com.march.graduation.model.AnalysisResultCode;
import com.march.graduation.model.Condition;
import com.march.graduation.model.Limit;
import com.march.graduation.model.craw.RecruitmentInfo;
import com.march.graduation.redis.ShardJedisClient;
import com.march.graduation.service.position.IPositionService;
import com.march.graduation.utils.EmptyFileChecker;
import com.march.graduation.utils.HanyuConvertPinyinHelper;
import com.march.graduation.utils.JsonUtils;
import com.march.graduation.view.JsonAndView;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// ********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-18 时间: 下午1:26
// ********************************************
@Controller
@RequestMapping("/index")
public class IndexController implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private static final TypeReference<List<RecruitmentInfo>> LIST_RECRUITMENT_TYPE = new TypeReference<List<RecruitmentInfo>>() { };

    private String crawRootPath;

    private ShardJedisClient shardJedisClient;

    private IPositionService positionService;

    private static final Cache<String, Boolean> loadCache = CacheBuilder.newBuilder().maximumSize(100)
            .initialCapacity(35).removalListener(new RemovalListener<String, Boolean>() {
                @Override
                public void onRemoval(RemovalNotification<String, Boolean> notification) {
                    logger.info("reload dir data: {}", notification.getKey());
                }
            }).build();

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        logger.info(crawRootPath);

        if(shardJedisClient.exists(CacheKeyConstant.CITY_LIST_KEY) && shardJedisClient.exists(CacheKeyConstant.POSITION_LIST_KEY)) {
            List<String> cityList = shardJedisClient.lrange(CacheKeyConstant.CITY_LIST_KEY, 0, -1);
            List<String> psTypeList = shardJedisClient.lrange(CacheKeyConstant.POSITION_LIST_KEY, 0, -1);
            if(CollectionUtils.isNotEmpty(cityList) && CollectionUtils.isNotEmpty(psTypeList)) {
                modelAndView.addObject("cityList", cityList);
                modelAndView.addObject("psTypeList", psTypeList);
            }
        } else {
            Path path = Paths.get(crawRootPath, "scrapy", "data", "position.txt");
            File psTypeFile = path.toFile();
            final String command = "python " + crawRootPath + "/scrapy/lagou.py";
            if (!psTypeFile.exists()) {
                Thread thread = new Thread(new Runnable() {
                    @Override public void run() {
                        try {
                            Runtime.getRuntime().exec(command);
                        } catch (IOException e) {
                            logger.error("exec python failure: {}", e);
                        }
                    }
                });
                thread.start();
            } else {
                try {
                    List<String> psTypeList = Files.readAllLines(path, Charset.defaultCharset());
                    if (CollectionUtils.isNotEmpty(psTypeList)) {
                        shardJedisClient.lpush(CacheKeyConstant.POSITION_LIST_KEY, psTypeList.toArray(new String[psTypeList.size()]));
                        shardJedisClient.expire(CacheKeyConstant.POSITION_LIST_KEY, 24 * 60 * 1000);
                        modelAndView.addObject("psTypeList", psTypeList);
                    }

                    List<String> cityList = Files.readAllLines(Paths.get(crawRootPath, "scrapy", "data", "city.txt"),
                            Charset.defaultCharset());
                    if (CollectionUtils.isNotEmpty(cityList)) {
                        shardJedisClient
                                .lpush(CacheKeyConstant.CITY_LIST_KEY, cityList.toArray(new String[cityList.size()]));
                        shardJedisClient.expire(CacheKeyConstant.CITY_LIST_KEY, 24 * 60 * 1000);
                        modelAndView.addObject("cityList", cityList);
                    }

                } catch (IOException e) {
                    logger.error("read {} failure: {}", psTypeFile.getName(), e);
                }
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "crawDataSearch", method = RequestMethod.POST)
    public JsonAndView crawDataSearch(@RequestParam(value = "psType", defaultValue = "") String psType,
            @RequestParam(value = "city", defaultValue = "") String city,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "limit", defaultValue = "20") int limitSize) {

        if(StringUtils.isBlank(psType) && StringUtils.isBlank(city)) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }
        psType = replaceSpecial(psType.toLowerCase().replace(".", "").replace("-", ""));

        Condition condition = new Condition();
        condition.setValue(psType);
        if(StringUtils.equals(city, "全国")) {
            condition.setSecondValue("all");
        } else {
            condition.setSecondValue(city);
        }
        //int totalSize = positionService.queryCountByCondition(condition);
        Limit limit = new Limit(pageNo * limitSize, limitSize);
        List<RecruitmentInfo> recruitmentInfoList = positionService.queryByCondition(condition, limit);
        if(CollectionUtils.isEmpty(recruitmentInfoList)) {
            return AnalysisResultCode.NOT_EXIST_ERROR.getJsonAndView();
        }
        JsonAndView jsonAndView = AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
        jsonAndView.addData("result", recruitmentInfoList);
        return jsonAndView;
    }

    @RequestMapping(value = "crawDataToDb", method = RequestMethod.GET)
    public JsonAndView crawDataToDb(@RequestParam(value = "psType", defaultValue = "") String psType,
            @RequestParam(value = "isAsy", defaultValue = "true") boolean isAsy) {

        if (StringUtils.isBlank(psType)) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }
        psType = replaceSpecial(psType);

        String cacheKey = String.format(CacheKeyConstant.POSITION_TYPE_DATA, psType);
        if (shardJedisClient.exists(cacheKey)) {
            List<String> unSerData = shardJedisClient.lrange(cacheKey, 0, -1);
            if (CollectionUtils.isNotEmpty(unSerData)) {
                List<RecruitmentInfo> recruitmentInfoData = new ArrayList<RecruitmentInfo>(unSerData.size());
                for (String serData : unSerData) {
                    try {
                        RecruitmentInfo recruitmentInfo = JsonUtils.toObjectSafe(serData, RecruitmentInfo.class);
                        recruitmentInfoData.add(recruitmentInfo);
                    } catch (IOException e) {
                        logger.error("string parse object failure: {}", e);
                    }
                }
                boolean isSuccess = positionService.batchInsertPositionData(recruitmentInfoData, isAsy);
                if (isSuccess) {
                    logger.info("record dir: " + String.format("%s", psType));
                    loadCache.put(psType, true);
                    return AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
                }
                return AnalysisResultCode.FAILURE_INFO.getJsonAndView();
            } else {
                return AnalysisResultCode.NOT_EXIST_ERROR.getJsonAndView();
            }
        } else {
            return AnalysisResultCode.NOT_EXIST_ERROR.getJsonAndView();
        }
    }

    @RequestMapping(value = "crawData", method = RequestMethod.GET)
    public JsonAndView crawData(@RequestParam(value = "psType", defaultValue = "") String psType) {
        logger.debug("crawData: " + psType);
        if (StringUtils.isBlank(psType)) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }
        psType = replaceSpecial(psType);

        Path jsonPath = Paths.get(crawRootPath, "scrapy", "data", "positionData", psType);

        if (jsonPath == null) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }

        final String cacheKey = String.format(CacheKeyConstant.POSITION_TYPE_DATA, psType);
        if (shardJedisClient.exists(cacheKey)) {
            List<String> unSerData = shardJedisClient.lrange(cacheKey, 0, -1);
            if (CollectionUtils.isNotEmpty(unSerData)) {
                List<RecruitmentInfo> recruitmentInfoData = new ArrayList<RecruitmentInfo>(unSerData.size());
                for (String serData : unSerData) {
                    try {
                        RecruitmentInfo recruitmentInfo = JsonUtils.toObjectSafe(serData, RecruitmentInfo.class);
                        recruitmentInfoData.add(recruitmentInfo);
                    } catch (IOException e) {
                        logger.error("string parse object failure: {}", e);
                    }
                }
                JsonAndView jsonAndView = AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
                jsonAndView.addData("psType", psType);
                jsonAndView.addData("size", recruitmentInfoData.size());
                jsonAndView.addData("result", recruitmentInfoData);
                return jsonAndView;
            }
        }

        File[] jsonFiles = jsonPath.toFile().listFiles(new FileFilter() {

            @Override public boolean accept(File pathname) {
                if (pathname.isDirectory())
                    return false;
                else if (pathname.getPath().endsWith(".json"))
                    return true;
                return false;
            }
        });
        if (jsonFiles == null || jsonFiles.length <= 0) {
            return AnalysisResultCode.NOT_EXIST_ERROR.getJsonAndView();
        }

        final List<RecruitmentInfo> totalDataByPsType = Lists.newArrayList();

        for (File jsonFile : jsonFiles) {
            if (EmptyFileChecker.isFileEmpty1(jsonFile)) {
                continue;
            }
            try {
                List<RecruitmentInfo> recruitmentInfoList = JsonUtils.toTypeSafeByFile(jsonFile, LIST_RECRUITMENT_TYPE);
                if (CollectionUtils.isNotEmpty(recruitmentInfoList)) {
                    totalDataByPsType.addAll(recruitmentInfoList);
                }

            } catch (IOException e) {
                logger.error("[file: {}] parse json node failure: {}", jsonFile.getName(), e);
            }
        }
        if (CollectionUtils.isEmpty(totalDataByPsType)) {
            return AnalysisResultCode.NOT_EXIST_ERROR.getJsonAndView();
        }
        if (!shardJedisClient.exists(cacheKey)) {
            String [] jsonStr = new String[totalDataByPsType.size()];
            int index = 0;
            for (RecruitmentInfo recruitmentInfo : totalDataByPsType) {
                jsonStr[index++] = JsonUtils.objectToJsonString(recruitmentInfo);
            }
            // set cache
            shardJedisClient.lpush(cacheKey, jsonStr);
            shardJedisClient.expire(cacheKey, 24 * 60 * 1000);
        }

        JsonAndView jsonAndView = AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
        jsonAndView.addData("psType", psType);
        jsonAndView.addData("size", totalDataByPsType.size());
        jsonAndView.addData("result", totalDataByPsType);

        return jsonAndView;
    }

    @RequestMapping(value = "crawDataDirList", method = RequestMethod.GET)
    public JsonAndView getCrawDataDirStatus() {
        File positionDir = Paths.get(crawRootPath, "scrapy", "data", "positionData").toFile();
        File[] files = positionDir.listFiles(new FileFilter() {

            @Override public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        if(files == null || files.length <= 0) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }
        List<String> dirNameList = new ArrayList<String>(files.length);
        for(File file : files) {
            String dirPath = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("/") + 1, file.getAbsolutePath().length());
            //noinspection ConstantConditions
            if(loadCache.getIfPresent(dirPath) != null) {
                dirNameList.add(dirPath + "_true");
            } else {
                dirNameList.add(dirPath);
            }
        }

        if(CollectionUtils.isEmpty(dirNameList)) {
            return AnalysisResultCode.FAILURE_INFO.getJsonAndView();
        }
        JsonAndView jsonAndView = AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
        jsonAndView.addData("dirList", dirNameList);
        jsonAndView.addData("dirSize", dirNameList.size());
        return jsonAndView;
    }

    @RequestMapping(value = "checkCrawData", method = RequestMethod.POST)
    public JsonAndView checkCrawData(@RequestParam(value = "cityPinyin", defaultValue = "") String cityPinyin,
            @RequestParam(value = "psTypePinyin", defaultValue = "") String psTypePinyin) {
        if (StringUtils.isBlank(cityPinyin) || StringUtils.isBlank(psTypePinyin)) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }
        File cityData = Paths.get(crawRootPath, "scrapy", "data", "positionData", psTypePinyin,
                "positionData_" + cityPinyin + "_" + psTypePinyin + ".json").toFile();
        if (cityData.exists()) {
            return AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
        }
        return AnalysisResultCode.NOT_EXIST_ERROR.getJsonAndView();
    }

    @RequestMapping(value = "/crawlDataAll", method = RequestMethod.POST)
    public JsonAndView crawDataAll(@RequestParam(value = "psType", defaultValue = "") String psType) {
        if (StringUtils.isBlank(psType)) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }
        String pythonFilePath = crawRootPath + "/scrapy/batchlagou.py ";

        psType = replaceSpecial(psType);

        File positionDir = Paths.get(crawRootPath, "scrapy", "data", "positionData", psType).toFile();

        if(positionDir.exists()) {
            if(!EmptyFileChecker.delAllFile(positionDir.getAbsolutePath())){
                logger.info("delete [dir: {}] failure", positionDir.getAbsolutePath());
            } else {
                logger.info("delete [dir: {}] success", positionDir.getAbsolutePath());
            }
        }
        final String cacheKey = String.format(CacheKeyConstant.POSITION_TYPE_DATA, psType);
        if (shardJedisClient.exists(cacheKey)) {
            shardJedisClient.del(cacheKey);
        }

        final String command = "python " + pythonFilePath + psType;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    logger.error("exec python failure: {}", e);
                }
            }
        });
        thread.start();
        JsonAndView jsonAndView = AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
        jsonAndView.addData("psType", psType);

        return jsonAndView;
    }


    @RequestMapping(value = "/crawlData", method = RequestMethod.POST)
    public JsonAndView crawlLagouData(@RequestParam(value = "positionType", defaultValue = "") String positiopnType,
            @RequestParam(value = "cityName", defaultValue = "") String cityName) {
        if (StringUtils.isBlank(cityName) || StringUtils.isBlank(positiopnType)) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }
        String pythonFilePath = crawRootPath + "/scrapy/lagou.py ";
        try {
            cityName = URLDecoder.decode(cityName, "UTF-8");
            positiopnType = URLDecoder.decode(positiopnType, "UTF-8").replace(".", "").replace("-", "");
        } catch (UnsupportedEncodingException e) {
            logger.error("decode failure: {}", e);
        }
        String cityPinyin;
        if (StringUtils.equals(cityName, "全国")) {
            cityPinyin = "all";
            cityName = cityPinyin;
        } else {
            cityPinyin = HanyuConvertPinyinHelper.convertbyString(cityName).toLowerCase().replace("zhangchun", "changchun");
        }
        String psTypePinyin = HanyuConvertPinyinHelper.convertbyString(positiopnType).toLowerCase().replace(".",
                "").replace("-", "");
        psTypePinyin = replaceSpecial(psTypePinyin);

        File cityData = Paths.get(crawRootPath, "scrapy", "data", "positionData", psTypePinyin,
                "positionData_" + cityPinyin + "_" + psTypePinyin + ".json").toFile();

        if (logger.isDebugEnabled()) {
            logger.debug("file: {}", cityData);
        }
        logger.info(cityData.getAbsolutePath());
        if (cityData.exists()) {
            return AnalysisResultCode.REPREATE_ERROR.getJsonAndView();
        }
        String args = psTypePinyin + " " + cityName;
        if(psTypePinyin.startsWith("web")) {
            args = positiopnType + " " + cityName;
        }

        final String command = "python " + pythonFilePath + args;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    logger.error("exec python failure: {}", e);
                }
            }
        });
        thread.start();
        JsonAndView jsonAndView = AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
        jsonAndView.addData("psType", positiopnType);
        jsonAndView.addData("city", cityName);
        jsonAndView.addData("cityPinyin", cityPinyin);
        jsonAndView.addData("psTypePinyin", psTypePinyin);

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

    public void setShardJedisClient(ShardJedisClient shardJedisClient) {
        this.shardJedisClient = shardJedisClient;
    }

    public void setCrawRootPath(String crawRootPath) {
        this.crawRootPath = crawRootPath;
    }

    public void setPositionService(IPositionService positionService) {
        this.positionService = positionService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(crawRootPath, "crawRootPath must be not null");
        Assert.notNull(shardJedisClient, "shardJedisClient must be not null");
        Assert.notNull(positionService, "positionService must be not null");
    }
}
