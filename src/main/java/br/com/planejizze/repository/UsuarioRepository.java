package br.com.planejizze.repository;

import br.com.planejizze.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    @Query(value = "select u.* from comprovante c " +
            " inner join usuario u on u.id = c.usuario_id " +
            " where c.uuid = ?1 " +
            " and u.active = true " +
            " and c.active = true " +
            " and jsonb_extract_path_text(c.payload, 'type') = 'account_confirmation' " +
            " and c.expire > extract(epoch from current_timestamp)",
            nativeQuery = true)
    Optional<Usuario> findUsuarioUsingConfirmationAccountToken(String token);

    Optional<Usuario> findOneByEmail(String email);

    @Query(value = "select u.* from comprovante c " +
            " inner join usuario u on u.id = c.usuario_id " +
            " where c.uuid = ?1 " +
            " and u.active = true " +
            " and c.active = true " +
            " and jsonb_extract_path_text(c.payload, 'type') = 'forget_password' " +
            " and c.expire > extract(epoch from current_timestamp)", nativeQuery = true)
    Optional<Usuario> findOneByForgetPasswordVoucher(String token);
}
