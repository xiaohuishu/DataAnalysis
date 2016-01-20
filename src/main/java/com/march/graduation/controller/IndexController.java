package com.march.graduation.controller;

import com.march.graduation.model.AnalysisResultCode;
import com.march.graduation.utils.HanyuConvertPinyinHelper;
import com.march.graduation.view.JsonAndView;
import org.apache.commons.collections.CollectionUtils;
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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

// ********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-18 时间: 下午1:26
// ********************************************
@Controller
@RequestMapping("/index")
public class IndexController implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private String crawRootPath;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        logger.info(crawRootPath);
        Path path = Paths.get(crawRootPath, "scrapy", "data", "position.txt");
        File psTypeFile = path.toFile();
        final String command = "python " + crawRootPath + "/scrapy/lagou.py";
        if(!psTypeFile.exists()) {
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
            return modelAndView;
        } else {
            try {
                List<String> psTypeList = Files.readAllLines(path, Charset.defaultCharset());
                if(CollectionUtils.isNotEmpty(psTypeList)) {
                    modelAndView.addObject("psTypeList", psTypeList);
                }

                List<String> cityList = Files.readAllLines(Paths.get(crawRootPath, "scrapy", "data", "city.txt"), Charset.defaultCharset());
                if(CollectionUtils.isNotEmpty(cityList)) {
                    modelAndView.addObject("cityList", cityList);
                }

            } catch (IOException e) {
                logger.error("read {} failure: {}", psTypeFile.getName(), e);
            }
            return modelAndView;
        }
    }


    @RequestMapping(value = "checkCrawData", method = RequestMethod.POST)
    public JsonAndView checkCrawData(@RequestParam(value = "cityPinyin", defaultValue = "") String cityPinyin,
            @RequestParam(value = "psTypePinyin", defaultValue = "") String psTypePinyin) {
        if (StringUtils.isBlank(cityPinyin) || StringUtils.isBlank(psTypePinyin)) {
            return AnalysisResultCode.ILLEGAL_ERROR.getJsonAndView();
        }
        logger.info(cityPinyin);
        File cityData = Paths.get(crawRootPath, "scrapy", "data", "positionData_" + cityPinyin + "_" + psTypePinyin + ".txt").toFile();
        if (cityData.exists()) {
            return AnalysisResultCode.SUCCESS_INFO.getJsonAndView();
        }
        return AnalysisResultCode.NOT_EXIST_ERROR.getJsonAndView();
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
            positiopnType = URLDecoder.decode(positiopnType, "UTF-8");
            logger.info(cityName + "........." + positiopnType);
        } catch (UnsupportedEncodingException e) {
            logger.error("decode failure: {}", e);
        }
        String cityPinyin = "";
        String psTypePinyin = "";
        if(StringUtils.equals(cityName, "全国")) {
            cityPinyin = "all";
        } else {
            cityPinyin = HanyuConvertPinyinHelper.convertbyString(cityName).toLowerCase();
            psTypePinyin =HanyuConvertPinyinHelper.convertbyString(positiopnType).toLowerCase();
        }
        File cityData = Paths.get(crawRootPath, "scrapy", "data", "positionData_" + cityPinyin + "_" + psTypePinyin + ".txt").toFile();

        if (logger.isDebugEnabled()) {
            logger.debug("file: {}", cityData);
        }
        logger.info(cityData.getAbsolutePath());
        if (cityData.exists()) {
            return AnalysisResultCode.REPREATE_ERROR.getJsonAndView();
        }

        String args = positiopnType + " " + cityName;
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

        return jsonAndView;
    }

    public void setCrawRootPath(String crawRootPath) {
        this.crawRootPath = crawRootPath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(crawRootPath, "crawRootPath must be not null");
    }
}
