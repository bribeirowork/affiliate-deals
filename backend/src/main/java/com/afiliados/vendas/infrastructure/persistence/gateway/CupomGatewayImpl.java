package com.afiliados.vendas.infrastructure.persistence.gateway;

import com.afiliados.vendas.domain.cupom.Cupom;
import com.afiliados.vendas.domain.cupom.StatusCupom;
import com.afiliados.vendas.infrastructure.persistence.entity.CupomJpaEntity;
import com.afiliados.vendas.infrastructure.persistence.mapper.CupomPersistenceMapper;
import com.afiliados.vendas.infrastructure.persistence.repository.CupomSpringDataRepository;
import com.afiliados.vendas.usecase.cupom.gateway.CupomGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CupomGatewayImpl implements CupomGateway {

    private final CupomSpringDataRepository springDataRepository;

    public CupomGatewayImpl(CupomSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public List<Cupom> buscarAtivos() {
        return springDataRepository.findByStatus(StatusCupom.ATIVO).stream()
                .map(CupomPersistenceMapper::paraDomain)
                .toList();
    }

    @Override
    public List<Cupom> buscarTodos() {
        return springDataRepository.findAll().stream()
                .map(CupomPersistenceMapper::paraDomain)
                .toList();
    }

    @Override
    public Optional<Cupom> buscarPorId(Long id) {
        return springDataRepository.findById(id)
                .map(CupomPersistenceMapper::paraDomain);
    }

    @Override
    public Cupom salvar(Cupom cupom) {
        CupomJpaEntity jpaEntity = CupomPersistenceMapper.paraJpaEntity(cupom);
        CupomJpaEntity salvo = springDataRepository.save(jpaEntity);
        return CupomPersistenceMapper.paraDomain(salvo);
    }

    @Override
    public void remover(Long id) {
        springDataRepository.deleteById(id);
    }
}
