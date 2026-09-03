package com.afiliados.vendas.usecase.cupom;

import com.afiliados.vendas.domain.cupom.Cupom;
import com.afiliados.vendas.domain.cupom.TipoDesconto;
import com.afiliados.vendas.domain.oferta.Loja;
import com.afiliados.vendas.usecase.cupom.dto.AtualizarCupomInput;
import com.afiliados.vendas.usecase.cupom.dto.CupomAdminDTO;
import com.afiliados.vendas.usecase.cupom.gateway.CupomGateway;
import com.afiliados.vendas.usecase.cupom.mapper.CupomDTOMapper;

public class AtualizarCupomUseCase {

    private final CupomGateway cupomGateway;

    public AtualizarCupomUseCase(CupomGateway cupomGateway) {
        this.cupomGateway = cupomGateway;
    }

    public CupomAdminDTO executar(AtualizarCupomInput input) {
        Cupom cupom = cupomGateway.buscarPorId(input.id())
                .orElseThrow(() -> new CupomNaoEncontradoException(input.id()));

        cupom.atualizarDados(
                input.codigo(),
                Loja.valueOf(input.loja()),
                input.linkOpcional(),
                TipoDesconto.valueOf(input.tipoDesconto()),
                input.valorDesconto(),
                input.valorMinimoCompra(),
                input.dataValidade()
        );

        Cupom atualizado = cupomGateway.salvar(cupom);
        return CupomDTOMapper.paraAdminDTO(atualizado);
    }
}
