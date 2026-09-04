package com.afiliados.vendas.resource.api;

import com.afiliados.vendas.domain.evento.TipoEvento;
import com.afiliados.vendas.usecase.evento.RegistrarEventoService;
import com.afiliados.vendas.usecase.evento.dto.RegistrarEventoInput;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eventos")
public class EventoRestController {

    private final RegistrarEventoService registrarEventoService;

    public EventoRestController(RegistrarEventoService registrarEventoService) {
        this.registrarEventoService = registrarEventoService;
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody EventoPayload payload,
                                          HttpServletRequest request) {
        String ip = extrairIp(request);
        String userAgent = request.getHeader("User-Agent");

        registrarEventoService.registrar(new RegistrarEventoInput(
                payload.ofertaId(),
                payload.tipo(),
                payload.sessionId(),
                payload.leadId(),
                ip,
                userAgent
        ));

        // Retorna 204 imediatamente — processamento ocorre em background (@Async)
        return ResponseEntity.noContent().build();
    }

    private String extrairIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    public record EventoPayload(
            Long ofertaId,
            TipoEvento tipo,
            String sessionId,
            String leadId
    ) {}
}
