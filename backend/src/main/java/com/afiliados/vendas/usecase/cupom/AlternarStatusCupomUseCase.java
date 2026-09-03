package com.afiliados.vendas.usecase.cupom;

import com.afiliados.vendas.domain.cupom.Cupom;
import com.afiliados.vendas.usecase.cupom.dto.CupomAdminDTO;
import com.afiliados.vendas.usecase.cupom.gateway.CupomGateway;
import com.afiliados.vendas.usecase.cupom.mapper.CupomDTOMapper;

public class AlternarStatusCupomUseCase {

    private final CupomGateway cupomGateway;

    public AlternarStatusCupomUseCase(CupomGateway cupomGateway) {
        this.cupomGateway = cupomGateway;
    }

    public CupomAdminDTO executar(Long id, boolean habilitar) {
        Cupom cupom = cupomGateway.buscarPorId(id)
                .orElseThrow(() -> new CupomNaoEncontradoException(id));

        if (habilitar) {
            cupom.habilitar();
        } else {
            cupom.desabilitar();
        }

        Cupom atualizado = cupomGateway.salvar(cupom);
        return CupomDTOMapper.paraAdminDTO(atualizado);
    }
}
