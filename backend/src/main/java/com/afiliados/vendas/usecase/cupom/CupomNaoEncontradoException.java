package com.afiliados.vendas.usecase.cupom;

public class CupomNaoEncontradoException extends RuntimeException {
    public CupomNaoEncontradoException(Long id) {
        super("Cupom não encontrado: " + id);
    }
}
