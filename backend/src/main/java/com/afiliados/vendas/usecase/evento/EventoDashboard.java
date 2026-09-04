package com.afiliados.vendas.usecase.evento;

import com.afiliados.vendas.infrastructure.persistence.entity.EventoCliqueJpaEntity;

import java.util.List;

public record EventoDashboard(
        long total,
        long cliquesOferta,
        long cliquesCupom,
        long cliquesComprar,
        long mobile,
        long desktop,
        List<TopOferta> topOfertas,
        List<DiaClique> cliquesPorDia,
        List<EventoCliqueJpaEntity> recentes
) {
    public record TopOferta(String nomeProduto, long total) {}
    public record DiaClique(String data, long total) {}

    public int mobilePercent() {
        long base = mobile + desktop;
        return base == 0 ? 0 : (int) Math.round(mobile * 100.0 / base);
    }

    public int desktopPercent() {
        long base = mobile + desktop;
        return base == 0 ? 0 : (int) Math.round(desktop * 100.0 / base);
    }

    public long maxDiaTotal() {
        return cliquesPorDia.stream().mapToLong(DiaClique::total).max().orElse(1);
    }

    public long maxOfertaTotal() {
        return topOfertas.stream().mapToLong(TopOferta::total).max().orElse(1);
    }
}
