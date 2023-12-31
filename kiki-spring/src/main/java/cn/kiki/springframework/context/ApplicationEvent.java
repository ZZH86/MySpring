package cn.kiki.springframework.context;

import java.util.EventObject;

/**
 * @Author hui cao
 * @Description: 以继承 java.util.EventObject 定义出具备事件功能的抽象类 ApplicationEvent，
 *    所有的事件包括关闭、刷新，以及用户自己实现的事件，都需要继承这个类。
 */
public abstract class ApplicationEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     * 构造原型事件
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
