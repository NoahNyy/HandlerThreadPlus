package nyy.org.handlerthreadplus.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author niuyy
 */
public class GenericSuperclassUtil {

    /**
	 * 获取泛型类Class对象，不是泛型类则返回null
     *
     * @param clazz 泛型类Class对象
     *
     * @return null if 不是泛型类
     */
    public static Class<?> getActualTypeArgument(Class<?> clazz) {
        Class<?> entityClass = null;
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass)
                    .getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                entityClass = (Class<?>) actualTypeArguments[0];
            }
        }

        return entityClass;
    }

}
