package com.afiliados.vendas.domain.oferta;

public class ImagemInvalidaException extends RuntimeException {
    public ImagemInvalidaException(String mensagem) {
        super(mensagem);
    }
}
