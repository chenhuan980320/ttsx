package com.ttsx.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.ttsx.util.FileUploadUtil;
import com.ttsx.util.StringUtil;
/**
 * 当应用启动时，我们就监听 创建文件上传的目录
 * company 逸恒科技
 * @author 胡66
 * @data 2020年11月4日
 * Email 906430016@qq.com
 */
@WebListener
public class CreateUploadPathListener  implements ServletContextListener{
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		String path = sce.getServletContext().getInitParameter("uploadPath");
		
		if(StringUtil.checkNull(path)) {
			path  ="images_ttsx";
		}
		String basePath = sce.getServletContext().getRealPath("/");//获取Tomcat在服务器中的绝对路径
		path = "../"+path;
		File fl = new File(basePath,path);
		
		if(!fl.exists()) {
			fl.mkdirs();
		}
	//	System.out.println(path);
		FileUploadUtil.uploadPath = path;
		
	}

}
