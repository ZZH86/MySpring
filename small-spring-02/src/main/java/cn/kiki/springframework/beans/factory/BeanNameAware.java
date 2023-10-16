package cn.kiki.springframework.beans.factory;

/**
 * @Author hui cao
 * @Description: 实现此接口，既能感知到所属的 BeanName
 */
public interface BeanNameAware extends Aware{

    void setBeanName(String name);
}
