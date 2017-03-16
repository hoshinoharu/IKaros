package com.reharu.harubase.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 星野悠 on 2017/1/31.
 */

public class ReflectTool {
    private static String TAG = "ReflectToolTag";

    public static Method getMethod(Class cls, String name, Class... params) {
        try {
            return cls.getDeclaredMethod(name, params);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getProperty(Class<T> clz, Object object, String propertyName) {
        T t = null;
        try {
            t = (T) getFieldVal(object, object.getClass().getDeclaredField(propertyName));
        } catch (Exception e) {
            HLog.ex(TAG, e);
        }
        return t;
    }

    public static Object getFieldVal(Object obj, Field field) {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        Object val = null;
        try {
            val = field.get(obj);
        } catch (IllegalAccessException e) {
            HLog.ex(TAG, e);
        }
        field.setAccessible(accessible);
        return val;
    }

    private static Map<Class, Type[]> typeMapper = new HashMap<>() ;

    public static Type getGenericType(Class clazz) {
        Type[] params = typeMapper.get(clazz);
        if(params == null) {
            Type genType = clazz.getGenericSuperclass();

            if (!(genType instanceof ParameterizedType)) {
                return Object.class;
            }
            params = ((ParameterizedType) genType).getActualTypeArguments();
            if (0 >= params.length) {
                return Object.class;
            }
            typeMapper.put(clazz, params) ;
        }
        return  params[0];
    }
}
