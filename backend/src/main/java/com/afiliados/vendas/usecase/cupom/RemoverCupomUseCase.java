package com.afiliados.vendas.usecase.cupom;

import com.afiliados.vendas.usecase.cupom.gateway.CupomGateway;

public class RemoverCupomUseCase {

    private final CupomGateway cupomGateway;

    public RemoverCupomUseCase(CupomGateway cupomGateway) {
        this.cupomGateway = cupomGateway;
    }

    public void executar(Long id) {
        cupomGateway.buscarPorId(id)
                .orElseThrow(() -> new CupomNaoEncontradoException(id));
        cupomGateway.remover(id);
    }
}
