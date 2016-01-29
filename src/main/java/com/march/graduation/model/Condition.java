package com.march.graduation.model;

import com.march.graduation.annotation.PropertyName;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

// ********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-26 时间: 下午5:42
// ********************************************
public class Condition implements Serializable {

    private static final long serialVersionUID = 1L;

    @PropertyName("city")
    private String propertyName;

    @PropertyName("positionName")
    private String secondPropertyName;

    private String value;

    private String secondValue;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getSecondPropertyName() {
        return secondPropertyName;
    }

    public void setSecondPropertyName(String secondPropertyName) {
        this.secondPropertyName = secondPropertyName;
    }

    public String getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(String secondValue) {
        this.secondValue = secondValue;
    }

    public int getConditionType() {
        return StringUtils.isBlank(value) ? (StringUtils.isBlank(secondValue) ? 0 : 2)
                : (StringUtils.isBlank(secondValue) ? 1 : 3);
    }

    @Override
    public String toString() {
        return "Condition{" + "propertyName='" + propertyName + '\'' + ", value=" + value + '}';
    }
}
