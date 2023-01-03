package projeto.rentabilidadeTipo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.rentabilidadeTipo.modelo.Rentabilidade;

import java.util.List;

public interface IRentabilidade extends JpaRepository<Rentabilidade, String> {

    Rentabilidade findByCodigoAndIdUsuario(String codigo, String idUsuario);
    List<Rentabilidade> findByDescricaoAndIdUsuario(String descricao, String idUsuario);

}
