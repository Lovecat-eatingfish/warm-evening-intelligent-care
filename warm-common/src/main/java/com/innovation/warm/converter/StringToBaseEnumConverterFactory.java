package com.innovation.warm.converter;

import com.innovation.warm.pojo.base.BaseEnum;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * ClassName: StringToBaseEnumCOnverterFactory
 * PackageName: com.innovation.warm.converter
 * Description:
 *
 * @Author: 32782
 * @Date: 2024/11/13 下午10:23
 * @Version: 1.0
 */
public class StringToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new Converter<String, T>() {
            @Override
            public T convert(String code) {
                T[] enumConstants = targetType.getEnumConstants();
                for (T enumConstant : enumConstants) {
                    if (enumConstant.getCode().equals(Integer.parseInt(code))) {
                        return enumConstant;
                    }
                }
                throw new IllegalArgumentException("code + : 非法参数异常");
            }
        };
    }
}
