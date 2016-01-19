package com.march.graduation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

// ********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-18 时间: 下午1:26
// ********************************************
@Controller
@RequestMapping("/index")
public class IndexController implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


    @RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull("shellRuntimeHandler", "shellRuntimeHandler must not be blank");
    }
}
