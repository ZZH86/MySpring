package cn.kiki.springframework.context.event;

/**
 * @Author hui cao
 * @Description: 监听关闭
 */
public class ContextClosedEvent extends ApplicationContextEvent{
    /**
     * Constructs a prototypical Event.
     * 构造原型事件
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
