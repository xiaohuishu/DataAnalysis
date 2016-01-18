package com.march.graduation.web.template.freemarker;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-18 时间: 下午12:03
//********************************************

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModelException;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * freemarker静态类、枚举map
 * <p>
 * <p>
 *
 * @author wangqiuyi Initial Created at 2013-4-18
 *         <p>
 *         <p>
 *         注入org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver的attributesMap属性中
 *         <p>
 *
 *         配置用例：
 *
 *         <pre>
 *     {@code
 *     <bean id="freeMarkerViewResolver"
 *         class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
 *         <property name="order" value="1" />
 *         <property name="viewClass">
 *             <value>org.springframework.web.servlet.view.freemarker.FreeMarkerView
 *             </value>
 *         </property>
 *         <property name="suffix" value=".ftl" />
 *         <property name="contentType" value="text/html;charset=UTF-8"></property>
 *         <property name="attributesMap" ref="freemarkerStaticModels" />
 *     </bean>
 *     <bean id="freemarkerStaticModels"
 *         class="com.qunar.travel.touch.web.template.freemarker.FreemarkerStaticModels"
 *         factory-method="getInstance">
 *         <property name="staticModels">
 *             <map>
 *                 <entry key="ImageUtils" value="com.qunar.travel.touch.utils.ImageUtils" />
 *                 <entry key="ElementConvertUtils" value="com.qunar.travel.touch.utils.ElementConvertUtils" />
 *                 <entry key="StringUtils" value="com.qunar.travel.touch.utils.StringUtils" />
 *                 <entry key="URLEncoder" value="java.net.URLEncoder" />
 *             </map>
 *         </property>
 *     </bean>
 *     }
 * </pre>
 */
public class FreemarkerStaticModels extends HashMap<Object, Object> {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerStaticModels.class);

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private static FreemarkerStaticModels FREEMARKER_STATIC_MODELS = new FreemarkerStaticModels();

    private static BeansWrapper wrapper = BeansWrapper.getDefaultInstance();

    private Map<String, String> staticModels;

    private Map<String, String> enumModels;

    private FreemarkerStaticModels() {
    }

    public static FreemarkerStaticModels getInstance() {
        return FREEMARKER_STATIC_MODELS;
    }

    public void setStaticModels(Map<String, String> staticModels) {
        if (MapUtils.isEmpty(this.staticModels) && MapUtils.isNotEmpty(staticModels)) {
            this.staticModels = staticModels;
            try {
                for (java.util.Map.Entry<String, String> entry : this.staticModels.entrySet()) {
                    FREEMARKER_STATIC_MODELS.put(entry.getKey(), wrapper.getStaticModels().get(entry.getValue()));
                }
            } catch (TemplateModelException e) {
                logger.error("get static models failure!!", e);
            }
        }
    }

    public void setEnumModels(Map<String, String> enumModels) {
        if (MapUtils.isEmpty(this.enumModels) && MapUtils.isNotEmpty(enumModels)) {
            this.enumModels = enumModels;
            try {
                for (java.util.Map.Entry<String, String> entry : this.enumModels.entrySet()) {
                    FREEMARKER_STATIC_MODELS.put(entry.getKey(), wrapper.getEnumModels().get(entry.getValue()));
                }
            } catch (TemplateModelException e) {
                logger.error("get enum models failure!!", e);
            }
        }
    }
}
