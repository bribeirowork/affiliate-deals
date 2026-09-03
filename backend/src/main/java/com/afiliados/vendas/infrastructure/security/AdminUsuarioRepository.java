package com.afiliados.vendas.infrastructure.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUsuarioRepository extends JpaRepository<AdminUsuario, Long> {
    Optional<AdminUsuario> findByUsername(String username);
}
