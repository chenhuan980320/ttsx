package com.ttsx.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理请求参数
 * company 逸恒科技
 * @author 胡66
 * @data 2020年10月25日
 * Email 906430016@qq.com
 */
public class RequestParamUtil {
	/**
	 * 将亲求中的参数封装成对象返回
	 */
				/**
				 * 方法使用     你给我一个class对象   和request是一个表单请求  里面有很多参数
				 * 		  我将这些参数和对象的set方法一一对比   然后设置有set方法的属性   然后返回一个设置好这些属性的对象给你
				 * @param <T>
				 * @param cls
				 * @param request
				 * @return
				 */
	public static <T> T getParams(Class<T> cls,HttpServletRequest request) {
		T t=null;
		try {
			//获取给定类中所有的setter方法
			Method[] methods =cls.getMethods();
			
			//存储所有是setter方法  以方法为键  对应方法对象为值
			Map<String,Method> setters = new HashMap<String,Method>();
			
			String methodName = null;
			for(Method method :methods) {
				methodName=method.getName();
				if(methodName.startsWith("set")) {//说明当前方法是一个setter方法
					setters.put(methodName, method);
				}
			}
			
			//获取请求中的所有参数的参数名
			Enumeration<String> names = request.getParameterNames();
			
			//循环所有的参数名  找到这个参数注入到对应的对象的属性中
			String name =null;
			Method method= null;
			Class<?>[] types=null;
			Class<?> type=null;
			Object obj = null;
			String objStr = null;
			t=cls.newInstance();   //实例化这个类的一个对象
			
			while(names.hasMoreElements()) {
				name = names.nextElement();
				obj =request.getParameter(name);//先判断参数是否为空
				if(obj==null) {
					continue;
				}
				objStr = String.valueOf(obj);
				if("".equals(objStr)) {
					continue;
				}
				
				methodName="set"+ name.substring(0,1).toUpperCase()+name.substring(1);
				method=setters.getOrDefault(methodName, null);
				if(method==null) {//说明该实体类中没有这个属性的注值方法
					continue;
				}
				
				//因为方法调用跟数据类型有关，所有我们必须先获取这个方法的新参列表
				types = method.getParameterTypes();
				if(types==null||types.length<=0) {//说明这个方法不带形参  那么我们也无法注值
					continue;
				}
				
				type =types[0];//应为我们提供setter方法一般都只有一个形参
				
				//反向激活这个注值方法
				if(Integer.TYPE==type ||Integer.class==type) {
					method.invoke(t, Integer.parseInt(objStr));
				}else if(Float.TYPE==type || Float.class==type) {
					method.invoke(t, Float.parseFloat(objStr));
				}else if(Double.TYPE==type||Double.class==type) {
					method.invoke(t, Double.parseDouble(objStr));
				}else{
					method.invoke(t, objStr);
				}
			}
			
		} catch (SecurityException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return t;
	}
	
}
