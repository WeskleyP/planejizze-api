package br.com.planejizze.repository;

import br.com.planejizze.model.Comprovante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprovanteRepository extends JpaRepository<Comprovante, String> {
}
