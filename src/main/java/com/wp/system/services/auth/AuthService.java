package com.wp.system.services.auth;

import com.wp.system.config.jwt.JwtProvider;
import com.wp.system.entity.auth.PhoneAuthData;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.auth.AuthErrorCode;
import com.wp.system.exception.user.UserErrorCode;
import com.wp.system.other.CurrencyLayerAdapter;
import com.wp.system.other.WalletType;
import com.wp.system.repository.auth.PhoneAuthRequestRepository;
import com.wp.system.repository.user.UserRepository;
import com.wp.system.request.auth.EmailAuthRequest;
import com.wp.system.request.auth.PhoneAuthAttemptRequest;
import com.wp.system.request.auth.AuthRequest;
import com.wp.system.request.auth.PhoneAuthCheckRequest;
import com.wp.system.response.auth.AuthDataResponse;
import com.wp.system.response.auth.PhoneAuthRequestResponse;
import com.wp.system.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public AuthDataResponse authUserByEmail(EmailAuthRequest request) {
        Optional<User> foundUser = this.userRepository.findByEmail(request.getEmail());

        if(foundUser.isEmpty())
            throw new ServiceException(UserErrorCode.NOT_FOUND);

        User user = foundUser.get();

        byte[] passwordBytes = Base64.getDecoder().decode(request.getPassword());

        if(passwordEncoder.matches(new String(passwordBytes), user.getPassword()))
            return new AuthDataResponse(jwtProvider.generateToken(user.getUsername()), user);

        throw new ServiceException(AuthErrorCode.INVALID_DATA);
    }

    public AuthDataResponse authUser(AuthRequest request) {
        User user = this.userService.getUserByUsername(request.getUsername());

        if(user.getPinCode() != null)
            if(request.getCode() == null || !user.getPinCode().equals(request.getCode()))
                throw new ServiceException(AuthErrorCode.INVALID_PINCODE);

        byte[] passwordBytes = Base64.getDecoder().decode(request.getPassword());

        if(passwordEncoder.matches(new String(passwordBytes), user.getPassword()))
            return new AuthDataResponse(jwtProvider.generateToken(user.getUsername()), user);

        throw new ServiceException(AuthErrorCode.INVALID_DATA);
    }

    public PhoneAuthRequestResponse createPhoneAuthRequest(PhoneAuthAttemptRequest request) {
        User foundUser = this.userService.getUserByUsername(request.getPhone());

        Random random = new Random();

        if(foundUser.getPinCode() != null)
            if(request.getPincode() == null || !foundUser.getPinCode().equals(request.getPincode()))
                throw new ServiceException(AuthErrorCode.INVALID_PINCODE);

        int code = random.nextInt(1000, 9999);

        System.out.println(code);

        PhoneAuthData data = new PhoneAuthData(request.getPhone(), code, foundUser);

        phoneAuthRequestRepository.save(data);

        return new PhoneAuthRequestResponse(data.getId());
    }

    public PhoneAuthData getPhoneAuthDataById(UUID id) {
        Optional<PhoneAuthData> phoneAuthData = this.phoneAuthRequestRepository.findById(id);

        if(phoneAuthData.isEmpty())
            throw new ServiceException(AuthErrorCode.PHONE_AUTH_DATA_NOT_FOUND);

        return phoneAuthData.get();
    }

    @Transactional
    public AuthDataResponse checkPhoneAuthRequest(PhoneAuthCheckRequest request) {
        PhoneAuthData authData = this.getPhoneAuthDataById(request.getRequestId());

        if(authData.getCode() != request.getCode())
            throw new ServiceException(AuthErrorCode.INVALID_PHONE_CODE);

        this.phoneAuthRequestRepository.delete(authData);

        return new AuthDataResponse(jwtProvider.generateToken(authData.getUser().getUsername()), authData.getUser());
    }
}
