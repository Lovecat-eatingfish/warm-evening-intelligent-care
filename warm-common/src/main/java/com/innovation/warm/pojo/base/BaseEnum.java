package com.innovation.warm.pojo.base;

/**
 * @author 32782
 */
// 枚举的父类： 作用便于 如果一直使用  前端返回json数据 转化为 java枚举实例  如果一直用@jsonvalue注解太麻烦
// 实现：org.springframework.core.convert.converter
// implements ConverterFactory<String, BaseEnum>   吧BaseEnum的子类一同 可以 用converter 转化
public interface BaseEnum {

    Integer getCode();

    String getName();
}
