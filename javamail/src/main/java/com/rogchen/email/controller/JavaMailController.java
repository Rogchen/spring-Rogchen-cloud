package com.rogchen.email.controller;

import javax.mail.*;
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
//            //方法一
//            Session session = Session.getInstance(props1);
//            session.setDebug(true);
//            Message msg = getMimeMessage(session, map.get("senderAddress").toString(), map.get("recipientAddress").toString());
//            Transport transport = session.getTransport();
//            transport.connect(map.get("senderAccount").toString(), map.get("senderPassword").toString());
//            transport.sendMessage(msg, msg.getAllRecipients());
//            transport.close();
              //方法二
            //利用上述的用户名和密码构造一个Authenticator对象，并把它给Session，
            //当需要进行验证的时候，会自动从Session中去取该Authenticator对象
            Authenticator authenticator = new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // TODO Auto-generated method stub
                    return new PasswordAuthentication(map.get("senderAccount").toString(), map.get("senderPassword").toString());
                }

            };
            //利用上述提供给系统的属性和Authenticator构造一个Session对象
            Session session = Session.getInstance(props1, authenticator);
            session.setDebug(true);
            Message msg = getMimeMessage(session, map.get("senderAddress").toString(), map.get("recipientAddress").toString());
            Transport.send(msg);    //直接使用静态方法，
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
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipientAddress));
        //设置邮件主题
        msg.setSubject("测试邮件发送", "UTF-8");
        //设置邮件正文
        msg.setContent("简单的纯文本邮", "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        return msg;
    }

    private static List<Map<String, String>> lists() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
//        map.put("senderAddress", "######@qq.com");
//        map.put("recipientAddress", "######@qq.com");
//        map.put("senderAccount", "######@qq.com");
//        map.put("host", "smtp.qq.com");
//        map.put("senderPassword", "######");
//        list.add(map);
        map = new HashMap<>();
        map.put("senderAddress", "######@163.com");
        map.put("recipientAddress", "######@qq.com");
        map.put("senderAccount", "######@163.com");
        map.put("host", "smtp.163.com");
        map.put("senderPassword", "######");
        list.add(map);
        return list;
    }
}
