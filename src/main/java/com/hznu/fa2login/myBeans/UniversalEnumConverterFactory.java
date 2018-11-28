package com.hznu.fa2login.myBeans;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author WheelChen
 * @date 2018-05-20 10:45:48
 */
public class UniversalEnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    private static final Map<Class, Converter> CONVERTER_MAP = new WeakHashMap<>();

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter result = CONVERTER_MAP.get(targetType);
        if(result == null) {
            result = new IntegerStrToEnum<T>(targetType);
            CONVERTER_MAP.put(targetType, result);
        }
        return result;
    }

    /**
     * 数字字符串转枚举类型
     * @param <T>
     */
    class IntegerStrToEnum<T extends BaseEnum> implements Converter<String, T> {
        private final Class<T> enumType;
        private Map<String, T> enumMap = new HashMap<>();

        public IntegerStrToEnum(Class<T> enumType) {
            this.enumType = enumType;
            T[] enums = enumType.getEnumConstants();
            for(T e : enums) {
                enumMap.put(e.getValue() + "", e);
            }
        }


        @Override
        public T convert(String source) {
            T result = enumMap.get(source);
            if(result == null) {
                throw new IllegalArgumentException("No element matches " + source);
            }
            return result;
        }
    }
}
