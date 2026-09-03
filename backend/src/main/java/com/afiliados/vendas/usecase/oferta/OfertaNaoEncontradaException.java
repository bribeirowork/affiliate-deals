package com.afiliados.vendas.usecase.oferta;

/** Falha de orquestração (não é regra de domínio): id inexistente no Gateway. */
public class OfertaNaoEncontradaException extends RuntimeException {
    public OfertaNaoEncontradaException(Long id) {
        super("Oferta não encontrada: " + id);
    }
}
