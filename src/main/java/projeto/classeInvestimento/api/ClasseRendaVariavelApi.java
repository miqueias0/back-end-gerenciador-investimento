package projeto.classeInvestimento.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.seguranca.controle.Seguranca;
import projeto.classeInvestimento.modelo.RendaVariavel;
import projeto.classeInvestimento.repository.RendaVariavelService;

import java.util.List;

@RestController
@RequestMapping("/api/classeRendaVariavel")
public class ClasseRendaVariavelApi {

    @Autowired
    private RendaVariavelService rendaVariavelService;
    @Autowired
    private Seguranca seguranca;

    @GetMapping("/obterLista")
    public ResponseEntity<List<RendaVariavel>> obterLista(@RequestHeader HttpHeaders sec){
        try {
            return ResponseEntity.ok(rendaVariavelService.findAll(seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/obterPorCodigo")
    public ResponseEntity<RendaVariavel> obterPorCodigo(@RequestHeader HttpHeaders sec, @RequestParam("codigo") String codigo){
        try {
            return ResponseEntity.ok(rendaVariavelService.findByCodigo(codigo, seguranca.validarToken(sec).getIdentificador()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/manter")
    public ResponseEntity<RendaVariavel> manter(@RequestHeader HttpHeaders sec, @RequestBody RendaVariavel rendaVariavel){
        try {
            rendaVariavel.setIdUsuario(seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok(rendaVariavelService.save(rendaVariavel));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/alterar")
    public ResponseEntity<RendaVariavel> alterar(@RequestHeader HttpHeaders sec, @RequestBody RendaVariavel rendaVariavel){
        try {
            rendaVariavel.setIdUsuario(seguranca.validarToken(sec).getIdentificador());
            return ResponseEntity.ok(rendaVariavelService.update(rendaVariavel));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
