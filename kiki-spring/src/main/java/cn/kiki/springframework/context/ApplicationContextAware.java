package cn.kiki.springframework.context;

import cn.kiki.springframework.beans.BeansException;
import cn.kiki.springframework.beans.factory.Aware;

/**
 * @Author hui cao
 * @Description: 实现此接口，既能感知到所属的 ApplicationContext
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
