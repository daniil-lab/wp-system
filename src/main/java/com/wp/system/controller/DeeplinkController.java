package com.wp.system.controller;

import com.wp.system.utils.DeeplinkObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Tag(name = "Deeplink")
public class DeeplinkController {
    @GetMapping("/apple-app-site-association")
    public ResponseEntity<DeeplinkObject> getDeeplinkObject() {
        return new ResponseEntity<>(new DeeplinkObject(), HttpStatus.OK);
    }
}
