package com.ttpai.test.email.util;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.ttpai.test.email.vo.MailInfo;

import freemarker.template.Template;

public class EMailSendUtil {
    private final static String host = "smtp.qq.com"; //服务器
    private final static String formName = "949616437@qq.com";//你的邮箱
    private final static String password = "qdlrieiecmrbbdah"; //授权码
    private final static String replayAddress = "949616437@qq.com"; //你的邮箱
    private final static String sendName = "邢金彪";


    public static void sendHtmlMail(MailInfo info, FreeMarkerConfigurer freeMarkerConfigurer, String templateName,
            Map<String, Object> map)throws Exception{
        info.setHost(host);
        info.setFormName(formName);
        info.setFormPassword(password);   //邮箱的授权码~不一定是密码
        info.setReplayAddress(replayAddress);
        info.setSsl("true");
        info.setContent(getMailText(freeMarkerConfigurer, templateName, map));

        Message message = getMessage(info);
        // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        // 设置HTML内容
        html.setContent(info.getContent(), "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
        // 将MiniMultipart对象设置为邮件内容
        message.setContent(mainPart);
        Transport.send(message);
    }

    public static String getMailText(FreeMarkerConfigurer freeMarkerConfigurer, String templateName,
            Map<String, Object> map) throws Exception {
        String htmlText = "";
        // 通过指定模板名获取FreeMarker模板实例
        Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
        // FreeMarker通过Map传递动态数据
        // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
        htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
        return htmlText;
    }

    public static void sendTextMail(MailInfo info) throws Exception {

        info.setHost(host);
        info.setFormName(formName);
        info.setFormPassword(password);   //网易邮箱的授权码~不一定是密码
        info.setReplayAddress(replayAddress);
        Message message = getMessage(info);
        //消息发送的内容
        message.setText(info.getContent());

        Transport.send(message);
    }

    private static Message getMessage(MailInfo info) throws Exception{
        final Properties p = System.getProperties() ;
        p.setProperty("mail.smtp.host", info.getHost());
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.user", info.getFormName());
        p.setProperty("mail.smtp.pass", info.getFormPassword());
        p.setProperty("mail.smtp.ssl.enable", info.getSsl());

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session session = Session.getInstance(p, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(p.getProperty("mail.smtp.user"),p.getProperty("mail.smtp.pass"));
            }
        });
        session.setDebug(true);
        Message message = new MimeMessage(session);
        //消息发送的主题
        message.setSubject(info.getSubject());
        //接受消息的人
        message.setReplyTo(InternetAddress.parse(info.getReplayAddress()));
        //消息的发送者
        message.setFrom(new InternetAddress(p.getProperty("mail.smtp.user"), sendName));
        // 创建邮件的接收者地址，并设置到邮件消息中
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(info.getToAddress()));
        // 消息发送的时间
        message.setSentDate(new Date());


        return message ;
    }
}