package br.com.planejizze.repository;

import br.com.planejizze.model.CategoriaReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaReceitaRepository extends JpaRepository<CategoriaReceita, Long> {
}
