package com.afiliados.vendas.usecase.evento.gateway;

import com.afiliados.vendas.domain.evento.TipoDispositivo;
import com.afiliados.vendas.domain.evento.TipoEvento;

import java.time.Instant;

public interface EventoGateway {

    void salvar(
            Long ofertaId,
            TipoEvento tipo,
            String sessionId,
            String leadId,
            TipoDispositivo deviceTipo,
            String os,
            String browser,
            String ipHash,
            String pais,
            String estado,
            String cidade,
            Instant criadoEm
    );
}
