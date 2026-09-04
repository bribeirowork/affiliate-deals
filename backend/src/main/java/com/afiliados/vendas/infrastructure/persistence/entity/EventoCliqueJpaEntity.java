package com.afiliados.vendas.infrastructure.persistence.entity;

import com.afiliados.vendas.domain.evento.TipoDispositivo;
import com.afiliados.vendas.domain.evento.TipoEvento;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "evento_clique", indexes = {
        @Index(name = "idx_evt_oferta_ts", columnList = "oferta_id, criado_em DESC"),
        @Index(name = "idx_evt_ts",        columnList = "criado_em DESC"),
        @Index(name = "idx_evt_device",    columnList = "device_tipo, criado_em DESC")
})
public class EventoCliqueJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oferta_id")
    private Long ofertaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoEvento tipo;

    @Column(name = "session_id", length = 64)
    private String sessionId;

    @Column(name = "lead_id", length = 100)
    private String leadId;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_tipo", length = 10)
    private TipoDispositivo deviceTipo;

    @Column(name = "os", length = 60)
    private String os;

    @Column(name = "browser", length = 60)
    private String browser;

    @Column(name = "ip_hash", length = 64)
    private String ipHash;

    @Column(name = "pais", length = 80)
    private String pais;

    @Column(name = "estado", length = 80)
    private String estado;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    protected EventoCliqueJpaEntity() {}

    public EventoCliqueJpaEntity(Long ofertaId, TipoEvento tipo, String sessionId, String leadId,
                                  TipoDispositivo deviceTipo, String os, String browser, String ipHash,
                                  String pais, String estado, String cidade, Instant criadoEm) {
        this.ofertaId  = ofertaId;
        this.tipo      = tipo;
        this.sessionId = sessionId;
        this.leadId    = leadId;
        this.deviceTipo = deviceTipo;
        this.os        = os;
        this.browser   = browser;
        this.ipHash    = ipHash;
        this.pais      = pais;
        this.estado    = estado;
        this.cidade    = cidade;
        this.criadoEm  = criadoEm;
    }

    public Long getId()               { return id; }
    public Long getOfertaId()         { return ofertaId; }
    public TipoEvento getTipo()       { return tipo; }
    public String getSessionId()      { return sessionId; }
    public String getLeadId()         { return leadId; }
    public TipoDispositivo getDeviceTipo() { return deviceTipo; }
    public String getOs()             { return os; }
    public String getBrowser()        { return browser; }
    public String getIpHash()         { return ipHash; }
    public String getPais()           { return pais; }
    public String getEstado()         { return estado; }
    public String getCidade()         { return cidade; }
    public Instant getCriadoEm()      { return criadoEm; }
}
