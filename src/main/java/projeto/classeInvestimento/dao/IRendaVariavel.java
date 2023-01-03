package projeto.classeInvestimento.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.classeInvestimento.modelo.RendaVariavel;

public interface IRendaVariavel extends JpaRepository<RendaVariavel, String> {

    RendaVariavel findByCodigoAndIdUsuario(String codigo, String idUsuario);

}
