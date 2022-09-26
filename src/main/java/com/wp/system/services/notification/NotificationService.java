package com.wp.system.services.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.repository.user.UserRepository;
import com.wp.system.request.notification.SendNotificationToAllUserRequest;
import com.wp.system.request.notification.SendNotificationToSomeUsersRequest;
import com.wp.system.request.notification.SendNotificationToUserRequest;
import com.wp.system.response.notification.SendNotificationResponse;
import com.wp.system.response.notification.SendNotificationToSomeUsersResponse;
import com.wp.system.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private FirebaseMessaging firebaseMessaging;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public SendNotificationToSomeUsersResponse sendNotificationToSomeUsers(SendNotificationToSomeUsersRequest request) {
        try {
            List<User> users = new ArrayList<>();

            request.getUserId().forEach((id) -> {
                users.add(userService.getUserById(id));
            });

            Notification notification = Notification
                    .builder()
                    .setTitle(request.getHeader())
                    .setBody(request.getBody())
                    .build();

            for(User user : users)
                for (String deviceToken : user.getDeviceTokens()) {
                    Message message = Message
                            .builder()
                            .setToken(deviceToken)
                            .setNotification(notification)
                            .build();

                    firebaseMessaging.send(message);
                }

            return new SendNotificationToSomeUsersResponse(users, request.getHeader(), request.getBody());
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw new ServiceException("Notification send error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public SendNotificationResponse sendNotificationToUser(SendNotificationToUserRequest request) {
        try {
            User user = this.userService.getUserById(request.getUserId());

            Notification notification = Notification
                    .builder()
                    .setTitle(request.getHeader())
                    .setBody(request.getBody())
                    .build();

            for (String deviceToken : user.getDeviceTokens()) {
                Message message = Message
                        .builder()
                        .setToken(deviceToken)
                        .setNotification(notification)
                        .build();

                firebaseMessaging.send(message);
            }

            return new SendNotificationResponse(user, request.getHeader(), request.getBody());
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw new ServiceException("Notification send error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public SendNotificationResponse sendNotificationToAllUsers(SendNotificationToAllUserRequest request) {
        try {
            List<User> users = userRepository.findAll();

            Notification notification = Notification
                    .builder()
                    .setTitle(request.getHeader())
                    .setBody(request.getBody())
                    .build();

            for (User user : users) {
                for (String deviceToken : user.getDeviceTokens()) {
                    Message message = Message
                            .builder()
                            .setToken(deviceToken)
                            .setNotification(notification)
                            .build();

                    firebaseMessaging.send(message);
                }
            }

            return new SendNotificationResponse(null, request.getHeader(), request.getBody());
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw new ServiceException("Notification not send", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
