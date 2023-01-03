package projeto.perfilInvestimento.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.perfilInvestimento.modelo.Perfil;

import java.util.List;

public interface IPerfil extends JpaRepository<Perfil, String> {

    Perfil findByCodigoAndIdUsuario(String codigo, String idUsuario);
    List<Perfil> findByDescricaoAndIdUsuario(String descricao, String idUsuario);

}
