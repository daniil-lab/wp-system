package com.wp.system.services.email;

import com.wp.system.entity.email.EmailMail;
import com.wp.system.entity.email.EmailSubmitRequest;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.utils.email.EmailBlank;
import com.wp.system.utils.email.EmailUtil;
import com.wp.system.repository.email.EmailMailRepository;
import com.wp.system.repository.email.EmailSubmitRequestRepository;
import com.wp.system.request.email.SubmitEmailVerificationRequest;
import com.wp.system.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailSubmitRequestRepository emailSubmitRequestRepository;

    @Autowired
    private EmailMailRepository emailMailRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public boolean resendEmailVerification(UUID userId) {
        Optional<EmailSubmitRequest> foundSubmit = emailSubmitRequestRepository.findByUserId(userId);

        if(foundSubmit.isEmpty())
            throw new ServiceException("Submit request not found", HttpStatus.NOT_FOUND);

        User user = userService.getUserById(userId);

        EmailSubmitRequest submitRequest = foundSubmit.get();

        submitRequest.setExpiration(Instant.now().plus(1, ChronoUnit.DAYS));

        Tuple2<String, String> blank = EmailBlank.submitEmail(submitRequest.getCode());

        MimeMessage msg = EmailUtil.createMail(
                mailSender.createMimeMessage(),
                user.getEmail().getAddress(),
                blank.getT1(),
                blank.getT2()
        );

        if(msg != null)
            mailSender.send(msg);

        emailSubmitRequestRepository.save(submitRequest);

        return true;
    }

    @Transactional
    public boolean submitEmailVerification(SubmitEmailVerificationRequest request) {
        Optional<EmailSubmitRequest> foundSubmit = emailSubmitRequestRepository.findByUserId(request.getUserId());

        if(foundSubmit.isEmpty())
            throw new ServiceException("Submit request not found", HttpStatus.NOT_FOUND);

        EmailSubmitRequest submitRequest = foundSubmit.get();

        if(submitRequest.getExpiration().isAfter(Instant.now())) {
            deleteEmailVerificationRequest(request.getUserId());
            throw new ServiceException("Submit request expiration error. Retry submit.", HttpStatus.BAD_REQUEST);
        }

        if(submitRequest.getCode() != request.getCode())
            throw new ServiceException("Invalid code", HttpStatus.BAD_REQUEST);

        userService.activateUserEmail(request.getUserId());

        emailSubmitRequestRepository.delete(submitRequest);

        return true;
    }

    public void deleteEmailVerificationRequest(UUID userId) {
        Optional<EmailSubmitRequest> validateEmailSubmit = emailSubmitRequestRepository.findByUserId(userId);

        validateEmailSubmit.ifPresent(emailSubmitRequest -> emailSubmitRequestRepository.delete(emailSubmitRequest));
    }

    @Transactional
    public boolean sendEmailVerificationMail(UUID userId) {
        Optional<EmailSubmitRequest> validateEmailSubmit = emailSubmitRequestRepository.findByUserId(userId);

        if(validateEmailSubmit.isPresent())
            throw new ServiceException("User already have verification request", HttpStatus.BAD_REQUEST);

        User user = userService.getUserById(userId);

        if(user.getEmail().getAddress() == null)
            throw new ServiceException("User don't have EMAIL", HttpStatus.BAD_REQUEST);

        EmailSubmitRequest emailSubmitRequest = new EmailSubmitRequest(user.getId());

        Tuple2<String, String> blank = EmailBlank.submitEmail(emailSubmitRequest.getCode());

        MimeMessage msg = EmailUtil.createMail(
                mailSender.createMimeMessage(),
                user.getEmail().getAddress(),
                blank.getT1(),
                blank.getT2()
        );

        if(msg != null)
            mailSender.send(msg);

        emailSubmitRequestRepository.save(emailSubmitRequest);

        return true;
    }

    @Transactional
    public EmailMail sendEmailMessage(User user, String subject, String body) {
        EmailMail mail = new EmailMail(user, subject, body);

        MimeMessage msg = EmailUtil.createMail(
                mailSender.createMimeMessage(),
                user.getEmail().getAddress(),
                subject,
                body
        );

        if(msg != null)
            mailSender.send(msg);

        emailMailRepository.save(mail);

        return mail;
    }
}
