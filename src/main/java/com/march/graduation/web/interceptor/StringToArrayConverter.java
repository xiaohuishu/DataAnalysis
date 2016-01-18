package com.march.graduation.web.interceptor;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Set;

public class StringToArrayConverter implements ConditionalGenericConverter {

    ConversionService conversionService = new DefaultConversionService();

    /*
     * (non-Javadoc)
     * @see org.springframework.core.convert.converter.GenericConverter#getConvertibleTypes()
     */
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Object[].class));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.core.convert.converter.ConditionalGenericConverter#matches(org.springframework.core.convert.TypeDescriptor, org.springframework.core.convert.TypeDescriptor)
     */
    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return this.conversionService.canConvert(sourceType, targetType.getElementTypeDescriptor());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.core.convert.converter.GenericConverter#convert(java.lang.Object, org.springframework.core.convert.TypeDescriptor, org.springframework.core.convert.TypeDescriptor)
     */
    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return Array.newInstance(targetType.getElementTypeDescriptor().getType(), 0);
        }
        String string = (String) source;
        if (string.length() == 0) {
            return Array.newInstance(targetType.getElementTypeDescriptor().getType(), 0);
        }
        String[] fields = StringUtils.commaDelimitedListToStringArray(string);
        Object target = Array.newInstance(targetType.getElementTypeDescriptor().getType(), fields.length);
        for (int i = 0; i < fields.length; i++) {
            String sourceElement = fields[i];
            Object targetElement = this.conversionService.convert(sourceElement.trim(), sourceType,
                    targetType.getElementTypeDescriptor());
            Array.set(target, i, targetElement);
        }
        return target;
    }
}
