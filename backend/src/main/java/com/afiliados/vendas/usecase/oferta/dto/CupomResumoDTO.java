package com.afiliados.vendas.usecase.oferta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Recorte do cupom para exibição embutida sob uma oferta da mesma loja.
 * Propositalmente próprio deste contexto (não reaproveita o CupomDTO do
 * bounded context de cupom) para manter Oferta e Cupom desacoplados.
 */
public record CupomResumoDTO(
        Long id,
        String codigo,
        String linkOpcional,
        String tipoDesconto,
        BigDecimal valorDesconto,
        LocalDateTime dataValidade
) {}
