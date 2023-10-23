package cn.kiki.springframework.util;

/**
 * 获取默认的类加载器
 */

public class ClassUtils {
    public static ClassLoader getDefaultClassLoader() {

        //1. 首先声明一个ClassLoader对象cl，并将其初始化为null。
        ClassLoader cl = null;

        //2. 尝试获取当前线程的上下文类加载器，并将其赋值给cl。如果获取失败，会抛出异常
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }

        //3. 如果cl仍然为null，则表示没有线程上下文类加载器，这时将使用当前类的类加载器作为默认类加载器
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
        }
        return cl;
    }

    /**
     * 检查指定的类是否为 CGLIB 生成的类
     * @param clazz the class to check
     */
    public static boolean isCglibProxyClass(Class<?> clazz) {
        return (clazz != null && isCglibProxyClassName(clazz.getName()));
    }

    /**
     * 检查指定的类名是否为 CGLIB 生成的类
     * @param className the class name to check
     */
    public static boolean isCglibProxyClassName(String className) {
        return (className != null && className.contains("$$"));
    }

}
