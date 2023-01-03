package projeto.rendaFixa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.rendaFixa.dao.IInvestimentoRendaFixa;
import projeto.rendaFixa.modelo.InvestimentoRendaFixaModelo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestimentoRendaFixaService {

    @Autowired
    private IInvestimentoRendaFixa iInvestimentoRendaFixa;

    private void retirarIdUsuario(InvestimentoRendaFixaModelo investimentoRendaFixaModelo) {
        investimentoRendaFixaModelo.setIdUsuario(null);
        if (investimentoRendaFixaModelo.getRentabilidade() != null) {
            investimentoRendaFixaModelo.getRentabilidade().setIdUsuario(null);
        }
        if (investimentoRendaFixaModelo.getPerfil() != null) {
            investimentoRendaFixaModelo.getPerfil().setIdUsuario(null);
        }
    }

    public InvestimentoRendaFixaModelo findById(Long id) {
        InvestimentoRendaFixaModelo investimentoRendaFixaModelo = iInvestimentoRendaFixa.findById(id).orElse(null);
        assert investimentoRendaFixaModelo != null;
        retirarIdUsuario(investimentoRendaFixaModelo);
        return investimentoRendaFixaModelo;
    }

    public List<InvestimentoRendaFixaModelo> findByIdUsuario(String idUsuario) {
        return iInvestimentoRendaFixa.findByIdUsuario(idUsuario).stream()
                .peek(this::retirarIdUsuario).collect(Collectors.toList());
    }

    public List<InvestimentoRendaFixaModelo> findByDescricao(String descricao, String idUsuario) {
        return iInvestimentoRendaFixa.findByDescricaoAndIdUsuario(descricao, idUsuario).stream()
                .peek(this::retirarIdUsuario).collect(Collectors.toList());
    }

    public List<InvestimentoRendaFixaModelo> findByListPerfil(List<String> perfilCodigo, String idUsuario) {
        return iInvestimentoRendaFixa.findByPerfilCodigoInAndIdUsuario(perfilCodigo, idUsuario).stream()
                .peek(this::retirarIdUsuario).collect(Collectors.toList());
    }

    public List<InvestimentoRendaFixaModelo> findByListRentabilidade(List<String> rentabilidadeCodigo, String idUsuario) {
        return iInvestimentoRendaFixa.findByRentabilidadeCodigoInAndIdUsuario(rentabilidadeCodigo, idUsuario).stream()
                .peek(this::retirarIdUsuario).collect(Collectors.toList());
    }

    public List<InvestimentoRendaFixaModelo> findByPrazo(String idUsuario, String prazoInicial, String prazoFinal) {
        return iInvestimentoRendaFixa.findByIdUsuario(idUsuario).stream()
                .filter(x -> Integer.parseInt(x.getPrazo()) >= Integer.parseInt(prazoInicial)
                        && Integer.parseInt(x.getPrazo()) <= Integer.parseInt(prazoFinal))
                .peek(this::retirarIdUsuario).collect(Collectors.toList());
    }

    public List<InvestimentoRendaFixaModelo> findByEmissor(String emissor, String idUsuario) {
        return iInvestimentoRendaFixa.findByEmissorAndIdUsuario(emissor, idUsuario).stream()
                .peek(this::retirarIdUsuario).collect(Collectors.toList());
    }

    public List<InvestimentoRendaFixaModelo> findByListRendaFixa(List<String> rendaFixaCodigo, String idUsuario) {
//        return iInvestimentoRendaFixa.findByRendaFixaCodigoAndIdUsuario(rendaFixaCodigo, idUsuario).stream()
//                .peek(this::retirarIdUsuario).collect(Collectors.toList());
        return null;
    }

    public InvestimentoRendaFixaModelo save(InvestimentoRendaFixaModelo investimentoRendaFixaModelo) {
        investimentoRendaFixaModelo = iInvestimentoRendaFixa.save(investimentoRendaFixaModelo);
        retirarIdUsuario(investimentoRendaFixaModelo);
        return investimentoRendaFixaModelo;
    }

    public InvestimentoRendaFixaModelo update(InvestimentoRendaFixaModelo investimentoRendaFixaModelo) {
        InvestimentoRendaFixaModelo modelo = iInvestimentoRendaFixa.findById(investimentoRendaFixaModelo.getId()).orElse(null);
        if (modelo != null) {
            modelo.setDescricao(investimentoRendaFixaModelo.getDescricao());
            modelo.setEmissor(investimentoRendaFixaModelo.getEmissor());
            if (investimentoRendaFixaModelo.getRendaFixa() != null) {
                modelo.setRendaFixa(investimentoRendaFixaModelo.getRendaFixa());
            } else if (investimentoRendaFixaModelo.getRendaFixa().getIdUsuario() != null) {
                modelo.setRendaFixa(investimentoRendaFixaModelo.getRendaFixa());
            } else {
                modelo.setRendaFixa(investimentoRendaFixaModelo.getRendaFixa());
            }
            if (investimentoRendaFixaModelo.getPerfil() != null && investimentoRendaFixaModelo.getPerfil().getIdUsuario() != null) {
                modelo.setPerfil(investimentoRendaFixaModelo.getPerfil());
            } else if (investimentoRendaFixaModelo.getPerfil() != null && investimentoRendaFixaModelo.getPerfil().getIdUsuario() == null) {
                investimentoRendaFixaModelo.getPerfil().setIdUsuario(modelo.getIdUsuario());
                modelo.setPerfil(investimentoRendaFixaModelo.getPerfil());
            } else {
                modelo.setPerfil(investimentoRendaFixaModelo.getPerfil());
            }
            if (investimentoRendaFixaModelo.getRentabilidade() != null && investimentoRendaFixaModelo.getRentabilidade().getIdUsuario() != null) {
                modelo.setRentabilidade(investimentoRendaFixaModelo.getRentabilidade());
            } else if (investimentoRendaFixaModelo.getRentabilidade() != null && investimentoRendaFixaModelo.getRentabilidade().getIdUsuario() == null) {
                investimentoRendaFixaModelo.getRentabilidade().setIdUsuario(modelo.getIdUsuario());
                modelo.setRentabilidade(investimentoRendaFixaModelo.getRentabilidade());
            } else {
                modelo.setRentabilidade(investimentoRendaFixaModelo.getRentabilidade());
            }
            modelo.setAplicacaoMinima(investimentoRendaFixaModelo.getAplicacaoMinima());
            modelo.setPrazo(investimentoRendaFixaModelo.getPrazo());
            modelo.setVencimento(investimentoRendaFixaModelo.getVencimento());
            modelo.setPrecoUnitario(investimentoRendaFixaModelo.getPrecoUnitario());
            return save(modelo);
        }
        return null;
    }
}
