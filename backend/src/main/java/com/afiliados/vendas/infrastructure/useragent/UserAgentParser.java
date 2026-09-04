package com.afiliados.vendas.infrastructure.useragent;

import com.afiliados.vendas.domain.evento.TipoDispositivo;
import org.springframework.stereotype.Component;

@Component
public class UserAgentParser {

    public TipoDispositivo detectarDispositivo(String ua) {
        if (ua == null) return TipoDispositivo.DESKTOP;
        String lower = ua.toLowerCase();
        return (lower.contains("mobile") || lower.contains("android") || lower.contains("iphone") || lower.contains("ipad"))
                ? TipoDispositivo.MOBILE
                : TipoDispositivo.DESKTOP;
    }

    public String detectarOs(String ua) {
        if (ua == null) return "Desconhecido";
        if (ua.contains("Windows")) return "Windows";
        if (ua.contains("Android")) return "Android";
        if (ua.contains("iPhone") || ua.contains("iPad")) return "iOS";
        if (ua.contains("Mac OS")) return "macOS";
        if (ua.contains("Linux")) return "Linux";
        return "Outro";
    }

    public String detectarBrowser(String ua) {
        if (ua == null) return "Desconhecido";
        if (ua.contains("Edg/")) return "Edge";
        if (ua.contains("OPR/") || ua.contains("Opera")) return "Opera";
        if (ua.contains("Chrome")) return "Chrome";
        if (ua.contains("Firefox")) return "Firefox";
        if (ua.contains("Safari")) return "Safari";
        return "Outro";
    }
}
