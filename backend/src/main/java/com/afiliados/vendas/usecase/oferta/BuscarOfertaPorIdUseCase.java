package com.afiliados.vendas.usecase.oferta;

import com.afiliados.vendas.usecase.oferta.dto.OfertaAdminDTO;
import com.afiliados.vendas.usecase.oferta.gateway.OfertaGateway;
import com.afiliados.vendas.usecase.oferta.mapper.OfertaDTOMapper;

/** Use Case: buscar uma oferta por id, para pré-preencher o formulário de edição. */
public class BuscarOfertaPorIdUseCase {

    private final OfertaGateway ofertaGateway;

    public BuscarOfertaPorIdUseCase(OfertaGateway ofertaGateway) {
        this.ofertaGateway = ofertaGateway;
    }

    public OfertaAdminDTO executar(Long id) {
        return ofertaGateway.buscarPorId(id)
                .map(OfertaDTOMapper::paraAdminDTO)
                .orElseThrow(() -> new OfertaNaoEncontradaException(id));
    }
}
