package com.wp.system.controller.logging;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Tag(name = "Image Store API")
@RequestMapping("/api/v1/image")
@SecurityRequirement(name = "Bearer")
public class SystemErrorLoggingController {
}
