package br.com.planejizze.repository;

import br.com.planejizze.model.Despesa;
import br.com.planejizze.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
