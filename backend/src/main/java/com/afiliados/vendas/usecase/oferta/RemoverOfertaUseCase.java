package com.afiliados.vendas.usecase.oferta;

import com.afiliados.vendas.usecase.oferta.gateway.OfertaGateway;

public class RemoverOfertaUseCase {

    private final OfertaGateway ofertaGateway;

    public RemoverOfertaUseCase(OfertaGateway ofertaGateway) {
        this.ofertaGateway = ofertaGateway;
    }

    public void executar(Long id) {
        ofertaGateway.buscarPorId(id)
                .orElseThrow(() -> new OfertaNaoEncontradaException(id));
        ofertaGateway.remover(id);
    }
}
