package projeto.perfilInvestimento.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.perfilInvestimento.dao.IPerfil;
import projeto.perfilInvestimento.modelo.Perfil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilService {

    @Autowired
    private IPerfil iPerfil;

    public Perfil findByCodigo(String codigo, String idUsuario){
        Perfil perfil = iPerfil.findByCodigoAndIdUsuario(codigo, idUsuario);
        perfil.setIdUsuario(null);
        return perfil;
    }

    public List<Perfil> findByDescricao(String descricao, String idUsuario){

        return iPerfil.findByDescricaoAndIdUsuario(descricao, idUsuario).stream()
                .peek(x -> x.setIdUsuario(null)).collect(Collectors.toList());
    }

    public List<Perfil> findAll(String idUsuario){
        return iPerfil.findAll().stream().filter(x -> x.getIdUsuario().equals(idUsuario))
                .peek(x -> x.setIdUsuario(null)).collect(Collectors.toList());
    }

    public Perfil save(Perfil perfil){
        perfil = iPerfil.save(perfil);
        perfil.setIdUsuario(null);
        return perfil;
    }

    public Perfil alterar(Perfil perfil){
        Perfil entity = findByCodigo(perfil.getCodigo(), perfil.getIdUsuario());
        if (entity != null){
            entity.setCodigo(perfil.getCodigo());
            entity.setCodigo(perfil.getCodigo());
            perfil = iPerfil.save(entity);
            perfil.setIdUsuario(null);
            return perfil;
        }
        return null;
    }
}
