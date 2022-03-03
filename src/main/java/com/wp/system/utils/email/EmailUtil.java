package com.wp.system.utils.email;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public class EmailUtil {

    public static MimeMessage createMail(MimeMessage msg, String toEmail, String subject, String body){
        try
        {
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("app@walletbox.app", "no-reply"));

            msg.setReplyTo(InternetAddress.parse("app@walletbox.app", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            return msg;
        }
        catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
