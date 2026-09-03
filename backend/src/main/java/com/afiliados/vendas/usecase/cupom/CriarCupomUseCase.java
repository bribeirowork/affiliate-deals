package com.afiliados.vendas.usecase.cupom;

import com.afiliados.vendas.domain.cupom.Cupom;
import com.afiliados.vendas.domain.cupom.TipoDesconto;
import com.afiliados.vendas.domain.oferta.Loja;
import com.afiliados.vendas.usecase.cupom.dto.CriarCupomInput;
import com.afiliados.vendas.usecase.cupom.dto.CupomAdminDTO;
import com.afiliados.vendas.usecase.cupom.gateway.CupomGateway;
import com.afiliados.vendas.usecase.cupom.mapper.CupomDTOMapper;

public class CriarCupomUseCase {

    private final CupomGateway cupomGateway;

    public CriarCupomUseCase(CupomGateway cupomGateway) {
        this.cupomGateway = cupomGateway;
    }

    public CupomAdminDTO executar(CriarCupomInput input) {
        Cupom cupom = Cupom.criar(
                input.codigo(),
                Loja.valueOf(input.loja()),
                input.linkOpcional(),
                TipoDesconto.valueOf(input.tipoDesconto()),
                input.valorDesconto(),
                input.valorMinimoCompra(),
                input.dataValidade()
        );

        Cupom salvo = cupomGateway.salvar(cupom);
        return CupomDTOMapper.paraAdminDTO(salvo);
    }
}
