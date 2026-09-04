package com.afiliados.vendas.infrastructure.persistence.gateway;

import com.afiliados.vendas.domain.evento.TipoDispositivo;
import com.afiliados.vendas.domain.evento.TipoEvento;
import com.afiliados.vendas.infrastructure.persistence.entity.EventoCliqueJpaEntity;
import com.afiliados.vendas.infrastructure.persistence.repository.EventoCliqueRepository;
import com.afiliados.vendas.usecase.evento.gateway.EventoGateway;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class EventoGatewayImpl implements EventoGateway {

    private final EventoCliqueRepository repository;

    public EventoGatewayImpl(EventoCliqueRepository repository) {
        this.repository = repository;
    }

    @Override
    public void salvar(Long ofertaId, TipoEvento tipo, String sessionId, String leadId,
                       TipoDispositivo deviceTipo, String os, String browser, String ipHash,
                       String pais, String estado, String cidade, Instant criadoEm) {
        repository.save(new EventoCliqueJpaEntity(
                ofertaId, tipo, sessionId, leadId,
                deviceTipo, os, browser, ipHash,
                pais, estado, cidade, criadoEm
        ));
    }
}
