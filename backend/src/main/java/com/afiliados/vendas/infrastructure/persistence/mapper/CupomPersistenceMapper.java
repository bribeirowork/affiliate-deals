package com.afiliados.vendas.infrastructure.persistence.mapper;

import com.afiliados.vendas.domain.cupom.Cupom;
import com.afiliados.vendas.infrastructure.persistence.entity.CupomJpaEntity;

public final class CupomPersistenceMapper {

    private CupomPersistenceMapper() {}

    public static Cupom paraDomain(CupomJpaEntity jpaEntity) {
        return Cupom.reconstruir(
                jpaEntity.getId(),
                jpaEntity.getCodigo(),
                jpaEntity.getLoja(),
                jpaEntity.getLinkOpcional(),
                jpaEntity.getTipoDesconto(),
                jpaEntity.getValorDesconto(),
                jpaEntity.getValorMinimoCompra(),
                jpaEntity.getDataValidade(),
                jpaEntity.getStatus(),
                jpaEntity.getCriadoEm()
        );
    }

    public static CupomJpaEntity paraJpaEntity(Cupom cupom) {
        return new CupomJpaEntity(
                cupom.getId(),
                cupom.getCodigo(),
                cupom.getLoja(),
                cupom.getLinkOpcional(),
                cupom.getTipoDesconto(),
                cupom.getValorDesconto(),
                cupom.getValorMinimoCompra(),
                cupom.getDataValidade(),
                cupom.getStatus(),
                cupom.getCriadoEm()
        );
    }
}
