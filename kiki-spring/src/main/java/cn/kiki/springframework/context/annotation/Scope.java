package cn.kiki.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * @Author hui cao
 * @Description: 定义拦截注解,用于配置作用域的自定义注解，方便通过配置Bean对象注解的时候，拿到Bean对象的作用域。不过一般都使用默认的 singleton
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    String value() default "singleton";
}
