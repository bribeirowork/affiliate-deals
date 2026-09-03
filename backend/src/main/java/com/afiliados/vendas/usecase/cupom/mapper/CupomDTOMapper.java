package com.afiliados.vendas.usecase.cupom.mapper;

import com.afiliados.vendas.domain.cupom.Cupom;
import com.afiliados.vendas.usecase.cupom.dto.CupomAdminDTO;
import com.afiliados.vendas.usecase.cupom.dto.CupomDTO;

public final class CupomDTOMapper {

    private CupomDTOMapper() {}

    public static CupomDTO paraDTO(Cupom cupom) {
        return new CupomDTO(
                cupom.getId(),
                cupom.getCodigo(),
                cupom.getLoja().name(),
                cupom.getLinkOpcional(),
                cupom.getTipoDesconto().name(),
                cupom.getValorDesconto(),
                cupom.getDataValidade()
        );
    }

    public static CupomAdminDTO paraAdminDTO(Cupom cupom) {
        return new CupomAdminDTO(
                cupom.getId(),
                cupom.getCodigo(),
                cupom.getLoja().name(),
                cupom.getLinkOpcional(),
                cupom.getTipoDesconto().name(),
                cupom.getValorDesconto(),
                cupom.getValorMinimoCompra(),
                cupom.getDataValidade(),
                cupom.getStatus().name()
        );
    }
}
