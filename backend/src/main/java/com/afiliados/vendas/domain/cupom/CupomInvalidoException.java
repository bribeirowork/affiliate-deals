package com.afiliados.vendas.domain.cupom;

public class CupomInvalidoException extends RuntimeException {
    public CupomInvalidoException(String mensagem) {
        super(mensagem);
    }
}
