package com.afiliados.vendas.resource.web;

import com.afiliados.vendas.usecase.evento.EventoDashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/admin/eventos")
public class EventoDashboardController {

    private static final ZoneId TZ_BR = ZoneId.of("America/Sao_Paulo");

    private final EventoDashboardService dashboardService;

    public EventoDashboardController(EventoDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public String dashboard(
            @RequestParam(required = false) String de,
            @RequestParam(required = false) String ate,
            Model model) {

        Instant instDe  = parseOuPadrao(de,  Instant.now().minus(30, ChronoUnit.DAYS));
        Instant instAte = parseOuPadrao(ate, Instant.now());

        model.addAttribute("dash", dashboardService.carregar(instDe, instAte, de, ate));
        return "admin/eventos/dashboard";
    }

    private Instant parseOuPadrao(String valor, Instant padrao) {
        if (valor == null || valor.isBlank()) return padrao;
        try {
            // datetime-local envia "YYYY-MM-DDTHH:mm" ou "YYYY-MM-DDTHH:mm:ss"
            LocalDateTime ldt = valor.length() == 16
                    ? LocalDateTime.parse(valor + ":00")
                    : LocalDateTime.parse(valor);
            return ldt.atZone(TZ_BR).toInstant();
        } catch (Exception e) {
            return padrao;
        }
    }
}
