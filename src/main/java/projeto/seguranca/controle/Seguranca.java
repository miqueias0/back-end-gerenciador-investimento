package projeto.seguranca.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import projeto.usuario.modelo.Usuario;
import projeto.usuario.repository.UsuarioService;
import projeto.seguranca.modelo.Autenticacao;

@Service
public class Seguranca {

    @Autowired
    private UsuarioService usuarioService;

    public String criarToken(String id, Integer tempoToken) throws Exception {
        Usuario usuario = usuarioService.obterPorId(id);
        if(usuario == null){
            throw new Exception("Usuario invalido");
        }
        Autenticacao autenticacao = new Autenticacao(usuario.getId());
        if(tempoToken == null){
            return new projeto.seguranca.token.Seguranca().criarToken(autenticacao);
        }else {
            return new projeto.seguranca.token.Seguranca().criarToken(autenticacao, tempoToken);
        }

    }

    public Autenticacao validarToken(HttpHeaders sec) throws Exception {
        return new projeto.seguranca.token.Seguranca().validarToken(sec.get("authorization").get(0));
    }
}
