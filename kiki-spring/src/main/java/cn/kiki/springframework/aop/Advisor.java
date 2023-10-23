package cn.kiki.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @Author hui cao
 * @Description:
 */
public interface Advisor {

    /**
     * Return the advice part of this aspect. An advice may be an
     * interceptor, a before advice, a throws advice, etc.
     */
    Advice getAdvice();
}
