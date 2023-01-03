package projeto.classeInvestimento.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import projeto.classeInvestimento.dao.IRendaFixa;
import projeto.classeInvestimento.modelo.RendaFixa;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RendaFixaService {

    @Autowired
    private IRendaFixa iRendaFixa;

    public List<RendaFixa> findAll(String idUsuario){
        return iRendaFixa.findAll().stream().filter(x -> x.getIdUsuario().equals(idUsuario))
                .peek(x -> x.setIdUsuario(null)).collect(Collectors.toList());
    }

    public RendaFixa findByCodigo(String codigo, String idUsuario){
        RendaFixa rendaFixa = iRendaFixa.findByCodigoAndIdUsuario(codigo, idUsuario);
        rendaFixa.setIdUsuario(null);
        return rendaFixa;
    }

    public RendaFixa save(RendaFixa rendaFixa){
        rendaFixa = iRendaFixa.save(rendaFixa);
        rendaFixa.setIdUsuario(null);
        return rendaFixa;
    }

    public RendaFixa update(RendaFixa rendaFixa){
        RendaFixa fixa = findByCodigo(rendaFixa.getCodigo(), rendaFixa.getIdUsuario());
        if (fixa != null){
            fixa.setCodigo(rendaFixa.getCodigo());
            fixa.setDescricao(rendaFixa.getDescricao());
            return save(fixa);
        }
        return null;
    }
}
