package projeto.rentabilidadeTipo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.rentabilidadeTipo.dao.IRentabilidade;
import projeto.rentabilidadeTipo.modelo.Rentabilidade;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentabilidadeService {

    @Autowired
    private IRentabilidade iRentabilidade;

    public Rentabilidade findByCodigo(String codigo, String idUsuario){
        Rentabilidade rentabilidade = iRentabilidade.findByCodigoAndIdUsuario(codigo, idUsuario);
        rentabilidade.setIdUsuario(null);
        return rentabilidade;
    }

    public List<Rentabilidade> findByDescricao(String descricao, String idUsuario){
        return iRentabilidade.findByDescricaoAndIdUsuario(descricao, idUsuario).stream()
                .peek(x -> x.setIdUsuario(null)).collect(Collectors.toList());
    }

    public List<Rentabilidade> findAll(String idUsuario){
        return iRentabilidade.findAll().stream().filter(x -> x.getIdUsuario().equals(idUsuario))
                .peek(x -> x.setIdUsuario(null)).collect(Collectors.toList());
    }

    public Rentabilidade save(Rentabilidade rentabilidade){
        rentabilidade = iRentabilidade.save(rentabilidade);
        rentabilidade.setIdUsuario(null);
        return rentabilidade;
    }

    public Rentabilidade alterar(Rentabilidade rentabilidade){
        Rentabilidade entity = findByCodigo(rentabilidade.getCodigo(), rentabilidade.getIdUsuario());
        if (entity != null){
            entity.setCodigo(rentabilidade.getCodigo());
            entity.setCodigo(rentabilidade.getCodigo());
            return save(entity);
        }
        return null;
    }
}
