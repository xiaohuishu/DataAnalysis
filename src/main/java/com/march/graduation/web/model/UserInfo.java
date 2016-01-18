package com.march.graduation.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;

public class UserInfo implements Serializable, Cloneable {

    private static final long serialVersionUID = 7325475146055673019L;

    public static final String USER_ID_DOMAIN_SEP = "@";

    public static final String QUNAR_DOMAIN = "qunar";

    public static final String SINA_DOMAIN = "sina";

    public static final String BAIDU_DOMAIN = "baidu";

    public static final String QQ_DOMAIN = "qq";

    public static final String RENREN_DOMAIN = "renren";

    public static final String DOUBAN_DOMAIN = "douban";

    public static final String TUAN800_DOMAIN = "tuan800";

    public static final String KAIXIN_DOMAIN = "kaixin001";

    /**
     * 微信
     */
    public static final String WEIXIN_DOMAIN = "weixin";

    public static final String[] PARTNER_DOMAINS = { SINA_DOMAIN, BAIDU_DOMAIN, QQ_DOMAIN, RENREN_DOMAIN,
                    DOUBAN_DOMAIN, TUAN800_DOMAIN, KAIXIN_DOMAIN };

    /**
     * 用户id, String类型，表示为："id@domain"，例如："123@qunar"
     */
    private String userId = "";

    @JsonIgnore
    private String userName = "";

    /**
     * 性别(0:未知,1:男,2:女)
     */
    private String gender = "0";

    /**
     * 用户头像 48 * 48
     */
    private String headImg;

    /**
     * 150 * 150
     */
    private String largeHeadImg;

    /**
     * 64 * 64
     */
    private String middleHeadImg;

    /**
     * 用户昵称
     */
    private String nickName;

    private int qunarUserId;

    /**
     * 是否绑定，仅用于判断第三方登录用户是否绑定去哪儿账号判断
     */
    private boolean bind;

    /**
     * 用户手机号码
     */
    private String mobile;

    private boolean tempUser = false;

    private long loginTime;

    /**
     * 是否激活
     */
    private boolean verified;

    /**
     * 积分
     */
    private int points;

    /**
     * 聪明旅行家
     */
    private boolean isSmartTraveler;

    public static int getQunarUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return 0;
        }
        if (userId.endsWith(QUNAR_DOMAIN)) {
            return NumberUtils.toInt(userId.replaceAll("\\D", ""));
        }
        if (StringUtils.isNumeric(userId)) {
            return NumberUtils.toInt(userId);
        }
        return 0;
    }

    /**
     * 获取用户Id String类型，表示为："id@domain"，例如："123@qunar"
     * 
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonIgnore
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setQunarUserId(int userId) {
        this.qunarUserId = userId;
        this.userId = userId + USER_ID_DOMAIN_SEP + QUNAR_DOMAIN;
    }

    /**
     * @return the qunarUserId
     */
    public int getQunarUserId() {
        if (qunarUserId > 0) {
            return qunarUserId;
        }
        if (StringUtils.isBlank(userId)) {
            return 0;
        }
        int index = userId.indexOf(USER_ID_DOMAIN_SEP + QUNAR_DOMAIN);
        if (index != -1) {
            qunarUserId = Integer.parseInt(userId.substring(0, index));
        }
        return qunarUserId;
    }

    /**
     * @return the headImg
     */
    public String getHeadImg() {
        return headImg;
    }

    /**
     * @param headImg the headImg to set
     */
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    /**
     * @return the nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName the nickName to set
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDomain() {
        if (userId == null) {
            return null;
        }
        int index = this.userId.indexOf(USER_ID_DOMAIN_SEP);
        if (index > 0 && index < (userId.length() - 1)) {
            return userId.substring(index + 1);
        } else {
            return null;
        }
    }

    public boolean isQunarUser() {
        if (userId == null) {
            return false;
        }
        return userId.endsWith(QUNAR_DOMAIN);
    }

    public boolean isOauthUser() {
        if (userId == null) {
            return false;
        }

        for (String domain : PARTNER_DOMAINS) {
            if (userId.endsWith(domain)) {
                return true;
            }
        }

        return false;
    }

    public boolean isSmartTraveler() {
        return isSmartTraveler;
    }

    public void setSmartTraveler(boolean isSmartTraveler) {
        this.isSmartTraveler = isSmartTraveler;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isBind() {
        return bind;
    }

    public void setBind(boolean bind) {
        this.bind = bind;
    }

    /**
     * @return the mobile
     */
    @JsonIgnore
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the tempUser
     */
    public boolean isTempUser() {
        return tempUser;
    }

    /**
     * @param tempUser the tempUser to set
     */
    public void setTempUser(boolean tempUser) {
        this.tempUser = tempUser;
    }

    public String getLargeHeadImg() {
        return largeHeadImg;
    }

    public void setLargeHeadImg(String largeHeadImg) {
        this.largeHeadImg = largeHeadImg;
    }

    /**
     * @return the loginTime
     */
    public long getLoginTime() {
        return loginTime;
    }

    /**
     * @param loginTime the loginTime to set
     */
    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * @return the verified
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * @param verified the verified to set
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getMiddleHeadImg() {
        return middleHeadImg;
    }

    public void setMiddleHeadImg(String middleHeadImg) {
        this.middleHeadImg = middleHeadImg;
    }

    /**
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /* (non-Javadoc)
         * @see java.lang.Object#clone()
         */
    @Override
    protected UserInfo clone() throws CloneNotSupportedException {
        UserInfo info = new UserInfo();
        info.setUserId(this.userId);
        info.setHeadImg(this.headImg);
        info.setLargeHeadImg(this.largeHeadImg);
        info.setMiddleHeadImg(this.middleHeadImg);
        info.setNickName(this.nickName);
        info.setTempUser(this.tempUser);
        info.setLoginTime(this.loginTime);
        info.setVerified(this.verified);
        return info;
    }

    public static void main(String[] args) {
        UserInfo user = new UserInfo();
        user.setUserId("0@qunar");
        System.out.println(user.getQunarUserId());
    }

}
