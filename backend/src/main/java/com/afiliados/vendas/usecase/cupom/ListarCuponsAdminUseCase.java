package com.afiliados.vendas.usecase.cupom;

import com.afiliados.vendas.usecase.cupom.dto.CupomAdminDTO;
import com.afiliados.vendas.usecase.cupom.gateway.CupomGateway;
import com.afiliados.vendas.usecase.cupom.mapper.CupomDTOMapper;

import java.util.List;

public class ListarCuponsAdminUseCase {

    private final CupomGateway cupomGateway;

    public ListarCuponsAdminUseCase(CupomGateway cupomGateway) {
        this.cupomGateway = cupomGateway;
    }

    public List<CupomAdminDTO> executar() {
        return cupomGateway.buscarTodos().stream()
                .map(CupomDTOMapper::paraAdminDTO)
                .toList();
    }
}
