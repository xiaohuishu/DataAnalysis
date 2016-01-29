package com.march.graduation.web.template.freemarker;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModelException;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

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
