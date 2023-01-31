package com.softwarehouse.serviceorder.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
    private static final String ADMIN_EMAIL = "douglasf.filho@gmail.com";

    private final SendGrid sg;

    public EmailService(@Value("${sendgrid.api-key}") final String sgSecret) {
        this.sg = new SendGrid(sgSecret);
    }

    public Response sendEmail(final String to, final String subject, final String body) {
        Email from = new Email(ADMIN_EMAIL);
        Email receiver = new Email(to);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, receiver, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            return this.sg.api(request);
        } catch (Exception ex) {
            log.error("error trying to send e-mail: {}", ex.getMessage());
            return null;
        }
    }

    public Response sendAdminEmail(final String subject, final String body) {
        return this.sendEmail(ADMIN_EMAIL, subject, body);
    }
}
