package cn.kiki.springframework.event;

import cn.kiki.springframework.context.event.ApplicationContextEvent;

/**
 * @Author hui cao
 * @Description: 创建一个自定义事件，在事件类的构造函数中可以添加自己的想要的入参信息。
 *    这个事件类最终会被完成的拿到监听里，所以添加的属性都会被获得到。
 */
public class CustomEvent extends ApplicationContextEvent {

    private Long id;
    private String message;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public CustomEvent(Object source, Long id, String message) {
        super(source);
        this.id = id;
        this.message = message;
    }

    // ...get/set

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
