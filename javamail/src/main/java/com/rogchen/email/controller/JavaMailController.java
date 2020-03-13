package com.rogchen.email.controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @Description: 控制器
 * @Product: IntelliJ IDEA
 * @Author Rogchen rogchen128@gmail.com
 * @Created Date: 2018/9/21 16:48
 **/
public class JavaMailController {


    public static void main(String[] args) throws Exception {
        int count = 300;
        CountDownLatch downLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        downLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (Map map : lists()) {
                        Properties props1 = new Properties();
                        props1.setProperty("mail.smtp.auth", "true");
                        props1.setProperty("mail.transport.protocol", map.get("protocol").toString());
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
                        Message msg = null;
                        try {
                            msg = getMimeMessage(session, map.get("senderAddress").toString(), map.get("recipientAddress").toString());
                            Transport.send(msg);    //直接使用静态方法，
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            downLatch.countDown();
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
        msg.setSubject("126-300并发邮件发送", "UTF-8");
        //设置邮件正文
        msg.setContent("126-300并发邮件发送", "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        return msg;
    }

    private static List<Map<String, String>> lists() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
//        map.put("protocol","smtp");   //默认smtp
//        map.put("senderAddress", "chenhk128@163.com");
//        map.put("recipientAddress", "837831701@qq.com");
//        map.put("senderAccount", "chenhk128@163.com");
//        map.put("host", "smtp.163.com");
//        map.put("senderPassword", "tkluo0qc602");
//        list.add(map);
//        map = new HashMap<>();
//      //  map.put("protocol","smtp");   //默认smtp
        map.put("protocol", "imap");
        map.put("senderAddress", "RS12333@mohrss.gov.cn");
        map.put("recipientAddress", "chenhk128@126.com");
        map.put("senderAccount", "RS12333@mohrss.gov.cn");
        map.put("host", "smail.mohrss.gov.cn");
        map.put("senderPassword", "Mohrss#123456");
        list.add(map);
        return list;
    }
}
