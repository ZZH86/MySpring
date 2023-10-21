package cn.kiki.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @Author hui cao
 * @Description:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";
}
