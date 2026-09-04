package com.afiliados.vendas.usecase.evento;

import com.afiliados.vendas.domain.evento.TipoDispositivo;
import com.afiliados.vendas.domain.evento.TipoEvento;
import com.afiliados.vendas.infrastructure.persistence.entity.EventoCliqueJpaEntity;
import com.afiliados.vendas.infrastructure.persistence.repository.EventoCliqueRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EventoDashboardService {

    private static final ZoneId TZ_BR = ZoneId.of("America/Sao_Paulo");
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(TZ_BR);

    private final EventoCliqueRepository repository;

    public EventoDashboardService(EventoCliqueRepository repository) {
        this.repository = repository;
    }

    public EventoDashboard carregar(Instant de, Instant ate, String filtroDe, String filtroAte) {
        long total          = repository.countByCriadoEmBetween(de, ate);
        long cliquesOferta  = repository.countByTipoAndCriadoEmBetween(TipoEvento.CLIQUE_OFERTA, de, ate);
        long cliquesCupom   = repository.countByTipoAndCriadoEmBetween(TipoEvento.CLIQUE_CUPOM, de, ate);
        long cliquesComprar = repository.countByTipoAndCriadoEmBetween(TipoEvento.CLIQUE_COMPRAR, de, ate);
        long mobile         = repository.countByDeviceTipoAndCriadoEmBetween(TipoDispositivo.MOBILE, de, ate);
        long desktop        = repository.countByDeviceTipoAndCriadoEmBetween(TipoDispositivo.DESKTOP, de, ate);

        List<EventoDashboard.TopOferta> topOfertas = repository.topOfertasPorCliques(de, ate)
                .stream()
                .map(row -> new EventoDashboard.TopOferta(
                        (String) row[0],
                        ((Number) row[1]).longValue()))
                .toList();

        List<EventoDashboard.DiaClique> cliquesPorDia = repository.cliquesPorDia(de, ate)
                .stream()
                .map(row -> new EventoDashboard.DiaClique(
                        row[0].toString(),
                        ((Number) row[1]).longValue()))
                .toList();

        List<EventoDashboard.EventoRecenteVM> recentes =
                repository.findTop20ByCriadoEmBetweenOrderByCriadoEmDesc(de, ate)
                        .stream()
                        .map(this::toVM)
                        .toList();

        return new EventoDashboard(total, cliquesOferta, cliquesCupom, cliquesComprar,
                mobile, desktop, topOfertas, cliquesPorDia, recentes, filtroDe, filtroAte);
    }

    private EventoDashboard.EventoRecenteVM toVM(EventoCliqueJpaEntity e) {
        return new EventoDashboard.EventoRecenteVM(
                e.getTipo() != null ? e.getTipo().name() : null,
                e.getDeviceTipo() != null ? e.getDeviceTipo().name() : null,
                e.getOs(),
                e.getBrowser(),
                e.getPais(),
                e.getCidade(),
                e.getCriadoEm() != null ? FMT.format(e.getCriadoEm()) : null
        );
    }
}
