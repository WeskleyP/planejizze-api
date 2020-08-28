package br.com.planejizze.repository;

import br.com.planejizze.model.Banco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {

    List<Banco> findAllByUsuarioIdOrUsuarioIdIsNull(Long id);

    Page<Banco> findAllByUsuarioIdOrUsuarioIdIsNull(Long id, Pageable pageable);

    Banco findByIdAndUsuarioIdOrUsuarioIsNull(Long id, Long userId);
}
