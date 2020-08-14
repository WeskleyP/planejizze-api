package br.com.planejizze.repository;

import br.com.planejizze.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Despesa, Long> {
}
