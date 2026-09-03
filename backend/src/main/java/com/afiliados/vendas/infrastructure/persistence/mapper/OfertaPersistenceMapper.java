package com.afiliados.vendas.infrastructure.persistence.mapper;

import com.afiliados.vendas.domain.oferta.ImagemProduto;
import com.afiliados.vendas.domain.oferta.Oferta;
import com.afiliados.vendas.infrastructure.persistence.entity.OfertaJpaEntity;

/**
 * Factory de infraestrutura: converte entre a entidade JPA (representação
 * relacional) e a entidade de domínio. Só o OfertaGatewayImpl conhece esta classe —
 * o Use Case nunca sabe que ela existe.
 */
public final class OfertaPersistenceMapper {

    private OfertaPersistenceMapper() {}

    public static Oferta paraDomain(OfertaJpaEntity jpaEntity) {
        ImagemProduto imagem = jpaEntity.getImagemDados() != null
                ? new ImagemProduto(jpaEntity.getImagemDados(), jpaEntity.getImagemContentType(),
                        jpaEntity.getImagemNomeArquivo())
                : null;

        return Oferta.reconstruir(
                jpaEntity.getId(),
                jpaEntity.getNomeProduto(),
                jpaEntity.getImagemUrl(),
                imagem,
                jpaEntity.getLoja(),
                jpaEntity.getValor(),
                jpaEntity.getValorOriginal(),
                jpaEntity.getLinkAfiliado(),
                jpaEntity.getDataExpiracaoDia(),
                jpaEntity.getDataExpiracaoSemana(),
                jpaEntity.getStatus(),
                jpaEntity.getCriadoEm()
        );
    }

    public static OfertaJpaEntity paraJpaEntity(Oferta oferta) {
        ImagemProduto imagem = oferta.getImagem();

        return new OfertaJpaEntity(
                oferta.getId(),
                oferta.getNomeProduto(),
                oferta.getImagemUrl(),
                imagem != null ? imagem.getDados() : null,
                imagem != null ? imagem.getTipoConteudo() : null,
                imagem != null ? imagem.getNomeArquivo() : null,
                oferta.getLoja(),
                oferta.getValor(),
                oferta.getValorOriginal(),
                oferta.getLinkAfiliado(),
                oferta.getDataExpiracaoDia(),
                oferta.getDataExpiracaoSemana(),
                oferta.getStatus(),
                oferta.getCriadoEm()
        );
    }
}
