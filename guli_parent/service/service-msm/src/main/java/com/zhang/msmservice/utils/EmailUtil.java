package com.zhang.msmservice.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 邮箱工具类
 */
@Component
@ConfigurationProperties(prefix = "email.config")
public class EmailUtil {
    /**
     * 验证码长度
     */
    private int codeLen;

    /**
     * 发送邮箱验证码的qq号
     */
    private String qq;

    /**
     * 发送邮件的邮箱
     */
    private String toSendEmail;

    /**
     * 发件人
     */
    private String sender;

    /**
     * 开启IMAP/SMTP服务获取的授权码
     */
    private String authPwd;

    /**
     * 邮件的主题
     */
    private String title;

    /**
     * 邮件的内容
     */
    private String content;

    /**
     * 获取随机验证码:四位数字验证码
     * @return
     */
    public String getAuthCode() {  //由于数字0，1 和字母 o，l 有时分不清，所有没有数字0和1
        String[] strArr = new String[]{"0","1","2", "3", "4", "5", "6", "7", "8", "9"};
        List list = Arrays.asList(strArr);//将数组转换为集合
        Collections.shuffle(list);  //打乱集合顺序
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i)); //将集合转化为字符串
        }
        return stringBuilder.toString().substring(stringBuilder.length() - codeLen); //截取字符串为验证码长度
    }

    /**
     * 发送邮箱验证码
     * @param email
     * @param authCode
     */
    public void sendAuthCodeEmail(String email, String authCode) {
        try {
            SimpleEmail mail = new SimpleEmail();
            mail.setHostName("smtp.qq.com");//发送邮件的服务器
            mail.setAuthentication(qq, authPwd);//第一个参数是发送邮箱验证码的qq号，第二个参数是开启IMAP/SMTP服务获取的授权码
            mail.setFrom(toSendEmail, sender);  //第一个参数是发送邮件的邮箱，第二个参数是发件人
            mail.setSSLOnConnect(true); //使用安全链接
            mail.addTo(email);//接收的邮箱
            mail.setSubject(title);//设置邮件的主题
            mail.setMsg(content + authCode+",有效时间60s");//设置邮件的内容
            mail.send();//发送
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    public int getCodeLen() {
        return codeLen;
    }

    public void setCodeLen(int codeLen) {
        this.codeLen = codeLen;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getToSendEmail() {
        return toSendEmail;
    }

    public void setToSendEmail(String toSendEmail) {
        this.toSendEmail = toSendEmail;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAuthPwd() {
        return authPwd;
    }

    public void setAuthPwd(String authPwd) {
        this.authPwd = authPwd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

