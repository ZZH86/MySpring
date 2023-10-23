package cn.kiki.springframework.context.support;

/**
 * @Author hui cao
 * @Description: 对外给用户提供的应用上下文方法
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    private String[] configLocations;

    /**
     * 从 XML 中加载 BeanDefinition，并刷新上下文
     */
    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    /**
     * 从 XML 中加载 BeanDefinition，并刷新上下文
     */
    public ClassPathXmlApplicationContext(String configLocations) {
        this(new String[]{configLocations});
    }


    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
