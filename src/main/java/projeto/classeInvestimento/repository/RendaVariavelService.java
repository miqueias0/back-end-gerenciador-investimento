package projeto.classeInvestimento.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.classeInvestimento.dao.IRendaVariavel;
import projeto.classeInvestimento.modelo.RendaVariavel;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RendaVariavelService {

    @Autowired
    private IRendaVariavel iRendaVariavel;

    public List<RendaVariavel> findAll(String idUsuario){
        return iRendaVariavel.findAll().stream().filter(x -> x.getIdUsuario().equals(idUsuario))
                .peek(x -> x.setIdUsuario(null)).collect(Collectors.toList());
    }

    public RendaVariavel findByCodigo(String codigo, String idUsuario){
        RendaVariavel rendaVariavel = iRendaVariavel.findByCodigoAndIdUsuario(codigo, idUsuario);
        rendaVariavel.setIdUsuario(null);
        return rendaVariavel;
    }

    public RendaVariavel save(RendaVariavel rendaVariavel){
        rendaVariavel = iRendaVariavel.save(rendaVariavel);
        rendaVariavel.setIdUsuario(null);
        return rendaVariavel;
    }

    public RendaVariavel update(RendaVariavel rendaVariavel){
        RendaVariavel variavel = findByCodigo(rendaVariavel.getCodigo(), rendaVariavel.getIdUsuario());
        if(variavel != null){
            variavel.setCodigo(rendaVariavel.getCodigo());
            variavel.setDescricao(rendaVariavel.getDescricao());
            return save(rendaVariavel);
        }
        return null;
    }
}
