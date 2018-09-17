package com.ep.api.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Map 对象与 JavaBean 对象互转工具类
 */
public class BeanToMapUtil<T> {

	private BeanToMapUtil() {
	}

	private static final String UNDERLINE = "_";

	public static String getUnderline() {
		return UNDERLINE;
	}

	/**
	 * 将 Map对象转化为JavaBean
	 * 
	 * @param map
	 * @param T
	 * @return
	 * @throws Exception
	 */
	public static <T> T convertMap2Bean(Map<String, Object> map, Class<T> T) throws Exception {
		if (map == null || map.size() == 0) {
			return null;
		}
		// 获取map中所有的key值，全部更新成大写，添加到keys集合中,与mybatis中驼峰命名匹配
		Object mvalue = null;
		Map<String, Object> newMap = new HashMap<String, Object>();
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			String key = it.next().getKey();
			mvalue = map.get(key);
			if (key.indexOf(UNDERLINE) != -1) {
				key = key.replaceAll(UNDERLINE, "");
			}
			newMap.put(key.toUpperCase(Locale.US), mvalue);
		}

		BeanInfo beanInfo = Introspector.getBeanInfo(T);
		@SuppressWarnings("deprecation")
		T bean = T.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			String upperPropertyName = propertyName.toUpperCase();

			if (newMap.keySet().contains(upperPropertyName)) {
				Object value = newMap.get(upperPropertyName);
				// 这个方法不会报参数类型不匹配的错误。
				BeanUtils.copyProperty(bean, propertyName, value);
			}
		}
		return bean;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map<String, Object> convertBean2Map(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class<? extends Object> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!"class".equals(propertyName)) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, null);
				}
			}
		}
		return returnMap;
	}

	/**
	 * 将 List<Map>对象转化为List<JavaBean>
	 * 
	 * @param listMap
	 * @param T
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> convertListMap2ListBean(List<Map<String, Object>> listMap, Class<T> T) throws Exception {
		List<T> beanList = new ArrayList<T>();
		if (listMap != null && !listMap.isEmpty()) {
			for (int i = 0, n = listMap.size(); i < n; i++) {
				Map<String, Object> map = listMap.get(i);
				T bean = convertMap2Bean(map, T);
				beanList.add(bean);
			}
			return beanList;
		}
		return beanList;
	}

	/**
	 * 将 List<JavaBean>对象转化为List<Map>
	 * 
	 * @param beanList
	 * @return
	 * @throws Exception
	 */
	public static <T> List<Map<String, Object>> convertListBean2ListMap(List<T> beanList, Class<T> T) throws Exception {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (int i = 0, n = beanList.size(); i < n; i++) {
			Object bean = beanList.get(i);
			Map<String, Object> map = convertBean2Map(bean);
			mapList.add(map);
		}
		return mapList;
	}

	public static Object parametersToJavaBean(HashMap map, Class cls) {
		Object obj = null;
		try {
			obj = cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 取出bean里的所有方法
		Method[] methods = cls.getMethods();
		for (int i = 0; i < methods.length; i++) {
			// 取方法名
			String method = methods[i].getName();
			// 取出方法的类型
			Class[] cc = methods[i].getParameterTypes();
			if (cc.length != 1)
				continue;

			// 如果方法名没有以set开头的则退出本次for
			if (method.indexOf("set") < 0)
				continue;
			// 类型
			String type = cc[0].getSimpleName();

			try {
				// 转成小写
				Object value = method.substring(3).toLowerCase();
				// 如果map里有该key
				if (map.containsKey(value)) {
					// 调用其底层方法
					setValue(type, map.get(value), i, methods, obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/***
	 * 调用底层方法设置值
	 */
	private static void setValue(String type, Object value, int i, Method[] method, Object bean) {
		if (value != null && !value.equals("")) {
			try {
				if (type.equals("String")) {
					// 第一个参数:从中调用基础方法的对象 第二个参数:用于方法调用的参数
					method[i].invoke(bean, new Object[] { value });
				} else if (type.equals("int") || type.equals("Integer")) {
					method[i].invoke(bean, new Object[] { new Integer("" + value) });
				} else if (type.equals("long") || type.equals("Long")) {
					method[i].invoke(bean, new Object[] { new Long("" + value) });
				} else if (type.equals("boolean") || type.equals("Boolean")) {
					method[i].invoke(bean, new Object[] { Boolean.valueOf("" + value) });
				} else if (type.equals("Date")) {
					Date date = null;
					if (value.getClass().getName().equals("java.util.Date")) {
						date = (Date) value;
					} else {
						String format = ((String) value).indexOf(":") > 0 ? "yyyy-MM-dd hh:mm" : "yyyy-MM-dd";
						date = parseDateTime("" + value, format);
					}
					if (date != null) {
						method[i].invoke(bean, new Object[] { date });
					}
				} else if (type.equals("byte[]")) {
					method[i].invoke(bean, new Object[] { new String(value + "").getBytes() });
				}
			} catch (Exception e) {
				System.out.println("将linkHashMap 或 HashTable 里的值填充到javabean时出错,请检查!");
				e.printStackTrace();
			}
		}
	}

	public static Date parseDateTime(String dateValue, String format) {
		SimpleDateFormat obj = new SimpleDateFormat(format);
		try {
			return obj.parse(dateValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
