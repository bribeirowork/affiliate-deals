package com.afiliados.vendas.domain.cupom;

import com.afiliados.vendas.domain.oferta.Loja;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade de domínio. Concentra a regra de validade de um cupom.
 * Assim como {@link com.afiliados.vendas.domain.oferta.Oferta}, nunca deve
 * transitar fora do núcleo (Use Cases) — apenas via DTOs mapeados.
 */
public class Cupom {

    private final Long id;
    private String codigo;
    private Loja loja;
    private String linkOpcional;
    private TipoDesconto tipoDesconto;
    private BigDecimal valorDesconto;
    private BigDecimal valorMinimoCompra;
    private LocalDateTime dataValidade;
    private StatusCupom status;
    private final LocalDateTime criadoEm;

    private Cupom(Long id, String codigo, Loja loja, String linkOpcional, TipoDesconto tipoDesconto,
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
        validar();
    }

    public static Cupom criar(String codigo, Loja loja, String linkOpcional, TipoDesconto tipoDesconto,
                               BigDecimal valorDesconto, BigDecimal valorMinimoCompra, LocalDateTime dataValidade) {
        return new Cupom(null, codigo, loja, linkOpcional, tipoDesconto, valorDesconto, valorMinimoCompra,
                dataValidade, StatusCupom.ATIVO, LocalDateTime.now());
    }

    public static Cupom reconstruir(Long id, String codigo, Loja loja, String linkOpcional,
                                     TipoDesconto tipoDesconto, BigDecimal valorDesconto,
                                     BigDecimal valorMinimoCompra, LocalDateTime dataValidade, StatusCupom status,
                                     LocalDateTime criadoEm) {
        return new Cupom(id, codigo, loja, linkOpcional, tipoDesconto, valorDesconto, valorMinimoCompra,
                dataValidade, status, criadoEm);
    }

    private void validar() {
        if (codigo == null || codigo.isBlank()) {
            throw new CupomInvalidoException("Código do cupom é obrigatório.");
        }
        if (loja == null) {
            throw new CupomInvalidoException("Loja é obrigatória.");
        }
        if (tipoDesconto == null) {
            throw new CupomInvalidoException("Tipo de desconto é obrigatório.");
        }
        if (valorDesconto == null || valorDesconto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CupomInvalidoException("Valor do desconto deve ser maior que zero.");
        }
        if (tipoDesconto == TipoDesconto.PERCENTUAL && valorDesconto.compareTo(new BigDecimal("100")) > 0) {
            throw new CupomInvalidoException("Desconto percentual não pode ser maior que 100%.");
        }
        if (valorMinimoCompra != null && valorMinimoCompra.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CupomInvalidoException("Valor mínimo de compra deve ser maior que zero.");
        }
        if (dataValidade == null) {
            throw new CupomInvalidoException("Data de validade é obrigatória.");
        }
    }

    /** Regra de negócio: um cupom só é exibível se estiver ativo e dentro da validade. */
    public boolean isValido(LocalDateTime referencia) {
        return status == StatusCupom.ATIVO && referencia.isBefore(dataValidade);
    }

    /**
     * Regra de negócio: cupons podem ser restritos a uma faixa de valor (ex.: "acima de R$300").
     * Sem valor mínimo definido, o cupom se aplica a qualquer compra da loja.
     */
    public boolean aplicavelPara(BigDecimal valorCompra) {
        return valorMinimoCompra == null || valorCompra.compareTo(valorMinimoCompra) >= 0;
    }

    public void atualizarDados(String codigo, Loja loja, String linkOpcional, TipoDesconto tipoDesconto,
                                BigDecimal valorDesconto, BigDecimal valorMinimoCompra, LocalDateTime dataValidade) {
        this.codigo = codigo;
        this.loja = loja;
        this.linkOpcional = linkOpcional;
        this.tipoDesconto = tipoDesconto;
        this.valorDesconto = valorDesconto;
        this.valorMinimoCompra = valorMinimoCompra;
        this.dataValidade = dataValidade;
        validar();
    }

    public void habilitar() {
        this.status = StatusCupom.ATIVO;
    }

    public void desabilitar() {
        this.status = StatusCupom.INATIVO;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cupom cupom)) return false;
        return Objects.equals(id, cupom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
