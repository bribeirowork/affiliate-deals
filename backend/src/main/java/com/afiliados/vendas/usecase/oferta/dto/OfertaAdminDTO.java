package com.afiliados.vendas.usecase.oferta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO usado pelo Backoffice (Thymeleaf) — carrega campos administrativos
 * (id, status, datas de expiração) que a vitrine pública não precisa ver.
 */
public record OfertaAdminDTO(
        Long id,
        String nomeProduto,
        String imagemUrl,
        boolean possuiImagemPropria,
        String loja,
        BigDecimal valor,
        BigDecimal valorOriginal,
        String linkAfiliado,
        LocalDateTime dataExpiracaoDia,
        LocalDateTime dataExpiracaoSemana,
        String status
) {}
