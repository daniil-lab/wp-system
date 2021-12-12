package com.wp.system.services.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.wp.system.entity.user.User;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.notification.NotificationErrorCode;
import com.wp.system.request.notification.SendNotificationToAllUserRequest;
import com.wp.system.request.notification.SendNotificationToUserRequest;
import com.wp.system.response.notification.SendNotificationResponse;
import com.wp.system.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private FirebaseMessaging firebaseMessaging;

    @Autowired
    private UserService userService;

    public NotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
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
            throw new ServiceException(NotificationErrorCode.SEND_ERROR);
        }
    }

    public SendNotificationResponse sendNotificationToAllUsers(SendNotificationToAllUserRequest request) {
        try {
            List<User> users = this.userService.getAllUsers();

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
            throw new ServiceException(NotificationErrorCode.SEND_ERROR);
        }
    }
}
