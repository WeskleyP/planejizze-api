package br.com.planejizze.repository;

import br.com.planejizze.model.Cartao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    List<Cartao> findAllByUsuarioIdOrUsuarioIdIsNull(Long id);

    Page<Cartao> findAllByUsuarioIdOrUsuarioIdIsNull(Long id, Pageable pageable);

    Cartao findByIdAndUsuarioIdOrUsuarioIsNull(Long id, Long userId);
}
