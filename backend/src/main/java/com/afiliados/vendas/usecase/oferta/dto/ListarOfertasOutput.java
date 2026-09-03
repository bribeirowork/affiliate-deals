package com.afiliados.vendas.usecase.oferta.dto;

import java.util.List;

public record ListarOfertasOutput(
        List<OfertaDTO> ofertasDoDia,
        List<OfertaDTO> ofertasDaSemana
) {}
