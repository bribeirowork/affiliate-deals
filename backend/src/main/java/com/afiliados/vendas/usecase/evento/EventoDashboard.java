package com.afiliados.vendas.usecase.evento;

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
        List<EventoRecenteVM> recentes,
        String filtroDe,
        String filtroAte
) {
    public record TopOferta(String nomeProduto, long total) {}
    public record DiaClique(String data, long total) {}

    /** Dados formatados para a view — não expõe a entidade JPA ao template. */
    public record EventoRecenteVM(
            String tipo,
            String deviceTipo,
            String os,
            String browser,
            String pais,
            String cidade,
            String criadoEm  // já formatado: "dd/MM/yyyy HH:mm:ss"
    ) {}

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

    public boolean filtroAtivo() {
        return (filtroDe != null && !filtroDe.isBlank())
                || (filtroAte != null && !filtroAte.isBlank());
    }
}
