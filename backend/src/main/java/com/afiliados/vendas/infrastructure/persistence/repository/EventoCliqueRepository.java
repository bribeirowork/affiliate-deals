package com.afiliados.vendas.infrastructure.persistence.repository;

import com.afiliados.vendas.domain.evento.TipoDispositivo;
import com.afiliados.vendas.domain.evento.TipoEvento;
import com.afiliados.vendas.infrastructure.persistence.entity.EventoCliqueJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface EventoCliqueRepository extends JpaRepository<EventoCliqueJpaEntity, Long> {

    long countByCriadoEmBetween(Instant de, Instant ate);

    long countByTipoAndCriadoEmBetween(TipoEvento tipo, Instant de, Instant ate);

    long countByDeviceTipoAndCriadoEmBetween(TipoDispositivo deviceTipo, Instant de, Instant ate);

    List<EventoCliqueJpaEntity> findTop20ByCriadoEmBetweenOrderByCriadoEmDesc(Instant de, Instant ate);

    @Query(value = """
            SELECT o.nome_produto, COUNT(e.id) AS total
            FROM evento_clique e
            JOIN ofertas o ON o.id = e.oferta_id
            WHERE e.criado_em BETWEEN :de AND :ate
            GROUP BY e.oferta_id, o.nome_produto
            ORDER BY total DESC
            LIMIT 5
            """, nativeQuery = true)
    List<Object[]> topOfertasPorCliques(@Param("de") Instant de, @Param("ate") Instant ate);

    @Query(value = """
            SELECT CAST(criado_em AS DATE) AS dia, COUNT(*) AS total
            FROM evento_clique
            WHERE criado_em BETWEEN :de AND :ate
            GROUP BY dia
            ORDER BY dia DESC
            """, nativeQuery = true)
    List<Object[]> cliquesPorDia(@Param("de") Instant de, @Param("ate") Instant ate);
}
