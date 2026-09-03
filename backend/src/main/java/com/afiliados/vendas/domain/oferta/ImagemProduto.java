package com.afiliados.vendas.domain.oferta;

import java.util.Arrays;
import java.util.Objects;

/**
 * Value object: os bytes de uma imagem enviada por upload para uma Oferta,
 * junto com seu tipo de conteúdo. Guarda a própria regra de validade
 * (tamanho máximo, tipo MIME) — quem só tem uma URL externa não usa isto.
 */
public class ImagemProduto {

    private static final int TAMANHO_MAXIMO_BYTES = 5 * 1024 * 1024; // 5MB

    private final byte[] dados;
    private final String tipoConteudo;
    private final String nomeArquivo;

    public ImagemProduto(byte[] dados, String tipoConteudo, String nomeArquivo) {
        this.dados = dados;
        this.tipoConteudo = tipoConteudo;
        this.nomeArquivo = nomeArquivo;
        validar();
    }

    private void validar() {
        if (dados == null || dados.length == 0) {
            throw new ImagemInvalidaException("Arquivo de imagem vazio.");
        }
        if (dados.length > TAMANHO_MAXIMO_BYTES) {
            throw new ImagemInvalidaException("A imagem excede o tamanho máximo de 5MB.");
        }
        if (tipoConteudo == null || !tipoConteudo.startsWith("image/")) {
            throw new ImagemInvalidaException("Arquivo enviado não é uma imagem válida.");
        }
    }

    public byte[] getDados() { return dados; }
    public String getTipoConteudo() { return tipoConteudo; }
    public String getNomeArquivo() { return nomeArquivo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImagemProduto that)) return false;
        return Arrays.equals(dados, that.dados) && Objects.equals(tipoConteudo, that.tipoConteudo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(dados), tipoConteudo);
    }
}
