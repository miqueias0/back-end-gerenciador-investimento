package projeto.rendaFixa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.rendaFixa.modelo.InvestimentoRendaFixaModelo;

import java.util.List;

public interface IInvestimentoRendaFixa extends JpaRepository<InvestimentoRendaFixaModelo, Long> {

    List<InvestimentoRendaFixaModelo> findByIdUsuario(String idUsuario);
    List<InvestimentoRendaFixaModelo> findByDescricaoAndIdUsuario(String descricao, String idUsuario);
    List<InvestimentoRendaFixaModelo> findByPerfilCodigoInAndIdUsuario(List<String> perfilCodigo, String idUsuario);
    List<InvestimentoRendaFixaModelo> findByRentabilidadeCodigoInAndIdUsuario(List<String> rentabilidadeCodigo, String idUsuario);
    List<InvestimentoRendaFixaModelo> findByEmissorAndIdUsuario(String emissor, String idUsuario);
//    List<InvestimentoRendaFixaModelo> findByRendaFixaCodigoAndIdUsuario(List<String> rendaFixaCodigo, String idUsuario);

}
