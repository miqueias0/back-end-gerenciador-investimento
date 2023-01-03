package projeto.classeInvestimento.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.classeInvestimento.modelo.RendaFixa;

public interface IRendaFixa extends JpaRepository<RendaFixa, String> {

    RendaFixa findByCodigoAndIdUsuario(String codigo, String idUsuario);

}
