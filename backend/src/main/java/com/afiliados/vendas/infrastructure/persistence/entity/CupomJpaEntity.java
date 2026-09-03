package com.afiliados.vendas.infrastructure.persistence.entity;

import com.afiliados.vendas.domain.cupom.StatusCupom;
import com.afiliados.vendas.domain.cupom.TipoDesconto;
import com.afiliados.vendas.domain.oferta.Loja;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cupons")
public class CupomJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Loja loja;

    @Column(length = 2000)
    private String linkOpcional;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDesconto tipoDesconto;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorDesconto;

    @Column(name = "valor_minimo_compra", precision = 12, scale = 2)
    private BigDecimal valorMinimoCompra;

    @Column(nullable = false)
    private LocalDateTime dataValidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCupom status;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    protected CupomJpaEntity() {
    }

    public CupomJpaEntity(Long id, String codigo, Loja loja, String linkOpcional, TipoDesconto tipoDesconto,
                           BigDecimal valorDesconto, BigDecimal valorMinimoCompra, LocalDateTime dataValidade,
                           StatusCupom status, LocalDateTime criadoEm) {
        this.id = id;
        this.codigo = codigo;
        this.loja = loja;
        this.linkOpcional = linkOpcional;
        this.tipoDesconto = tipoDesconto;
        this.valorDesconto = valorDesconto;
        this.valorMinimoCompra = valorMinimoCompra;
        this.dataValidade = dataValidade;
        this.status = status;
        this.criadoEm = criadoEm;
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public Loja getLoja() { return loja; }
    public String getLinkOpcional() { return linkOpcional; }
    public TipoDesconto getTipoDesconto() { return tipoDesconto; }
    public BigDecimal getValorDesconto() { return valorDesconto; }
    public BigDecimal getValorMinimoCompra() { return valorMinimoCompra; }
    public LocalDateTime getDataValidade() { return dataValidade; }
    public StatusCupom getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
