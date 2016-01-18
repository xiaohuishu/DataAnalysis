package com.march.graduation.controller;

import com.march.graduation.web.annotation.ClientIp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

// ********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-18 时间: 下午2:17
// ********************************************
@Controller
@RequestMapping("/user")
public class UserController implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/invalidate", method = { RequestMethod.GET })
    public ModelAndView invalidate(@RequestParam(value = "username", defaultValue = "")String username) {
        if(StringUtils.isBlank(username)) {
            return new ModelAndView("login");
        }
        if(logger.isDebugEnabled()) {
            logger.debug("start invalidate username: {}", username);
        }

        return new ModelAndView("login");
    }

    @RequestMapping(value = {"/login", "lg"}, method = RequestMethod.POST)
    public ModelAndView login(@ClientIp String clientIP,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "")String password) {
        return new ModelAndView("login");
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
