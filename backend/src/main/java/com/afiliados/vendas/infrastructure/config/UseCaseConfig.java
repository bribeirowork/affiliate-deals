package com.afiliados.vendas.infrastructure.config;

import com.afiliados.vendas.usecase.cupom.*;
import com.afiliados.vendas.usecase.cupom.gateway.CupomGateway;
import com.afiliados.vendas.usecase.oferta.*;
import com.afiliados.vendas.usecase.oferta.gateway.OfertaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Os Use Cases são POJOs de propósito único, sem dependência de Spring —
 * esta classe é o único ponto que os conecta ao container (injetando os
 * Gateways, que são as portas de saída implementadas pela infraestrutura).
 */
@Configuration
public class UseCaseConfig {

    // ---- Oferta ----

    @Bean
    public ListarOfertasUseCase listarOfertasUseCase(OfertaGateway ofertaGateway, CupomGateway cupomGateway) {
        return new ListarOfertasUseCase(ofertaGateway, cupomGateway);
    }

    @Bean
    public ListarOfertasAdminUseCase listarOfertasAdminUseCase(OfertaGateway ofertaGateway) {
        return new ListarOfertasAdminUseCase(ofertaGateway);
    }

    @Bean
    public BuscarOfertaPorIdUseCase buscarOfertaPorIdUseCase(OfertaGateway ofertaGateway) {
        return new BuscarOfertaPorIdUseCase(ofertaGateway);
    }

    @Bean
    public CriarOfertaUseCase criarOfertaUseCase(OfertaGateway ofertaGateway) {
        return new CriarOfertaUseCase(ofertaGateway);
    }

    @Bean
    public AtualizarOfertaUseCase atualizarOfertaUseCase(OfertaGateway ofertaGateway) {
        return new AtualizarOfertaUseCase(ofertaGateway);
    }

    @Bean
    public RemoverOfertaUseCase removerOfertaUseCase(OfertaGateway ofertaGateway) {
        return new RemoverOfertaUseCase(ofertaGateway);
    }

    @Bean
    public AlternarStatusOfertaUseCase alternarStatusOfertaUseCase(OfertaGateway ofertaGateway) {
        return new AlternarStatusOfertaUseCase(ofertaGateway);
    }

    @Bean
    public BuscarImagemOfertaUseCase buscarImagemOfertaUseCase(OfertaGateway ofertaGateway) {
        return new BuscarImagemOfertaUseCase(ofertaGateway);
    }

    // ---- Cupom ----

    @Bean
    public ListarCuponsUseCase listarCuponsUseCase(CupomGateway cupomGateway) {
        return new ListarCuponsUseCase(cupomGateway);
    }

    @Bean
    public ListarCuponsAdminUseCase listarCuponsAdminUseCase(CupomGateway cupomGateway) {
        return new ListarCuponsAdminUseCase(cupomGateway);
    }

    @Bean
    public BuscarCupomPorIdUseCase buscarCupomPorIdUseCase(CupomGateway cupomGateway) {
        return new BuscarCupomPorIdUseCase(cupomGateway);
    }

    @Bean
    public CriarCupomUseCase criarCupomUseCase(CupomGateway cupomGateway) {
        return new CriarCupomUseCase(cupomGateway);
    }

    @Bean
    public AtualizarCupomUseCase atualizarCupomUseCase(CupomGateway cupomGateway) {
        return new AtualizarCupomUseCase(cupomGateway);
    }

    @Bean
    public RemoverCupomUseCase removerCupomUseCase(CupomGateway cupomGateway) {
        return new RemoverCupomUseCase(cupomGateway);
    }

    @Bean
    public AlternarStatusCupomUseCase alternarStatusCupomUseCase(CupomGateway cupomGateway) {
        return new AlternarStatusCupomUseCase(cupomGateway);
    }
}
