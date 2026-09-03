package com.afiliados.vendas.usecase.oferta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO público (consumido pela API REST / React). Nenhuma entidade de domínio
 * chega até aqui — a conversão é feita pelo OfertaDTOMapper.
 * As datas de expiração alimentam o cronômetro de urgência no frontend.
 */
public record OfertaDTO(
        Long id,
        String nomeProduto,
        String imagemUrl,
        String loja,
        BigDecimal valor,
        BigDecimal valorOriginal,
        String linkAfiliado,
        LocalDateTime dataExpiracaoDia,
        LocalDateTime dataExpiracaoSemana,
        List<CupomResumoDTO> cupons
) {}
