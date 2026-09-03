package com.afiliados.vendas.infrastructure.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/** Ponte entre o Spring Security e a base de usuários do backoffice (JPA). */
@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminUsuarioRepository adminUsuarioRepository;

    public AdminUserDetailsService(AdminUsuarioRepository adminUsuarioRepository) {
        this.adminUsuarioRepository = adminUsuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUsuario usuario = adminUsuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getSenhaHash())
                .disabled(!usuario.isHabilitado())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .build();
    }
}
