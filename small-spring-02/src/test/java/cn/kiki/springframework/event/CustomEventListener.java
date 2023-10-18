package cn.kiki.springframework.event;

import cn.kiki.springframework.context.ApplicationListener;

import java.util.Date;

/**
 * @Author hui cao
 * @Description: 用于监听 CustomEvent 事件的监听器，这里你可以处理自己想要的操作，比如一些用户注册后发送优惠券和短信通知等
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date());
        System.out.println("消息：" + event.getId() + ":" + event.getMessage());
    }

}
