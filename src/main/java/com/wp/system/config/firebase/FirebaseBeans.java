package com.wp.system.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FirebaseBeans {

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException  {
        FileInputStream configStream = new FileInputStream("src/main/java/com/wp/system/config/firebase/conf.json");

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(configStream)).build();

        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions);

        return FirebaseMessaging.getInstance(app);
    }
}
