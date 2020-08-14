package br.com.planejizze.repository;

import br.com.planejizze.model.Planejamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanejamentoRepository extends JpaRepository<Planejamento, Long> {
}
