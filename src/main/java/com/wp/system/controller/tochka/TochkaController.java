package com.wp.system.controller.tochka;

import com.wp.system.services.tochka.TochkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller("api/v1/tochka")
public class TochkaController {
    @Autowired
    private TochkaService tochkaService;

    @GetMapping("/auth-hook")
    public RedirectView authHook(
            @RequestParam
                    String code
    ) {
        RedirectView redirect = new RedirectView();

        redirect.setUrl("walletbox.app/tochka-auth/" + code);

        return redirect;
    }
}
