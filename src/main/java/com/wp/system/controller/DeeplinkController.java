package com.wp.system.controller;

import com.wp.system.utils.DeeplinkObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Deeplink")
public class DeeplinkController {
    @GetMapping(value = "/apple-app-site-association", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeeplinkObject> getDeeplinkObject() {
        return new ResponseEntity<>(new DeeplinkObject(), HttpStatus.OK);
    }
}
