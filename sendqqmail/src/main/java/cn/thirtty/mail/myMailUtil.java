package cn.thirtty.mail;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class myMailUtil {

	public static void sendMail(String toEmail, String emailMsg)
			throws GeneralSecurityException, IOException, AddressException, MessagingException {
		// 1、读取邮件配置
		Properties props = new Properties();

		// 使用类加载器读取配置文件
		ClassLoader loader = myMailUtil.class.getClassLoader();
		props.load(loader.getResourceAsStream("mail.properties"));
		String targettalk = props.getProperty("targettalk");
		final String username = props.getProperty("username");
		final String password = props.getProperty("password");

		// QQ邮箱的SSL加密
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);

		props.put("mail.smtp.ssl.socketFactory", sf);

		// 2、创建验证器

		Authenticator authenticator = new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(username, password);

			}
		};
		// 1、连接
		Session session = Session.getDefaultInstance(props, authenticator);

		// 2、发送的内容对象Mesage
		Message message = new MimeMessage(session);
		// 2.1、发件人是谁
		message.setFrom(new InternetAddress(username));

		message.setRecipient(RecipientType.TO, new InternetAddress(toEmail));
		// 2.3 主题（标题）
		message.setSubject(targettalk);
		// 2.4 正文

		message.setContent(emailMsg, "text/html;charset=UTF-8");
		// 3、发�?
		Transport.send(message);
	}

	public static void main(String[] args) throws Exception {
		sendMail("sf2033@163.com", "ccce测试来吧");
	}
}
