package cn.kiki.springframework.util;

/**
 * @Author hui cao
 * @Description: 定义解析字符串接口
 */
public interface StringValueResolver {

    //解析字符串
    String resolveStringValue(String strVal);
}
