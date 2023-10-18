package cn.kiki.springframework.context.event;

import cn.kiki.springframework.context.ApplicationContext;
import cn.kiki.springframework.context.ApplicationEvent;

/**
 * @Author hui cao
 * @Description:
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     * 构造原型事件
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }
}
