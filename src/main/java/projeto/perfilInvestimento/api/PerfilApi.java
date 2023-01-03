package projeto.perfilInvestimento.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.perfilInvestimento.modelo.Perfil;
import projeto.perfilInvestimento.repository.PerfilService;
import projeto.seguranca.controle.Seguranca;
import projeto.seguranca.modelo.Autenticacao;

import java.util.List;

@RestController
@RequestMapping("/api/perfil")
public class PerfilApi {

    @Autowired
    private PerfilService perfilService;
    @Autowired
    private Seguranca seguranca;

    @GetMapping("/obterLista")
    public ResponseEntity<List<Perfil>> obterLista(@RequestHeader HttpHeaders sec, @RequestParam("descricao") String descricao){
        try {
            Autenticacao autenticacao = seguranca.validarToken(sec);
            return ResponseEntity.ok(descricao!= null && !descricao.equals("")?
                    perfilService.findByDescricao(descricao, autenticacao.getIdentificador()):
                    perfilService.findAll(autenticacao.getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterPorCodigo")
    public ResponseEntity<Perfil> obterPorCodigo(@RequestHeader HttpHeaders sec, @RequestParam("codigo") String codigo){
        try {
            return ResponseEntity.ok(perfilService.findByCodigo(codigo, seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/manter")
    public ResponseEntity<Perfil> manter(@RequestHeader HttpHeaders sec, @RequestBody Perfil perfil){
        try {
            perfil.setIdUsuario(seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok(perfilService.save(perfil));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/alterar")
    public ResponseEntity<Perfil> alterar(@RequestHeader HttpHeaders sec, @RequestBody Perfil perfil){
        try {
            perfil.setIdUsuario(seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok(perfil);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
