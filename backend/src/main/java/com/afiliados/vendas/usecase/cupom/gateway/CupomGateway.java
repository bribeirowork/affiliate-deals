package com.afiliados.vendas.usecase.cupom.gateway;

import com.afiliados.vendas.domain.cupom.Cupom;

import java.util.List;
import java.util.Optional;

public interface CupomGateway {

    List<Cupom> buscarAtivos();

    List<Cupom> buscarTodos();

    Optional<Cupom> buscarPorId(Long id);

    Cupom salvar(Cupom cupom);

    void remover(Long id);
}
