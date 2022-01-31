package com.wp.system.services.auth;

import com.wp.system.config.jwt.JwtProvider;
import com.wp.system.entity.auth.SmsSubmit;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.other.*;
import com.wp.system.other.sms.sendpulse.SendPulseSmsSender;
import com.wp.system.repository.auth.PhoneAuthRequestRepository;
import com.wp.system.repository.auth.SmsSubmitRepository;
import com.wp.system.repository.user.UserRepository;
import com.wp.system.request.auth.*;
import com.wp.system.response.auth.AuthDataResponse;
import com.wp.system.request.auth.CheckOnRegisterByPhoneRequest;
import com.wp.system.response.auth.SmsSubmitResponse;
import com.wp.system.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CurrencyLayerAdapter currencyLayerAdapter;

    @Autowired
    private PhoneAuthRequestRepository phoneAuthRequestRepository;

    @Autowired
    private SmsSubmitRepository smsSubmitRepository;

    public Boolean checkOnRegister(CheckOnRegisterByPhoneRequest request) {
        this.userService.getUserByUsername(request.getPhone());

        return true;
    }

    public Boolean checkOnRegisterEmail(CheckOnRegisterByEmailRequest request) {
        this.userService.getUserByEmail(request.getEmail());

        return true;
    }

    @Transactional
    public SmsSubmitResponse smsSubmitAttempt(SmsSubmitRequest request) {
//        EmailSender emailSender = new SendPulseEmailSender();
//
//        emailSender.setSubject("test");
//        emailSender.addBody("test");
//        emailSender.setTo("test", "developerdaniil@gmail.com");
//
//        try {
//            emailSender.addFile("testt", Files.readAllBytes(new File(CategoryIcon.HOME.getIconPath()).toPath()));
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//
//        emailSender.sendEmail();
        Random rand = new Random();

        StringBuilder codeString = new StringBuilder();
        for (int j = 0; j < 4; j++) {
            codeString.append(rand.nextInt(8) + 1);
        }

        int code = Integer.parseInt(codeString.toString());

        SmsSubmit smsSubmit = new SmsSubmit(code, request.getPhone());

        smsSubmitRepository.save(smsSubmit);

        SmsSender smsSender = new SendPulseSmsSender();

        smsSender.setPhone(request.getPhone());
        smsSender.setContent("Ваш код для подтверждения: " + code);

        if(!smsSender.send())
            throw new ServiceException("SMS does`t send", HttpStatus.INTERNAL_SERVER_ERROR);

        return new SmsSubmitResponse(smsSubmit.getId());
    }

    public Boolean smsSubmitResult(SmsSubmitResultRequest request) {
        Optional<SmsSubmit> foundSubmit = this.smsSubmitRepository.findById(request.getId());

        if(foundSubmit.isEmpty())
            throw new ServiceException("Invalid SMSSubmit ID", HttpStatus.BAD_REQUEST);

        SmsSubmit smsSubmit = foundSubmit.get();

        if(smsSubmit.getCode() != request.getCode())
            throw new ServiceException("Invalid SMS code", HttpStatus.BAD_REQUEST);

        smsSubmitRepository.delete(smsSubmit);

        return true;
    }

    public AuthDataResponse authBySmsSubmit(SmsSubmitResultRequest request) {
        Optional<SmsSubmit> foundSubmit = this.smsSubmitRepository.findById(request.getId());

        if(foundSubmit.isEmpty())
            throw new ServiceException("Invalid SMSSubmit ID", HttpStatus.BAD_REQUEST);

        SmsSubmit smsSubmit = foundSubmit.get();

        if(smsSubmit.getCode() != request.getCode())
            throw new ServiceException("Invalid SMS code", HttpStatus.BAD_REQUEST);

        smsSubmitRepository.delete(smsSubmit);

        User user = this.userService.getUserByUsername(smsSubmit.getPhone());

        return new AuthDataResponse(jwtProvider.generateToken(user.getUsername()), user);
    }

    public AuthDataResponse authUserByEmail(EmailAuthRequest request) {
        User user = userService.getUserByEmail(request.getEmail());

        byte[] passwordBytes = Base64.getDecoder().decode(request.getPassword());

        if(passwordEncoder.matches(new String(passwordBytes), user.getPassword()))
            return new AuthDataResponse(jwtProvider.generateToken(user.getUsername()), user);

        throw new ServiceException("Check given data. Auth failed.", HttpStatus.BAD_REQUEST);
    }

    public AuthDataResponse authUser(AuthRequest request) {
        User user = this.userService.getUserByUsername(request.getUsername());

        byte[] passwordBytes = Base64.getDecoder().decode(request.getPassword());

        if(passwordEncoder.matches(new String(passwordBytes), user.getPassword()))
            return new AuthDataResponse(jwtProvider.generateToken(user.getUsername()), user);

        throw new ServiceException("Check given data. Auth failed.", HttpStatus.BAD_REQUEST);
    }
}
