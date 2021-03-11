package alex.learn.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUitls {
	
	/**
	 * 将实体转化为map
	 * @param t
	 * @return
	 */
	public static <T> Map<String, String> parseObj2Map(T t) {
		Class<?> clzz = t.getClass();
		Map<String, String> params = new HashMap<String, String>();
		Field[] fields = clzz.getDeclaredFields();
		for(Field field : fields) {
			field.setAccessible(true);
			String propertyName = field.getName();
			params.put(propertyName, (String)get(t, propertyName));
		}
		return params;
	}
	
	/**
	 * 将实体转化为map
	 * @param t
	 * @return
	 */
	public static <T> Map<String, Object> parseObject2Map(T t) {
		Class<?> clzz = t.getClass();
		Map<String, Object> params = new HashMap<String, Object>();
		Field[] fields = clzz.getDeclaredFields();
		for(Field field : fields) {
			field.setAccessible(true);
			String propertyName = field.getName();
			params.put(propertyName, get(t, propertyName));
		}
		return params;
	}
	
	/**
	 * 根据对象和属性名称，获取属性值
	 * @param t
	 * @param propertyName
	 * @return
	 */
	private static <T> Object get(T t, String propertyName) {
        if("".equals(propertyName) || propertyName == null) {
            return null;
        }
        try{
            Class<?> clzz = t.getClass();
            String methodName = "get" + propertyName.substring(0, 1).toUpperCase().concat(propertyName.substring(1));
            Method method = clzz.getDeclaredMethod(methodName);
            return method.invoke(t);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
