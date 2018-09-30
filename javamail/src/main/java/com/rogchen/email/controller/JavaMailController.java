package com.rogchen.email.controller;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * @Description: 控制器
 * @Product: IntelliJ IDEA
 * @Author Rogchen rogchen128@gmail.com
 * @Created Date: 2018/9/21 16:48
 **/
public class JavaMailController {

    public static void main(String[] args) throws Exception {
        for (Map map : lists()) {
            Properties props1 = new Properties();
            props1.setProperty("mail.smtp.auth", "true");
            props1.setProperty("mail.transport.protocol", "smtp");
            props1.setProperty("mail.smtp.host", map.get("host").toString());
            Session session = Session.getInstance(props1);
            session.setDebug(true);
            Message msg = getMimeMessage(session, map.get("senderAddress").toString(), map.get("recipientAddress").toString());
            Transport transport = session.getTransport();
            transport.connect(map.get("senderAccount").toString(), map.get("senderPassword").toString());
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        }
    }

    public static MimeMessage getMimeMessage(Session session, String senderAddress, String recipientAddress) throws Exception {
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("837831701@qq.com"));
        //设置邮件主题
        msg.setSubject("邮件主题", "UTF-8");
        //设置邮件正文
        msg.setContent("简单的纯文本邮件！" + senderAddress, "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }

    private static List<Map<String, String>> lists() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("senderAddress", "154047387@qq.com");
        map.put("recipientAddress", "837831701@qq.com");
        map.put("senderAccount", "154047387@qq.com");
        map.put("host", "smtp.qq.com");
        map.put("senderPassword", "uoqrgofrchmdbide");
        list.add(map);
        map = new HashMap<>();
        map.put("senderAddress", "chenhk128@163.com");
        map.put("recipientAddress", "837831701@qq.com");
        map.put("senderAccount", "chenhk128@163.com");
        map.put("host", "smtp.163.com");
        map.put("senderPassword", "tkluo0qc602");
        return list;
    }
}
