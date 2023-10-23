package cn.kiki.springframework.context.event;

import cn.kiki.springframework.context.ApplicationEvent;
import cn.kiki.springframework.context.ApplicationListener;

/**
 * @Author hui cao
 * @Description: 事件广播器
 */
public interface ApplicationEventMulticaster {

    /**
     * Add a listener to be notified of all events.
     * @param listener the listener to add
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * Remove a listener from the notification list.
     * @param listener the listener to remove
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 将给定的应用程序事件广播到适当的侦听器
     * @param event the event to multicast
     */
    void multicastEvent(ApplicationEvent event);
}
