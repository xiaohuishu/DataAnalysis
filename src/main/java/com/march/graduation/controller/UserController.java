package com.march.graduation.controller;

import com.march.graduation.model.User;
import com.march.graduation.web.annotation.ClientIp;
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

import java.util.List;

// ********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-18 时间: 下午2:17
// ********************************************
@Controller
@RequestMapping("/user")
public class UserController implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private List<User> userList;

    @RequestMapping(value = "/invalidate", method = { RequestMethod.GET })
    public ModelAndView invalidate(@RequestParam(value = "username", defaultValue = "") String username) {
        if (StringUtils.isBlank(username)) {
            return new ModelAndView("login");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("start invalidate username: {}", username);
        }

        return new ModelAndView("login");
    }

    @RequestMapping(value = { "/login", "lg" }, method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView login(@ClientIp String clientIP,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password) {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return new ModelAndView("login");
        }
        logger.info("ip: {} access login username: {}", clientIP, username);
        User user = new User(username, password);

        boolean isSuccess = false;

        for (User tmpUser : userList) {
            if (user.equals(tmpUser)) {
                isSuccess = true;
                break;
            }
        }
        if (isSuccess) {
            return new ModelAndView("index").addObject("username", username);
        }
        return new ModelAndView("login").addObject("username", username).addObject("errMsg", "帐号密码验证不通过");
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(userList, "userList must be not null");
    }
}
