package com.afiliados.vendas.infrastructure.geoip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class GeoIpService {

    private static final Logger log = LoggerFactory.getLogger(GeoIpService.class);
    private static final String API = "http://ip-api.com/json/{ip}?fields=status,country,regionName,city&lang=pt";

    private final RestTemplate restTemplate;

    public GeoIpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GeoInfo buscar(String ip) {
        if (ip == null || ip.isBlank() || ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
            return GeoInfo.desconhecido();
        }
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> resp = restTemplate.getForObject(API, Map.class, ip);
            if (resp == null || !"success".equals(resp.get("status"))) return GeoInfo.desconhecido();
            return new GeoInfo(
                    (String) resp.get("country"),
                    (String) resp.get("regionName"),
                    (String) resp.get("city")
            );
        } catch (Exception e) {
            log.debug("Geo lookup falhou para IP {}: {}", ip, e.getMessage());
            return GeoInfo.desconhecido();
        }
    }

    public record GeoInfo(String pais, String estado, String cidade) {
        static GeoInfo desconhecido() { return new GeoInfo(null, null, null); }
    }
}
