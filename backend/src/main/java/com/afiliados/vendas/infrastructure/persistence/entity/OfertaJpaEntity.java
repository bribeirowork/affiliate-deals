package com.afiliados.vendas.infrastructure.persistence.entity;

import com.afiliados.vendas.domain.oferta.Loja;
import com.afiliados.vendas.domain.oferta.StatusOferta;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Modelo de persistência (representação relacional). Não é a entidade de
 * domínio — a conversão entre os dois mundos é feita pelo OfertaPersistenceMapper.
 */
@Entity
@Table(name = "ofertas")
public class OfertaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeProduto;

    @Column(length = 1000)
    private String imagemUrl;

    @Lob
    @Column(name = "imagem_dados")
    private byte[] imagemDados;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @Column(name = "imagem_nome_arquivo")
    private String imagemNomeArquivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Loja loja;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(name = "valor_original", precision = 12, scale = 2)
    private BigDecimal valorOriginal;

    @Column(nullable = false, length = 2000)
    private String linkAfiliado;

    @Column(nullable = false)
    private LocalDateTime dataExpiracaoDia;

    @Column(nullable = false)
    private LocalDateTime dataExpiracaoSemana;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOferta status;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    protected OfertaJpaEntity() {
        // exigido pelo JPA
    }

    public OfertaJpaEntity(Long id, String nomeProduto, String imagemUrl, byte[] imagemDados,
                            String imagemContentType, String imagemNomeArquivo, Loja loja, BigDecimal valor,
                            BigDecimal valorOriginal, String linkAfiliado, LocalDateTime dataExpiracaoDia,
                            LocalDateTime dataExpiracaoSemana, StatusOferta status, LocalDateTime criadoEm) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.imagemUrl = imagemUrl;
        this.imagemDados = imagemDados;
        this.imagemContentType = imagemContentType;
        this.imagemNomeArquivo = imagemNomeArquivo;
        this.loja = loja;
        this.valor = valor;
        this.valorOriginal = valorOriginal;
        this.linkAfiliado = linkAfiliado;
        this.dataExpiracaoDia = dataExpiracaoDia;
        this.dataExpiracaoSemana = dataExpiracaoSemana;
        this.status = status;
        this.criadoEm = criadoEm;
    }

    public Long getId() { return id; }
    public String getNomeProduto() { return nomeProduto; }
    public String getImagemUrl() { return imagemUrl; }
    public byte[] getImagemDados() { return imagemDados; }
    public String getImagemContentType() { return imagemContentType; }
    public String getImagemNomeArquivo() { return imagemNomeArquivo; }
    public Loja getLoja() { return loja; }
    public BigDecimal getValor() { return valor; }
    public BigDecimal getValorOriginal() { return valorOriginal; }
    public String getLinkAfiliado() { return linkAfiliado; }
    public LocalDateTime getDataExpiracaoDia() { return dataExpiracaoDia; }
    public LocalDateTime getDataExpiracaoSemana() { return dataExpiracaoSemana; }
    public StatusOferta getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
