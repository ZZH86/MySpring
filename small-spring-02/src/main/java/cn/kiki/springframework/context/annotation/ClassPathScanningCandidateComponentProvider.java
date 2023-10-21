package cn.kiki.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import cn.kiki.springframework.beans.factory.config.BeanDefinition;
import cn.kiki.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Author hui cao
 * @Description: 处理对象扫描装配
 */
public class ClassPathScanningCandidateComponentProvider {

    // 先要提供一个可以通过配置路径 basePackage=cn.kiki.springframework.test.bean，
    // 解析出 classes 信息的工具方法 findCandidateComponents，
    // 通过这个方法就可以扫描到所有 @Component 注解的 Bean 对象了。

    /**
     * 扫描指定路径下的包的所有类，如果类有 @Compoment 注解，就把该类加入到 beanDefinition 数组
     * @param basePackage
     * @return
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }
}
