package cn.kiki.springframework.aop.aspectj;

import cn.kiki.springframework.aop.ClassFilter;
import cn.kiki.springframework.aop.MethodMatcher;
import cn.kiki.springframework.aop.Pointcut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author hui cao
 * @Description: 切点表达式类
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

    static {
        // PointcutPrimitive.EXECUTION 是一个切点原语，它是在面向切面编程（AOP）中用来定义切点的一种方式。
        // 切点是在程序执行过程中选择特定代码位置进行拦截和增强的规则。
        // PointcutPrimitive.EXECUTION 用于匹配程序执行过程中的方法执行点。它可以指定匹配的方法的访问修饰符、返回类型、方法名、参数类型等。
        // 举个例子，如果我们定义了一个切点规则为 "execution(public * com.example.service.*Service.*(..))"，
        // 它将匹配所有公共方法名以 "Service" 结尾的类中的方法，并且方法参数可以是任意类型。
        // 在AOP中，我们可以使用切点来选择需要在程序执行过程中进行拦截和增强的特定方法。
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    private final PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(String expression) {
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
        this.pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
