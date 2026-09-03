package com.afiliados.vendas.domain.oferta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade de domínio. Concentra toda a regra de negócio de uma oferta,
 * incluindo a classificação temporal (do dia / da semana / expirada).
 * Nunca deve ser exposta fora do núcleo (Use Cases) — Resource layers só
 * enxergam DTOs produzidos pelos Mappers.
 */
public class Oferta {

    private final Long id;
    private String nomeProduto;
    private String imagemUrl;
    private ImagemProduto imagem;
    private Loja loja;
    private BigDecimal valor;
    private BigDecimal valorOriginal;
    private String linkAfiliado;
    private LocalDateTime dataExpiracaoDia;
    private LocalDateTime dataExpiracaoSemana;
    private StatusOferta status;
    private final LocalDateTime criadoEm;

    private Oferta(Long id, String nomeProduto, String imagemUrl, ImagemProduto imagem, Loja loja, BigDecimal valor,
                    BigDecimal valorOriginal, String linkAfiliado, LocalDateTime dataExpiracaoDia,
                    LocalDateTime dataExpiracaoSemana, StatusOferta status, LocalDateTime criadoEm) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.imagemUrl = imagemUrl;
        this.imagem = imagem;
        this.loja = loja;
        this.valor = valor;
        this.valorOriginal = valorOriginal;
        this.linkAfiliado = linkAfiliado;
        this.dataExpiracaoDia = dataExpiracaoDia;
        this.dataExpiracaoSemana = dataExpiracaoSemana;
        this.status = status;
        this.criadoEm = criadoEm;
        validar();
    }

    /** Factory para criação de uma nova oferta (ainda sem identidade persistida). */
    public static Oferta criar(String nomeProduto, String imagemUrl, ImagemProduto imagem, Loja loja,
                                BigDecimal valor, BigDecimal valorOriginal, String linkAfiliado,
                                LocalDateTime dataExpiracaoDia, LocalDateTime dataExpiracaoSemana) {
        return new Oferta(null, nomeProduto, imagemUrl, imagem, loja, valor, valorOriginal, linkAfiliado,
                dataExpiracaoDia, dataExpiracaoSemana, StatusOferta.ATIVA, LocalDateTime.now());
    }

    /** Factory usada pela camada de persistência para reconstruir a entidade a partir do banco. */
    public static Oferta reconstruir(Long id, String nomeProduto, String imagemUrl, ImagemProduto imagem, Loja loja,
                                      BigDecimal valor, BigDecimal valorOriginal, String linkAfiliado,
                                      LocalDateTime dataExpiracaoDia, LocalDateTime dataExpiracaoSemana,
                                      StatusOferta status, LocalDateTime criadoEm) {
        return new Oferta(id, nomeProduto, imagemUrl, imagem, loja, valor, valorOriginal, linkAfiliado,
                dataExpiracaoDia, dataExpiracaoSemana, status, criadoEm);
    }

    private void validar() {
        if (nomeProduto == null || nomeProduto.isBlank()) {
            throw new OfertaInvalidaException("Nome do produto é obrigatório.");
        }
        if (loja == null) {
            throw new OfertaInvalidaException("Loja é obrigatória.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OfertaInvalidaException("Valor deve ser maior que zero.");
        }
        if (valorOriginal != null && valorOriginal.compareTo(valor) <= 0) {
            throw new OfertaInvalidaException("Preço original deve ser maior que o valor atual.");
        }
        if (linkAfiliado == null || linkAfiliado.isBlank()) {
            throw new OfertaInvalidaException("Link de afiliado é obrigatório.");
        }
        if (dataExpiracaoDia == null || dataExpiracaoSemana == null) {
            throw new OfertaInvalidaException("Datas de expiração são obrigatórias.");
        }
        if (dataExpiracaoSemana.isBefore(dataExpiracaoDia)) {
            throw new OfertaInvalidaException("A expiração semanal não pode ser anterior à expiração diária.");
        }
    }

    /**
     * Regra de negócio central: separa "oferta do dia" de "oferta da semana".
     * Enquanto a referência de tempo não passar de {@code dataExpiracaoDia}, a oferta
     * pertence ao dia. Depois disso, e até {@code dataExpiracaoSemana}, ela "cai"
     * automaticamente para a seção da semana. Após isso, está expirada.
     */
    public SecaoOferta classificarSecao(LocalDateTime referencia) {
        if (status == StatusOferta.INATIVA) {
            return SecaoOferta.EXPIRADA;
        }
        if (referencia.isBefore(dataExpiracaoDia)) {
            return SecaoOferta.DO_DIA;
        }
        if (referencia.isBefore(dataExpiracaoSemana)) {
            return SecaoOferta.DA_SEMANA;
        }
        return SecaoOferta.EXPIRADA;
    }

    public void atualizarDados(String nomeProduto, String imagemUrl, ImagemProduto imagem, Loja loja,
                                BigDecimal valor, BigDecimal valorOriginal, String linkAfiliado,
                                LocalDateTime dataExpiracaoDia, LocalDateTime dataExpiracaoSemana) {
        this.nomeProduto = nomeProduto;
        this.imagemUrl = imagemUrl;
        this.imagem = imagem;
        this.loja = loja;
        this.valor = valor;
        this.valorOriginal = valorOriginal;
        this.linkAfiliado = linkAfiliado;
        this.dataExpiracaoDia = dataExpiracaoDia;
        this.dataExpiracaoSemana = dataExpiracaoSemana;
        validar();
    }

    /** true quando a oferta tem uma imagem própria enviada por upload (em vez de uma URL externa). */
    public boolean possuiImagemPropria() {
        return imagem != null;
    }

    /** true quando há um preço original registrado (ou seja, esta é uma oferta promocional). */
    public boolean possuiDesconto() {
        return valorOriginal != null;
    }

    public void habilitar() {
        this.status = StatusOferta.ATIVA;
    }

    public void desabilitar() {
        this.status = StatusOferta.INATIVA;
    }

    public Long getId() { return id; }
    public String getNomeProduto() { return nomeProduto; }
    public String getImagemUrl() { return imagemUrl; }
    public ImagemProduto getImagem() { return imagem; }
    public Loja getLoja() { return loja; }
    public BigDecimal getValor() { return valor; }
    public BigDecimal getValorOriginal() { return valorOriginal; }
    public String getLinkAfiliado() { return linkAfiliado; }
    public LocalDateTime getDataExpiracaoDia() { return dataExpiracaoDia; }
    public LocalDateTime getDataExpiracaoSemana() { return dataExpiracaoSemana; }
    public StatusOferta getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Oferta oferta)) return false;
        return Objects.equals(id, oferta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
