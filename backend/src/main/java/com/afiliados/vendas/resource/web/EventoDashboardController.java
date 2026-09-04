package com.afiliados.vendas.resource.web;

import com.afiliados.vendas.usecase.evento.EventoDashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/eventos")
public class EventoDashboardController {

    private final EventoDashboardService dashboardService;

    public EventoDashboardController(EventoDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("dash", dashboardService.carregar());
        return "admin/eventos/dashboard";
    }
}
