package com.afiliados.vendas.infrastructure.persistence.repository;

import com.afiliados.vendas.infrastructure.persistence.entity.EventoCliqueJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoCliqueRepository extends JpaRepository<EventoCliqueJpaEntity, Long> {}
