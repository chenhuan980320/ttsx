package com.ttsx.util;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;






public class MyUtil {


	public String myEmail = "hu17608481308@163.com";	//发送邮件的邮箱
	public String myHost = "smtp.163.com";				//主机
	public String myEmailPassword="BUVECAXMSKPIVBJP";		//注册，这是授权码
	
//	private boolean flag=false;
//	private int clientX;
//	private int clientY;
//	
//	public void adterMessage(String title,String message){
//		JOptionPane.showMessageDialog(null, message,title,JOptionPane.INFORMATION_MESSAGE);
//	}
	//信息提示狂
//	public void messagebox(Shell shell,String s1,String s2){
//		MessageBox mb=new MessageBox(shell);
//		mb.setText(s1);//"提示信息"
//		mb.setMessage(s2);//"登录成功！"
//		mb.open();
//		
//	}
	//首先要有邮箱，妖气SMTP服务，再一个要授权码
	public String sendEmail(String email) throws MessagingException{
		//随机的是一个6位的随机数
		Random r = new Random();
		String code="";			//000000-999999
		for(int i=0;i<6;i++){
			code+=r.nextInt(10);
		}
		//开始发邮件
		try {
			Properties props=new Properties();
			props.setProperty("mail.transport.protocol", "smtp");	//使用smtp的邮件传输协议
			props.setProperty("mail.smtp.host", myHost);		//主机地址
			props.setProperty("mail.smtp.auth", "true");	//授权通过
			Session session = Session.getInstance(props);	//通过我们这些配置，得到一个会话程序
			session.setDebug(true);
			//创建邮件
			MimeMessage message=new MimeMessage(session);
			message.setFrom(new InternetAddress(myEmail,"胡66报考助手","utf-8"));  //设置发件人
			message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(email,"用户","utf-8"));//设置收件人
			message.setSubject("欢迎您","utf-8");//设置主题
			message.setContent("亲，欢迎使用淘宝，您的幸运数位："+code,"text/html;charset=utf-8");
			message.setSentDate(new Date());
			message.saveChanges();//保存好
			
			//开始发送
			Transport transport = session.getTransport();
			transport.connect(myEmail,myEmailPassword);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessageRemovedException e) {
			e.printStackTrace();
		}
		return code;
	}
}
	//缩放图片
//	public Image images(String ss,int width,int height,Device display){
//		//设置图片  "src/image/biaoti.png"
//		ImageData imageData=new ImageData(ss);
//		//缩放图片 你的label有多大 我就设置多达 
//		//lblNewLabel.getBounds().width,lblNewLabel.getBounds().height
//		imageData= imageData.scaledTo(width,height);
//		Image image = new Image(display,imageData);
//		return image;
//	}
	//加密
//	public String addpwd(String pwd) {
//		byte[] bs = null;
//		try {
//			MessageDigest md = MessageDigest.getInstance("md5");
//			//开始加密
//			bs = md.digest(pwd.getBytes());
//			//System.out.println(new String(bs));
//			String out= new BigInteger(1,bs).toString(16);
//			//System.out.println(out);
//			//在加密一次
//			MessageDigest md2=MessageDigest.getInstance("sha1");
//			bs=md2.digest(out.getBytes());
//			out=new BigInteger(1,bs).toString(16);
//			//System.err.println(out);
//		} catch (NoSuchAlgorithmException e) {
//		
//			e.printStackTrace();
//		} 
//		return new String(bs);
//	}
	
	
	//窗口拖动
//	public void moveShell(Composite composite,Shell shell){
//		
//		composite.addMouseMoveListener(new MouseMoveListener() {
//			public void mouseMove(MouseEvent arg0) {
//				if(flag){
//					
//					//归根结底，在于setLocation
////					System.err.println("目前窗口的左的值："+shell.getLocation().x);
////					System.out.println("移动的值"+(arg0.x-clientX));
//					shell.setLocation(shell.getLocation().x+(arg0.x-clientX),shell.getLocation().y+(arg0.y-clientY));
//					
//				}
//			}
//		});
//		composite.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseDown(MouseEvent e) {
//				flag=true;
//				clientX = e.x;
//				clientY = e.y;
//			}
//			@Override
//			public void mouseUp(MouseEvent e) {
//				//按下
//				flag=false;		//代表可以拖动
//				//得到坐标
//				
//			}
//		});
//	}
//	
//	/**
//	 * 从数据库提取图片用这个方法
//	 * @param ss		转成byte[]字节数组，例子(byte[]) list.get(i).get("photo")
//	 * @param width		宽
//	 * @param height	高
//	 * @param display 记得设置为全局变量
//	 * @return
//	 */
//	public Image byteimages(byte[] bs,int width,int height,Device display){
//		InputStream is= new ByteArrayInputStream(bs);
//		ImageData imd=new ImageData(is);
//		imd=imd.scaledTo(width, height);
//		Image image = new Image(display,imd);
//		try {
//			is.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return image;
//	}
//	
//}
