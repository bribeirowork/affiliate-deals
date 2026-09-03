package com.afiliados.vendas.infrastructure.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Cria um usuário admin padrão em ambiente de demonstração (H2 em memória).
 * Em produção, troque por um processo de provisionamento controlado e remova este seeder.
 */
@Component
public class AdminUsuarioSeeder implements CommandLineRunner {

    private final AdminUsuarioRepository adminUsuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUsuarioSeeder(AdminUsuarioRepository adminUsuarioRepository, PasswordEncoder passwordEncoder) {
        this.adminUsuarioRepository = adminUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (adminUsuarioRepository.findByUsername("admin").isEmpty()) {
            adminUsuarioRepository.save(new AdminUsuario("admin", passwordEncoder.encode("admin123")));
        }
    }
}
