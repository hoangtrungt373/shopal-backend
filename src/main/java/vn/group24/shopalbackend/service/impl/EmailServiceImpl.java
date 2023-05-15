package vn.group24.shopalbackend.service.impl;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import vn.group24.shopalbackend.controller.request.EmailDetailRequest;
import vn.group24.shopalbackend.service.EmailService;

/**
 *
 * @author ttg
 */
@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendEmail(EmailDetailRequest request) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
//
//            Map<String, Object> variables = new HashMap<>();
//            String otp = generateOtp();
//            variables.put("code", otp);

            Context context = new Context();
            context.setVariables(request.getProperties());

            helper.setFrom(sender);
            helper.setTo(request.getRecipient());
            helper.setSubject(request.getSubject());
            helper.setText(templateEngine.process(request.getTemplate(), context), true);

            javaMailSender.send(message);

            return "Send OK";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
