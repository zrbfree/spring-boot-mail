package com.rick;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisApplicationTests {
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Desc :  发送简单邮件
	 * User : RICK
	 * Time : 2017/8/28 17:23
	  */

	@Test
	public void sendSimpleEmail(){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("zrbfree@163.com");//发送者.
		message.setTo("297955686@qq.com");//接收者.
		message.setSubject("测试邮件（邮件主题）");//邮件主题.
		message.setText("这是邮件内容");//邮件内容.
		mailSender.send(message);
	}


	/**
	 * Desc :  测试发送附件.(这里发送图片.)
	 * User : RICK
	 * Time : 2017/8/28 17:29
	  */

	@Test
	public void sendAttachmentsEmail() throws MessagingException {
		//这个是javax.mail.internet.MimeMessage下的，不要搞错了。
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

		//基本设置
		helper.setFrom("zrbfree@163.com");//发送者.
		helper.setTo("297955686@qq.com");//接收者.
		helper.setSubject("测试附件（邮件主题）");//邮件主题.
		helper.setText("这是邮件内容（有附件哦.）");//邮件内容.

		//org.springframework.core.io.FileSystemResource下的:
		//附件1,获取文件对象.
		FileSystemResource file1 = new FileSystemResource(new File("C:\\Users\\RICK\\Pictures\\Saved Pictures\\2.jpg"));
		//添加附件，这里第一个参数是在邮件中显示的名称，也可以直接是head.jpg，但是一定要有文件后缀，不然就无法显示图片了。
		helper.addAttachment("图片1.jpg", file1);
		//附件2
		FileSystemResource file2 = new FileSystemResource(new File("C:\\Users\\RICK\\Pictures\\Saved Pictures\\fendou.jpg"));
		helper.addAttachment("奋斗2.jpg", file2);
		mailSender.send(mimeMessage);

	}


	/**
	 * Desc :  邮件中使用静态资源.
	 * User : RICK
	 * Time : 2017/8/28 17:30
	  */
	@Test
	public void sendInlineMail() throws Exception{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		//基本设置.
		helper.setFrom("zrbfree@163.com");//发送者.
		helper.setTo("297955686@qq.com");//接收者.
		helper.setSubject("测试静态资源（邮件主题）");//邮件主题.
		// 邮件内容，第二个参数指定发送的是HTML格式
		//说明：嵌入图片<img src='cid:head'/>，其中cid:是固定的写法，而aaa是一个contentId。
		helper.setText("<body>这是图片：<img src='cid:head' /></body>", true);

		FileSystemResource file = new FileSystemResource(new File("C:\\Users\\RICK\\Pictures\\Saved Pictures\\fendou.jpg"));
		helper.addInline("head",file);
		mailSender.send(mimeMessage);
	}


	/**
	 * Desc :  模板邮件
	 * User : RICK
	 * Time : 2017/8/28 17:38
	  */
	@Test
	public void sendTemplateMail() throws Exception{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		//基本设置.
		helper.setFrom("zrbfree@163.com");//发送者.
		helper.setTo("297955686@qq.com");//接收者.
		helper.setSubject("模板邮件（邮件主题）");//邮件主题.

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", "rick");
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		// 设定去哪里读取相应的ftl模板
		cfg.setClassForTemplateLoading(this.getClass(), "/templates");
		// 在模板文件目录中寻找名称为name的模板文件
		Template template   = cfg.getTemplate("email.ftl");
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		helper.setText(html, true);
		mailSender.send(mimeMessage);
	}

}
