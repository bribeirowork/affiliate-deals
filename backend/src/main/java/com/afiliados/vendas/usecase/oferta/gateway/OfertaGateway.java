package com.afiliados.vendas.usecase.oferta.gateway;

import com.afiliados.vendas.domain.oferta.Oferta;

import java.util.List;
import java.util.Optional;

/**
 * Porta de saída (definida pelo núcleo, implementada pela infraestrutura).
 * O Use Case depende apenas desta abstração — nunca de JPA, Hibernate, etc.
 */
public interface OfertaGateway {

    List<Oferta> buscarAtivas();

    List<Oferta> buscarTodas();

    Optional<Oferta> buscarPorId(Long id);

    Oferta salvar(Oferta oferta);

    void remover(Long id);
}
