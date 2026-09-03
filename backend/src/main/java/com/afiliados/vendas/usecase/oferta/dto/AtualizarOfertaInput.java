package com.afiliados.vendas.usecase.oferta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AtualizarOfertaInput(
        Long id,
        String nomeProduto,
        String imagemUrl,
        byte[] imagemDados,
        String imagemContentType,
        String imagemNomeArquivo,
        boolean removerImagem,
        String loja,
        BigDecimal valor,
        BigDecimal valorOriginal,
        String linkAfiliado,
        LocalDateTime dataExpiracaoDia,
        LocalDateTime dataExpiracaoSemana
) {}
