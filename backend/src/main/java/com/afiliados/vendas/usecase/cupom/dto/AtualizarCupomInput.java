package com.afiliados.vendas.usecase.cupom.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AtualizarCupomInput(
        Long id,
        String codigo,
        String loja,
        String linkOpcional,
        String tipoDesconto,
        BigDecimal valorDesconto,
        BigDecimal valorMinimoCompra,
        LocalDateTime dataValidade
) {}
