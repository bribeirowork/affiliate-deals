package com.afiliados.vendas.infrastructure.persistence.repository;

import com.afiliados.vendas.domain.cupom.StatusCupom;
import com.afiliados.vendas.infrastructure.persistence.entity.CupomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CupomSpringDataRepository extends JpaRepository<CupomJpaEntity, Long> {

    List<CupomJpaEntity> findByStatus(StatusCupom status);
}
