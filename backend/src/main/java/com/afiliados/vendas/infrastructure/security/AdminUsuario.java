package com.afiliados.vendas.infrastructure.security;

import jakarta.persistence.*;

/**
 * Usuário do backoffice. Fica em infrastructure.security por ser um
 * detalhe de autenticação (Spring Security), não uma entidade de domínio
 * de negócio do sistema de vendas.
 */
@Entity
@Table(name = "admin_usuarios")
public class AdminUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String senhaHash;

    @Column(nullable = false)
    private boolean habilitado = true;

    protected AdminUsuario() {
    }

    public AdminUsuario(String username, String senhaHash) {
        this.username = username;
        this.senhaHash = senhaHash;
        this.habilitado = true;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getSenhaHash() { return senhaHash; }
    public boolean isHabilitado() { return habilitado; }
}
