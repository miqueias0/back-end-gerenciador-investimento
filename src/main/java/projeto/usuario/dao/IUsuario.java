package projeto.usuario.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.usuario.modelo.Usuario;

import java.util.Optional;

public interface IUsuario extends JpaRepository<Usuario, String> {
    Optional<Usuario> findById(String id);
    Usuario findByNomeUsuario(String nomeUsuario);
    Usuario findByNomeUsuarioOrEmailOrTelefone(String nomeUsuario, String email, String telefone);
    Usuario findByEmail(String email);
    Usuario findByTelefone(String telefone);

}
