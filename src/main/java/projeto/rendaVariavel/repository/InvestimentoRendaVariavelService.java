package projeto.rendaVariavel.repository;

import com.google.appengine.repackaged.com.google.common.util.concurrent.AtomicDouble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.rendaFixa.modelo.InvestimentoRendaFixaModelo;
import projeto.rendaVariavel.dao.IInvestimentoRendaVariavel;
import projeto.rendaVariavel.modelo.InvestimentoRendaVariavelModelo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestimentoRendaVariavelService {

    @Autowired
    private IInvestimentoRendaVariavel variavel;

    private void retirarIdUsuario(InvestimentoRendaVariavelModelo investimentoRendaVariavelModelo) {
        investimentoRendaVariavelModelo.setIdUsuario(null);
    }

    public InvestimentoRendaVariavelModelo findById(Long id){
        InvestimentoRendaVariavelModelo modelo = variavel.findById(id).orElse(null);
        assert modelo != null;
        retirarIdUsuario(modelo);
        return modelo;
    }

    public List<InvestimentoRendaVariavelModelo> findByPapelAndIdUsuario(String papel, String idUsuario){
        return variavel.findByPapelAndIdUsuario(papel, idUsuario).stream().peek(this::retirarIdUsuario).collect(Collectors.toList());
    }
    public List<InvestimentoRendaVariavelModelo> findByIdUsuario(String idUsuario){
        return variavel.findByIdUsuario(idUsuario).stream().peek(this::retirarIdUsuario).collect(Collectors.toList());
    }

    public List<InvestimentoRendaVariavelModelo> findByDescricaoAndIdUsuario(String descricao, String idUsuario){
        return variavel.findByDescricaoAndIdUsuario(descricao, idUsuario)
                .stream().peek(this::retirarIdUsuario).collect(Collectors.toList());
    }

    public List<InvestimentoRendaVariavelModelo> findByRendaVariavelCodigoInAndIdUsuario(List<String> rendaVariavelCodigo,
                                                                                         String idUsuario){
//        return variavel.findByRendaVariavelCodigoInAndIdUsuario(rendaVariavelCodigo, idUsuario)
//                .stream().peek(this::retirarIdUsuario).collect(Collectors.toList());
        return null;
    }

    public List<InvestimentoRendaVariavelModelo> findByPrazo(String idUsuario, Date prazoInicial, Date prazoFinal) {
        return variavel.findByIdUsuario(idUsuario).stream()
                .filter(x -> x.getDataCompra().getTime() >= prazoInicial.getTime()
                        && x.getDataCompra().getTime() <= prazoFinal.getTime())
                .peek(this::retirarIdUsuario).collect(Collectors.toList());
    }

    public InvestimentoRendaVariavelModelo save(InvestimentoRendaVariavelModelo modelo){
        modelo = variavel.save(modelo);
        retirarIdUsuario(modelo);
        return modelo;
    }

    public InvestimentoRendaVariavelModelo update(InvestimentoRendaVariavelModelo modelo){
        InvestimentoRendaVariavelModelo variavelModelo = findById(modelo.getId());
        if(variavelModelo != null){
            variavelModelo.setDataCompra(modelo.getDataCompra());
            variavelModelo.setDividendoMensal(modelo.getDividendoMensal());
            variavelModelo.setDividendoTotal(modelo.getQuantidade().multiply(modelo.getDividendoMensal()));
            variavelModelo.setDescricao(modelo.getDescricao());
            variavelModelo.setPapel(modelo.getPapel());
            variavelModelo.setPrecoUnitario(modelo.getPrecoUnitario());
            variavelModelo.setQuantidade(modelo.getQuantidade());
            variavelModelo.setValorMedio(modelo.getPrecoUnitario().multiply(modelo.getQuantidade())
                    .divide(modelo.getQuantidade()));
            variavelModelo.setValorToral(modelo.getPrecoUnitario().multiply(modelo.getQuantidade()));
            if(modelo.getRendaVariavel() != null){
                variavelModelo.setRendaVariavel(modelo.getRendaVariavel());
            }else if(modelo.getRendaVariavel() != null){
                variavelModelo.setRendaVariavel(modelo.getRendaVariavel());
            }else {
                variavelModelo.setRendaVariavel(modelo.getRendaVariavel());
            }
            return save(variavelModelo);
        }
        return null;
    }

    public BigDecimal obterTotalDividendos(String idUsuario){
        AtomicDouble valorTotal = new AtomicDouble();
        findByIdUsuario(idUsuario).stream().filter(x -> x.getMovimentacao() == null).map(x -> valorTotal.addAndGet(x.getDividendoMensal().doubleValue())).collect(Collectors.toList());
        return BigDecimal.valueOf(valorTotal.doubleValue());
    }

}
