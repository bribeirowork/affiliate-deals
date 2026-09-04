package com.afiliados.vendas.usecase.evento;

import com.afiliados.vendas.domain.evento.TipoDispositivo;
import com.afiliados.vendas.infrastructure.geoip.GeoIpService;
import com.afiliados.vendas.infrastructure.useragent.UserAgentParser;
import com.afiliados.vendas.usecase.evento.dto.RegistrarEventoInput;
import com.afiliados.vendas.usecase.evento.gateway.EventoGateway;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;

@Service
public class RegistrarEventoService {

    private final EventoGateway eventoGateway;
    private final GeoIpService geoIpService;
    private final UserAgentParser uaParser;

    public RegistrarEventoService(EventoGateway eventoGateway,
                                  GeoIpService geoIpService,
                                  UserAgentParser uaParser) {
        this.eventoGateway = eventoGateway;
        this.geoIpService = geoIpService;
        this.uaParser = uaParser;
    }

    // @Async garante que o controller retorna 204 imediatamente;
    // geo lookup e persistência acontecem em background sem bloquear o frontend.
    @Async
    public void registrar(RegistrarEventoInput input) {
        TipoDispositivo device = uaParser.detectarDispositivo(input.userAgent());
        String os      = uaParser.detectarOs(input.userAgent());
        String browser = uaParser.detectarBrowser(input.userAgent());
        String ipHash  = hashIp(input.ip());

        GeoIpService.GeoInfo geo = geoIpService.buscar(input.ip());

        eventoGateway.salvar(
                input.ofertaId(),
                input.tipo(),
                input.sessionId(),
                input.leadId(),
                device, os, browser,
                ipHash,
                geo.pais(), geo.estado(), geo.cidade(),
                Instant.now()
        );
    }

    private String hashIp(String ip) {
        if (ip == null) return null;
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(ip.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
