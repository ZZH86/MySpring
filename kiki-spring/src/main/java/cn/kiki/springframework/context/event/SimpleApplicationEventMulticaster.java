package cn.kiki.springframework.context.event;

import cn.kiki.springframework.beans.factory.BeanFactory;
import cn.kiki.springframework.context.ApplicationEvent;
import cn.kiki.springframework.context.ApplicationListener;

/**
 * @Author hui cao
 * @Description:
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }

}