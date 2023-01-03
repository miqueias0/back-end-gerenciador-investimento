package projeto.usuario.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projeto.usuario.modelo.Usuario;
import projeto.usuario.dao.IUsuario;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@Service
public class UsuarioService {
    @Autowired(required = true)
    private IUsuario iUsuario;

    public Usuario manter(@NotNull Usuario usuario) {
        usuario.setId(UUID.randomUUID().toString());
        usuario.setEmail(usuario.getEmail().toLowerCase());
        iUsuario.save(usuario);
        return usuario;
    }

    public Usuario update(@NotNull Usuario usuario) throws Exception {
        Usuario entity = iUsuario.findById(usuario.getId()).orElse(null);
        if(entity != null){
            entity.setEmail(usuario.getEmail());
            entity.setNomeUsuario(usuario.getNomeUsuario());
            entity.setTelefone(usuario.getTelefone());
            if(usuario.getSenha().equals(entity.getSenha())){
                throw new Exception("Senha não pode ser igual a anterior");
            }
            entity.setSenha(usuario.getSenha());
            iUsuario.save(entity);
            return entity;
        }
        return null;
    }

    public Usuario obterPorId(String id){
        return iUsuario.findById(id).orElse(null);
    }

    public Usuario obterPorNomeUsuario(String nomeUsuario){
        return iUsuario.findByNomeUsuario(nomeUsuario);
    }

    public Usuario obterPorEmail(String email){
        return iUsuario.findByEmail(email);
    }

    public Usuario obterPorTelefone(String telefone){
        return iUsuario.findByTelefone(telefone);
    }

    public Usuario login(@NotNull Usuario usuario){
        Usuario logado;
        logado = iUsuario.findByNomeUsuarioOrEmailOrTelefone(usuario.getNomeUsuario(), usuario.getNomeUsuario(), usuario.getNomeUsuario());
        return usuario.getSenha().equals(logado.getSenha())? logado: null;
    }

}
