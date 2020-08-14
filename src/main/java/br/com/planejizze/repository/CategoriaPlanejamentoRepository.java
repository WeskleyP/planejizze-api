package br.com.planejizze.repository;

import br.com.planejizze.model.CategoriaPlanejamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaPlanejamentoRepository extends JpaRepository<CategoriaPlanejamento, Long> {
}
