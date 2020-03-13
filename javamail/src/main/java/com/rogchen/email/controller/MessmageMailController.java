package com.rogchen.email.controller;

import org.springframework.stereotype.Controller;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen rogchen128@gmail.com
 * @Created Date: 18-10-10 22:21
 **/
@Controller
public class MessmageMailController {

    public static void main(String[] args) throws Exception {
        for (Map map : lists()) {
            Properties props1 = new Properties();
            props1.setProperty("mail.transport.protocol", "smtp");
            props1.setProperty("mail.smtp.auth", "true");
            props1.setProperty("mail.smtp.host", map.get("host").toString());
            Session session = Session.getInstance(props1);
            session.setDebug(true);
            Message msg = fileMimeMessage(session, map.get("senderAddress").toString(), map.get("recipientAddress").toString());
            Transport transport = session.getTransport("smtp");
            transport.connect(map.get("senderAccount").toString(), map.get("senderPassword").toString());
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        }
    }
    private static List<Map<String, String>> lists() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
//        map.put("senderAddress", "######@qq.com");
//        map.put("recipientAddress", "######@qq.com");
//        map.put("senderAccount", "######@qq.com");
//        map.put("host", "smtp.qq.com");
//        map.put("senderPassword", "#");
//        list.add(map);
        map = new HashMap<>();
        map.put("recipientAddress", "######@qq.com");
        map.put("senderAddress", "######@163.com");
        map.put("senderAccount", "######@163.com");
        map.put("host", "smtp.163.com");
        map.put("senderPassword", "##");
        list.add(map);
        return list;
    }
    /**
     *  普通文本
     * @param session
     * @param senderAddress
     * @param recipientAddress
     * @return
     * @throws Exception
     */
    public static MimeMessage sampleMimeMessage(Session session, String senderAddress, String recipientAddress) throws Exception {
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置邮件主题
        msg.setSubject("测试邮件发送", "UTF-8");
        //设置邮件正文
        msg.setContent("简单的纯文本邮", "text/html;charset=UTF-8");
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipientAddress));
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        return msg;
    }


    /**
     *  发送附件-图片为例，文本需要测试
     * @param session
     * @param senderAddress
     * @param recipientAddress
     * @return
     * @throws MessagingException
     */
    public static MimeMessage fileMimeMessage(Session session, String senderAddress, String recipientAddress) throws MessagingException, UnsupportedEncodingException {
        Multipart mp = new MimeMultipart();   // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置邮件主题
        msg.setSubject("欢迎使用附件系统", "UTF-8");
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipientAddress));
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        //多个附件多个bp
        //使用MimeBodyPart构建附件
        BodyPart bp = new MimeBodyPart();
        FileDataSource file = new FileDataSource(new File("/home/chenhk/abc.png"));
        bp.setDataHandler(new DataHandler(file));
        bp.setFileName(MimeUtility.encodeText(file.getName(), "UTF-8", "UTF-8"));
        mp.addBodyPart(bp);

        msg.setContent(mp);
        msg.saveChanges();
        return msg;
    }
}
