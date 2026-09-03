package com.afiliados.vendas.infrastructure.persistence.gateway;

import com.afiliados.vendas.domain.oferta.Oferta;
import com.afiliados.vendas.domain.oferta.StatusOferta;
import com.afiliados.vendas.infrastructure.persistence.entity.OfertaJpaEntity;
import com.afiliados.vendas.infrastructure.persistence.mapper.OfertaPersistenceMapper;
import com.afiliados.vendas.infrastructure.persistence.repository.OfertaSpringDataRepository;
import com.afiliados.vendas.usecase.oferta.gateway.OfertaGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Implementação do port OfertaGateway. Pertence à infraestrutura: conhece JPA,
 * mas é a única classe que conhece tanto o Spring Data Repository quanto o Mapper.
 * O Use Case que a injeta enxerga apenas a interface OfertaGateway.
 */
@Component
public class OfertaGatewayImpl implements OfertaGateway {

    private final OfertaSpringDataRepository springDataRepository;

    public OfertaGatewayImpl(OfertaSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public List<Oferta> buscarAtivas() {
        return springDataRepository.findByStatus(StatusOferta.ATIVA).stream()
                .map(OfertaPersistenceMapper::paraDomain)
                .toList();
    }

    @Override
    public List<Oferta> buscarTodas() {
        return springDataRepository.findAll().stream()
                .map(OfertaPersistenceMapper::paraDomain)
                .toList();
    }

    @Override
    public Optional<Oferta> buscarPorId(Long id) {
        return springDataRepository.findById(id)
                .map(OfertaPersistenceMapper::paraDomain);
    }

    @Override
    public Oferta salvar(Oferta oferta) {
        OfertaJpaEntity jpaEntity = OfertaPersistenceMapper.paraJpaEntity(oferta);
        OfertaJpaEntity salva = springDataRepository.save(jpaEntity);
        return OfertaPersistenceMapper.paraDomain(salva);
    }

    @Override
    public void remover(Long id) {
        springDataRepository.deleteById(id);
    }
}
