package com.ttsx.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * 文件上传的工具类
 * company 源辰信息
 * @author navy
 * @date 2020年10月25日
 * Email haijunzhou@hnit.edu.cn
 */
public class FileUploadUtil {
	public static String uploadPath = "../images_ttsx"; //文件默认的上传路径
	private static final String ALLOWEDLIST = "gif,jpg,png,doc,docx,xls,xlsx,txt,zip,rar,mp3,map4"; // 允许上传的文件类型
	private static final String DENIEDLIST = "exe,bat,jsp"; // 不允许上传的文件类型
	private static final int MAXFILESIZE = 10 * 1024 * 1024; //单个上传文件的最大值
	private static final int TOTALMAXFILESIZE = 100 * 1024 * 1024; //总上传文件最大值

	/**
	 * 获取文件上传的对象
	 * @param pageContext
	 * @return
	 * @throws ServletException 
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws SmartUploadException 
	 */
	private SmartUpload getSmartUpload(PageContext pageContext) throws ServletException, IOException, SQLException, SmartUploadException {
		SmartUpload su = new SmartUpload();

		// 初始化上传组件
		su.initialize(pageContext);
		// 设置参数
		su.setAllowedFilesList(ALLOWEDLIST);
		su.setDeniedFilesList(DENIEDLIST);
		su.setMaxFileSize(MAXFILESIZE);
		su.setTotalMaxFileSize(TOTALMAXFILESIZE);
		// 设置上传数据的编码集
		su.setCharset("UTF-8");
		su.upload();
		return su;
	}

	/**
	 * 文件上传
	 * @param pageContext
	 * @return
	 * @throws IOException 
	 * @throws SmartUploadException 
	 * @throws ServletException 
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> uploads(PageContext pageContext) throws SmartUploadException, IOException, ServletException, SQLException {
		Map<String, String> map = new HashMap<String, String>();
		// 获取上传的参数信息，非文件参数 此时的Request是jspsmartupload中的
		SmartUpload su = this.getSmartUpload(pageContext);

		Request rq = su.getRequest(); 

		//获取所有表单控件的名字
		Enumeration<String> enus = rq.getParameterNames();
		//单个控件名称 name属性
		String name = null;
		while(enus.hasMoreElements()) {		
			name = enus.nextElement();
			map.put(name, rq.getParameter(name));
		}
		//return map;  //此时获取的所有的普通文本信息

		// 处理上传的文件
		Files files = su.getFiles();
		// 如果无文件，则直接放回文本信息
		if( files == null || files.getCount() <= 0) {
			return map;
		}

		// 有就循环取出用户上传的文件
		Collection<File> fls = files.getCollection();
		// TODO 文件存储服务器的详细位置
		// 获取保存文件夹的绝对路径 -> tomcat在服务器的绝对路径  C...\webapps\项目名\
		String basePath = pageContext.getServletContext().getRealPath("/");

		String fileName = null; //上传后的文件名称
		String filedName = null; //文件控件名
		String filePath = null; //文件保存路径

		String pathStr = ""; //多文件拼接路径
		String temp = null; //网页文件中文件上传的控件

		for(File fl : fls) {
			if( !fl.isMissing() ) {//文件存在

				temp = fl.getFieldName(); //photos files 页面多个文件域控件
				if( StringUtil.checkNull(filedName) ) { //如果这个变量为空，说明这是第一次要上传的文件
					filedName = temp;
				}else { //否则这是其他的文件域控件
					if( !temp.equals(filedName) ) {
						map.put(filedName, pathStr);
						pathStr = ""; //初始化 	清空上一个上传路径 准备放下一个文件上传路径
						filedName = temp;
					}
				}

				//为了避免命名重复覆盖，自定义文件名
				filedName = fl.getFieldName();
				fileName = uploadPath + "/" + new Date().getTime() + "_" + fl.getFileName();

				if( StringUtil.checkNull(pathStr) ) { //说明这是第一个要上传的文件
					pathStr = fileName;
				}else {
					pathStr += ";" + fileName; //多图片字符串拼接
				}

				//存储在服务器中  自定义路径 + 自定义名称
				filePath = basePath + fileName;
				//存储文件
				fl.saveAs(filePath, SmartUpload.SAVE_PHYSICAL);			
			}		
		}		
		map.put(filedName, pathStr);
		return map;
	}

	/**
	 * 基于对象的文件上传
	 * @param <T>
	 * @param cls
	 * @param pageContext
	 * @return
	 * @throws Exception
	 */
	public <T> T uploads(Class<T> cls, PageContext pageContext) throws Exception {
		T t = cls.newInstance();

		// 获取给定的类中的所有setter方法
		Method[] methods = cls.getMethods();

		// 存储所有是setter方法，以方法名为键，对应的方法对象为值
		Map<String, Method> setters = new HashMap<String, Method>();

		String methodName = null;
		for (Method method : methods) {
			methodName = method.getName();
			if (methodName.startsWith("set")) { // 说明当前方法是一个setter方法
				setters.put(methodName, method);
			}
		}

		Map<String, String> map = this.uploads(pageContext); // 获取请求中的所有参数信息
		//System.out.println(map+"所有参数信息");
		// 循环所有的参数名，找到这个参数注入时对应的setter方法，将这个参数注入到对应的对象的属性中
		String name = null;
		Method method = null;
		Class<?>[] types = null;
		Class<?> type = null;
		String val = null;

		t = cls.newInstance(); // 实例化这个类的一个对象

		// map.forEach((key, val) -> {} // function(key, val){   }
		for (Entry<String, String> entry : map.entrySet()) {
			val = entry.getValue();
			name = entry.getKey();
			
			if (StringUtil.checkNull(val)) {
				continue;
			}
			
			methodName = "set" + name.substring(0,1).toUpperCase() + name.substring(1);
			method = setters.getOrDefault(methodName, null);
			if (method == null) { // 说明该实体类中没有这个属性的注值方法
				continue;
			}
			
			// 因为方法调用跟数据类型有关，所有我们必须先获取这个方法的新参列表
			types = method.getParameterTypes();
			if (types == null || types.length <= 0) { // 说明这个方法不带形参，那么我们也无法注值
				continue;
			}
			
			type = types[0]; // 因为我们提供setter方法一般都只有一个形参
			
			// 反向激活这个方法注值
			if (Integer.TYPE == type || Integer.class == type) {
				method.invoke(t, Integer.parseInt(val));
			} else if (Float.TYPE == type || Float.class == type) {
				method.invoke(t, Float.parseFloat(val));
			} else if (Double.TYPE == type || Double.class == type) {
				method.invoke(t, Double.parseDouble(val));
			} else {
				method.invoke(t, val);
			}
		}
		return t;
	}
}
