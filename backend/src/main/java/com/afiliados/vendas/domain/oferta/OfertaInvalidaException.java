package com.afiliados.vendas.domain.oferta;

public class OfertaInvalidaException extends RuntimeException {
    public OfertaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
