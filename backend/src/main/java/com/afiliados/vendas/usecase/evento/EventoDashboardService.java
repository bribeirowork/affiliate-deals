package com.afiliados.vendas.usecase.evento;

import com.afiliados.vendas.domain.evento.TipoDispositivo;
import com.afiliados.vendas.domain.evento.TipoEvento;
import com.afiliados.vendas.infrastructure.persistence.entity.EventoCliqueJpaEntity;
import com.afiliados.vendas.infrastructure.persistence.repository.EventoCliqueRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EventoDashboardService {

    private final EventoCliqueRepository repository;

    public EventoDashboardService(EventoCliqueRepository repository) {
        this.repository = repository;
    }

    public EventoDashboard carregar() {
        long total         = repository.count();
        long cliquesOferta = repository.countByTipo(TipoEvento.CLIQUE_OFERTA);
        long cliquesCupom  = repository.countByTipo(TipoEvento.CLIQUE_CUPOM);
        long cliquesComprar = repository.countByTipo(TipoEvento.CLIQUE_COMPRAR);
        long mobile        = repository.countByDeviceTipo(TipoDispositivo.MOBILE);
        long desktop       = repository.countByDeviceTipo(TipoDispositivo.DESKTOP);

        List<EventoDashboard.TopOferta> topOfertas = repository.topOfertasPorCliques()
                .stream()
                .map(row -> new EventoDashboard.TopOferta(
                        (String) row[0],
                        ((Number) row[1]).longValue()))
                .toList();

        Instant seteDiasAtras = Instant.now().minus(7, ChronoUnit.DAYS);
        List<EventoDashboard.DiaClique> cliquesPorDia = repository.cliquesPorDia(seteDiasAtras)
                .stream()
                .map(row -> new EventoDashboard.DiaClique(
                        row[0].toString(),
                        ((Number) row[1]).longValue()))
                .toList();

        List<EventoCliqueJpaEntity> recentes = repository.findTop20ByOrderByCriadoEmDesc();

        return new EventoDashboard(total, cliquesOferta, cliquesCupom, cliquesComprar,
                mobile, desktop, topOfertas, cliquesPorDia, recentes);
    }
}
