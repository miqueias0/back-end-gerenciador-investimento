package projeto.rentabilidadeTipo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.perfilInvestimento.modelo.Perfil;
import projeto.perfilInvestimento.repository.PerfilService;
import projeto.rentabilidadeTipo.modelo.Rentabilidade;
import projeto.rentabilidadeTipo.repository.RentabilidadeService;
import projeto.seguranca.controle.Seguranca;
import projeto.seguranca.modelo.Autenticacao;

import java.util.List;

@RestController
@RequestMapping("/api/rentabilidade")
public class RentabilidadeApi {

    @Autowired
    private RentabilidadeService rentabilidadeService;
    @Autowired
    private Seguranca seguranca;

    @GetMapping("/obterLista")
    public ResponseEntity<List<Rentabilidade>> obterLista(@RequestHeader HttpHeaders sec, @RequestParam("descricao") String descricao){
        try {
            Autenticacao autenticacao = seguranca.validarToken(sec);
            return ResponseEntity.ok(descricao!= null && !descricao.equals("")?
                    rentabilidadeService.findByDescricao(descricao, autenticacao.getIdentificador()):
                    rentabilidadeService.findAll(autenticacao.getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterPorCodigo")
    public ResponseEntity<Rentabilidade> obterPorCodigo(@RequestHeader HttpHeaders sec, @RequestParam("codigo") String codigo){
        try {
            return ResponseEntity.ok(rentabilidadeService.findByCodigo(codigo, seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/manter")
    public ResponseEntity<Rentabilidade> manter(@RequestHeader HttpHeaders sec, @RequestBody Rentabilidade rentabilidade){
        try {
            rentabilidade.setIdUsuario(seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok(rentabilidadeService.save(rentabilidade));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/alterar")
    public ResponseEntity<Rentabilidade> alterar(@RequestHeader HttpHeaders sec, @RequestBody Rentabilidade rentabilidade){
        try {
            rentabilidade.setIdUsuario(seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok(rentabilidade);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
