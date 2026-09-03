package com.afiliados.vendas.resource.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {

    @GetMapping("/admin")
    public String home() {
        return "redirect:/admin/ofertas";
    }

    @GetMapping("/admin/login")
    public String login() {
        return "admin/login";
    }
}
