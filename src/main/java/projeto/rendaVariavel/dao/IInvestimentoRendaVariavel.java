package projeto.rendaVariavel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.rendaVariavel.modelo.InvestimentoRendaVariavelModelo;

import java.util.List;

public interface IInvestimentoRendaVariavel extends JpaRepository<InvestimentoRendaVariavelModelo, Long> {

    List<InvestimentoRendaVariavelModelo> findByIdUsuario(String idUsuario);
    List<InvestimentoRendaVariavelModelo> findByPapelAndIdUsuario(String papel, String idUsuario);
    List<InvestimentoRendaVariavelModelo> findByDescricaoAndIdUsuario(String descricao, String idUsuario);
//    List<InvestimentoRendaVariavelModelo> findByRendaVariavelCodigoInAndIdUsuario(List<String> rendaVariavelCodigo, String idUsuario);

}
