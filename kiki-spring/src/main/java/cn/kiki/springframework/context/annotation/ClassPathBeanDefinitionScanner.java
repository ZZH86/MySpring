package cn.kiki.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import cn.kiki.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;
import cn.kiki.springframework.beans.factory.support.BeanDefinitionRegistry;
import cn.kiki.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @Author hui cao
 * @Description:
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider{
    private BeanDefinitionRegistry beanDefinitionRegistry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidateComponents) {
                // 解析 bean 的作用域 singleton 或 prototype
                String beanScope = resolveBeanScope(beanDefinition);
                if(StrUtil.isNotEmpty(beanScope)){
                    beanDefinition.setScope(beanScope);
                }
                beanDefinitionRegistry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }

        // 注册处理注解的 BeanPostProcessor（@Autowired、@Value）
        // 由于AutowiredAnnotationBeanPostProcessor并没有标注@Component,所以是无法在类扫描时注入到beanFactory中的,此处需要我们手动进行注册
        beanDefinitionRegistry.registerBeanDefinition("cn.kiki.springframework.context.annotation.internalAutowiredAnnotationProcessor", new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));

    }

    /**
     * 获取 bean 的名字
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if(StrUtil.isEmpty(value)){
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }

    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if(null != scope) return scope.value();
        return StrUtil.EMPTY;
    }
}
