package br.com.planejizze.service;

import br.com.planejizze.exceptions.EmailNotFoundException;
import br.com.planejizze.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository users;

    @Autowired
    public CustomUserDetailsService(UsuarioRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {
        return this.users.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("Email: " + email + " not found"));
    }
}

