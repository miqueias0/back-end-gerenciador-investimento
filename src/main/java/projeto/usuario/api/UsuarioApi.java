package projeto.usuario.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.rendaVariavel.repository.InvestimentoRendaVariavelService;
import projeto.seguranca.controle.Seguranca;
import projeto.usuario.api.loginModelo.LoginModelo;
import projeto.usuario.modelo.Usuario;
import projeto.usuario.repository.UsuarioService;
import projeto.seguranca.modelo.Autenticacao;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioApi {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private Seguranca seguranca;

    @GetMapping(value = "/obterPorId")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Usuario> obterPorId(@RequestHeader HttpHeaders sec) throws Exception {
        Autenticacao autenticacao;
        try {
            autenticacao = seguranca.validarToken(sec);
            Usuario usuario = usuarioService.obterPorId(autenticacao.getIdentificador());
            usuario.setSenha(null);
            usuario.setId(null);
            return ResponseEntity.ok().body(usuario);
        } catch (Exception e) {
            throw new Exception("Erro ao obter por id", e);
        }

    }

    @PostMapping(value = "/manter")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> manter(@RequestBody Usuario usuario) throws Exception {
        try {
            String token = seguranca.criarToken(usuarioService.manter(usuario).getId(), 300);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            throw new Exception("Erro ao manter usuario", e);
        }
    }

    @PostMapping(value = "/alterarUsuario")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Usuario> alterarUsuario(@RequestHeader HttpHeaders sec, @RequestBody Usuario usuario) throws Exception {
        Autenticacao autenticacao;
        try {
            autenticacao = seguranca.validarToken(sec);
            return ResponseEntity.ok().body(usuarioService.update(usuario));
        } catch (Exception e) {
            throw new Exception("Erro ao alterar usuario", e);
        }
    }

    @PostMapping(value = "/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> login(@RequestBody LoginModelo loginModelo) {
        try {
            loginModelo.setUsuario(usuarioService.login(loginModelo.getUsuario()));
            return ResponseEntity.ok().body(seguranca.criarToken(
                    loginModelo.getUsuario().getId(), loginModelo.getManterLogado()? null: 300));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/loginComToken")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> loginComToken(@RequestHeader HttpHeaders sec) {
        try {
            return ResponseEntity.ok().body(seguranca.criarToken(
                    seguranca.validarToken(sec).getIdentificador(), null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
