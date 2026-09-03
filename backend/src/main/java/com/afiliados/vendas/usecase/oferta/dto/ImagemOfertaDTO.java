package com.afiliados.vendas.usecase.oferta.dto;

/** DTO enxuto para servir só os bytes de uma imagem — nunca trafega junto de listagens. */
public record ImagemOfertaDTO(
        byte[] dados,
        String contentType,
        String nomeArquivo
) {}
