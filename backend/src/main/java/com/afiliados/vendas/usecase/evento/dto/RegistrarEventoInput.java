package com.afiliados.vendas.usecase.evento.dto;

import com.afiliados.vendas.domain.evento.TipoEvento;

public record RegistrarEventoInput(
        Long ofertaId,
        TipoEvento tipo,
        String sessionId,
        String leadId,
        String ip,
        String userAgent
) {}
