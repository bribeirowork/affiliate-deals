package com.afiliados.vendas.infrastructure.persistence.repository;

import com.afiliados.vendas.domain.oferta.StatusOferta;
import com.afiliados.vendas.infrastructure.persistence.entity.OfertaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Adapter de infraestrutura (Spring Data). Só é usado dentro do OfertaGatewayImpl. */
public interface OfertaSpringDataRepository extends JpaRepository<OfertaJpaEntity, Long> {

    List<OfertaJpaEntity> findByStatus(StatusOferta status);
}
