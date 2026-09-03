package com.afiliados.vendas.usecase.cupom;

import com.afiliados.vendas.usecase.cupom.dto.CupomAdminDTO;
import com.afiliados.vendas.usecase.cupom.gateway.CupomGateway;
import com.afiliados.vendas.usecase.cupom.mapper.CupomDTOMapper;

public class BuscarCupomPorIdUseCase {

    private final CupomGateway cupomGateway;

    public BuscarCupomPorIdUseCase(CupomGateway cupomGateway) {
        this.cupomGateway = cupomGateway;
    }

    public CupomAdminDTO executar(Long id) {
        return cupomGateway.buscarPorId(id)
                .map(CupomDTOMapper::paraAdminDTO)
                .orElseThrow(() -> new CupomNaoEncontradoException(id));
    }
}
